package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class TulpaEntityModel extends DefaultedEntityGeoModel<TulpaEntity> {
    public TulpaEntityModel() {
        super(AylythUtil.id("living/tulpa"), true);
    }

    public static class TulpaPlayerEntityModel extends TulpaEntityModel {

        @Override
        public Identifier getTextureResource(TulpaEntity object) {
            return getTexture(object);
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