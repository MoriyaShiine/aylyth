package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.ElderAylythianEntityModel;
import moriyashiine.aylyth.client.render.entity.living.layer.ElderAylythianGlowLayerRenderer;
import moriyashiine.aylyth.common.entity.mob.ElderAylythianEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ElderAylythianEntityRenderer extends GeoEntityRenderer<ElderAylythianEntity> {
	public ElderAylythianEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ElderAylythianEntityModel());
		addRenderLayer(new ElderAylythianGlowLayerRenderer(this));
	}
	
	@Override
	public boolean shouldRender(ElderAylythianEntity entity, Frustum frustum, double x, double y, double z) {
		return !entity.isInvisible() && super.shouldRender(entity, frustum, x, y, z);
	}
}
