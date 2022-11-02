package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.model.entity.ScionEntityModel;
import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ScionFeatureRenderer extends FeatureRenderer<ScionEntity, BipedEntityModel<ScionEntity>> {
    public final ScionEntityModel<ScionEntity> MODEL;

    public ScionFeatureRenderer(FeatureRendererContext<ScionEntity, BipedEntityModel<ScionEntity>> context, EntityModelLoader loader) {
        super(context);
        MODEL = new ScionEntityModel<>(loader.getModelPart(ScionEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ScionEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        this.getContextModel().setAttributes(MODEL);
        MODEL.child = false;
        MODEL.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(this.getPlayerSkinTexture())), light, OverlayTexture.DEFAULT_UV, 1,1,1,1);
        matrices.pop();
    }

    public Identifier getPlayerSkinTexture(){
        return AylythUtil.id("textures/entity/living/scion/scion.png");
    }
}
