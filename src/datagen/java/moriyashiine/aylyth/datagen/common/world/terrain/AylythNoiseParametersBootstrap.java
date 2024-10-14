package moriyashiine.aylyth.datagen.common.world.terrain;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.registry.Registerable;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;

import static moriyashiine.aylyth.common.data.world.terrain.AylythNoiseParameters.*;

public final class AylythNoiseParametersBootstrap {
    private AylythNoiseParametersBootstrap() {}

    public static void bootstrap(Registerable<NoiseParameters> context) {
        context.register(SURFACE, new NoiseParameters(-6, DoubleList.of(1, 1, 1)));
        context.register(PODZOL_COMMON, new NoiseParameters(-4, DoubleList.of(1, 1)));
        context.register(PODZOL_RARE, new NoiseParameters(-5, DoubleList.of(1, 1, 1)));
        context.register(BOWELS_SOUL_SAND, new NoiseParameters(-5, DoubleList.of(1, 1, 1)));
    }
}
