package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class BoneflyEntityModel extends GeoModel<BoneflyEntity> {
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
    public void setCustomAnimations(BoneflyEntity animatable, long instanceId, AnimationState<BoneflyEntity> animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        var extraData = animationEvent.getData(DataTickets.ENTITY_MODEL_DATA);
        var head = this.getAnimationProcessor().getBone("head");
        var neck = this.getAnimationProcessor().getBone("neck");
        var neckJoint = this.getAnimationProcessor().getBone("neckJoint");
        if (head != null) {
            head.setRotX(head.getRotX() + extraData.headPitch() * 3.1415927F / 540.0F);
            head.setRotY(head.getRotY() + extraData.netHeadYaw() * 0.0058177644F);
        }

        if (neck != null) {
            neck.setRotX(neck.getRotX() + extraData.headPitch() * 3.1415927F / 1080.0F);
            neck.setRotY(neck.getRotY() + extraData.netHeadYaw() * 0.0058177644F);
        }

        if (neckJoint != null) {
            neckJoint.setRotX(neckJoint.getRotX() + extraData.headPitch() * 3.1415927F / 1080.0F);
            neckJoint.setRotY(neckJoint.getRotY() + extraData.netHeadYaw() * 0.0058177644F);
        }
    }
}
