package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.ElderAylythianEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoRenderer;

public class ElderAylythianGlowLayerRenderer extends AylythAutoGlowLayer<ElderAylythianEntity> {
	private static Identifier[] TEXTURE_LOCATIONS;
	
	public ElderAylythianGlowLayerRenderer(GeoRenderer<ElderAylythianEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	protected Identifier getTextureResource(ElderAylythianEntity animatable) {
		if (TEXTURE_LOCATIONS == null) {
			TEXTURE_LOCATIONS = new Identifier[ElderAylythianEntity.VARIANTS];
			for (int i = 0; i < ElderAylythianEntity.VARIANTS; i++) {
				TEXTURE_LOCATIONS[i] = Aylyth.id("textures/entity/living/elder_aylythian/" + i + "_glowmask.png");
			}
		}
		return TEXTURE_LOCATIONS[animatable.getDataTracker().get(ElderAylythianEntity.VARIANT)];
	}
}
