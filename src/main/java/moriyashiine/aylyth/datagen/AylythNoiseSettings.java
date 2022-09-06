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

import static moriyashiine.aylyth.datagen.AylythDensityFunctionTypes.*;
import static moriyashiine.aylyth.datagen.AylythNoiseTypes.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public class AylythNoiseSettings {

    public static void init() {
        AylythNoiseTypes.init();
        AylythDensityFunctionTypes.init();
    }

    public static final ChunkGeneratorSettings SETTINGS = Registry.register(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, new Identifier(Aylyth.MOD_ID, "aylyth_settings"), createSettings());

    static ChunkGeneratorSettings createSettings() {
        var seaLevel = 0;
        var disableMobGen = false;
        var aquifers = false;
        var oreVeins = true;
        var legacyRandom = false;
        return new ChunkGeneratorSettings(shapeConfig(), defaultState(Blocks.DEEPSLATE), defaultState(Blocks.WATER), noiseRouter(), materialRules(), spawnTargets(), seaLevel, disableMobGen, aquifers, oreVeins, legacyRandom);
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
                                yClampedGradient(
                                        holderFunction(SLOPED_CHEESE_FUNCTION),
                                        -1000000.0,
                                        1.5625,
                                        holderFunction(SLOPED_CHEESE_FUNCTION),
                                        max(
                                                
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
//                        yClampedGradient(
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
