package moriyashiine.aylyth.common.data.world;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

public interface AylythBiomes {

    RegistryKey<Biome> CLEARING = bind("clearing");
    RegistryKey<Biome> OVERGROWN_CLEARING = bind("overgrown_clearing");
    RegistryKey<Biome> COPSE = bind("copse");
    RegistryKey<Biome> DEEPWOOD = bind("deepwood");
    RegistryKey<Biome> CONIFEROUS_COPSE = bind("coniferous_copse");
    RegistryKey<Biome> CONIFEROUS_DEEPWOOD = bind("coniferous_deepwood");
    RegistryKey<Biome> UPLANDS = bind("uplands");
    RegistryKey<Biome> MIRE = bind("mire");
    RegistryKey<Biome> BOWELS = bind("bowels");

    private static RegistryKey<Biome> bind(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, AylythUtil.id(name));
    }
}
