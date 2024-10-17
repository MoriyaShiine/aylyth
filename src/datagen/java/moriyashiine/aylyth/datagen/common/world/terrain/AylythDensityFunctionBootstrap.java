package moriyashiine.aylyth.datagen.common.world.terrain;

import moriyashiine.aylyth.common.data.world.terrain.AylythNoiseParams;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.noise.InterpolatedNoiseSampler;
import net.minecraft.world.biome.source.util.VanillaTerrainParametersCreator;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes.RegistryEntryHolder;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes.Spline;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes.WeirdScaledSampler;

import static moriyashiine.aylyth.common.data.world.AylythDimensionData.*;
import static moriyashiine.aylyth.common.data.world.terrain.AylythDensityFunctions.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public final class AylythDensityFunctionBootstrap {
    private AylythDensityFunctionBootstrap() {}

    public static void bootstrap(Registerable<DensityFunction> context) {
        var densityFuns = context.getRegistryLookup(RegistryKeys.DENSITY_FUNCTION);
        var noiseParams = context.getRegistryLookup(RegistryKeys.NOISE_PARAMETERS);

        var floodedness = context.register(AQUIFER_FLUID_FLOODEDNESS, noise(noiseParams.getOrThrow(AylythNoiseParams.FLOODEDNESS), 1.0D, 0.67D));
        var fluidSpread = context.register(AQUIFER_FLUID_SPREAD, noise(noiseParams.getOrThrow(AylythNoiseParams.FLUID_SPREAD), 1.0D, 1D / 1.4D));

        var shiftX = context.register(SHIFT_X, flatCache(cache2d(shiftA(noiseParams.getOrThrow(AylythNoiseParams.OFFSET)))));
        var shiftZ = context.register(SHIFT_Z, flatCache(cache2d(shiftB(noiseParams.getOrThrow(AylythNoiseParams.OFFSET)))));

        var temperature = context.register(TEMPERATURE, flatCache(shiftedNoise(wrap(shiftX), wrap(shiftZ), 0.25, noiseParams.getOrThrow(AylythNoiseParams.TEMPERATURE))));
        var vegetation = context.register(VEGETATION, flatCache(shiftedNoise(wrap(shiftX), wrap(shiftZ), 0.25, noiseParams.getOrThrow(AylythNoiseParams.VEGETATION))));
        var continents = context.register(CONTINENTS, flatCache(shiftedNoise(wrap(shiftX), wrap(shiftZ), 0.25, noiseParams.getOrThrow(AylythNoiseParams.CONTINENTS))));
        var erosion = context.register(EROSION, flatCache(shiftedNoise(wrap(shiftX), wrap(shiftZ), 0.25, noiseParams.getOrThrow(AylythNoiseParams.EROSION))));
        var ridges = context.register(RIDGES, flatCache(shiftedNoise(wrap(shiftX), wrap(shiftZ), 0.25, noiseParams.getOrThrow(AylythNoiseParams.RIDGES))));
        var ridgesFolded = context.register(RIDGES_FOLDED, mul(constant(-3.0), add(constant(-0.3333333333333333), add(constant(-0.6666666666666666), wrap(ridges).abs()).abs())));

        var offset = context.register(OFFSET, offset(wrapSlidePos(continents), wrapSlidePos(erosion), wrapSlidePos(ridgesFolded)));
        var depth = context.register(DEPTH, add(yClampedGradient(MIN_HEIGHT, ACTUAL_MAX_HEIGHT, 1.5, -1.5), wrap(offset)));

        var factor = context.register(FACTOR, factor(wrapSlidePos(continents), wrapSlidePos(erosion), wrapSlidePos(ridges), wrapSlidePos(ridgesFolded)));
        var jaggedness = context.register(JAGGEDNESS, jaggedness(wrapSlidePos(continents), wrapSlidePos(erosion), wrapSlidePos(ridges), wrapSlidePos(ridgesFolded)));
        var slopedCheese = context.register(SLOPED_CHEESE, slopedCheese(depth, jaggedness, factor, noiseParams));

        var caveSpaghetti2dThiknessModulator = context.register(CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR, cavesSpaghetti2dThickness(noiseParams));
        var cavesSpaghetti2d = context.register(CAVES_SPAGHETTI_2D, cavesSpaghetti2d(caveSpaghetti2dThiknessModulator, noiseParams));
        var cavesSpaghettiRoughness = context.register(CAVES_SPAGHETTI_ROUGHNESS, cavesSpaghettiRoughness(noiseParams));
        var cavesPillars = context.register(CAVES_PILLARS, cavesPillars(noiseParams));

        int fromY = DimensionType.MIN_HEIGHT * 2;
        int toY = DimensionType.MAX_COLUMN_HEIGHT * 2;
        var y = context.register(Y, yClampedGradient(fromY, toY, fromY, toY));

        var cavesNoodle =  context.register(CAVES_NOODLE, cavesNoodle(y, noiseParams));
        var cavesEntrances = context.register(CAVES_ENTRANCES, constant(1));

        var initialDensityWithoutJaggedness = context.register(DENSITY_INITIAL_WITHOUT_JAGGEDNESS, initialDensity(densityFuns));
        var finalDensity = context.register(DENSITY_FINAL, finalDensity(densityFuns, noiseParams));
    }

    static DensityFunction wrap(RegistryEntry<DensityFunction> fun) {
        return new RegistryEntryHolder(fun);
    }

    private static Spline.DensityFunctionWrapper wrapSlidePos(RegistryEntry<DensityFunction> fun) {
        return new Spline.DensityFunctionWrapper(fun);
    }

    private static DensityFunction offset(Spline.DensityFunctionWrapper continentsCoordinate, Spline.DensityFunctionWrapper erosionCoordinate, Spline.DensityFunctionWrapper ridgesFoldedCoordinate) {
        return withBlending(
                add(
                        constant(-0.50375f),
                        spline(
                                VanillaTerrainParametersCreator.createOffsetSpline(continentsCoordinate, erosionCoordinate, ridgesFoldedCoordinate, false)
                        )
                ),
                blendOffset());
    }

    private static DensityFunction factor(Spline.DensityFunctionWrapper continentsCoordinate, Spline.DensityFunctionWrapper erosionCoordinate, Spline.DensityFunctionWrapper ridgesCoordinate, Spline.DensityFunctionWrapper ridgesFoldedCoordinate) {
        return withBlending(
                spline(VanillaTerrainParametersCreator.createFactorSpline(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate, false)),
                constant(10)
        );
    }

    private static DensityFunction jaggedness(Spline.DensityFunctionWrapper continentsCoordinate, Spline.DensityFunctionWrapper erosionCoordinate, Spline.DensityFunctionWrapper ridgesCoordinate, Spline.DensityFunctionWrapper ridgesFoldedCoordinate) {
        return withBlending(
                spline(VanillaTerrainParametersCreator.createJaggednessSpline(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate, false)),
                constant(0)
        );
    }

    private static DensityFunction slopedCheese(RegistryEntry<DensityFunction> depth, RegistryEntry<DensityFunction> jaggednes, RegistryEntry<DensityFunction> factor, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return add(
                mul(
                        constant(4.0),
                        mul(
                                add(
                                        wrap(depth),
                                        mul(
                                                wrap(jaggednes),
                                                noise(noiseParameters.getOrThrow(AylythNoiseParams.JAGGED), 1500.0, 0.0).halfNegative()
                                        )
                                ),
                                wrap(factor)
                        ).quarterNegative()
                ),
                InterpolatedNoiseSampler.createBase3dNoiseFunction(0.25, 0.125, 80.0, 160.0, 8.0)
        );
    }

    private static DensityFunction cavesSpaghetti2dThickness(RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return cacheOnce(
                add(
                        constant(-0.95),
                        mul(
                                constant(-0.35),
                                noise(noiseParameters.getOrThrow(AylythNoiseParams.SPAGHETTI_2D_THICKNESS), 2.0, 1.0)
                        )
                )
        );
    }

    private static DensityFunction cavesSpaghetti2d(RegistryEntry<DensityFunction> caveSpaghetti2dThicknessModulator, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return max(
                add(
                        weirdScaledSampler(
                                noise(noiseParameters.getOrThrow(AylythNoiseParams.SPAGHETTI_2D_MODULATOR), 2.0, 1.0),
                                noiseParameters.getOrThrow(AylythNoiseParams.SPAGHETTI_2D),
                                WeirdScaledSampler.RarityValueMapper.TYPE2
                        ),
                        mul(
                                constant(0.083),
                                wrap(caveSpaghetti2dThicknessModulator)
                        )
                ),
                add(
                        add(
                                add(
                                        constant(0.0),
                                        mul(
                                                constant(8.0),
                                                noise(noiseParameters.getOrThrow(AylythNoiseParams.SPAGHETTI_2D_ELEVATION), 1.0, 0.0)
                                        )
                                ),
                                yClampedGradient(
                                        -64,
                                        80,
                                        8.0,
                                        -40.0
                                )
                        ).abs(),
                        wrap(caveSpaghetti2dThicknessModulator)
                )
        ).clamp(-1.0, 1.0);
    }

    private static DensityFunction cavesSpaghettiRoughness(RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return cacheOnce(
                mul(
                        add(
                                constant(-0.05),
                                mul(
                                        constant(-0.05),
                                        noise(noiseParameters.getOrThrow(AylythNoiseParams.SPAGHETTI_2D_MODULATOR))
                                )
                        ),
                        add(
                                constant(-0.4),
                                noise(noiseParameters.getOrThrow(AylythNoiseParams.SPAGHETTI_2D_ROUGHNESS)).abs()
                        )
                )
        );
    }

    private static DensityFunction cavesPillars(RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return cacheOnce(
                mul(
                        add(
                                mul(
                                        constant(2.0),
                                        noise(noiseParameters.getOrThrow(AylythNoiseParams.PILLAR), 25.0, 0.3)
                                ),
                                add(
                                        constant(-1.0),
                                        mul(
                                                constant(-1.0),
                                                noise(noiseParameters.getOrThrow(AylythNoiseParams.PILLAR_RARENESS))
                                        )
                                )
                        ),
                        add(
                                constant(0.55),
                                mul(
                                        constant(0.55),
                                        noise(noiseParameters.getOrThrow(AylythNoiseParams.PILLAR_THICKNESS))
                                )
                        ).cube()
                )
        );
    }

    private static DensityFunction cavesNoodle(RegistryEntry<DensityFunction> y, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) { // NOTE: This is the cave type that sucks and I hate
        return rangeChoice(
                interpolated(
                        rangeChoice(
                                wrap(y),
                                -60.0,
                                21.0,
                                noise(noiseParameters.getOrThrow(AylythNoiseParams.NOODLE)),
                                constant(-1.0)
                        )
                ),
                -1000000.0,
                0.0,
                constant(64.0),
                add(
                        interpolated(
                                rangeChoice(
                                        wrap(y),
                                        -60.0,
                                        21.0,
                                        add(
                                                constant(-0.075),
                                                mul(
                                                        constant(-0.025),
                                                        noise(noiseParameters.getOrThrow(AylythNoiseParams.NOODLE_THICKNESS))
                                                )
                                        ),
                                        constant(0.0)
                                )
                        ),
                        mul(
                                constant(1.5),
                                max(
                                        interpolated(
                                                rangeChoice(
                                                        wrap(y),
                                                        -60.0,
                                                        21.0,
                                                        noise(noiseParameters.getOrThrow(AylythNoiseParams.NOODLE_RIDGE_A), 2.6666666666666665, 2.6666666666666665),
                                                        constant(0.0)
                                                )
                                        ).abs(),
                                        interpolated(
                                                rangeChoice(
                                                        wrap(y),
                                                        -60.0,
                                                        21.0,
                                                        noise(noiseParameters.getOrThrow(AylythNoiseParams.NOODLE_RIDGE_B), 2.6666666666666665, 2.6666666666666665),
                                                        constant(0.0)
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static DensityFunction withBlending(DensityFunction end, DensityFunction start) {
        return flatCache(cache2d(lerp(blendAlpha(), start, end)));
    }

    private static DensityFunction slide(DensityFunction densityFunction, int i, int j, int k, int l, double d, int m, int n, double e) {
        DensityFunction densityFunction2 = densityFunction;
        DensityFunction densityFunction3 = yClampedGradient(i + j - k, i + j - l, 1.0, 0.0);
        densityFunction2 = lerp(densityFunction3, constant(d), densityFunction2);
        DensityFunction densityFunction4 = yClampedGradient(i + m, i + n, 0.0, 1.0);
        densityFunction2 = lerp(densityFunction4, constant(e), densityFunction2);
        return densityFunction2;
    }

    private static DensityFunction postProcess(DensityFunction densityFunction) {
        DensityFunction densityFunction2 = blendDensity(densityFunction);
        return mul(interpolated(densityFunction2), constant(0.64)).squeeze();
    }

    private static DensityFunction initialDensity(RegistryEntryLookup<DensityFunction> functions) {
        return slide(
                add(
                        constant(-0.703125),
                        mul(
                                constant(4),
                                mul(
                                        wrap(functions.getOrThrow(DEPTH)),
                                        cache2d(wrap(functions.getOrThrow(FACTOR)))
                                ).quarterNegative()
                        )
                ).clamp(-64, 64),
                MIN_HEIGHT,
                ACTUAL_MAX_HEIGHT,
                80, 64, -0.078125,
                0, 24, 0.1171875
        );
//        return slide(
//                add(
//                        constant(-0.703125),
//                        mul(
//                                constant(4.0),
//                                mul(
//                                        ,
//                                ).quarterNegative()
//                        )
//                ).clamp(-64.0, 64.0),
//                MIN_HEIGHT,
//                ACTUAL_MAX_HEIGHT,
//                80, 64, -0.078125,
//                0, 24, 0.1171875
//        );
    }

    private static DensityFunction finalDensity(RegistryEntryLookup<DensityFunction> functions, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return min(
                AylythDensityFunctionBootstrap.postProcess(
                        AylythDensityFunctionBootstrap.slide(
                                rangeChoice(
                                        AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(SLOPED_CHEESE)),
                                        -1000000.0,
                                        1.5625,
                                        min(
                                                AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(SLOPED_CHEESE)),
                                                mul(
                                                        constant(5.0),
                                                        AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(CAVES_ENTRANCES))
                                                )
                                        ),
                                        max(
                                                min(
                                                        min(
                                                                add(
                                                                        mul(
                                                                                constant(4.0),
                                                                                noise(noiseParameters.getOrThrow(AylythNoiseParams.CAVE_LAYER), 1.0, 8.0).square()
                                                                        ),
                                                                        add(
                                                                                add(
                                                                                        constant(0.27),
                                                                                        noise(noiseParameters.getOrThrow(AylythNoiseParams.CAVE_CHEESE), 2.0, 0.95)
                                                                                ).clamp(-1.0, 1.0),
                                                                                add(
                                                                                        constant(1.5),
                                                                                        mul(
                                                                                                constant(-0.64),
                                                                                                AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(SLOPED_CHEESE))
                                                                                        )
                                                                                ).clamp(0.0, 0.5)
                                                                        )
                                                                ),
                                                                AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(CAVES_ENTRANCES))
                                                        ),
                                                        add(
                                                                AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(CAVES_SPAGHETTI_2D)),
                                                                AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(CAVES_SPAGHETTI_ROUGHNESS))
                                                        )
                                                ),
                                                rangeChoice(
                                                        AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(CAVES_PILLARS)),
                                                        -1000000.0,
                                                        0.03,
                                                        constant(-1000000.0),
                                                        AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(CAVES_PILLARS))
                                                )
                                        )
                                ),
                                MIN_HEIGHT,
                                ACTUAL_MAX_HEIGHT,
                                80, 64, -0.078125,
                                0, 24, 0.1171875
                        )
                ),
                AylythDensityFunctionBootstrap.wrap(functions.getOrThrow(CAVES_NOODLE))
        );
    }
}
