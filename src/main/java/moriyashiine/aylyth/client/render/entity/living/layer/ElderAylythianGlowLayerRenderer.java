package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.ElderAylythianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@Environment(EnvType.CLIENT)
public class ElderAylythianGlowLayerRenderer extends GeoLayerRenderer<ElderAylythianEntity> {
	private static Identifier[] TEXTURE_LOCATIONS;
	
	public ElderAylythianGlowLayerRenderer(IGeoRenderer<ElderAylythianEntity> entityRendererIn) {
		super(entityRendererIn);
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, ElderAylythianEntity elderAylythian, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (TEXTURE_LOCATIONS == null) {
			TEXTURE_LOCATIONS = new Identifier[ElderAylythianEntity.VARIANTS];
			for (int i = 0; i < ElderAylythianEntity.VARIANTS; i++) {
				TEXTURE_LOCATIONS[i] = new Identifier(Aylyth.MOD_ID, "textures/entity/living/elder_aylythian/" + i + "_eyes.png");
			}
		}
		renderModel(getEntityModel(), TEXTURE_LOCATIONS[elderAylythian.getDataTracker().get(ElderAylythianEntity.VARIANT)], matrixStackIn, bufferIn, 0xF000F0, elderAylythian, partialTicks, 1, 1, 1);
	}
}
