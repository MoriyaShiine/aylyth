package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.WreatheredHindEntityModel;
import moriyashiine.aylyth.common.entity.mob.WreatheredHindEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WreatheredHindEntityRenderer extends GeoEntityRenderer<WreatheredHindEntity> {
    public WreatheredHindEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new WreatheredHindEntityModel());
    }
}
