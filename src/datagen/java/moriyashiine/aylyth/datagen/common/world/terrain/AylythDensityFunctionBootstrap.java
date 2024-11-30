package moriyashiine.aylyth.datagen.common.world.terrain;

import moriyashiine.aylyth.common.data.world.terrain.AylythNoiseParams;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Spline;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes.RegistryEntryHolder;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes.Spline.DensityFunctionWrapper;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes.WeirdScaledSampler.RarityValueMapper;

import static java.lang.Math.floorDiv;
import static moriyashiine.aylyth.common.data.world.AylythDimensionData.*;
import static moriyashiine.aylyth.common.data.world.terrain.AylythDensityFunctions.*;
import static moriyashiine.aylyth.datagen.mixin.DensityFunctionsAccessor.*;
import static net.minecraft.util.math.noise.InterpolatedNoiseSampler.createBase3dNoiseFunction;
import static net.minecraft.world.biome.source.util.VanillaTerrainParametersCreator.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctions.*;

public final class AylythDensityFunctionBootstrap {
    private AylythDensityFunctionBootstrap() {}

    // VANILLACOPY DensityFunctions::bootstrap & DensityFunctions::createSurfaceNoiseRouter

    private static final double NOISE_XZ_SCALE = 0.25;

    public static void bootstrap(Registerable<DensityFunction> context) {
        var noiseParams = context.getRegistryLookup(RegistryKeys.NOISE_PARAMETERS);

        var floodedness = context.register(AQUIFER_FLUID_FLOODEDNESS, noise(noiseParams.getOrThrow(AylythNoiseParams.FLOODEDNESS), 0.67));
        var fluidSpread = context.register(AQUIFER_FLUID_SPREAD, noise(noiseParams.getOrThrow(AylythNoiseParams.FLUID_SPREAD), 1 / 1.4));

        var shiftX = context.register(SHIFT_X, flatCache(cache2d(shiftA(noiseParams.getOrThrow(AylythNoiseParams.OFFSET)))));
        var shiftZ = context.register(SHIFT_Z, flatCache(cache2d(shiftB(noiseParams.getOrThrow(AylythNoiseParams.OFFSET)))));

        var temperature = context.register(TEMPERATURE, flatCache(shiftedNoise(wrap(shiftX), wrap(shiftZ), NOISE_XZ_SCALE, noiseParams.getOrThrow(AylythNoiseParams.TEMPERATURE))));
        var vegetation = context.register(VEGETATION, flatCache(shiftedNoise(wrap(shiftX), wrap(shiftZ), NOISE_XZ_SCALE, noiseParams.getOrThrow(AylythNoiseParams.VEGETATION))));
        var continents = context.register(CONTINENTS, continents(wrap(shiftX), wrap(shiftZ), noiseParams.getOrThrow(AylythNoiseParams.CONTINENTS)));
        var erosion = context.register(EROSION, flatCache(shiftedNoise(wrap(shiftX), wrap(shiftZ), NOISE_XZ_SCALE, noiseParams.getOrThrow(AylythNoiseParams.EROSION))));
        var ridges = context.register(RIDGES, flatCache(shiftedNoise(wrap(shiftX), wrap(shiftZ), NOISE_XZ_SCALE, noiseParams.getOrThrow(AylythNoiseParams.RIDGES))));
        var ridgesFolded = context.register(RIDGES_FOLDED, createRidgesFoldedOverworldFunction(wrap(ridges)));

        var offset = context.register(OFFSET, offset(wrapSlidePos(continents), wrapSlidePos(erosion), wrapSlidePos(ridgesFolded)));
        var depth = context.register(DEPTH, add(yClampedGradient(MIN_HEIGHT, ACTUAL_MAX_HEIGHT, 1.5, -1.5), wrap(offset)));

        var factor = context.register(FACTOR, factor(wrapSlidePos(continents), wrapSlidePos(erosion), wrapSlidePos(ridges), wrapSlidePos(ridgesFolded)));
        var jaggedness = context.register(JAGGEDNESS, jaggedness(wrapSlidePos(continents), wrapSlidePos(erosion), wrapSlidePos(ridges), wrapSlidePos(ridgesFolded)));

        var base3dInlined = createBase3dNoiseFunction(NOISE_XZ_SCALE, 0.125, 80, 160, 8);
        var slopedCheese = context.register(SLOPED_CHEESE, slopedCheese(base3dInlined, wrap(factor), wrap(depth), wrap(jaggedness), noiseParams.getOrThrow(AylythNoiseParams.JAGGED)));

        var caveSpaghetti2dThiknessModulator = context.register(CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR, cacheOnce(noiseInRange(noiseParams.getOrThrow(AylythNoiseParams.SPAGHETTI_2D_THICKNESS), 2, 1, -0.6, -1.3)));

        // Limit spaghetti caves to 80 height
        var cavesSpaghetti2d = context.register(CAVES_SPAGHETTI_2D, cavesSpaghetti2d(noiseParams.getOrThrow(AylythNoiseParams.SPAGHETTI_2D_MODULATOR), noiseParams.getOrThrow(AylythNoiseParams.SPAGHETTI_2D), noiseParams.getOrThrow(AylythNoiseParams.SPAGHETTI_2D_ELEVATION), wrap(caveSpaghetti2dThiknessModulator), MIN_HEIGHT, 80)); // MAX_HEIGHT -> 80
        var cavesSpaghettiRoughness = context.register(CAVES_SPAGHETTI_ROUGHNESS, cacheOnce(mul(noiseInRange(noiseParams.getOrThrow(AylythNoiseParams.SPAGHETTI_ROUGHNESS_MODULATOR), 0, -0.1), add(noise(noiseParams.getOrThrow(AylythNoiseParams.SPAGHETTI_ROUGHNESS)).abs(), constant(-0.4)))));
        var cavesPillars = context.register(CAVES_PILLARS, cavesPillars(noiseParams.getOrThrow(AylythNoiseParams.PILLAR), noiseParams.getOrThrow(AylythNoiseParams.PILLAR_RARITY), noiseParams.getOrThrow(AylythNoiseParams.PILLAR_THICKNESS)));

        int fromY = DimensionType.MIN_HEIGHT * 2;
        int toY = DimensionType.MAX_COLUMN_HEIGHT * 2;
        var y = context.register(Y, yClampedGradient(fromY, toY, fromY, toY));

        // Limit noodle caves to 20 height
        var cavesNoodle = context.register(CAVES_NOODLE, cavesNoodle(wrap(y), noiseParams.getOrThrow(AylythNoiseParams.NOODLE), noiseParams.getOrThrow(AylythNoiseParams.NOODLE_THICKNESS), noiseParams.getOrThrow(AylythNoiseParams.NOODLE_RIDGE_A), noiseParams.getOrThrow(AylythNoiseParams.NOODLE_RIDGE_B), MIN_HEIGHT + 4, 20)); // MAX_HEIGHT -> 20
        var cavesEntrances = context.register(CAVES_ENTRANCES, constant(1)); // changed to constant

        var initialDensityWithoutJaggedness = context.register(DENSITY_INITIAL_WITHOUT_JAGGEDNESS, densityInitialWithoutJaggedness(wrap(factor), wrap(depth), MIN_HEIGHT, ACTUAL_MAX_HEIGHT));

        var cavesFinalInlined = caves(noiseParams.getOrThrow(AylythNoiseParams.CAVE_LAYER), noiseParams.getOrThrow(AylythNoiseParams.CAVE_CHEESE), wrap(slopedCheese), wrap(cavesSpaghetti2d), wrap(cavesSpaghettiRoughness), wrap(cavesEntrances), wrap(cavesPillars));
        var finalDensity = context.register(DENSITY_FINAL, densityFinal(wrap(slopedCheese), wrap(cavesEntrances), wrap(cavesNoodle), cavesFinalInlined, MIN_HEIGHT, ACTUAL_MAX_HEIGHT));
    }

