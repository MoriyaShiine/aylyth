package moriyashiine.aylyth.client.advancement;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import moriyashiine.aylyth.mixin.client.AdvancementWidgetAccessor;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementObtainedStatus;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;

public class CustomAdvancementWidget extends AdvancementWidget {

    public CustomAdvancementWidget(AdvancementTab tab, MinecraftClient client, Advancement advancement, CustomAdvancementDisplay display) {
        super(tab, client, advancement, display);
    }

    // [VanillaCopy]
    @Override
    public void renderWidgets(DrawContext matrices, int x, int y) {
        AdvancementWidgetAccessor accessor = (AdvancementWidgetAccessor)this;
        if (!accessor.getDisplay().isHidden() || accessor.getProgress() != null && accessor.getProgress().isDone()) {
            CustomAdvancementDisplay display = (CustomAdvancementDisplay)accessor.getDisplay();
            float f = accessor.getProgress() == null ? 0.0F : accessor.getProgress().getProgressBarPercentage();
            AdvancementObtainedStatus advancementObtainedStatus;
            if (f >= 1.0F) {
                advancementObtainedStatus = AdvancementObtainedStatus.OBTAINED;
            } else {
                advancementObtainedStatus = AdvancementObtainedStatus.UNOBTAINED;
            }

            matrices.drawTexture(AdvancementWidgetAccessor.getWidgetsTexture(), x + accessor.getX() + 3, y + accessor.getY(), display.getFrame().getTextureV(), 128 + advancementObtainedStatus.getSpriteIndex() * 26, 26, 26);
            matrices.drawTexture(display.getTexture(), x + accessor.getX() + 8, y + accessor.getY() + 5, 0, 0, 16, 16, 16, 16);
        }

        for(AdvancementWidget advancementWidget : accessor.getChildren()) {
            advancementWidget.renderWidgets(matrices, x, y);
        }
    }

    // [VanillaCopy]
    @Override
    public void drawTooltip(DrawContext matrices, int originX, int originY, float alpha, int x, int y) {
        AdvancementWidgetAccessor accessor = (AdvancementWidgetAccessor)this;
        CustomAdvancementDisplay display = (CustomAdvancementDisplay)accessor.getDisplay();

        boolean bl = x + originX + accessor.getX() + accessor.getWidth() + 26 >= accessor.getTab().getScreen().width;
        String string = accessor.getProgress() == null ? null : accessor.getProgress().getProgressBarFraction();
        int i = string == null ? 0 : accessor.getClient().textRenderer.getWidth(string);
        boolean bl2 = 113 - originY - accessor.getY() - 26 <= 6 + accessor.getDescription().size() * 9;
        float f = accessor.getProgress() == null ? 0.0F : accessor.getProgress().getProgressBarPercentage();
        int j = MathHelper.floor(f * (float)accessor.getWidth());
        AdvancementObtainedStatus advancementObtainedStatus;
        AdvancementObtainedStatus advancementObtainedStatus2;
        AdvancementObtainedStatus advancementObtainedStatus3;
        if (f >= 1.0F) {
            j = accessor.getWidth() / 2;
            advancementObtainedStatus = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus2 = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus3 = AdvancementObtainedStatus.OBTAINED;
        } else if (j < 2) {
            j = accessor.getWidth() / 2;
            advancementObtainedStatus = AdvancementObtainedStatus.UNOBTAINED;
            advancementObtainedStatus2 = AdvancementObtainedStatus.UNOBTAINED;
            advancementObtainedStatus3 = AdvancementObtainedStatus.UNOBTAINED;
        } else if (j > accessor.getWidth() - 2) {
            j = accessor.getWidth() / 2;
            advancementObtainedStatus = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus2 = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus3 = AdvancementObtainedStatus.UNOBTAINED;
        } else {
            advancementObtainedStatus = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus2 = AdvancementObtainedStatus.UNOBTAINED;
            advancementObtainedStatus3 = AdvancementObtainedStatus.UNOBTAINED;
        }

        int k = accessor.getWidth() - j;
        RenderSystem.enableBlend();
        int l = originY + accessor.getY();
        int m;
        if (bl) {
            m = originX + accessor.getX() - accessor.getWidth() + 26 + 6;
        } else {
            m = originX + accessor.getX();
        }

        int n = 32 + accessor.getDescription().size() * 9;
        if (!accessor.getDescription().isEmpty()) {
            if (bl2) {
                matrices.drawNineSlicedTexture(AdvancementWidgetAccessor.getWidgetsTexture(), m, l + 26 - n, accessor.getWidth(), n, 10, 200, 26, 0, 52);
            } else {
                matrices.drawNineSlicedTexture(AdvancementWidgetAccessor.getWidgetsTexture(), m, l, accessor.getWidth(), n, 10, 200, 26, 0, 52);
            }
        }

        matrices.drawTexture(AdvancementWidgetAccessor.getWidgetsTexture(), m, l, 0, advancementObtainedStatus.getSpriteIndex() * 26, j, 26);
        matrices.drawTexture(AdvancementWidgetAccessor.getWidgetsTexture(), m + j, l, 200 - k, advancementObtainedStatus2.getSpriteIndex() * 26, k, 26);
        matrices.drawTexture(AdvancementWidgetAccessor.getWidgetsTexture(), originX + accessor.getX() + 3, originY + accessor.getY(), display.getFrame().getTextureV(), 128 + advancementObtainedStatus3.getSpriteIndex() * 26, 26, 26);
        if (bl) {
            matrices.drawTextWithShadow(accessor.getClient().textRenderer, accessor.getTitle(), m + 5, originY + accessor.getY() + 9, -1);
            if (string != null) {
                matrices.drawTextWithShadow(accessor.getClient().textRenderer, string, originX + accessor.getX() - i, originY + accessor.getY() + 9, -1);
            }
        } else {
            matrices.drawTextWithShadow(accessor.getClient().textRenderer, accessor.getTitle(), originX + accessor.getX() + 32, originY + accessor.getY() + 9, -1);
            if (string != null) {
                matrices.drawTextWithShadow(accessor.getClient().textRenderer, string, originX + accessor.getX() + accessor.getWidth() - i - 5, originY + accessor.getY() + 9, -1);
            }
        }

        if (bl2) {
            for(int o = 0; o < accessor.getDescription().size(); ++o) {
                matrices.drawText(accessor.getClient().textRenderer, accessor.getDescription().get(o), m + 5, l + 26 - n + 7 + o * 9, -5592406, false);
            }
        } else {
            for(int o = 0; o < accessor.getDescription().size(); ++o) {
                matrices.drawText(accessor.getClient().textRenderer, accessor.getDescription().get(o), m + 5, originY + accessor.getY() + 9 + 17 + o * 9, -5592406, false);
            }
        }

        matrices.drawTexture(display.getTexture(), originX + accessor.getX() + 8, originY + accessor.getY() + 5, 0, 0, 16, 16, 16, 16);
    }
}
