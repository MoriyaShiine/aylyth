package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.data.report.BiomeListProvider;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

public class ModBiomes {
	public static final Identifier AYLYTHIAN_FOREST_ID = new Identifier(Aylyth.MOD_ID, "aylythian_forest");
	public static final Identifier DEEP_AYLYTHIAN_FOREST_ID = new Identifier(Aylyth.MOD_ID, "deep_aylythian_forest");
	public static final Biome AYLYTHIAN_FOREST = createForest(false, new SpawnSettings.Builder());
	public static final Biome DEEP_AYLYTHIAN_FOREST = createForest(true, new SpawnSettings.Builder());
	
	public static void init() {
		Registry.register(BuiltinRegistries.BIOME, AYLYTHIAN_FOREST_ID, AYLYTHIAN_FOREST);
		Registry.register(BuiltinRegistries.BIOME, DEEP_AYLYTHIAN_FOREST_ID, DEEP_AYLYTHIAN_FOREST);
	}
	
	private static Biome createForest(boolean deep, SpawnSettings.Builder spawnSettings) {
		GenerationSettings.Builder builder = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		DefaultBiomeFeatures.addLandCarvers(builder);
		DefaultBiomeFeatures.addDefaultLakes(builder);
		DefaultBiomeFeatures.addForestFlowers(builder);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ModWorldGenerators.DEEP_ROOF_TREES : ModWorldGenerators.ROOF_TREES);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.FOREST_TREES);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_PLAIN);
		DefaultBiomeFeatures.addLargeFerns(builder);
		DefaultBiomeFeatures.addPlainsTallGrass(builder);
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addSprings(builder);
		DefaultBiomeFeatures.addFrozenTopLayer(builder);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST).depth(deep ? 0.4F : 0.3F).scale(deep ? 0.3F : 0.2F).temperature(0.7F).downfall(0.8F).effects((new BiomeEffects.Builder()).foliageColor(deep ? 0x9E811A : 0x627F38).grassColor(0xB5883B).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(0x000000).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(spawnSettings.build()).generationSettings(builder.build()).build();
	}
}
