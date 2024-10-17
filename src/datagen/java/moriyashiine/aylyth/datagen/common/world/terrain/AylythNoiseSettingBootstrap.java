package moriyashiine.aylyth.datagen.common.world.terrain;

import moriyashiine.aylyth.common.data.world.AylythDimensionData;
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

import static moriyashiine.aylyth.common.data.world.terrain.AylythDensityFunctions.*;
import static moriyashiine.aylyth.common.data.world.terrain.AylythNoises.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public final class AylythNoiseSettingBootstrap {
    private AylythNoiseSettingBootstrap() {}

    public static void bootstrap(Registerable<ChunkGeneratorSettings> context) {
        context.register(AylythDimensionData.CHUNK_GEN_SETTINGS, createSettings(context.getRegistryLookup(RegistryKeys.DENSITY_FUNCTION), context.getRegistryLookup(RegistryKeys.NOISE_PARAMETERS)));
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
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(FLOODEDNESS_FUNCTION_KEY)),
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(FLUID_SPREAD_FUNCTION_KEY)),
                zero(),  // lavaNoise
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(TEMPERATURE_FUNCTION_KEY)),
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(VEGETATION_FUNCTION_KEY)),
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(CONTINENTS_FUNCTION_KEY)),
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(EROSION_FUNCTION_KEY)),
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(DEPTH_FUNCTION_KEY)),
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(RIDGES_FUNCTION_KEY)),
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(INITIAL_DENSITY_WITHOUT_JAGGEDNESS_FUNCTION_KEY)),
                AylythDensityFunctionBootstrap.holderFunction(functions.getOrThrow(FINAL_DENSITY_FUNCTION_KEY)),
                zero(),  // veinToggle
                zero(),  // veinRidged
                zero()); // veinGap
    }

    static List<MultiNoiseUtil.NoiseHypercube> spawnTargets() {
        return List.of();
    }
}