    static DensityFunction wrap(RegistryEntry<DensityFunction> fun) {
        return new RegistryEntryHolder(fun);
    }

    private static DensityFunctionWrapper wrapSlidePos(RegistryEntry<DensityFunction> fun) {
        return new DensityFunctionWrapper(fun);
    }

    private static DensityFunction continents(DensityFunction shiftX, DensityFunction shiftZ, RegistryEntry<NoiseParameters> continentsNoise) {
        // Reduce the size and number of oceans

        var vanillaContinents = new DensityFunctionWrapper(RegistryEntry.of(flatCache(shiftedNoise(shiftX, shiftZ, NOISE_XZ_SCALE, continentsNoise))));

        // Perhaps a more correct way would be to change the OFFSET function, see VanillaTerrainParametersCreator::createOffsetSpline
        return flatCache(spline(Spline.builder(vanillaContinents).add(-0.5f, 0, 0.5f).build())); // added
    }

    private static DensityFunction offset(DensityFunctionWrapper continentsSplinePos, DensityFunctionWrapper erosionSplinePos, DensityFunctionWrapper ridgesFoldedSplinePos) {
        return applyBlending(
                add(constant(field_37690), spline(createOffsetSpline(continentsSplinePos, erosionSplinePos, ridgesFoldedSplinePos, false))),
                blendOffset()
        );
    }

