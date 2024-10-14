package moriyashiine.aylyth.common.data.world.terrain;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.densityfunction.DensityFunction;

public interface AylythDensityFunctions {

    RegistryKey<DensityFunction> MODIFIED_CONTINENTS = bind("modified_continents");
    RegistryKey<DensityFunction> MODIFIED_EROSION = bind("modified_erosion");
    RegistryKey<DensityFunction> MODIFIED_CAVES_ENTRANCES = bind("modified_caves_entrances");

    RegistryKey<DensityFunction> OVERRIDDEN_OFFSET = bind("overridden_offset");
    RegistryKey<DensityFunction> OVERRIDDEN_FACTOR = bind("overridden_factor");
    RegistryKey<DensityFunction> OVERRIDDEN_JAGGEDNESS = bind("overridden_jaggedness");
    RegistryKey<DensityFunction> OVERRIDDEN_DEPTH = bind("overridden_depth");
    RegistryKey<DensityFunction> OVERRIDDEN_SLOPED_CHEESE = bind("overridden_sloped_cheese");
    RegistryKey<DensityFunction> OVERRIDDEN_INITIAL_DENSITY_WITHOUT_JAGGEDNESS = bind("overridden_initial_density_jaggedness");
    RegistryKey<DensityFunction> OVERRIDDEN_FINALE_DENSITY = bind("overridden_final_density");

    RegistryKey<DensityFunction> SHIFT_X_KEY = bind("shift_x");
    RegistryKey<DensityFunction> SHIFT_Z_KEY = bind("shift_z");
    RegistryKey<DensityFunction> RIDGES_FUNCTION_KEY = bind("ridges");
    RegistryKey<DensityFunction> RIDGES_FOLDED_FUNCTION_KEY = bind("ridges_folded");
    RegistryKey<DensityFunction> CONTINENTS_FUNCTION_KEY = bind("continents");
    RegistryKey<DensityFunction> EROSION_FUNCTION_KEY = bind("erosion");
    RegistryKey<DensityFunction> OFFSET_FUNCTION_KEY = bind("offset");
    RegistryKey<DensityFunction> FACTOR_FUNCTION_KEY = bind("factor");
    RegistryKey<DensityFunction> JAGGEDNESS_FUNCTION_KEY = bind("jaggedness");
    RegistryKey<DensityFunction> DEPTH_FUNCTION_KEY = bind("depth");
    RegistryKey<DensityFunction> SLOPED_CHEESE_FUNCTION_KEY = bind("sloped_cheese");
    RegistryKey<DensityFunction> Y_FUNCTION_KEY = bind("y");
    RegistryKey<DensityFunction> CAVES_SPAGHETTI_ROUGHNESS_FUNCTION_KEY = bind("caves/spaghetti_roughness");
    RegistryKey<DensityFunction> CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR_FUNCTION_KEY = bind("caves/spaghetti_2d_thickness_modulator");
    RegistryKey<DensityFunction> CAVES_SPAGHETTI_2D_FUNCTION_KEY = bind("caves/spaghetti_2d");
    RegistryKey<DensityFunction> CAVES_NOODLE_FUNCTION_KEY = bind("caves/noodle");
    RegistryKey<DensityFunction> CAVES_PILLARS_FUNCTION_KEY = bind("caves/pillars");
    RegistryKey<DensityFunction> CAVES_ENTRANCES_FUNCTION_KEY = bind("caves/entrances");

    private static RegistryKey<DensityFunction> bind(String name) {
        return RegistryKey.of(RegistryKeys.DENSITY_FUNCTION, Aylyth.id(name));
    }
}
