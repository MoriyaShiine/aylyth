package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WreathedHindEntityModel extends AnimatedGeoModel<WreathedHindEntity> {

    @Override
    public Identifier getModelResource(WreathedHindEntity object) {
        return new Identifier(Aylyth.MOD_ID, "geo/wreathed_hind.geo.json");
    }

    @Override
    public Identifier getTextureResource(WreathedHindEntity object) {
        return new Identifier(Aylyth.MOD_ID, "textures/entity/living/wreathed_hind/wreathed_hind.png");
    }

    @Override
    public Identifier getAnimationResource(WreathedHindEntity animatable) {
        return new Identifier(Aylyth.MOD_ID, "animations/entity/wreathed_hind.animation.json");
    }
}
