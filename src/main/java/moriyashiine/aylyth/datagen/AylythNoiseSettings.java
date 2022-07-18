package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.noise.NoiseRouter;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

import java.util.List;

import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public class AylythNoiseSettings {

    public static void init() {}

    public static final ChunkGeneratorSettings SETTINGS = Registry.register(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, new Identifier(Aylyth.MOD_ID, "aylyth_settings"), createSettings());

    static ChunkGeneratorSettings createSettings() {
        var seaLevel = 0;
        var disableMobGen = false;
        var aquifers = false;
        var oreVeins = true;
        var legacyRandom = false;
        return new ChunkGeneratorSettings(shapeConfig(), defaultState(Blocks.STONE), defaultState(Blocks.WATER), testRouter(), materialRules(), spawnTargets(), seaLevel, disableMobGen, aquifers, oreVeins, legacyRandom);
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
                zero(),  // temperature
                zero(),  // vegetation
                method_40502(noiseEntry(NoiseParametersKeys.CONTINENTALNESS), 1, 0.5),  // continents
                noise(noiseEntry(NoiseParametersKeys.EROSION)),  // erosion
                BuiltinRegistries.DENSITY_FUNCTION.get(DensityFunctions.DEPTH_OVERWORLD),  // depth
                zero(),  // ridges
                initialDensity(),  // initialDensityWithoutJaggedness
                zero(),  // finalDensity
                zero(),  // veinToggle
                zero(),  // veinRidged
                zero()); // veinGap
    }

    static DensityFunction initialDensity() {
        return cache2d(new RegistryEntryHolder(BuiltinRegistries.DENSITY_FUNCTION.getEntry(DensityFunctions.FACTOR_OVERWORLD).get()));
    }

    static DensityFunction getFunctionRaw(String id) {
        return BuiltinRegistries.DENSITY_FUNCTION.get(RegistryKey.of(Registry.DENSITY_FUNCTION_KEY, new Identifier(id)));
    }

    static DensityFunction slide(DensityFunction densityFunction, int i, int j, int k, int l, double d, int m, int n, double e) {
        DensityFunction densityFunction2 = densityFunction;
        DensityFunction densityFunction3 = yClampedGradient(i + j - k, i + j - l, 1.0, 0.0);
        densityFunction2 = method_40488(densityFunction3, constant(d), densityFunction2);
        DensityFunction densityFunction4 = yClampedGradient(i + m, i + n, 0.0, 1.0);
        densityFunction2 = method_40488(densityFunction4, constant(e), densityFunction2);
        return densityFunction2;
    }

    static DensityFunction postProcess(DensityFunction densityFunction) {
        DensityFunction densityFunction2 = blendDensity(densityFunction);
        return mul(interpolated(densityFunction2), constant(0.64)).squeeze();
    }

    static DensityFunction finalDensity() {
        return add(constant(0.1), interpolated(blendDensity(add(constant(0.05), mul(yClampedGradient(-64, 69, 0.1, 1), mul(yClampedGradient(60, 100, -0.1, -1), noise(noiseEntry(NoiseParametersKeys.SURFACE))))))).squeeze());
    }

    static RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> noiseEntry(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
        return BuiltinRegistries.NOISE_PARAMETERS.getEntry(key).get();
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
