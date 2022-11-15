package moriyashiine.aylyth.common.world.generator.feature;

import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.property.Properties;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class DoubleBlockFeature extends Feature<SimpleBlockFeatureConfig> {

    public DoubleBlockFeature() {
        super(SimpleBlockFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        var world = context.getWorld();
        var rand = context.getRandom();
        var pos = context.getOrigin();
        var state = context.getConfig().toPlace().getBlockState(rand, pos);
        if (state.contains(Properties.DOUBLE_BLOCK_HALF) && state.canPlaceAt(world, pos)) {
            setBlockState(world, pos, state);
            setBlockState(world, pos.up(), state.with(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER));
            return true;
        }
        return false;
    }
}
