package moriyashiine.aylyth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.data.tag.AylythStatusEffectTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ClearAllEffectsConsumeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;
import java.util.List;

@Mixin(ClearAllEffectsConsumeEffect.class)
public class ClearAllEffectsConsumeEffectMixin {

    /**
     * By wrapping the original operation and handling the internal active status effects directly, we avoid running
     * <code>onRemoved</code> function of the status effect and the <code>onApplied</code> function when adding the
     * effect back.
    */
    @WrapOperation(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private boolean storeEffects(LivingEntity instance, Operation<Boolean> original, @Local(argsOnly = true) ItemStack stack) {
        if (stack.isOf(Items.MILK_BUCKET)) {
            LivingEntityAccessor accessor = (LivingEntityAccessor) instance;
            List<StatusEffectInstance> effectsToAddBack = new ObjectArrayList<>();
            Iterator<StatusEffectInstance> iter = instance.getStatusEffects().iterator();
            while (iter.hasNext()) {
                StatusEffectInstance effect = iter.next();
                if (effect.getEffectType().isIn(AylythStatusEffectTags.MILK_CANNOT_CURE)) {
                    iter.remove();
                    effectsToAddBack.add(effect);
                }
            }
            boolean res = original.call(instance);
            for (StatusEffectInstance effect : effectsToAddBack) {
                accessor.getActiveStatusEffects().put(effect.getEffectType(), effect);
            }
            return res;
        }
        return original.call(instance);
    }
}
