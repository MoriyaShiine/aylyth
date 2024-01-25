package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class TulpaEntityModel extends GeoModel<TulpaEntity> {

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
    public void setCustomAnimations(TulpaEntity entity, long uniqueID, AnimationState<TulpaEntity> customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
        var head = this.getAnimationProcessor().getBone("head");

        var extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
        if (head != null) {
            head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }





    public static class TulpaPlayerEntityModel extends GeoModel<TulpaEntity> {

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
                PlayerEntity player = entity.getWorld().getPlayerByUuid(entity.getSkinUuid());
                if(player instanceof AbstractClientPlayerEntity abstractClientPlayerEntity){
                    return abstractClientPlayerEntity.getSkinTexture();
                }
            }
            return new Identifier("textures/entity/steve.png");
        }
    }







}