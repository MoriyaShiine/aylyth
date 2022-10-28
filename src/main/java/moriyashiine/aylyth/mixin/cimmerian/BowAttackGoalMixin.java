package moriyashiine.aylyth.mixin.cimmerian;

import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.registry.ModPotions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowAttackGoal.class)
public abstract class BowAttackGoalMixin<T extends HostileEntity> {

    @Shadow @Final private T actor;

    @Shadow public abstract void stop();

    @Inject(method = "shouldContinue", at = @At("HEAD"), cancellable = true)
    private void aylyth_shouldContinue(CallbackInfoReturnable<Boolean> cir) {
//        var target = actor.getTarget();
//        if (target != null && AylythUtil.shouldUndeadAttack(target, actor)) {
//            stop();
//            cir.setReturnValue(false);
//        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void aylyth_tick(CallbackInfo ci) {
//        var target = actor.getTarget();
//        if (target != null && AylythUtil.shouldUndeadAttack(target, actor)) {
//            stop();
//            ci.cancel();
//        }
    }
}
