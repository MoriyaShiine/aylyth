package moriyashiine.aylyth.common.world.gen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.LargeWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.types.SmallWoodyGrowthBlock;
import moriyashiine.aylyth.common.world.gen.AylythStructureProcessors;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class BlackWellStructureProcessor extends StructureProcessor {
    public static final Codec<BlackWellStructureProcessor> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("air_weight").forGetter(blackWellStructureProcessor -> blackWellStructureProcessor.airWeight),
                    Codec.INT.fieldOf("small_woody_growth_weight").forGetter(blackWellStructureProcessor -> blackWellStructureProcessor.smallGrowthWeight),
                    Codec.INT.fieldOf("large_woody_growth_weight").forGetter(blackWellStructureProcessor -> blackWellStructureProcessor.normalWeight)
            ).apply(instance, BlackWellStructureProcessor::new)
    );

    private final int airWeight;
    private final int smallGrowthWeight;
    private final int normalWeight;
    private final int total;

    public BlackWellStructureProcessor(int airWeight, int smallGrowthWeight, int normalWeight) {
        this.airWeight = airWeight;
        this.smallGrowthWeight = smallGrowthWeight;
        this.normalWeight = normalWeight;
        this.total = airWeight + smallGrowthWeight + normalWeight;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo currentBlockInfo, StructurePlacementData data) {
        if (currentBlockInfo.state().isOf(AylythBlocks.LARGE_WOODY_GROWTH)) {
            Random random = data.getRandom(currentBlockInfo.pos());
            int chosen = random.nextInt(total);
            if (chosen < airWeight) {
                return new StructureTemplate.StructureBlockInfo(currentBlockInfo.pos(), Blocks.AIR.getDefaultState(), null);
            }
            if (chosen < airWeight + smallGrowthWeight && currentBlockInfo.state().get(LargeWoodyGrowthBlock.HALF) == DoubleBlockHalf.LOWER) {
                return new StructureTemplate.StructureBlockInfo(currentBlockInfo.pos(), AylythBlocks.SMALL_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true), null);
            }

            if (currentBlockInfo.state().get(LargeWoodyGrowthBlock.HALF) == DoubleBlockHalf.UPPER && !world.getBlockState(currentBlockInfo.pos().down()).isOf(AylythBlocks.LARGE_WOODY_GROWTH)) {
                return new StructureTemplate.StructureBlockInfo(currentBlockInfo.pos(), Blocks.AIR.getDefaultState(), null);
            }
        }
        return super.process(world, pos, pivot, originalBlockInfo, currentBlockInfo, data);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AylythStructureProcessors.BLACK_WELL;
    }
}
