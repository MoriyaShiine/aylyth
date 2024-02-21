package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;

public class ModNoiseKeys {
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> FLOODEDNESS = createKey("aquifer_fluid_level_floodedness");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> FLUID_SPREAD = createKey("aquifer_fluid_level_spread");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> OFFSET = createKey("offset");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> RIDGE = createKey("ridge");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> JAGGED = createKey("jagged");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CONTINENTS = createKey("continents");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> TEMPERATURE = createKey("temperature");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> VEGETATION = createKey("vegetation");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> EROSION = createKey("erosion");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> BASE_LAYER = createKey("base_layer");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SURFACE = createKey("surface");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CAVE_LAYER = createKey("cave_layer");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CAVE_CHEESE = createKey("cave_cheese");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D = createKey("spaghetti_2d");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_MODULATOR = createKey("spaghetti_2d_modulator");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_ROUGHNESS = createKey("spaghetti_2d_roughness");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_THICKNESS = createKey("spaghetti_2d_thickness");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_ELEVATION = createKey("spaghetti_2d_elevation");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_3D_1 = createKey("spaghetti_3d_1");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_3D_2 = createKey("spaghetti_3d_2");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_3D_RARITY = createKey("spaghetti_3d_rareness");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_3D_THICKNESS = createKey("spaghetti_3d_thickness");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> NOODLE = createKey("noodle");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> NOODLE_THICKNESS = createKey("noodle_thickness");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> NOODLE_RIDGE_A = createKey("noodle_ridge_a");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> NOODLE_RIDGE_B = createKey("noodle_ridge_b");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PILLAR = createKey("pillar");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PILLAR_RARENESS = createKey("pillar_rareness");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PILLAR_THICKNESS = createKey("pillar_thickness");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CAVE_ENTRANCES = createKey("cave_entrances");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PODZOL_COMMON = createKey("podzol_common");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PODZOL_RARE = createKey("podzol_rare");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> BOWELS_SOUL_SAND = createKey("bowels_soul_sand");

    private static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> createKey(String id) {
        return RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, AylythUtil.id(id));
    }
}
