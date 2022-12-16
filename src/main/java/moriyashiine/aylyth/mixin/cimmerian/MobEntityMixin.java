package moriyashiine.aylyth.mixin.cimmerian;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Shadow private @Nullable LivingEntity target;

    @Inject(method = "getTarget", at = @At("HEAD"), cancellable = true)
    private void aylyth_getTarget(CallbackInfoReturnable<LivingEntity> cir) {
        if (target != null && AylythUtil.shouldUndeadAttack(target, (MobEntity)(Object)this)) {
            cir.setReturnValue(null);
        }
    }
}
