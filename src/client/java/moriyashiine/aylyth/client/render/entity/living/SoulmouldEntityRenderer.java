package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.SoulmouldEntityModel;
import moriyashiine.aylyth.common.entity.types.mob.YmpemouldEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SoulmouldEntityRenderer extends GeoEntityRenderer<YmpemouldEntity> {
    public SoulmouldEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SoulmouldEntityModel());
    }

    @Override
    public  RenderLayer getRenderType(YmpemouldEntity animatable, Identifier textureLocation, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(textureLocation, true);
    }

    @Override
    public void renderRecursively(MatrixStack stack, YmpemouldEntity animatable, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int renderColor) {
        var mainHand = getAnimatable().getMainHandStack();
        if (bone.getName().equals("rightHeldItem") && !mainHand.isEmpty()) {
            stack.push();
            stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-75));
            stack.translate(0.55D, 0.35D, 1.15D);
            stack.scale(1.0f, 1.0f, 1.0f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, packedLight, packedOverlay, stack, bufferSource, null, 0);
            stack.pop();
            buffer = bufferSource.getBuffer(RenderLayer.getEntityTranslucent(getTextureLocation(animatable)));
        }
        super.renderRecursively(stack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, renderColor);
    }

    @Override
    protected float getDeathMaxRotation(YmpemouldEntity animatable, float partialTick) {
        return 0.0F;
    }
}