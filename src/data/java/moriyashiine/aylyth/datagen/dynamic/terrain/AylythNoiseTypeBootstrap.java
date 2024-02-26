package moriyashiine.aylyth.datagen.dynamic.terrain;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.registry.Registerable;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;

import static moriyashiine.aylyth.common.registry.key.ModNoiseKeys.*;

public class AylythNoiseTypeBootstrap {

    public static void init() {}

    public static void bootstrap(Registerable<NoiseParameters> context) {
        context.register(FLOODEDNESS, new NoiseParameters(-7, DoubleList.of(1.0)));
        context.register(FLUID_SPREAD, new NoiseParameters(-5, DoubleList.of(1.0)));
        context.register(OFFSET, new NoiseParameters(-3, DoubleList.of(1.0, 1.0, 1.0, 0.0)));
        context.register(RIDGE, new NoiseParameters(-7, DoubleList.of(1.0, 2.0, 1.0, 0.0, 0.0, 0.0)));
        context.register(JAGGED, new NoiseParameters(-16, DoubleList.of(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)));
        context.register(CONTINENTS, new NoiseParameters(-9, DoubleList.of(1.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0)));
        context.register(TEMPERATURE, new NoiseParameters(-10, DoubleList.of(1.5, 0.0, 1.0, 0.0, 0.0, 0.0)));
        context.register(VEGETATION, new NoiseParameters(-8, DoubleList.of(1.0, 1.0, 0.0, 0.0, 0.0, 0.0)));
        context.register(EROSION, new NoiseParameters(-9, DoubleList.of(1.0, 1.0, 0.0, 0.0, 1.0, 1.0)));
        context.register(BASE_LAYER, new NoiseParameters(-8, DoubleList.of(1.0, 1.0, 1.0, 1.0)));
        context.register(SURFACE, new NoiseParameters(-6, DoubleList.of(1.0, 1.0, 1.0)));
        context.register(CAVE_LAYER, new NoiseParameters(-8, DoubleList.of(1.0)));
        context.register(CAVE_CHEESE, new NoiseParameters(-8, DoubleList.of(0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 0.0, 2.0, 0.0)));
        context.register(SPAGHETTI_2D, new NoiseParameters(-7, DoubleList.of(1.0)));
        context.register(SPAGHETTI_2D_MODULATOR, new NoiseParameters(-11, DoubleList.of(1.0)));
        context.register(SPAGHETTI_2D_ROUGHNESS, new NoiseParameters(-5, DoubleList.of(1.0)));
        context.register(SPAGHETTI_2D_THICKNESS, new NoiseParameters(-11, DoubleList.of(1.0)));
        context.register(SPAGHETTI_2D_ELEVATION, new NoiseParameters(-8, DoubleList.of(1.0)));
        context.register(SPAGHETTI_3D_1, new NoiseParameters(-7, DoubleList.of(1.0)));
        context.register(SPAGHETTI_3D_2, new NoiseParameters(-7, DoubleList.of(1.0)));
        context.register(SPAGHETTI_3D_RARITY, new NoiseParameters(-11, DoubleList.of(1.0)));
        context.register(SPAGHETTI_3D_THICKNESS, new NoiseParameters(-11, DoubleList.of(1.0)));
        context.register(NOODLE, new NoiseParameters(-8, DoubleList.of(1.0)));
        context.register(NOODLE_THICKNESS, new NoiseParameters(-8, DoubleList.of(1.0)));
        context.register(NOODLE_RIDGE_A, new NoiseParameters(-7, DoubleList.of(1.0)));
        context.register(NOODLE_RIDGE_B, new NoiseParameters(-7, DoubleList.of(1.0)));
        context.register(PILLAR, new NoiseParameters(-7, DoubleList.of(1.0, 1.0)));
        context.register(PILLAR_RARENESS, new NoiseParameters(-8, DoubleList.of(1.0)));
        context.register(PILLAR_THICKNESS, new NoiseParameters(-8, DoubleList.of(1.0)));
        context.register(CAVE_ENTRANCES, new NoiseParameters(-7, DoubleList.of(0.4, 0.5, 1.0)));
        context.register(PODZOL_COMMON, new NoiseParameters(-4, DoubleList.of(1.0, 1.0)));
        context.register(PODZOL_RARE, new NoiseParameters(-5, DoubleList.of(1.0, 1.0, 1.0)));
        context.register(BOWELS_SOUL_SAND, new NoiseParameters(-5, DoubleList.of(1.0, 1.0, 1.0)));
    }
}
