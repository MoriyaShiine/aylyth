package moriyashiine.aylyth.common.block.sapling;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class SimpleSaplingGenerator extends SaplingGenerator {
    private final RegistryKey<ConfiguredFeature<?, ?>> tree;

    public SimpleSaplingGenerator(RegistryKey<ConfiguredFeature<?, ?>> tree) {
        this.tree = tree;
    }

    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return tree;
    }
}
