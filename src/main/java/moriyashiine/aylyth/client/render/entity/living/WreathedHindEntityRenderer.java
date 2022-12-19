package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.WreathedHindEntityModel;
import moriyashiine.aylyth.client.render.entity.living.layer.SoulmouldEyesLayer;
import moriyashiine.aylyth.client.render.entity.living.layer.WreathedHindGlowLayerRenderer;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import software.bernie.example.entity.GeoExampleEntity;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;

public class WreathedHindEntityRenderer extends GeoEntityRenderer<WreathedHindEntity> {
    private int currentTick = -1;

    public WreathedHindEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new WreathedHindEntityModel());
        this.addLayer(new WreathedHindGlowLayerRenderer(this));
    }

    @Override
    public void render(GeoModel model, WreathedHindEntity animatable, float partialTick, RenderLayer type, MatrixStack poseStack, VertexConsumerProvider bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (currentTick < 0 || currentTick != animatable.age) {
            this.currentTick = animatable.age;
            if (model.getBone("iGuessThisistheHead").isPresent()) {
                double x = model.getBone("iGuessThisistheHead").get().getWorldPosition().x;
                double y = model.getBone("iGuessThisistheHead").get().getWorldPosition().y;
                double z = model.getBone("iGuessThisistheHead").get().getWorldPosition().z;

                animatable.getEntityWorld().addParticle(DustParticleEffect.DEFAULT,
                        x,
                        y,
                        z,
                        (animatable.getRandom().nextDouble() - 0.5D), - animatable.getRandom().nextDouble(),
                        (animatable.getRandom().nextDouble() - 0.5D));
            }
        }
        super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
