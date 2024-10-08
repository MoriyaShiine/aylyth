package moriyashiine.aylyth.common.block.sapling;

import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class SimpleLargeSaplingGenerator extends LargeTreeSaplingGenerator {
    private final RegistryKey<ConfiguredFeature<?, ?>> tree;
    private final RegistryKey<ConfiguredFeature<?, ?>> largeTree;

    public SimpleLargeSaplingGenerator(RegistryKey<ConfiguredFeature<?, ?>> tree, RegistryKey<ConfiguredFeature<?, ?>> largeTree) {
        this.tree = tree;
        this.largeTree = largeTree;
    }

    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return tree;
    }

    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return largeTree;
    }
}
