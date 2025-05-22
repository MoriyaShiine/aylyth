package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.render.entity.state.TulpaPlayerEntityRenderState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.equipment.EquipmentModelLoader;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerCapeModel;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.ItemStack;

public class AylythCapeFeatureRenderer extends FeatureRenderer<TulpaPlayerEntityRenderState, BipedEntityModel<TulpaPlayerEntityRenderState>> {
    private final BipedEntityModel<TulpaPlayerEntityRenderState> model;
    private final EquipmentModelLoader equipmentModelLoader;

    public AylythCapeFeatureRenderer(
            FeatureRendererContext<TulpaPlayerEntityRenderState, BipedEntityModel<TulpaPlayerEntityRenderState>> featureRendererContext,
            LoadedEntityModels models,
            EquipmentModelLoader equipmentModelLoader
    ) {
        super(featureRendererContext);
        this.model = new PlayerCapeModel<>(models.getModelPart(EntityModelLayers.PLAYER_CAPE));
        this.equipmentModelLoader = equipmentModelLoader;
    }

    private boolean hasCustomModelForLayer(ItemStack stack, EquipmentModel.LayerType layerType) {
        EquippableComponent equippableComponent = stack.get(DataComponentTypes.EQUIPPABLE);
        if (equippableComponent != null && equippableComponent.assetId().isPresent()) {
            EquipmentModel equipmentModel = this.equipmentModelLoader.get(equippableComponent.assetId().get());
            return !equipmentModel.getLayers(layerType).isEmpty();
        } else {
            return false;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, TulpaPlayerEntityRenderState state, float limbAngle, float limbDistance) {
        if (!state.invisible && state.capeVisible) {
            SkinTextures skinTextures = state.skinTextures;
            if (skinTextures.capeTexture() != null) {
                if (!this.hasCustomModelForLayer(state.equippedChestStack, EquipmentModel.LayerType.WINGS)) {
                    matrixStack.push();
                    if (this.hasCustomModelForLayer(state.equippedChestStack, EquipmentModel.LayerType.HUMANOID)) {
                        matrixStack.translate(0.0F, -0.053125F, 0.06875F);
                    }

                    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(skinTextures.capeTexture()));
                    this.getContextModel().copyTransforms(this.model);
                    this.model.setAngles(state);
                    this.model.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
                    matrixStack.pop();
                }
            }
        }
    }
}
