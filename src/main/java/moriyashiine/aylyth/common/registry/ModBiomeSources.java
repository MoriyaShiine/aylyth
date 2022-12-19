package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBiomeSources {

    public static void init() {}

    static {
        Registry.register(Registry.BIOME_SOURCE, new Identifier(Aylyth.MOD_ID, "aylyth_biome_provider"), AylythBiomeSource.CODEC);
    }
}
