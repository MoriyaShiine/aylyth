package moriyashiine.aylyth.datagen.common.world.terrain;

import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.data.world.terrain.AylythDensityFunctions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.NoiseHypercube;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseRouter;

import java.util.List;

import static moriyashiine.aylyth.common.data.world.terrain.AylythDensityFunctions.*;
import static moriyashiine.aylyth.common.data.world.terrain.AylythNoises.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public final class AylythNoiseSettingBootstrap {
    private AylythNoiseSettingBootstrap() {}

    public static void bootstrap(Registerable<ChunkGeneratorSettings> context) {
        var densityFunctions = context.getRegistryLookup(RegistryKeys.DENSITY_FUNCTION);
        var noiseParameters = context.getRegistryLookup(RegistryKeys.NOISE_PARAMETERS);

        // VANILLACOPY ChunkGeneratorSettings.OVERWORLD
        // Fewer oceans, flatter terrain, disable ore veins, no caves entrances, height 384 -> 304, vertical size 2 -> 1

        var vanillaChunkGenSettings = ChunkGeneratorSettings.createSurfaceSettings(context, false, false);

        var vanillaShape = vanillaChunkGenSettings.generationShapeConfig();
        var shape = new GenerationShapeConfig(vanillaShape.minimumY(), 304, vanillaShape.horizontalSize(), 1);

        var vanillaNoiseRouter = DensityFunctions.createSurfaceNoiseRouter(densityFunctions, noiseParameters, false, false);
        var noiseRouter = new NoiseRouter(
                vanillaNoiseRouter.barrierNoise(),
                vanillaNoiseRouter.fluidLevelFloodednessNoise(),
                vanillaNoiseRouter.fluidLevelSpreadNoise(),
                vanillaNoiseRouter.lavaNoise(),
                vanillaNoiseRouter.temperature(),
                vanillaNoiseRouter.vegetation(),
                AylythDensityFunctionBootstrap.holderFunction(densityFunctions.getOrThrow(MODIFIED_CONTINENTS)),
                AylythDensityFunctionBootstrap.holderFunction(densityFunctions.getOrThrow(AylythDensityFunctions.MODIFIED_EROSION)),
                AylythDensityFunctionBootstrap.holderFunction(densityFunctions.getOrThrow(OVERRIDDEN_DEPTH)),
                vanillaNoiseRouter.ridges(),
                AylythDensityFunctionBootstrap.holderFunction(densityFunctions.getOrThrow(OVERRIDDEN_INITIAL_DENSITY_WITHOUT_JAGGEDNESS)),
                AylythDensityFunctionBootstrap.holderFunction(densityFunctions.getOrThrow(OVERRIDDEN_FINALE_DENSITY)),
                zero(),
                zero(),
                zero()
        );

        var materialRules = AylythMaterialRuleBootstrap.materialRules();
        var spawnTarget = List.<NoiseHypercube>of();
        var seaLevel = vanillaChunkGenSettings.seaLevel();

        context.register(AylythDimensionData.CHUNK_GEN_SETTINGS, new ChunkGeneratorSettings(shape, Blocks.DEEPSLATE.getDefaultState(), Blocks.WATER.getDefaultState(), noiseRouter, materialRules, spawnTarget, seaLevel, false, true, false, false));

        // Old
        // context.register(AylythDimensionData.CHUNK_GEN_SETTINGS, createSettings(context.getRegistryLookup(RegistryKeys.DENSITY_FUNCTION), context.getRegistryLookup(RegistryKeys.NOISE_PARAMETERS)));
    }

    static ChunkGeneratorSettings createSettings(RegistryEntryLookup<DensityFunction> functions, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        var seaLevel = 47;
        var disableMobGen = false;
        var aquifers = true;
        var oreVeins = false;
        var legacyRandom = false;
        return new ChunkGeneratorSettings(shapeConfig(), defaultState(Blocks.DEEPSLATE), defaultState(Blocks.WATER), noiseRouter(functions, noiseParameters), AylythMaterialRuleBootstrap.materialRules(), spawnTargets(), seaLevel, disableMobGen, aquifers, oreVeins, legacyRandom);
    }

    static BlockState defaultState(Block block) {
        return block.getDefaultState();
    }

    static GenerationShapeConfig shapeConfig() {
        var minY = -64;
        var height = 304;
        var horizontal = 1;
        var vertical = 1;
        return new GenerationShapeConfig(minY, height, horizontal, vertical);
    }

    static NoiseRouter noiseRouter(RegistryEntryLookup<DensityFunction> functions, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return new NoiseRouter(
                zero(),  // barrierNoise
                noise(noiseParameters.getOrThrow(FLOODEDNESS), 1.0D, 0.67D),  // fluidLevelFloodednessNoise
                noise(noiseParameters.getOrThrow(FLUID_SPREAD), 1.0D, 1D / 1.4D),  // fluidLevelSpreadNoise
                zero(),  // lavaNoise
                shiftedNoise(AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(SHIFT_X_KEY)), AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(SHIFT_Z_KEY)), 0.25, noiseParameters.getOrThrow(TEMPERATURE)),  // temperature
                shiftedNoise(AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(SHIFT_X_KEY)), AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(SHIFT_Z_KEY)), 0.25, noiseParameters.getOrThrow(VEGETATION)),  // vegetation
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(CONTINENTS_FUNCTION_KEY)),  // continents
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(EROSION_FUNCTION_KEY)),  // erosion
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(DEPTH_FUNCTION_KEY)),  // depth
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(RIDGES_FUNCTION_KEY)),  // ridges
                initialDensity(functions),  // initialDensityWithoutJaggedness
                finalDensity(functions, noiseParameters),  // finalDensity
                zero(),  // veinToggle
                zero(),  // veinRidged
                zero()); // veinGap
    }

    static DensityFunction initialDensity(RegistryEntryLookup<DensityFunction> functions) {
        return AylythDensityFunctionBootstrap.slide(
                add(
                        constant(-0.703125),
                        mul(
                                constant(4),
                                mul(
                                        AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(DEPTH_FUNCTION_KEY)),
                                        cache2d(AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(FACTOR_FUNCTION_KEY)))
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

    static DensityFunction finalDensity(RegistryEntryLookup<DensityFunction> functions, RegistryEntryLookup<DoublePerlinNoiseSampler.NoiseParameters> noiseParameters) {
        return min(
                AylythDensityFunctionBootstrap.postProcess(
                        AylythDensityFunctionBootstrap.slide(
                                rangeChoice(
                                        AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(SLOPED_CHEESE_FUNCTION_KEY)),
                                        -1000000.0,
                                        1.5625,
                                        min(
                                                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(SLOPED_CHEESE_FUNCTION_KEY)),
                                                mul(
                                                        constant(5.0),
                                                        AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(CAVES_ENTRANCES_FUNCTION_KEY))
                                                )
                                        ),
                                        max(
                                                min(
                                                        min(
                                                                add(
                                                                        mul(
                                                                                constant(4.0),
                                                                                noise(noiseParameters.getOrThrow(CAVE_LAYER), 1.0, 8.0).square()
                                                                        ),
                                                                        add(
                                                                                add(
                                                                                        constant(0.27),
                                                                                        noise(noiseParameters.getOrThrow(CAVE_CHEESE), 2.0, 0.95)
                                                                                ).clamp(-1.0, 1.0),
                                                                                add(
                                                                                        constant(1.5),
                                                                                        mul(
                                                                                                constant(-0.64),
                                                                                                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(SLOPED_CHEESE_FUNCTION_KEY))
                                                                                        )
                                                                                ).clamp(0.0, 0.5)
                                                                        )
                                                                ),
                                                                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(CAVES_ENTRANCES_FUNCTION_KEY))
                                                        ),
                                                        add(
                                                                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(CAVES_SPAGHETTI_2D_FUNCTION_KEY)),
                                                                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(CAVES_SPAGHETTI_ROUGHNESS_FUNCTION_KEY))
                                                        )
                                                ),
                                                rangeChoice(
                                                        AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(CAVES_PILLARS_FUNCTION_KEY)),
                                                        -1000000.0,
                                                        0.03,
                                                        constant(-1000000.0),
                                                        AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(CAVES_PILLARS_FUNCTION_KEY))
                                                )
                                        )
                                ),
                                -64,
                                272,
                                80, 64, -0.078125,
                                0, 24, 0.1171875
                        )
                ),
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(CAVES_NOODLE_FUNCTION_KEY))
        );
    }

    static List<NoiseHypercube> spawnTargets() {
        return List.of();
    }
}