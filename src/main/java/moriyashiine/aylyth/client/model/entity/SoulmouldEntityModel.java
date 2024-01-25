package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.SoulmouldEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class SoulmouldEntityModel extends GeoModel<SoulmouldEntity> {
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

    @Override
    public void setCustomAnimations(SoulmouldEntity animatable, long instanceId, AnimationState<SoulmouldEntity> animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        var head = this.getAnimationProcessor().getBone("head");
        var extraData = animationEvent.getData(DataTickets.ENTITY_MODEL_DATA);
        if (head != null) {
            head.setRotX(head.getRotX() + extraData.headPitch() * 3.1415927F / 180.0F);
            head.setRotY(head.getRotY() + extraData.netHeadYaw() * 0.017453292F);
        }
    }
}