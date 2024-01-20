package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.AylythianEntityModel;
import moriyashiine.aylyth.client.render.entity.living.layer.AylythianGlowLayerRenderer;
import moriyashiine.aylyth.common.entity.mob.AylythianEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AylythianEntityRenderer extends GeoEntityRenderer<AylythianEntity> {
	public AylythianEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new AylythianEntityModel());
		addRenderLayer(new AylythianGlowLayerRenderer(this));
	}
	
	@Override
	public boolean shouldRender(AylythianEntity entity, Frustum frustum, double x, double y, double z) {
		return !entity.isInvisible() && super.shouldRender(entity, frustum, x, y, z);
	}
}
