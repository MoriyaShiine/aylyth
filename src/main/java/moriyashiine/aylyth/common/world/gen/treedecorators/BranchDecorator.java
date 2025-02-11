package moriyashiine.aylyth.common.world.gen.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.world.gen.AylythTreeDecoratorTypes;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class BranchDecorator extends TreeDecorator {
    public static final Codec<BranchDecorator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockStateProvider.TYPE_CODEC.fieldOf("block_state").forGetter(branchDecorator -> branchDecorator.provider),
                    IntProvider.NON_NEGATIVE_CODEC.fieldOf("min_branch_height").forGetter(branchDecorator -> branchDecorator.minBranchHeight),
                    Codec.FLOAT.fieldOf("chance").forGetter(branchDecorator -> branchDecorator.chance)
            ).apply(instance, BranchDecorator::new)
    );

    private final BlockStateProvider provider;
    private final IntProvider minBranchHeight;
    private final float chance;

    public BranchDecorator(BlockStateProvider provider, IntProvider minBranchHeight, float chance) {
        this.provider = provider;
        this.minBranchHeight = minBranchHeight;
        this.chance = chance;
    }

    @Override
    public void generate(Generator generator) {
        BlockPos lowestPos = generator.getLogPositions().get(0);
        for (BlockPos pos : generator.getLogPositions()) {
            if (pos.getY() <= lowestPos.getY() + minBranchHeight.get(generator.getRandom())) {
                continue;
            }
            if (generator.getRandom().nextFloat() < chance && generator.getWorld().testBlockState(pos, state -> !state.contains(Properties.AXIS) || state.get(Properties.AXIS).isVertical())) {
                for (Direction dir : Direction.Type.HORIZONTAL.getShuffled(generator.getRandom())) {
                    BlockPos branchPos = pos.offset(dir);
                    if (generator.isAir(branchPos)) {
                        // For users, this could possibly benefit from a guard, only trying to set the state if it's present
                        BlockState state = provider.get(generator.getRandom(), pos).with(Properties.HORIZONTAL_FACING, dir);
                        generator.replace(branchPos, state);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return AylythTreeDecoratorTypes.BRANCHES;
    }
}
