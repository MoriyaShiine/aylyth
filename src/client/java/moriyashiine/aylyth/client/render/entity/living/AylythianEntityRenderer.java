package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.AylythianEntityModel;
import moriyashiine.aylyth.client.render.entity.living.layer.AylythAutoGlowLayer;
import moriyashiine.aylyth.common.entity.types.mob.AylythianEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AylythianEntityRenderer extends GeoEntityRenderer<AylythianEntity> {
	public AylythianEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new AylythianEntityModel());
		addRenderLayer(new AylythAutoGlowLayer<>(this));
	}
}
