package moriyashiine.aylyth.common.block.types;

import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.mixin.SaplingBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class YmpeSaplingBlock extends SaplingBlock {
    protected final RegistryKey<ConfiguredFeature<?, ?>> failFeature;

    public YmpeSaplingBlock(SaplingGenerator generator, RegistryKey<ConfiguredFeature<?, ?>> failFeature, Settings settings) {
        super(generator, settings);
        this.failFeature = failFeature;
    }

    @Override
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, state.cycle(STAGE), Block.NO_REDRAW);
        } else {
            if (world.getRegistryKey() == AylythDimensionData.WORLD) {
                if (canGenerateLargeTree(world, pos, state) || random.nextFloat() <= 0.5) {
                    ((SaplingBlockAccessor) this).getGenerator().generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
                    return;
                }
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            ConfiguredFeature<?, ?> feature = world.getRegistryManager().get(RegistryKeys.CONFIGURED_FEATURE).get(failFeature);
            if (feature != null) {
                feature.generate(world, world.getChunkManager().getChunkGenerator(), random, pos);
            }
        }
    }

    public static boolean canGenerateLargeTree(ServerWorld world, BlockPos pos, BlockState state) {
        for (int i = 0; i >= -1; i--) {
            for (int j = 0; j >= -1; j--) {
                if (LargeTreeSaplingGenerator.canGenerateLargeTree(state, world, pos, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
