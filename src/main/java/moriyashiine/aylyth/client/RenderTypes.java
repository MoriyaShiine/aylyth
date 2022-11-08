package moriyashiine.aylyth.client;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.mixin.client.RenderLayerAccessor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

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
}
