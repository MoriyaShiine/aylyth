package moriyashiine.aylyth.client.render;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.mixin.client.RenderLayerAccessor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class RenderTypes extends RenderLayer {
    public RenderTypes(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static net.minecraft.client.render.Shader renderlayer_tint;
    public static final RenderLayer.MultiPhase TINT = RenderLayerAccessor.callOf(
            "renderlayer_tint",
            VertexFormats.POSITION_TEXTURE,
            VertexFormat.DrawMode.QUADS,
            256, false, false,
            RenderLayer.MultiPhaseParameters.builder()
                    .shader(new RenderPhase.Shader(() -> renderlayer_tint))
                    .texture(new RenderPhase.Texture(new Identifier(Aylyth.MOD_ID, "textures/misc/woody_growth_tint.png"), true, false))
                    .writeMaskState(RenderPhase.COLOR_MASK)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .depthTest(RenderPhase.EQUAL_DEPTH_TEST)
                    .transparency(RenderPhase.GLINT_TRANSPARENCY)
                    .texturing(RenderPhase.GLINT_TEXTURING)
                    .build(false)
    );

    public static final Function<Identifier, RenderLayer> ENTITY_NO_OUTLINE_DEPTH_FIX = Util.memoize(texture -> {
        MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
                .shader(ENTITY_SOLID_SHADER)
                .texture(new RenderPhase.Texture(texture, false, false))
                .cull(DISABLE_CULLING)
                .lightmap(ENABLE_LIGHTMAP)
                .overlay(ENABLE_OVERLAY_COLOR)
                .build(false);
        return RenderLayer.of("entity_no_outline_fixed", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, false, multiPhaseParameters);
    });

    public static final Function<Identifier, RenderLayer> GLOWING_LAYER = Util.memoize(texture -> {
        MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(Transparency.TRANSLUCENT_TRANSPARENCY)
                .cull(DISABLE_CULLING).lightmap(ENABLE_LIGHTMAP)
                .overlay(DISABLE_OVERLAY_COLOR)
                .layering(VIEW_OFFSET_Z_LAYERING)
                .shader(ENERGY_SWIRL_SHADER)
                .build(true);
        return RenderLayer.of("glowing_layer", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, false, multiPhaseParameters);
    });
}
