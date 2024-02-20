package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.aylyth.client.advancement.CustomAdvancementWidget;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AdvancementTab.class)
public abstract class AdvancementTabMixin {
    @Shadow @Final private AdvancementDisplay display;

    @WrapOperation(method = "<init>", at = @At(value = "NEW", target = "(Lnet/minecraft/client/gui/screen/advancement/AdvancementTab;Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/advancement/Advancement;Lnet/minecraft/advancement/AdvancementDisplay;)Lnet/minecraft/client/gui/screen/advancement/AdvancementWidget;"))
    private AdvancementWidget aylyth_init(AdvancementTab tab, MinecraftClient client, Advancement advancement, AdvancementDisplay display, Operation<AdvancementWidget> original) {
        if (display instanceof CustomAdvancementDisplay customAdvancementDisplay) {
            return new CustomAdvancementWidget((AdvancementTab) (Object) this, client, advancement, customAdvancementDisplay);
        } else {
            return original.call(tab, client, advancement, display);
        }
    }

    @WrapOperation(method = "addAdvancement", at = @At(value = "NEW", target = "(Lnet/minecraft/client/gui/screen/advancement/AdvancementTab;Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/advancement/Advancement;Lnet/minecraft/advancement/AdvancementDisplay;)Lnet/minecraft/client/gui/screen/advancement/AdvancementWidget;"))
    private AdvancementWidget aylyth_addAdvancement(AdvancementTab tab, MinecraftClient client, Advancement advancement, AdvancementDisplay display, Operation<AdvancementWidget> original) {
        if (advancement.getDisplay() instanceof CustomAdvancementDisplay customAdvancementDisplay) {
            return new CustomAdvancementWidget((AdvancementTab) (Object) this, MinecraftClient.getInstance(), advancement, customAdvancementDisplay);
        }
        return original.call(tab, client, advancement, display);
    }

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

            context.drawTexture(customAdvancementDisplay.getTexture(), i, j, 0, 0, 16, 16, 16, 16);
        } else {
            original.call(instance, context, x, y, index, stack);
        }
    }
}
