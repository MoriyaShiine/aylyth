package moriyashiine.aylyth.common.block.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BetterSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return null;
    }

    @Nullable
    protected abstract RegistryKey<ConfiguredFeature<?, ?>> getTreeKey(Random random, boolean bees);

    @Override
    public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
        var registryEntry = world.getRegistryManager().get(RegistryKeys.CONFIGURED_FEATURE).getEntry(this.getTreeKey(random, this.areFlowersNearby(world, pos)));
        if (registryEntry.isEmpty()) {
            return false;
        } else {
            ConfiguredFeature<?, ?> configuredFeature = registryEntry.get().value();
            BlockState blockState = world.getFluidState(pos).getBlockState();
            world.setBlockState(pos, blockState, Block.NO_REDRAW);
            if (configuredFeature.generate(world, chunkGenerator, random, pos)) {
                if (world.getBlockState(pos) == blockState) {
                    world.updateListeners(pos, state, blockState, Block.NOTIFY_LISTENERS);
                }

                return true;
            } else {
                world.setBlockState(pos, state, Block.NO_REDRAW);
                return false;
            }
        }
    }

    private boolean areFlowersNearby(WorldAccess world, BlockPos pos) {
        for(BlockPos blockPos : BlockPos.Mutable.iterate(pos.down().north(2).west(2), pos.up().south(2).east(2))) {
            if (world.getBlockState(blockPos).isIn(BlockTags.FLOWERS)) {
                return true;
            }
        }

        return false;
    }
}
