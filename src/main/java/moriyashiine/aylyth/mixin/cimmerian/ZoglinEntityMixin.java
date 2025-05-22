package moriyashiine.aylyth.mixin.cimmerian;

import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZoglinEntity.class)
public class ZoglinEntityMixin {

    @Inject(method = "shouldAttack", at = @At("HEAD"), cancellable = true)
    private void aylyth_shouldAttack(ServerWorld world, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (target.hasStatusEffect(AylythStatusEffects.CIMMERIAN)) {
            cir.setReturnValue(false);
        }
    }
}
