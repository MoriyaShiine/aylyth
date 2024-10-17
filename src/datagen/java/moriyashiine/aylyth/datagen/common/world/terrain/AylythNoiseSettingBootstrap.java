package moriyashiine.aylyth.datagen.common.world.terrain;

import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.NoiseHypercube;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.noise.NoiseRouter;

import java.util.List;

import static moriyashiine.aylyth.common.data.world.AylythDimensionData.*;
import static moriyashiine.aylyth.common.data.world.terrain.AylythDensityFunctions.*;
import static moriyashiine.aylyth.datagen.common.world.terrain.AylythDensityFunctionBootstrap.wrap;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public final class AylythNoiseSettingBootstrap {
    private AylythNoiseSettingBootstrap() {}

    public static void bootstrap(Registerable<ChunkGeneratorSettings> context) {
        var densityFuns = context.getRegistryLookup(RegistryKeys.DENSITY_FUNCTION);

        var shapeConfig = new GenerationShapeConfig(MIN_HEIGHT, SHAPE_MAX_HEIGHT, 1, 1);
        var noiseRouter = new NoiseRouter(
                zero(),
                wrap(densityFuns.getOrThrow(FLOODEDNESS_FUNCTION_KEY)),
                wrap(densityFuns.getOrThrow(FLUID_SPREAD_FUNCTION_KEY)),
                zero(),
                wrap(densityFuns.getOrThrow(TEMPERATURE_FUNCTION_KEY)),
                wrap(densityFuns.getOrThrow(VEGETATION_FUNCTION_KEY)),
                wrap(densityFuns.getOrThrow(CONTINENTS_FUNCTION_KEY)),
                wrap(densityFuns.getOrThrow(EROSION_FUNCTION_KEY)),
                wrap(densityFuns.getOrThrow(DEPTH_FUNCTION_KEY)),
                wrap(densityFuns.getOrThrow(RIDGES_FUNCTION_KEY)),
                wrap(densityFuns.getOrThrow(INITIAL_DENSITY_WITHOUT_JAGGEDNESS_FUNCTION_KEY)),
                wrap(densityFuns.getOrThrow(FINAL_DENSITY_FUNCTION_KEY)),
                zero(),
                zero(),
                zero()
        );
        var surfaceMaterialRules = AylythMaterialRules.create();
        var spawnTarget = List.<NoiseHypercube>of();
        var seaLevel = 47;

        context.register(AylythDimensionData.CHUNK_GEN_SETTINGS, new ChunkGeneratorSettings(shapeConfig, Blocks.DEEPSLATE.getDefaultState(), Blocks.WATER.getDefaultState(), noiseRouter, surfaceMaterialRules, spawnTarget, seaLevel, false, true, false, false));
    }
}