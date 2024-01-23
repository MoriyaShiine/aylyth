package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModBiomeKeys {
    public static final RegistryKey<Biome> CLEARING = RegistryKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "clearing"));
    public static final RegistryKey<Biome> OVERGROWN_CLEARING = RegistryKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "overgrown_clearing"));
    public static final RegistryKey<Biome> COPSE = RegistryKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "copse"));
    public static final RegistryKey<Biome> DEEPWOOD = RegistryKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "deepwood"));
    public static final RegistryKey<Biome> CONIFEROUS_COPSE = RegistryKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "coniferous_copse"));
    public static final RegistryKey<Biome> CONIFEROUS_DEEPWOOD = RegistryKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "coniferous_deepwood"));
    public static final RegistryKey<Biome> UPLANDS = RegistryKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "uplands"));
    public static final RegistryKey<Biome> MIRE = RegistryKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "mire"));
    public static final RegistryKey<Biome> BOWELS = RegistryKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "bowels"));
}
