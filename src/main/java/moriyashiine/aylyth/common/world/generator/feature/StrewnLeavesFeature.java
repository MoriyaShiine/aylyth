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
        var testBlock = context.getConfig().testBlock;
        var strewnState = context.getConfig().strewnState;
        var world = context.getWorld();
        var random = context.getRandom();
        BlockPos origin = context.getOrigin();
        for (BlockPos position : BlockPos.iterateRandomly(random, 10, origin, 3)) {
            position = position.withY(world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, position.getX(), position.getZ()));
            if (world.testBlockState(position, AbstractBlock.AbstractBlockState::isAir)) {
                for (int i = 0; i < 30; i++) {
                    if (world.testBlockState(position.up(i), blockState -> blockState.getBlock() == testBlock)) {
                        setBlockState(world, position, strewnState);
                        break;
                    }
                }
            }
        }
        return true;
    }

    public static class StrewnLeavesConfig implements FeatureConfig {
        public static final Codec<StrewnLeavesConfig> CODEC = RecordCodecBuilder.create(strewnLeavesConfigInstance -> strewnLeavesConfigInstance
                .group(
                        Registry.BLOCK.getCodec().fieldOf("test_block").forGetter(strewnLeavesConfig -> strewnLeavesConfig.testBlock),
                        BlockState.CODEC.fieldOf("block").forGetter(strewnLeavesConfig -> strewnLeavesConfig.strewnState)
                )
                .apply(strewnLeavesConfigInstance, StrewnLeavesConfig::new));
        public final Block testBlock;
        public final BlockState strewnState;

        public StrewnLeavesConfig(Block testBlock, BlockState strewnState) {
            this.strewnState = strewnState;
            this.testBlock = testBlock;
        }
    }
}
