package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.ElderAylythianEntity;
import moriyashiine.aylyth.common.entity.mob.FaunaylythianEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FaunaylythianEntityModel extends AnimatedGeoModel<FaunaylythianEntity> {
    private static final Identifier MODEL_LOCATION = new Identifier(Aylyth.MOD_ID, "geo/faunaylythian.geo.json");
    private static Identifier[] TEXTURE_LOCATIONS;
    private static final Identifier ANIMATION_FILE_LOCATION = new Identifier(Aylyth.MOD_ID, "animations/entity/faunaylythian.animation.json");


    @Override
    public Identifier getModelResource(FaunaylythianEntity object) {
        return MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureResource(FaunaylythianEntity object) {
        if (TEXTURE_LOCATIONS == null) {
            TEXTURE_LOCATIONS = new Identifier[ElderAylythianEntity.VARIANTS];
            for (int i = 0; i < ElderAylythianEntity.VARIANTS; i++) {
                TEXTURE_LOCATIONS[i] = new Identifier(Aylyth.MOD_ID, "textures/entity/living/faunaylythian/" + i + ".png");
            }
        }
        return TEXTURE_LOCATIONS[object.getDataTracker().get(ElderAylythianEntity.VARIANT)];
    }

    @Override
    public Identifier getAnimationResource(FaunaylythianEntity animatable) {
        return ANIMATION_FILE_LOCATION;
    }
}
