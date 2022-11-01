package moriyashiine.aylyth.common.world.generator.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.StrewnLeavesBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class LeafPileFeature extends Feature<LeafPileFeature.LeafPileConfig> {
    public LeafPileFeature() {
        super(LeafPileFeature.LeafPileConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<LeafPileFeature.LeafPileConfig> context) {
        var testBlock = context.getConfig().testBlock;
        var block = context.getConfig().block;
        var world = context.getWorld();
        var random = context.getRandom();
        BlockPos origin;
        origin = context.getOrigin();
        origin = origin.withY(world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, origin.getX(), origin.getZ()));
        if (world.testBlockState(origin, AbstractBlock.AbstractBlockState::isAir)) {
            for (int i = 0; i < 30; i++) {
                if (world.testBlockState(origin.up(i), blockState -> blockState.getBlock() == testBlock)) {
                    setBlockState(world, origin, block.getDefaultState().with(StrewnLeavesBlock.LEAVES, random.nextBetween(5, 7)));
                    for (Direction dir : Direction.Type.HORIZONTAL) {
                        var offset = origin.offset(dir, 1);
                        var setState = block.getDefaultState().with(StrewnLeavesBlock.LEAVES, random.nextBetween(0, 5));
                        if (world.isAir(offset) && setState.canPlaceAt(world, offset)) {
                            setBlockState(world, offset, setState);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static class LeafPileConfig implements FeatureConfig {
        public static final Codec<LeafPileFeature.LeafPileConfig> CODEC = RecordCodecBuilder.create(strewnLeavesConfigInstance -> strewnLeavesConfigInstance
                .group(
                        Registry.BLOCK.getCodec().fieldOf("test_block").forGetter(strewnLeavesConfig -> strewnLeavesConfig.testBlock),
                        Registry.BLOCK.getCodec().fieldOf("block").forGetter(strewnLeavesConfig -> strewnLeavesConfig.block)
                )
                .apply(strewnLeavesConfigInstance, LeafPileFeature.LeafPileConfig::new));
        public final Block testBlock;
        public final StrewnLeavesBlock block;

        public LeafPileConfig(Block testBlock, Block block) {
            if (block instanceof StrewnLeavesBlock strewnLeavesBlock) {
                this.block = strewnLeavesBlock;
            } else {
                throw new IllegalArgumentException("%s is not a valid StrewnLeavesBlock.".formatted(block));
            }
            this.testBlock = testBlock;
        }
    }
}
