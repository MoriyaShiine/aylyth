package moriyashiine.aylyth.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.mixin.client.SkyRenderingAccessor;
import moriyashiine.aylyth.mixin.client.WorldRendererAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

public final class AylythSkyRenderer implements DimensionRenderingRegistry.SkyRenderer {
    private AylythSkyRenderer() {}
    public static final AylythSkyRenderer INSTANCE = new AylythSkyRenderer();
    public static final Identifier SPARKS_TEXTURE = Aylyth.id("textures/environment/sun.png");

    // [VanillaCopy] WorldRenderer::renderSky
    @Override
    public void render(WorldRenderContext context) {
        MatrixStack matrixStack = new MatrixStack();
        float tickDelta = context.tickCounter().getTickDelta(false);
        float skyAngleDegs = context.world().getSkyAngle(tickDelta);
        float rainGradientOffset = 1.0F - context.world().getRainGradient(tickDelta);
        float starBrightness = context.world().getStarBrightness(tickDelta) * rainGradientOffset;
        int l = context.world().getMoonPhase();
        int m = context.world().getSkyColor(context.camera().getPos(), tickDelta);
        float n = ColorHelper.getRedFloat(m);
        float o = ColorHelper.getGreenFloat(m);
        float p = ColorHelper.getBlueFloat(m);
        SkyRendering skyRendering = ((WorldRendererAccessor) context.worldRenderer()).getSkyRendering();
        skyRendering.renderSky(n, o, p);
        VertexConsumerProvider.Immediate immediate = (VertexConsumerProvider.Immediate) context.consumers();
        Fog fog = RenderSystem.getShaderFog();
        this.renderCelestialBodies(skyRendering, matrixStack, immediate, skyAngleDegs, l, rainGradientOffset, starBrightness, fog);
        immediate.draw();
//		if (this.isSkyDark(tickDelta)) {
//			skyRendering.renderSkyDark(matrixStack);
//		}
    }

    // [VanillaCopy] SkyRendering::renderCelestialBodies
    public void renderCelestialBodies(SkyRendering skyRendering, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, float rot, int phase, float alpha, float starBrightness, Fog fog) {
        SkyRenderingAccessor accessor = (SkyRenderingAccessor) skyRendering; 
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rot * 360.0F));
        this.renderSparks(alpha, vertexConsumers, matrices);
        vertexConsumers.draw();
        if (starBrightness > 0.0F) {
            accessor.invokeRenderStars(fog, starBrightness, matrices);
        }

        matrices.pop();
    }

    // [VanillaCopy] SkyRendering::renderSun
    public void renderSparks(float alpha, VertexConsumerProvider vertexConsumers, MatrixStack matrices) {
        float size = 20.0F;
        float distance = 100.0F;
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getCelestial(SPARKS_TEXTURE));
        int i = ColorHelper.getWhite(alpha);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        vertexConsumer.vertex(matrix4f, -size, distance, -size).texture(0.0F, 0.0F).color(i);
        vertexConsumer.vertex(matrix4f, size, distance, -size).texture(1.0F, 0.0F).color(i);
        vertexConsumer.vertex(matrix4f, size, distance, size).texture(1.0F, 1.0F).color(i);
        vertexConsumer.vertex(matrix4f, -size, distance, size).texture(0.0F, 1.0F).color(i);
    }
}
