package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.AylythClient;
import moriyashiine.aylyth.client.model.YmpeInfestationModel;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModComponents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class YmpeInfestationFeature extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
	private static final Identifier TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/entity/living/scion_overlay.png");
	private static final YmpeInfestationModel[] MODELS = new YmpeInfestationModel[5];
	private final boolean slim;
	
	public YmpeInfestationFeature(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context, EntityModelLoader loader, boolean slim) {
		super(context);
		this.slim = slim;
		if (MODELS[0] == null) {
			MODELS[0] = new YmpeInfestationModel(loader.getModelPart(AylythClient.YMPE_INFESTATION_STAGE_1_MODEL_LAYER));
			MODELS[1] = new YmpeInfestationModel(loader.getModelPart(AylythClient.YMPE_INFESTATION_STAGE_2_MODEL_LAYER));
			MODELS[2] = new YmpeInfestationModel(loader.getModelPart(AylythClient.YMPE_INFESTATION_STAGE_3_MODEL_LAYER));
			MODELS[3] = new YmpeInfestationModel(loader.getModelPart(AylythClient.YMPE_INFESTATION_STAGE_4_MODEL_LAYER));
			MODELS[4] = new YmpeInfestationModel(loader.getModelPart(AylythClient.YMPE_INFESTATION_STAGE_5_MODEL_LAYER));
		}
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		int stage = ModComponents.YMPE_INFESTATION.get(entity).getStage();
		if (stage > 0) {
			YmpeInfestationModel model = MODELS[Math.min(4, stage - 1)];
			getContextModel().copyBipedStateTo(model);
			model.adjustArmPivots(slim);
			model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
		}
	}
}
