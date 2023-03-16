package moriyashiine.aylyth.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.client.advancement.CustomAdvancementWidget;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementTab.class)
public abstract class AdvancementTabMixin {

    @Shadow protected abstract void addWidget(AdvancementWidget widget, Advancement advancement);

    @Shadow @Final private AdvancementDisplay display;

    @Shadow @Final private AdvancementTabType type;

    @Shadow @Final private int index;

    @Shadow @Final @Mutable private AdvancementWidget rootWidget;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void aylyth_init(MinecraftClient client, AdvancementsScreen screen, AdvancementTabType type, int index, Advancement root, AdvancementDisplay display, CallbackInfo ci) {
        if (display instanceof CustomAdvancementDisplay customAdvancementDisplay) {
            this.rootWidget = new CustomAdvancementWidget((AdvancementTab) (Object) this, client, root, customAdvancementDisplay);
            this.addWidget(this.rootWidget, root);
        }
    }

    @Inject(method = "addAdvancement", at = @At("HEAD"), cancellable = true)
    private void aylyth_addAdvancement(Advancement advancement, CallbackInfo ci) {
        if (advancement.getDisplay() instanceof CustomAdvancementDisplay customAdvancementDisplay) {
            CustomAdvancementWidget custom = new CustomAdvancementWidget((AdvancementTab) (Object) this, MinecraftClient.getInstance(), advancement, customAdvancementDisplay);
            addWidget(custom, advancement);
            ci.cancel();
        }
    }

    @Inject(method = "drawIcon", at = @At("HEAD"))
    private void aylyth_drawIcon(int x, int y, ItemRenderer itemRenderer, CallbackInfo ci) {
        if (this.display instanceof CustomAdvancementDisplay customAdvancementDisplay) {
            int i = x + this.type.getTabX(this.index);
            int j = y + this.type.getTabY(this.index);
            switch (this.type) {
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

            RenderSystem.setShaderTexture(0, customAdvancementDisplay.getTexture());
            DrawableHelper.drawTexture(new MatrixStack(), i, j, 0, 0, 16, 16, 16, 16);
        }
    }
}
