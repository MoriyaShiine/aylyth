package moriyashiine.aylyth.common.world.generator.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.foliage.FoliagePlacer;

public class DirectionalTreeNode extends FoliagePlacer.TreeNode {

    public final Direction dir;

    public DirectionalTreeNode(BlockPos center, int foliageRadius, boolean giantTrunk, Direction dir) {
        super(center, foliageRadius, giantTrunk);
        this.dir = dir;
    }
}
