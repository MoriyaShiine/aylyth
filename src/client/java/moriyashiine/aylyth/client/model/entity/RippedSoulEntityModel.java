package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.RippedSoulEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class RippedSoulEntityModel extends DefaultedEntityGeoModel<RippedSoulEntity> {
    public RippedSoulEntityModel() {
        super(Aylyth.id("living/ripped_soul"));
    }

    @Override
    public RenderLayer getRenderType(RippedSoulEntity animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucentEmissive(texture);
    }
}
