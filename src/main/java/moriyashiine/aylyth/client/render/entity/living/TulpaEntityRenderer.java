package moriyashiine.aylyth.client.render.entity.living;


import moriyashiine.aylyth.client.model.entity.TulpaEntityModel;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TulpaEntityRenderer extends GeoEntityRenderer<TulpaEntity> {
    public TulpaEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new TulpaEntityModel());
    }


    public RenderLayer getRenderType(TulpaEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation, true);
    }
}