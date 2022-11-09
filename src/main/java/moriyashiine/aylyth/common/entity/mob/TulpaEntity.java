package moriyashiine.aylyth.common.entity.mob;

import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.ai.brain.TulpaBrain;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class TulpaEntity extends HostileEntity implements TameableHostileEntity, IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Byte> TAMEABLE = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final TrackedData<Integer> ACTION_STATE = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public TulpaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createTulpaAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32)
                .add(EntityAttributes.GENERIC_ARMOR, 2f);
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("tulpaBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().pop();
        TulpaBrain.updateActivities(this);
        super.mobTick();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return TulpaBrain.create(this, dynamic);
    }

    @SuppressWarnings("All")
    @Override
    public Brain<TulpaEntity> getBrain() {
        return (Brain<TulpaEntity>) super.getBrain();
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ACTION_STATE, 0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    public int getActionState() {
        return this.dataTracker.get(ACTION_STATE);
    }

    public void setActionState(int i) {
        this.dataTracker.set(ACTION_STATE, i);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(player.getStackInHand(hand).isEmpty() && player.getUuid().equals(this.getOwnerUuid())) {
            this.cycleActionState(player);
        }
        return super.interactMob(player, hand);
    }

    private void cycleActionState(PlayerEntity player) {
        if(getActionState() == 0) {
            setActionState(2);
        } else if(getActionState() == 2) {
            setActionState(1);
        } else if(getActionState() == 1) {
            setActionState(0);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
        nbt.putInt("ActionState", getActionState());
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
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
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

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 5, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if(event.isMoving()){
            builder.addAnimation("tulpa_walking", true);
        }else{
            builder.addAnimation("tulpa_idle", true);
        }

        if(!builder.getRawAnimationList().isEmpty()) {
            event.getController().setAnimation(builder);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public boolean isEnemy(LivingEntity entity) {
        return true;//TODO
    }
}
