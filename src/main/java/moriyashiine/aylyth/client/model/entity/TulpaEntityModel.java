package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TulpaEntityModel extends AnimatedGeoModel<TulpaEntity> {


    public Identifier getModelResource(TulpaEntity object) {
        return new Identifier(Aylyth.MOD_ID, "geo/tulpa.geo.json");
    }

    public Identifier getTextureResource(TulpaEntity object) {
        return new Identifier(Aylyth.MOD_ID, "textures/entity/living/tulpa/tulpa.png");
    }

    public Identifier getAnimationResource(TulpaEntity animatable) {
        return new Identifier(Aylyth.MOD_ID, "animations/entity/tulpa.animation.json");
    }
}