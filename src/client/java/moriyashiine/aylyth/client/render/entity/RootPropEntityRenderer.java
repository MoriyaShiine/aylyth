package moriyashiine.aylyth.client.render.entity;

import moriyashiine.aylyth.client.model.entity.RootPropEntityModel;
import moriyashiine.aylyth.client.render.entity.state.RootPropEntityRenderState;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.RootPropEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class RootPropEntityRenderer extends EntityRenderer<RootPropEntity, RootPropEntityRenderState> {
    private static final Identifier TEXTURE = Aylyth.id("textures/entity/root_prop.png");
    private final RootPropEntityModel model;

    public RootPropEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new RootPropEntityModel(context.getPart(RootPropEntityModel.LAYER_LOCATION));
    }

    @Override
    public void updateRenderState(RootPropEntity entity, RootPropEntityRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.yaw = entity.getYaw(tickDelta);
        state.pitch = entity.getPitch(tickDelta);
        state.animationProgress = entity.getAnimationProgress(tickDelta);
    }

    @Override
    public void render(RootPropEntityRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        float animProgress = state.animationProgress;
        if (animProgress != 0.0F) {
            float scale = 2.0F;
            if (animProgress > 0.9F) {
                scale *= (1.0F - animProgress) / 0.1F;
            }

            matrixStack.push();
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F - state.yaw));
            matrixStack.scale(-scale, -scale, scale);
            matrixStack.translate(0.0, -0.626, 0.0);
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(TEXTURE));
            this.model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
            matrixStack.pop();
            super.render(state, matrixStack, vertexConsumers, light);
        }
    }

    @Override
    public RootPropEntityRenderState createRenderState() {
        return new RootPropEntityRenderState();
    }
}