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
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.noise.NoiseRouter;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

import java.util.List;

import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;
import static net.minecraft.world.gen.noise.NoiseParametersKeys.*;

public class AylythNoiseSettings {

    public static void init() {}

    public static final ChunkGeneratorSettings SETTINGS = Registry.register(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, new Identifier(Aylyth.MOD_ID, "aylyth_settings"), createSettings());

    static ChunkGeneratorSettings createSettings() {
        var seaLevel = 0;
        var disableMobGen = false;
        var aquifers = false;
        var oreVeins = true;
        var legacyRandom = false;
        return new ChunkGeneratorSettings(shapeConfig(), defaultState(Blocks.STONE), defaultState(Blocks.WATER), noiseRouter(), emptyRules(), spawnTargets(), seaLevel, disableMobGen, aquifers, oreVeins, legacyRandom);
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
                noise(NoiseParametersKeys.TEMPERATURE),  // temperature
                noise(NoiseParametersKeys.VEGETATION),  // vegetation
                noise(NoiseParametersKeys.CONTINENTALNESS),  // continents
                noise(NoiseParametersKeys.EROSION),  // erosion
                BuiltinRegistries.DENSITY_FUNCTION.get(DensityFunctions.DEPTH_OVERWORLD),  // depth
                zero(),  // ridges
                initialDensity(),  // initialDensityWithoutJaggedness
                finalDensity(),  // finalDensity
                zero(),  // veinToggle
                zero(),  // veinRidged
                zero()); // veinGap
    }

    static DensityFunction initialDensity() {
        return cache2d(new RegistryEntryHolder(BuiltinRegistries.DENSITY_FUNCTION.getEntry(DensityFunctions.FACTOR_OVERWORLD).get()));
    }

    static DensityFunction finalDensity() {
        return postProcess(
                add(
                        yClampedGradient(-64, 272, 1.0, -1.0),
                        add(
                                smallVariedFlatterLand(),
                                plateauedHills()
                        )
                )
        );
    }

    // This is the regular base terrain. Should be flatter, with gradual variations in the land
    static DensityFunction smallVariedFlatterLand() {
        return scaledNoise(GRAVEL, 1, 0.5);
    }

    // This is for the uplands biome. These should be somewhat rare. Should be a large area created with a combination of mountainous terrain and plateaus.
    static DensityFunction plateauedHills() {
        return constant(0);
//        return add(
//                noise(CONTINENTALNESS),
//                yClampedGradient(84, 150, 1, 0)
//        );
    }

    static DensityFunction getFunctionRaw(String id) {
        return BuiltinRegistries.DENSITY_FUNCTION.get(RegistryKey.of(Registry.DENSITY_FUNCTION_KEY, new Identifier(id)));
    }

    static DensityFunction slide(DensityFunction end, int minWorldHeight, int maxWorldHeight, int fromTopSlide, int toTopSlide, double topStart, int fromBottomSlide, int toBottomSlide, double bottomStart) {
        DensityFunction densityFunction3 = gradient(minWorldHeight + maxWorldHeight - fromTopSlide, minWorldHeight + maxWorldHeight - toTopSlide, 1.0, 0.0);
        DensityFunction densityFunction2 = lerp(densityFunction3, topStart, end);
        DensityFunction densityFunction4 = gradient(minWorldHeight + fromBottomSlide, minWorldHeight + toBottomSlide, 0.0, 1.0);
        densityFunction2 = lerp(densityFunction4, bottomStart, densityFunction2);
        return densityFunction2;
    }

    static DensityFunction lerp(DensityFunction delta, double start, DensityFunction end) {
        return method_42359(delta, start, end);
    }

    static DensityFunction gradient(int fromY, int toY, double fromValue, double toValue) {
        return yClampedGradient(fromY, toY, fromValue, toValue);
    }

    static DensityFunction condition(DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange) {
        return rangeChoice(input, minInclusive, maxExclusive, whenInRange, whenOutOfRange);
    }

    static DensityFunction postProcess(DensityFunction densityFunction) {
        DensityFunction densityFunction2 = blendDensity(densityFunction);
        return mul(interpolated(densityFunction2), constant(0.64)).squeeze();
    }

    static RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> noiseEntry(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
        return BuiltinRegistries.NOISE_PARAMETERS.getEntry(key).get();
    }

    static DensityFunction scaledNoise(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key, double xzScale, double yScale) {
        return method_40502(noiseEntry(key), xzScale, yScale);
    }

    static DensityFunction noise(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> key) {
        return DensityFunctionTypes.noise(noiseEntry(key));
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
