package moriyashiine.aylyth.client.render.entity.living.feature;

import moriyashiine.aylyth.client.model.CuirassModel;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModComponents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CuirassFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private static final Identifier TEXTURE = new Identifier(Aylyth.MOD_ID, "textures/entity/living/ympe_cuirass.png");
    private static final CuirassModel[] MODELS = new CuirassModel[5];

    public CuirassFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context, EntityModelLoader loader) {
        super(context);
        if (MODELS[0] == null) {
            MODELS[0] = new CuirassModel(loader.getModelPart(CuirassModel.LAYER_LOCATION_1));
            MODELS[1] = new CuirassModel(loader.getModelPart(CuirassModel.LAYER_LOCATION_2));
            MODELS[2] = new CuirassModel(loader.getModelPart(CuirassModel.LAYER_LOCATION_3));
            MODELS[3] = new CuirassModel(loader.getModelPart(CuirassModel.LAYER_LOCATION_4));
            MODELS[4] = new CuirassModel(loader.getModelPart(CuirassModel.LAYER_LOCATION_5));
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        float stage = ModComponents.CUIRASS_COMPONENT.get(entity).getStage();
        if (stage > 0) {
            CuirassModel model;
             if(stage < 5){
                 model = MODELS[0];
            }else if(stage < 10){
                 model = MODELS[1];
            } else if (stage < 15) {
                 model = MODELS[2];
            } else if(stage < 20){
                 model = MODELS[3];
            } else {
                 model = MODELS[4];
            }
            getContextModel().copyBipedStateTo(model);
            model.adjustArmPivots(entity.getModel().equals("slim"));
            model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        }
    }
}
