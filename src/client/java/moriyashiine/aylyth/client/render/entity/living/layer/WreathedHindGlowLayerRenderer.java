package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoRenderer;

public class WreathedHindGlowLayerRenderer extends AylythAutoGlowLayer<WreathedHindEntity> {
	public WreathedHindGlowLayerRenderer(GeoRenderer<WreathedHindEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	protected Identifier getTextureResource(WreathedHindEntity animatable) {
		if (!animatable.isPledged()) {
			return Aylyth.id("textures/entity/living/wreathed_hind_glowmask.png");
		} else {
			return Aylyth.id("textures/entity/living/wreathed_hind_pledged_glowmask.png");
		}
	}
}
