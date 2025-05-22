package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.model.entity.layer.CuirassModel;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CuirassFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    private static final Identifier TEXTURE = Aylyth.id("textures/entity/living/ympe_cuirass.png");
    private static final CuirassModel<PlayerEntityRenderState>[] MODELS = new CuirassModel[5];

    public CuirassFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context, LoadedEntityModels models) {
        super(context);
        if (MODELS[0] == null) {
            MODELS[0] = new CuirassModel<>(models.getModelPart(CuirassModel.LAYER_LOCATION_1));
            MODELS[1] = new CuirassModel<>(models.getModelPart(CuirassModel.LAYER_LOCATION_2));
            MODELS[2] = new CuirassModel<>(models.getModelPart(CuirassModel.LAYER_LOCATION_3));
            MODELS[3] = new CuirassModel<>(models.getModelPart(CuirassModel.LAYER_LOCATION_4));
            MODELS[4] = new CuirassModel<>(models.getModelPart(CuirassModel.LAYER_LOCATION_5));
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        Integer stage = state.aylyth$cuirassStage();
        if (stage > 0) {
            CuirassModel<PlayerEntityRenderState> model;
            model = switch (stage) {
                case Integer ignored when stage < 5 -> MODELS[0];
                case Integer ignored when stage < 10 -> MODELS[1];
                case Integer ignored when stage < 15 -> MODELS[2];
                case Integer ignored when stage < 20 -> MODELS[3];
                default -> MODELS[4];
            };
            getContextModel().copyTransforms(model);
            model.adjustArmPivots(state.skinTextures.model() == SkinTextures.Model.SLIM);
            model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE)), light, OverlayTexture.DEFAULT_UV);
        }
    }
}
