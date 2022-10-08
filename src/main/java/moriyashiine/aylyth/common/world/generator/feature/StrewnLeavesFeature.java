package moriyashiine.aylyth.common.world.generator.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.StrewnLeavesBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class StrewnLeavesFeature extends Feature<StrewnLeavesFeature.StrewnLeavesConfig> {
    public StrewnLeavesFeature() {
        super(StrewnLeavesConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<StrewnLeavesConfig> context) {
        var testState = context.getConfig().testState;
        var block = context.getConfig().block;
        var range = context.getConfig().range;
        var world = context.getWorld();
        var random = context.getRandom();
        BlockPos origin;
        origin = context.getOrigin();
        origin = origin.withY(world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, origin.getX(), origin.getZ()));
        if (world.testBlockState(origin, AbstractBlock.AbstractBlockState::isAir)) {
            for (int i = 0; i < 30; i++) {
                if (world.testBlockState(origin.up(i), blockState -> blockState.getBlock() == testState.getBlock())) {
                    if (random.nextFloat() > 0.75) {
                        setBlockState(world, origin, block.getDefaultState().with(StrewnLeavesBlock.LEAVES, random.nextBetween(5, 7)));
                        for (Direction dir : Direction.Type.HORIZONTAL) {
                            var offset = origin.offset(dir, 1);
                            var setState = block.getDefaultState().with(StrewnLeavesBlock.LEAVES, random.nextBetween(0, 5));
                            if (world.isAir(offset) && block.canPlaceAt(setState, world, offset)) {
                                setBlockState(world, offset, setState);
                            }
                        }
                    }
                    break;
                }
            }
        }
        return false;
    }

    public static class StrewnLeavesConfig implements FeatureConfig {
        public static final Codec<StrewnLeavesConfig> CODEC = RecordCodecBuilder.create(strewnLeavesConfigInstance -> strewnLeavesConfigInstance
                .group(
                        BlockState.CODEC.fieldOf("testState").forGetter(strewnLeavesConfig -> strewnLeavesConfig.testState),
                        Registry.BLOCK.getCodec().fieldOf("block").forGetter(strewnLeavesConfig -> strewnLeavesConfig.block),
                        Codec.INT.fieldOf("range").forGetter(strewnLeavesConfig -> strewnLeavesConfig.range))
                .apply(strewnLeavesConfigInstance, StrewnLeavesConfig::new));
        public final BlockState testState;
        public final StrewnLeavesBlock block;
        public final int range;

        public StrewnLeavesConfig(BlockState testState, Block block, int range) {
            if (block instanceof StrewnLeavesBlock strewnLeavesBlock) {
                this.block = strewnLeavesBlock;
            } else {
                throw new IllegalArgumentException("%s is not a valid StrewnLeavesBlock.".formatted(block));
            }
            this.testState = testState;
            this.range = range;
        }
    }
}
