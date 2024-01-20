package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.AylythClient;
import moriyashiine.aylyth.client.model.YmpeThornRingModel;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModComponents;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class YmpeThornRingFeature extends FeatureRenderer<LivingEntity, EntityModel<LivingEntity>> {
	private static final Identifier TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/entity/living/ympe_thorn_ring.png");
	public final YmpeThornRingModel model;

	public YmpeThornRingFeature(FeatureRendererContext<LivingEntity, EntityModel<LivingEntity>> context, EntityModelLoader loader) {
		super(context);
		model = new YmpeThornRingModel(loader.getModelPart(AylythClient.YMPE_THORN_RING_MODEL_LAYER));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		int thornProgress = ModComponents.YMPE_THORNS.get(entity).getThornProgress();

		matrices.push();
		matrices.translate(0, entity.getHeight() * 0.5, -entity.getWidth() * 3);

		for(int i = 0; i < thornProgress; i++) {
			matrices.push();
			matrices.translate(0, -(entity.getHeight() * (0.25 * i)), 0);
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i % 2 == 0 ? -25 : 25));
			matrices.scale(entity.getWidth() / 0.4F, entity.getWidth() / 0.4F, entity.getWidth() / 0.4F);
			model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityAlpha(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
			matrices.pop();
		}

		matrices.pop();
	}
}
