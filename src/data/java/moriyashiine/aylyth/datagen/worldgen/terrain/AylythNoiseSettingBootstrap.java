package moriyashiine.aylyth.datagen.worldgen.terrain;

import moriyashiine.aylyth.common.registry.key.ModDimensionKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.noise.NoiseRouter;

import java.util.List;

import static moriyashiine.aylyth.common.registry.key.ModDensityFunctionKeys.*;
import static moriyashiine.aylyth.common.registry.key.ModNoiseKeys.*;
import static moriyashiine.aylyth.datagen.worldgen.terrain.AylythDensityFunctionBootstrap.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public class AylythNoiseSettingBootstrap {

    public static void bootstrap(Registerable<ChunkGeneratorSettings> context) {
        context.register(ModDimensionKeys.AYLYTH_CHUNK_GEN_SETTINGS, createSettings(context.getRegistryLookup(RegistryKeys.DENSITY_FUNCTION), context.getRegistryLookup(RegistryKeys.NOISE_PARAMETERS)));
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
                shiftedNoise(holderFunction(functions.getOrThrow(SHIFT_X_KEY)), holderFunction(functions.getOrThrow(SHIFT_Z_KEY)), 0.25, noiseParameters.getOrThrow(TEMPERATURE)),  // temperature
                shiftedNoise(holderFunction(functions.getOrThrow(SHIFT_X_KEY)), holderFunction(functions.getOrThrow(SHIFT_Z_KEY)), 0.25, noiseParameters.getOrThrow(VEGETATION)),  // vegetation
                holderFunction(functions.getOrThrow(CONTINENTS_FUNCTION_KEY)),  // continents
                holderFunction(functions.getOrThrow(EROSION_FUNCTION_KEY)),  // erosion
                holderFunction(functions.getOrThrow(DEPTH_FUNCTION_KEY)),  // depth
                holderFunction(functions.getOrThrow(RIDGES_FUNCTION_KEY)),  // ridges
                initialDensity(functions),  // initialDensityWithoutJaggedness
                finalDensity(functions, noiseParameters),  // finalDensity
                zero(),  // veinToggle
                zero(),  // veinRidged
                zero()); // veinGap
    }

    static DensityFunction initialDensity(RegistryEntryLookup<DensityFunction> functions) {
        return slide(
                add(
                        constant(-0.703125),
                        mul(
                                constant(4),
                                mul(
                                        holderFunction(functions.getOrThrow(DEPTH_FUNCTION_KEY)),
                                        cache2d(holderFunction(functions.getOrThrow(FACTOR_FUNCTION_KEY)))
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
                postProcess(
                        slide(
                                rangeChoice(
                                        holderFunction(functions.getOrThrow(SLOPED_CHEESE_FUNCTION_KEY)),
                                        -1000000.0,
                                        1.5625,
                                        min(
                                                holderFunction(functions.getOrThrow(SLOPED_CHEESE_FUNCTION_KEY)),
                                                mul(
                                                        constant(5.0),
                                                        holderFunction(functions.getOrThrow(CAVES_ENTRANCES_FUNCTION_KEY))
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
                                                                                                holderFunction(functions.getOrThrow(SLOPED_CHEESE_FUNCTION_KEY))
                                                                                        )
                                                                                ).clamp(0.0, 0.5)
                                                                        )
                                                                ),
                                                                holderFunction(functions.getOrThrow(CAVES_ENTRANCES_FUNCTION_KEY))
                                                        ),
                                                        add(
                                                                holderFunction(functions.getOrThrow(CAVES_SPAGHETTI_2D_FUNCTION_KEY)),
                                                                holderFunction(functions.getOrThrow(CAVES_SPAGHETTI_ROUGHNESS_FUNCTION_KEY))
                                                        )
                                                ),
                                                rangeChoice(
                                                        holderFunction(functions.getOrThrow(CAVES_PILLARS_FUNCTION_KEY)),
                                                        -1000000.0,
                                                        0.03,
                                                        constant(-1000000.0),
                                                        holderFunction(functions.getOrThrow(CAVES_PILLARS_FUNCTION_KEY))
                                                )
                                        )
                                ),
                                -64,
                                272,
                                80, 64, -0.078125,
                                0, 24, 0.1171875
                        )
                ),
                holderFunction(functions.getOrThrow(CAVES_NOODLE_FUNCTION_KEY))
        );
    }

    static List<MultiNoiseUtil.NoiseHypercube> spawnTargets() {
        return List.of();
    }
}