package moriyashiine.aylyth.common.data.world;

import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;

public interface AylythNoises {

    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> FLOODEDNESS = bind("aquifer_fluid_level_floodedness");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> FLUID_SPREAD = bind("aquifer_fluid_level_spread");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> OFFSET = bind("offset");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> RIDGE = bind("ridge");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> JAGGED = bind("jagged");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CONTINENTS = bind("continents");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> TEMPERATURE = bind("temperature");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> VEGETATION = bind("vegetation");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> EROSION = bind("erosion");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> BASE_LAYER = bind("base_layer");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SURFACE = bind("surface");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CAVE_LAYER = bind("cave_layer");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CAVE_CHEESE = bind("cave_cheese");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D = bind("spaghetti_2d");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_MODULATOR = bind("spaghetti_2d_modulator");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_ROUGHNESS = bind("spaghetti_2d_roughness");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_THICKNESS = bind("spaghetti_2d_thickness");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_ELEVATION = bind("spaghetti_2d_elevation");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_3D_1 = bind("spaghetti_3d_1");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_3D_2 = bind("spaghetti_3d_2");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_3D_RARITY = bind("spaghetti_3d_rareness");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_3D_THICKNESS = bind("spaghetti_3d_thickness");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> NOODLE = bind("noodle");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> NOODLE_THICKNESS = bind("noodle_thickness");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> NOODLE_RIDGE_A = bind("noodle_ridge_a");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> NOODLE_RIDGE_B = bind("noodle_ridge_b");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PILLAR = bind("pillar");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PILLAR_RARENESS = bind("pillar_rareness");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PILLAR_THICKNESS = bind("pillar_thickness");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CAVE_ENTRANCES = bind("cave_entrances");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PODZOL_COMMON = bind("podzol_common");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> PODZOL_RARE = bind("podzol_rare");
    RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> BOWELS_SOUL_SAND = bind("bowels_soul_sand");

    private static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> bind(String name) {
        return RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, AylythUtil.id(name));
    }
}
