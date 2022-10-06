package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.noise.InterpolatedNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.source.util.VanillaTerrainParametersCreator;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;

import static moriyashiine.aylyth.datagen.AylythNoiseTypes.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public class AylythDensityFunctionTypes {

    public static void init() {}

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

    public static final RegistryEntry<DensityFunction> SHIFT_X;
    public static final RegistryEntry<DensityFunction> SHIFT_Z;
    public static final RegistryEntry<DensityFunction> RIDGES_FUNCTION;
    public static final RegistryEntry<DensityFunction> RIDGES_FOLDED_FUNCTION;
    public static final RegistryEntry<DensityFunction> CONTINENTS_FUNCTION;
    public static final RegistryEntry<DensityFunction> EROSION_FUNCTION;
    public static final RegistryEntry<DensityFunction> OFFSET_FUNCTION;
    public static final RegistryEntry<DensityFunction> FACTOR_FUNCTION;
    public static final RegistryEntry<DensityFunction> JAGGEDNESS_FUNCTION;
    public static final RegistryEntry<DensityFunction> DEPTH_FUNCTION;
    public static final RegistryEntry<DensityFunction> SLOPED_CHEESE_FUNCTION;
    public static final RegistryEntry<DensityFunction> Y_FUNCTION;
    public static final RegistryEntry<DensityFunction> CAVES_SPAGHETTI_ROUGHNESS_FUNCTION;
    public static final RegistryEntry<DensityFunction> CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR_FUNCTION;
    public static final RegistryEntry<DensityFunction> CAVES_SPAGHETTI_2D_FUNCTION;
    public static final RegistryEntry<DensityFunction> CAVES_NOODLE_FUNCTION;
    public static final RegistryEntry<DensityFunction> CAVES_PILLARS_FUNCTION;
    public static final RegistryEntry<DensityFunction> CAVES_ENTRANCES_FUNCTION;

    private static RegistryKey<DensityFunction> functionKey(String id) {
        return RegistryKey.of(Registry.DENSITY_FUNCTION_KEY, new Identifier(Aylyth.MOD_ID, id));
    }

    private static RegistryEntry<DensityFunction> registerFunction(RegistryKey<DensityFunction> key, DensityFunction function) {
        return BuiltinRegistries.add(BuiltinRegistries.DENSITY_FUNCTION, key, function);
    }


    static {
        SHIFT_X = registerFunction(SHIFT_X_KEY, flatCache(cache2d(shiftA(OFFSET))));
        SHIFT_Z = registerFunction(SHIFT_Z_KEY, flatCache(cache2d(shiftB(OFFSET))));
        RIDGES_FUNCTION = registerFunction(RIDGES_FUNCTION_KEY, flatCache(shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, RIDGE)));
        var function = mul(constant(-3.0), add(constant(-0.3333333333333333), add(constant(-0.6666666666666666), holderFunction(RIDGES_FUNCTION).abs()).abs()));
        RIDGES_FOLDED_FUNCTION = registerFunction(RIDGES_FOLDED_FUNCTION_KEY, function);
        CONTINENTS_FUNCTION = registerFunction(CONTINENTS_FUNCTION_KEY, flatCache(shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, CONTINENTS)));
        EROSION_FUNCTION = registerFunction(EROSION_FUNCTION_KEY, flatCache(shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, EROSION)));
        Spline.DensityFunctionWrapper continentsCoordinate = new Spline.DensityFunctionWrapper(CONTINENTS_FUNCTION);
        Spline.DensityFunctionWrapper erosionCoordinate = new Spline.DensityFunctionWrapper(EROSION_FUNCTION);
        Spline.DensityFunctionWrapper ridgesCoordinate = new Spline.DensityFunctionWrapper(RIDGES_FUNCTION);
        Spline.DensityFunctionWrapper ridgesFoldedCoordinate = new Spline.DensityFunctionWrapper(RIDGES_FOLDED_FUNCTION);
        OFFSET_FUNCTION = registerFunction(OFFSET_FUNCTION_KEY, offset(continentsCoordinate, erosionCoordinate, ridgesFoldedCoordinate));
        FACTOR_FUNCTION = registerFunction(FACTOR_FUNCTION_KEY, factor(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate));
        JAGGEDNESS_FUNCTION = registerFunction(JAGGEDNESS_FUNCTION_KEY, jaggedness(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate));
        DEPTH_FUNCTION = registerFunction(DEPTH_FUNCTION_KEY, depth());
        Y_FUNCTION = registerFunction(Y_FUNCTION_KEY, aylythY());
        CAVES_SPAGHETTI_ROUGHNESS_FUNCTION = registerFunction(CAVES_SPAGHETTI_ROUGHNESS_FUNCTION_KEY, cavesSpaghettiRoughness());
        CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR_FUNCTION = registerFunction(CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR_FUNCTION_KEY, cavesSpaghetti2dThickness());
        CAVES_SPAGHETTI_2D_FUNCTION = registerFunction(CAVES_SPAGHETTI_2D_FUNCTION_KEY, cavesSpaghetti2d());
        CAVES_NOODLE_FUNCTION = registerFunction(CAVES_NOODLE_FUNCTION_KEY, cavesNoodle());
        CAVES_PILLARS_FUNCTION = registerFunction(CAVES_PILLARS_FUNCTION_KEY, cavesPillars());
        CAVES_ENTRANCES_FUNCTION = registerFunction(CAVES_ENTRANCES_FUNCTION_KEY, cavesEntrances());
        SLOPED_CHEESE_FUNCTION = registerFunction(SLOPED_CHEESE_FUNCTION_KEY, slopedCheese());
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

    static DensityFunction depth() {
        return add(
                yClampedGradient(-64, 272, 1.5, -1.5),
                holderFunction(OFFSET_FUNCTION)
        );
    }

    private static DensityFunction aylythY() {
        var min = DimensionType.MIN_HEIGHT * 2;
        var max = DimensionType.MAX_COLUMN_HEIGHT * 2;
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

    static DensityFunction slopedCheese() {
        return add(
                mul(
                        constant(4.0),
                        mul(
                                add(
                                        holderFunction(DEPTH_FUNCTION),
                                        mul(
                                                holderFunction(JAGGEDNESS_FUNCTION),
                                                noise(JAGGED, 1500.0, 0.0).halfNegative()
                                        )
                                ),
                                holderFunction(FACTOR_FUNCTION)
                        ).quarterNegative()
                ),
                InterpolatedNoiseSampler.createBase3dNoiseFunction(0.25, 0.125, 80.0, 160.0, 8.0)
        );
    }

//    private static DensityFunction offset() {
//        return flatCache(
//                cache2d(
//                        add(
//                                mul(
//                                        blendOffset(),
//                                        add(
//                                                constant(1),
//                                                mul(
//                                                        constant(-1),
//                                                        cacheOnce(
//                                                                blendAlpha()
//                                                        )
//                                                )
//                                        )
//                                ),
//                                add(
//                                        mul(
//                                                cacheOnce(
//                                                        blendAlpha()
//                                                ),
//                                                add(
//                                                        constant(-0.5037500262260437),
//                                                        spline(
//                                                                splineBuilder(holderFunction(CONTINENTS_FUNCTION))
//                                                                        .method_41295(-1.1f,
//                                                                                splineBuilder(holderFunction(EROSION_FUNCTION))
//                                                                                        .method_41295(-0.85f,
//                                                                                                splineBuilder(holderFunction(RIDGES_FOLDED_FUNCTION))
//                                                                                                        .method_41294(-0.002502462680753048f, 0.03506934471127279f)
//                                                                                                        .method_41294(0.002502462680753048f, 0.03506934471127279f)
//                                                                                                        .build()
//                                                                                        ).build()
//                                                                        )
//                                                                        .method_41295(-1.02f,
//                                                                                splineBuilder(holderFunction(EROSION_FUNCTION))
//                                                                                        .method_41295(-0.85f,
//                                                                                                splineBuilder(holderFunction(RIDGES_FOLDED_FUNCTION))
//                                                                                                        .method_41294( -0.002502462680753048f, 0.09111569309500964f)
//                                                                                                        .method_41294(0.002502462680753048f, 0.09111569309500964f)
//                                                                                                        .build()
//                                                                                        ).build()
//                                                                        )
//                                                                        .method_41295(-0.51f,
//                                                                                splineBuilder(holderFunction(EROSION_FUNCTION))
//                                                                                        .method_41295(-0.85f,
//                                                                                                splineBuilder(holderFunction(RIDGES_FOLDED_FUNCTION))
//                                                                                                        .method_41294(-0.002502462680753048f, 0.16417109360568027f)
//                                                                                                        .method_41294(0.002502462680753048f, 0.16417109360568027f)
//                                                                                                        .build()
//                                                                                        ).build()
//                                                                        )
//                                                                        .method_41295(-0.44f,
//                                                                                splineBuilder(holderFunction(EROSION_FUNCTION))
//                                                                                        .method_41295(-0.85f,
//                                                                                                splineBuilder(holderFunction(RIDGES_FOLDED_FUNCTION))
//                                                                                                        .method_41294(-0.002502462680753048f, 0.22940270935960586f)
//                                                                                                        .method_41294(0.002502462680753048f, 0.22940270935960586f)
//                                                                                                        .build()
//                                                                                        ).build()
//                                                                        )
//                                                                        .method_41295(-0.18f,
//                                                                                splineBuilder(holderFunction(EROSION_FUNCTION))
//                                                                                        .method_41295(-0.85f,
//                                                                                                splineBuilder(holderFunction(RIDGES_FOLDED_FUNCTION))
//                                                                                                        .method_41294(-0.002502462680753048f, 0.2966456280788178f)
//                                                                                                        .method_41294(0.002502462680753048f, 0.2966456280788178f)
//                                                                                                        .build()
//                                                                                        ).build()
//                                                                        )
//                                                                        .method_41295(-0.16f,
//                                                                                splineBuilder(holderFunction(EROSION_FUNCTION))
//                                                                                        .method_41295(-0.85f,
//                                                                                                splineBuilder(holderFunction(RIDGES_FOLDED_FUNCTION))
//                                                                                                        .method_41294(-1.083819874392086f, 0.08061521966503271f)
//                                                                                                        .add(-1.083819874392086f, 0.08061521966503271f, 1.7427089930485968f)
//                                                                                                        .add(-0.7603488537946785f, 0.683503588530867f, 2.1194961737672737f)
//                                                                                                        .add(-0.7603488537946785f, 0.683503588530867f, -1.9754730014112176f)
//                                                                                                        .add(-0.39614656780349233f, 0.08061521966503271f, -1.5769496352778398f)
//                                                                                                        .add(-0.39614656780349233f, 0.08061521966503271f, -0.3500481674559379f)
//                                                                                                        .add(-0.002502462680753048f, -0.07443219875756402f, -0.5996575709648867f)
//                                                                                                        .add(-0.002502462680753048f, -0.07443219875756402f, 0.558339315829996f)
//                                                                                                        .add(0.39614656780349233f, 0.08061521966503271f, 0.3500481674559379f)
//                                                                                                        .add(0.39614656780349233f, 0.08061521966503271f, 1.5769496352778398f)
//                                                                                                        .add(0.7603488537946785f, 0.683503588530867f, 1.9754730014112176f)
//                                                                                                        .add(0.7603488537946785f, 0.683503588530867f, -2.1194961737672737f)
//                                                                                                        .add(1.083819874392086f, 0.08061521966503271f, -1.7427089930485968f)
//                                                                                                        .method_41294(1.083819874392086f, 0.08061521966503271f)
//                                                                                                        .build()
//                                                                                                )
//                                                                                        .method_41295(-0.7f,
//                                                                                                splineBuilder(holderFunction(RIDGES_FOLDED_FUNCTION))
//                                                                                                        .method_41294(-1.083819874392086f, 0.08061521966503271f)
//                                                                                                        .add(-1.083819874392086f, 0.08061521966503271f, 1.7427089930485968f)
//                                                                                                        .add(-0.7603488537946785f, 0.6202149430127908f, 2.1194961737672737f)
//                                                                                                )
//                                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//    }

    private static DensityFunction cavesSpaghettiRoughness() {
        return cacheOnce(
                mul(
                        add(
                                constant(-0.05),
                                mul(
                                        constant(-0.05),
                                        noise(SPAGHETTI_2D_MODULATOR)
                                )
                        ),
                        add(
                                constant(-0.4),
                                noise(SPAGHETTI_2D_ROUGHNESS).abs()
                        )
                )
        );
    }

    private static DensityFunction cavesSpaghetti2dThickness() {
        return cacheOnce(
                add(
                        constant(-0.95),
                        mul(
                                constant(-0.35000000000000003),
                                noise(SPAGHETTI_2D_THICKNESS, 2.0, 1.0)
                        )
                )
        );
    }

    private static DensityFunction cavesSpaghetti2d() {
        return max(
                    add(
                            weirdScaledSampler(
                                noise(SPAGHETTI_2D_MODULATOR, 2.0, 1.0),
                                SPAGHETTI_2D,
                                WeirdScaledSampler.RarityValueMapper.TYPE2
                            ),
                             mul(
                                 constant(0.083),
                                 holderFunction(CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR_FUNCTION)
                            )
                    ),
                    add(
                            add(
                                    add(
                                            constant(0.0),
                                            mul(
                                                    constant(8.0),
                                                    noise(SPAGHETTI_2D_ELEVATION, 1.0, 0.0)
                                            )
                                    ),
                                    yClampedGradient(
                                            -64,
                                            320,
                                            8.0,
                                            -40.0
                                    )
                            ).abs(),
                            holderFunction(CAVES_SPAGHETTI_2D_THICKNESS_MODULATOR_FUNCTION)
                    )
        ).clamp(-1.0, 1.0);
    }

    private static DensityFunction cavesNoodle() {
        return rangeChoice(
                interpolated(
                        rangeChoice(
                            holderFunction(Y_FUNCTION),
                                -60.0,
                                321.0,
                                noise(NOODLE),
                                constant(-1.0)
                        )
                ),
                -1000000.0,
                0.0,
                constant(64.0),
                add(
                        interpolated(
                                rangeChoice(
                                        holderFunction(Y_FUNCTION),
                                        -60.0,
                                        321.0,
                                        add(
                                                constant(-0.07500000000000001),
                                                mul(
                                                        constant(-0.025),
                                                        noise(NOODLE_THICKNESS)
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
                                                       holderFunction(Y_FUNCTION),
                                                       -60.0,
                                                       321.0,
                                                        noise(NOODLE_RIDGE_A, 2.6666666666666665, 2.6666666666666665),
                                                        constant(0.0)
                                                )
                                        ).abs(),
                                        interpolated(
                                                rangeChoice(
                                                        holderFunction(Y_FUNCTION),
                                                        -60.0,
                                                        321.0,
                                                        noise(NOODLE_RIDGE_B, 2.6666666666666665, 2.6666666666666665),
                                                        constant(0.0)
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static DensityFunction cavesPillars() {
        return cacheOnce(
                mul(
                        add(
                                mul(
                                        constant(2.0),
                                        noise(PILLAR, 25.0, 0.3)
                                ),
                                add(
                                        constant(-1.0),
                                        mul(
                                                constant(-1.0),
                                                noise(PILLAR_RARENESS)
                                        )
                                )
                        ),
                        add(
                                constant(0.55),
                                mul(
                                        constant(0.55),
                                        noise(PILLAR_THICKNESS)
                                )
                        ).cube()
                )
        );
    }

    private static DensityFunction cavesEntrances() {
        return cacheOnce(
                min(
                        add(
                                add(
                                        constant(0.37),
                                        noise(CAVE_ENTRANCES, 0.75, 0.5)
                                ),
                                yClampedGradient(
                                        -10,
                                        30,
                                        0.3,
                                        0.0
                                )
                        ),
                        add(
                                holderFunction(CAVES_SPAGHETTI_ROUGHNESS_FUNCTION),
                                add(
                                        max(
                                                weirdScaledSampler(
                                                        cacheOnce(
                                                                noise(SPAGHETTI_3D_RARITY, 2.0, 1.0)
                                                        ),
                                                        SPAGHETTI_3D_1,
                                                        WeirdScaledSampler.RarityValueMapper.TYPE1
                                                ),
                                                weirdScaledSampler(
                                                        cacheOnce(
                                                                noise(SPAGHETTI_3D_RARITY, 2.0, 1.0)
                                                        ),
                                                        SPAGHETTI_3D_2,
                                                        WeirdScaledSampler.RarityValueMapper.TYPE1
                                                )
                                        ),
                                        add(
                                                constant(-0.0765),
                                                mul(
                                                        constant(-0.011499999999999996),
                                                        noise(SPAGHETTI_3D_THICKNESS)
                                                )
                                        )
                                ).clamp(-1.0, 1.0)
                        )
                )
        );
    }

    static DensityFunction holderFunction(RegistryEntry<DensityFunction> functionEntry) {
        return new RegistryEntryHolder(functionEntry);
    }

    static DensityFunction noiseFromKey(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
        return DensityFunctionTypes.noise(noiseEntry(key));
    }

    static RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> noiseEntry(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
        return BuiltinRegistries.NOISE_PARAMETERS.getEntry(key).get();
    }

    static DensityFunction withBlending(DensityFunction end, DensityFunction start) {
        return flatCache(cache2d(lerp(blendAlpha(), start, end)));
    }

    static DensityFunction getFunctionRaw(String id) {
        return BuiltinRegistries.DENSITY_FUNCTION.get(RegistryKey.of(Registry.DENSITY_FUNCTION_KEY, new Identifier(id)));
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

    static RegistryEntry<DensityFunction> densityEntry(RegistryKey<DensityFunction> key) {
        return BuiltinRegistries.DENSITY_FUNCTION.getEntry(key).get();
    }

    static DensityFunction scaledNoise(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key, double xzScale, double yScale) {
        return DensityFunctionTypes.noise(noiseEntry(key), xzScale, yScale);
    }

    static net.minecraft.util.math.Spline.Builder<DensityFunctionTypes.Spline.SplinePos, DensityFunctionTypes.Spline.DensityFunctionWrapper> splineBuilder(DensityFunction function) {
        return net.minecraft.util.math.Spline.builder(new DensityFunctionTypes.Spline.DensityFunctionWrapper(RegistryEntry.of(function)));
    }
}
