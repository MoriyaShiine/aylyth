package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.client.RenderTypes;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@Environment(EnvType.CLIENT)
public class WreathedHindGlowLayerRenderer extends GeoLayerRenderer<WreathedHindEntity> {
	public WreathedHindGlowLayerRenderer(IGeoRenderer<WreathedHindEntity> entityRendererIn) {
		super(entityRendererIn);
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, WreathedHindEntity wreathedHindEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		Identifier defaultTexture = AylythUtil.id("textures/entity/living/wreathed_hind/wreathed_hind_eyes.png");
		Identifier pledgedTexture = AylythUtil.id("textures/entity/living/wreathed_hind/wreathed_hind_eyes_pledged.png");
		Identifier LAYER = wreathedHindEntity.getPledgedPlayerUUIDs().isEmpty() ? defaultTexture : pledgedTexture;

		getRenderer().render(getEntityModel().getModel(AylythUtil.id("geo/wreathed_hind.geo.json")),
				wreathedHindEntity,
				partialTicks,
				RenderTypes.GLOWING_LAYER.apply(LAYER),
				matrixStackIn,
				bufferIn,
				bufferIn.getBuffer(RenderTypes.GLOWING_LAYER.apply(LAYER)),
				0xF000F0,
				OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
