package moriyashiine.aylyth.client.renderer.entity.living;

import moriyashiine.aylyth.client.model.entity.ElderAylythianEntityModel;
import moriyashiine.aylyth.client.renderer.entity.living.layer.ElderAylythianGlowLayerRenderer;
import moriyashiine.aylyth.common.entity.mob.ElderAylythianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class ElderAylythianEntityRenderer extends GeoEntityRenderer<ElderAylythianEntity> {
	public ElderAylythianEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ElderAylythianEntityModel());
		addLayer(new ElderAylythianGlowLayerRenderer(this));
	}
	
	@Override
	public boolean shouldRender(ElderAylythianEntity entity, Frustum frustum, double x, double y, double z) {
		return !entity.isInvisible() && super.shouldRender(entity, frustum, x, y, z);
	}
}