    private static DensityFunction factor(DensityFunctionWrapper continentsSplinePos, DensityFunctionWrapper erosionSplinePos, DensityFunctionWrapper ridgesSplinePos, DensityFunctionWrapper ridgesFoldedSplinePos) {
        return applyBlending(
                spline(createFactorSpline(continentsSplinePos, erosionSplinePos, ridgesSplinePos, ridgesFoldedSplinePos, false)),
                constant(10)
        );
    }

    private static DensityFunction jaggedness(DensityFunctionWrapper continentsSplinePos, DensityFunctionWrapper erosionSplinePos, DensityFunctionWrapper ridgesSplinePos, DensityFunctionWrapper ridgesFoldedSplinePos) {
        return applyBlending(
                spline(createJaggednessSpline(continentsSplinePos, erosionSplinePos, ridgesSplinePos, ridgesFoldedSplinePos, false)),
                zero()
        );
    }

    private static DensityFunction slopedCheese(DensityFunction base3dNoise, DensityFunction factor, DensityFunction depth, DensityFunction jaggedness, RegistryEntry<NoiseParameters> jaggedNoise) {
        return add(
                createInitialDensityFunction(factor, add(depth, mul(jaggedness, noise(jaggedNoise, 1500, 0).halfNegative()))),
                base3dNoise
        );
    }

    private static DensityFunction cavesSpaghetti2d(RegistryEntry<NoiseParameters> spaghetti2dModulator, RegistryEntry<NoiseParameters> spaghetti2d, RegistryEntry<NoiseParameters> spaghetti2dElevation, DensityFunction spaghetti2dThicknessModulator, int minHeight, int maxHeight) {
        var scaledSpaghetti2dModulator = weirdScaledSampler(noise(spaghetti2dModulator, 2, 1), spaghetti2d, RarityValueMapper.TYPE2);
        var spaghetti2d1 = add(scaledSpaghetti2dModulator, mul(constant(0.083), spaghetti2dThicknessModulator));

        var clampedSpaghetti2dElevation = noiseInRange(spaghetti2dElevation, 0, floorDiv(-64, 8), 8);
        var heightedSpaghetti2dElevation = add(clampedSpaghetti2dElevation, yClampedGradient(minHeight, maxHeight, 8, -40)).abs();
        var spaghetti2d2 = add(heightedSpaghetti2dElevation, spaghetti2dThicknessModulator).cube();

        return max(spaghetti2d1, spaghetti2d2).clamp(-1, 1);
    }

