package moriyashiine.aylyth.common.world.gen.foliageplacers;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.types.PomegranateLeavesBlock;
import moriyashiine.aylyth.common.world.gen.AylythFoliagePlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Set;

public class PomegranateFoliagePlacer extends FoliagePlacer {

    public static final Codec<PomegranateFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> fillFoliagePlacerFields(instance).and(Codec.intRange(0, 16).fieldOf("height").forGetter(placer -> placer.height)).apply(instance, PomegranateFoliagePlacer::new));
    private final int height;

    public PomegranateFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return AylythFoliagePlacerTypes.POMEGRANATE;
    }

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer replacer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        Set<BlockPos> positions = Sets.newHashSet();
        if (treeNode instanceof DirectionalTreeNode pomeNode) {
            Direction dir = pomeNode.dir;
            BlockPos pos = pomeNode.getCenter().down().offset(dir.getOpposite()).offset(dir.rotateYCounterclockwise(), 2);
            placeIn(pos, pos.offset(dir, 2).offset(dir.rotateYClockwise(), 4), positions);
            placeIn(pos.offset(dir.rotateYClockwise()).offset(dir, 3), pos.offset(dir.rotateYClockwise(), 3).offset(dir, 3), positions);
            placeIn(pos.up(), pos.up().offset(dir.rotateYClockwise(), 4), positions);
            placeIn(pos.up().offset(dir).offset(dir.rotateYClockwise()), pos.up().offset(dir).offset(dir.rotateYClockwise(), 3), positions);
            positions.add(pos.up().offset(dir, 2).offset(dir.rotateYClockwise()));
            placeIn(pos.up(2).offset(dir.rotateYClockwise()), pos.up(2).offset(dir.rotateYClockwise(), 3), positions);
            // RANDOM SECTION - 2ND LAYER
            placeInIf(pos.up().offset(dir), pos.up().offset(dir).offset(dir.rotateYClockwise(), 4), random, positions);
            placeInIf(pos.up().offset(dir, 2).offset(dir.rotateYClockwise()), pos.up().offset(dir, 2).offset(dir.rotateYClockwise(), 3), random, positions);
            // TOP LAYER
            placeInIf(pos.up(2).offset(dir).offset(dir.rotateYClockwise()), pos.up(2).offset(dir).offset(dir.rotateYClockwise(), 3), random, positions);
            for (BlockPos blockPos : positions) {
                if (blockPos.getY() == treeNode.getCenter().down().getY() && random.nextInt(10) == 0) {
                    placeFruitingFoliage(world, replacer, random, config, blockPos);
                } else {
                    placeFoliageBlock(world, replacer, random, config, blockPos);
                }
            }
        } else {
            BlockPos pos = treeNode.getCenter().down(2).north(2).west();
            placeIn(pos, pos.south(4).east(2), positions);
            placeIn(pos.south(1).west(), pos.south(3).east(3), positions);
            placeIn(pos.up().south(1), pos.up().south(3).east(2), positions);
            positions.add(pos.up(2).south(2).east());
            // RANDOM SECTION - 2ND LAYER
            placeInIf(pos.up(), pos.up().east(2), random, positions);
            placeInIf(pos.up().south().west(), pos.up().south(3).west(), random, positions);
            placeInIf(pos.up().south().east(3), pos.up().south(3).east(3), random, positions);
            placeInIf(pos.up().south(4), pos.up().south(4).east(2), random, positions);
            // TOP LAYER
            placeInIf(pos.up(2).south().east(), pos.up(2).south(3).east(), random, positions);
            placeInIf(pos.up(2).south(2), pos.up(2).south(2).east(2), random, positions);
            for (BlockPos blockPos : positions) {
                if (blockPos.getY() == treeNode.getCenter().down(2).getY() && random.nextInt(10) == 0) {
                    placeFruitingFoliage(world, replacer, random, config, blockPos);
                } else {
                    placeFoliageBlock(world, replacer, random, config, blockPos);
                }
            }
        }
    }

    private void placeInIf(BlockPos pos1, BlockPos pos2, Random random, Set<BlockPos> posSet) {
        for (BlockPos blockPos : BlockPos.iterate(pos1, pos2)) {
            if (chanceTester(random)) {
                posSet.add(blockPos.toImmutable());
            }
        }
    }

    private void placeFruitingFoliage(TestableWorld world, FoliagePlacer.BlockPlacer replacer, Random random, TreeFeatureConfig config, BlockPos pos) {
        if (TreeFeature.canReplace(world, pos)) {
            BlockState blockState = config.foliageProvider.get(random, pos);
            if (blockState.contains(Properties.WATERLOGGED)) {
                blockState = blockState.with(Properties.WATERLOGGED, world.testFluidState(pos, fluidState -> fluidState.isEqualAndStill(Fluids.WATER)));
            }
            if (blockState.contains(PomegranateLeavesBlock.FRUITING)) {
                blockState = blockState.with(PomegranateLeavesBlock.FRUITING, random.nextInt(3)+1);
            }
            replacer.placeBlock(pos, blockState);
        }
    }

    private boolean chanceTester(Random random) {
        return random.nextInt(4) < 3;
    }

    private void placeIn(BlockPos pos1, BlockPos pos2, Set<BlockPos> posSet) {
        for (BlockPos blockPos : BlockPos.iterate(pos1, pos2)) {
            posSet.add(blockPos.toImmutable());
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
