package moriyashiine.aylyth.common.entity.mob;

import moriyashiine.aylyth.api.interfaces.ProlongedDeath;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

public class SoulmouldEntity extends HostileEntity implements TameableHostileEntity, GeoEntity, ProlongedDeath {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    protected static final TrackedData<Boolean> DORMANT = DataTracker.registerData(SoulmouldEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Optional<BlockPos>> DORMANT_POS = DataTracker.registerData(SoulmouldEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
    public static final TrackedData<Integer> ATTACK_STATE = DataTracker.registerData(SoulmouldEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> ACTION_STATE = DataTracker.registerData(SoulmouldEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Direction> DORMANT_DIR = DataTracker.registerData(SoulmouldEntity.class, TrackedDataHandlerRegistry.FACING);
    private static final TrackedData<Byte> TAMEABLE = DataTracker.registerData(SoulmouldEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(SoulmouldEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public int activationTicks = 0;
    public int dashSlashTicks = 0;
    public SoulmouldEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1.6f);
        this.setPathfindingPenalty(PathNodeType.LAVA, 0);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0);
    }

    public static DefaultAttributeContainer.Builder createSoulmouldAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 160)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0f)
                .add(EntityAttributes.GENERIC_ARMOR, 24f)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 6f);
    }
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(1, new SoulmouldAttackLogicGoal(this));
        this.goalSelector.add(0, new SoulmouldDashSlashGoal(this));
        this.targetSelector.add(1, new TamedTrackAttackerGoal(this));
        this.targetSelector.add(2, new TamedAttackWithOwnerGoal<>(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, LivingEntity.class, 10, true, false, livingEntity -> !livingEntity.equals(this.getOwner()) && !(livingEntity instanceof TameableEntity tamed && tamed.getOwner() != null && tamed.getOwner().equals(this.getOwner())) && !(livingEntity instanceof ArmorStandEntity) && !(livingEntity instanceof SoulmouldEntity mould && mould.isOwner(this.getOwner())) && this.getActionState() == 2 && !(livingEntity instanceof BatEntity) && !(livingEntity instanceof PlayerEntity player && player.getUuid().equals(UUID.fromString("1ece513b-8d36-4f04-9be2-f341aa8c9ee2")))));
    }
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACK_STATE, 0);
        this.dataTracker.startTracking(ACTION_STATE, 0);
        this.dataTracker.startTracking(DORMANT_POS, Optional.empty());
        this.dataTracker.startTracking(DORMANT_DIR, this.getHorizontalFacing());
        this.dataTracker.startTracking(DORMANT, true);
        this.dataTracker.startTracking(TAMEABLE, (byte) 0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.of(UUID.fromString("1ece513b-8d36-4f04-9be2-f341aa8c9ee2")));
    }

    @Override
    public int getDeathAnimationTime(){
        return 20 * 4;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_SOULMOULD_HURT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_SOULMOULD_DEATH.value();
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!getWorld().isClient()) {
            if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                amount *= 0.5;
            }
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
        if(getDormantPos().isPresent()) {
            nbt.put("DormantPos", NbtHelper.fromBlockPos(getDormantPos().get()));
        }
        nbt.putInt("DormantDir", getDormantDir().getId());
        nbt.putInt("ActionState", getActionState());
        nbt.putInt("AttackState", getActionState());


        nbt.putInt("activationTicks", activationTicks);
        nbt.putInt("dashSlashTicks", dashSlashTicks);
        nbt.putBoolean("Dormant", this.isDormant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        UUID ownerUUID;
        if (nbt.containsUuid("Owner")) {
            ownerUUID = nbt.getUuid("Owner");
        } else {
            String string = nbt.getString("Owner");
            ownerUUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (ownerUUID != null) {
            try {
                this.setOwnerUuid(ownerUUID);
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }
        if(nbt.contains("DormantPos")) {
            setDormantPos(NbtHelper.toBlockPos(nbt.getCompound("DormantPos")));
        }
        this.setActionState(nbt.getInt("ActionState"));
        this.setAttackState(nbt.getInt("AttackState"));
        this.setDormantDir(Direction.byId(nbt.getInt("DormantDir")));
        activationTicks = nbt.getInt("activationTicks");
        dashSlashTicks = nbt.getInt("dashSlashTicks");
        this.setDormant(nbt.getBoolean("Dormant"));
    }

    @Override
    public UUID getOwnerUuid() {
        return this.dataTracker.get(OWNER_UUID).orElse(null);
    }

    @Override
    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Override
    public void setOwner(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
    }

    public int getAttackState() {
        return this.dataTracker.get(ATTACK_STATE);
    }

    public void setAttackState(int state) {
        this.dataTracker.set(ATTACK_STATE, state);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        try {
            UUID uUID = this.getOwnerUuid();
            return uUID == null ? null : this.getWorld().getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Override
    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    @Override
    public boolean isTamed() {
        return (this.dataTracker.get(TAMEABLE) & 4) != 0;
    }

    @Override
    public void setTamed(boolean tamed) {
        byte b = this.dataTracker.get(TAMEABLE);
        if (tamed) {
            this.dataTracker.set(TAMEABLE, (byte) (b | 4));
        } else {
            this.dataTracker.set(TAMEABLE, (byte) (b & -5));
        }

        this.onTamedChanged();
    }

    public void reset() {
        this.setTarget(null);
        this.navigation.stop();
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).isEmpty() && player.getUuid().equals(this.getOwnerUuid())) {
            if (player.isSneaking()) {
                this.cycleActionState(player);
            }
        }
        return super.interactMob(player, hand);
    }

    private void cycleActionState(PlayerEntity player) {
        if(getActionState() == 0) {
            setActionState(2);
            player.sendMessage(Text.literal("amogus").setStyle(Style.EMPTY.withColor(Formatting.DARK_RED).withObfuscated(true).withFont(new Identifier("minecraft", "default"))), true);
        } else if(getActionState() == 2) {
            setActionState(1);
            player.sendMessage(Text.translatable("info.aylyth.mould_activate", getWorld().getRegistryKey().getValue().getPath()).setStyle(Style.EMPTY.withColor(Formatting.AQUA)), true);
        } else if(getActionState() == 1) {
            setActionState(0);
            player.sendMessage(Text.translatable("info.aylyth.mould_deactivate", getWorld().getRegistryKey().getValue().getPath()).setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)), true);
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        setDormantPos(getBlockPos());
        this.initEquipment(random, difficulty);
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.YMPE_GLAIVE));
        this.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 0.0F;
    }

    public int getActionState() {
        return this.dataTracker.get(ACTION_STATE);
    }
    public void setActionState(int i) {
        this.dataTracker.set(ACTION_STATE, i);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getAttackState() == 2 && this.age % 2 == 0) {
            dashSlashTicks++;
        }
        if(dashSlashTicks >= 17) {
            setAttackState(0);
            dashSlashTicks = 0;
        }

        if (age % 5 == 0 && getHealth() < getMaxHealth() && isDormant()) {
            heal(2);
        }
        if (getTarget() != null && (!getTarget().isAlive() || getTarget().getHealth() <= 0)) setTarget(null);
        if(!getWorld().isClient) {
            if (!isDormant()) {
                if ((this.getTarget() == null || (this.getTarget() != null && this.getDormantPos().isPresent() && !this.getDormantPos().get().isWithinDistance(this.getTarget().getBlockPos(), 16))) && forwardSpeed == 0 && this.getNavigation().isIdle() && isAtDormantPos()) {
                    setDormant(true);
                    this.playSound(ModSoundEvents.ENTITY_SOULMOULD_AMBIENT.value(), 1f, 1f);
                    this.updatePositionAndAngles(getDormantPos().get().getX() + 0.5, getDormantPos().get().getY(), getDormantPos().get().getZ() + 0.5, this.getYaw(), this.getPitch());
                }
            } else if (getTarget() != null && squaredDistanceTo(getTarget()) < 100 && dataTracker.get(ACTION_STATE) != 0) {
                if(activationTicks == 0) {
                    this.playSound(ModSoundEvents.ENTITY_SOULMOULD_AMBIENT.value(), 1f, 1f);
                }
                activationTicks++;
                setAttackState(1);
                if (activationTicks > 60) {
                    this.setDormant(false);
                    this.setAttackState(0);
                    this.activationTicks = 0;
                }
            }
        }
        if(isDormant()) {
            setVelocity(0, getVelocity().y, 0);
            setYaw(getDormantDir().asRotation());
            setBodyYaw(getDormantDir().asRotation());
            setHeadYaw(getDormantDir().asRotation());
            setPitch(0);
        }
        if ((this.getTarget() == null || (this.getTarget() != null && this.getDormantPos().isPresent() && !this.getTarget().isAlive())) && getNavigation().isIdle() && !isAtDormantPos() && !isDormant()) updateDormantPos();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if(getWorld() instanceof ServerWorld server)
            server.getServer().getPlayerManager().sendToAround(null, this.getX(), this.getY(), this.getZ(), 32.0, server.getRegistryKey(), new PlaySoundS2CPacket(RegistryEntry.of(SoundEvents.ENTITY_IRON_GOLEM_STEP), this.getSoundCategory(), this.getX(), this.getY(), this.getZ(), 32.0f, 1.0f, 69L));
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar animationData) {
        animationData.add(new AnimationController<>(this, "controller", 5, this::predicate));
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        var animationBuilder = RawAnimation.begin();
        if (this.isDormant()) {
            if (getAttackState() == 1) {
                animationBuilder.then("soulmould_inactive_to_active", Animation.LoopType.HOLD_ON_LAST_FRAME);
            } else {
                animationBuilder.thenLoop("soulmould_inactive");
            }
        } else if(this.getAttackState() == 2) {
            animationBuilder.then("soulmould_dashing", Animation.LoopType.PLAY_ONCE);
        } else {
            if (!this.hasVehicle() && event.isMoving()) {
                animationBuilder.thenLoop("soulmould_walking");
            } else {
                animationBuilder.thenLoop("soulmould_idle");
            }
        }

        if(!animationBuilder.getAnimationStages().isEmpty()) {
            event.getController().setAnimation(animationBuilder);
        }
        return PlayState.CONTINUE;
    }

    public double getAngleBetweenEntities(Entity first, Entity second) {
        return Math.atan2(second.getZ() - first.getZ(), second.getX() - first.getX()) * (180 / Math.PI) + 90;
    }


    @Override
    public void pushAwayFrom(Entity entity) {

    }
    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public Optional<BlockPos> getDormantPos() {
        return getDataTracker().get(DORMANT_POS);
    }

    public void setDormantPos(BlockPos pos) {
        getDataTracker().set(DORMANT_POS, Optional.of(pos));
    }

    private boolean isAtDormantPos() {
        Optional<BlockPos> restPos = getDormantPos();
        return restPos.map(blockPos -> blockPos.isWithinDistance(this.getBlockPos(), 1.6f)).orElse(false);
    }

    private void updateDormantPos() {
        boolean reassign = true;
        if (getDormantPos().isPresent()) {
            BlockPos pos = getDormantPos().get();
            if (this.getNavigation().startMovingTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.5)) {
                reassign = false;
            }
            reassign = false;
        }
        if (reassign) {
            setDormantPos(getBlockPos());
        }
    }
    public Direction getDormantDir() {
        return getDataTracker().get(DORMANT_DIR);
    }
    public void setDormantDir(Direction dir) {
        getDataTracker().set(DORMANT_DIR, dir);
    }

    public boolean isDormant() {
        return getDataTracker().get(DORMANT);
    }

    public void setDormant(boolean rest) {
        getDataTracker().set(DORMANT, rest);
    }


    public static class SoulmouldAttackLogicGoal extends Goal {
        private final SoulmouldEntity mould;
        private int scrunkly;
        private double targetX;
        private double targetY;
        private double targetZ;

        public SoulmouldAttackLogicGoal(SoulmouldEntity entity) {
            this.mould = entity;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
        }
        @Override
        public boolean canStart() {
            LivingEntity target = this.mould.getTarget();
            return target != null && target.isAlive() && !this.mould.isDormant();
        }
        @Override
        public void start() {
            this.scrunkly = 0;
        }

        @Override
        public void stop() {
            this.mould.getNavigation().stop();
        }

        @Override
        public void tick() {
            LivingEntity target = this.mould.getTarget();
            if(target == null) return;
            double distance = this.mould.squaredDistanceTo(this.targetX, this.targetY, this.targetZ);
            if (--this.scrunkly <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || target.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0D) || this.mould.getNavigation().isIdle()) {
                this.targetX = target.getX();
                this.targetY = target.getY();
                this.targetZ = target.getZ();
                this.scrunkly = 4 + this.mould.getRandom().nextInt(6);
                if (distance > 32.0D * 32.0D) {
                    this.scrunkly += 10;
                } else if (distance > 16.0D * 16.0D) {
                    this.scrunkly += 5;
                }
                if (!this.mould.getNavigation().startMovingTo(target, 0.3D)) {
                    this.scrunkly += 15;
                }
            }
            distance = this.mould.squaredDistanceTo(this.targetX, this.targetY, this.targetZ);
            if (target.getY() - this.mould.getY() >= -1 && target.getY() - this.mould.getY() <= 3) {
                //distance < 3.5D * 3.5D &&
                if (Math.abs(MathHelper.wrapDegrees(this.mould.getAngleBetweenEntities(target, this.mould) - this.mould.getYaw())) < 35.0D) {
                    this.mould.setAttackState(2);
                    this.mould.dashSlashTicks = 0;
                }
            }
        }

    }
    public static class SoulmouldDashSlashGoal extends Goal {
        private final SoulmouldEntity mould;
        public SoulmouldDashSlashGoal(SoulmouldEntity entity) {
            this.mould = entity;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
        }

        @Override
        public boolean canStart() {
            return this.mould.getAttackState() == 2 && !this.mould.isDormant() && mould.getTarget() != null;
        }

        @Override
        public void tick() {
            int ticks = mould.dashSlashTicks;
            LivingEntity target = mould.getTarget();
            this.mould.lookAtEntity(this.mould.getTarget(), 80, 80);
            if(ticks == 9 || ticks == 12 || ticks == 15) {
                Vec3d vec3d = this.mould.getVelocity();
                Vec3d vec3d2 = null;
                if (target != null) {
                    vec3d2 = new Vec3d(target.getX() - mould.getX(), 0.0, target.getZ() - this.mould.getZ());
                    vec3d2 = vec3d2.normalize().multiply(1).add(vec3d);
                    this.mould.setVelocity(vec3d2.x, 0, vec3d2.z);
                }
            }
            if(ticks == 10 || ticks == 13 || ticks == 15) {
                mould.playSound(ModSoundEvents.ENTITY_SOULMOULD_ATTACK.value(), 1f, 1f);
                List<LivingEntity> entities = mould.getWorld().getEntitiesByClass(LivingEntity.class, mould.getBoundingBox().expand(4, 3, 4), livingEntity -> livingEntity != mould && livingEntity != mould.getOwner() && !(livingEntity instanceof SoulmouldEntity smould && smould.getOwner() == mould.getOwner()) && mould.distanceTo(livingEntity) <= 4 + livingEntity.getWidth() / 2 && livingEntity.getY() <= mould.getY() + 3);
                for(LivingEntity entity: entities) {
                    Vec3d vec = entity.getPos().subtract(mould.getPos()).normalize().negate();
                    entity.takeKnockback(1, vec.x, vec.z);
                    mould.tryAttack(entity);
                }
            }
        }
    }
    public static class TamedAttackWithOwnerGoal<T extends TameableHostileEntity> extends TrackTargetGoal {
        private final T tamed;
        private LivingEntity attacking;
        private int lastAttackTime;

        public TamedAttackWithOwnerGoal(T tamed) {
            super((MobEntity) tamed, false);
            this.tamed = tamed;
            this.setControls(EnumSet.of(Control.TARGET));
        }

        public boolean canStart() {
            if (this.tamed.isTamed()) {
                LivingEntity livingEntity = this.tamed.getOwner();
                if (livingEntity == null) {
                    return false;
                } else {
                    this.attacking = livingEntity.getAttacking();
                    int i = livingEntity.getLastAttackTime();
                    return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT) && this.tamed.canAttackWithOwner(this.attacking, livingEntity);
                }
            } else {
                return false;
            }
        }

        public void start() {
            this.mob.setTarget(this.attacking);
            LivingEntity livingEntity = this.tamed.getOwner();
            if (livingEntity != null) {
                this.lastAttackTime = livingEntity.getLastAttackTime();
            }

            super.start();
        }
    }
    public static class TamedTrackAttackerGoal extends TrackTargetGoal {
        private final TameableHostileEntity tameable;
        private LivingEntity attacker;
        private int lastAttackedTime;

        public TamedTrackAttackerGoal(TameableHostileEntity tameable) {
            super((MobEntity) tameable, false);
            this.tameable = tameable;
            this.setControls(EnumSet.of(Control.TARGET));
        }

        public boolean canStart() {
            if (this.tameable.isTamed()) {
                LivingEntity livingEntity = this.tameable.getOwner();
                if (livingEntity == null) {
                    return false;
                } else {
                    this.attacker = livingEntity.getAttacker();
                    if (this.attacker != null) {
                        int i = livingEntity.getLastAttackedTime();
                        return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.tameable.canAttackWithOwner(this.attacker, livingEntity);
                    }
                }
            }
            return false;
        }
        public void start() {
            this.mob.setTarget(this.attacker);
            LivingEntity livingEntity = this.tameable.getOwner();
            if (livingEntity != null) {
                this.lastAttackedTime = livingEntity.getLastAttackedTime();
            }

            super.start();
        }
    }
}
