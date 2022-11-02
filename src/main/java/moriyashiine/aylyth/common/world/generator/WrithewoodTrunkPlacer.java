package moriyashiine.aylyth.common.world.generator;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
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
import java.util.function.Consumer;

public class WrithewoodTrunkPlacer extends StraightTrunkPlacer {
    public static final Codec<WrithewoodTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, WrithewoodTrunkPlacer::new));

    public WrithewoodTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModFeatures.WRITHEWOOD_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        ImmutableList.Builder<FoliagePlacer.TreeNode> builder = ImmutableList.builder();
        builder.addAll(super.generate(world, replacer, random, height, startPos, config));
        // Build trunk. 1/5th the height
        var fifthHeight = height / 5;
        for (int i = 0; i < fifthHeight - 1; i++) {
            placeOnCardinals(startPos, blockPos -> getAndSetState(world, replacer, random, blockPos, config));
        }
        placeOnCardinals(startPos, blockPos -> getAndSetState(world, replacer, random, blockPos, config, state -> ModBlocks.WRITHEWOOD_BLOCKS.wood.getDefaultState()));
        var branches = random.nextBoolean() ? fifthHeight + random.nextInt(3) : fifthHeight - random.nextInt(3);
        var operableHeight = height - fifthHeight - 1; // Leave some room to the base trunk
        for (int i = 0; i < branches; i++) {
            var blockHeight = random.nextInt(operableHeight);
            var dir = Direction.Type.HORIZONTAL.random(random);
            var pos = startPos.withY(blockHeight).offset(dir);
            var lastPos = pos.offset(dir, random.nextInt(3)+1);
            for (BlockPos branchPos : BlockPos.iterate(pos, lastPos)) {
                getAndSetState(world, replacer, random, branchPos, config, state -> {
                    if (state.contains(Properties.AXIS)) {
                        return state.with(Properties.AXIS, dir.getAxis());
                    }
                    return state;
                });
            }
            builder.add(new FoliagePlacer.TreeNode(lastPos, 1, false));
        }
        return builder.build();
    }

    private void placeOnCardinals(BlockPos blockPos, Consumer<BlockPos> setFunction) {
        setFunction.accept(blockPos.north());
        setFunction.accept(blockPos.south());
        setFunction.accept(blockPos.east());
        setFunction.accept(blockPos.west());
    }
}
