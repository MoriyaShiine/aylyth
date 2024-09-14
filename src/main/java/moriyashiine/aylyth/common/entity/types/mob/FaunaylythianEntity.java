package moriyashiine.aylyth.common.entity.types.mob;

import moriyashiine.aylyth.common.entity.ai.goal.PounceAttackGoal;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.other.AylythSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class FaunaylythianEntity extends HostileEntity implements GeoEntity {
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(FaunaylythianEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> POUNCING = DataTracker.registerData(FaunaylythianEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static final int VARIANTS = 2;

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public FaunaylythianEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8)
                .add(EntityAttributes.GENERIC_ARMOR, 2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
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
        goalSelector.add(3, new PounceAttackGoal(this, 0.7f));
        targetSelector.add(0, new RevengeGoal(this));
        targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(VARIANT, 0);
        dataTracker.startTracking(POUNCING, false);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(VARIANT, nbt.getInt("Variant"));
        dataTracker.set(POUNCING, nbt.getBoolean("Pouncing"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", dataTracker.get(VARIANT));
        nbt.putBoolean("Pouncing", dataTracker.get(POUNCING));
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        double random = this.random.nextDouble();
        if (random <= 0.20 && !getWorld().isClient && getWorld().getBlockState(getBlockPos()).isReplaceable() && AylythBlocks.YMPE_SAPLING.getDefaultState().canPlaceAt(getWorld(), getBlockPos())) {
            getWorld().setBlockState(getBlockPos(), AylythBlocks.YMPE_SAPLING.getDefaultState());
            playSound(SoundEvents.BLOCK_GRASS_PLACE, getSoundVolume(), getSoundPitch());
        } else if (random <= 0.30 && !getWorld().isClient && getWorld().getBlockState(getBlockPos()).isReplaceable() && AylythBlocks.LARGE_WOODY_GROWTH.getDefaultState().canPlaceAt(getWorld(), getBlockPos())) {
            placeWoodyGrowths(getWorld(), getBlockPos());
        }
    }

    public void placeWoodyGrowths(World world, BlockPos blockPos){
        List<BlockPos> listPos = new ArrayList<>();
        int index = 0;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 1; y++) {
                    if (!world.isClient && world.getBlockState(blockPos.add(x,y,z)).isReplaceable() && world.getBlockState(blockPos.add(x,y,z).down()).isIn(BlockTags.DIRT)) {
                        listPos.add(index, blockPos.add(x,y,z));
                        index++;
                    }
                }

            }
        }

        if (!listPos.isEmpty()) {
            int random = world.getRandom().nextBetween(1, 3);
            Block largeWoodyGrowth = AylythBlocks.LARGE_WOODY_GROWTH;
            for (int i = 0; i < random; i++) {
                if (listPos.size() >= i) {
                    BlockPos placePos = listPos.get(world.getRandom().nextInt(listPos.size()));
                    if (world.getRandom().nextBoolean()) {
                        if (largeWoodyGrowth.getDefaultState().canPlaceAt(world, placePos)) {
                            world.setBlockState(placePos,largeWoodyGrowth.getDefaultState());
                        }
                    } else {
                        world.setBlockState(placePos, AylythBlocks.SMALL_WOODY_GROWTH.getDefaultState());
                    }
                    playSound(SoundEvents.BLOCK_GRASS_PLACE, getSoundVolume(), getSoundPitch());
                }

            }
        }
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return false;
    }

    public static boolean canSpawn(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return canMobSpawn(type, world, spawnReason, pos, random) && world.getDifficulty() != Difficulty.PEACEFUL && random.nextBoolean();
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar animationData) {
        animationData.add(new AnimationController<>(this, "controller", 10, animationEvent -> {
            float limbSwingAmount = Math.abs(animationEvent.getLimbSwingAmount());
            var builder = RawAnimation.begin();
            if(dataTracker.get(POUNCING)){
                builder.then("animation.faunaylythian.prepare", Animation.LoopType.PLAY_ONCE);
                builder.then("animation.faunaylythian.jump", Animation.LoopType.PLAY_ONCE);
            } else if (limbSwingAmount > 0.01F) {
                if (limbSwingAmount > 0.6F) {
                    builder.thenLoop("animation.faunaylythian.chasing");
                } else {
                    builder.thenLoop("animation.faunaylythian.walk");
                }
            } else {
                builder.thenLoop("animation.faunaylythian.idle");
            }
            animationEvent.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        }));
        animationData.add(new AnimationController<>(this, "animation.faunaylythian.attack", 0, animationEvent -> {
            var builder = RawAnimation.begin();
            if (handSwingTicks > 0 && !isDead()) {
                animationEvent.getController().setAnimation(builder.thenLoop("animation.faunaylythian.hit"));
                return PlayState.CONTINUE;
            }
            return PlayState.STOP;
        }));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AylythSoundEvents.ENTITY_FAUNAYLYTHIAN_AMBIENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AylythSoundEvents.ENTITY_FAUNAYLYTHIAN_HURT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AylythSoundEvents.ENTITY_FAUNAYLYTHIAN_DEATH.value();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }
}
