package moriyashiine.aylyth.client.advancement;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererData;
import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererDataType;
import moriyashiine.aylyth.common.advancement.renderdata.TextureRendererData;
import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererDataTypes;
import moriyashiine.aylyth.common.other.customregistry.CustomRegistries;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;

import java.util.Map;

public class AdvancementIconRendererRegistry {
    private static final Map<AdvancementRendererDataType<?>, AdvancementIconRenderer<AdvancementRendererData>> REGISTRY = new Reference2ObjectOpenHashMap<>();

    public static AdvancementIconRenderer<AdvancementRendererData> getRenderer(AdvancementRendererDataType<?> type) {
        var renderer = REGISTRY.get(type);
        if (renderer == null) {
            throw new IllegalStateException("Renderer for advancement icon data {%s} does not exist!".formatted(CustomRegistries.ADVANCEMENT_RENDERER_DATA_TYPE.getId(type)));
        }
        return renderer;
    }

    public static <T extends AdvancementRendererData> void register(AdvancementRendererDataType<T> type, AdvancementIconRenderer<T> renderer) {
        REGISTRY.put(type, (AdvancementIconRenderer<AdvancementRendererData>) renderer);
    }

    public static void init() {
        register(AdvancementRendererDataTypes.TEXTURE_RENDERER_DATA, AdvancementIconRendererRegistry::renderTexture);
    }

    private static void renderTexture(DrawContext drawContext, TextureRendererData data, int x, int y) {
        int red = (data.color() >> 24) & 255;
        int green = (data.color() >> 16) & 255;
        int blue = (data.color() >> 8) & 255;
        int alpha = data.color() & 255;

        RenderSystem.setShaderTexture(0, data.texture());
        RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(matrix4f, (float)x, (float)y, (float)0).color(red, green, blue, alpha).texture(0, 0).next();
        bufferBuilder.vertex(matrix4f, (float)x, (float)y+16, (float)0).color(red, green, blue, alpha).texture(0, 1).next();
        bufferBuilder.vertex(matrix4f, (float)x+16, (float)y+16, (float)0).color(red, green, blue, alpha).texture(1, 1).next();
        bufferBuilder.vertex(matrix4f, (float)x+16, (float)y, (float)0).color(red, green, blue, alpha).texture(1, 0).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
    }
}
