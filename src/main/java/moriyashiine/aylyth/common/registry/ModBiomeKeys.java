package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class ModBiomeKeys {
    public static final RegistryKey<Biome> CLEARING_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "clearing"));
    public static final RegistryKey<Biome> OVERGROWN_CLEARING_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "overgrown_clearing"));
    public static final RegistryKey<Biome> COPSE_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "copse"));
    public static final RegistryKey<Biome> DEEPWOOD_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "deepwood"));
    public static final RegistryKey<Biome> CONIFEROUS_COPSE_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "coniferous_copse"));
    public static final RegistryKey<Biome> CONIFEROUS_DEEPWOOD_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "coniferous_deepwood"));
    public static final RegistryKey<Biome> UPLANDS_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "uplands"));
    public static final RegistryKey<Biome> MIRE_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "mire"));
    public static final RegistryKey<Biome> BOWELS_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "bowels"));
}
