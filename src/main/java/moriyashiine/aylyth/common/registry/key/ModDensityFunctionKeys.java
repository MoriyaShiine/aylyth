package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.densityfunction.DensityFunction;

public class ModDensityFunctionKeys {
    public static final RegistryKey<DensityFunction> SHIFT_X_KEY = functionKey("shift_x");
    public static final RegistryKey<DensityFunction> SHIFT_Z_KEY = functionKey("shift_z");
    public static final RegistryKey<DensityFunction> RIDGES_FUNCTION_KEY = functionKey("ridges");
    public static final RegistryKey<DensityFunction> RIDGES_FOLDED_FUNCTION_KEY = functionKey("ridges_folded");
    public static final RegistryKey<DensityFunction> CONTINENTS_FUNCTION_KEY = functionKey("continents");
    public static final RegistryKey<DensityFunction> EROSION_FUNCTION_KEY = functionKey("erosion");
    public static final RegistryKey<DensityFunction> OFFSET_FUNCTION_KEY = functionKey("offset");
    public static final RegistryKey<DensityFunction> FACTOR_FUNCTION_KEY = functionKey("factor");
    public static final RegistryKey<DensityFunction> JAGGEDNESS_FUNCTION_KEY = functionKey("jaggedness");
    public static final RegistryKey<DensityFunction> DEPTH_FUNCTION_KEY = functionKey("depth");
    public static final RegistryKey<DensityFunction> SLOPED_CHEESE_FUNCTION_KEY = functionKey("sloped_cheese");
    public static final RegistryKey<DensityFunction> Y_FUNCTION_KEY = functionKey("y");
    public static final RegistryKey<DensityFunction> CAVES_SPAGHETTI_ROUGHNESS_FUNCTION_KEY = functionKey("caves/spaghetti_roughness");
    public static final RegistryKey<DensityFunction> CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR_FUNCTION_KEY = functionKey("caves/spaghetti_2d_thickness_modulator");
    public static final RegistryKey<DensityFunction> CAVES_SPAGHETTI_2D_FUNCTION_KEY = functionKey("caves/spaghetti_2d");
    public static final RegistryKey<DensityFunction> CAVES_NOODLE_FUNCTION_KEY = functionKey("caves/noodle");
    public static final RegistryKey<DensityFunction> CAVES_PILLARS_FUNCTION_KEY = functionKey("caves/pillars");
    public static final RegistryKey<DensityFunction> CAVES_ENTRANCES_FUNCTION_KEY = functionKey("caves/entrances");

    private static RegistryKey<DensityFunction> functionKey(String id) {
        return RegistryKey.of(RegistryKeys.DENSITY_FUNCTION, AylythUtil.id(id));
    }
}
