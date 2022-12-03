package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.SoulmouldEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SoulmouldEntityModel extends AnimatedTickingGeoModel<SoulmouldEntity> {
    public SoulmouldEntityModel() {
    }

    public Identifier getModelResource(SoulmouldEntity object) {
        return new Identifier(Aylyth.MOD_ID, "geo/soulmould.geo.json");
    }

    public Identifier getTextureResource(SoulmouldEntity object) {
        return new Identifier(Aylyth.MOD_ID, "textures/entity/living/mould/ympemould.png");
    }

    public Identifier getAnimationResource(SoulmouldEntity animatable) {
        return new Identifier(Aylyth.MOD_ID, "animations/entity/soulmould.animation.json");
    }

    public void codeAnimations(SoulmouldEntity entity, Integer uniqueID, AnimationEvent<?> customPredicate) {
        super.codeAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData)customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(head.getRotationX() + extraData.headPitch * 3.1415927F / 180.0F);
            head.setRotationY(head.getRotationY() + extraData.netHeadYaw * 0.017453292F);
        }

    }
}