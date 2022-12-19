package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.mob.WreatheredHindEntity;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WreatheredHindEntityModel extends AnimatedGeoModel<WreatheredHindEntity> {

    @Override
    public Identifier getModelResource(WreatheredHindEntity object) {
        return new Identifier(Aylyth.MOD_ID, "geo/wreathered_hind.geo.json");
    }

    @Override
    public Identifier getTextureResource(WreatheredHindEntity object) {
        return new Identifier(Aylyth.MOD_ID, "textures/entity/living/wreathered_hind/wreathered_hind.png");
    }

    @Override
    public Identifier getAnimationResource(WreatheredHindEntity animatable) {
        return new Identifier(Aylyth.MOD_ID, "animations/entity/wreathered_hind.animation.json");
    }
}
