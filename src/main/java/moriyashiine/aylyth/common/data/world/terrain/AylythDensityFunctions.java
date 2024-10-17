package moriyashiine.aylyth.common.data.world.terrain;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.densityfunction.DensityFunction;

public interface AylythDensityFunctions {

    RegistryKey<DensityFunction> AQUIFER_FLUID_FLOODEDNESS = bind("aquifer_fluid_floodedness");
    RegistryKey<DensityFunction> AQUIFER_FLUID_SPREAD = bind("aquifer_fluid_spread");

    RegistryKey<DensityFunction> SHIFT_X = bind("shift_x");
    RegistryKey<DensityFunction> SHIFT_Z = bind("shift_z");

    RegistryKey<DensityFunction> TEMPERATURE = bind("temperature");
    RegistryKey<DensityFunction> VEGETATION = bind("vegetation");
    RegistryKey<DensityFunction> CONTINENTS = bind("continents");
    RegistryKey<DensityFunction> EROSION = bind("erosion");
    RegistryKey<DensityFunction> RIDGES = bind("ridges");
    RegistryKey<DensityFunction> RIDGES_FOLDED = bind("ridges_folded");

    RegistryKey<DensityFunction> OFFSET = bind("offset");
    RegistryKey<DensityFunction> DEPTH = bind("depth");

    RegistryKey<DensityFunction> FACTOR = bind("factor");
    RegistryKey<DensityFunction> JAGGEDNESS = bind("jaggedness");
    RegistryKey<DensityFunction> SLOPED_CHEESE = bind("sloped_cheese");

    RegistryKey<DensityFunction> CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR = bind("caves/spaghetti_2d_thickness_modulator");
    RegistryKey<DensityFunction> CAVES_SPAGHETTI_2D = bind("caves/spaghetti_2d");
    RegistryKey<DensityFunction> CAVES_SPAGHETTI_ROUGHNESS = bind("caves/spaghetti_roughness");
    RegistryKey<DensityFunction> CAVES_PILLARS = bind("caves/pillars");

    RegistryKey<DensityFunction> Y = bind("y");

    RegistryKey<DensityFunction> CAVES_NOODLE = bind("caves/noodle");
    RegistryKey<DensityFunction> CAVES_ENTRANCES = bind("caves/entrances");

    RegistryKey<DensityFunction> DENSITY_INITIAL_WITHOUT_JAGGEDNESS = bind("density_initial_without_jaggedness");
    RegistryKey<DensityFunction> DENSITY_FINAL = bind("density_final");

    private static RegistryKey<DensityFunction> bind(String name) {
        return RegistryKey.of(RegistryKeys.DENSITY_FUNCTION, Aylyth.id(name));
    }
}
