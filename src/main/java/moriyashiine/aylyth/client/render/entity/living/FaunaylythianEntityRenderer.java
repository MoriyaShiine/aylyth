package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.FaunaylythianEntityModel;
import moriyashiine.aylyth.common.entity.mob.FaunaylythianEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FaunaylythianEntityRenderer extends GeoEntityRenderer<FaunaylythianEntity> {
    public FaunaylythianEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FaunaylythianEntityModel());
    }
}
