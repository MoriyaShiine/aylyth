package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.RippedSoulEntityModel;
import moriyashiine.aylyth.common.entity.types.mob.RippedSoulEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RippedSoulEntityRenderer extends GeoEntityRenderer<RippedSoulEntity> {
    public RippedSoulEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RippedSoulEntityModel());
    }
}
