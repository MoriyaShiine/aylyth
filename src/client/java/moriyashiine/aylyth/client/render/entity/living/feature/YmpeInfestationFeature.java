package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.model.entity.layer.YmpeInfestationModel;
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

public class YmpeInfestationFeature extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
	private static final Identifier TEXTURE = Aylyth.id("textures/entity/living/branches_overlay.png");
	private static final YmpeInfestationModel<PlayerEntityRenderState>[] MODELS = new YmpeInfestationModel[5];

	public YmpeInfestationFeature(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context, LoadedEntityModels models) {
		super(context);
		if (MODELS[0] == null) {
			MODELS[0] = new YmpeInfestationModel<>(models.getModelPart(YmpeInfestationModel.YMPE_INFESTATION_STAGE_1_MODEL_LAYER));
			MODELS[1] = new YmpeInfestationModel<>(models.getModelPart(YmpeInfestationModel.YMPE_INFESTATION_STAGE_2_MODEL_LAYER));
			MODELS[2] = new YmpeInfestationModel<>(models.getModelPart(YmpeInfestationModel.YMPE_INFESTATION_STAGE_3_MODEL_LAYER));
			MODELS[3] = new YmpeInfestationModel<>(models.getModelPart(YmpeInfestationModel.YMPE_INFESTATION_STAGE_4_MODEL_LAYER));
			MODELS[4] = new YmpeInfestationModel<>(models.getModelPart(YmpeInfestationModel.YMPE_INFESTATION_STAGE_5_MODEL_LAYER));
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityRenderState state, float limbAngle, float limbDistance) {
		int stage = state.aylyth$infestationStage();
		if (stage > 0) {
			YmpeInfestationModel<PlayerEntityRenderState> model = MODELS[Math.min(4, stage - 1)];
			getContextModel().copyTransforms(model);
			model.adjustArmPivots(state.skinTextures.model() == SkinTextures.Model.SLIM);
			model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE)), light, OverlayTexture.DEFAULT_UV);
		}
	}
}
