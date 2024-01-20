package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.client.render.RenderTypes;
import moriyashiine.aylyth.common.entity.mob.AylythianEntity;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class WreathedHindGlowLayerRenderer extends GeoRenderLayer<WreathedHindEntity> {
	public WreathedHindGlowLayerRenderer(GeoRenderer<WreathedHindEntity> entityRendererIn) {
		super(entityRendererIn);
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, WreathedHindEntity wreathedHindEntity, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
		Identifier defaultTexture = AylythUtil.id("textures/entity/living/wreathed_hind/wreathed_hind_eyes.png");
		Identifier pledgedTexture = AylythUtil.id("textures/entity/living/wreathed_hind/wreathed_hind_eyes_pledged.png");
		Identifier LAYER = wreathedHindEntity.getPledgedPlayerUUIDs().isEmpty() ? defaultTexture : pledgedTexture;

		getRenderer().reRender(getGeoModel().getBakedModel(AylythUtil.id("geo/wreathed_hind.geo.json")),
				matrixStackIn,
				bufferIn,
				wreathedHindEntity,
				RenderTypes.GLOWING_LAYER.apply(LAYER),
				bufferIn.getBuffer(RenderTypes.GLOWING_LAYER.apply(LAYER)),
				partialTicks,
				0xF000F0,
				OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
