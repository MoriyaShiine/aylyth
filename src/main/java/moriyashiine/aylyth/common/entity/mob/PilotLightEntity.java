package moriyashiine.aylyth.common.entity.mob;

import moriyashiine.aylyth.common.registry.ModDimensions;
import moriyashiine.aylyth.common.registry.ModParticles;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class PilotLightEntity extends AmbientEntity {
	public PilotLightEntity(EntityType<? extends AmbientEntity> entityType, World world) {
		super(entityType, world);
		moveControl = new FlightMoveControl(this, 10, true);
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 5).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}
	
	@Override
	protected void initGoals() {
		super.initGoals();
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
			damage(DamageSource.DROWN, 1);
		}
		if (world.isClient) {
			world.addParticle(ModParticles.PILOT_LIGHT, getParticleX(0.5), getY() + 0.25 + MathHelper.nextDouble(random, -0.25, 0.25), getParticleZ(0.5), 0, 0, 0);
		}
	}
	
	@Override
	public void onPlayerCollision(PlayerEntity player) {
		super.onPlayerCollision(player);
		if (player instanceof ServerPlayerEntity serverPlayer && player.world.getRegistryKey() == ModDimensions.AYLYTH) {
			if (player.isCreative() || player.experienceLevel >= 5) {
				ServerWorld toWorld = player.world.getServer().getWorld(serverPlayer.getSpawnPointDimension());
				BlockPos toPos = serverPlayer.getSpawnPointPosition() == null ? toWorld.getSpawnPos() : serverPlayer.getSpawnPointPosition(); //white pilot light
				//todo yellow plot light
				FabricDimensions.teleport(player, toWorld, new TeleportTarget(Vec3d.of(toPos), Vec3d.ZERO, player.headYaw, player.getPitch()));
				if (!player.isCreative()) {
					player.addExperience(-55);
				}
				remove(RemovalReason.DISCARDED);
			}
		}
	}
	
	@Override
	protected void pushAway(Entity entity) {
	}
	
	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}
	
	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
	}
}
