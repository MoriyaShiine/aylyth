package moriyashiine.aylyth.common.world.gen.features;

import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class DoubleBlockFeature extends Feature<SimpleBlockFeatureConfig> {

    public DoubleBlockFeature() {
        super(SimpleBlockFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random rand = context.getRandom();
        BlockPos pos = context.getOrigin();
        BlockState state = context.getConfig().toPlace().get(rand, pos);
        if (state.contains(Properties.DOUBLE_BLOCK_HALF) && state.canPlaceAt(world, pos)) {
            if (state.contains(Properties.WATERLOGGED) && world.getFluidState(pos).getFluid() == Fluids.WATER) {
                state = state.with(Properties.WATERLOGGED, true);
            }
            setBlockState(world, pos, state);
            setBlockState(world, pos.up(), state.with(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER).with(Properties.WATERLOGGED, world.getFluidState(pos.up()).getFluid() == Fluids.WATER));
            return true;
        }
        return false;
    }
}
