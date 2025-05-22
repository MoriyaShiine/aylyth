package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.model.entity.layer.YmpeThornRingModel;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class YmpeThornRingFeature extends FeatureRenderer<LivingEntityRenderState, EntityModel<LivingEntityRenderState>> {
	private static final Identifier TEXTURE = Aylyth.id("textures/entity/living/ympe_thorn_ring.png");
	public final YmpeThornRingModel<LivingEntityRenderState> model;

	public YmpeThornRingFeature(FeatureRendererContext<LivingEntityRenderState, EntityModel<LivingEntityRenderState>> context, LoadedEntityModels models) {
		super(context);
		model = new YmpeThornRingModel<>(models.getModelPart(YmpeThornRingModel.YMPE_THORN_RING_MODEL_LAYER));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntityRenderState state, float limbAngle, float limbDistance) {
		int stage = state.aylyth$thornsStage();
		if (stage > 0) {
			matrices.push();
			matrices.translate(0, state.height * 0.5, -state.width * 3);

			for (int i = 0; i < stage; i++) {
				matrices.push();
				matrices.translate(0, -(state.height * (0.25 * i)), 0);
				matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
				matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i % 2 == 0 ? -25 : 25));
				matrices.scale(state.width / 0.4F, state.width / 0.4F, state.width / 0.4F);
				model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityAlpha(TEXTURE)), light, OverlayTexture.DEFAULT_UV);
				matrices.pop();
			}

			matrices.pop();
		}
	}
}
