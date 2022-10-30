package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.model.entity.ScionEntityModel;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ScionFeatureRenderer extends FeatureRenderer<ScionEntity, ScionEntityModel<ScionEntity>> {
    public final BipedEntityModel MODEL;

    public ScionFeatureRenderer(FeatureRendererContext<ScionEntity, ScionEntityModel<ScionEntity>> context, EntityModelLoader loader) {
        super(context);
        MODEL = new BipedEntityModel(loader.getModelPart(EntityModelLayers.PLAYER));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ScionEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        MODEL.child = false;
        MODEL.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(this.getPlayerSkinTexture(entity))), light, OverlayTexture.DEFAULT_UV, 1,1,1,1);
        matrices.pop();
    }

    public Identifier getPlayerSkinTexture(ScionEntity scion){
        if(ScionEntity.getStoredPlayerUUID() != null){
            PlayerEntity player = scion.world.getPlayerByUuid(ScionEntity.getStoredPlayerUUID());
            if(player instanceof AbstractClientPlayerEntity abstractClientPlayerEntity){
                return abstractClientPlayerEntity.getSkinTexture();
            }else{
                return DefaultSkinHelper.getTexture(scion.getUuid());
            }
        }
        return DefaultSkinHelper.getTexture(scion.getUuid());
    }
}
