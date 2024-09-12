package moriyashiine.aylyth.common.entity.type.mob;

import moriyashiine.aylyth.common.block.type.LargeWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.registry.AylythSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
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
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Arm;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class AylythianEntity extends HostileEntity implements GeoEntity {
	private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	
	public AylythianEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.setCanPickUpLoot(true);
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 35)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
				.add(EntityAttributes.GENERIC_ARMOR, 2)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}
	
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar animationData) {
		animationData.add(new AnimationController<>(this, "controller", 10, animationEvent -> {
			float limbSwingAmount = Math.abs(animationEvent.getLimbSwingAmount());
			var builder = RawAnimation.begin();
			if (limbSwingAmount > 0.01F) {
				MoveState state = limbSwingAmount > 0.6F ? MoveState.RUN : limbSwingAmount > 0.3F ? MoveState.WALK : MoveState.STALK;
				builder = switch (state) {
					case RUN -> builder.thenLoop("run");
					case WALK -> builder.thenLoop("walk");
					case STALK -> builder.thenLoop("stalk");
				};
			}
			else {
				builder.thenLoop("idle");
			}
			animationEvent.getController().setAnimation(builder);
			return PlayState.CONTINUE;
		}));
		animationData.add(new AnimationController<>(this, "arms", 0, animationEvent -> {
			var builder = RawAnimation.begin();
			if (handSwingTicks > 0 && !isDead()) {
				animationEvent.getController().setAnimation(builder.thenLoop(getMainArm() == Arm.RIGHT ? "clawswipe_right" : "clawswipe_left"));
				return PlayState.CONTINUE;
			}
			return PlayState.STOP;
		}));
	}
	
	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}
	
	@Override
	public void tick() {
		super.tick();
		if (age % 200 == 0) {
			heal(1);
		}
	}

	@Override
	public boolean canPickupItem(ItemStack stack) {
		return false;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return AylythSoundEvents.ENTITY_AYLYTHIAN_AMBIENT.value();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return AylythSoundEvents.ENTITY_AYLYTHIAN_HURT.value();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return AylythSoundEvents.ENTITY_AYLYTHIAN_DEATH.value();
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
		return super.damage(source, source.isIn(DamageTypeTags.IS_FIRE) ? amount * 2 : amount);
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
		double random = this.random.nextDouble();
		if (random <= 0.20 && !getWorld().isClient && getWorld().getBlockState(getBlockPos()).isReplaceable() && AylythBlocks.LARGE_WOODY_GROWTH.getDefaultState().canPlaceAt(getWorld(), getBlockPos())) {
			placeWoodyGrowths(getWorld(), getBlockPos());
		} else if (random <= 0.30 && !getWorld().isClient && getWorld().getBlockState(getBlockPos()).isReplaceable() && AylythBlocks.YMPE_SAPLING.getDefaultState().canPlaceAt(getWorld(), getBlockPos())) {
			BlockState state = AylythBlocks.YMPE_SAPLING.getDefaultState();
			getWorld().setBlockState(getBlockPos(), state);
			playSound(state.getSoundGroup().getPlaceSound(), getSoundVolume(), getSoundPitch());
		}
	}

	public void placeWoodyGrowths(World world, BlockPos blockPos){
		List<BlockPos> possiblePositions = new ArrayList<>();
		for(int x = -1; x <= 1; x++){
			for(int z = -1; z <= 1; z++){
				for(int y = -1; y <= 1; y++){
					BlockPos offsetPos = blockPos.add(x,y,z);
					if(!world.isClient && world.getBlockState(offsetPos).isReplaceable() && world.getBlockState(offsetPos.down()).isIn(BlockTags.DIRT) ){
						possiblePositions.add(offsetPos);
					}
				}

			}
		}
		if (!possiblePositions.isEmpty()) {
			int random = this.random.nextBetween(1, 3);
			for(int i = 0; i < random; i++){
				if(possiblePositions.size() >= i){
					BlockPos placePos = Util.getRandom(possiblePositions, this.random);
					BlockState placementState = this.random.nextBoolean() ? AylythBlocks.LARGE_WOODY_GROWTH.getDefaultState() : AylythBlocks.SMALL_WOODY_GROWTH.getDefaultState();
					if(placementState.canPlaceAt(world, placePos)){
						LargeWoodyGrowthBlock.placeInWorld(placementState, world, placePos);
						playSound(placementState.getSoundGroup().getPlaceSound(), getSoundVolume(), getSoundPitch());
					}
				}
			}
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

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	public static boolean isTargetInBush(LivingEntity target) {
		if (target != null && target.isSneaking()) {
			for (int i = 0; i <= target.getHeight(); i++) {
				if (target.getWorld().getBlockState(target.getBlockPos().up(i)).getBlock() != AylythBlocks.AYLYTH_BUSH) {
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
