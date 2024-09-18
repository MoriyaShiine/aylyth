package moriyashiine.aylyth.client.render.entity;

import moriyashiine.aylyth.client.model.entity.RootPropEntityModel;
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

public class RootPropEntityRenderer extends EntityRenderer<RootPropEntity> {
    private static final Identifier TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/entity/root_prop.png");
    private final RootPropEntityModel<RootPropEntity> model;

    public RootPropEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new RootPropEntityModel<>(context.getPart(RootPropEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(RootPropEntity rootPropEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float h = rootPropEntity.getAnimationProgress(g);
        if (h != 0.0F) {
            float j = 2.0F;
            if (h > 0.9F) {
                j *= (1.0F - h) / 0.1F;
            }

            matrixStack.push();
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F - rootPropEntity.getYaw()));
            matrixStack.scale(-j, -j, j);
            float k = 0.03125F;
            matrixStack.translate(0.0, -0.626, 0.0);
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            this.model.setAngles(rootPropEntity, h, 0.0F, 0.0F, rootPropEntity.getYaw(), rootPropEntity.getPitch());
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
            this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
            super.render(rootPropEntity, f, g, matrixStack, vertexConsumerProvider, i);
        }
    }

    public Identifier getTexture(RootPropEntity rootPropEntity) {
        return TEXTURE;
    }
}