package moriyashiine.aylyth.common.entity.type.mob;

import moriyashiine.aylyth.common.entity.ai.goal.RootPropAttack;
import moriyashiine.aylyth.common.registry.AylythBlocks;
import moriyashiine.aylyth.common.registry.AylythSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
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

public class ElderAylythianEntity extends HostileEntity implements GeoEntity {
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(ElderAylythianEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static final int VARIANTS = 3;

	private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	
	public ElderAylythianEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 100)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 13)
				.add(EntityAttributes.GENERIC_ARMOR, 6)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
	}
	
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar animationData) {
		animationData.add(new AnimationController<>(this, "controller", 10, animationEvent -> {
			float limbSwingAmount = Math.abs(animationEvent.getLimbSwingAmount());
			var builder = RawAnimation.begin();
			if (limbSwingAmount > 0.01F) {
				if (limbSwingAmount > 0.6F) {
					builder.thenLoop("run");
				}
				else {
					builder.thenLoop("walk");
				}
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
				animationEvent.getController().setAnimation(builder.thenLoop("clawswipe"));
				return PlayState.CONTINUE;
			}
			return PlayState.STOP;
		}));
	}

	@Override
	public boolean canPickupItem(ItemStack stack) {
		return false;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}
	
	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return AylythSoundEvents.ENTITY_ELDER_AYLYTHIAN_AMBIENT.value();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return AylythSoundEvents.ENTITY_ELDER_AYLYTHIAN_HURT.value();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return AylythSoundEvents.ENTITY_ELDER_AYLYTHIAN_DEATH.value();
	}
	
	@Override
	public boolean spawnsTooManyForEachTry(int count) {
		return count > 1;
	}
	
	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return 0.5F;
	}
	
	@Override
	public int getLimitPerChunk() {
		return 1;
	}
	
	@Override
	public boolean damage(DamageSource source, float amount) {
		setPersistent();
		return super.damage(source, source.isIn(DamageTypeTags.IS_FIRE) ? amount * 2 : amount);
	}

	@Override
	public boolean tryAttack(Entity target) {
		setPersistent();
		return super.tryAttack(target);
	}
	
	@Override
	public void setTarget(@Nullable LivingEntity target) {
		if (AylythianEntity.isTargetInBush(target)) {
			target = null;
		}
		super.setTarget(target);
	}
	
	@Override
	protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
		super.dropEquipment(source, lootingMultiplier, allowDrops);
		if (!getWorld().isClient) {
			int xOffset = Math.sin(bodyYaw * MathHelper.RADIANS_PER_DEGREE) > 0 ? 1 : -1;
			int zOffset = Math.cos(bodyYaw * MathHelper.RADIANS_PER_DEGREE) > 0 ? 1 : -1;
			BlockPos[] checkPoses = {getBlockPos(), getBlockPos().add(xOffset, 0, 0), getBlockPos().add(0, 0, zOffset), getBlockPos().add(xOffset, 0, zOffset)};
			boolean hasPlaced = false;
			for (BlockPos checkPos : checkPoses) {
				if (getWorld().getBlockState(checkPos).isReplaceable() && AylythBlocks.YMPE_SAPLING.getDefaultState().canPlaceAt(getWorld(), checkPos)) {
					getWorld().setBlockState(checkPos, AylythBlocks.YMPE_SAPLING.getDefaultState());
					hasPlaced = true;
				}
			}
			if (hasPlaced) {
				playSound(AylythBlocks.YMPE_SAPLING.getDefaultState().getSoundGroup().getPlaceSound(), getSoundVolume(), getSoundPitch());
			}
		}
		// TODO: Commented out due to a ympe dagger recipe already existing for this drop
//		ItemEntity item = dropItem(ModItems.AYLYTHIAN_HEART);
//		if (item != null) {
//			item.setCovetedItem();
//		}
	}
	
	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		dataTracker.set(VARIANT, random.nextInt(VARIANTS));
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}
	
	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1.2F, false));
		goalSelector.add(2, new WanderAroundFarGoal(this, 0.5F));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		goalSelector.add(4, new RootPropAttack(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(VARIANT, 0);
	}
	
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		dataTracker.set(VARIANT, nbt.getInt("Variant"));
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", dataTracker.get(VARIANT));
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}
}
