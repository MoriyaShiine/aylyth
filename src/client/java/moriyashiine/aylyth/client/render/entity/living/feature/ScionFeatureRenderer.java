package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.model.entity.ScionEntityModel;
import moriyashiine.aylyth.client.render.entity.state.ScionEntityRenderState;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ScionFeatureRenderer extends FeatureRenderer<ScionEntityRenderState, BipedEntityModel<ScionEntityRenderState>> {
    public static final Identifier SCION_TEXTURE = Aylyth.id("textures/entity/living/scion.png");
    public static final Identifier OVERLAY_TEXTURE = Aylyth.id("textures/entity/living/scion_overlay.png");
    public final ScionEntityModel model;
    public final ScionEntityModel overlayModel;

    public ScionFeatureRenderer(FeatureRendererContext<ScionEntityRenderState, BipedEntityModel<ScionEntityRenderState>> context, LoadedEntityModels models) {
        super(context);
        model = new ScionEntityModel(models.getModelPart(ScionEntityModel.LAYER_LOCATION));
        overlayModel = new ScionEntityModel(models.getModelPart(ScionEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ScionEntityRenderState state, float limbAngle, float limbDistance) {
        matrices.push();
        this.getContextModel().copyTransforms(model);
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(SCION_TEXTURE)), light, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
        if (state.playerTexture != null) {
            this.getContextModel().copyTransforms(overlayModel);
            overlayModel.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(OVERLAY_TEXTURE)), light, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);

        }
        matrices.pop();
    }
}
