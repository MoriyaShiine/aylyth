package moriyashiine.aylyth.datagen.common.world.terrain;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes.Spline.DensityFunctionWrapper;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseParametersKeys;

import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

import static moriyashiine.aylyth.common.data.world.terrain.AylythDensityFunctions.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctions.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctions.applySurfaceSlides;

public final class AylythDensityFunctionBootstrap {
    private AylythDensityFunctionBootstrap() {}

    public static void bootstrap(Registerable<DensityFunction> context) {
        var densityFunctions = context.getRegistryLookup(RegistryKeys.DENSITY_FUNCTION);
        var noiseParameters = context.getRegistryLookup(RegistryKeys.NOISE_PARAMETERS);

        // VANILLACOPY

        // The correct way would be to change the DensityFunctions.OFFSET_OVERWORLD

        var vanillaContinentsSplinePos = new DensityFunctionWrapper(densityFunctions.getOrThrow(DensityFunctions.CONTINENTS_OVERWORLD));
        var modifiedContinents = context.register(MODIFIED_CONTINENTS, flatCache(spline(net.minecraft.util.math.Spline.builder(vanillaContinentsSplinePos).add(-0.2f, 0, 0.8f).build())));

        var vanillaErosionSplinePos = new DensityFunctionWrapper(densityFunctions.getOrThrow(DensityFunctions.EROSION_OVERWORLD));
        var modifiedErosion = context.register(MODIFIED_EROSION, flatCache(spline(net.minecraft.util.math.Spline.builder(vanillaErosionSplinePos).add(-0.3f, 0, 0.7f).build())));

        var modifiedCavesEntrances = context.register(MODIFIED_CAVES_ENTRANCES, constant(1));

        // Use our continents and erosion functions

        var vanillaJaggedNoise = noise(noiseParameters.getOrThrow(NoiseParametersKeys.JAGGED), 1500, 0);
        registerSlopedCheeseFunction(context, densityFunctions, vanillaJaggedNoise, modifiedContinents, modifiedErosion, OVERRIDDEN_OFFSET, OVERRIDDEN_FACTOR, OVERRIDDEN_JAGGEDNESS, OVERRIDDEN_DEPTH, OVERRIDDEN_SLOPED_CHEESE, false);

        var overriddenInitialDensity = createInitialDensityFunction(cache2d(wrap(densityFunctions.getOrThrow(OVERRIDDEN_FACTOR))), wrap(densityFunctions.getOrThrow(OVERRIDDEN_DEPTH)));
        context.register(OVERRIDDEN_INITIAL_DENSITY_WITHOUT_JAGGEDNESS, applySurfaceSlides(false, add(overriddenInitialDensity, constant(DensityFunctions.field_38250)).clamp(-DensityFunctions.field_37691, DensityFunctions.field_37691)));

        var overriddenSlopedCheese = wrap(densityFunctions.getOrThrow(OVERRIDDEN_SLOPED_CHEESE));
        var overriddenCavesEntrance = min(overriddenSlopedCheese, mul(constant(5), wrap(modifiedCavesEntrances)));
        var overriddenFinalCaves = rangeChoice(overriddenSlopedCheese, -1000000, DensityFunctions.field_36617, overriddenCavesEntrance, createCavesFunction(densityFunctions, noiseParameters, overriddenSlopedCheese));
        context.register(OVERRIDDEN_FINALE_DENSITY, min(applyBlendDensity(applySurfaceSlides(false, overriddenFinalCaves)), wrap(densityFunctions.getOrThrow(DensityFunctions.CAVES_NOODLE_OVERWORLD))));
    }

    static DensityFunction wrap(RegistryEntry<DensityFunction> densityFunction) {
        return new DensityFunctionTypes.RegistryEntryHolder(densityFunction);
    }
}
