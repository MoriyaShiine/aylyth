package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.block.SoulHearthBlock;
import moriyashiine.aylyth.common.component.entity.CuirassComponent;
import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import moriyashiine.aylyth.common.registry.ModEntityComponents;
import moriyashiine.aylyth.common.registry.ModEntityAttributes;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import moriyashiine.aylyth.common.registry.key.ModDamageTypeKeys;
import moriyashiine.aylyth.common.registry.key.ModDimensionKeys;
import moriyashiine.aylyth.common.registry.tag.ModDamageTypeTags;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.ModWorldState;
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

import static moriyashiine.aylyth.common.block.SoulHearthBlock.HALF;

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
        cir.getReturnValue().add(ModEntityAttributes.MAX_VITAL_HEALTH);
    }

    @Override
    public float getCurrentVitalHealth() {
        return ModEntityComponents.VITAL_HEALTH.get(this).getCurrentVitalHealth();
    }

    @Override
    public void setCurrentVitalHealth(float vital) {
        ModEntityComponents.VITAL_HEALTH.get(this).setCurrentVitalHealth(vital);
    }

    @Override
    public UUID getHindUuid() {
        return ModEntityComponents.HIND_PLEDGE.get(this).getHindUuid();
    }

    @Override
    public void setHindUuid(@Nullable UUID uuid) {
        ModEntityComponents.HIND_PLEDGE.get(this).setHindUuid(uuid);
    }

    @Inject(method = "findRespawnPosition", at = @At(value = "HEAD", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), cancellable = true)
    private static void soulHearthRespawn(ServerWorld world, BlockPos pos, float angle, boolean forced, boolean alive, CallbackInfoReturnable<Optional<Vec3d>> cir){
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof SoulHearthBlock && blockState.get(SoulHearthBlock.CHARGES) > 0 && blockState.get(HALF) == DoubleBlockHalf.LOWER && world.getRegistryKey() == ModDimensionKeys.AYLYTH) {
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
            CuirassComponent component = ModEntityComponents.CUIRASS_COMPONENT.get(player);
            boolean bypassesCuirass = source.isIn(ModDamageTypeTags.BYPASSES_CUIRASS);
            boolean isAxe = source.getAttacker() instanceof LivingEntity livingEntity1 && livingEntity1.getMainHandStack().getItem() instanceof AxeItem;
            boolean isFireDamage = source.isIn(DamageTypeTags.IS_FIRE);
            if (isAxe || isFireDamage) {
                component.setStage(0);
                component.setStageTimer(0);
                player.getWorld().playSoundFromEntity(null, player, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE.value(), SoundCategory.PLAYERS, 1, player.getSoundPitch());
                return amount;
            } else if (!bypassesCuirass) {
                while (component.getStage() > 0) {
                    amount--;
                    component.setStage(component.getStage() - 1);
                    player.getWorld().playSoundFromEntity(null, player, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE.value(), SoundCategory.PLAYERS, 1, player.getSoundPitch());
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

    @Inject(method = "tick", at = @At("TAIL"))
    private void removePledgeASAP(CallbackInfo ci){
        // Could also put this in a server tick event?, checking that the player is online/available
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

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void unpledgeHind(DamageSource damageSource, CallbackInfo ci) {
        if (damageSource.isOf(ModDamageTypeKeys.KILLING_BLOW)) {
            setHindUuid(null);
        }
    }
}
