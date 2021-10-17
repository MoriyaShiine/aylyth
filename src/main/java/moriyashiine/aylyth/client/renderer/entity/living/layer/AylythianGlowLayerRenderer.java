package moriyashiine.aylyth.client.renderer.entity.living.layer;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.AylythianEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class AylythianGlowLayerRenderer extends GeoLayerRenderer<AylythianEntity> {
	private static final Identifier TEXTURE_LOCATION = new Identifier(Aylyth.MOD_ID, "textures/entity/aylythian/glow.png");
	public AylythianGlowLayerRenderer(IGeoRenderer<AylythianEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, AylythianEntity aylythian, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		renderModel((GeoModelProvider<AylythianEntity>) getEntityModel(), TEXTURE_LOCATION, matrixStackIn, bufferIn, 0xf000f0, aylythian, partialTicks, 1, 1, 1);
	}
}
