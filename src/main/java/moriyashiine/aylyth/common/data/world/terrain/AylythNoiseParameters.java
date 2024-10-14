package moriyashiine.aylyth.common.data.world.terrain;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;

public interface AylythNoiseParameters {

    RegistryKey<NoiseParameters> SURFACE = bind("surface");
    RegistryKey<NoiseParameters> PODZOL_COMMON = bind("podzol_common");
    RegistryKey<NoiseParameters> PODZOL_RARE = bind("podzol_rare");
    RegistryKey<NoiseParameters> BOWELS_SOUL_SAND = bind("bowels_soul_sand");

    private static RegistryKey<NoiseParameters> bind(String name) {
        return RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, Aylyth.id(name));
    }
}
