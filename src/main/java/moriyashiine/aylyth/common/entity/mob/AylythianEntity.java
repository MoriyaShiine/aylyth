package moriyashiine.aylyth.common.entity.mob;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class AylythianEntity extends HostileEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public AylythianEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.setCanPickUpLoot(true);
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 35).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5).add(EntityAttributes.GENERIC_ARMOR, 2).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}
	
	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 10, animationEvent -> {
			float limbSwingAmount = Math.abs(animationEvent.getLimbSwingAmount());
			AnimationBuilder builder = new AnimationBuilder();
			if (limbSwingAmount > 0.01F) {
				MoveState state = limbSwingAmount > 0.6F ? MoveState.RUN : limbSwingAmount > 0.3F ? MoveState.WALK : MoveState.STALK;
				builder = switch (state) {
					case RUN -> builder.addAnimation("run", true);
					case WALK -> builder.addAnimation("walk", true);
					case STALK -> builder.addAnimation("stalk", true);
				};
			}
			else {
				builder.addAnimation("idle", true);
			}
			animationEvent.getController().setAnimation(builder);
			return PlayState.CONTINUE;
		}));
		animationData.addAnimationController(new AnimationController<>(this, "arms", 0, animationEvent -> {
			AnimationBuilder builder = new AnimationBuilder();
			if (handSwingTicks > 0 && !isDead()) {
				animationEvent.getController().setAnimation(builder.addAnimation(getMainArm() == Arm.RIGHT ? "clawswipe_right" : "clawswipe_left", true));
				return PlayState.CONTINUE;
			}
			return PlayState.STOP;
		}));
	}
	
	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public void tick() {
		super.tick();
		if (age % 200 == 0) {
			heal(1);
		}
	}
	
	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_AYLYTHIAN_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.ENTITY_AYLYTHIAN_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENTITY_AYLYTHIAN_DEATH;
	}
	
	@Override
	public boolean spawnsTooManyForEachTry(int count) {
		return count > 3;
	}
	
	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return 0.5F;
	}
	
	@Override
	public int getLimitPerChunk() {
		return 3;
	}
	
	@Override
	public boolean damage(DamageSource source, float amount) {
		return super.damage(source, source.isFire() ? amount * 2 : amount);
	}
	
	@Override
	public void setTarget(@Nullable LivingEntity target) {
		if (isTargetInBush(target)) {
			target = null;
		}
		super.setTarget(target);
	}
	
	@Override
	protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
		super.dropEquipment(source, lootingMultiplier, allowDrops);
		if (!world.isClient && world.getBlockState(getBlockPos()).getMaterial().isReplaceable() && ModBlocks.YMPE_SAPLING.getDefaultState().canPlaceAt(world, getBlockPos())) {
			world.setBlockState(getBlockPos(), ModBlocks.YMPE_SAPLING.getDefaultState());
			playSound(SoundEvents.BLOCK_GRASS_PLACE, getSoundVolume(), getSoundPitch());
		}
	}
	
	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1.2F, false));
		goalSelector.add(2, new WanderAroundFarGoal(this, 0.5F));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
	}
	
	public static boolean canSpawn(EntityType<? extends MobEntity> aylythianEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
		return canMobSpawn(aylythianEntityEntityType, serverWorldAccess, spawnReason, blockPos, random) && serverWorldAccess.getDifficulty() != Difficulty.PEACEFUL && random.nextBoolean();
	}
	
	public static boolean isTargetInBush(LivingEntity target) {
		if (target != null && target.isSneaking()) {
			for (int i = 0; i <= target.getHeight(); i++) {
				if (target.world.getBlockState(target.getBlockPos().up(i)).getBlock() != ModBlocks.AYLYTH_BUSH) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	enum MoveState {
		WALK, RUN, STALK
	}
}
