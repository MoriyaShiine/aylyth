package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

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

    @Override
    public void setCustomAnimations(TulpaEntity entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("tulpaHead");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}