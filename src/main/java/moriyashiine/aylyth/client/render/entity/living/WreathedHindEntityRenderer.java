package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.WreathedHindEntityModel;
import moriyashiine.aylyth.client.render.entity.living.layer.WreathedHindGlowLayerRenderer;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.registry.ModParticles;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WreathedHindEntityRenderer extends GeoEntityRenderer<WreathedHindEntity> {
    private int currentTick = -1;
    public WreathedHindEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new WreathedHindEntityModel());
        this.addRenderLayer(new WreathedHindGlowLayerRenderer(this));
        this.shadowRadius = 1;
    }

    @Override
    public void render(WreathedHindEntity animatable, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if (currentTick < 0 || currentTick != animatable.age) {
            this.currentTick = animatable.age;
            if (model.getBone("iGuessThisistheHead").isPresent()) {
                double x = model.getBone("iGuessThisistheHead").get().getWorldPosition().x;
                double y = model.getBone("iGuessThisistheHead").get().getWorldPosition().y;
                double z = model.getBone("iGuessThisistheHead").get().getWorldPosition().z;
                double randX = animatable.getRandom().nextDouble();
                double randZ = animatable.getRandom().nextDouble();
                animatable.getEntityWorld().addParticle(ModParticles.HIND_SMOKE,
                        randX + x - 0.5,
                        y,
                        randZ + z - 0.5,
                        0, 0.07,
                        0);
            }
        }
        super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
