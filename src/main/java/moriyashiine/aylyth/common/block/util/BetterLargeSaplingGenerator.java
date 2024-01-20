package moriyashiine.aylyth.common.block.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BetterLargeSaplingGenerator extends BetterSaplingGenerator {

    @Override
    public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
        for(int i = 0; i >= -1; --i) {
            for(int j = 0; j >= -1; --j) {
                if (LargeTreeSaplingGenerator.canGenerateLargeTree(state, world, pos, i, j)) {
                    return this.generateLargeTree(world, chunkGenerator, pos, state, random, i, j);
                }
            }
        }

        return super.generate(world, chunkGenerator, pos, state, random);
    }

    @Nullable
    protected abstract RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeKey(Random random);

    public boolean generateLargeTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random, int x, int z) {
        var registryEntry = world.getRegistryManager().get(RegistryKeys.CONFIGURED_FEATURE).getEntry(this.getLargeTreeKey(random));
        if (registryEntry.isEmpty()) {
            return false;
        } else {
            ConfiguredFeature<?, ?> configuredFeature = registryEntry.get().value();
            BlockState blockState = Blocks.AIR.getDefaultState();
            world.setBlockState(pos.add(x, 0, z), blockState, Block.NO_REDRAW);
            world.setBlockState(pos.add(x + 1, 0, z), blockState, Block.NO_REDRAW);
            world.setBlockState(pos.add(x, 0, z + 1), blockState, Block.NO_REDRAW);
            world.setBlockState(pos.add(x + 1, 0, z + 1), blockState, Block.NO_REDRAW);
            if (configuredFeature.generate(world, chunkGenerator, random, pos.add(x, 0, z))) {
                return true;
            } else {
                world.setBlockState(pos.add(x, 0, z), state, Block.NO_REDRAW);
                world.setBlockState(pos.add(x + 1, 0, z), state, Block.NO_REDRAW);
                world.setBlockState(pos.add(x, 0, z + 1), state, Block.NO_REDRAW);
                world.setBlockState(pos.add(x + 1, 0, z + 1), state, Block.NO_REDRAW);
                return false;
            }
        }
    }
}
