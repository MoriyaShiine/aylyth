package moriyashiine.aylyth.common.entity.types.passive;

import moriyashiine.aylyth.common.other.AylythParticleTypes;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PilotLightEntity extends AmbientEntity implements Flutterer {
	private static final TrackedData<Boolean> IS_BLUE = DataTracker.registerData(PilotLightEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	
	public PilotLightEntity(EntityType<? extends AmbientEntity> entityType, World world) {
		super(entityType, world);
		moveControl = new FlightMoveControl(this, 10, true);
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
			if (!isBlue()) {
				getWorld().addParticle(AylythParticleTypes.PILOT_LIGHT, getParticleX(0.25), getY() + 0.125F + MathHelper.nextDouble(random, -0.125, 0.125), getParticleZ(0.25), 1, 1, 0.2F);
			} else {
				getWorld().addParticle(AylythParticleTypes.PILOT_LIGHT, getParticleX(0.25), getY() + 0.125F + MathHelper.nextDouble(random, -0.125, 0.125), getParticleZ(0.25), 0.25, 0.25, 1);
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
		if (player instanceof ServerPlayerEntity serverPlayer && player.getWorld().getRegistryKey() == AylythDimensionData.AYLYTH) {
			if (player.isCreative() || player.experienceLevel >= 5) {
				if(player.getWorld().getServer() != null){
					ServerWorld toWorld = player.getWorld().getServer().getWorld(serverPlayer.getSpawnPointDimension());
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
		setBlue(random.nextBoolean());
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(IS_BLUE, false);
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("IsBlue", isBlue());
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		setBlue(nbt.getBoolean("IsBlue"));
	}
	
	private void setBlue(boolean blue) {
		this.dataTracker.set(IS_BLUE, blue);
	}
	
	private boolean isBlue() {
		return dataTracker.get(IS_BLUE);
	}

	public static boolean canSpawn(EntityType<PilotLightEntity> pilotLightEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
		return spawnReason != SpawnReason.NATURAL || random.nextInt(10) == 0;
	}

}
