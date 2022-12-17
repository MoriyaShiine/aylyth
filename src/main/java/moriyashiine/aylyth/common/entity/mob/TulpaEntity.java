package moriyashiine.aylyth.common.entity.mob;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.ai.brain.TulpaBrain;
import moriyashiine.aylyth.common.screenhandler.TulpaScreenHandler;
import moriyashiine.aylyth.mixin.MobEntityAccessor;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.item.TaglockItem;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
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

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class TulpaEntity extends HostileEntity implements TameableHostileEntity, IAnimatable, CrossbowUser, InventoryOwner, InventoryChangedListener {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final TrackedData<Byte> TAMEABLE = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final byte IDLE = 0;
    private static final byte FOLLOW = 1;
    private static final byte SICKO = 2;
    public static final TrackedData<Byte> ACTION_STATE = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Optional<UUID>> SKIN_UUID = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public static final TrackedData<Boolean> TRANSFORMING = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> USING_ITEM = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static final TrackedData<Boolean> IS_ATTACKING = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final SimpleInventory inventory = new SimpleInventory(14);
    public final SimpleInventory armorInventory = new SimpleInventory(4);
    public int transformTime = 22;
    public int shieldCoolDown;
    @Nullable
    public PlayerEntity interactTarget;

    public TulpaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent();
        this.inventory.addListener(this);
        this.armorInventory.addListener(this);
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
        if (this.shieldCoolDown > 0) {
            --this.shieldCoolDown;
        }
        super.mobTick();
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ACTION_STATE, (byte) 0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(SKIN_UUID, Optional.empty());
        this.dataTracker.startTracking(TAMEABLE, (byte) 0);
        this.dataTracker.startTracking(TRANSFORMING, false);
        this.dataTracker.startTracking(USING_ITEM, false);
        this.dataTracker.startTracking(IS_ATTACKING, false);
    }

    @Override
    public void equipStack(EquipmentSlot slotIn, ItemStack stack) {
        super.equipStack(slotIn, stack);
        switch (slotIn) {
            case HEAD -> equipFromInventory(slotIn, this.armorInventory, 0);
            case CHEST -> equipFromInventory(slotIn, this.armorInventory, 1);
            case LEGS -> equipFromInventory(slotIn, this.armorInventory, 2);
            case FEET -> equipFromInventory(slotIn, this.armorInventory, 3);
            case MAINHAND -> equipFromInventory(slotIn, this.inventory, 0);
            case OFFHAND -> equipFromInventory(slotIn, this.inventory, 1);
        }
    }

    public void equipFromInventory(EquipmentSlot slotIn, SimpleInventory inventory, int i){
        if (inventory.getStack(i).isEmpty()){
            inventory.setStack(i, ((MobEntityAccessor) this).armorItems().get(slotIn.getEntitySlotId()));
        }
    }

    public byte getActionState() {
        return dataTracker.get(ACTION_STATE);
    }

    private void setActionState(byte id) {
        dataTracker.set(ACTION_STATE, id);
    }



    public void setInteractTarget(@Nullable PlayerEntity interactTarget) {
        this.interactTarget = interactTarget;
        if (this.getInteractTarget() != null && interactTarget == null) {
            this.setInteractTarget(null);
        }
    }

    @Nullable
    public PlayerEntity getInteractTarget() {
        return this.interactTarget;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(getOwnerUuid() == player.getUuid() || Aylyth.isDebugMode()){
            ItemStack itemStack = player.getMainHandStack();
            if(FabricLoader.getInstance().isModLoaded("bewitchment")){
                if(itemStack.getItem() instanceof TaglockItem){
                    LivingEntity living = BewitchmentAPI.getTaglockOwner(world, itemStack);
                    if(living instanceof PlayerEntity playerEntity){
                        setSkinUuid(playerEntity.getUuid());
                        this.setCustomName(playerEntity.getName());
                        this.dataTracker.set(TRANSFORMING, true);
                        return ActionResult.CONSUME;
                    }
                }
            }else{
                if(itemStack.isOf(Items.PAPER) && itemStack.hasCustomName()){
                    if(player.getServer() != null){
                        UserCache userCache = player.getServer().getUserCache();
                        Optional<GameProfile> cacheByName = userCache.findByName(itemStack.getName().getString());
                        if(cacheByName.isPresent()){
                            UUID uuid = cacheByName.get().getId();
                            setSkinUuid(uuid);
                            this.setCustomName(itemStack.getName());
                            this.dataTracker.set(TRANSFORMING, true);
                            return ActionResult.CONSUME;
                        }
                    }
                }
            }
            if(!player.isSneaking()){
                if (player.world != null && !this.world.isClient()) {
                    setInteractTarget(player);
                    player.openHandledScreen(new TulpaScreenHandlerFactory());
                }
            } else if(!this.world.isClient() && hand == Hand.MAIN_HAND &&player.getStackInHand(hand).isEmpty() && player.getUuid().equals(this.getOwnerUuid())) {
                this.cycleActionState(player);
            }
        }
        return super.interactMob(player, hand);
    }

    private void cycleActionState(PlayerEntity player) {
        if(getActionState() == IDLE){
            setActionState(FOLLOW);
            TulpaBrain.setShouldFollowOwner(this, true);
            player.sendMessage(Text.translatable("info.aylyth.tulpa_follow", world.getRegistryKey().getValue().getPath()).setStyle(Style.EMPTY.withColor(Formatting.AQUA)), true);
        }else if(getActionState() == FOLLOW){
            setActionState(SICKO);
            TulpaBrain.setShouldFollowOwner(this, false);
            player.sendMessage(Text.translatable("amogus", world.getRegistryKey().getValue().getPath()).setStyle(Style.EMPTY.withColor(Formatting.DARK_RED).withObfuscated(true).withFont(new Identifier("minecraft", "default"))), true);
        }else if(getActionState() == SICKO){
            setActionState(IDLE);
            player.sendMessage(Text.translatable("info.aylyth.tulpa_wander", world.getRegistryKey().getValue().getPath()).setStyle(Style.EMPTY.withColor(Formatting.AQUA)), true);
        }
    }

    protected void loot(ItemEntity item) {
        InventoryOwner.pickUpItem(this, this, item);
    }

    @Override
    public boolean canGather(ItemStack stack) {
        return this.inventory.canInsert(stack);
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

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
        nbt.putByte("ActionState", getActionState());
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
        nbt.putInt("ShieldCoolDown", this.shieldCoolDown);
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
        setActionState(nbt.getByte("ActionState"));

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
                this.armorInventory.setStack(index, ItemStack.fromNbt(armorItems.getCompound(i)));
            }
        }
        if (nbt.contains("HandItems", 9)) {
            NbtList handItems = nbt.getList("HandItems", 10);
            for (int i = 0; i < ((MobEntityAccessor)this).handItems().size(); ++i) {
                int handSlot = i == 0 ? 0 : 1;
                this.inventory.setStack(handSlot, ItemStack.fromNbt(handItems.getCompound(i)));
            }
        }
        if(nbt.contains("TransformTime")){
            this.transformTime = nbt.getInt("TransformTime");
        }
        this.setCanPickUpLoot(true);
        this.shieldCoolDown = nbt.getInt("ShieldCoolDown");
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (amount > 0.0F && blockedByShield(source)) {
            this.damageShield(amount);
            if (!source.isProjectile()) {
                Entity entity = source.getSource();
                if (entity instanceof LivingEntity) {
                    this.takeShieldHit((LivingEntity) entity);
                    world.sendEntityStatus(entity, (byte) 29);
                }
            }
        }
        boolean damage = super.damage(source, amount);
        if (this.world.isClient) {
            return false;
        } else if (damage && source.getAttacker() instanceof LivingEntity) {
            return true;
        }
        return damage;
    }

    public static int slotToInventoryIndex(EquipmentSlot slot) {
        return switch (slot) {
            case CHEST -> 1;
            case FEET -> 3;
            case LEGS -> 2;
            default -> 0;
        };
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
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    private <E extends IAnimatable> PlayState movementPredicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if(this.dataTracker.get(TRANSFORMING)){
            builder.addAnimation("tulpa_metamorphosis", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
        }else if(event.isMoving()){
            builder.addAnimation("tulpa_walking", ILoopType.EDefaultLoopTypes.LOOP);
        }else{
            builder.addAnimation("tulpa_idle", ILoopType.EDefaultLoopTypes.LOOP);
        }
        if(!builder.getRawAnimationList().isEmpty()) {
            event.getController().setAnimation(builder);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
            builder.addAnimation("tulpa_death", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;

        }else if(this.getDataTracker().get(IS_ATTACKING)){
            builder.addAnimation("tulpa_attacking_right", ILoopType.EDefaultLoopTypes.LOOP);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public SimpleInventory getInventory() {
        return inventory;
    }

    @Override
    public boolean isUsingItem() {
        this.dataTracker.set(USING_ITEM, super.isUsingItem());
        return super.isUsingItem();
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
        this.shieldCoolDown = 8;
    }
    //**CROSSBOW USER END

    @Override
    public void onInventoryChanged(Inventory sender) {

    }

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
            return new TulpaScreenHandler(syncId, inv, this.tulpaEntity().inventory, this.tulpaEntity().armorInventory, this.tulpaEntity());
        }

        @Override
        public Text getDisplayName() {
            return this.tulpaEntity().getDisplayName();
        }
    }

    public static class TulpaPlayerEntity extends PathAwareEntity {
        private static final TrackedData<Optional<UUID>> SKIN_UUID = DataTracker.registerData(TulpaPlayerEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
        private static final TrackedData<Boolean> USING = DataTracker.registerData(TulpaPlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        public TulpaPlayerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
            super(entityType, world);
        }

        protected void initDataTracker() {
            super.initDataTracker();
            this.dataTracker.startTracking(SKIN_UUID, Optional.empty());
            this.dataTracker.startTracking(USING, false);
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

        public void setUsingItem(boolean b) {
            this.dataTracker.set(USING, b);
        }

        public boolean getUsingItem(){
            return this.dataTracker.get(USING);
        }
    }
}
