package moriyashiine.aylyth.datagen;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Spline;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;
import net.minecraft.util.math.noise.InterpolatedNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.biome.source.util.VanillaTerrainParametersCreator;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes.Spline.SplinePos;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes.Spline.DensityFunctionWrapper;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.noise.NoiseRouter;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

import java.util.List;

import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public class AylythNoiseSettings {

    public static void init() {}

    public static final RegistryKey<NoiseParameters> OFFSET_KEY = noiseKey("offset");
    public static final RegistryKey<NoiseParameters> RIDGE_KEY = noiseKey("ridge");
    public static final RegistryKey<NoiseParameters> JAGGED_KEY = noiseKey("jagged");
    public static final RegistryKey<NoiseParameters> CONTINENTS_KEY = noiseKey("continents");
    public static final RegistryKey<NoiseParameters> TEMPERATURE_KEY = noiseKey("temperature");
    public static final RegistryKey<NoiseParameters> VEGETATION_KEY = noiseKey("vegetation");
    public static final RegistryKey<NoiseParameters> EROSION_KEY = noiseKey("erosion");
    public static final RegistryKey<NoiseParameters> BASE_LAYER_KEY = noiseKey("base_layer");
    public static final RegistryKey<NoiseParameters> CAVE_CHEESE_KEY = noiseKey("cave_cheese");
    public static final RegistryEntry<NoiseParameters> OFFSET;
    public static final RegistryEntry<NoiseParameters> RIDGE;
    public static final RegistryEntry<NoiseParameters> JAGGED;
    public static final RegistryEntry<NoiseParameters> CONTINENTS;
    public static final RegistryEntry<NoiseParameters> TEMPERATURE;
    public static final RegistryEntry<NoiseParameters> VEGETATION;
    public static final RegistryEntry<NoiseParameters> EROSION;
    public static final RegistryEntry<NoiseParameters> BASE_LAYER;
    public static final RegistryEntry<NoiseParameters> CAVE_CHEESE;

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

    private static RegistryKey<NoiseParameters> noiseKey(String id) {
        return RegistryKey.of(Registry.NOISE_KEY, new Identifier(Aylyth.MOD_ID, id));
    }

    private static RegistryKey<DensityFunction> functionKey(String id) {
        return RegistryKey.of(Registry.DENSITY_FUNCTION_KEY, new Identifier(Aylyth.MOD_ID, id));
    }

    private static RegistryEntry<NoiseParameters> registerNoise(RegistryKey<NoiseParameters> key, int firstOctave, double... amplitudes) {
        return BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, key, new NoiseParameters(firstOctave, DoubleList.of(amplitudes)));
    }

    private static RegistryEntry<DensityFunction> registerFunction(RegistryKey<DensityFunction> key, DensityFunction function) {
        return BuiltinRegistries.add(BuiltinRegistries.DENSITY_FUNCTION, key, function);
    }

    private static DensityFunction offset(DensityFunctionWrapper continentsCoordinate, DensityFunctionWrapper erosionCoordinate, DensityFunctionWrapper ridgesFoldedCoordinate) {
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
                gradient(-64, 272, -1.5, 1.5),
                holderFunction(OFFSET_FUNCTION)
        );
    }

    private static DensityFunction factor(DensityFunctionWrapper continentsCoordinate, DensityFunctionWrapper erosionCoordinate, DensityFunctionWrapper ridgesCoordinate, DensityFunctionWrapper ridgesFoldedCoordinate) {
        return withBlending(
                        spline(VanillaTerrainParametersCreator.createFactorSpline(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate, false)),
                        constant(10)
                );
    }

    private static DensityFunction jaggedness(DensityFunctionWrapper continentsCoordinate, DensityFunctionWrapper erosionCoordinate, DensityFunctionWrapper ridgesCoordinate, DensityFunctionWrapper ridgesFoldedCoordinate) {
        return withBlending(
                spline(VanillaTerrainParametersCreator.createJaggednessSpline(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate, false)),
                constant(0)
        );
    }

    static DensityFunction slopedCheese() {
        return  add(
                mul(
                        constant(4),
                        mul(
                                add(
                                        holderFunction(DEPTH_FUNCTION),
                                        mul(
                                                holderFunction(JAGGEDNESS_FUNCTION),
                                                noiseFromKey(JAGGED_KEY).halfNegative()
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

    static {
        OFFSET = registerNoise(OFFSET_KEY, -3, 1.0, 1.0, 1.0, 0.0);
        RIDGE = registerNoise(RIDGE_KEY, -7, 1.0, 2.0, 1.0, 0.0, 0.0, 0.0);
        JAGGED = registerNoise(JAGGED_KEY, -16, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
        CONTINENTS = registerNoise(CONTINENTS_KEY, -9, 1.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0);
        TEMPERATURE = registerNoise(TEMPERATURE_KEY,-10, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0);
        VEGETATION = registerNoise(VEGETATION_KEY,-8, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0);
        EROSION = registerNoise(EROSION_KEY, -9, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0);
        BASE_LAYER = registerNoise(BASE_LAYER_KEY,-8, 1.0, 1.0, 1.0, 1.0);
        CAVE_CHEESE = registerNoise(CAVE_CHEESE_KEY, -8, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 0.0, 2.0, 0.0);

        SHIFT_X = registerFunction(SHIFT_X_KEY, flatCache(cache2d(shiftA(OFFSET))));
        SHIFT_Z = registerFunction(SHIFT_Z_KEY, flatCache(cache2d(shiftB(OFFSET))));
        RIDGES_FUNCTION = registerFunction(RIDGES_FUNCTION_KEY, flatCache(shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, RIDGE)));
        var function = mul(constant(-3.0), add(constant(-0.3333333333333333), add(constant(-0.6666666666666666), holderFunction(RIDGES_FUNCTION).abs()).abs()));
        RIDGES_FOLDED_FUNCTION = registerFunction(RIDGES_FOLDED_FUNCTION_KEY, function);
        CONTINENTS_FUNCTION = registerFunction(CONTINENTS_FUNCTION_KEY, flatCache(shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, CONTINENTS)));
        EROSION_FUNCTION = registerFunction(EROSION_FUNCTION_KEY, flatCache(shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, EROSION)));
        DensityFunctionWrapper continentsCoordinate = new DensityFunctionWrapper(CONTINENTS_FUNCTION);
        DensityFunctionWrapper erosionCoordinate = new DensityFunctionWrapper(EROSION_FUNCTION);
        DensityFunctionWrapper ridgesCoordinate = new DensityFunctionWrapper(RIDGES_FUNCTION);
        DensityFunctionWrapper ridgesFoldedCoordinate = new DensityFunctionWrapper(RIDGES_FOLDED_FUNCTION);
        OFFSET_FUNCTION = registerFunction(OFFSET_FUNCTION_KEY, offset(continentsCoordinate, erosionCoordinate, ridgesFoldedCoordinate));
        FACTOR_FUNCTION = registerFunction(FACTOR_FUNCTION_KEY, factor(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate));
        JAGGEDNESS_FUNCTION = registerFunction(JAGGEDNESS_FUNCTION_KEY, jaggedness(continentsCoordinate, erosionCoordinate, ridgesCoordinate, ridgesFoldedCoordinate));
        DEPTH_FUNCTION = registerFunction(DEPTH_FUNCTION_KEY, depth());
        SLOPED_CHEESE_FUNCTION = registerFunction(SLOPED_CHEESE_FUNCTION_KEY, slopedCheese());
    }

    public static final ChunkGeneratorSettings SETTINGS = Registry.register(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, new Identifier(Aylyth.MOD_ID, "aylyth_settings"), createSettings());

    static ChunkGeneratorSettings createSettings() {
        var seaLevel = 0;
        var disableMobGen = false;
        var aquifers = false;
        var oreVeins = true;
        var legacyRandom = false;
        return new ChunkGeneratorSettings(shapeConfig(), defaultState(Blocks.STONE), defaultState(Blocks.WATER), noiseRouter(), materialRules(), spawnTargets(), seaLevel, disableMobGen, aquifers, oreVeins, legacyRandom);
    }

    static BlockState defaultState(Block block) {
        return block.getDefaultState();
    }

    static GenerationShapeConfig shapeConfig() {
        var minY = -32;
        var height = 128;
        var horizontal = 1;
        var vertical = 1;
        return new GenerationShapeConfig(minY, height, horizontal, vertical);
    }

    static NoiseRouter testRouter() {
        DensityFunction densityFunction2 = getFunctionRaw("shift_x");
        DensityFunction densityFunction3 = getFunctionRaw("shift_z");
        DensityFunction densityFunction4 = shiftedNoise(densityFunction2, densityFunction3, 0.25, BuiltinRegistries.NOISE_PARAMETERS.entryOf(NoiseParametersKeys.TEMPERATURE));
        DensityFunction densityFunction5 = shiftedNoise(densityFunction2, densityFunction3, 0.25, BuiltinRegistries.NOISE_PARAMETERS.entryOf(NoiseParametersKeys.VEGETATION));
        DensityFunction den = BuiltinRegistries.DENSITY_FUNCTION.get(new Identifier("overworld_amplified/sloped_cheese"));
        DensityFunction densityFunction6 = postProcess(slide(den, -64, 200, 16, 0, -0.078125, 0, 24, 0.1171875));
        return new NoiseRouter(zero(), zero(), zero(), zero(), densityFunction4, densityFunction5, zero(), zero(), zero(), zero(), zero(), densityFunction6, zero(), zero(), zero());
    }

    static NoiseRouter noiseRouter() {
        return new NoiseRouter(
                zero(),  // barrierNoise
                zero(),  // fluidLevelFloodednessNoise
                zero(),  // fluidLevelSpreadNoise
                zero(),  // lavaNoise
                shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, TEMPERATURE),  // temperature
                shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, VEGETATION),  // vegetation
                holderFunction(CONTINENTS_FUNCTION),  // continents
                holderFunction(EROSION_FUNCTION),  // erosion
                holderFunction(DEPTH_FUNCTION),  // depth
                holderFunction(RIDGES_FUNCTION),  // ridges
                initialDensity(),  // initialDensityWithoutJaggedness
                finalDensity(),  // finalDensity
                zero(),  // veinToggle
                zero(),  // veinRidged
                zero()); // veinGap
    }

    static DensityFunction initialDensity() {
        return slide(
                add(
                        constant(-0.703125),
                        mul(
                                constant(4),
                                mul(
                                        holderFunction(DEPTH_FUNCTION),
                                        cache2d(holderFunction(FACTOR_FUNCTION))
                                ).quarterNegative()
                        )
                ).clamp(-64, 64),
                -64,
                272,
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
//                -64,
//                272,
//                80, 64, -0.078125,
//                0, 24, 0.1171875
//        );
    }

    static DensityFunction finalDensity() {
        return min(
                postProcess(
                        slide(
                                condition(
                                        holderFunction(SLOPED_CHEESE_FUNCTION),
                                        -1000000.0,
                                        1.5625,
                                        holderFunction(SLOPED_CHEESE_FUNCTION),
                                        add(
                                                mul(
                                                        constant(4),
                                                        scaledNoise(NoiseParametersKeys.CAVE_LAYER, 1, 8).square()
                                                ),
                                                add(
                                                        add(
                                                                constant(0.27),
                                                                noiseCaves()
                                                        ).clamp(-1, 1),
                                                        add(
                                                                constant(1.5),
                                                                mul(
                                                                        constant(-0.64),
                                                                        holderFunction(SLOPED_CHEESE_FUNCTION)
                                                                )
                                                        ).clamp(0, 0.5)
                                                )
                                        )
                                ),
                                -64,
                                272,
                                80, 64, -0.078125,
                                0, 24, 0.1171875
                        )
                ),
                noiseCaves()
        );
//        return postProcess(
//                slide(
//                        condition(
//                                add(
//                                        yClampedGradient(-64, 84, 1.0, 0.0),
//                                        add(
//                                                smallVariedFlatterLand(),
//                                                plateauedHills()
//                                        )
//                                ),
//                                -100000.0,
//                                1.5,
//                                add(
//                                        yClampedGradient(-64, 84, 1.0, 0.0),
//                                        add(
//                                                smallVariedFlatterLand(),
//                                                plateauedHills()
//                                        )
//                                ),
//                                noiseCaves()
//
//                        ),
//                        -64,
//                        272,
//                        80, 64, -0.078125,
//                        0, 24, 0.1171875
//                )
//        );
    }

    // This is the regular base terrain. Should be flatter, with gradual variations in the land
    static DensityFunction smallVariedFlatterLand() {
        return add(
                yClampedGradient(64, 84, 1.0, -2.0),
                noise(BASE_LAYER, 1, 0.5)
        );
//        return add(
//                yClampedGradient(64, 84, 1.0, -2.0),
//                scaledNoise(GRAVEL, 1, 0.5)
//        );
    }

    // This is for the uplands biome. These should be somewhat rare. Should be a large area created with a combination of mountainous terrain and plateaus.
    static DensityFunction plateauedHills() {
        return add(
                yClampedGradient(64, 150, 3.0, -1.0),
                spline(splineBuilder(noise(CONTINENTS, 1, 1))
                        .add(0.3f, 0.4f, 0.0f)
                        .add(0.5f, 1.25f, 2.0f)
                        .add(0.65f, 2.25f, 0.0f)
                        .add(1.0f, 2.25f).build())
        );
//        return add(
//                noiseFromKey(CONTINENTALNESS),
//                yClampedGradient(84, 150, 1, 0)
//        );
    }

    // This is to add some cheese caves
    static DensityFunction noiseCaves() {
        return add(
                noise(CAVE_CHEESE, 1.0, 1.0),
                constant(0.27)
        ).clamp(-1.0, 1.0);
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

    static DensityFunction condition(DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return rangeChoice(input, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    static DensityFunction gradient(int fromY, int toY, double fromValue, double toValue) {
        return yClampedGradient(fromY, toY, fromValue, toValue);
    }

    static DensityFunction postProcess(DensityFunction densityFunction) {
        DensityFunction densityFunction2 = blendDensity(densityFunction);
        return mul(interpolated(densityFunction2), constant(0.64)).squeeze();
    }

    static DensityFunction withBlending(DensityFunction end, DensityFunction start) {
        return flatCache(cache2d(lerp(blendAlpha(), start, end)));
    }

    static RegistryEntry<DensityFunction> densityEntry(RegistryKey<DensityFunction> key) {
        return BuiltinRegistries.DENSITY_FUNCTION.getEntry(key).get();
    }

    static DensityFunction scaledNoise(RegistryKey<NoiseParameters> key, double xzScale, double yScale) {
        return DensityFunctionTypes.noise(noiseEntry(key), xzScale, yScale);
    }

    static DensityFunction noiseFromKey(RegistryKey<NoiseParameters> key) {
        return DensityFunctionTypes.noise(noiseEntry(key));
    }

    static RegistryEntry<NoiseParameters> noiseEntry(RegistryKey<NoiseParameters> key) {
        return BuiltinRegistries.NOISE_PARAMETERS.getEntry(key).get();
    }

    static DensityFunction holderFunction(RegistryEntry<DensityFunction> functionEntry) {
        return new RegistryEntryHolder(functionEntry);
    }

    static Spline.Builder<SplinePos, DensityFunctionWrapper> splineBuilder(DensityFunction function) {
        return Spline.builder(new DensityFunctionWrapper(RegistryEntry.of(function)));
    }

    static MaterialRules.MaterialRule materialRules() {
        var dirt = MaterialRules.block(defaultState(Blocks.DIRT));
        var grass = MaterialRules.block(defaultState(Blocks.GRASS_BLOCK));
        var onReplaceWaterWithGrass = MaterialRules.condition(MaterialRules.water(-1, 0), grass);
        var onFloor = MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(onReplaceWaterWithGrass, dirt));
        var onUnderFloor = MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, dirt);
        return MaterialRules.sequence(onFloor, onUnderFloor);
    }

    static List<MultiNoiseUtil.NoiseHypercube> spawnTargets() {
        return List.of();
    }
}
