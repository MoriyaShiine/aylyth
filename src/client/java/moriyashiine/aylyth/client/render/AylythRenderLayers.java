package moriyashiine.aylyth.client.render;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class AylythRenderLayers extends RenderLayer {
    public AylythRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static net.minecraft.client.gl.ShaderProgram renderLayerSeep;
    public static final MultiPhase SEEP = of(
            "seep",
            VertexFormats.POSITION,
            VertexFormat.DrawMode.QUADS,
            256, false, false,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(new RenderPhase.ShaderProgram(() -> renderLayerSeep))
                    .texture(RenderPhase.Textures.create()
                            .add(AylythUtil.id("textures/environment/seep_0.png"), false, false)
                            .add(AylythUtil.id("textures/environment/seep_1.png"), false, false)
                            .build()
                    )
                    .build(false)
    );

    public static net.minecraft.client.gl.ShaderProgram renderLayerTint;
    public static final MultiPhase TINT = of(
            "renderlayer_tint",
            VertexFormats.POSITION_TEXTURE,
            VertexFormat.DrawMode.QUADS,
            256, false, false,
            MultiPhaseParameters.builder()
                    .program(new ShaderProgram(() -> renderLayerTint))
                    .texture(new Texture(AylythUtil.id("textures/misc/woody_growth_tint.png"), true, false))
                    .writeMaskState(COLOR_MASK)
                    .cull(DISABLE_CULLING)
                    .depthTest(EQUAL_DEPTH_TEST)
                    .transparency(GLINT_TRANSPARENCY)
                    .texturing(GLINT_TEXTURING)
                    .build(false)
    );

    public static final Function<Identifier, RenderLayer> ENTITY_NO_OUTLINE_DEPTH_FIX = Util.memoize(texture -> {
        MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
                .program(ENTITY_SOLID_PROGRAM)
                .texture(new Texture(texture, false, false))
                .cull(DISABLE_CULLING)
                .lightmap(ENABLE_LIGHTMAP)
                .overlay(ENABLE_OVERLAY_COLOR)
                .build(false);
        return of("entity_no_outline_fixed", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, false, multiPhaseParameters);
    });

    public static final Function<Identifier, RenderLayer> GLOWING_LAYER = Util.memoize(texture -> {
        MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
                .texture(new Texture(texture, false, false))
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .cull(DISABLE_CULLING).lightmap(ENABLE_LIGHTMAP)
                .overlay(DISABLE_OVERLAY_COLOR)
                .layering(VIEW_OFFSET_Z_LAYERING)
                .program(ENERGY_SWIRL_PROGRAM)
                .build(true);
        return of("glowing_layer", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, false, multiPhaseParameters);
    });
}