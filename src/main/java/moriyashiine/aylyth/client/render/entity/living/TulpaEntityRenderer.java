package moriyashiine.aylyth.client.render.entity.living;


import moriyashiine.aylyth.client.model.entity.TulpaEntityModel;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class TulpaEntityRenderer extends GeoEntityRenderer<TulpaEntity> {
    private TulpaEntity.TulpaPlayerEntity tulpaPlayerEntity;
    protected PlayerEntityModel model;
    public TulpaEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new TulpaEntityModel());
        this.model = new PlayerEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER), false);

    }

    @Override
    public void render(TulpaEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider bufferIn, int packedLightIn) {
        if(entity.getSkinUuid() == null || entity.getDataTracker().get(TulpaEntity.TRANSFORMING)){
            super.render(entity, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
        }else{
            matrixStack.push();
            if(tulpaPlayerEntity == null) {
                tulpaPlayerEntity = ModEntityTypes.TULPA_PLAYER.create(entity.world);
                assert tulpaPlayerEntity != null;
            }
            tulpaPlayerEntity.age = entity.age;
            tulpaPlayerEntity.hurtTime = entity.hurtTime;
            tulpaPlayerEntity.maxHurtTime = Integer.MAX_VALUE;
            tulpaPlayerEntity.limbDistance = entity.limbDistance;
            tulpaPlayerEntity.lastLimbDistance = entity.lastLimbDistance;
            tulpaPlayerEntity.limbAngle = entity.limbAngle;
            tulpaPlayerEntity.headYaw = entity.headYaw;
            tulpaPlayerEntity.prevHeadYaw = entity.prevHeadYaw;
            tulpaPlayerEntity.bodyYaw = entity.bodyYaw;
            tulpaPlayerEntity.prevBodyYaw = entity.prevBodyYaw;
            tulpaPlayerEntity.handSwinging = entity.handSwinging;
            tulpaPlayerEntity.handSwingTicks = entity.handSwingTicks;
            tulpaPlayerEntity.handSwingProgress = entity.handSwingProgress;
            tulpaPlayerEntity.lastHandSwingProgress = entity.lastHandSwingProgress;
            tulpaPlayerEntity.setPitch(entity.getPitch());
            tulpaPlayerEntity.prevPitch = entity.prevPitch;
            tulpaPlayerEntity.preferredHand = entity.preferredHand;
            tulpaPlayerEntity.setStackInHand(Hand.MAIN_HAND, entity.getMainHandStack());
            tulpaPlayerEntity.setStackInHand(Hand.OFF_HAND, entity.getOffHandStack());
            tulpaPlayerEntity.equipStack(EquipmentSlot.HEAD, entity.getEquippedStack(EquipmentSlot.HEAD));
            tulpaPlayerEntity.equipStack(EquipmentSlot.CHEST, entity.getEquippedStack(EquipmentSlot.CHEST));
            tulpaPlayerEntity.equipStack(EquipmentSlot.LEGS, entity.getEquippedStack(EquipmentSlot.LEGS));
            tulpaPlayerEntity.equipStack(EquipmentSlot.FEET, entity.getEquippedStack(EquipmentSlot.FEET));
            tulpaPlayerEntity.setCurrentHand(entity.getActiveHand() == null ? Hand.MAIN_HAND : entity.getActiveHand());
            tulpaPlayerEntity.setSneaking(entity.isSneaking());
            tulpaPlayerEntity.isSneaking();
            tulpaPlayerEntity.forwardSpeed=entity.forwardSpeed;
            tulpaPlayerEntity.setPose(entity.getPose());
            tulpaPlayerEntity.setSprinting(entity.isSprinting());
            tulpaPlayerEntity.setSkinUuid(entity.getSkinUuid());
            tulpaPlayerEntity.setCustomName(entity.getCustomName());
            tulpaPlayerEntity.setUsingItem(entity.getItemUseTime() > 0);

            MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(tulpaPlayerEntity).render(tulpaPlayerEntity, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
            matrixStack.pop();
        }
    }

    public RenderLayer getRenderType(TulpaEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation, true);
    }


    @Override
    public void renderEarly(TulpaEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.rtb = renderTypeBuffer;
        this.whTexture = this.getTextureResource(animatable);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }



    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("rightItem") && !mainHand.isEmpty()) {
            stack.push();
            stack.translate(0.25,0.4,0.05);
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
            MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND,
                    packedLightIn, packedOverlayIn, stack, rtb,0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }else if (bone.getName().equals("leftItem") && !offHand.isEmpty()) {
            stack.push();
            stack.translate(-0.25,0.4,0.05);
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
            stack.scale(1.0f, 1.0f, 1.0f);
            if(offHand.isOf(Items.SHIELD)){
                stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
                stack.translate(0,0.2,-1.4);
            }
            MinecraftClient.getInstance().getItemRenderer().renderItem(offHand, ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND,
                    packedLightIn, packedOverlayIn, stack, this.rtb, 0);
            stack.pop();
            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}