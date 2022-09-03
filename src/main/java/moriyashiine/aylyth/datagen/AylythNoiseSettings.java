package moriyashiine.aylyth.datagen;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Spline;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseRouter;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

import java.util.List;

import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public class AylythNoiseSettings {

    public static void init() {}

    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CONTINENTS_KEY = RegistryKey.of(Registry.NOISE_KEY, new Identifier(Aylyth.MOD_ID, "continents"));
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> BASE_LAYER_KEY = RegistryKey.of(Registry.NOISE_KEY, new Identifier(Aylyth.MOD_ID, "base_layer"));
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> CAVE_CHEESE_KEY = RegistryKey.of(Registry.NOISE_KEY, new Identifier(Aylyth.MOD_ID, "cave_cheese"));
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> TEMPERATURE_KEY = RegistryKey.of(Registry.NOISE_KEY, new Identifier(Aylyth.MOD_ID, "temperature"));
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> VEGETATION_KEY = RegistryKey.of(Registry.NOISE_KEY, new Identifier(Aylyth.MOD_ID, "vegetation"));
    public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> EROSION_KEY = RegistryKey.of(Registry.NOISE_KEY, new Identifier(Aylyth.MOD_ID, "erosion"));

    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> CONTINENTS = BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, CONTINENTS_KEY, new DoublePerlinNoiseSampler.NoiseParameters(-9, DoubleList.of(1.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0)));
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> BASE_LAYER = BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, BASE_LAYER_KEY, new DoublePerlinNoiseSampler.NoiseParameters(-8, DoubleList.of(1.0, 1.0, 1.0, 1.0)));
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> CAVE_CHEESE = BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, CAVE_CHEESE_KEY, new DoublePerlinNoiseSampler.NoiseParameters(-8, DoubleList.of(0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 0.0, 2.0, 0.0)));
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> TEMPERATURE = BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, TEMPERATURE_KEY, new DoublePerlinNoiseSampler.NoiseParameters(-10, DoubleList.of(1.5, 0.0, 1.0, 0.0, 0.0, 0.0)));
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> VEGETATION = BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, VEGETATION_KEY, new DoublePerlinNoiseSampler.NoiseParameters(-8, DoubleList.of(1.0, 1.0, 0.0, 0.0, 0.0, 0.0)));
    public static final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> EROSION = BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, EROSION_KEY, new DoublePerlinNoiseSampler.NoiseParameters(-9, DoubleList.of(1.0, 1.0, 0.0, 0.0, 1.0, 1.0)));

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
        var minY = -64;
        var height = 336;
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
                scaledNoise(TEMPERATURE, 1.0, 1.0),  // temperature
                scaledNoise(VEGETATION, 1.0, 1.0),  // vegetation
                scaledNoise(CONTINENTS, 1, 1),  // continents
                scaledNoise(EROSION, 1.0, 1.0),  // erosion
                depth(),  // depth
                ridges(),  // ridges
                initialDensity(),  // initialDensityWithoutJaggedness
                finalDensity(),  // finalDensity
                zero(),  // veinToggle
                zero(),  // veinRidged
                zero()); // veinGap
    }

    static DensityFunction depth() {
        return zero();
    }

    static DensityFunction ridges() {
        return zero();
    }

    static DensityFunction initialDensity() {
        return zero();
    }

    static DensityFunction finalDensity() {
        return zero();
    }

    static DensityFunction scaledNoise(RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> noiseEntry, double xzScale, double yScale) {
        return method_40502(noiseEntry, xzScale, yScale);
    }

    static MaterialRules.MaterialRule materialRules() {
        var dirt = MaterialRules.block(defaultState(Blocks.DIRT));
        var grass = MaterialRules.block(defaultState(Blocks.GRASS_BLOCK));
        var onReplaceWaterWithGrass = MaterialRules.condition(MaterialRules.water(-1, 0), grass);
        var onFloor = MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(onReplaceWaterWithGrass, dirt));
        var onUnderFloor = MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, dirt);
        var onSurface = MaterialRules.condition(MaterialRules.stoneDepth(0, false, 0, VerticalSurfaceType.FLOOR), MaterialRules.sequence(onFloor, onUnderFloor));
//        var aboveBasicSurface = MaterialRules.condition(MaterialRules.surface(), onSurface);
        var bedrock = MaterialRules.condition(MaterialRules.verticalGradient("aylyth:bedrock_layer", YOffset.BOTTOM, YOffset.aboveBottom(5)), MaterialRules.block(defaultState(Blocks.BEDROCK)));
        return MaterialRules.sequence(bedrock, onSurface);
    }

    static MaterialRules.MaterialRule emptyRules() {
        return MaterialRules.block(Blocks.STONE.getDefaultState());
    }

    static List<MultiNoiseUtil.NoiseHypercube> spawnTargets() {
        return List.of();
    }
}
