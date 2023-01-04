package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.item.YmpeLanceItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/enchantment/EnchantmentTarget$9")
public class EnchantmentTargetMixin {
    @Inject(method = "isAcceptableItem(Lnet/minecraft/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    private void aylyth_lanceIsAcceptableItem(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (item instanceof YmpeLanceItem){
            cir.setReturnValue(true);
        }
    }
}