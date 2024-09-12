package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.WreathedHindEntityModel;
import moriyashiine.aylyth.client.render.entity.living.layer.WreathedHindGlowLayerRenderer;
import moriyashiine.aylyth.common.entity.type.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.registry.AylythParticleTypes;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WreathedHindEntityRenderer extends GeoEntityRenderer<WreathedHindEntity> {
    public WreathedHindEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new WreathedHindEntityModel());
        this.addRenderLayer(new WreathedHindGlowLayerRenderer(this));
        this.shadowRadius = 1;
    }

    @Override
    public void render(WreathedHindEntity animatable, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if (animatable.getWorld().getTime() % 2 == 0) {
            if (model.getBone("iGuessThisistheHead").isPresent()) {
                double x = model.getBone("iGuessThisistheHead").get().getWorldPosition().x;
                double y = model.getBone("iGuessThisistheHead").get().getWorldPosition().y;
                double z = model.getBone("iGuessThisistheHead").get().getWorldPosition().z;
                double randX = animatable.getRandom().nextDouble();
                double randZ = animatable.getRandom().nextDouble();
                animatable.getEntityWorld().addParticle(AylythParticleTypes.HIND_SMOKE,
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
