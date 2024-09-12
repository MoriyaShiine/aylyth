package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.common.entity.type.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoRenderer;

public class WreathedHindGlowLayerRenderer extends AylythAutoGlowLayer<WreathedHindEntity> {
	public WreathedHindGlowLayerRenderer(GeoRenderer<WreathedHindEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	protected Identifier getTextureResource(WreathedHindEntity animatable) {
		if (!animatable.isPledged()) {
			return AylythUtil.id("textures/entity/living/wreathed_hind_glowmask.png");
		} else {
			return AylythUtil.id("textures/entity/living/wreathed_hind_pledged_glowmask.png");
		}
	}
}
