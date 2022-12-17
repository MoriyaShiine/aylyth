package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BoneflyEntityModel extends AnimatedGeoModel<BoneflyEntity> {
    public BoneflyEntityModel() {
    }

    public Identifier getModelResource(BoneflyEntity object) {
        return new Identifier(Aylyth.MOD_ID, "geo/bonefly.geo.json");
    }

    public Identifier getTextureResource(BoneflyEntity object) {
        return new Identifier(Aylyth.MOD_ID, "textures/entity/living/bonefly/bonefly.png");
    }

    public Identifier getAnimationResource(BoneflyEntity animatable) {
        return new Identifier(Aylyth.MOD_ID, "animations/entity/bonefly.animation.json");
    }

    @Override
    public void setCustomAnimations(BoneflyEntity animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        EntityModelData extraData = (EntityModelData)animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone neck = this.getAnimationProcessor().getBone("neck");
        IBone neckJoint = this.getAnimationProcessor().getBone("neckJoint");
        if (head != null) {
            head.setRotationX(head.getRotationX() + extraData.headPitch * 3.1415927F / 540.0F);
            head.setRotationY(head.getRotationY() + extraData.netHeadYaw * 0.0058177644F);
        }

        if (neck != null) {
            neck.setRotationX(neck.getRotationX() + extraData.headPitch * 3.1415927F / 1080.0F);
            neck.setRotationY(neck.getRotationY() + extraData.netHeadYaw * 0.0058177644F);
        }

        if (neckJoint != null) {
            neckJoint.setRotationX(neckJoint.getRotationX() + extraData.headPitch * 3.1415927F / 1080.0F);
            neckJoint.setRotationY(neckJoint.getRotationY() + extraData.netHeadYaw * 0.0058177644F);
        }
    }
}
