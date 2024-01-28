package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.client.render.AylythRenderLayers;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class AylythAutoGlowLayer<E extends GeoAnimatable> extends GeoRenderLayer<E> {
    public AylythAutoGlowLayer(GeoRenderer<E> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    protected Identifier getTextureResource(E animatable) {
        Identifier texture = super.getTextureResource(animatable);
        String path = texture.getPath();
        int i = path.lastIndexOf(".");
        return texture.withPath(path.substring(0, i) + "_glowmask" + path.substring(i));
    }

    @Override
    public void render(MatrixStack matrixStackIn, E entity, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
        RenderLayer renderLayer = AylythRenderLayers.GLOWING_LAYER.apply(getTextureResource(entity));
        getRenderer().reRender(getDefaultBakedModel(entity),
                matrixStackIn,
                bufferIn,
                entity,
                renderLayer,
                bufferIn.getBuffer(renderLayer),
                partialTicks,
                0xF000F0,
                OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
