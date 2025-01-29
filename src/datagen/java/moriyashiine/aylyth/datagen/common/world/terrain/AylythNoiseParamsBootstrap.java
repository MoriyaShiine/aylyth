package moriyashiine.aylyth.datagen.common.world.terrain;

import net.minecraft.registry.Registerable;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;

import static moriyashiine.aylyth.common.data.world.terrain.AylythNoiseParams.*;

public final class AylythNoiseParamsBootstrap {
    private AylythNoiseParamsBootstrap() {}

    public static void bootstrap(Registerable<NoiseParameters> context) {
        // VANILLACOPY BuiltinNoiseParameters::bootstrap

        context.register(FLOODEDNESS, new NoiseParameters(-7, 1));
        context.register(FLUID_SPREAD, new NoiseParameters(-5, 1));

        context.register(TEMPERATURE, new NoiseParameters(-10, 1.5, 0, 1, 0, 0, 0));
        context.register(VEGETATION, new NoiseParameters(-8, 1, 1, 0, 0, 0, 0));
        context.register(CONTINENTS, new NoiseParameters(-9, 1, 1, 2, 2, 2, 1, 1, 1, 1));
        context.register(EROSION, new NoiseParameters(-9, 1, 1, 0, 0, 1, 1)); // [1, 0, 1, 1] -> [1, 0, 0, 1, 1]
        context.register(RIDGES, new NoiseParameters(-7, 1, 2, 1, 0, 0, 0));

        context.register(OFFSET, new NoiseParameters(-3, 1, 1, 1, 0));
        context.register(JAGGED, new NoiseParameters(-16, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));

        context.register(CAVE_LAYER, new NoiseParameters(-8, 1));
        context.register(CAVE_CHEESE, new NoiseParameters(-8, 0.5, 1, 2, 1, 2, 1, 0, 2, 0));
        context.register(SPAGHETTI_2D, new NoiseParameters(-7, 1));
        context.register(SPAGHETTI_2D_MODULATOR, new NoiseParameters(-11, 1));
        context.register(SPAGHETTI_2D_THICKNESS, new NoiseParameters(-11, 1));
        context.register(SPAGHETTI_2D_ELEVATION, new NoiseParameters(-8, 1));
        context.register(SPAGHETTI_ROUGHNESS, new NoiseParameters(-5, 1));
        context.register(SPAGHETTI_ROUGHNESS_MODULATOR, new NoiseParameters(-8, 1));
        context.register(PILLAR, new NoiseParameters(-7, 1, 1));
        context.register(PILLAR_RARITY, new NoiseParameters(-8, 1));
        context.register(PILLAR_THICKNESS, new NoiseParameters(-8, 1));
        context.register(NOODLE, new NoiseParameters(-8, 1));
        context.register(NOODLE_THICKNESS, new NoiseParameters(-8, 1));
        context.register(NOODLE_RIDGE_A, new NoiseParameters(-7, 1));
        context.register(NOODLE_RIDGE_B, new NoiseParameters(-7, 1));

        // Own

        context.register(SURFACE, new NoiseParameters(-6, 1, 1, 1));

        context.register(PODZOL_PATCHES, new NoiseParameters(-5, 1, 1, 1));
        context.register(UPLANDS_SAPSTONE_RINGS, new NoiseParameters(-5, 1));
        context.register(UPLANDS_LIGNITE_SAPSTONE_PATCHES, new NoiseParameters(-5, 1, 1, 1));
        context.register(DEEPWOOD_PODZOL_VEINS, new NoiseParameters(-6, 1, 1, 1));
        context.register(DEEPWOOD_DARK_PODZOL_PATCHES, new NoiseParameters(-5, 1, 1));
        context.register(COPSE_DARK_PODZOL_VEINS, new NoiseParameters(-6, 1, 1, 1));
        context.register(BOWELS_SOUL_SAND_PATCHES, new NoiseParameters(-5, 1, 1, 1));
    }
}
