package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.aylyth.client.advancement.AdvancementIconRenderer;
import moriyashiine.aylyth.client.advancement.AdvancementIconRendererRegistry;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AdvancementTab.class)
public abstract class AdvancementTabMixin {
    @Shadow @Final private AdvancementDisplay display;

    @WrapOperation(method = "drawIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/advancement/AdvancementTabType;drawIcon(Lnet/minecraft/client/gui/DrawContext;IIILnet/minecraft/item/ItemStack;)V"))
    private void aylyth_drawIcon(AdvancementTabType instance, DrawContext context, int x, int y, int index, ItemStack stack, Operation<Void> original) {
        if (this.display instanceof CustomAdvancementDisplay customAdvancementDisplay) {
            int i = x + instance.getTabX(index);
            int j = y + instance.getTabY(index);
            switch (instance) {
                case ABOVE -> {
                    i += 6;
                    j += 9;
                }
                case BELOW -> {
                    i += 6;
                    j += 6;
                }
                case LEFT -> {
                    i += 10;
                    j += 5;
                }
                case RIGHT -> {
                    i += 6;
                    j += 5;
                }
            }

            AdvancementIconRenderer.render(context, customAdvancementDisplay.getRendererData(), i, j);
        } else {
            original.call(instance, context, x, y, index, stack);
        }
    }
}
