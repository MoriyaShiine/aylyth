package moriyashiine.aylyth.client.renderer.entity.living;

import moriyashiine.aylyth.client.model.entity.AylythianEntityModel;
import moriyashiine.aylyth.common.entity.AylythianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class AylythianEntityRenderer extends GeoEntityRenderer<AylythianEntity> {
	public AylythianEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new AylythianEntityModel());
	}
}
