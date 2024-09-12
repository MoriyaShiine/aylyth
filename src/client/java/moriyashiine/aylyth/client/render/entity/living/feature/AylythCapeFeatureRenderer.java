package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.common.entity.type.mob.TulpaEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class AylythCapeFeatureRenderer  extends FeatureRenderer<TulpaEntity.TulpaPlayerEntity, BipedEntityModel<TulpaEntity.TulpaPlayerEntity>> {
    public PlayerEntityModel playerEntityModel;
    public AylythCapeFeatureRenderer(FeatureRendererContext<TulpaEntity.TulpaPlayerEntity, BipedEntityModel<TulpaEntity.TulpaPlayerEntity>> featureRendererContext, EntityModelLoader loader) {
        super(featureRendererContext);
        playerEntityModel = new PlayerEntityModel(loader.getModelPart(EntityModelLayers.PLAYER), false);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, TulpaEntity.TulpaPlayerEntity tulpaPlayerEntity, float f, float g, float h, float j, float k, float l) {
        if(tulpaPlayerEntity.getSkinUuid() != null){
            PlayerEntity playerEntity = tulpaPlayerEntity.getWorld().getPlayerByUuid(tulpaPlayerEntity.getSkinUuid());
            if(playerEntity instanceof AbstractClientPlayerEntity clientPlayer){
                if (clientPlayer.canRenderCapeTexture() && !tulpaPlayerEntity.isInvisible() && clientPlayer.getCapeTexture() != null) {
                    ItemStack itemStack = tulpaPlayerEntity.getEquippedStack(EquipmentSlot.CHEST);
                    if (!itemStack.isOf(Items.ELYTRA)) {
                        matrixStack.push();
                        matrixStack.translate(0.0, 0.0, 0.125);
                        double d = MathHelper.lerp((double)h, tulpaPlayerEntity.prevCapeX, tulpaPlayerEntity.capeX)
                                - MathHelper.lerp((double)h, tulpaPlayerEntity.prevX, tulpaPlayerEntity.getX());
                        double e = MathHelper.lerp((double)h, tulpaPlayerEntity.prevCapeY, tulpaPlayerEntity.capeY)
                                - MathHelper.lerp((double)h, tulpaPlayerEntity.prevY, tulpaPlayerEntity.getY());
                        double m = MathHelper.lerp((double)h, tulpaPlayerEntity.prevCapeZ, tulpaPlayerEntity.capeZ)
                                - MathHelper.lerp((double)h, tulpaPlayerEntity.prevZ, tulpaPlayerEntity.getZ());
                        float n = tulpaPlayerEntity.prevBodyYaw + (tulpaPlayerEntity.bodyYaw - tulpaPlayerEntity.prevBodyYaw);
                        double o = (double)MathHelper.sin(n * (float) (Math.PI / 180.0));
                        double p = (double)(-MathHelper.cos(n * (float) (Math.PI / 180.0)));
                        float q = (float)e * 10.0F;
                        q = MathHelper.clamp(q, -6.0F, 32.0F);
                        float r = (float)(d * o + m * p) * 100.0F;
                        r = MathHelper.clamp(r, 0.0F, 150.0F);
                        float s = (float)(d * p - m * o) * 100.0F;
                        s = MathHelper.clamp(s, -20.0F, 20.0F);
                        if (r < 0.0F) {
                            r = 0.0F;
                        }

                        float t = MathHelper.lerp(h, tulpaPlayerEntity.prevStrideDistance, tulpaPlayerEntity.strideDistance);
                        q += MathHelper.sin(MathHelper.lerp(h, tulpaPlayerEntity.prevHorizontalSpeed, tulpaPlayerEntity.horizontalSpeed) * 6.0F) * 32.0F * t;
                        if (tulpaPlayerEntity.isInSneakingPose()) {
                            q += 25.0F;
                        }

                        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(6.0F + r / 2.0F + q));
                        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(s / 2.0F));
                        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - s / 2.0F));
                        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(clientPlayer.getCapeTexture()));
                        playerEntityModel.renderCape(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
                        matrixStack.pop();
                    }
                }
            }
        }
    }
}
