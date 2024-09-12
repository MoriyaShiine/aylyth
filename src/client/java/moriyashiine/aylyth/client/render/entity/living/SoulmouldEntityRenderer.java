package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.SoulmouldEntityModel;
import moriyashiine.aylyth.common.entity.type.mob.SoulmouldEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SoulmouldEntityRenderer extends GeoEntityRenderer<SoulmouldEntity> {
    public SoulmouldEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SoulmouldEntityModel());
    }

    @Override
    public  RenderLayer getRenderType(SoulmouldEntity animatable, Identifier textureLocation, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(textureLocation, true);
    }


    @Override
    public void renderRecursively(MatrixStack stack, SoulmouldEntity animatable, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer bufferIn, boolean isReRender, float partialTick, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        var mainHand = getAnimatable().getMainHandStack();
        if (bone.getName().equals("rightHeldItem") && !mainHand.isEmpty()) {
            stack.push();
            stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-75));
            stack.translate(0.55D, 0.35D, 1.15D);
            stack.scale(1.0f, 1.0f, 1.0f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, bufferSource, null, 0);
            stack.pop();
            bufferIn = bufferSource.getBuffer(RenderLayer.getEntityTranslucent(getTexture(animatable)));
        }
        super.renderRecursively(stack, animatable, bone, renderType, bufferSource, bufferIn, isReRender, partialTick, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(SoulmouldEntity entityLivingBaseIn) {
        return 0.0F;
    }
}