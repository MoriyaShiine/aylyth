package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
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
        var height = 272;
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
                                                                                        noise(CAVE_CHEESE, 1.0, 0.6666666666666666)
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

    static MaterialRules.MaterialRule emptyRules() {
        return MaterialRules.block(Blocks.STONE.getDefaultState());
    }

    static List<MultiNoiseUtil.NoiseHypercube> spawnTargets() {
        return List.of();
    }
}
