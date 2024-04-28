package moriyashiine.aylyth.datagen.dynamic.terrain;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.noise.InterpolatedNoiseSampler;
import net.minecraft.world.biome.source.util.VanillaTerrainParametersCreator;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.densityfunction.DensityFunction;

import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

import static moriyashiine.aylyth.common.registry.key.ModDensityFunctionKeys.*;
import static moriyashiine.aylyth.common.registry.key.ModNoiseKeys.*;

public class AylythDensityFunctionBootstrap {

    public static void bootstrap(Registerable<DensityFunction> context) {
        var noiseParameters = context.getRegistryLookup(RegistryKeys.NOISE_PARAMETERS);

        var shiftX = context.register(SHIFT_X_KEY, flatCache(cache2d(shiftA(noiseParameters.getOrThrow(OFFSET)))));
        var shiftZ = context.register(SHIFT_Z_KEY, flatCache(cache2d(shiftB(noiseParameters.getOrThrow(OFFSET)))));
        var ridges = context.register(RIDGES_FUNCTION_KEY, flatCache(shiftedNoise(holderFunction(shiftX), holderFunction(shiftZ), 0.25, noiseParameters.getOrThrow(RIDGE))));
        var ridgesFolded = context.register(RIDGES_FOLDED_FUNCTION_KEY, mul(constant(-3.0), add(constant(-0.3333333333333333), add(constant(-0.6666666666666666), holderFunction(ridges).abs()).abs())));
        var continents = context.register(CONTINENTS_FUNCTION_KEY, flatCache(shiftedNoise(holderFunction(shiftX), holderFunction(shiftZ), 0.25, noiseParameters.getOrThrow(CONTINENTS))));
        var erosion = context.register(EROSION_FUNCTION_KEY, flatCache(shiftedNoise(holderFunction(shiftX), holderFunction(shiftZ), 0.25, noiseParameters.getOrThrow(EROSION))));

        Spline.DensityFunctionWrapper continentsCoordinate = new Spline.DensityFunctionWrapper(continents);
        Spline.DensityFunctionWrapper erosionCoordinate = new Spline.DensityFunctionWrapper(erosion);
        Spline.DensityFunctionWrapper ridgesCoordinate = new Spline.DensityFunctionWrapper(ridges);
        Spline.DensityFunctionWrapper ridgesFoldedCoordinate = new Spline.DensityFunctionWrapper(ridgesFolded);

        var offset = context.register(OFFSET_FUNCTION_KEY, offset(continentsCoordinate, erosionCoordinate, ridgesFoldedCoordinate));
        var factor = context.register(FACTOR_FUNCTION_KEY, factor(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate));
        var jaggedness = context.register(JAGGEDNESS_FUNCTION_KEY, jaggedness(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate));
        var depth = context.register(DEPTH_FUNCTION_KEY, depth(offset));
        var y = context.register(Y_FUNCTION_KEY, aylythY());
        context.register(CAVES_SPAGHETTI_ROUGHNESS_FUNCTION_KEY, cavesSpaghettiRoughness(noiseParameters));
        var caveSpaghetti2dThiknessModulator = context.register(CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR_FUNCTION_KEY, cavesSpaghetti2dThickness(noiseParameters));
        context.register(CAVES_SPAGHETTI_2D_FUNCTION_KEY, cavesSpaghetti2d(caveSpaghetti2dThiknessModulator, noiseParameters));
        context.register(CAVES_NOODLE_FUNCTION_KEY, cavesNoodle(y, noiseParameters));
        context.register(CAVES_PILLARS_FUNCTION_KEY, cavesPillars(noiseParameters));
        context.register(CAVES_ENTRANCES_FUNCTION_KEY, cavesEntrances());
        context.register(SLOPED_CHEESE_FUNCTION_KEY, slopedCheese(depth, jaggedness, factor, noiseParameters));
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

    static DensityFunction depth(RegistryEntry<DensityFunction> offset) {
        return add(
                yClampedGradient(-64, 272, 1.5, -1.5),
                holderFunction(offset)
        );
    }

    private static DensityFunction aylythY() {
        int min = DimensionType.MIN_HEIGHT * 2;
        int max = DimensionType.MAX_COLUMN_HEIGHT * 2;
        return yClampedGradient(
                min,
                max,
                min,
                max
        );
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

    static DensityFunction slopedCheese(RegistryEntry<DensityFunction> depth, RegistryEntry<DensityFunction> jaggednes, RegistryEntry<DensityFunction> factor, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return add(
                mul(
                        constant(4.0),
                        mul(
                                add(
                                        holderFunction(depth),
                                        mul(
                                                holderFunction(jaggednes),
                                                noise(noiseParameters.getOrThrow(JAGGED), 1500.0, 0.0).halfNegative()
                                        )
                                ),
                                holderFunction(factor)
                        ).quarterNegative()
                ),
                InterpolatedNoiseSampler.createBase3dNoiseFunction(0.25, 0.125, 80.0, 160.0, 8.0)
        );
    }

    private static DensityFunction cavesSpaghettiRoughness(RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return cacheOnce(
                mul(
                        add(
                                constant(-0.05),
                                mul(
                                        constant(-0.05),
                                        noise(noiseParameters.getOrThrow(SPAGHETTI_2D_MODULATOR))
                                )
                        ),
                        add(
                                constant(-0.4),
                                noise(noiseParameters.getOrThrow(SPAGHETTI_2D_ROUGHNESS)).abs()
                        )
                )
        );
    }

    private static DensityFunction cavesSpaghetti2dThickness(RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return cacheOnce(
                add(
                        constant(-0.95),
                        mul(
                                constant(-0.35),
                                noise(noiseParameters.getOrThrow(SPAGHETTI_2D_THICKNESS), 2.0, 1.0)
                        )
                )
        );
    }

    private static DensityFunction cavesSpaghetti2d(RegistryEntry<DensityFunction> caveSpaghetti2dThicknessModulator, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return max(
                    add(
                            weirdScaledSampler(
                                noise(noiseParameters.getOrThrow(SPAGHETTI_2D_MODULATOR), 2.0, 1.0),
                                noiseParameters.getOrThrow(SPAGHETTI_2D),
                                WeirdScaledSampler.RarityValueMapper.TYPE2
                            ),
                            mul(
                                 constant(0.083),
                                 holderFunction(caveSpaghetti2dThicknessModulator)
                            )
                    ),
                    add(
                            add(
                                    add(
                                            constant(0.0),
                                            mul(
                                                    constant(8.0),
                                                    noise(noiseParameters.getOrThrow(SPAGHETTI_2D_ELEVATION), 1.0, 0.0)
                                            )
                                    ),
                                    yClampedGradient(
                                            -64,
                                            80,
                                            8.0,
                                            -40.0
                                    )
                            ).abs(),
                            holderFunction(caveSpaghetti2dThicknessModulator)
                    )
        ).clamp(-1.0, 1.0);
    }

    private static DensityFunction cavesNoodle(RegistryEntry<DensityFunction> y, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) { // NOTE: This is the cave type that sucks and I hate
        return rangeChoice(
                interpolated(
                        rangeChoice(
                            holderFunction(y),
                                -60.0,
                                21.0,
                                noise(noiseParameters.getOrThrow(NOODLE)),
                                constant(-1.0)
                        )
                ),
                -1000000.0,
                0.0,
                constant(64.0),
                add(
                        interpolated(
                                rangeChoice(
                                        holderFunction(y),
                                        -60.0,
                                        21.0,
                                        add(
                                                constant(-0.075),
                                                mul(
                                                        constant(-0.025),
                                                        noise(noiseParameters.getOrThrow(NOODLE_THICKNESS))
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
                                                       holderFunction(y),
                                                       -60.0,
                                                        21.0,
                                                        noise(noiseParameters.getOrThrow(NOODLE_RIDGE_A), 2.6666666666666665, 2.6666666666666665),
                                                        constant(0.0)
                                                )
                                        ).abs(),
                                        interpolated(
                                                rangeChoice(
                                                        holderFunction(y),
                                                        -60.0,
                                                        21.0,
                                                        noise(noiseParameters.getOrThrow(NOODLE_RIDGE_B), 2.6666666666666665, 2.6666666666666665),
                                                        constant(0.0)
                                                )
                                        )
                                )
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
                                        noise(noiseParameters.getOrThrow(PILLAR), 25.0, 0.3)
                                ),
                                add(
                                        constant(-1.0),
                                        mul(
                                                constant(-1.0),
                                                noise(noiseParameters.getOrThrow(PILLAR_RARENESS))
                                        )
                                )
                        ),
                        add(
                                constant(0.55),
                                mul(
                                        constant(0.55),
                                        noise(noiseParameters.getOrThrow(PILLAR_THICKNESS))
                                )
                        ).cube()
                )
        );
    }

    private static DensityFunction cavesEntrances() {
        return constant(1);
    }

    static DensityFunction holderFunction(RegistryEntry<DensityFunction> functionEntry) {
        return new RegistryEntryHolder(functionEntry);
    }

    static DensityFunction withBlending(DensityFunction end, DensityFunction start) {
        return flatCache(cache2d(lerp(blendAlpha(), start, end)));
    }

    static DensityFunction slide(DensityFunction densityFunction, int i, int j, int k, int l, double d, int m, int n, double e) {
        DensityFunction densityFunction2 = densityFunction;
        DensityFunction densityFunction3 = yClampedGradient(i + j - k, i + j - l, 1.0, 0.0);
        densityFunction2 = lerp(densityFunction3, constant(d), densityFunction2);
        DensityFunction densityFunction4 = yClampedGradient(i + m, i + n, 0.0, 1.0);
        densityFunction2 = lerp(densityFunction4, constant(e), densityFunction2);
        return densityFunction2;
    }

    static DensityFunction postProcess(DensityFunction densityFunction) {
        DensityFunction densityFunction2 = blendDensity(densityFunction);
        return mul(interpolated(densityFunction2), constant(0.64)).squeeze();
    }
}
