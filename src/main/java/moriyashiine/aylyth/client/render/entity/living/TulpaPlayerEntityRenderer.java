package moriyashiine.aylyth.client.render.entity.living;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import static moriyashiine.aylyth.common.entity.mob.TulpaEntity.*;

public class TulpaPlayerEntityRenderer extends LivingEntityRenderer<TulpaPlayerEntity, BipedEntityModel<TulpaPlayerEntity>> {
    private final BipedEntityModel normalModel;
    private final BipedEntityModel slimModel;
    public TulpaPlayerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER)), 0F);
        this.normalModel = this.getModel();
        this.slimModel = new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_SLIM));
        this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new BipedEntityModel(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR))));
        this.addFeature(new PlayerHeldItemFeatureRenderer(this, ctx.getHeldItemRenderer()));
        this.addFeature(new StuckArrowsFeatureRenderer(ctx, this));
        this.addFeature(new HeadFeatureRenderer<>(this, ctx.getModelLoader(), ctx.getHeldItemRenderer()));
        this.addFeature(new ElytraFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new StuckStingersFeatureRenderer(this));
    }

    @Override
    public void render(TulpaPlayerEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        var view = MinecraftClient.getInstance().getCameraEntity();
        if (view instanceof AbstractClientPlayerEntity && DefaultSkinHelper.getModel(view.getUuid()).equals("slim")) {
            this.model = slimModel;
        } else {
            this.model = normalModel;
        }
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(TulpaPlayerEntity entity) {
        if(entity.getSkinUuid() != null){
            PlayerEntity player = entity.world.getPlayerByUuid(entity.getSkinUuid());
            if(player instanceof AbstractClientPlayerEntity abstractClientPlayerEntity){
                return abstractClientPlayerEntity.getSkinTexture();
            }
        }
        return new Identifier("textures/entity/steve.png");
    }


}
