package moriyashiine.aylyth.common.world.gen.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.types.LargeWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.world.gen.AylythTreeDecoratorTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

public class RangedTreeDecorator extends TreeDecorator {
    public static final Codec<RangedTreeDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.listOf().fieldOf("blockstates").forGetter(woodyGrowthsDecorator -> woodyGrowthsDecorator.blockStates),
            Codec.intRange(0, 64).fieldOf("tries").forGetter(woodyGrowthsDecorator -> woodyGrowthsDecorator.tries),
            Codec.intRange(0, 64).fieldOf("range").forGetter(woodyGrowthsDecorator -> woodyGrowthsDecorator.range)
    ).apply(instance, RangedTreeDecorator::new));

    private final List<BlockState> blockStates;
    private final int tries;
    private final int range;

    public RangedTreeDecorator(List<BlockState> blockStates, int tries, int range) {
        this.blockStates = blockStates;
        this.tries = tries;
        this.range = range;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return AylythTreeDecoratorTypes.RANGED;
    }

    @Override
    public void generate(Generator generator) {
        BlockPos rootPos = generator.getLogPositions().get(0);

        List<BlockPos> base = generator.getLogPositions().stream().filter(blockPos -> blockPos.getY() == rootPos.getY()).toList();
        int minX = 0; boolean minxInit = false;
        int maxX = 0; boolean maxxInit = false;
        int minZ = 0; boolean minzInit = false;
        int maxZ = 0; boolean maxzInit = false;
        for (BlockPos pos : base) {
            if (pos.getX() < minX || !minxInit) {
                minX = pos.getX();
                minxInit = true;
            }
            if (pos.getX() > maxX || !maxxInit) {
                maxX = pos.getX();
                maxxInit = true;
            }
            if (pos.getZ() < minZ || !minzInit) {
                minZ = pos.getZ();
                minzInit = true;
            }
            if (pos.getZ() > maxZ || !maxzInit) {
                maxZ = pos.getZ();
                maxzInit = true;
            }
        }

        BlockPos startPos = new BlockPos(minX+((maxX-minX)/2), rootPos.getY(), minZ+((maxZ-minZ)/2));
        for (BlockPos pos : BlockPos.iterateRandomly(generator.getRandom(), tries, startPos, range)) {
            pos = pos.withY(startPos.getY());
            if (TreeFeature.canReplace(generator.getWorld(), pos)) {
                BlockState blockState = Util.getRandom(blockStates, generator.getRandom());
                if (generator.getWorld().testBlockState(pos.down(), state -> !state.isReplaceable()) && TreeFeature.canReplace(generator.getWorld(), pos)) {
                    if (blockState.isOf(AylythBlocks.LARGE_WOODY_GROWTH)) {
                        if (TreeFeature.canReplace(generator.getWorld(), pos.up())) {
                            generator.replace(pos, blockState.with(LargeWoodyGrowthBlock.HALF, DoubleBlockHalf.LOWER));
                            generator.replace(pos.up(), blockState.with(LargeWoodyGrowthBlock.HALF, DoubleBlockHalf.UPPER));
                        }
                    } else {
                        generator.replace(pos, blockState);
                    }
                }
            }
        }
    }
}
