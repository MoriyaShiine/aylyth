package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TulpaEntityModel extends DefaultedEntityGeoModel<TulpaEntity> {
    public TulpaEntityModel() {
        super(Aylyth.id("living/tulpa"), true);
    }

    public static class TulpaPlayerEntityModel extends TulpaEntityModel {
        @Override
        public Identifier getTextureResource(TulpaEntity animatable, GeoRenderer<TulpaEntity> renderer) {
            return getTexture(animatable);
        }

        public Identifier getTexture(TulpaEntity entity) {
            if(entity.getSkinUuid() != null){ // TODO: Rewrite to get the gameprofile from the cache
                PlayerEntity player = entity.getWorld().getPlayerByUuid(entity.getSkinUuid());
                if(player instanceof AbstractClientPlayerEntity abstractClientPlayerEntity){
                    return abstractClientPlayerEntity.getSkinTextures().texture();
                }
            }
            return Identifier.of("textures/entity/steve.png");
        }
    }
}
