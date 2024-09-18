package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.ElderAylythianEntityModel;
import moriyashiine.aylyth.client.render.entity.living.layer.ElderAylythianGlowLayerRenderer;
import moriyashiine.aylyth.common.entity.types.mob.ElderAylythianEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ElderAylythianEntityRenderer extends GeoEntityRenderer<ElderAylythianEntity> {
	public ElderAylythianEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ElderAylythianEntityModel());
		addRenderLayer(new ElderAylythianGlowLayerRenderer(this));
	}
}
