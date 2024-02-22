package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AdvancementWidget.class)
public class AdvancementWidgetMixin {

    @Shadow @Final private AdvancementDisplay display;
    @Shadow @Final private int x;
    @Shadow @Final private int y;
    @Unique
    private TriState aylyth_customDisplay = TriState.DEFAULT;

    @WrapOperation(method = "renderWidgets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;II)V"))
    private void wrapCustomWidget(DrawContext instance, ItemStack stack, int x, int y, Operation<Void> original) {
        if (aylyth_customDisplay == TriState.DEFAULT) {
            aylyth_customDisplay = (display instanceof CustomAdvancementDisplay ? TriState.TRUE : TriState.FALSE);
        }

        if (aylyth_customDisplay.get()) {
            instance.drawTexture(((CustomAdvancementDisplay)display).getTexture(), x, y, 0, 0, 16, 16, 16, 16);
        } else {
            original.call(instance, stack, x, y);
        }
    }

    @WrapOperation(method = "drawTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;II)V"))
    private void wrapCustomTooltip(DrawContext instance, ItemStack stack, int x, int y, Operation<Void> original) {
        if (aylyth_customDisplay.get()) {
            instance.drawTexture(((CustomAdvancementDisplay)display).getTexture(), x, y, 0, 0, 16, 16, 16, 16);

        } else {
            original.call(instance, stack, x, y);
        }
    }
}
