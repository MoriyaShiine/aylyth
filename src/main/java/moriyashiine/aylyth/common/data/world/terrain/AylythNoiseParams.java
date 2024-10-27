package moriyashiine.aylyth.common.data.world.terrain;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;

public interface AylythNoiseParams {

    RegistryKey<NoiseParameters> FLOODEDNESS = bind("aquifer_fluid_level_floodedness");
    RegistryKey<NoiseParameters> FLUID_SPREAD = bind("aquifer_fluid_level_spread");

    RegistryKey<NoiseParameters> TEMPERATURE = bind("temperature");
    RegistryKey<NoiseParameters> VEGETATION = bind("vegetation");
    RegistryKey<NoiseParameters> CONTINENTS = bind("continents");
    RegistryKey<NoiseParameters> EROSION = bind("erosion");
    RegistryKey<NoiseParameters> RIDGES = bind("ridges");

    RegistryKey<NoiseParameters> OFFSET = bind("offset");
    RegistryKey<NoiseParameters> JAGGED = bind("jagged");

    RegistryKey<NoiseParameters> SPAGHETTI_2D = bind("spaghetti_2d");
    RegistryKey<NoiseParameters> SPAGHETTI_2D_MODULATOR = bind("spaghetti_2d_modulator");
    RegistryKey<NoiseParameters> SPAGHETTI_2D_THICKNESS = bind("spaghetti_2d_thickness");
    RegistryKey<NoiseParameters> SPAGHETTI_2D_ELEVATION = bind("spaghetti_2d_elevation");
    RegistryKey<NoiseParameters> SPAGHETTI_ROUGHNESS = bind("spaghetti_roughness");
    RegistryKey<NoiseParameters> SPAGHETTI_ROUGHNESS_MODULATOR = bind("spaghetti_roughness_modulator");
    RegistryKey<NoiseParameters> PILLAR = bind("pillar");
    RegistryKey<NoiseParameters> PILLAR_RARITY = bind("pillar_rarity");
    RegistryKey<NoiseParameters> PILLAR_THICKNESS = bind("pillar_thickness");
    RegistryKey<NoiseParameters> NOODLE = bind("noodle");
    RegistryKey<NoiseParameters> NOODLE_THICKNESS = bind("noodle_thickness");
    RegistryKey<NoiseParameters> NOODLE_RIDGE_A = bind("noodle_ridge_a");
    RegistryKey<NoiseParameters> NOODLE_RIDGE_B = bind("noodle_ridge_b");
    RegistryKey<NoiseParameters> CAVE_LAYER = bind("cave_layer");
    RegistryKey<NoiseParameters> CAVE_CHEESE = bind("cave_cheese");

    RegistryKey<NoiseParameters> SURFACE = bind("surface");
    RegistryKey<NoiseParameters> PODZOL_COMMON = bind("podzol_common");
    RegistryKey<NoiseParameters> PODZOL_RARE = bind("podzol_rare");
    RegistryKey<NoiseParameters> BOWELS_SOUL_SAND = bind("bowels_soul_sand");

    private static RegistryKey<NoiseParameters> bind(String name) {
        return RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, Aylyth.id(name));
    }
}
