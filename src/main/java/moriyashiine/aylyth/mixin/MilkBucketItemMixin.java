package moriyashiine.aylyth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.common.registry.tag.ModEffectTags;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @Unique

    @WrapOperation(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private boolean finishUsing(LivingEntity player, Operation<Boolean> original, @Local(argsOnly = true) ItemStack stack) {

        ArrayList<StatusEffectInstance> arr = new ArrayList<>();
        for (StatusEffectInstance persistentStatusEffect : player.getStatusEffects()) {
            if (TagUtil.isIn(ModEffectTags.MILK_BYPASSING, persistentStatusEffect.getEffectType())) {
                arr.add(persistentStatusEffect);
            }
        }

        original.call(player);

        for (StatusEffectInstance effectInstance : arr) {
            player.addStatusEffect(effectInstance);
        }

        return true;
    }
}
