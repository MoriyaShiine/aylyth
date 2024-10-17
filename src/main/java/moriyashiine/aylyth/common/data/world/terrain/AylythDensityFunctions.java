package moriyashiine.aylyth.common.data.world.terrain;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.densityfunction.DensityFunction;

public interface AylythDensityFunctions {

    RegistryKey<DensityFunction> FLOODEDNESS_FUNCTION_KEY = bind("floodedness");
    RegistryKey<DensityFunction> FLUID_SPREAD_FUNCTION_KEY = bind("fluid_spread");
    RegistryKey<DensityFunction> SHIFT_X_KEY = bind("shift_x");
    RegistryKey<DensityFunction> SHIFT_Z_KEY = bind("shift_z");
    RegistryKey<DensityFunction> TEMPERATURE_FUNCTION_KEY = bind("temperature");
    RegistryKey<DensityFunction> VEGETATION_FUNCTION_KEY = bind("vegetation");
    RegistryKey<DensityFunction> CONTINENTS_FUNCTION_KEY = bind("continents");
    RegistryKey<DensityFunction> EROSION_FUNCTION_KEY = bind("erosion");
    RegistryKey<DensityFunction> RIDGES_FUNCTION_KEY = bind("ridges");
    RegistryKey<DensityFunction> RIDGES_FOLDED_FUNCTION_KEY = bind("ridges_folded");
    RegistryKey<DensityFunction> OFFSET_FUNCTION_KEY = bind("offset");
    RegistryKey<DensityFunction> DEPTH_FUNCTION_KEY = bind("depth");
    RegistryKey<DensityFunction> FACTOR_FUNCTION_KEY = bind("factor");
    RegistryKey<DensityFunction> JAGGEDNESS_FUNCTION_KEY = bind("jaggedness");
    RegistryKey<DensityFunction> SLOPED_CHEESE_FUNCTION_KEY = bind("sloped_cheese");
    RegistryKey<DensityFunction> Y_FUNCTION_KEY = bind("y");
    RegistryKey<DensityFunction> CAVES_SPAGHETTI_ROUGHNESS_FUNCTION_KEY = bind("caves/spaghetti_roughness");
    RegistryKey<DensityFunction> CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR_FUNCTION_KEY = bind("caves/spaghetti_2d_thickness_modulator");
    RegistryKey<DensityFunction> CAVES_SPAGHETTI_2D_FUNCTION_KEY = bind("caves/spaghetti_2d");
    RegistryKey<DensityFunction> CAVES_NOODLE_FUNCTION_KEY = bind("caves/noodle");
    RegistryKey<DensityFunction> CAVES_PILLARS_FUNCTION_KEY = bind("caves/pillars");
    RegistryKey<DensityFunction> CAVES_ENTRANCES_FUNCTION_KEY = bind("caves/entrances");
    RegistryKey<DensityFunction> INITIAL_DENSITY_WITHOUT_JAGGEDNESS_FUNCTION_KEY = bind("initial_density_without_jaggedness");
    RegistryKey<DensityFunction> FINAL_DENSITY_FUNCTION_KEY = bind("final_density");

    private static RegistryKey<DensityFunction> bind(String name) {
        return RegistryKey.of(RegistryKeys.DENSITY_FUNCTION, Aylyth.id(name));
    }
}
