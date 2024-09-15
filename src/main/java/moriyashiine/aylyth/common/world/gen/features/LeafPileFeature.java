package moriyashiine.aylyth.common.world.gen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.types.StrewnLeavesBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class LeafPileFeature extends Feature<LeafPileFeature.LeafPileConfig> {
    public LeafPileFeature() {
        super(LeafPileConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<LeafPileConfig> context) {
        Block testBlock = context.getConfig().testBlock;
        Block block = context.getConfig().block;
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin;
        origin = context.getOrigin();
        origin = origin.withY(world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, origin.getX(), origin.getZ()));
        if (world.testBlockState(origin, AbstractBlock.AbstractBlockState::isAir)) {
            for (int i = 0; i < 30; i++) {
                if (world.testBlockState(origin.up(i), blockState -> blockState.getBlock() == testBlock)) {
                    setBlockState(world, origin, block.getDefaultState().with(StrewnLeavesBlock.LEAVES, random.nextBetween(5, 7)));
                    for (Direction dir : Direction.Type.HORIZONTAL) {
                        BlockPos offset = origin.offset(dir, 1);
                        BlockState setState = block.getDefaultState().with(StrewnLeavesBlock.LEAVES, random.nextBetween(0, 5));
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
        public static final Codec<LeafPileConfig> CODEC = RecordCodecBuilder.create(strewnLeavesConfigInstance -> strewnLeavesConfigInstance
                .group(
                        Registries.BLOCK.getCodec().fieldOf("test_block").forGetter(strewnLeavesConfig -> strewnLeavesConfig.testBlock),
                        Registries.BLOCK.getCodec().fieldOf("block").forGetter(strewnLeavesConfig -> strewnLeavesConfig.block)
                )
                .apply(strewnLeavesConfigInstance, LeafPileConfig::new));
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
