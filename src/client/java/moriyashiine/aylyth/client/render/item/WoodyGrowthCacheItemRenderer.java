package moriyashiine.aylyth.client.render.item;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.client.AylythClient;
import moriyashiine.aylyth.client.render.AylythRenderLayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class WoodyGrowthCacheItemRenderer implements SpecialModelRenderer<ItemStack> {
    private static final Supplier<WoodyGrowthCacheItemRenderer> INSTANCE = Suppliers.memoize(WoodyGrowthCacheItemRenderer::new);

    @Override
    public void render(@Nullable ItemStack data, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Random rand = Random.create(42);
        BakedModel model = mc.getBakedModelManager().getModel(AylythClient.modelId("large_woody_growth", "inventory"));
        if (model == mc.getBakedModelManager().getMissingBlockModel()) {
            VertexConsumer consumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, RenderLayers.getItemLayer(data), true, glint);
            for(Direction direction : Direction.values()) {
                rand.setSeed(42L);
                for (BakedQuad quad : model.getQuads(null, direction, rand)) {
                    consumer.quad(matrices.peek(), quad, 1.0f, 1.0f, 1.0f, 1.0f, light, overlay);
                }
            }

            rand.setSeed(42L);
            for (BakedQuad quad : model.getQuads(null, null, rand)) {
                consumer.quad(matrices.peek(), quad, 1.0f, 1.0f, 1.0f, 1.0f, light, overlay);
            }
            return;
        }
        VertexConsumer consumer = VertexConsumers.union(vertexConsumers.getBuffer(AylythRenderLayers.TINT), vertexConsumers.getBuffer(RenderLayers.getItemLayer(data)));
        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);
        model.getTransformation().getTransformation(mode).apply(mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND, matrices);
        matrices.translate(-0.5, -0.5, -0.5);
        for (BakedQuad quad : model.getQuads(null, null, rand)) {
            consumer.quad(matrices.peek(), quad, 1.0f, 1.0f, 1.0f, 1.0f, light, overlay);
        }
        matrices.pop();
    }

    @Nullable
    @Override
    public ItemStack getData(ItemStack stack) {
        return stack;
    }

    public static class Unbaked implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> CODEC = MapCodec.unit(Unbaked::new);

        @Nullable
        @Override
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return INSTANCE.get();
        }


        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }
    }
}
