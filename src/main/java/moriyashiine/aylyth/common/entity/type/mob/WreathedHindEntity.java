package moriyashiine.aylyth.common.entity.type.mob;

import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.api.interfaces.Pledgeable;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.entity.trackeddata.AylythTrackedDataHandlers;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import moriyashiine.aylyth.common.entity.ai.brain.WreathedHindBrain;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WreathedHindEntity extends HostileEntity implements GeoEntity, Pledgeable {
    private static final EntityAttributeModifier SNEAKY_SPEED_PENALTY = new EntityAttributeModifier(UUID.fromString("5CD17E11-A74A-43D3-A529-90FDE04B191E"), "sneaky", -0.15D, EntityAttributeModifier.Operation.ADDITION);
    private EntityAttributeInstance modifiableattributeinstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final TrackedData<AttackType> ATTACK_TYPE = DataTracker.registerData(WreathedHindEntity.class, AylythTrackedDataHandlers.WREATHED_ATTACK_TYPE);
    public static final TrackedData<Boolean> IS_PLEDGED = DataTracker.registerData(WreathedHindEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public WreathedHindEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 13)
                .add(EntityAttributes.GENERIC_ARMOR, 3)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACK_TYPE, AttackType.NONE);
        this.dataTracker.startTracking(IS_PLEDGED, false);
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("wreathedHindBrain");
        this.getBrain().tick((ServerWorld)this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        WreathedHindBrain.updateActivities(this);
        super.mobTick();
        if (WreathedHindBrain.isPledgedPlayerLow(this.getTarget(), this)) {
            modifiableattributeinstance.removeModifier(SNEAKY_SPEED_PENALTY);
            modifiableattributeinstance.addTemporaryModifier(SNEAKY_SPEED_PENALTY);
        } else if (modifiableattributeinstance.hasModifier(SNEAKY_SPEED_PENALTY)) {
            modifiableattributeinstance.removeModifier(SNEAKY_SPEED_PENALTY);
        }
        if (age % 20 == 0) {
            if (getPledgedPlayerUUID() == null) {
                setIsPledged(false);
            }
        }
    }

    @Override
    public int getMaxLookYawChange() {
        return 3;
    }

    @Override
    public int getMaxLookPitchChange() {
        return 3;
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return state.isIn(FluidTags.WATER);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if(stack.isIn(AylythItemTags.PLEDGE_ITEMS) && getPledgedPlayerUUID() == null) {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                AylythCriteria.HIND_PLEDGE.trigger(serverPlayer, this);
            }
            setPledgedPlayer(player);
            AylythUtil.decreaseStack(stack, player);
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        ((AttachmentTarget)getWorld()).getAttachedOrCreate(ModAttachmentTypes.PLEDGE_STATE).removePledge(this);
    }

    @Override
    public boolean tryAttack(Entity target) {
       if (getAttackType() == AttackType.MELEE) {
           return super.tryAttack(target);
        } else if (getAttackType() == AttackType.KILLING) {
            if (target instanceof PlayerEntity player) {
                return tryKillingAttack(player);
            }
        }
       return false;
    }

    public boolean tryKillingAttack(PlayerEntity target) {
        float f = 6;
        boolean bl = target.damage(getWorld().modDamageSources().killingBlow(this), f);
        if (bl) {
            this.disablePlayerShield(target, this.getMainHandStack(), target.isUsingItem() ? target.getActiveItem() : ItemStack.EMPTY);
            this.applyDamageEffects(this, target);
            this.onAttacking(target);
        }
        if (target.isDead()) {
            removePledge();
        }
        return bl;
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean result = super.damage(source, amount);
        if (result) {
            Entity attacker = source.getAttacker();
            if (attacker != null && attacker.getUuid().equals(getPledgedPlayerUUID())) {
                if (!getBrain().hasMemoryModule(AylythMemoryTypes.SECOND_CHANCE)) {
                    getBrain().remember(AylythMemoryTypes.SECOND_CHANCE, WreathedHindBrain.SecondChance.WARNING, 600);
                } else {
                    getBrain().remember(AylythMemoryTypes.SECOND_CHANCE, WreathedHindBrain.SecondChance.BETRAY, 600);
                }
            }
        }
        return result;
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        placeStrewnLeaves(getWorld(), getBlockPos()); // TODO: should we try to spawn strewn leaves when it is killed by the kill command? What about mob griefing?
    }

    public void placeStrewnLeaves(World world, BlockPos blockPos){
        List<BlockPos> possiblePositions = new ArrayList<>();
        for(int x = -2; x <= 2; x++){
            for(int z = -2; z <= 2; z++){
                for(int y = -2; y <= 2; y++){
                    BlockPos offsetPos = blockPos.add(x,y,z);
                    if(!world.isClient && world.getBlockState(offsetPos).isReplaceable() && world.getBlockState(offsetPos.down()).isIn(BlockTags.DIRT) ){
                        possiblePositions.add(offsetPos);
                    }
                }
            }
        }
        if (!possiblePositions.isEmpty()) {
            int random = this.random.nextBetween(2, 4);
            for(int i = 0; i < random; i++){
                if(possiblePositions.size() >= i){
                    BlockPos placePos = Util.getRandom(possiblePositions, this.random);
                    world.setBlockState(placePos, AylythBlocks.OAK_STREWN_LEAVES.getDefaultState());
                    playSound(AylythSoundEvents.BLOCK_STREWN_LEAVES_STEP.value(), getSoundVolume(), getSoundPitch());
                }
            }
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (getPledgedPlayerUUID() != null) {

        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar animationData) {
        animationData.add(new AnimationController<>(this, "controller", 10, this::predicate));
        animationData.add(new AnimationController<>(this, "controller2", 1, this::attackPredicate));
    }

    private <T extends GeoAnimatable> PlayState attackPredicate(AnimationState<T> event) {
        var builder = RawAnimation.begin();
        if (this.getAttackType() == AttackType.MELEE) {
            builder.then("attack.melee", Animation.LoopType.PLAY_ONCE);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        } else if (this.getAttackType() == AttackType.RANGED) {
            builder.then("attack.ranged", Animation.LoopType.PLAY_ONCE);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        } else if (this.getAttackType() == AttackType.KILLING) {
            builder.then("killing.blow", Animation.LoopType.PLAY_ONCE);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> event) {
        var builder = RawAnimation.begin();
        float limbSwingAmount = Math.abs(event.getLimbSwingAmount());
        if(limbSwingAmount > 0.01F) {
            builder.thenLoop("walk");
        }else{
            builder.thenLoop("idle");
        }
        event.getController().setAnimation(builder);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public AttackType getAttackType() {
        return dataTracker.get(ATTACK_TYPE);
    }

    public void setAttackType(AttackType attackType) {
        dataTracker.set(ATTACK_TYPE, attackType);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return WreathedHindBrain.create(this, dynamic);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<WreathedHindEntity> getBrain() {
        return (Brain<WreathedHindEntity>) super.getBrain();
    }

    public static boolean canSpawn(EntityType<WreathedHindEntity> wreathedHindEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return canMobSpawn(wreathedHindEntityEntityType, serverWorldAccess, spawnReason, blockPos, random) && serverWorldAccess.getDifficulty() != Difficulty.PEACEFUL && random.nextBoolean();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AylythSoundEvents.ENTITY_WREATHED_HIND_AMBIENT.value();
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 300;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AylythSoundEvents.ENTITY_WREATHED_HIND_HURT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AylythSoundEvents.ENTITY_WREATHED_HIND_DEATH.value();
    }

    @Nullable
    @Override
    public UUID getPledgedPlayerUUID() {
        if (!getWorld().isClient) {
            return ((AttachmentTarget)getWorld()).getAttachedOrCreate(ModAttachmentTypes.PLEDGE_STATE).getPledged(this);
        }
        return null;
    }

    public void setPledgedPlayer(PlayerEntity player) {
        if (!getWorld().isClient) {
            ((AttachmentTarget)getWorld()).getAttachedOrCreate(ModAttachmentTypes.PLEDGE_STATE).addPledge(player.getUuid(), this.getUuid());
        }
        setIsPledged(true);
    }

    @Override
    public void removePledge() {
        if (!getWorld().isClient) {
            ((AttachmentTarget)getWorld()).getAttachedOrCreate(ModAttachmentTypes.PLEDGE_STATE).removePledge(this);
        }
        setIsPledged(false);
    }

    public boolean isPledged() {
        return dataTracker.get(IS_PLEDGED);
    }

    public void setIsPledged(boolean isPledged) {
        dataTracker.set(IS_PLEDGED, isPledged);
    }

    public enum AttackType implements StringIdentifiable {
        NONE("none"),
        MELEE("melee"),
        RANGED("ranged"),
        KILLING("killing");

        private final String name;

        AttackType(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
