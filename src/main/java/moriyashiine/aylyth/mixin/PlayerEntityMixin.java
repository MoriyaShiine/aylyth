package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.api.interfaces.Vital;
import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.block.SoulHearthBlock;
import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static moriyashiine.aylyth.common.block.SoulHearthBlock.HALF;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements Vital {
    private static final EntityAttributeModifier VITAL_HEALTH_MODIFIER = new EntityAttributeModifier(UUID.fromString("2ee98b0b-7180-46ac-97ce-d8f7307bffb1"), "vital modifier", 20, EntityAttributeModifier.Operation.ADDITION);



    @Shadow
    protected boolean isSubmergedInWater;

    @Shadow
    public abstract PlayerInventory getInventory();

    @Shadow
    public abstract boolean isInvulnerableTo(DamageSource var1);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }



    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeAylythData(NbtCompound compoundTag, CallbackInfo info) {
        NbtCompound tag = new NbtCompound();
        tag.putBoolean("vital", hasVital());

        compoundTag.put("aylyth_data", tag);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readAylythData(NbtCompound compoundTag, CallbackInfo info) {
        NbtCompound tag = (NbtCompound) compoundTag.get("aylyth_data");
        if (tag != null) {
            setVital(tag.getBoolean("vital"));
        }
    }

    @Override
    public boolean hasVital() {
        return dataTracker.get(AylythUtil.VITAL);
    }

    @Override
    public void setVital(boolean vital) {
        dataTracker.set(AylythUtil.VITAL, vital);
        PlayerEntity player = (PlayerEntity) (Object) this;
        EntityAttributeInstance healthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if(healthAttribute != null){
            if (healthAttribute.hasModifier(VITAL_HEALTH_MODIFIER)) {
                healthAttribute.removeModifier(VITAL_HEALTH_MODIFIER);
            }
            if (vital && !healthAttribute.hasModifier(VITAL_HEALTH_MODIFIER)) {
                healthAttribute.addPersistentModifier(VITAL_HEALTH_MODIFIER);
            }
        }
    }

    @Inject(method = "initDataTracker()V", at = @At("TAIL"))
    private void addAylythTrackers(CallbackInfo info) {
        dataTracker.startTracking(AylythUtil.VITAL, false);
    }

    @Inject(method = "findRespawnPosition", at = @At(value = "HEAD", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), cancellable = true)
    private static void aylyth$injectSoulHeartRespawn(ServerWorld world, BlockPos pos, float angle, boolean forced, boolean alive, CallbackInfoReturnable<Optional<Vec3d>> cir){
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
    private void aylyth$webbingScuffedry(CallbackInfoReturnable<Boolean> cir) {
        Entity var3 = this.getVehicle();
        if (var3 instanceof BoneflyEntity fly) {
            if (!Objects.equals(this.getVehicle().getFirstPassenger(), this)) {
                cir.setReturnValue(false);
            }
        }

    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void submergedDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(this.getInventory().contains(ModItems.SOULTRAP_EFFIGY_ITEM.getDefaultStack()) && this.isSubmergedInWater && !isInvulnerableTo(source) && !this.isDead() && random.nextInt(6) == 1) {
            super.damage(source, amount);
            this.timeUntilRegen = 0;
        }
    }



    @Override
    public void stopRiding() {
        Entity var2 = this.getVehicle();
        if (var2 instanceof BoneflyEntity fly) {
            fly.getPassengerList().forEach(Entity::dismountVehicle);
        }

        super.stopRiding();
    }

    @Override
    public EntityGroup getGroup() {
        return this.getInventory().contains(ModItems.SOULTRAP_EFFIGY_ITEM.getDefaultStack()) ? EntityGroup.UNDEAD : super.getGroup();
    }

    @Override
    public boolean isUndead() {
        return this.getInventory().contains(ModItems.SOULTRAP_EFFIGY_ITEM.getDefaultStack()) || super.isUndead();
    }

    @Override
    public boolean hurtByWater() {
        return this.getInventory().contains(ModItems.SOULTRAP_EFFIGY_ITEM.getDefaultStack()) && (this.getWorld().getBiome(this.getBlockPos()).isIn(BiomeTags.IS_RIVER) || this.isInFlowingFluid(FluidTags.WATER)) || super.hurtByWater();
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (!this.getInventory().contains(ModItems.SOULTRAP_EFFIGY_ITEM.getDefaultStack())) {
            return super.canHaveStatusEffect(effect);
        } else {
            return effect.getEffectType() == StatusEffects.WITHER || effect.getEffectType() == StatusEffects.INSTANT_DAMAGE || effect.getEffectType() == StatusEffects.INSTANT_HEALTH;
        }
    }

    private boolean isInFlowingFluid(TagKey<Fluid> tag) {
        if (this.isRegionUnloaded()) {
            return false;
        } else {
            Box box = this.getBoundingBox().contract(0.001);
            int i = MathHelper.floor(box.minX);
            int j = MathHelper.ceil(box.maxX);
            int k = MathHelper.floor(box.minY);
            int l = MathHelper.ceil(box.maxY);
            int m = MathHelper.floor(box.minZ);
            int n = MathHelper.ceil(box.maxZ);
            double d = 0.0;
            boolean bl = this.isPushedByFluids();
            boolean bl2 = false;
            Vec3d vec3d = Vec3d.ZERO;
            int o = 0;
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for(int p = i; p < j; ++p) {
                for(int q = k; q < l; ++q) {
                    for(int r = m; r < n; ++r) {
                        mutable.set(p, q, r);
                        FluidState fluidState = this.world.getFluidState(mutable);
                        double e;
                        if (fluidState.isIn(tag) && (e = (double)((float)q + fluidState.getHeight(this.world, mutable))) >= box.minY && !fluidState.isEqualAndStill(Fluids.WATER)) {
                            bl2 = true;
                            d = Math.max(e - box.minY, d);
                            if (bl) {
                                Vec3d vec3d2 = fluidState.getVelocity(this.world, mutable);
                                if (d < 0.4) {
                                    vec3d2 = vec3d2.multiply(d);
                                }

                                vec3d = vec3d.add(vec3d2);
                                ++o;
                            }
                        }
                    }
                }
            }

            return bl2;
        }
    }
}
