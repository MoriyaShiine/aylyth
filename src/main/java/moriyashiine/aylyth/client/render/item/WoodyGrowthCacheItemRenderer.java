package moriyashiine.aylyth.client.render.item;

import moriyashiine.aylyth.client.render.AylythRenderLayers;
import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class WoodyGrowthCacheItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Random rand = Random.create(42);
        BakedModel model = mc.getBakedModelManager().getModel(new ModelIdentifier(Aylyth.MOD_ID, "large_woody_growth", "inventory"));
        if (model == mc.getBakedModelManager().getMissingModel()) {
            VertexConsumer consumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, RenderLayers.getItemLayer(stack, false), true, stack.hasGlint());
            for(Direction direction : Direction.values()) {
                rand.setSeed(42L);
                for (BakedQuad quad : model.getQuads(null, direction, rand)) {
                    consumer.quad(matrices.peek(), quad, 1.0f, 1.0f, 1.0f, light, overlay);
                }
            }

            rand.setSeed(42L);
            for (BakedQuad quad : model.getQuads(null, null, rand)) {
                consumer.quad(matrices.peek(), quad, 1.0f, 1.0f, 1.0f, light, overlay);
            }
            return;
        }
        VertexConsumer consumer = VertexConsumers.union(vertexConsumers.getBuffer(AylythRenderLayers.TINT), vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, true)));
        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);
        model.getTransformation().getTransformation(mode).apply(mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND, matrices);
        matrices.translate(-0.5, -0.5, -0.5);
        for (BakedQuad quad : model.getQuads(null, null, rand)) {
            consumer.quad(matrices.peek(), quad, 1.0f, 1.0f, 1.0f, light, overlay);
        }
        matrices.pop();
    }
}
