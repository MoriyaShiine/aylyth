package moriyashiine.aylyth.client.model.block;

import moriyashiine.aylyth.client.util.RenderUtils;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.function.Supplier;

public class SoulHearthBlockModel extends ForwardingBakedModel {

    public SoulHearthBlockModel(BakedModel model) {
        this.wrapped = model;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        if (state.isOf(AylythBlocks.SOUL_HEARTH) && state.get(SoulHearthBlock.HALF) == DoubleBlockHalf.LOWER) {
            BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(Aylyth.id("pomegranate"), "inventory"));
            MatrixStack stack = new MatrixStack();
            stack.push();
            float scale = 0.4f;
            stack.scale(scale, scale, scale);
            stack.translate(0.3/scale, 0.75/scale, 0.3/scale);
            for (int i = 0; i < state.get(SoulHearthBlock.CHARGES); i++) {
                stack.translate(0, 0.06, 0);
                stack.push();
                stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
                stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90 * i), 0.5f, 0.5f, 0.5f);
                RenderUtils.copyOver(context, stack);
                model.emitItemQuads(new ItemStack(AylythItems.POMEGRANATE), randomSupplier, context);
                context.popTransform();
                stack.pop();
            }
            stack.pop();
        }
    }
}
