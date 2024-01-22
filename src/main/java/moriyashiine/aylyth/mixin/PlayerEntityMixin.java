package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.api.interfaces.VitalHolder;
import moriyashiine.aylyth.common.block.SoulHearthBlock;
import moriyashiine.aylyth.common.component.entity.CuirassComponent;
import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import moriyashiine.aylyth.common.registry.ModComponents;
import moriyashiine.aylyth.common.registry.key.ModDamageTypeKeys;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.ModWorldState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stat;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static moriyashiine.aylyth.common.block.SoulHearthBlock.HALF;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements VitalHolder, HindPledgeHolder {

    @Shadow
    protected boolean isSubmergedInWater;

    @Shadow
    public abstract PlayerInventory getInventory();

    @Shadow
    public abstract boolean isInvulnerableTo(DamageSource var1);

    @Shadow public abstract void remove(RemovalReason reason);

    @Shadow public abstract void increaseStat(Stat<?> stat, int amount);

    @Shadow public abstract void increaseStat(Identifier stat, int amount);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

//    @Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;vanishCursedItems()V"), cancellable = true)
//    private void aylyth_keepPledgeInv(CallbackInfo ci) {
//        if (getHindUuid() != null) {
//            ci.cancel();
//        }
//    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeAylythData(NbtCompound nbtCompound, CallbackInfo info) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("Vital", getVitalThuribleLevel());
        if (this.getHindUuid() != null) {
            nbt.putUuid("HindUuid", getHindUuid());
        }
        nbtCompound.put("AylythData", nbt);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readAylythData(NbtCompound nbtCompound, CallbackInfo info) {
        NbtCompound nbt = (NbtCompound) nbtCompound.get("AylythData");
        if (nbt != null) {
            setVitalThuribleLevel(nbt.getInt("Vital"));
        }
        if (nbt != null && nbt.containsUuid("HindUuid")) {
            UUID ownerUUID = nbt.getUuid("HindUuid");
            this.setHindUuid(ownerUUID);
        }
    }

    @Override
    public int getVitalThuribleLevel() {
        return dataTracker.get(AylythUtil.VITAL);
    }

    @Override
    public void setVitalThuribleLevel(int vital) {
        if(vital <= 10){
            dataTracker.set(AylythUtil.VITAL, vital);
            PlayerEntity player = (PlayerEntity) (Object) this;
            EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            int level = dataTracker.get(AylythUtil.VITAL);
            AylythUtil.handleVital(healthAttribute, level);
        }
    }

    @Override
    public UUID getHindUuid() {
        return  this.dataTracker.get(AylythUtil.HIND_UUID).orElse(null);
    }

    @Override
    public void setHindUuid(@Nullable UUID uuid) {
        this.dataTracker.set(AylythUtil.HIND_UUID, Optional.ofNullable(uuid));
    }

    @Inject(method = "initDataTracker()V", at = @At("TAIL"))
    private void addAylythTrackers(CallbackInfo info) {
        dataTracker.startTracking(AylythUtil.VITAL, 0);
        dataTracker.startTracking(AylythUtil.HIND_UUID, Optional.empty());
    }

    @Inject(method = "findRespawnPosition", at = @At(value = "HEAD", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), cancellable = true)
    private static void aylyth_injectSoulHeartRespawn(ServerWorld world, BlockPos pos, float angle, boolean forced, boolean alive, CallbackInfoReturnable<Optional<Vec3d>> cir){
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof SoulHearthBlock && blockState.get(SoulHearthBlock.CHARGES) > 0 && blockState.get(HALF) == DoubleBlockHalf.LOWER && SoulHearthBlock.isAylyth(world)) {
            Optional<Vec3d> optional = SoulHearthBlock.findRespawnPosition(EntityType.PLAYER, world, pos);
            if (!alive && optional.isPresent()) {
                world.setBlockState(pos, blockState.with(SoulHearthBlock.CHARGES, blockState.get(SoulHearthBlock.CHARGES) - 1).with(HALF, DoubleBlockHalf.LOWER), Block.NOTIFY_ALL);
                world.setBlockState(pos.up(), blockState.with(SoulHearthBlock.CHARGES, blockState.get(SoulHearthBlock.CHARGES) - 1).with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
            }
            cir.setReturnValue(optional);
        }
    }

    @Inject(method = "shouldDismount", at = {@At("HEAD")}, cancellable = true)
    private void aylyth_webbingScuffedry(CallbackInfoReturnable<Boolean> cir) {
        Entity var3 = this.getVehicle();
        if (var3 instanceof BoneflyEntity fly) {
            if (!Objects.equals(this.getVehicle().getFirstPassenger(), this)) {
                cir.setReturnValue(false);
            }
        }

    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/entity/player/PlayerEntity;getHealth()F"), ordinal = 0, argsOnly = true)
    private float aylyth$modifyDamageForCuirass(float amount, DamageSource source) {
        if (!getWorld().isClient) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            CuirassComponent component = ModComponents.CUIRASS_COMPONENT.get(player);
            boolean bl = source.isOf(DamageTypes.MAGIC) || source.isIn(DamageTypeTags.IS_FALL) || source.isOf(DamageTypes.OUT_OF_WORLD);
            boolean bl2 = source.getAttacker() != null && source.getAttacker() instanceof LivingEntity livingEntity1 && livingEntity1.getMainHandStack().getItem() instanceof AxeItem;
            boolean bl3 = source.isIn(DamageTypeTags.IS_FIRE);
            if(bl2 || bl3){
                component.setStage(0);
                component.setStageTimer(0);
                player.getWorld().playSoundFromEntity(null, player, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE, SoundCategory.PLAYERS, 1, player.getSoundPitch());
                return amount;
            } else if(bl){
                return amount;
            } else {
                while (component.getStage() > 0) {
                    amount--;
                    component.setStage(component.getStage() - 1);
                    player.getWorld().playSoundFromEntity(null, player, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE, SoundCategory.PLAYERS, 1, player.getSoundPitch());
                }
                return amount;
            }
        }
        return amount;
    }



    @Inject(method = "tick", at = @At("TAIL"))
    private void aylyth_removePledgeASAP(CallbackInfo ci){
        if(getHindUuid() != null && !getWorld().isClient()){
            ModWorldState modWorldState = ModWorldState.get(getWorld());
            PlayerEntity player = (PlayerEntity) (Object) this;
            if (modWorldState.hasPledgesToRemove()) {
                if (modWorldState.removePledge(player.getUuid())) {
                    setHindUuid(null);
                }
            }
        }
    }

    @Override
    public void stopRiding() {
        if (this.getVehicle() instanceof BoneflyEntity fly) {
            fly.getPassengerList().forEach(Entity::dismountVehicle);
        }
        super.stopRiding();
    }

    @Override
    public EntityGroup getGroup() {
        return this.getInventory().contains(ModItems.YMPE_EFFIGY_ITEM.getDefaultStack()) ? EntityGroup.UNDEAD : super.getGroup();
    }

    @Override
    public boolean hurtByWater() {
        return this.getInventory().contains(ModItems.YMPE_EFFIGY_ITEM.getDefaultStack()) && (this.getWorld().getBiome(this.getBlockPos()).isIn(BiomeTags.IS_RIVER) || fluidHeight.getDouble(FluidTags.WATER) > 0) || super.hurtByWater();
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void aylyth_unpledgeHind(DamageSource damageSource, CallbackInfo ci) {
        PlayerEntity thiz = (PlayerEntity) (Object) this;
        if (damageSource.isOf(ModDamageTypeKeys.UNBLOCKABLE)) {
            HindPledgeHolder.of(thiz).ifPresent(hindPledgeHolder -> {
                hindPledgeHolder.setHindUuid(null);
            });
        }
    }
}
