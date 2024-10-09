package moriyashiine.aylyth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.data.tag.AylythStatusEffectTags;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @WrapOperation(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private boolean finishUsing(LivingEntity player, Operation<Boolean> original) {

        List<StatusEffectInstance> arr = new ObjectArrayList<>();
        for (StatusEffectInstance persistentStatusEffect : player.getStatusEffects()) {
            if (TagUtil.isIn(AylythStatusEffectTags.MILK_CANNOT_CURE, persistentStatusEffect.getEffectType())) {
                arr.add(persistentStatusEffect);
            }
        }

        boolean originalReturn = original.call(player);

        for (StatusEffectInstance effectInstance : arr) {
            player.addStatusEffect(effectInstance);
        }

        return originalReturn;
    }
}
