package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.client.render.RenderTypes;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.AylythianEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.example.entity.CoolKidEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class AylythianGlowLayerRenderer extends GeoRenderLayer<AylythianEntity> {
	private static final Identifier TEXTURE_LOCATION = new Identifier(Aylyth.MOD_ID, "textures/entity/living/aylythian_eyes.png");
	
	public AylythianGlowLayerRenderer(GeoRenderer<AylythianEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, AylythianEntity aylythian, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
		getRenderer().reRender(getGeoModel().getBakedModel(AylythUtil.id("geo/aylythian.geo.json")),
				matrixStackIn,
				bufferIn,
				aylythian,
				RenderTypes.GLOWING_LAYER.apply(TEXTURE_LOCATION),
				bufferIn.getBuffer(RenderTypes.GLOWING_LAYER.apply(TEXTURE_LOCATION)),
				partialTicks,
				0xF000F0,
				OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
