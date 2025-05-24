package moriyashiine.aylyth.client.model.block;

import moriyashiine.aylyth.client.util.RenderUtils;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.mixin.client.BasicItemModelAccessor;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.render.item.model.BasicItemModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.WrapperBakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SoulHearthBlockModel extends WrapperBakedModel {
    private final BakedModelManager modelManager;
    private BakedModel pomegranate = null;

    public SoulHearthBlockModel(BakedModelManager modelManager, BakedModel model) {
        super(model);
        this.modelManager = modelManager;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(QuadEmitter emitter, BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, Predicate<@Nullable Direction> cullTest) {
        super.emitBlockQuads(emitter, blockView, state, pos, randomSupplier, cullTest);
        if (pomegranate == null) {
            if (modelManager.getItemModel(Aylyth.id("pomegranate")) instanceof BasicItemModel itemModel) {
                pomegranate = ((BasicItemModelAccessor)itemModel).getModel();
            } else {
                pomegranate = modelManager.getMissingBlockModel();
            }
        }
        if (state.isOf(AylythBlocks.SOUL_HEARTH) && state.get(SoulHearthBlock.CHARGES) > 0 && state.get(SoulHearthBlock.HALF) == DoubleBlockHalf.LOWER) {
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
                RenderUtils.copyOver(emitter, stack);
                pomegranate.emitBlockQuads(emitter, blockView, null, pos, randomSupplier, cullTest);
                emitter.popTransform();
                stack.pop();
            }
            stack.pop();
        }
    }
}
