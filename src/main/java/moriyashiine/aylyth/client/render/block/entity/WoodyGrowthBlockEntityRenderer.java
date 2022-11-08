package moriyashiine.aylyth.client.render.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.client.RenderTypes;
import moriyashiine.aylyth.common.block.entity.WoodyGrowthCacheBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class WoodyGrowthBlockEntityRenderer implements BlockEntityRenderer<WoodyGrowthCacheBlockEntity> {

    public WoodyGrowthBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    private static final List<Pair<Vec3d, Vec3d>> MODEL_13_ROT_POS = Util.make(Lists.newArrayList(), pairs -> {
        pairs.add(Pair.of(new Vec3d(0.15, 1.35, 0.28), new Vec3d(0, 15, 0))); // 0
        pairs.add(Pair.of(new Vec3d(0.55, -1.2, 1.36), new Vec3d(-115, 0, 0))); // 1
        pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 2
        pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 3
        pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 4
        pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 5
        pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 6
        pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 7
        pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 8
    });

    private static final List<Pair<Vec3d, Vec3d>> ROTATIONS_POSITIONS = Util.make(Lists.newArrayList(), pairs -> {
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 0
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 1
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 2
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 3
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 4
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 5
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 6
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 7
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 8
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 9
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 10
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 11
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 12
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 13
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 14
                pairs.add(Pair.of(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0))); // 15
    });

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
        if (entity.isEmpty()) return;
        rand.setSeed(renderingSeed);
        var treeModel = Math.abs(rand.nextLong() % 16);
        for (int i = 0; i < 1; i++) {
            var itemStack = entity.getItem(i);
            matrices.push();
            var isBlock = itemStack.getItem() instanceof BlockItem;
            var scaleScalar = isBlock ? 0.25f : 0.5f;
            matrices.scale(scaleScalar, scaleScalar, scaleScalar);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(0));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(34));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90));
            var vec = new Vec3d(1.57, 0.25, 1.25);
            var xT = vec.x;
            var yT = vec.y;
            var zT = vec.z;
            xT /= scaleScalar;
            yT /= scaleScalar;
            zT /= scaleScalar;
            matrices.translate(xT, yT, zT);
            MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.NONE, light, overlay, matrices, vertexConsumers, 42);
            matrices.pop();
        }
    }
}
