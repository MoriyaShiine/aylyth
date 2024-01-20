package moriyashiine.aylyth.common.world.generator.foliageplacer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.registry.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.function.BiConsumer;

public class WrithewoodFoliagePlacer extends FoliagePlacer {
    public static final Codec<WrithewoodFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> fillFoliagePlacerFields(instance).apply(instance, WrithewoodFoliagePlacer::new));

    public WrithewoodFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModFeatures.WRITHEWOOD_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, FoliagePlacer.BlockPlacer replacer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos pos = treeNode.getCenter();
        if (treeNode instanceof WrithewoodTreeNode dirNode) {
            Direction dir = dirNode.dir;
            int length = dirNode.branchLength;
            generateBetween(world, replacer, random, config, pos.offset(dir.getOpposite(), length > 2 ? 1 : 0).offset(dir.rotateYCounterclockwise()), pos.offset(dir, length/2).offset(dir.rotateYClockwise()));
            generateBetween(world, replacer, random, config, pos.offset(dir, length > 3 ? 2 : 1), pos.offset(dir, length > 3 ? 4 : 2));
            if (length > 3) {
                generateBetween(world, replacer, random, config, pos.offset(dir.rotateYCounterclockwise(), 2), pos.offset(dir).offset(dir.rotateYCounterclockwise(), 2), 0.5f);
                generateBetween(world, replacer, random, config, pos.offset(dir.rotateYClockwise(), 2), pos.offset(dir).offset(dir.rotateYClockwise(), 2), 0.5f);
            }
            // These two method calls generate some leaves on the top and bottom of the logs. I don't think it looks good though
//            generateBetween(world, replacer, random, config, pos.offset(dir.getOpposite()).up(), pos.offset(dir, 2).up());
//            generateBetween(world, replacer, random, config, pos.offset(dir.getOpposite()).down(), pos.offset(dir, 2).down());
        } else {
            generateSquare(world, replacer, random, config, pos, radius, -2, false);
            generateSquare(world, replacer, random, config, pos, radius+1, -1, false);
            generateSquare(world, replacer, random, config, pos, radius+2, 0, false);
            generateSquare(world, replacer, random, config, pos, radius+1, 1, false);
        }
    }

    protected void generateBetween(TestableWorld world, FoliagePlacer.BlockPlacer replacer, Random random, TreeFeatureConfig config, BlockPos pos1, BlockPos pos2) {
        generateBetween(world, replacer, random, config, pos1, pos2, 1f);
    }

    protected void generateBetween(TestableWorld world, FoliagePlacer.BlockPlacer replacer, Random random, TreeFeatureConfig config, BlockPos pos1, BlockPos pos2, float placementChance) {
        for (BlockPos mutable : BlockPos.Mutable.iterate(pos1, pos2)) {
            if (random.nextFloat() <= placementChance) {
                placeFoliageBlock(world, replacer, random, config, mutable);
            }
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return 3;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return dx == radius && dz == radius;
    }

    public static class WrithewoodTreeNode extends DirectionalTreeNode {

        public final int branchLength;

        public WrithewoodTreeNode(BlockPos center, int foliageRadius, boolean giantTrunk, Direction dir, int branchlength) {
            super(center, foliageRadius, giantTrunk, dir);
            this.branchLength = branchlength;
        }
    }
}
