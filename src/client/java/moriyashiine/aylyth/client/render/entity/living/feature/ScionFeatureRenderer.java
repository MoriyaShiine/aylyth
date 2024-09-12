package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.model.entity.ScionEntityModel;
import moriyashiine.aylyth.common.entity.type.mob.ScionEntity;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ScionFeatureRenderer extends FeatureRenderer<ScionEntity, BipedEntityModel<ScionEntity>> {
    public final ScionEntityModel<ScionEntity> MODEL;
    public final ScionEntityModel<ScionEntity> OVERLAY_MODEL;

    public ScionFeatureRenderer(FeatureRendererContext<ScionEntity, BipedEntityModel<ScionEntity>> context, EntityModelLoader loader) {
        super(context);
        MODEL = new ScionEntityModel<>(loader.getModelPart(ScionEntityModel.LAYER_LOCATION));
        OVERLAY_MODEL = new ScionEntityModel<>(loader.getModelPart(ScionEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ScionEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        this.getContextModel().copyBipedStateTo(MODEL);
        MODEL.child = false;
        MODEL.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(this.getScionTexture())), light, OverlayTexture.DEFAULT_UV, 1,1,1,1);
        if(entity.getStoredPlayerUUID() != null){
            this.getContextModel().copyBipedStateTo(OVERLAY_MODEL);
            OVERLAY_MODEL.child = false;
            OVERLAY_MODEL.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(AylythUtil.id("textures/entity/living/scion_overlay.png"))), light, OverlayTexture.DEFAULT_UV, 1,1,1,1);

        }
        matrices.pop();
    }

    public Identifier getScionTexture(){
        return AylythUtil.id("textures/entity/living/scion.png");
    }
}
