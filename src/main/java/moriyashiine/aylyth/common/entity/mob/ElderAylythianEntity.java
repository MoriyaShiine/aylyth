package moriyashiine.aylyth.common.entity.mob;

import moriyashiine.aylyth.common.entity.ai.goal.RootPropAttack;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
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

public class ElderAylythianEntity extends HostileEntity implements IAnimatable {
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(ElderAylythianEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static final int VARIANTS = 3;
	
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public ElderAylythianEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 100).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 13).add(EntityAttributes.GENERIC_ARMOR, 6).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
	}
	
	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 10, animationEvent -> {
			float limbSwingAmount = Math.abs(animationEvent.getLimbSwingAmount());
			AnimationBuilder builder = new AnimationBuilder();
			if (limbSwingAmount > 0.01F) {
				if (limbSwingAmount > 0.6F) {
					builder.addAnimation("run", true);
				}
				else {
					builder.addAnimation("walk", true);
				}
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
				animationEvent.getController().setAnimation(builder.addAnimation("clawswipe", true));
				return PlayState.CONTINUE;
			}
			return PlayState.STOP;
		}));
	}
	
	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_ELDER_AYLYTHIAN_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.ENTITY_ELDER_AYLYTHIAN_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENTITY_ELDER_AYLYTHIAN_DEATH;
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
		return super.damage(source, source.isFire() ? amount * 2 : amount);
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
		if (!world.isClient) {
			int xOffset = Math.sin(bodyYaw * Math.PI / 180) > 0 ? 1 : -1;
			int zOffset = Math.cos(bodyYaw * Math.PI / 180) > 0 ? 1 : -1;
			BlockPos[] checkPoses = {getBlockPos(), getBlockPos().add(xOffset, 0, 0), getBlockPos().add(0, 0, zOffset), getBlockPos().add(xOffset, 0, zOffset)};
			for (BlockPos checkPos : checkPoses) {
				if (world.getBlockState(checkPos).getMaterial().isReplaceable() && ModBlocks.YMPE_BLOCKS.sapling.getDefaultState().canPlaceAt(world, checkPos)) {
					world.setBlockState(checkPos, ModBlocks.YMPE_BLOCKS.sapling.getDefaultState());
				}
			}
			playSound(SoundEvents.BLOCK_GRASS_PLACE, getSoundVolume(), getSoundPitch());
		}
		ItemEntity item = dropItem(ModItems.AYLYTHIAN_HEART);
		if (item != null) {
			item.setCovetedItem();
		}
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
