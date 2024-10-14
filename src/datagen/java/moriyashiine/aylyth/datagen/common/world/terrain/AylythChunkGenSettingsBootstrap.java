package moriyashiine.aylyth.datagen.common.world.terrain;

import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.data.world.terrain.AylythDensityFunctions;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.NoiseHypercube;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseRouter;

import java.util.List;

import static moriyashiine.aylyth.common.data.world.terrain.AylythDensityFunctions.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public final class AylythChunkGenSettingsBootstrap {
    private AylythChunkGenSettingsBootstrap() {}

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
                AylythDensityFunctionBootstrap.wrap(densityFunctions.getOrThrow(MODIFIED_CONTINENTS)),
                AylythDensityFunctionBootstrap.wrap(densityFunctions.getOrThrow(AylythDensityFunctions.MODIFIED_EROSION)),
                AylythDensityFunctionBootstrap.wrap(densityFunctions.getOrThrow(OVERRIDDEN_DEPTH)),
                vanillaNoiseRouter.ridges(),
                AylythDensityFunctionBootstrap.wrap(densityFunctions.getOrThrow(OVERRIDDEN_INITIAL_DENSITY_WITHOUT_JAGGEDNESS)),
                AylythDensityFunctionBootstrap.wrap(densityFunctions.getOrThrow(OVERRIDDEN_FINALE_DENSITY)),
                zero(),
                zero(),
                zero()
        );

        var materialRules = AylythMaterialRuleBootstrap.materialRules();
        var spawnTarget = List.<NoiseHypercube>of();
        var seaLevel = vanillaChunkGenSettings.seaLevel();

        context.register(AylythDimensionData.CHUNK_GEN_SETTINGS, new ChunkGeneratorSettings(shape, Blocks.DEEPSLATE.getDefaultState(), Blocks.WATER.getDefaultState(), noiseRouter, materialRules, spawnTarget, seaLevel, false, true, false, false));
    }
}