package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity.TulpaPlayerEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckArrowsFeatureRenderer;
import net.minecraft.client.render.entity.feature.StuckStingersFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class TulpaPlayerEntityRenderer extends LivingEntityRenderer<TulpaPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
    private final PlayerEntityModel wideModel;
    private final PlayerEntityModel slimModel;
    public TulpaPlayerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerEntityModel(ctx.getPart(EntityModelLayers.PLAYER), false), 0F);
        this.wideModel = this.getModel();
        this.slimModel = new PlayerEntityModel(ctx.getPart(EntityModelLayers.PLAYER_SLIM), true);
        this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getEquipmentRenderer()));
        this.addFeature(new PlayerHeldItemFeatureRenderer<>(this));
        this.addFeature(new StuckArrowsFeatureRenderer<>(this, ctx));
        this.addFeature(new HeadFeatureRenderer<>(this, ctx.getEntityModels(), HeadFeatureRenderer.HeadTransformation.DEFAULT));
        this.addFeature(new ElytraFeatureRenderer<>(this, ctx.getEntityModels(), ctx.getEquipmentRenderer()));
        this.addFeature(new StuckStingersFeatureRenderer<>(this, ctx));
        this.addFeature(new CapeFeatureRenderer(this, ctx.getEntityModels(), ctx.getEquipmentModelLoader()));
    }

    @Override
    public void updateRenderState(TulpaPlayerEntity entity, PlayerEntityRenderState renderState, float f) {
        super.updateRenderState(entity, renderState, f);
        World world = entity.getWorld();
        UUID playerUuid = entity.getSkinUuid();
        //TODO: test this when affected player leaves and another player is still online
        if (playerUuid != null && world.getPlayerByUuid(playerUuid) instanceof AbstractClientPlayerEntity player) {
            renderState.skinTextures = player.getSkinTextures();
        } else {
            renderState.skinTextures = DefaultSkinHelper.getSteve();
        }
    }

    @Override
    public void render(PlayerEntityRenderState renderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.model = renderState.skinTextures.model() == SkinTextures.Model.SLIM ? slimModel : wideModel;

        this.model.setAngles(renderState);
        super.render(renderState, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public PlayerEntityRenderState createRenderState() {
        return new PlayerEntityRenderState();
    }

    @Override
    public Identifier getTexture(PlayerEntityRenderState state) {
        return state.skinTextures.texture();
    }
}
