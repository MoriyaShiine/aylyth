package moriyashiine.aylyth.mixin.cimmerian;

import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.registry.ModPotions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin {

    @Shadow @Final protected PathAwareEntity mob;

    @Shadow public abstract void stop();

    @Inject(method = "shouldContinue", at = @At(value = "JUMP", opcode = Opcodes.IFNE, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void aylyth_shouldContinue(CallbackInfoReturnable<Boolean> cir, LivingEntity livingEntity) {
//        if (livingEntity != null && AylythUtil.shouldUndeadAttack(livingEntity, mob)) {
//            stop();
//            cir.setReturnValue(false);
//        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void aylyth_tick(CallbackInfo ci) {
//        var livingEntity = mob.getTarget();
//        if (livingEntity != null && AylythUtil.shouldUndeadAttack(livingEntity, mob)) {
//            stop();
//            ci.cancel();
//        }
    }
}
