package moriyashiine.aylyth.client.render.block.entity;

import moriyashiine.aylyth.client.RenderTypes;
import moriyashiine.aylyth.common.block.entity.WoodyGrowthCacheBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.random.Random;

public class WoodyGrowthBlockEntityRenderer implements BlockEntityRenderer<WoodyGrowthCacheBlockEntity> {

    public WoodyGrowthBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(WoodyGrowthCacheBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Random rand = Random.create(42L);
        if (entity == null || MinecraftClient.getInstance().world == null) {
            return;
        }
        var state = entity.getCachedState();
        var consumer = VertexConsumers.union(vertexConsumers.getBuffer(RenderTypes.TINT), vertexConsumers.getBuffer(RenderLayer.getCutoutMipped()));
        var model = MinecraftClient.getInstance().getBlockRenderManager().getModel(state);
        var renderingSeed = state.getRenderingSeed(entity.getPos());
        rand.setSeed(renderingSeed);
        for (BakedQuad quad : model.getQuads(state, null, rand)) {
            consumer.quad(matrices.peek(), quad, 1.0f, 1.0f, 1.0f, light, overlay);
        }
    }
}
