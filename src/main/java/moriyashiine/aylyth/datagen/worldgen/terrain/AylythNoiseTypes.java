package moriyashiine.aylyth.datagen.worldgen.terrain;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AylythNoiseTypes {

    public static void init() {}

    public static final RegistryKey<NoiseParameters> FLOODEDNESS = register("aquifer_fluid_level_floodedness");
    public static final RegistryKey<NoiseParameters> FLUID_SPREAD = register("aquifer_fluid_level_spread");
    public static final RegistryKey<NoiseParameters> OFFSET = register("offset");
    public static final RegistryKey<NoiseParameters> RIDGE = register("ridge");
    public static final RegistryKey<NoiseParameters> JAGGED = register("jagged");
    public static final RegistryKey<NoiseParameters> CONTINENTS = register("continents");
    public static final RegistryKey<NoiseParameters> TEMPERATURE = register("temperature");
    public static final RegistryKey<NoiseParameters> VEGETATION = register("vegetation");
    public static final RegistryKey<NoiseParameters> EROSION = register("erosion");
    public static final RegistryKey<NoiseParameters> BASE_LAYER = register("base_layer");
    public static final RegistryKey<NoiseParameters> SURFACE = register("surface");
    public static final RegistryKey<NoiseParameters> CAVE_LAYER = register("cave_layer");
    public static final RegistryKey<NoiseParameters> CAVE_CHEESE = register("cave_cheese");
    public static final RegistryKey<NoiseParameters> SPAGHETTI_2D = register("spaghetti_2d");
    public static final RegistryKey<NoiseParameters> SPAGHETTI_2D_MODULATOR = register("spaghetti_2d_modulator");
    public static final RegistryKey<NoiseParameters> SPAGHETTI_2D_ROUGHNESS = register("spaghetti_2d_roughness");
    public static final RegistryKey<NoiseParameters> SPAGHETTI_2D_THICKNESS = register("spaghetti_2d_thickness");
    public static final RegistryKey<NoiseParameters> SPAGHETTI_2D_ELEVATION = register("spaghetti_2d_elevation");
    public static final RegistryKey<NoiseParameters> SPAGHETTI_3D_1 = register("spaghetti_3d_1");
    public static final RegistryKey<NoiseParameters> SPAGHETTI_3D_2 = register("spaghetti_3d_2");
    public static final RegistryKey<NoiseParameters> SPAGHETTI_3D_RARITY = register("spaghetti_3d_rareness");
    public static final RegistryKey<NoiseParameters> SPAGHETTI_3D_THICKNESS = register("spaghetti_3d_thickness");
    public static final RegistryKey<NoiseParameters> NOODLE = register("noodle");
    public static final RegistryKey<NoiseParameters> NOODLE_THICKNESS = register("noodle_thickness");
    public static final RegistryKey<NoiseParameters> NOODLE_RIDGE_A = register("noodle_ridge_a");
    public static final RegistryKey<NoiseParameters> NOODLE_RIDGE_B = register("noodle_ridge_b");
    public static final RegistryKey<NoiseParameters> PILLAR = register("pillar");
    public static final RegistryKey<NoiseParameters> PILLAR_RARENESS = register("pillar_rareness");
    public static final RegistryKey<NoiseParameters> PILLAR_THICKNESS = register("pillar_thickness");
    public static final RegistryKey<NoiseParameters> CAVE_ENTRANCES = register("cave_entrances");
    public static final RegistryKey<NoiseParameters> PODZOL_COMMON = register("podzol_common");
    public static final RegistryKey<NoiseParameters> PODZOL_RARE = register("podzol_rare");
    public static final RegistryKey<NoiseParameters> BOWELS_SOUL_SAND = register("bowels_soul_sand");

    private static RegistryKey<NoiseParameters> register(String id) {
        return RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, AylythUtil.id(id));
    }

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
