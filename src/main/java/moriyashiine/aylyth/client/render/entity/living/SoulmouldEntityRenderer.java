package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.SoulmouldEntityModel;
import moriyashiine.aylyth.client.render.entity.living.layer.SoulmouldEyesLayer;
import moriyashiine.aylyth.common.entity.mob.SoulmouldEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SoulmouldEntityRenderer extends GeoEntityRenderer<SoulmouldEntity> {
    public SoulmouldEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SoulmouldEntityModel());
        this.addLayer(new SoulmouldEyesLayer(this));
    }

    public RenderLayer getRenderType(SoulmouldEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation, true);
    }
}