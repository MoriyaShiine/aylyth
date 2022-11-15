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
    protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        var pos = treeNode.getCenter();
        if (treeNode instanceof WrithewoodTreeNode dirNode) {
            var dir = dirNode.dir;
            generateBetween(world, replacer, random, config, pos.offset(dir.getOpposite()).offset(dir.rotateYCounterclockwise()), pos.offset(dir, 3).offset(dir.rotateYClockwise()));
            generateBetween(world, replacer, random, config, pos.offset(dir, 3), pos.offset(dir, 5));
            generateBetween(world, replacer, random, config, pos.offset(dir.rotateYCounterclockwise(), 2), pos.offset(dir, 2).offset(dir.rotateYCounterclockwise(), 2));
            generateBetween(world, replacer, random, config, pos.offset(dir.rotateYClockwise(), 2), pos.offset(dir, 2).offset(dir.rotateYClockwise(), 2));
            generateBetween(world, replacer, random, config, pos.offset(dir.getOpposite()).up(), pos.offset(dir, 2).up());
            generateBetween(world, replacer, random, config, pos.offset(dir.getOpposite()).down(), pos.offset(dir, 2).down());
        } else {
            generateSquare(world, replacer, random, config, pos, radius, -2, false);
            generateSquare(world, replacer, random, config, pos, radius+1, -1, false);
            generateSquare(world, replacer, random, config, pos, radius+2, 0, false);
            generateSquare(world, replacer, random, config, pos, radius+1, 1, false);
        }
    }

    protected void generateBetween(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, BlockPos pos1, BlockPos pos2) {
        for (BlockPos mutable : BlockPos.Mutable.iterate(pos1, pos2)) {
            placeFoliageBlock(world, replacer, random, config, mutable);
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
