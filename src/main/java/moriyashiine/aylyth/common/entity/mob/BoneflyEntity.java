package moriyashiine.aylyth.common.entity.mob;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import moriyashiine.aylyth.api.interfaces.ProlongedDeath;
import moriyashiine.aylyth.common.registry.ModComponents;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.mixin.EntityAccessor;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BoneflyEntity extends HostileEntity implements GeoEntity, TameableHostileEntity, ProlongedDeath {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    protected static final TrackedData<Boolean> DORMANT = DataTracker.registerData(BoneflyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Integer> ACTION_STATE = DataTracker.registerData(BoneflyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(BoneflyEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public int stabTicks = 0;
    public BoneflyEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setStepHeight(1.5f);
        this.setPersistent();
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    public static DefaultAttributeContainer.Builder createBoneflyAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0f).add(EntityAttributes.GENERIC_ARMOR, 24f);
    }
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DORMANT, false);
        this.dataTracker.startTracking(ACTION_STATE, 0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.of(UUID.fromString("1ece513b-8d36-4f04-9be2-f341aa8c9ee2")));
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
            try {
                this.setOwnerUuid(ownerUUID);
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }
        this.setDormant(nbt.getBoolean("Dormant"));
        stabTicks = nbt.getInt("stabTicks");
    }

    public boolean isDormant() {
        return getDataTracker().get(DORMANT);
    }

    @Override
    protected void addFlapEffects() {
        playSound(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, 0.5f, 1);
    }

    @Override
    protected boolean isFlappingWings() {
        return this.isInAir();
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

    @Override
    public void travel(Vec3d travelVector) {
        boolean flying = this.isInAir();
        float speed = (float) this.getAttributeValue(flying ? EntityAttributes.GENERIC_FLYING_SPEED : EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (!this.hasPassengers() && !this.canBeControlledByRider()) {
            super.travel(travelVector);
            return;
        }
        Entity passenger = this.getFirstPassenger();
        if (passenger instanceof LivingEntity livingEntityPassenger) {
            this.headYaw = (float) this.serverYaw;
            this.serverHeadYaw = this.headYaw;
            this.serverYaw = this.serverYaw - livingEntityPassenger.sidewaysSpeed * 2f;
            this.serverPitch = livingEntityPassenger.getPitch() * 0.5F;
            boolean isPlayerUpwardsMoving = ModComponents.RIDER_COMPONENT.get(livingEntityPassenger).isPressingUp();
            boolean isPlayerDownwardsMoving = ModComponents.RIDER_COMPONENT.get(livingEntityPassenger).isPressingDown();
            double getFlightDelta = isPlayerUpwardsMoving && isPlayerDownwardsMoving ? 0 : isPlayerUpwardsMoving ? 0.8 : isPlayerDownwardsMoving ? -0.6 : 0;
            this.setPitch((float) this.serverPitch);
            this.setYaw((float) this.serverYaw);
            this.setRotation(this.getYaw(), this.getPitch());
            this.bodyYaw = (float) this.serverYaw;
            if(!flying) {
                setActionState(0);
            }
            if(this.hasPassengers() && isPlayerDownwardsMoving && isPlayerUpwardsMoving && this.isInAir()) {
                if(this.getActionState() == 0 || this.getActionState() == 2) {
                    this.setActionState(1);
                } else {
                    this.setActionState(0);
                    if(this.getPassengerList().size() > 1) {
                        this.getPassengerList().get(1).dismountVehicle();
                    }
                }
            }

            if (!flying && isPlayerUpwardsMoving) this.jump();

            if (this.getFirstPassenger() != null) {
                travelVector = new Vec3d(0, getFlightDelta, livingEntityPassenger.forwardSpeed * (flying ? 1 : 0.4f));
                this.setMovementSpeed(speed);
                this.stepBobbingAmount = 0;
            } else if (passenger instanceof PlayerEntity) {
                this.updateLimbs(false);
                this.setVelocity(Vec3d.ZERO);
                return;
            }
        }
        if (flying) {
            this.applyMovementInput(travelVector, speed);
            this.move(MovementType.SELF, getVelocity());
            this.setVelocity(getVelocity().multiply(0.91f));
            this.updateLimbs(false);
            this.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
        }
        else {
            super.travel(travelVector);
            this.updateLeash();
        }
    }
    @Override
    public void tick() {
        super.tick();
        if (age % 20 == 0 && getHealth() < getMaxHealth() && isDormant()) {
            heal(2);
        }
        if(isDormant()) {
            setVelocity(0, getVelocity().y, 0);
            setPitch(0);
        }
        if(this.getActionState() == 1) {
            stabTicks++;
            if(stabTicks >= 10) {
                this.setActionState(2);
                if(!this.getWorld().isClient() && !this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().offset(0, -2, 0).expand(1), entity -> entity != this).isEmpty() && this.getPassengerList().size() <= 1) {
                    LivingEntity livingEntity = this.getWorld().getClosestEntity(this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().offset(0, -2, 0).expand(1), entity -> entity != this), TargetPredicate.createAttackable(), this, this.getX(), this.getY(), this.getZ());
                    if(livingEntity != null) {
                        if(!(livingEntity instanceof PlayerEntity player)) {
                            this.tryAttack(livingEntity);
                            livingEntity.startRiding(this, true);
                        } else {
                            this.tryAttack(livingEntity);
                            playerStartRiding(this, player);
                        }
                    }
                }
                stabTicks = 0;
            }
        }
    }
    private void playerStartRiding(Entity entity, PlayerEntity player) {
        if (entity == player.getVehicle()) {
            return;
        }
        Entity entity2 = entity;
        while (entity2.getVehicle() != null) {
            if (entity2.getVehicle() == this) {
                return;
            }
            entity2 = entity2.getVehicle();
        }
        if (this.hasVehicle()) {
            player.stopRiding();
        }
        this.setPose(EntityPose.STANDING);
        player.vehicle = entity;
        boneflyAddPassenger(player, player.getVehicle());
        ((EntityAccessor) entity).invokeStreamIntoPassengers().filter(passenger -> passenger instanceof ServerPlayerEntity).forEach(playr -> Criteria.STARTED_RIDING.trigger((ServerPlayerEntity)playr));
    }
    protected void boneflyAddPassenger(Entity passenger, Entity adder) {
        if (passenger.getVehicle() != this) {
            throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
        } else {
            if (adder.passengerList.isEmpty()) {
                adder.passengerList = ImmutableList.of(passenger);
            } else {
                List<Entity> list = Lists.newArrayList(adder.passengerList);
                list.add(passenger);

                adder.passengerList = ImmutableList.copyOf(list);
            }

        }
    }
    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!getWorld().isClient()) {
            if (source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity player && player.isHolding(ModItems.YMPE_EFFIGY_ITEM)) {
                this.setOwner(player);
            }
        }
        return super.damage(source, amount);
    }
    public boolean isInAir() {
        return this.isHighEnough((int) getStepHeight() + 1);
    }

    public boolean isHighEnough(int altitude) {
        return this.getAltitude(altitude) >= altitude;
    }

    public double getAltitude(int limit) {
        BlockPos.Mutable mutable = this.getBlockPos().mutableCopy();

        // limit so we don't do dozens of iterations per tick
        for (int i = 0; i <= limit && mutable.getY() > this.getWorld().getDimension().minY() && !this.getWorld().getBlockState(mutable.move(Direction.DOWN)).blocksMovement(); i++);
        return this.getY() - mutable.getY() - 0.11;
    }
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if((stack.getItem().equals(Items.BONE_BLOCK) || stack.isIn(ItemTags.SOUL_FIRE_BASE_BLOCKS)) && this.getHealth() < this.getMaxHealth()) {
            stack.decrement(1);
            this.heal(1);
        }
        if (this.isOwner(player) && stack.isEmpty() && this.isTamed() && !this.hasPassengers()) {
            if(player.isSneaking()) {
                this.setDormant(!this.isDormant());
            } else {
                player.startRiding(this);
                this.navigation.stop();
            }
        }

        return super.interactMob(player, hand);
    }

    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
    }

    @Override
    public void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        if (!this.hasPassenger(passenger)) {
            return;
        }
        if(getFirstPassenger() == passenger) {
            float f = 0.5f;
            float g = (float) ((this.isRemoved() ? (double) 0.01f : this.getMountedHeightOffset()) + passenger.getHeightOffset());
            Vec3d vec3d = new Vec3d(f, 0.0, 0.0).rotateY(-this.getYaw() * ((float) Math.PI / 180) - 1.5707964f);
            passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
            passenger.setYaw(passenger.getYaw());
            passenger.setHeadYaw(passenger.getHeadYaw());
        } else {
            passenger.setPosition(this.getX(), this.getY() - 0.5f, this.getZ());
            passenger.setYaw(this.getYaw());
            passenger.setHeadYaw(this.getHeadYaw());
        }
    }

    @Override
    public double getMountedHeightOffset() {
        return 2.3;
    }



    public boolean canBeControlledByRider() {
        return this.getFirstPassenger() instanceof LivingEntity;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public boolean isUndead() {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar animationData) {
        animationData.add(new AnimationController<>(this, "aeroPredicate", 5, this::aeroPredicate));
        animationData.add(new AnimationController<>(this, "hurtPredicate", 1, this::hurtPredicate));
        animationData.add(new AnimationController<>(this, "grabPredicate", 5, this::grabPredicate));
    }
    private <E extends GeoAnimatable> PlayState aeroPredicate(AnimationState<E> event) {
        var animationBuilder = RawAnimation.begin();
        if(this.isDormant()){
            animationBuilder.thenLoop("resting");
        }else if (this.isInAir()) {
            if(event.isMoving()){
                animationBuilder.thenLoop("flight");
            }else{
                animationBuilder.thenLoop("flight_idle");
            }
        } else {
            if(event.isMoving()) {
                animationBuilder.thenLoop("walking");
            } else {
                animationBuilder.thenLoop("idle");
            }
        }

        if(!animationBuilder.getAnimationStages().isEmpty()) {
            event.getController().setAnimation(animationBuilder);
        }
        return PlayState.CONTINUE;
    }
    private <E extends GeoAnimatable> PlayState grabPredicate(AnimationState<E> event) {
        var animationBuilder = RawAnimation.begin();
        if(this.isInAir()){
            if (this.getActionState() == 2) {//TODO implement grab modes
                animationBuilder.thenLoop("stabIdle");
            } else if(this.getActionState() == 1) {
                animationBuilder.then("stab", Animation.LoopType.PLAY_ONCE);
            } else {
                return PlayState.STOP;
            }
        }else {
            return PlayState.STOP;
        }
        if(!animationBuilder.getAnimationStages().isEmpty()) {
            event.getController().setAnimation(animationBuilder);
        }
        return PlayState.CONTINUE;
    }
    private <E extends GeoAnimatable> PlayState hurtPredicate(AnimationState<E> event) {
        var animationBuilder = RawAnimation.begin();
        if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
            animationBuilder.then("death", Animation.LoopType.PLAY_ONCE);
        } else if(this.hurtTime > 0 || this.deathTime > 0) {
            animationBuilder.then("hurt", Animation.LoopType.PLAY_ONCE);
        } else {
            return PlayState.STOP;
        }
        if(!animationBuilder.getAnimationStages().isEmpty()) {
            event.getController().setAnimation(animationBuilder);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public UUID getOwnerUuid() {
        return  this.dataTracker.get(OWNER_UUID).orElse(null);
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
        return true;
    }

    @Override
    public void setTamed(boolean tamed) {

    }

    public static BoneflyEntity create(World world, BlockPos pos, float yaw, float pitch, @Nullable PlayerEntity owner) {
        BoneflyEntity bonefly = ModEntityTypes.BONEFLY.create(world);
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