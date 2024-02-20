package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.common.entity.mob.RippedSoulEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;

public class RippedSoulEntityRenderer extends EntityRenderer<RippedSoulEntity> {
    public RippedSoulEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(RippedSoulEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        entity.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
    }

    @Override
    public Identifier getTexture(RippedSoulEntity entity) {
        return null;
    }
}
