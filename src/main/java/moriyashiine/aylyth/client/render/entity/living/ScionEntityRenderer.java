package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.ScionCoreEntityModel;
import moriyashiine.aylyth.client.render.entity.living.feature.ScionFeatureRenderer;
import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ScionEntityRenderer extends BipedEntityRenderer<ScionEntity, BipedEntityModel<ScionEntity>> {

    public ScionEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ScionCoreEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0.5f);
        addFeature(new ScionFeatureRenderer(this, ctx.getModelLoader()));
        addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR))));

    }

    @Override
    public Identifier getTexture(ScionEntity mobEntity) {
        if(mobEntity.getStoredPlayerUUID() != null){
            PlayerEntity player = mobEntity.world.getPlayerByUuid(mobEntity.getStoredPlayerUUID());
            if(player instanceof AbstractClientPlayerEntity abstractClientPlayerEntity){
                return abstractClientPlayerEntity.getSkinTexture();
            }
        }
        return AylythUtil.id("textures/entity/living/scion/scion_npc_base.png");
    }
}
