package moriyashiine.aylyth.common.entity.types.mob;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.AylythTrackedDataHandlers;
import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BooleanSupplier;

public class PilotLightEntity extends AmbientEntity implements Flutterer {
	private static final TrackedData<Color> COLOR = DataTracker.registerData(PilotLightEntity.class, AylythTrackedDataHandlers.PILOT_LIGHT_COLOR);
	
	public PilotLightEntity(EntityType<? extends AmbientEntity> entityType, World world) {
		super(entityType, world);
		moveControl = new FlightMoveControl(this, 10, true);
	}

	/**
	 * Creates a simple escape pilot light. When right-clicked, teleports the player to the Axis Mundi.
	 * @return New pilot light entity
	 */
	@Nullable
	public static PilotLightEntity createGreenPilotLight(World world) {
		PilotLightEntity entity = AylythEntityTypes.PILOT_LIGHT.create(world);
		if (entity == null) {
			return null;
		}
		entity.setColor(Color.GREEN);
		return entity;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 5).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.25);
	}
	
	@Override
	public boolean hasNoGravity() {
		return true;
	}
	
	@Override
	protected EntityNavigation createNavigation(World world) {
		return new BirdNavigation(this, world);
	}
	
	@Override
	public void tick() {
		super.tick();

		boolean wet = isWet();
		setInvulnerable(!wet);
		if (wet) {
			damage(getDamageSources().drown(), 1);
		}

		if (getWorld().isClient) {
			switch (getColor()) {
				case BLUE -> getWorld().addParticle(AylythParticleTypes.PILOT_LIGHT, getParticleX(0.25), getY() + 0.125F + MathHelper.nextDouble(random, -0.125, 0.125), getParticleZ(0.25), 0.25, 0.25, 1);
				case YELLOW -> getWorld().addParticle(AylythParticleTypes.PILOT_LIGHT, getParticleX(0.25), getY() + 0.125F + MathHelper.nextDouble(random, -0.125, 0.125), getParticleZ(0.25), 1, 1, 0.2F);
				case GREEN -> getWorld().addParticle(AylythParticleTypes.PILOT_LIGHT, getParticleX(0.25), getY() + 0.125F + MathHelper.nextDouble(random, -0.125, 0.125), getParticleZ(0.25), 0.2F, 1, 0.2F);
			}
		}
	}
	
	@Override
	public void tickMovement() {
		super.tickMovement();
		if (!getWorld().isClient && age % 200 < 20) {
			if (getVelocity().length() <= 0.1F && random.nextFloat() < 0.1F) {
				setVelocity(random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
				setVelocity(getVelocity().multiply(0.1, getVelocity().getY() < 0 ? (getY() > 100 ? -0.025 : 0.01) : 0.025, 0.1));
			}
		}
	}
	
	@Override
	public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
		World world = player.getWorld();
		if (player instanceof ServerPlayerEntity serverPlayer && world.getRegistryKey() == AylythDimensionData.WORLD) {
			if (getColor() == Color.GREEN) {
				Optional<Vec3d> position = AylythUtil.findTeleportPosition(serverPlayer.getServerWorld(), player.getWorld().getSpawnPos());

				if (position.isPresent()) {
					FabricDimensions.teleport(player, serverPlayer.getServerWorld(), new TeleportTarget(position.get(), Vec3d.ZERO, player.headYaw, player.getPitch()));

					remove(RemovalReason.DISCARDED);
					return ActionResult.SUCCESS;
				}
			} else if (player.isCreative() || player.experienceLevel >= 5) {
				if (player.getWorld().getServer() != null) {
					ServerWorld toWorld = serverPlayer.server.getWorld(serverPlayer.getSpawnPointDimension());
					if (toWorld == null) {
						toWorld = serverPlayer.server.getOverworld();
					}
					BlockPos toPos = serverPlayer.getSpawnPointPosition() == null ? toWorld.getSpawnPos() : serverPlayer.getSpawnPointPosition();
					FabricDimensions.teleport(player, toWorld, new TeleportTarget(Vec3d.of(toPos), Vec3d.ZERO, player.headYaw, player.getPitch()));
				}

				if (!player.isCreative()) {
					player.addExperienceLevels(-5);
				}
				remove(RemovalReason.DISCARDED);
				return ActionResult.SUCCESS;
			}
		}
		return super.interactAt(player, hitPos, hand);
	}
	
	@Override
	public boolean isInAir() {
		return true;
	}
	
	@Override
	public int getLimitPerChunk() {
		return 2;
	}
	
	@Override
	protected void pushAway(Entity entity) {
	}



	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		setColor(random.nextBoolean() ? Color.YELLOW : Color.BLUE);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(COLOR, Color.YELLOW);
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString("color", getColor().asString());
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("color")) {
			setColor(Color.CODEC.parse(NbtOps.INSTANCE, nbt.get("color")).getOrThrow(false, s -> {}));
		}
	}
	
	public void setColor(Color color) {
		this.dataTracker.set(COLOR, color);
	}
	
	public Color getColor() {
		return dataTracker.get(COLOR);
	}

	public static boolean canSpawn(EntityType<PilotLightEntity> pilotLightEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
		return spawnReason != SpawnReason.NATURAL || random.nextInt(10) == 0;
	}

	public enum Color implements StringIdentifiable {
		YELLOW("yellow"),
		BLUE("blue"),
		GREEN("green");

		public static final com.mojang.serialization.Codec<Color> CODEC = StringIdentifiable.createCodec(Color::values);

		private final String name;

		Color(String name) {
			this.name = name;
		}

		public Color getColor(String name) {
			for (Color color : values()) {
				if (color.name.equals(name)) {
					return color;
				}
			}
			throw new IllegalArgumentException("Name is invalid");
		}

		@Override
		public String asString() {
			return name;
		}
	}
}
