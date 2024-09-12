package moriyashiine.aylyth.mixin.cimmerian;

import moriyashiine.aylyth.common.registry.AylythStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZoglinEntity.class)
public class ZoglinEntityMixin {

    @Inject(method = "shouldAttack", at = @At("HEAD"), cancellable = true)
    private void aylyth_shouldAttack(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity.hasStatusEffect(AylythStatusEffects.CIMMERIAN)) {
            cir.setReturnValue(false);
        }
    }
}
