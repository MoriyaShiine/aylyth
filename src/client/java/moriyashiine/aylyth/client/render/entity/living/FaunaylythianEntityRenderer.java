package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.FaunaylythianEntityModel;
import moriyashiine.aylyth.common.entity.types.mob.FaunaylythianEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FaunaylythianEntityRenderer extends GeoEntityRenderer<FaunaylythianEntity> {
    public FaunaylythianEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FaunaylythianEntityModel());
    }
}