    private static DensityFunction cavesPillars(RegistryEntry<NoiseParameters> pillarNoise, RegistryEntry<NoiseParameters> pillarRarityNoise, RegistryEntry<NoiseParameters> pillarsThicknessNoise) {
        var pillar = noise(pillarNoise, 25, 0.3);
        var pillarRarity = noiseInRange(pillarRarityNoise, 0, -2);
        var basePillar = add(mul(pillar, constant(2)), pillarRarity);

        var clampedPillarThickness = noiseInRange(pillarsThicknessNoise, 0, 1.1);

        return cacheOnce(mul(basePillar, clampedPillarThickness.cube()));
    }

    private static DensityFunction cavesNoodle(DensityFunction y, RegistryEntry<NoiseParameters> noodle, RegistryEntry<NoiseParameters> noodleThickness, RegistryEntry<NoiseParameters> noodleRidgeA, RegistryEntry<NoiseParameters> noodleRidgeB, int minHeight, int maxHeight) {
        var heightedNoodle = verticalRangeChoice(y, noise(noodle, 1, 1), minHeight, maxHeight, -1);

        var heightedNoodleThickness = verticalRangeChoice(y, noiseInRange(noodleThickness, 1, 1, -0.05, -0.1), minHeight, maxHeight, 0);

        var heightedNoodleRidgeA = verticalRangeChoice(y, noise(noodleRidgeA, 2.6666666666666665, 2.6666666666666665), minHeight, maxHeight, 0);
        var heightedNoodleRidgeB = verticalRangeChoice(y, noise(noodleRidgeB, 2.6666666666666665, 2.6666666666666665), minHeight, maxHeight, 0);
        var heightedNoodleRidgeFinal = mul(constant(1.5), max(heightedNoodleRidgeA.abs(), heightedNoodleRidgeB.abs()));

        return rangeChoice(heightedNoodle, -1000000, 0, constant(64), add(heightedNoodleThickness, heightedNoodleRidgeFinal));
    }

    private static DensityFunction caves(RegistryEntry<NoiseParameters> cavesLayerNoise, RegistryEntry<NoiseParameters> cavesCheeseNoise, DensityFunction slopedCheese, DensityFunction cavesSpaghetti2d, DensityFunction cavesSpaghettiRoughness, DensityFunction cavesEntrances, DensityFunction cavesPillars) {
        var cavesLayer = mul(constant(4), noise(cavesLayerNoise, 8).square());
        var cavesCheese = add(
                add(constant(0.27), noise(cavesCheeseNoise, 2, 0.95)).clamp(-1, 1), // 1 -> 2; 0.6666666666666666 -> 0.95
                add(constant(1.5), mul(constant(-0.64), slopedCheese)).clamp(0, 0.5)
        );
        var finaleCavesEntrances = min(min(add(cavesLayer, cavesCheese), cavesEntrances), add(cavesSpaghetti2d, cavesSpaghettiRoughness));
        var clampedCavesPillars = rangeChoice(cavesPillars, -1000000, 0.03, constant(-1000000), cavesPillars);

        return max(finaleCavesEntrances, clampedCavesPillars);
    }

    private static DensityFunction applySurfaceSlides(DensityFunction fun, int minHeight, int maxHeight) {
        return applySlides(fun, minHeight, maxHeight, 80, 64, -0.078125, 0, 24, 0.1171875);
    }

    private static DensityFunction densityInitialWithoutJaggedness(DensityFunction factor, DensityFunction depth, int minHeight, int maxHeight) {
        return applySurfaceSlides(
                add(createInitialDensityFunction(cache2d(factor), depth), constant(getCheeseNoiseTarget())).clamp(-field_37691, field_37691), minHeight, maxHeight
        );
    }

    private static DensityFunction densityFinal(DensityFunction slopedCheese, DensityFunction cavesEntrances, DensityFunction cavesNoodle, DensityFunction caves, int minHeight, int maxHeight) {
        return min(
                applyBlendDensity(applySurfaceSlides(rangeChoice(slopedCheese, -1000000, getSurfaceDensityThreshold(), min(slopedCheese, mul(constant(5), cavesEntrances)), caves), minHeight, maxHeight)),
                cavesNoodle
        );
    }
}
