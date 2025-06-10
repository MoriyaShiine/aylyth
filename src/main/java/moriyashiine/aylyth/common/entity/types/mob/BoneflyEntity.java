package moriyashiine.aylyth.common.entity.types.mob;

import moriyashiine.aylyth.api.interfaces.ProlongedDeath;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.attachments.AdditionalPlayerInput;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.mixin.LivingEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.UUID;

public class BoneflyEntity extends HostileEntity implements GeoEntity, TameableHostileEntity, ProlongedDeath {
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("walk");
    private static final RawAnimation FLIGHT = RawAnimation.begin().thenPlay("flight");
    private static final RawAnimation FLIGHT_IDLE = RawAnimation.begin().thenPlay("flight_idle");
    private static final RawAnimation FLIGHT_GRAB = RawAnimation.begin().thenPlay("flight_grab");
    private static final RawAnimation FLIGHT_HOLD = RawAnimation.begin().thenPlay("flight_hold");
    private static final RawAnimation REST = RawAnimation.begin().thenPlay("rest");
    private static final RawAnimation STAND_TO_REST = RawAnimation.begin().thenPlay("stand_to_rest");
    private static final RawAnimation REST_TO_STAND = RawAnimation.begin().thenPlay("rest_to_stand");
    private static final RawAnimation HURT = RawAnimation.begin().thenPlay("hurt");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("death");
    private static final RawAnimation TWITCH = RawAnimation.begin().thenPlay("twitch");
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    protected static final TrackedData<Boolean> DORMANT = DataTracker.registerData(BoneflyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Integer> ACTION_STATE = DataTracker.registerData(BoneflyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(BoneflyEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public int stabTicks = 0;
    protected boolean jumped = false;
    protected boolean jumping = false;
    protected boolean inAir = false;
    protected boolean flying = false;
    protected boolean grabbing = false;

    public BoneflyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent();
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    public static DefaultAttributeContainer.Builder createBoneflyAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 60)
                .add(EntityAttributes.ATTACK_DAMAGE, 8)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.FLYING_SPEED, 0.4)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 1.0f)
                .add(EntityAttributes.ARMOR, 24f)
                .add(EntityAttributes.STEP_HEIGHT, 1.5f);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(DORMANT, false);
        builder.add(ACTION_STATE, 0);
        builder.add(OWNER_UUID, Optional.of(UUID.fromString("1ece513b-8d36-4f04-9be2-f341aa8c9ee2")));
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(5, new BoneflyWanderAroundFarGoal(this, 0.7));

    }

    @Override
    public int getDeathAnimationTime(){
        return 20 * 4;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {}

    @Override
    public boolean isClimbing() {
        return false;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
        nbt.putInt("ActionState", getActionState());
        nbt.putBoolean("Dormant", this.isDormant());
        nbt.putInt("stabTicks", stabTicks);
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
        this.setActionState(nbt.getInt("ActionState"));

        if (ownerUUID != null) {
            this.setOwnerUuid(ownerUUID);
            this.setTamed(true);
        }
        this.setDormant(nbt.getBoolean("Dormant"));
        stabTicks = nbt.getInt("stabTicks");
    }

    @Override
    protected void addFlapEffects() {
        playSound(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, 0.5f, 1);
    }

    @Override
    protected boolean isFlappingWings() {
        return !this.isDead() && this.isInAir() && this.flying && age % 20 == 0;
    }

    public int getActionState() {
        return this.dataTracker.get(ACTION_STATE);
    }

    public void setActionState(int i) {
        this.dataTracker.set(ACTION_STATE, i);
    }

    public void setDormant(boolean rest) {
        getDataTracker().set(DORMANT, rest);
    }

    public boolean isDormant() {
        return getDataTracker().get(DORMANT);
    }

    @Override
    public void tick() {
        super.tick();
        if (age % 20 == 0 && getHealth() < getMaxHealth() && isDormant()) {
            heal(2);
        }
        if (isDormant()) {
            setVelocity(0, getVelocity().y, 0);
            setPitch(0);
        }
        if (this.getActionState() == 1) {
            stabTicks++;
            if (stabTicks >= 10) {
                this.setActionState(2);
                if (this.getWorld() instanceof ServerWorld serverWorld && !serverWorld.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().offset(0, -2, 0).expand(1), entity -> entity != this).isEmpty() && this.getPassengerList().size() <= 1) {
                    // This does grabbing, but it's super jank. Riding is probably not the best way to handle this.
                    LivingEntity livingEntity = serverWorld.getClosestEntity(serverWorld.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().offset(0, -2, 0).expand(1), entity -> entity != this), TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getZ());
                    if (livingEntity != null) {
                        this.tryAttack(serverWorld, livingEntity);
                        livingEntity.startRiding(this);
                    }
                }
                stabTicks = 0;
            }
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        return entity instanceof PlayerEntity player ? player : super.getControllingPassenger();
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        AdditionalPlayerInput moreInputs = controllingPlayer.getAttachedOrCreate(AylythEntityAttachmentTypes.ADDITIONAL_PLAYER_INPUT);
        boolean isAscending = moreInputs.ascending();
        boolean isDescending = moreInputs.descending();
        double verticalSpeed = 0;
        // jump activates when isAscending && !jumped
        // flying activates when inAir && isAscending && !jumped
        // jumped = true when onGround && jumping
        // jumped = false when onGround || !jumping
        if (isAscending) {
            if (isDescending) {
                this.grabbing = true;
            } else {
                this.jumping = true;
                verticalSpeed = 0.8;
            }
        } else if (isDescending) {
            verticalSpeed = -0.6;
        } else {
            this.jumped = false;
            this.jumping = false;
        }
        return new Vec3d(0, verticalSpeed, controllingPlayer.forwardSpeed);
    }

    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        this.headYaw = (float) this.serverYaw;
        this.serverHeadYaw = this.headYaw;
        this.serverYaw = this.serverYaw - controllingPlayer.sidewaysSpeed * 3f;
        this.serverPitch = controllingPlayer.getPitch() * 0.5F;
        this.setRotation((float) this.serverYaw, (float) this.serverPitch);
        this.bodyYaw = (float) this.serverYaw;

        if (this.isOnGround()) {
            this.inAir = false;
            this.flying = false;
            this.jumped = false;
            if (this.jumping) {
                this.jump();
                this.jumping = false;
                this.jumped = true;
            }
        } else if (this.jumping && !this.jumped) {
            this.flying = true;
            this.jumping = false;
        }

        if (!this.isInAir()) {
            setActionState(0);
        }

        if (this.hasPassengers() && this.flying) {
            if (this.getActionState() == 0 || this.getActionState() == 2) {
                this.setActionState(1);
            } else {
                this.setActionState(0);
                if(this.getPassengerList().size() > 1) {
                    this.getPassengerList().get(1).dismountVehicle();
                }
            }
        }
    }

    @Override
    public void travel(Vec3d travelVector) {
        if (this.flying && this.isLogicalSideForUpdatingMovement()) {
            Vec3d vel = ((LivingEntityAccessor)this).invokeApplyMovementInput(travelVector, 0.99f);
            this.setVelocity(vel.x * 0.91F, vel.y * 0.95F, vel.z * 0.91F);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void jump() {
        super.jump();
        this.inAir = true;
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return (float) this.getAttributeValue(this.flying ? EntityAttributes.FLYING_SPEED : EntityAttributes.MOVEMENT_SPEED) * 0.5f;
    }

    @Override
    protected float getOffGroundSpeed() {
        return this.flying ? (float) this.getAttributeValue(EntityAttributes.FLYING_SPEED) * 0.3f : super.getOffGroundSpeed();
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity player && player.isHolding(AylythItems.YMPE_EFFIGY)) {
            this.setOwner(player);
        }
        return super.damage(world, source, amount);
    }

    public boolean isInAir() {
        return inAir;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if((stack.getItem().equals(Items.BONE_BLOCK) || stack.isIn(ItemTags.SOUL_FIRE_BASE_BLOCKS)) && this.getHealth() < this.getMaxHealth()) {
            stack.decrementUnlessCreative(1, player);
            this.heal(1);
        }
        if (hand == Hand.MAIN_HAND) {
            if (this.isOwner(player) && stack.isEmpty() && this.isTamed() && !this.hasPassengers()) {
                if (player.isSneaking()) {
                    this.setDormant(!this.isDormant());
                } else {
                    this.setDormant(false);
                    player.startRiding(this);
                    this.navigation.stop();
                }
            }
        }

        return super.interactMob(player, hand);
    }

    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
    }

    @Override
    public void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        if (getFirstPassenger() == passenger) {
            Vec3d position = this.getPassengerRidingPos(passenger);
            passenger.setPosition(position.x, position.y, position.z);
            passenger.setYaw(passenger.getYaw());
            passenger.setHeadYaw(passenger.getHeadYaw());
        } else {
            passenger.setPosition(this.getX(), this.getY() - 0.5f, this.getZ());
            passenger.setYaw(this.getYaw());
            passenger.setHeadYaw(this.getHeadYaw());
        }
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar animationData) {
        animationData.add(new AnimationController<>(this, "Move", 5, this::moveHandler));
        animationData.add(new AnimationController<>(this, "Damage", 1, this::hurtHandler));
        animationData.add(new AnimationController<>(this, "Grab", 5, this::grabHandler));
        animationData.add(new AnimationController<>(this, "Effect", 5, this::effectHandler));
    }

    private <E extends BoneflyEntity> PlayState moveHandler(AnimationState<E> event) {
        var entity = event.getAnimatable();
        RawAnimation animation;
        if (entity.isDormant()) {
            animation = REST;
        } else if (entity.flying) {
            animation = event.isMoving() ? FLIGHT : FLIGHT_IDLE;
        } else {
            animation = event.isMoving() ? WALK : IDLE;
        }

        return event.setAndContinue(animation);
    }

    private <E extends BoneflyEntity> PlayState grabHandler(AnimationState<E> event) {
        return PlayState.STOP;
//        var entity = event.getAnimatable();
//        RawAnimation animation;
//        if (!entity.isInAir()) {
//            return PlayState.STOP;
//        }
//
//        switch (entity.getActionState()) {
//            case 1 -> animation = FLIGHT_GRAB;
//            case 2 -> animation = FLIGHT_HOLD;
//            default -> {
//                return PlayState.STOP;
//            }
//        }
//        return event.setAndContinue(animation);
    }

    private <E extends BoneflyEntity> PlayState hurtHandler(AnimationState<E> event) {
        var entity = event.getAnimatable();
        RawAnimation animation;
        if ((entity.dead || entity.getHealth() < 0.01 || entity.isDead())) {
            animation = DEATH;
        } else if (entity.hurtTime > 0 || entity.deathTime > 0) {
            animation = HURT;
        } else {
            return PlayState.STOP;
        }

        return event.setAndContinue(animation);
    }

    private <E extends BoneflyEntity> PlayState effectHandler(AnimationState<E> event) {
        return event.setAndContinue(TWITCH);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
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

    @Nullable
    @Override
    public LivingEntity getOwner() {
        UUID ownerId = this.getOwnerUuid();
        return ownerId == null ? null : this.getWorld().getPlayerByUuid(ownerId);
    }

    @Override
    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    @Override
    public boolean isTamed() {
        return true;
    }

    @Override
    public void setTamed(boolean tamed) {

    }

    public static BoneflyEntity create(World world, BlockPos pos, float yaw, float pitch, SpawnReason reason, @Nullable PlayerEntity owner) {
        BoneflyEntity bonefly = AylythEntityTypes.BONEFLY.create(world, reason);
        bonefly.refreshPositionAndAngles(pos, yaw, pitch);
        if (owner != null) {
            bonefly.setOwner(owner);
        }
        return bonefly;
    }

    public class BoneflyWanderAroundFarGoal extends WanderAroundGoal {
        public static final float CHANCE = 0.001F;
        protected final float probability;

        public BoneflyWanderAroundFarGoal(PathAwareEntity pathAwareEntity, double d) {
            this(pathAwareEntity, d, CHANCE);
        }

        public BoneflyWanderAroundFarGoal(PathAwareEntity mob, double speed, float probability) {
            super(mob, speed);
            this.probability = probability;
        }

        @Override
        public boolean canStart() {
            return !BoneflyEntity.this.isDormant() && !BoneflyEntity.this.hasPassengers() && super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            return !BoneflyEntity.this.isDormant() && !BoneflyEntity.this.hasPassengers() && super.shouldContinue();
        }

        @Nullable
        @Override
        protected Vec3d getWanderTarget() {
            if (this.mob.isInsideWaterOrBubbleColumn()) {
                Vec3d vec3d = FuzzyTargeting.find(this.mob, 15, 7);
                return vec3d == null ? super.getWanderTarget() : vec3d;
            } else {
                return this.mob.getRandom().nextFloat() >= this.probability ? FuzzyTargeting.find(this.mob, 10, 7) : super.getWanderTarget();
            }
        }
    }
}