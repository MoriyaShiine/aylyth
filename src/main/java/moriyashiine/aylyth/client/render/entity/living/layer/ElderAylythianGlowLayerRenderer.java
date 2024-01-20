package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.client.render.RenderTypes;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.AylythianEntity;
import moriyashiine.aylyth.common.entity.mob.ElderAylythianEntity;
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

public class ElderAylythianGlowLayerRenderer extends GeoRenderLayer<ElderAylythianEntity> {
	private static Identifier[] TEXTURE_LOCATIONS;
	
	public ElderAylythianGlowLayerRenderer(GeoRenderer<ElderAylythianEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, ElderAylythianEntity elderAylythian, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
		if (TEXTURE_LOCATIONS == null) {
			TEXTURE_LOCATIONS = new Identifier[ElderAylythianEntity.VARIANTS];
			for (int i = 0; i < ElderAylythianEntity.VARIANTS; i++) {
				TEXTURE_LOCATIONS[i] = new Identifier(Aylyth.MOD_ID, "textures/entity/living/elder_aylythian/" + i + "_eyes.png");
			}
		}
		//renderModel(getEntityModel(), TEXTURE_LOCATIONS[elderAylythian.getDataTracker().get(ElderAylythianEntity.VARIANT)], matrixStackIn, bufferIn, 0xF000F0, elderAylythian, partialTicks, 1, 1, 1);
		Identifier LAYER = TEXTURE_LOCATIONS[elderAylythian.getDataTracker().get(ElderAylythianEntity.VARIANT)];
		getRenderer().reRender(getGeoModel().getBakedModel(AylythUtil.id("geo/elder_aylythian.geo.json")),
				matrixStackIn,
				bufferIn,
				elderAylythian,
				RenderTypes.GLOWING_LAYER.apply(LAYER),
				bufferIn.getBuffer(RenderTypes.GLOWING_LAYER.apply(LAYER)),
				partialTicks,
				0xF000F0,
				OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
