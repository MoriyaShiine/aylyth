package moriyashiine.aylyth.common.world.generator.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModFeatures;
import moriyashiine.aylyth.common.world.generator.foliageplacer.WrithewoodFoliagePlacer;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WrithewoodTrunkPlacer extends StraightTrunkPlacer {
    public static final Codec<WrithewoodTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, WrithewoodTrunkPlacer::new));

    public WrithewoodTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
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
        var mutable = startPos.mutableCopy();
        for (int i = 0; i < fifthHeight - 1; i++) {
            placeOnCardinals(mutable, blockPos -> getAndSetState(world, replacer, random, blockPos, config));
            mutable.move(Direction.UP);
        }
        placeOnCardinals(mutable, blockPos -> getAndSetState(world, replacer, random, blockPos, config, state -> ModBlocks.WRITHEWOOD_BLOCKS.wood.getDefaultState()));

        // branches
        //  decide the number of branches on the height/5 give or take two.
        //  iterate through the number of branches, deciding whether a branch is placed based on whether a branch is
        //  near and if there is a branch existing on this side. If there is already a branch, we want to be biased
        //  against placing a branch here.
        var branches = fifthHeight + ((random.nextBoolean() ? 1 : -1) * random.nextInt(2));
        Set<BlockPos> branchLocations = Sets.newHashSet();
        for (int i = 0; i < branches; i++) {
            var blockHeight = MathHelper.clamp(random.nextInt(height), fifthHeight+random.nextInt(3)+2, height-random.nextInt(3)-5);
            var dir = Direction.Type.HORIZONTAL.random(random);
            var pos = startPos.up(blockHeight).offset(dir);
            if (pos.getY() <= startPos.up(fifthHeight+2).getY() || setHasWithin(branchLocations, pos, 2)) {
                continue;
            }
            branchLocations.add(pos);
            var branchLength = random.nextInt(3)+1;
            var lastPos = pos.offset(dir, branchLength);
            for (BlockPos branchPos : BlockPos.iterate(pos, lastPos)) {
                getAndSetState(world, replacer, random, branchPos, config, state -> {
                    if (state.contains(Properties.AXIS)) {
                        return state.with(Properties.AXIS, dir.getAxis());
                    }
                    return state;
                });
            }
            builder.add(new WrithewoodFoliagePlacer.WrithewoodTreeNode(lastPos, 1, false, dir, branchLength+1));
        }
        return builder.build();
    }

    private boolean setHasWithin(Set<BlockPos> posSet, BlockPos pos, int length) {
        for (int i = -length; i <= length; i++) {
            if (posSet.contains(pos.add(0, i, 0))) {
                return true;
            }
        }
        return false;
    }

    private void placeOnCardinals(BlockPos blockPos, Consumer<BlockPos> setFunction) {
        setFunction.accept(blockPos.north());
        setFunction.accept(blockPos.south());
        setFunction.accept(blockPos.east());
        setFunction.accept(blockPos.west());
    }
}
