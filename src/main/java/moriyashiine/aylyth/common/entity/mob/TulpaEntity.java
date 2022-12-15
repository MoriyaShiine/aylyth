package moriyashiine.aylyth.common.entity.mob;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.ai.brain.TulpaBrain;
import moriyashiine.aylyth.common.screenhandler.TulpaScreenHandler;
import moriyashiine.aylyth.mixin.MobEntityAccessor;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.UserCache;
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

public class TulpaEntity extends HostileEntity implements TameableHostileEntity, IAnimatable, InventoryOwner, InventoryChangedListener, CrossbowUser {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final TrackedData<Byte> TAMEABLE = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final TrackedData<Integer> ACTION_STATE = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Optional<UUID>> SKIN_UUID = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public static final TrackedData<Boolean> TRANSFORMING = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final SimpleInventory inventory = new SimpleInventory(18);
    public int transformTime = 22;
    @Nullable
    public PlayerEntity interactTarget;

    public TulpaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.inventory.addListener(this);
        this.setPersistent();
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
        if(dataTracker.get(TRANSFORMING)){
            transformTime--;
            if(transformTime < 0){
            dataTracker.set(TRANSFORMING, false);
            }
        }
        super.mobTick();
    }



    @Override
    protected void loot(ItemEntity item) {
        this.triggerItemPickedUpByEntityCriteria(item);
        TulpaBrain.loot(this, item);
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
        this.dataTracker.startTracking(SKIN_UUID, Optional.empty());
        this.dataTracker.startTracking(TAMEABLE, (byte) 0);
        this.dataTracker.startTracking(TRANSFORMING, false);
    }


    @Override
    public void equipStack(EquipmentSlot slotIn, ItemStack stack) {
        super.equipStack(slotIn, stack);
        switch (slotIn) {
            case CHEST -> {
                if (this.inventory.getStack(1).isEmpty())
                    this.inventory.setStack(1, ((MobEntityAccessor) this).armorItems().get(slotIn.getEntitySlotId()));
            }
            case FEET -> {
                if (this.inventory.getStack(3).isEmpty())
                    this.inventory.setStack(3, ((MobEntityAccessor) this).armorItems().get(slotIn.getEntitySlotId()));
            }
            case HEAD -> {
                if (this.inventory.getStack(0).isEmpty())
                    this.inventory.setStack(0, ((MobEntityAccessor) this).armorItems().get(slotIn.getEntitySlotId()));
            }
            case LEGS -> {
                if (this.inventory.getStack(2).isEmpty())
                    this.inventory.setStack(2, ((MobEntityAccessor) this).armorItems().get(slotIn.getEntitySlotId()));
            }
            case MAINHAND -> {
                if (this.inventory.getStack(5).isEmpty())
                    this.inventory.setStack(5, ((MobEntityAccessor) this).armorItems().get(slotIn.getEntitySlotId()));
            }
            case OFFHAND -> {
                if (this.inventory.getStack(4).isEmpty())
                    this.inventory.setStack(4, ((MobEntityAccessor) this).armorItems().get(slotIn.getEntitySlotId()));
            }
        }
    }

    public int getActionState() {
        return this.dataTracker.get(ACTION_STATE);
    }

    public void setActionState(int i) {
        this.dataTracker.set(ACTION_STATE, i);
    }


    public void setInteractTarget(@Nullable PlayerEntity interactTarget) {
        boolean bl = this.getInteractTarget() != null && interactTarget == null;
        this.interactTarget = interactTarget;
        if (bl) {
            this.setInteractTarget(null);
        }
    }

    @Nullable
    public PlayerEntity getInteractTarget() {
        return this.interactTarget;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(true ){//TODO //getOwnerUuid() == player.getUuid()){
            ItemStack itemStack = player.getMainHandStack();
            if(itemStack.isOf(Items.PAPER) && itemStack.hasCustomName()){
                if(player.getServer() != null){
                    UserCache userCache = player.getServer().getUserCache();
                    Optional<GameProfile> cacheByName = userCache.findByName(itemStack.getName().getString());
                    if(cacheByName.isPresent()){
                        UUID uuid = cacheByName.get().getId();
                        setSkinUuid(uuid);
                        this.setCustomName(itemStack.getName());
                        this.dataTracker.set(TRANSFORMING, true);
                    }
                }
            }else if(!player.isSneaking()){
                this.openGui(player);
            } else if(player.getStackInHand(hand).isEmpty() && player.getUuid().equals(this.getOwnerUuid())) {
                this.cycleActionState(player);
            }
        }
        return super.interactMob(player, hand);
    }

    public void openGui(PlayerEntity player) {
        if (player.world != null && !this.world.isClient()) {
            setInteractTarget(player);
            player.openHandledScreen(new TulpaScreenHandlerFactory());
        }
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
        NbtList listnbt = new NbtList();
        for (int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemstack = this.inventory.getStack(i);
            NbtCompound compoundnbt = new NbtCompound();
            compoundnbt.putByte("Slot", (byte) i);
            itemstack.writeNbt(compoundnbt);
            listnbt.add(compoundnbt);

        }
        nbt.put("Inventory", listnbt);
        nbt.putInt("TransformTime", transformTime);
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
        NbtList listnbt = nbt.getList("Inventory", 10);
        for (int i = 0; i < listnbt.size(); ++i) {
            NbtCompound compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            this.inventory.setStack(j, ItemStack.fromNbt(compoundnbt));
        }

        if (nbt.contains("ArmorItems", 9)) {
            NbtList armorItems = nbt.getList("ArmorItems", 10);
            for (int i = 0; i < ((MobEntityAccessor)this).armorItems().size(); ++i) {
                int index = slotToInventoryIndex(MobEntity.getPreferredEquipmentSlot(ItemStack.fromNbt(armorItems.getCompound(i))));
                this.inventory.setStack(index, ItemStack.fromNbt(armorItems.getCompound(i)));
            }
        }
        if (nbt.contains("HandItems", 9)) {
            NbtList handItems = nbt.getList("HandItems", 10);
            for (int i = 0; i < ((MobEntityAccessor)this).handItems().size(); ++i) {
                int handSlot = i == 0 ? 5 : 4;
                this.inventory.setStack(handSlot, ItemStack.fromNbt(handItems.getCompound(i)));
            }
        }
        if(nbt.contains("TransformTime")){
            this.transformTime = nbt.getInt("TransformTime");
        }
    }

    public static int slotToInventoryIndex(EquipmentSlot slot) {
        switch (slot) {
            case CHEST:
                return 1;
            case FEET:
                return 3;
            case HEAD:
                return 0;
            case LEGS:
                return 2;
            default:
                break;
        }
        return 0;
    }

    @Override
    public UUID getOwnerUuid() {
        return this.dataTracker.get(OWNER_UUID).orElse(null);
    }

    @Override
    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }


    public UUID getSkinUuid() {
        return this.dataTracker.get(SKIN_UUID).orElse(null);
    }


    public void setSkinUuid(@Nullable UUID uuid) {
        this.dataTracker.set(SKIN_UUID, Optional.ofNullable(uuid));
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
        animationData.addAnimationController(new AnimationController<>(this, "moveController", 5, this::movementPredicate));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 5, this::attackPredicate));
    }
    private <E extends IAnimatable> PlayState movementPredicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if(this.dataTracker.get(TRANSFORMING)){
            builder.addAnimation("tulpa_metamorphosis", false);
        }else if(event.isMoving()){
            builder.addAnimation("tulpa_walking", true);
        }else{
            builder.addAnimation("tulpa_idle", true);
        }
        if(!builder.getRawAnimationList().isEmpty()) {
            event.getController().setAnimation(builder);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
            builder.addAnimation("tulpa_death", false);
        }else if(this.isAttacking()){
            builder.addAnimation("tulpa_attacking_right", true);
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

    @Override
    public SimpleInventory getInventory() {
        return inventory;
    }

    protected ItemStack addItem(ItemStack stack) {
        return this.inventory.addStack(stack);
    }

    protected boolean canInsertIntoInventory(ItemStack stack) {
        return this.inventory.canInsert(stack);
    }

    @Override
    public void onInventoryChanged(Inventory sender) {

    }

    //**CROSSBOW USER START
    @Override
    public void setCharging(boolean charging) {

    }

    @Override
    public void shoot(LivingEntity target, ItemStack crossbow, ProjectileEntity projectile, float multiShotSpray) {
        this.shoot(this, target, projectile, multiShotSpray, 1.6F);
    }

    @Override
    public void postShoot() {

    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {

    }
    //**CROSSBOW USER END

    private class TulpaScreenHandlerFactory implements ExtendedScreenHandlerFactory {
        private TulpaEntity tulpaEntity() {
            return TulpaEntity.this;
        }


        @Override
        public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
            buf.writeVarInt(this.tulpaEntity().getId());
        }

        @Nullable
        @Override
        public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
            var guardInv = this.tulpaEntity().inventory;
            return new TulpaScreenHandler(syncId, inv, guardInv, this.tulpaEntity());
        }

        @Override
        public Text getDisplayName() {
            return this.tulpaEntity().getDisplayName();
        }
    }

    public static class TulpaPlayerEntity extends PathAwareEntity {
        private static final TrackedData<Optional<UUID>> SKIN_UUID = DataTracker.registerData(TulpaPlayerEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
        public TulpaPlayerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
            super(entityType, world);
        }

        protected void initDataTracker() {
            super.initDataTracker();
            this.dataTracker.startTracking(SKIN_UUID, Optional.empty());
        }

        @Nullable
        public UUID getSkinUuid() {
            return this.dataTracker.get(SKIN_UUID).orElse(null);
        }

        public void setSkinUuid(@Nullable UUID uuid) {
            this.dataTracker.set(SKIN_UUID, Optional.ofNullable(uuid));
        }


        @Override
        public void writeCustomDataToNbt(NbtCompound nbt) {
            super.writeCustomDataToNbt(nbt);
            if (this.getSkinUuid() != null) {
                nbt.putUuid("SkinUUID", this.getSkinUuid());
            }
        }

        @Override
        public void readCustomDataFromNbt(NbtCompound nbt) {
            super.readCustomDataFromNbt(nbt);
            UUID ownerUUID;
            if (nbt.containsUuid("SkinUUID")) {
                ownerUUID = nbt.getUuid("SkinUUID");
            } else {
                String string = nbt.getString("SkinUUID");
                ownerUUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
            }
            if (ownerUUID != null) {
                this.setSkinUuid(ownerUUID);
            }
        }
    }
}
