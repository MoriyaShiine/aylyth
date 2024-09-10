package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class AylythBiomeSources {
    private AylythBiomeSources() {}

    public static void register() {
        // TODO remove "_biome_provider"
        Registry.register(Registries.BIOME_SOURCE, AylythUtil.id("aylyth_biome_provider"), AylythBiomeSource.CODEC);
    }
}
