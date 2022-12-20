package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.client.RenderTypes;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.AylythianEntity;
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
public class AylythianGlowLayerRenderer extends GeoLayerRenderer<AylythianEntity> {
	private static final Identifier TEXTURE_LOCATION = new Identifier(Aylyth.MOD_ID, "textures/entity/living/aylythian_eyes.png");
	
	public AylythianGlowLayerRenderer(IGeoRenderer<AylythianEntity> entityRendererIn) {
		super(entityRendererIn);
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, AylythianEntity aylythian, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		//renderModel(getEntityModel(), TEXTURE_LOCATION, matrixStackIn, bufferIn, 0xF000F0, aylythian, partialTicks, 1, 1, 1);
		getRenderer().render(getEntityModel().getModel(AylythUtil.id("geo/aylythian.geo.json")),
				aylythian,
				partialTicks,
				RenderTypes.GLOWING_LAYER.apply(TEXTURE_LOCATION),
				matrixStackIn,
				bufferIn,
				bufferIn.getBuffer(RenderTypes.GLOWING_LAYER.apply(TEXTURE_LOCATION)),
				0xF000F0,
				OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
