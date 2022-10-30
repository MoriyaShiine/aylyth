package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.block.SoulHeart;
import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Objects;
import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    protected boolean isSubmergedInWater;

    @Shadow
    public abstract PlayerInventory getInventory();

    @Shadow
    public abstract boolean isInvulnerableTo(DamageSource var1);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "findRespawnPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), cancellable = true)
    private static void aylyth$injectSoulHeartRespawn(ServerWorld world, BlockPos pos, float angle, boolean forced, boolean alive, CallbackInfoReturnable<Optional<Vec3d>> cir){
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof SoulHeart && blockState.get(SoulHeart.CHARGED) && SoulHeart.isAylyth(world)) {
            Optional<Vec3d> optional = SoulHeart.findRespawnPosition(EntityType.PLAYER, world, pos);
            if (!alive && optional.isPresent()) {
                world.setBlockState(pos, blockState.with(SoulHeart.CHARGED, false), Block.NOTIFY_ALL);
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

    @ModifyArgs(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void aylyth$onDamaged(Args args) {
        DamageSource source = args.get(0);
        float value = args.get(1);
        if (this.getInventory().contains(ModItems.SOULTRAP_EFFIGY_ITEM.getDefaultStack()) && this.isSubmergedInWater && !this.isInvulnerableTo(source) && !this.isDead() && this.random.nextInt(6) == 1) {
            args.set(1, value * 2.0F);
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
