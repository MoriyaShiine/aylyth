package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.render.entity.living.feature.ScionFeatureRenderer;
import moriyashiine.aylyth.client.render.entity.state.ScionEntityRenderState;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.ScionEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class ScionEntityRenderer extends BipedEntityRenderer<ScionEntity, ScionEntityRenderState, BipedEntityModel<ScionEntityRenderState>> {
    private final ScionCoreEntityModel normalModel;
    private final ScionCoreEntityModel slimModel;
    private final Identifier BASE_TEXTURE = Aylyth.id("textures/entity/living/scion_npc_base.png");

    public ScionEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ScionCoreEntityModel(ctx.getPart(EntityModelLayers.PLAYER)), 0.5f);
        this.normalModel = (ScionCoreEntityModel) this.getModel();
        this.slimModel = new ScionCoreEntityModel(ctx.getPart(EntityModelLayers.PLAYER_SLIM));

        addFeature(new ScionFeatureRenderer(this, ctx.getEntityModels()));
        addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), ctx.getEquipmentRenderer()));
    }

    @Override
    public void updateRenderState(ScionEntity mobEntity, ScionEntityRenderState bipedEntityRenderState, float f) {
        super.updateRenderState(mobEntity, bipedEntityRenderState, f);
        World world = mobEntity.getWorld();
        UUID playerUuid = mobEntity.getStoredPlayerUUID();
        //TODO: test this when affected player leaves and another player is still online
        if (playerUuid != null && world.getPlayerByUuid(playerUuid) instanceof AbstractClientPlayerEntity player) {
            bipedEntityRenderState.playerTexture = player.getSkinTextures().texture();
            bipedEntityRenderState.modelType = player.getSkinTextures().model();
        } else {
            bipedEntityRenderState.playerTexture = BASE_TEXTURE;
            bipedEntityRenderState.modelType = SkinTextures.Model.WIDE;
        }
    }

    @Override
    public void render(ScionEntityRenderState renderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(renderState, matrixStack, vertexConsumerProvider, i);
        this.model = renderState.modelType == SkinTextures.Model.SLIM ? slimModel : normalModel;
    }

    @Override
    public Identifier getTexture(ScionEntityRenderState state) {
        return state.playerTexture;
    }

    @Override
    protected boolean hasLabel(ScionEntity mobEntity, double d) {
        return super.hasLabel(mobEntity, d) && mobEntity.getStoredPlayerUUID() != null;
    }

    @Override
    public ScionEntityRenderState createRenderState() {
        return new ScionEntityRenderState();
    }

    public static class ScionCoreEntityModel extends BipedEntityModel<ScionEntityRenderState> {
        public ScionCoreEntityModel(ModelPart modelPart) {
            super(modelPart);
        }
    }
}
