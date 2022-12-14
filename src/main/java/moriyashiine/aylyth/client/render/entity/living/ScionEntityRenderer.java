package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.render.entity.living.feature.ScionFeatureRenderer;
import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ScionEntityRenderer extends BipedEntityRenderer<ScionEntity, BipedEntityModel<ScionEntity>> {
    private final ScionCoreEntityModel normalModel;
    private final ScionCoreEntityModel slimModel;


    public ScionEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ScionCoreEntityModel(ctx.getPart(EntityModelLayers.PLAYER)), 0.5f);
        this.normalModel = (ScionCoreEntityModel) this.getModel();
        this.slimModel = new ScionCoreEntityModel(ctx.getPart(EntityModelLayers.PLAYER_SLIM));

        addFeature(new ScionFeatureRenderer(this, ctx.getModelLoader()));
        addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR))));
    }

    @Override
    public void render(@NotNull ScionEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        this.model = getSlim(mobEntity) ? slimModel : normalModel;

    }

    public boolean getSlim(ScionEntity mobEntity){
        if(mobEntity.getStoredPlayerUUID() != null){
            return DefaultSkinHelper.getModel(mobEntity.getStoredPlayerUUID()).equals("slim");
        }
        return false;
    }

    @Override
    public Identifier getTexture(ScionEntity mobEntity) {
        if(mobEntity.getStoredPlayerUUID() != null){
            PlayerEntity player = mobEntity.world.getPlayerByUuid(mobEntity.getStoredPlayerUUID());
            if(player instanceof AbstractClientPlayerEntity abstractClientPlayerEntity){
                return abstractClientPlayerEntity.getSkinTexture();
            }
        }
        return AylythUtil.id("textures/entity/living/scion/scion_npc_base.png");
    }

    @Override
    protected boolean hasLabel(ScionEntity mobEntity) {
        return super.hasLabel(mobEntity) && mobEntity.getStoredPlayerUUID() != null;
    }

    @Environment(EnvType.CLIENT)
    public static class ScionCoreEntityModel extends BipedEntityModel<ScionEntity> {
        public ScionCoreEntityModel(ModelPart modelPart) {
            super(modelPart);
        }
    }
}
