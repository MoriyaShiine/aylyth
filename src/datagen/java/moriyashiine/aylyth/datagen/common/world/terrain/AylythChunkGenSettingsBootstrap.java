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

public final class AylythChunkGenSettingsBootstrap {
    private AylythChunkGenSettingsBootstrap() {}

    public static void bootstrap(Registerable<ChunkGeneratorSettings> context) {
        var densityFuns = context.getRegistryLookup(RegistryKeys.DENSITY_FUNCTION);

        var shapeConfig = new GenerationShapeConfig(MIN_HEIGHT, SHAPE_MAX_HEIGHT, 1, 1);
        var noiseRouter = new NoiseRouter(
                zero(),
                wrap(densityFuns.getOrThrow(AQUIFER_FLUID_FLOODEDNESS)),
                wrap(densityFuns.getOrThrow(AQUIFER_FLUID_SPREAD)),
                zero(),
                wrap(densityFuns.getOrThrow(TEMPERATURE)),
                wrap(densityFuns.getOrThrow(VEGETATION)),
                wrap(densityFuns.getOrThrow(CONTINENTS)),
                wrap(densityFuns.getOrThrow(EROSION)),
                wrap(densityFuns.getOrThrow(DEPTH)),
                wrap(densityFuns.getOrThrow(RIDGES)),
                wrap(densityFuns.getOrThrow(DENSITY_INITIAL_WITHOUT_JAGGEDNESS)),
                wrap(densityFuns.getOrThrow(DENSITY_FINAL)),
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