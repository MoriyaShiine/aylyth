package moriyashiine.aylyth.client.advancement;

import moriyashiine.aylyth.common.advancement.AdvancementRendererData;
import net.minecraft.client.gui.DrawContext;

public interface AdvancementIconRenderer<T extends AdvancementRendererData> {
    static void render(DrawContext context, AdvancementRendererData data, int x, int y) {
        AdvancementIconRendererRegistry.getRenderer(data.getType()).renderIcon(context, data, x, y);
    }

    void renderIcon(DrawContext drawContext, T data, int x, int y);
}
