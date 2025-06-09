package moriyashiine.aylyth.common.entity.types.mob;

import com.mojang.serialization.Dynamic;
import io.netty.buffer.ByteBuf;
import moriyashiine.aylyth.api.interfaces.Pledgeable;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.advancement.AylythCriteria;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.entity.AylythTrackedDataHandlers;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import moriyashiine.aylyth.common.entity.ai.brains.WreathedHindBrain;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import moriyashiine.aylyth.common.world.AylythWorldAttachmentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.IntFunction;

public class WreathedHindEntity extends HostileEntity implements GeoEntity, Pledgeable {
    private static final RawAnimation KILLING_BLOW = RawAnimation.begin().thenPlay("attack.killing_blow");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final Identifier SNEAKY_MODIFIER = Aylyth.id("sneaky_modifier");
    private static final EntityAttributeModifier SNEAKY_SPEED_PENALTY = new EntityAttributeModifier(SNEAKY_MODIFIER, -0.15D, EntityAttributeModifier.Operation.ADD_VALUE);
    private EntityAttributeInstance modifiableattributeinstance = this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
    public static final TrackedData<AttackType> ATTACK_TYPE = DataTracker.registerData(WreathedHindEntity.class, AylythTrackedDataHandlers.WREATHED_ATTACK_TYPE);
    public static final TrackedData<Boolean> IS_PLEDGED = DataTracker.registerData(WreathedHindEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public WreathedHindEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 100)
                .add(EntityAttributes.ATTACK_DAMAGE, 13)
                .add(EntityAttributes.ARMOR, 3)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.225)
                .add(EntityAttributes.FOLLOW_RANGE, 32)
                .add(EntityAttributes.STEP_HEIGHT, 1.0)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, 4);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder
                .add(ATTACK_TYPE, AttackType.NONE)
                .add(IS_PLEDGED, false)
        );
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profilers.get().push("wreathedHindBrain");
        this.getBrain().tick((ServerWorld)this.getWorld(), this);
        Profilers.get().pop();
        WreathedHindBrain.updateActivities(this);
        super.mobTick(world);
        if (WreathedHindBrain.isPledgedPlayerLow(this.getTarget(), this)) {
            modifiableattributeinstance.removeModifier(SNEAKY_MODIFIER);
            modifiableattributeinstance.addTemporaryModifier(SNEAKY_SPEED_PENALTY);
        } else if (modifiableattributeinstance.hasModifier(SNEAKY_MODIFIER)) {
            modifiableattributeinstance.removeModifier(SNEAKY_MODIFIER);
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
            stack.decrementUnlessCreative(1, player);
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        getWorld().getAttachedOrCreate(AylythWorldAttachmentTypes.PLEDGE_STATE).removePledge(this);
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        boolean attack = false;
        if (getAttackType() == AttackType.MELEE) {
            attack = super.tryAttack(world, target);
        } else if (getAttackType() == AttackType.KILLING) {
            attack = tryKillingAttack(world, target);
        }

        if (attack) {
            UUID pledged = getPledgedPlayerUUID();
            if (pledged != null && target instanceof LivingEntity livingTarget && pledged.equals(target.getUuid())) {
                if (livingTarget.isDead()) {
                    this.getBrain().forget(MemoryModuleType.HURT_BY_ENTITY);
                    removePledge();
                }
            }
        }

        return false;
    }

    public boolean tryKillingAttack(ServerWorld world, Entity target) {
        boolean bl = target.damage(world, world.aylythDamageSources().killingBlow(this), 6);
        if (bl) {
            this.onAttacking(target);
        }
        return bl;
    }

    @Override
    public boolean disablesShield() {
        return getAttackType() == AttackType.KILLING;
    }

    @Override
    public boolean isInAttackRange(LivingEntity entity) {
        double r = this.getAttributeValue(EntityAttributes.ENTITY_INTERACTION_RANGE);
        return entity.getBoundingBox().squaredMagnitude(this.getEyePos()) < r * r;
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        boolean result =  super.damage(world, source, amount);
        if (result) {
            Entity attacker = source.getAttacker();
            if (attacker != null && attacker.getUuid().equals(getPledgedPlayerUUID())) {
                if (!getBrain().hasMemoryModule(AylythMemoryTypes.SECOND_CHANCE)) {
                    getBrain().remember(AylythMemoryTypes.SECOND_CHANCE, WreathedHindBrain.SecondChance.WARNING, 600);
                } else {
                    getBrain().remember(AylythMemoryTypes.SECOND_CHANCE, WreathedHindBrain.SecondChance.BETRAY, 120 * 20);
                }
            }
        }
        return result;
    }

    @Override
    protected void drop(ServerWorld world, DamageSource damageSource) {
        super.drop(world, damageSource);
        if (world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            placeStrewnLeaves(getWorld(), getBlockPos());
        }
    }

    public void placeStrewnLeaves(World world, BlockPos blockPos){
        List<BlockPos> possiblePositions = new ArrayList<>();
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = -2; y <= 2; y++) {
                    BlockPos offsetPos = blockPos.add(x,y,z);
                    if (!world.isClient && world.getBlockState(offsetPos).isReplaceable() && world.getBlockState(offsetPos.down()).isIn(BlockTags.DIRT) ) {
                        possiblePositions.add(offsetPos);
                    }
                }
            }
        }
        if (!possiblePositions.isEmpty()) {
            int random = this.random.nextBetween(2, 4);
            for (int i = 0; i < random; i++) {
                if (possiblePositions.size() >= i) {
                    BlockPos placePos = Util.getRandom(possiblePositions, this.random);
                    world.setBlockState(placePos, AylythBlocks.OAK_STREWN_LEAVES.getDefaultState());
                    playSound(AylythSoundEvents.BLOCK_STREWN_LEAVES_STEP.value(), getSoundVolume(), getSoundPitch());
                }
            }
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        // TODO: Huh?
        if (getPledgedPlayerUUID() != null) {

        }
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar animationData) {
        animationData.add(DefaultAnimations.genericWalkIdleController(this));
        animationData.add(new AnimationController<>(this, "Attack", 1, this::attackPredicate));
    }

    private <T extends WreathedHindEntity> PlayState attackPredicate(AnimationState<T> event) {
        var entity = event.getAnimatable();
        RawAnimation animation;
        switch (entity.getAttackType()) {
            case MELEE -> animation = DefaultAnimations.ATTACK_SWING;
            case RANGED -> animation = DefaultAnimations.ATTACK_CAST;
            case KILLING -> animation = KILLING_BLOW;
            default -> {
                return PlayState.STOP;
            }
        };
        return event.setAndContinue(animation);
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
            return getWorld().getAttachedOrCreate(AylythWorldAttachmentTypes.PLEDGE_STATE).getPledged(this);
        }
        return null;
    }

    public void setPledgedPlayer(PlayerEntity player) {
        if (!getWorld().isClient) {
            getWorld().getAttachedOrCreate(AylythWorldAttachmentTypes.PLEDGE_STATE).addPledge(player.getUuid(), this.getUuid());
        }
        setIsPledged(true);
    }

    @Override
    public void removePledge() {
        if (!getWorld().isClient) {
            getWorld().getAttachedOrCreate(AylythWorldAttachmentTypes.PLEDGE_STATE).removePledge(this);
        }
        setIsPledged(false);
    }

    public boolean isPledged() {
        return dataTracker.get(IS_PLEDGED);
    }

    public void setIsPledged(boolean isPledged) {
        dataTracker.set(IS_PLEDGED, isPledged);
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    public enum AttackType implements StringIdentifiable {
        NONE(0, "none"),
        MELEE(1, "melee"),
        RANGED(2, "ranged"),
        KILLING(3, "killing");

        public static final IntFunction<AttackType> ID_TO_VALUE_FUNCTION = ValueLists.createIdToValueFunction(
                AttackType::getIndex, values(), ValueLists.OutOfBoundsHandling.WRAP
        );
        public static final PacketCodec<ByteBuf, AttackType> PACKET_CODEC = PacketCodecs.indexed(ID_TO_VALUE_FUNCTION, AttackType::getIndex);
        private final int index;
        private final String name;

        AttackType(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return this.index;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
