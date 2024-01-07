package moriyashiine.aylyth.common.entity.mob;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.api.interfaces.ProlongedDeath;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.ai.brain.TulpaBrain;
import moriyashiine.aylyth.common.registry.ModDataTrackers;
import moriyashiine.aylyth.common.screenhandler.TulpaScreenHandler;
import moriyashiine.aylyth.mixin.MobEntityAccessor;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.item.TaglockItem;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
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
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
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
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class TulpaEntity extends HostileEntity implements TameableHostileEntity, IAnimatable, CrossbowUser,
        InventoryOwner, InventoryChangedListener, ProlongedDeath {
    @Nullable
    private GameProfile skinProfile;
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final TrackedData<Byte> TAMEABLE = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BYTE);
    // TODO: Use a custom tracked data with a serializable enum instead. Let's us set necessary state when changed
    public static final TrackedData<ActionState> ACTION_STATE = DataTracker.registerData(TulpaEntity.class, ModDataTrackers.TULPA_ACTION_STATE);
    private static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Optional<UUID>> SKIN_UUID = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public static final TrackedData<Boolean> TRANSFORMING = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static final TrackedData<Boolean> IS_ATTACKING = DataTracker.registerData(TulpaEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final SimpleInventory inventory = new SimpleInventory(12);
    public static final int MAX_TRANSFORM_TIME = 20 * 5;
    public int transformTime = MAX_TRANSFORM_TIME;
    public int shieldCoolDown;
    @Nullable
    public PlayerEntity interactTarget;
    public float prevStrideDistance;
    public float strideDistance;
    public double prevCapeX;
    public double prevCapeY;
    public double prevCapeZ;
    public double capeX;
    public double capeY;
    public double capeZ;

    public static final float SHOOT_SPEED = 1.6F;
    public static final float MELEE_ATTACK_RANGE = 4.0F;

    public TulpaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent();
        this.inventory.addListener(this);
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
        if(dataTracker.get(TRANSFORMING)) {
            this.getBrain().forget(MemoryModuleType.WALK_TARGET);
            this.getBrain().forget(MemoryModuleType.LOOK_TARGET);
            transformTime--;
            if(transformTime < 0) {
                dataTracker.set(TRANSFORMING, false);
            }
        }
        if (this.shieldCoolDown > 0) {
            --this.shieldCoolDown;
        }
        this.updateCapeAngles();
        super.mobTick();
    }

    @Override
    public void tickMovement() {
        this.prevStrideDistance = this.strideDistance;
        super.tickMovement();
        float f;
        if (this.onGround && !this.isDead() && !this.isSwimming()) {
            f = Math.min(0.1F, (float)this.getVelocity().horizontalLength());
        } else {
            f = 0.0F;
        }
        this.strideDistance += (f - this.strideDistance) * 0.4F;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ACTION_STATE, ActionState.IDLE);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(SKIN_UUID, Optional.empty());
        this.dataTracker.startTracking(TAMEABLE, (byte) 0);
        this.dataTracker.startTracking(TRANSFORMING, false);
        this.dataTracker.startTracking(IS_ATTACKING, false);
    }

    public ActionState getActionState() {
        return dataTracker.get(ACTION_STATE);
    }

    private void setActionState(ActionState actionState) {
        getActionState().onUnset.accept(this);
        dataTracker.set(ACTION_STATE, actionState);
        getActionState().onSet.accept(this);
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
    public ItemStack eatFood(World world, ItemStack stack) {
        if (stack.isFood()) {
            float healAmount;
            if (stack.getItem().getFoodComponent() != null) {
                healAmount = stack.getItem().getFoodComponent().getHunger();
            } else {
                healAmount = 2;
            }
            heal(healAmount);
        }
        return super.eatFood(world, stack);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(true) {
            if(getOwnerUuid() == player.getUuid() || Aylyth.isDebugMode()) {
                if(getSkinUuid() == null) {
                    ItemStack itemStack = player.getMainHandStack();
                    if(FabricLoader.getInstance().isModLoaded("bewitchment")) {
                        if(itemStack.getItem() instanceof TaglockItem) {
                            LivingEntity living = BewitchmentAPI.getTaglockOwner(world, itemStack);
                            if(living instanceof PlayerEntity playerEntity) {
                                setSkinUuid(playerEntity.getUuid());
                                this.setCustomName(playerEntity.getName());
                                this.dataTracker.set(TRANSFORMING, true);
                                setSkinProfile(player.getGameProfile());
                                return ActionResult.CONSUME;
                            }
                        }
                    } else {
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
                }
                if (!player.isSneaking() && player.getMainHandStack().isEmpty()) {
                    if (player.world != null && !this.world.isClient()) {
                        setInteractTarget(player);
                        player.openHandledScreen(new TulpaScreenHandlerFactory());
                    }
                } else if (!this.world.isClient() && player.getMainHandStack().isEmpty() && player.getUuid().equals(this.getOwnerUuid())) {
                    this.cycleActionState(player);
                }
            }
        }

        return super.interactMob(player, hand);
    }

    private void cycleActionState(PlayerEntity player) {
        ActionState nextState = getActionState().next();
        setActionState(nextState);
        nextState.onCycle.accept(player);
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

    @SuppressWarnings("unchecked")
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
        nbt.putString("ActionState", getActionState().asString());
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
        if (this.getSkinUuid() != null) {
            nbt.putUuid("SkinUUID", this.getSkinUuid());
        }
        if (getSkinProfile() != null) {
            NbtCompound nbtCompound = new NbtCompound();
            NbtHelper.writeGameProfile(nbtCompound, skinProfile);
            nbt.put("SkinProfile", nbtCompound);
        }
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
        setActionState(ActionState.CODEC.parse(NbtOps.INSTANCE, nbt.get("ActionState")).result().orElse(ActionState.IDLE));
        if (ownerUUID != null) {
            try {
                this.setOwnerUuid(ownerUUID);
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }
        NbtList nbtList = nbt.getList("Inventory", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound compoundnbt = nbtList.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j < 12) {
                this.inventory.setStack(j, ItemStack.fromNbt(compoundnbt));
            } else {
                ItemScatterer.spawn(world, this.getBlockX(), this.getBlockY() + 1, this.getBlockZ(), ItemStack.fromNbt(compoundnbt));
            }
        }
        if(nbt.contains("TransformTime")){
            this.transformTime = nbt.getInt("TransformTime");
        }
        this.setCanPickUpLoot(true);
        this.shieldCoolDown = nbt.getInt("ShieldCoolDown");
        UUID skinUUID;
        if (nbt.containsUuid("SkinUUID")) {
            skinUUID = nbt.getUuid("SkinUUID");
        } else {
            String string = nbt.getString("SkinUUID");
            skinUUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (skinUUID != null) {
            this.setSkinUuid(skinUUID);
        }
        if (nbt.contains("SkinProfile", NbtElement.COMPOUND_TYPE)) {
            this.setSkinProfile(NbtHelper.toGameProfile(nbt.getCompound("SkinProfile")));
        }
    }

    public GameProfile getSkinProfile() {
        return skinProfile;
    }

    public void setSkinProfile(@Nullable GameProfile profile) {
        this.skinProfile = profile;
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
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

    @Override
    protected void dropInventory() {
        MobEntityAccessor accessor = ((MobEntityAccessor)this);
        ItemScatterer.spawn(world, this, inventory);
        ItemScatterer.spawn(world, this.getBlockPos(), accessor.armorItems());
        ItemScatterer.spawn(world, this.getBlockPos(), accessor.handItems());
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

    @Nullable
    @Override
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerUuid();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
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
    public int getDeathAnimationTime(){
        return (int)(20d * 6.8);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "moveController", 5, this::movementPredicate));
    }

    private <E extends IAnimatable> PlayState movementPredicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
            if (dataTracker.get(SKIN_UUID).isPresent()) {
                builder.addAnimation("tulpa_mimic_death", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
            } else {
                builder.addAnimation("tulpa_death", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
            }
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        } else if (this.getSkinProfile() != null) {
            builder.addAnimation("tulpa_transform", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME);
        } else if (this.getDataTracker().get(IS_ATTACKING)) {
            builder.addAnimation("tulpa_attacking_right", ILoopType.EDefaultLoopTypes.LOOP);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            builder.addAnimation("tulpa_walking", ILoopType.EDefaultLoopTypes.LOOP);
        } else {
            builder.addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
        }
        if (!builder.getRawAnimationList().isEmpty()) {
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

    @Override
    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        // TODO: Check both "c:bows" and "c:crossbows" when tag changes are implemented
        return weapon.getRegistryEntry().isIn(ConventionalItemTags.BOWS);
    }

    //**CROSSBOW USER START
    @Override
    public void setCharging(boolean charging) {
        setCurrentHand(ProjectileUtil.getHandPossiblyHolding(this, Items.CROSSBOW));
//        this.dataTracker.set(IS_ATTACKING, charging);
    }

    @Override
    public void shoot(LivingEntity target, ItemStack crossbow, ProjectileEntity projectile, float multiShotSpray) {
        this.shoot(this, target, projectile, multiShotSpray, SHOOT_SPEED);
    }

    @Override
    public void postShoot() {

    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        this.shieldCoolDown = 8;
        // TODO: Change this back to a tag check for "c:bows" when crossbows are removed from the tag
        Hand hand = ProjectileUtil.getHandPossiblyHolding(this, Items.BOW);
        if (getStackInHand(hand).isOf(Items.BOW)) {
            ProjectileEntity arrow = ProjectileUtil.createArrowProjectile(this, new ItemStack(Items.ARROW), pullProgress);
            this.world.spawnEntity(arrow);
            double xDiff = target.getX() - getX();
            double yDiff = target.getBodyY(0.3333333333333333) - arrow.getY();
            double zDiff = target.getZ() - getZ();
            double dist = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
            arrow.setVelocity(xDiff, yDiff + dist * (double)0.2f, zDiff, 1.6f, 14 - this.world.getDifficulty().getId() * 4);
            this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        } else {
            this.shoot(this, SHOOT_SPEED);
        }
    }
    //**CROSSBOW USER END

    @Override
    public double squaredAttackRange(LivingEntity target) {
        return (this.getWidth() * MELEE_ATTACK_RANGE) * (this.getWidth() * MELEE_ATTACK_RANGE) + target.getWidth();
    }

    private void updateCapeAngles() {
        this.prevCapeX = this.capeX;
        this.prevCapeY = this.capeY;
        this.prevCapeZ = this.capeZ;
        double d = this.getX() - this.capeX;
        double e = this.getY() - this.capeY;
        double f = this.getZ() - this.capeZ;
        double g = 10.0;
        if (d > 10.0) {
            this.capeX = this.getX();
            this.prevCapeX = this.capeX;
        }

        if (f > 10.0) {
            this.capeZ = this.getZ();
            this.prevCapeZ = this.capeZ;
        }

        if (e > 10.0) {
            this.capeY = this.getY();
            this.prevCapeY = this.capeY;
        }

        if (d < -10.0) {
            this.capeX = this.getX();
            this.prevCapeX = this.capeX;
        }

        if (f < -10.0) {
            this.capeZ = this.getZ();
            this.prevCapeZ = this.capeZ;
        }

        if (e < -10.0) {
            this.capeY = this.getY();
            this.prevCapeY = this.capeY;
        }

        this.capeX += d * 0.25;
        this.capeZ += f * 0.25;
        this.capeY += e * 0.25;
    }

    @Override
    public void onInventoryChanged(Inventory sender) {

    }

    public enum ActionState implements StringIdentifiable {
        IDLE("idle",
                player -> player.sendMessage(Text.translatable("info.aylyth.tulpa_wander").setStyle(Style.EMPTY.withColor(Formatting.AQUA)), true),
                tulpa -> {},
                tulpa -> {}
        ),
        FOLLOW("follow",
                player -> player.sendMessage(Text.translatable("info.aylyth.tulpa_follow").setStyle(Style.EMPTY.withColor(Formatting.AQUA)), true),
                tulpa -> TulpaBrain.setShouldFollowOwner(tulpa, true),
                tulpa -> TulpaBrain.setShouldFollowOwner(tulpa, false)
        ),
        SICKO("sicko",
                player -> player.sendMessage(Text.literal("amogus").setStyle(Style.EMPTY.withColor(Formatting.DARK_RED).withObfuscated(true)), true),
                tulpa -> {},
                tulpa -> {}
        );

        public static final com.mojang.serialization.Codec<ActionState> CODEC = StringIdentifiable.createCodec(ActionState::values);
        private final String name;
        private final Consumer<PlayerEntity> onCycle;
        private final Consumer<TulpaEntity> onUnset;
        private final Consumer<TulpaEntity> onSet;

        ActionState(String name, Consumer<PlayerEntity> onCycle, Consumer<TulpaEntity> onSet, Consumer<TulpaEntity> onUnset) {
            this.name = name;
            this.onCycle = onCycle;
            this.onUnset = onUnset;
            this.onSet = onSet;
        }

        public ActionState next() {
            if (this.ordinal() == values().length-1) {
                return ActionState.IDLE;
            }
            return values()[this.ordinal()+1];
        }

        @Override
        public String asString() {
            return this.name;
        }
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
            return new TulpaScreenHandler(syncId, inv, this.tulpaEntity().inventory, ((MobEntityAccessor)this.tulpaEntity()).armorItems(), ((MobEntityAccessor)this.tulpaEntity()).handItems(), this.tulpaEntity());
        }

        @Override
        public Text getDisplayName() {
            return this.tulpaEntity().getDisplayName();
        }
    }

    public static class TulpaPlayerEntity extends PathAwareEntity {
        private static final TrackedData<Optional<UUID>> SKIN_UUID = DataTracker.registerData(TulpaPlayerEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
        public float prevStrideDistance;
        public float strideDistance;
        public double prevCapeX;
        public double prevCapeY;
        public double prevCapeZ;
        public double capeX;
        public double capeY;
        public double capeZ;
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
