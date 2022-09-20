package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.densityfunction.DensityFunction;
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
        var seaLevel = -64;
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
        var minY = -64;
        var height = 304;
        var horizontal = 1;
        var vertical = 1;
        return new GenerationShapeConfig(minY, height, horizontal, vertical);
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
                                rangeChoice(
                                        holderFunction(SLOPED_CHEESE_FUNCTION),
                                        -1000000.0,
                                        1.5625,
                                        min(
                                                holderFunction(SLOPED_CHEESE_FUNCTION),
                                                mul(
                                                        constant(5.0),
                                                        holderFunction(CAVES_ENTRANCES_FUNCTION)
                                                )
                                        ),
                                        max(
                                                min(
                                                        min(
                                                                add(
                                                                        mul(
                                                                                constant(4.0),
                                                                                noise(CAVE_LAYER, 1.0, 8.0).square()
                                                                        ),
                                                                        add(
                                                                                add(
                                                                                        constant(0.27),
                                                                                        noise(CAVE_CHEESE, 2.0, 0.95)
                                                                                ).clamp(-1.0, 1.0),
                                                                                add(
                                                                                        constant(1.5),
                                                                                        mul(
                                                                                                constant(-0.64),
                                                                                                holderFunction(SLOPED_CHEESE_FUNCTION)
                                                                                        )
                                                                                ).clamp(0.0, 0.5)
                                                                        )
                                                                ),
                                                                holderFunction(CAVES_ENTRANCES_FUNCTION)
                                                        ),
                                                        add(
                                                                holderFunction(CAVES_SPAGHETTI_2D_FUNCTION),
                                                                holderFunction(CAVES_SPAGHETTI_ROUGHNESS_FUNCTION)
                                                        )
                                                ),
                                                rangeChoice(
                                                        holderFunction(CAVES_PILLARS_FUNCTION),
                                                        -1000000.0,
                                                        0.03,
                                                        constant(-1000000.0),
                                                        holderFunction(CAVES_PILLARS_FUNCTION)
                                                )
                                        )
                                ),
                                -64,
                                272,
                                80, 64, -0.078125,
                                0, 24, 0.1171875
                        )
                ),
                holderFunction(CAVES_NOODLE_FUNCTION)
        );
    }

    static MaterialRules.MaterialRule materialRules() {
        var dirt = MaterialRules.block(defaultState(Blocks.DIRT));
        var grass = MaterialRules.block(defaultState(Blocks.GRASS_BLOCK));
        var onReplaceWaterWithGrass = MaterialRules.condition(MaterialRules.water(-1, 0), grass);
        var onFloor = MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(onReplaceWaterWithGrass, dirt));
        var onUnderFloor = MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, dirt);
        var onSurface = MaterialRules.condition(MaterialRules.stoneDepth(0, false, 0, VerticalSurfaceType.FLOOR), MaterialRules.sequence(onFloor, onUnderFloor));
        var aboveBasicSurface = MaterialRules.condition(MaterialRules.surface(), onSurface);
        var bedrock = MaterialRules.condition(MaterialRules.verticalGradient("aylyth:bedrock_layer", YOffset.BOTTOM, YOffset.aboveBottom(5)), MaterialRules.block(defaultState(Blocks.BEDROCK)));
        var uplands = MaterialRules.condition(MaterialRules.biome(ModBiomes.UPLANDS_ID), MaterialRules.condition(MaterialRules.surface(), MaterialRules.condition(MaterialRules.stoneDepth(0, false, 0, VerticalSurfaceType.FLOOR), uplandsTerracotta())));
        return MaterialRules.sequence(bedrock, uplands, aboveBasicSurface);
    }

    static MaterialRules.MaterialRule uplandsTerracotta() {
        return MaterialRules.sequence(AylythMaterialRules.surfaceNoiseBlock(Blocks.DEEPSLATE, -2, -0.6), AylythMaterialRules.surfaceNoiseBlock(Blocks.BROWN_TERRACOTTA, -0.6, -0.15), AylythMaterialRules.surfaceNoiseBlock(Blocks.YELLOW_TERRACOTTA, -0.15, 0), AylythMaterialRules.surfaceNoiseBlock(Blocks.ORANGE_TERRACOTTA, 0, 0.3), AylythMaterialRules.surfaceNoiseBlock(Blocks.RED_TERRACOTTA, 0.3, 0.6), AylythMaterialRules.surfaceNoiseBlock(Blocks.TERRACOTTA, 0.6, 0.8), AylythMaterialRules.surfaceNoiseBlock(Blocks.DEEPSLATE, 0.8, 2.0));
    }

    static MaterialRules.MaterialRule emptyRules() {
        return MaterialRules.block(Blocks.STONE.getDefaultState());
    }

    static List<MultiNoiseUtil.NoiseHypercube> spawnTargets() {
        return List.of();
    }
}