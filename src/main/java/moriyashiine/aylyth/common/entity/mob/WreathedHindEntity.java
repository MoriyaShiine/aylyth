package moriyashiine.aylyth.common.entity.mob;

import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.api.interfaces.Pledgeable;
import moriyashiine.aylyth.common.entity.ai.brain.WreathedHindBrain;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModDamageSources;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
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
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final TrackedData<Byte> ATTACK_TYPE = DataTracker.registerData(WreathedHindEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final byte NONE = 0;
    public static final byte MELEE_ATTACK = 1;
    public static final byte RANGE_ATTACK = 2;
    public static final byte KILLING_ATTACK = 3;
    private final Set<UUID> pledgedPlayerUUIDS = new HashSet<>();

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
        this.dataTracker.startTracking(ATTACK_TYPE, (byte) 0);
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("wreathedHindBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().pop();
        WreathedHindBrain.updateActivities(this);
        super.mobTick();
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return state.isIn(FluidTags.WATER);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        toNbtPledgeable(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        fromNbtPledgeable(nbt);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if((stack.getItem().equals(ModItems.NYSIAN_GRAPES))) {
            getPledgedPlayerUUIDs().add(player.getUuid());
            HindPledgeHolder.of(player).ifPresent(hindHolder -> hindHolder.setHindUuid(this.getUuid()));
            stack.decrement(1);
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if(getAttackType() == NONE){
            setAttackType(MELEE_ATTACK);
        }

        if(getAttackType() == MELEE_ATTACK){
            if(WreathedHindBrain.isPledgedPlayerLow(target, this)){
                setAttackType(KILLING_ATTACK);
            }else{
                return super.tryAttack(target);
            }
        }else if(getAttackType() == KILLING_ATTACK){
            if(target instanceof PlayerEntity player){
                return tryKillingAttack(player);
            }
        }
       return false;
    }

    public boolean tryKillingAttack(PlayerEntity target){
        float f = 6;
        boolean bl = target.damage(ModDamageSources.UNBLOCKABLE, f);
        if (bl) {
            this.disablePlayerShield(target, this.getMainHandStack(), target.isUsingItem() ? target.getActiveItem() : ItemStack.EMPTY);
            this.applyDamageEffects(this, target);
            this.onAttacking(target);
        }
        return bl;
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        placeStewnLeaves(world, getBlockPos());
    }

    public void placeStewnLeaves(World world, BlockPos blockPos){
        List<BlockPos> listPos = new ArrayList<>();
        int index = 0;
        for(int x = -2; x <= 2; x++){
            for(int z = -2; z <= 2; z++){
                for(int y = -2; y <= 2; y++){
                    if(!world.isClient && world.getBlockState(blockPos.add(x,y,z)).getMaterial().isReplaceable() && world.getBlockState(blockPos.add(x,y,z).down()).isIn(BlockTags.DIRT) ){
                        listPos.add(index, blockPos.add(x,y,z));
                        index++;
                    }
                }
            }
        }
        int random = world.getRandom().nextBetween(2, 4);
        for(int i = 0; i < random; i++){
            if(listPos.size() >= i){
                BlockPos placePos = listPos.get(world.getRandom().nextInt(listPos.size()));
                world.setBlockState(placePos, ModBlocks.OAK_STREWN_LEAVES.getDefaultState());
                playSound(SoundEvents.BLOCK_GRASS_PLACE, getSoundVolume(), getSoundPitch());
            }
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 5, this::predicate));
        animationData.addAnimationController(new AnimationController<>(this, "controller2", 1, this::attackPredicate));
    }

    private <T extends IAnimatable> PlayState attackPredicate(AnimationEvent<T> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if(this.getAttackType() == MELEE_ATTACK){
            builder.addAnimation("attack.melee", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        }else if(this.getAttackType() == RANGE_ATTACK){
            builder.addAnimation("attack.ranged", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        }else if(this.getAttackType() == KILLING_ATTACK){
            builder.addAnimation("killing.blow", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
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

    public byte getAttackType() {
        return dataTracker.get(ATTACK_TYPE);
    }

    public void setAttackType(byte id) {
        dataTracker.set(ATTACK_TYPE, id);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return WreathedHindBrain.create(this, dynamic);
    }

    @SuppressWarnings("All")
    @Override
    public Brain<WreathedHindEntity> getBrain() {
        return (Brain<WreathedHindEntity>) super.getBrain();
    }

    public static boolean canSpawn(EntityType<WreathedHindEntity> wreathedHindEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return true;//TODO
    }

    @Override
    public Collection<UUID> getPledgedPlayerUUIDs() {
        return pledgedPlayerUUIDS;
    }
}
