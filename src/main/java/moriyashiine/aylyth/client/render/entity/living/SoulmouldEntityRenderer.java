package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.SoulmouldEntityModel;
import moriyashiine.aylyth.client.render.entity.living.layer.SoulmouldEyesLayer;
import moriyashiine.aylyth.common.entity.mob.SoulmouldEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SoulmouldEntityRenderer extends GeoEntityRenderer<SoulmouldEntity> {
    public SoulmouldEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SoulmouldEntityModel());
        this.addLayer(new SoulmouldEyesLayer(this));
    }

    @Override
    public RenderLayer getRenderType(SoulmouldEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation, true);
    }


    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("rightHeldItem") && !mainHand.isEmpty()) {
            stack.push();
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-75));
            stack.translate(0.55D, 0.35D, 1.15D);
            stack.scale(1.0f, 1.0f, 1.0f);
            MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, rtb,0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(SoulmouldEntity entityLivingBaseIn) {
        return 0.0F;
    }
}