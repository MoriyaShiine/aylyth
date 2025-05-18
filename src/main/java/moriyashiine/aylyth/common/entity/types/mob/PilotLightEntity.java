package moriyashiine.aylyth.common.entity.types.mob;

import io.netty.buffer.ByteBuf;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.AylythTrackedDataHandlers;
import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

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
	public static PilotLightEntity createGreenPilotLight(World world, SpawnReason reason) {
		PilotLightEntity entity = AylythEntityTypes.PILOT_LIGHT.create(world, reason);
		if (entity == null) {
			return null;
		}
		entity.setColor(Color.GREEN);
		return entity;
	}

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		setColor(random.nextBoolean() ? Color.YELLOW : Color.BLUE);
		return super.initialize(world, difficulty, spawnReason, entityData);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder
				.add(COLOR, Color.YELLOW)
		);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.MAX_HEALTH, 5)
				.add(EntityAttributes.MOVEMENT_SPEED, 0.25)
				.add(EntityAttributes.FLYING_SPEED, 0.25);
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
	protected void mobTick(ServerWorld world) {
		super.mobTick(world);
		// TODO: Check that this still works as expected
		boolean wet = isWet();
		setInvulnerable(!wet);
		if (wet) {
			damage(world, getDamageSources().drown(), 1);
		}
	}

	@Override
	public void tick() {
		super.tick();

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
				AylythUtil.teleportTo(serverPlayer.getServerWorld(), serverPlayer, serverPlayer.getServerWorld().getSpawnPos(), AylythUtil::findTeleportPosition);

				remove(RemovalReason.DISCARDED);
				return ActionResult.SUCCESS;
			} else if (player.isCreative() || player.experienceLevel >= 5) {
				// TODO: Check that this still works well
				player.teleportTo(serverPlayer.getRespawnTarget(true, TeleportTarget.NO_OP));

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



	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString("color", getColor().asString());
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("color")) {
			setColor(Color.CODEC.parse(NbtOps.INSTANCE, nbt.get("color")).getOrThrow());
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
		YELLOW(0, "yellow"),
		BLUE(1, "blue"),
		GREEN(2, "green");

		public static final com.mojang.serialization.Codec<Color> CODEC = StringIdentifiable.createCodec(Color::values);
		public static final IntFunction<Color> ID_TO_VALUE_FUNCTION = ValueLists.createIdToValueFunction(
				Color::getIndex, values(), ValueLists.OutOfBoundsHandling.WRAP
		);
		public static final PacketCodec<ByteBuf, Color> PACKET_CODEC = PacketCodecs.indexed(ID_TO_VALUE_FUNCTION, Color::getIndex);

		private final int index;
		private final String name;

		Color(int index, String name) {
			this.index = index;
			this.name = name;
		}

		public int getIndex() {
			return index;
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
