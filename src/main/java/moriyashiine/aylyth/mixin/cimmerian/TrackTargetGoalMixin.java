package moriyashiine.aylyth.mixin.cimmerian;

import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.registry.ModPotions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TrackTargetGoal.class)
public abstract class TrackTargetGoalMixin {

    @Shadow @Nullable protected LivingEntity target;

    @Shadow @Final protected MobEntity mob;

    @Shadow public abstract void stop();

    @Inject(method = "shouldContinue", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void aylyth_shouldContinue(CallbackInfoReturnable<Boolean> cir, LivingEntity livingEntity) {
//        if (livingEntity != null && AylythUtil.shouldUndeadAttack(livingEntity, mob)) {
//            stop();
//            cir.setReturnValue(false);
//        }
    }
}
