package moriyashiine.aylyth.common.entity.mob;

import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.api.interfaces.Pledgeable;
import moriyashiine.aylyth.common.entity.ai.brain.WreathedHindBrain;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.Entity;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.*;

public class WreathedHindEntity extends HostileEntity implements IAnimatable, Pledgeable {
    private static final EntityAttributeModifier SNEAKY_SPEED_PENALTY = new EntityAttributeModifier(UUID.fromString("5CD17E11-A74A-43D3-A529-90FDE04B191E"), "sneaky", -0.15D, EntityAttributeModifier.Operation.ADDITION);
    private EntityAttributeInstance modifiableattributeinstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final TrackedData<AttackType> ATTACK_TYPE = DataTracker.registerData(WreathedHindEntity.class, ModDataTrackers.WREATHED_HIND_ATTACKS);
    public static final TrackedData<Boolean> IS_PLEDGED = DataTracker.registerData(WreathedHindEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private UUID pledgedPlayer = null;

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
        this.world.getProfiler().push("wreathedHindBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().pop();
        WreathedHindBrain.updateActivities(this);
        super.mobTick();
        if (WreathedHindBrain.isPledgedPlayerLow(this.getTarget(), this)) {
            modifiableattributeinstance.removeModifier(SNEAKY_SPEED_PENALTY);
            modifiableattributeinstance.addTemporaryModifier(SNEAKY_SPEED_PENALTY);
        } else if (modifiableattributeinstance.hasModifier(SNEAKY_SPEED_PENALTY)) {
            modifiableattributeinstance.removeModifier(SNEAKY_SPEED_PENALTY);
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
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (getPledgedPlayerUUID() != null) {
            nbt.putUuid("pledged_player", getPledgedPlayerUUID());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("pledged_player")) {
            pledgedPlayer = nbt.getUuid("pledged_player");
            setIsPledged(true);
        }
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if(stack.isIn(ModTags.PLEDGE_ITEMS) && getPledgedPlayerUUID() == null) {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                ModCriteria.HIND_PLEDGE.trigger(serverPlayer, this);
            }
            setPledgedPlayer(player);
            HindPledgeHolder.of(player).ifPresent(hindHolder -> hindHolder.setHindUuid(this.getUuid()));
            AylythUtil.decreaseStack(stack, player);
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        addPledgeToRemove(world);
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
        boolean bl = target.damage(ModDamageSources.killingBlow(this), f);
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
                if (!getBrain().hasMemoryModule(ModMemoryTypes.SECOND_CHANCE)) {
                    getBrain().remember(ModMemoryTypes.SECOND_CHANCE, WreathedHindBrain.SecondChance.WARNING, 600);
                } else {
                    getBrain().remember(ModMemoryTypes.SECOND_CHANCE, WreathedHindBrain.SecondChance.BETRAY, 600);
                }
            }
        }
        return result;
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        placeStrewnLeaves(world, getBlockPos()); // TODO: should we try to spawn strewn leaves when it is killed by the kill command? What about mob griefing?
    }

    public void placeStrewnLeaves(World world, BlockPos blockPos){
        List<BlockPos> possiblePositions = new ArrayList<>();
        for(int x = -2; x <= 2; x++){
            for(int z = -2; z <= 2; z++){
                for(int y = -2; y <= 2; y++){
                    BlockPos offsetPos = blockPos.add(x,y,z);
                    if(!world.isClient && world.getBlockState(offsetPos).getMaterial().isReplaceable() && world.getBlockState(offsetPos.down()).isIn(BlockTags.DIRT) ){
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
                    world.setBlockState(placePos, ModBlocks.OAK_STREWN_LEAVES.getDefaultState());
                    playSound(ModSoundEvents.BLOCK_STREWN_LEAVES_STEP, getSoundVolume(), getSoundPitch());
                }
            }
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
        animationData.addAnimationController(new AnimationController<>(this, "controller2", 1, this::attackPredicate));
    }

    private <T extends IAnimatable> PlayState attackPredicate(AnimationEvent<T> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if (this.getAttackType() == AttackType.MELEE) {
            builder.addAnimation("melee", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        } else if (this.getAttackType() == AttackType.RANGED) {
            builder.addAnimation("ranged_attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        } else if (this.getAttackType() == AttackType.KILLING) {
            builder.addAnimation("killing_blow", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <T extends IAnimatable> PlayState predicate(AnimationEvent<T> event) {
        AnimationBuilder builder = new AnimationBuilder();
        float limbSwingAmount = Math.abs(event.getLimbSwingAmount());
        if(limbSwingAmount > 0.01F) {
            builder.addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP);
        }else{
            builder.addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
        }
        event.getController().setAnimation(builder);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
        return ModSoundEvents.ENTITY_WREATHED_HIND_AMBIENT;
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 300;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_WREATHED_HIND_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_WREATHED_HIND_DEATH;
    }

    @Nullable
    @Override
    public UUID getPledgedPlayerUUID() {
        return pledgedPlayer;
    }

    public void setPledgedPlayer(PlayerEntity player) {
        this.pledgedPlayer = player.getUuid();
        setIsPledged(true);
    }

    @Override
    public void removePledge() {
        this.pledgedPlayer = null;
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
