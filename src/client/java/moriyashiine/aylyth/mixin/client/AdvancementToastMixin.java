package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.aylyth.client.advancement.AdvancementIconRenderer;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin {

    @Shadow @Final private Advancement advancement;

    @WrapOperation(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;II)V"))
    private void aylyth_draw(DrawContext instance, ItemStack stack, int x, int y, Operation<Void> original) {
        if (this.advancement.getDisplay() instanceof CustomAdvancementDisplay customAdvancementDisplay) {
            AdvancementIconRenderer.render(instance, customAdvancementDisplay.getRendererData(), x, y);
        } else {
            original.call(instance, stack, x, y);
        }
    }
}
