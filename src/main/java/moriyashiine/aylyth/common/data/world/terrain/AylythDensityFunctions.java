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

    private static RegistryKey<DensityFunction> bind(String name) {
        return RegistryKey.of(RegistryKeys.DENSITY_FUNCTION, Aylyth.id(name));
    }
}
