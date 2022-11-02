package moriyashiine.aylyth.common.world.generator.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class HorizontalFacingFeature extends Feature<HorizontalFacingFeature.HorizontalFacingBlockFeatureConfig> {

    public HorizontalFacingFeature() {
        super(HorizontalFacingBlockFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<HorizontalFacingBlockFeatureConfig> context) {
        var block = context.getConfig().facingBlock;
        var origin = context.getOrigin();
        BlockPos testPos;
        var world = context.getWorld();
        for (Direction dir : Direction.Type.HORIZONTAL) {
            testPos = origin.offset(dir);
            var oppDir = dir.getOpposite();
            var state = block.getDefaultState().with(Properties.HORIZONTAL_FACING, oppDir);
            if (world.isAir(origin) && testSideSolidFullValid(context.getConfig().tag, world, testPos, oppDir) && state.canPlaceAt(world, origin)) {
               setBlockState(world, origin, state);
            }
        }
        return false;
    }

    private boolean testSideSolidFullValid(TagKey<Block> test, StructureWorldAccess world, BlockPos pos, Direction dir) {
        return world.testBlockState(pos, blockState -> blockState.isSideSolidFullSquare(world, pos, dir) && blockState.isIn(test));
    }

    public static class HorizontalFacingBlockFeatureConfig implements FeatureConfig {
        public static final Codec<HorizontalFacingBlockFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Registry.BLOCK.getCodec().fieldOf("facing_block").forGetter(config -> config.facingBlock),
                TagKey.codec(Registry.BLOCK_KEY).fieldOf("test_against").forGetter(config -> config.tag)
        ).apply(instance, HorizontalFacingBlockFeatureConfig::new));

        final Block facingBlock;
        final TagKey<Block> tag;

        public HorizontalFacingBlockFeatureConfig(Block facingBlock, TagKey<Block> tag) {
            if (facingBlock.getStateManager().getProperty("facing") == null) {
                throw new IllegalArgumentException("FacingBlockFeatureConfig must have a block with the 'facing' block state");
            }
            this.facingBlock = facingBlock;
            this.tag = tag;
        }
    }
}
