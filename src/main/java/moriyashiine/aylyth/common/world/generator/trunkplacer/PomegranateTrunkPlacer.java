package moriyashiine.aylyth.common.world.generator.trunkplacer;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.registry.AylythTrunkPlacerTypes;
import moriyashiine.aylyth.common.world.generator.foliageplacer.DirectionalTreeNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PomegranateTrunkPlacer extends StraightTrunkPlacer {

    public static final Codec<PomegranateTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).apply(instance, PomegranateTrunkPlacer::new));

    private static final BlockState WOOD_STATE = AylythBlocks.POMEGRANATE_WOOD.getDefaultState();

    public PomegranateTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return AylythTrunkPlacerTypes.POMEGRANATE;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        list.addAll(super.generate(world, replacer, random, height, startPos, config));
        // I need to generate along the curve and know what the top is for the leaf generation
        addIf(random.nextInt(3) == 0, list, () -> alongCurve(world, Direction.NORTH, startPos.offset(Direction.NORTH), replacer, random, height, config));
        addIf(random.nextInt(3) == 0, list, () -> alongCurve(world, Direction.SOUTH, startPos.offset(Direction.SOUTH), replacer, random, height, config));
        addIf(random.nextInt(3) == 0, list, () -> alongCurve(world, Direction.EAST, startPos.offset(Direction.EAST), replacer, random, height, config));
        addIf(random.nextInt(3) == 0, list, () -> alongCurve(world, Direction.WEST, startPos.offset(Direction.WEST), replacer, random, height, config));
        return list;
    }

    protected void addIf(boolean test, List<FoliagePlacer.TreeNode> list, Supplier<FoliagePlacer.TreeNode> supplier) {
        if (test) {
            list.add(supplier.get());
        }
    }

    protected FoliagePlacer.TreeNode alongCurve(TestableWorld world, Direction dir, BlockPos pos, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, TreeFeatureConfig config) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        getAndSetWoodState(world, replacer, random, mutable, config, state -> setWithAxis(state, dir));
        mutable.move(dir).move(Direction.UP);
        getAndSetWoodState(world, replacer, random, mutable, config);
        for (int i = 2; i < height-1; i++) {
            mutable.move(Direction.UP);
            getAndSetState(world, replacer, random, mutable, config);
        }
        return new DirectionalTreeNode(mutable.move(Direction.UP).toImmutable(), 1, false, dir);
    }

    protected boolean getAndSetWoodState(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig config) {
        return getAndSetWoodState(world, replacer, random, pos, config, Function.identity());
    }

        protected boolean getAndSetWoodState(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig config, Function<BlockState, BlockState> function) {
        if (this.canReplace(world, pos)) {
            replacer.accept(pos, function.apply(WOOD_STATE));
            return true;
        } else {
            return false;
        }
    }

    public BlockState setWithAxis(BlockState state, Direction dir) {
        if (state.contains(PillarBlock.AXIS)) {
            return state.with(PillarBlock.AXIS, dir.getAxis());
        }
        return state;
    }
}
