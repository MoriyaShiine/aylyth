package moriyashiine.aylyth.datagen;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;

public class AylythNoiseTypes {

    public static void init() {}

    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> OFFSET_KEY = noiseKey("offset");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> RIDGE_KEY = noiseKey("ridge");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> JAGGED_KEY = noiseKey("jagged");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CONTINENTS_KEY = noiseKey("continents");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> TEMPERATURE_KEY = noiseKey("temperature");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> VEGETATION_KEY = noiseKey("vegetation");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> EROSION_KEY = noiseKey("erosion");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> BASE_LAYER_KEY = noiseKey("base_layer");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CAVE_CHEESE_KEY = noiseKey("cave_cheese");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CAVE_ENTRANCES_KEY = noiseKey("cave_entrances");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_MODULATOR_KEY = noiseKey("spaghetti_2d_modulator");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_ROUGHNESS_KEY = noiseKey("spaghetti_roughness");
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_THICKNESS_KEY = noiseKey("spaghetti_2d_thickness");
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> OFFSET;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> RIDGE;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> JAGGED;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> CONTINENTS;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> TEMPERATURE;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> VEGETATION;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> EROSION;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> BASE_LAYER;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> CAVE_CHEESE;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> CAVE_ENTRANCES;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_MODULATOR;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_ROUGHNESS;
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> SPAGHETTI_2D_THICKNESS;

    private static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> noiseKey(String id) {
        return RegistryKey.of(Registry.NOISE_KEY, new Identifier(Aylyth.MOD_ID, id));
    }

    private static RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> registerNoise(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key, int firstOctave, double... amplitudes) {
        return BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, key, new DoublePerlinNoiseSampler.NoiseParameters(firstOctave, DoubleList.of(amplitudes)));
    }

    static {
        OFFSET = registerNoise(OFFSET_KEY, -3, 1.0, 1.0, 1.0, 0.0);
        RIDGE = registerNoise(RIDGE_KEY, -7, 1.0, 2.0, 1.0, 0.0, 0.0, 0.0);
        JAGGED = registerNoise(JAGGED_KEY, -16, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
        CONTINENTS = registerNoise(CONTINENTS_KEY, -9, 1.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0);
        TEMPERATURE = registerNoise(TEMPERATURE_KEY,-10, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0);
        VEGETATION = registerNoise(VEGETATION_KEY,-8, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0);
        EROSION = registerNoise(EROSION_KEY, -9, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0);
        BASE_LAYER = registerNoise(BASE_LAYER_KEY,-8, 1.0, 1.0, 1.0, 1.0);
        CAVE_CHEESE = registerNoise(CAVE_CHEESE_KEY, -8, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 0.0, 2.0, 0.0);
        CAVE_ENTRANCES = registerNoise(CAVE_ENTRANCES_KEY, -7, 0.4, 0.5, 1.0);
        SPAGHETTI_2D_MODULATOR = registerNoise(SPAGHETTI_2D_MODULATOR_KEY, -11, 1.0);
        SPAGHETTI_ROUGHNESS = registerNoise(SPAGHETTI_ROUGHNESS_KEY, -5, 1.0);
        SPAGHETTI_2D_THICKNESS = registerNoise(SPAGHETTI_2D_THICKNESS_KEY, -11, 1.0);
    }
}
