package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TulpaEntityModel extends AnimatedGeoModel<TulpaEntity> {

    public static Identifier MODEL = AylythUtil.id("geo/tulpa.geo.json");
    public static Identifier TEXTURE = AylythUtil.id("textures/entity/living/tulpa/tulpa.png");
    public static Identifier ANIMATION = AylythUtil.id("animations/entity/tulpa.animation.json");


    public Identifier getModelResource(TulpaEntity object) {
        return MODEL;
    }

    public Identifier getTextureResource(TulpaEntity object) {
        return TEXTURE;
    }

    public Identifier getAnimationResource(TulpaEntity animatable) {
        return ANIMATION;
    }

    @Override
    public void setCustomAnimations(TulpaEntity entity, int uniqueID, AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }





    @Environment(EnvType.CLIENT)
    public static class TulpaPlayerEntityModel extends AnimatedGeoModel<TulpaEntity> {

        @Override
        public Identifier getModelResource(TulpaEntity object) {
            return TulpaEntityModel.MODEL;
        }

        @Override
        public Identifier getTextureResource(TulpaEntity object) {
            return getTexture(object);
        }

        @Override
        public Identifier getAnimationResource(TulpaEntity animatable) {
            return TulpaEntityModel.ANIMATION;
        }

        public Identifier getTexture(TulpaEntity entity) {
            if(entity.getSkinUuid() != null){ // TODO: Rewrite to get the gameprofile from the cache
                PlayerEntity player = entity.world.getPlayerByUuid(entity.getSkinUuid());
                if(player instanceof AbstractClientPlayerEntity abstractClientPlayerEntity){
                    return abstractClientPlayerEntity.getSkinTexture();
                }
            }
            return new Identifier("textures/entity/steve.png");
        }
    }







}