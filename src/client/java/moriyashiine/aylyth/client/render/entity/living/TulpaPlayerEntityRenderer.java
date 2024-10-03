package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.render.entity.living.feature.AylythCapeFeatureRenderer;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity.TulpaPlayerEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckStingersFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;

public class TulpaPlayerEntityRenderer extends LivingEntityRenderer<TulpaPlayerEntity, BipedEntityModel<TulpaPlayerEntity>> {
    private final BipedEntityModel normalModel;
    private final BipedEntityModel slimModel;
    public TulpaPlayerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0F);
        this.normalModel = this.getModel();
        this.slimModel = new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_SLIM));
        this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new BipedEntityModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
        this.addFeature(new PlayerHeldItemFeatureRenderer(this, ctx.getHeldItemRenderer()));
        this.addFeature(new StuckArrowsFeatureRenderer(ctx, this));
        this.addFeature(new HeadFeatureRenderer<>(this, ctx.getModelLoader(), ctx.getHeldItemRenderer()));
        this.addFeature(new ElytraFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new StuckStingersFeatureRenderer(this));
        this.addFeature(new AylythCapeFeatureRenderer(this, ctx.getModelLoader()));
    }

    @Override
    public void render(TulpaPlayerEntity livingEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        if(livingEntity.getSkinUuid() != null){
            PlayerEntity player = livingEntity.getWorld().getPlayerByUuid(livingEntity.getSkinUuid());

            if (player instanceof AbstractClientPlayerEntity && DefaultSkinHelper.getModel(player.getUuid()).equals("slim")) {
                this.model = slimModel;
            } else {
                this.model = normalModel;
            }
        }

        this.setModelPose(livingEntity);
        super.render(livingEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    private void setModelPose(TulpaPlayerEntity livingEntity) {
        BipedEntityModel.ArmPose armPose = getArmPose(livingEntity, Hand.MAIN_HAND);
        BipedEntityModel.ArmPose armPose2 = getArmPose(livingEntity, Hand.OFF_HAND);
        if (livingEntity.getMainArm() == Arm.RIGHT) {
            normalModel.rightArmPose = armPose;
            normalModel.leftArmPose = armPose2;
            slimModel.rightArmPose = armPose;
            slimModel.leftArmPose = armPose2;
        } else {
            normalModel.rightArmPose = armPose2;
            normalModel.leftArmPose = armPose;
            slimModel.rightArmPose = armPose2;
            slimModel.leftArmPose = armPose;
        }
    }

    private static BipedEntityModel.ArmPose getArmPose(TulpaPlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            return BipedEntityModel.ArmPose.EMPTY;
        } else {
            if (player.getActiveHand() == hand && player.getItemUseTimeLeft() > 0) {
                UseAction useAction = itemStack.getUseAction();
                if (useAction == UseAction.BLOCK || (player.isUsingItem() && player.getMainHandStack().isOf(Items.SHIELD))) {
                    return BipedEntityModel.ArmPose.BLOCK;
                }

                if (useAction == UseAction.BOW) {
                    return BipedEntityModel.ArmPose.BOW_AND_ARROW;
                }

                if (useAction == UseAction.SPEAR) {
                    return BipedEntityModel.ArmPose.THROW_SPEAR;
                }

                if (useAction == UseAction.CROSSBOW && hand == player.getActiveHand()) {
                    return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
                }

                if (useAction == UseAction.SPYGLASS) {
                    return BipedEntityModel.ArmPose.SPYGLASS;
                }

                if (useAction == UseAction.TOOT_HORN) {
                    return BipedEntityModel.ArmPose.TOOT_HORN;
                }
            } else if (!player.handSwinging && itemStack.isOf(Items.CROSSBOW) && CrossbowItem.isCharged(itemStack)) {
                return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
            }

            return BipedEntityModel.ArmPose.ITEM;
        }
    }

    @Override
    public Identifier getTexture(TulpaPlayerEntity entity) {
        if(entity.getSkinUuid() != null){
            PlayerEntity player = entity.getWorld().getPlayerByUuid(entity.getSkinUuid());
            if(player instanceof AbstractClientPlayerEntity abstractClientPlayerEntity){
                return abstractClientPlayerEntity.getSkinTexture();
            }
        }
        return new Identifier("textures/entity/steve.png");
    }

    public static float quadEaseInOut(float time) {
        return time < 0.5f ? 2.0f * time * time : -1.0f + (4.0f - 2.0f * time) * time;
    }
}
