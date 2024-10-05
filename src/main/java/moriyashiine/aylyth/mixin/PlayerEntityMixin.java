package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.common.data.AylythDamageTypes;
import moriyashiine.aylyth.common.data.tag.AylythDamageTypeTags;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.entity.AylythAttributes;
import moriyashiine.aylyth.common.entity.AylythEntityComponents;
import moriyashiine.aylyth.common.entity.components.CuirassComponent;
import moriyashiine.aylyth.common.entity.types.mob.BoneflyEntity;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import moriyashiine.aylyth.common.world.AylythWorldAttachmentTypes;
import moriyashiine.aylyth.common.world.attachments.PledgeState;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
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

import static moriyashiine.aylyth.common.block.types.SoulHearthBlock.HALF;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements VitalHealthHolder, HindPledgeHolder {

    @Shadow
    public abstract PlayerInventory getInventory();

    @Shadow
    public abstract boolean isInvulnerableTo(DamageSource var1);

    @Shadow public abstract void remove(RemovalReason reason);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createPlayerAttributes", at = @At("RETURN"), allow = 1)
    private static void addAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(AylythAttributes.MAX_VITAL_HEALTH);
    }

    @Override
    public float getCurrentVitalHealth() {
        return AylythEntityComponents.VITAL_HEALTH.get(this).getCurrentVitalHealth();
    }

    @Override
    public void setCurrentVitalHealth(float vital) {
        AylythEntityComponents.VITAL_HEALTH.get(this).setCurrentVitalHealth(vital);
    }

    @Override
    public UUID getHindUuid() {
        if (!getWorld().isClient) {
            PledgeState pledgeState = ((AttachmentTarget)getWorld()).getAttachedOrCreate(AylythWorldAttachmentTypes.PLEDGE_STATE);
            return pledgeState.getPledge((PlayerEntity)(Object) this);
        }
        return null;
    }

    @Override
    public void setHindUuid(@Nullable UUID uuid) {
        if (!getWorld().isClient) {
            PledgeState pledgeState = ((AttachmentTarget)getWorld()).getAttachedOrCreate(AylythWorldAttachmentTypes.PLEDGE_STATE);
            if (uuid == null) {
                pledgeState.removePledge((PlayerEntity)(Object) this);
            } else {
                pledgeState.addPledge(getUuid(), uuid);
            }

        }
    }

    @Inject(method = "findRespawnPosition", at = @At(value = "HEAD", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), cancellable = true)
    private static void soulHearthRespawn(ServerWorld world, BlockPos pos, float angle, boolean forced, boolean alive, CallbackInfoReturnable<Optional<Vec3d>> cir){
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof SoulHearthBlock && blockState.get(SoulHearthBlock.CHARGES) > 0 && blockState.get(HALF) == DoubleBlockHalf.LOWER && world.getRegistryKey() == AylythDimensionData.WORLD) {
            Optional<Vec3d> optional = SoulHearthBlock.findRespawnPosition(EntityType.PLAYER, world, pos);
            if (!alive && optional.isPresent()) {
                world.setBlockState(pos, blockState.with(SoulHearthBlock.CHARGES, blockState.get(SoulHearthBlock.CHARGES) - 1).with(HALF, DoubleBlockHalf.LOWER));
                world.setBlockState(pos.up(), blockState.with(SoulHearthBlock.CHARGES, blockState.get(SoulHearthBlock.CHARGES) - 1).with(HALF, DoubleBlockHalf.UPPER));
            }
            cir.setReturnValue(optional);
        }
    }

    @Inject(method = "shouldDismount", at = {@At("HEAD")}, cancellable = true)
    private void webbingScuffedry(CallbackInfoReturnable<Boolean> cir) {
        Entity var3 = this.getVehicle();
        if (var3 instanceof BoneflyEntity) {
            if (!Objects.equals(this.getVehicle().getFirstPassenger(), this)) {
                cir.setReturnValue(false);
            }
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/entity/player/PlayerEntity;getHealth()F"), ordinal = 0, argsOnly = true)
    private float modifyDamageForCuirass(float amount, DamageSource source) {
        if (!getWorld().isClient) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            CuirassComponent component = AylythEntityComponents.CUIRASS_COMPONENT.get(player);
            boolean isAxe = source.getAttacker() instanceof LivingEntity livingEntity1 && livingEntity1.getMainHandStack().getItem() instanceof AxeItem;
            if (isAxe || source.isIn(DamageTypeTags.IS_FIRE)) {
                component.setStage(0);
                component.setStageTimer(0);
                player.getWorld().playSoundFromEntity(null, player, AylythSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE.value(), SoundCategory.PLAYERS, 1, player.getSoundPitch());
                return amount;
            } else if (!source.isIn(AylythDamageTypeTags.BYPASSES_CUIRASS)) {
                while (component.getStage() > 0) {
                    amount--;
                    component.setStage(component.getStage() - 1);
                    player.getWorld().playSoundFromEntity(null, player, AylythSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE.value(), SoundCategory.PLAYERS, 1, player.getSoundPitch());
                }
                return amount;
            }
        }
        return amount;
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "LOAD", opcode = Opcodes.FLOAD, ordinal = 2), argsOnly = true)
    private float vitalAbsorption(float damage) {
        float absorbed = Math.max(damage-getCurrentVitalHealth(), 0);
        setCurrentVitalHealth((int) (getCurrentVitalHealth()-damage));
        return absorbed;
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void unpledgeHind(DamageSource damageSource, CallbackInfo ci) {
        if (damageSource.isOf(AylythDamageTypes.KILLING_BLOW)) {
            setHindUuid(null);
        }
    }
}
