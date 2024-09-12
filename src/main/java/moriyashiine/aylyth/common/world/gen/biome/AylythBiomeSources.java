package moriyashiine.aylyth.common.world.gen.biome;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class AylythBiomeSources {
    private AylythBiomeSources() {}

    public static void register() {
        // TODO remove "_biome_provider"
        Registry.register(Registries.BIOME_SOURCE, AylythUtil.id("aylyth_biome_provider"), AylythBiomeSource.CODEC);
    }
}
