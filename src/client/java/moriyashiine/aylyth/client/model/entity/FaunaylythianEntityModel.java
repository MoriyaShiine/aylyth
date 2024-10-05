package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.ElderAylythianEntity;
import moriyashiine.aylyth.common.entity.types.mob.FaunaylythianEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FaunaylythianEntityModel extends GeoModel<FaunaylythianEntity> {
    private static final Identifier MODEL_LOCATION = Aylyth.id("geo/entity/living/faunaylythian.geo.json");
    private static Identifier[] TEXTURE_LOCATIONS;
    private static final Identifier ANIMATION_FILE_LOCATION = Aylyth.id("animations/entity/living/faunaylythian.animation.json");


    @Override
    public Identifier getModelResource(FaunaylythianEntity object) {
        return MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureResource(FaunaylythianEntity object) {
        if (TEXTURE_LOCATIONS == null) {
            TEXTURE_LOCATIONS = new Identifier[ElderAylythianEntity.VARIANTS];
            for (int i = 0; i < ElderAylythianEntity.VARIANTS; i++) {
                TEXTURE_LOCATIONS[i] = Aylyth.id("textures/entity/living/faunaylythian/" + i + ".png");
            }
        }
        return TEXTURE_LOCATIONS[object.getDataTracker().get(ElderAylythianEntity.VARIANT)];
    }

    @Override
    public Identifier getAnimationResource(FaunaylythianEntity animatable) {
        return ANIMATION_FILE_LOCATION;
    }
}
