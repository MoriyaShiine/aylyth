package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

public class ModBiomes {
	public static final Identifier CLEARING_ID = new Identifier(Aylyth.MOD_ID, "clearing");
	public static final Identifier OVERGROWN_CLEARING_ID = new Identifier(Aylyth.MOD_ID, "overgrown_clearing");
	public static final Identifier OVERGROWN_CONIFEROUS_CLEARING_ID = new Identifier(Aylyth.MOD_ID, "overgrown_coniferous_clearing");
	public static final Identifier FOREST_ID = new Identifier(Aylyth.MOD_ID, "forest");
	public static final Identifier DEEP_FOREST_ID = new Identifier(Aylyth.MOD_ID, "deep_forest");
	public static final Identifier CONIFEROUS_FOREST_ID = new Identifier(Aylyth.MOD_ID, "coniferous_forest");
	public static final Identifier DEEP_CONIFEROUS_FOREST_ID = new Identifier(Aylyth.MOD_ID, "deep_coniferous_forest");
	public static final Identifier BOWELS_ID = new Identifier(Aylyth.MOD_ID, "bowels");
	public static final Biome CLEARING = createTest(0x69db27);
	public static final Biome OVERGROWN_CLEARING = createTest(0xc8ce16);
	public static final Biome OVERGROWN_CONIFEROUS_CLEARING = createTest(0x4f510d);
	public static final Biome FOREST = createTest(0xce8516); //createForest(false, new SpawnSettings.Builder());
	public static final Biome DEEP_FOREST = createTest(0xce3516);
	public static final Biome CONIFEROUS_FOREST = createTest(0x5e3e0e);
	public static final Biome DEEP_CONIFEROUS_FOREST = createTest(0x661b0c);
	public static final Biome BOWELS = createTest(0xAAAAAA);
	
	public static void init() {
		Registry.register(BuiltinRegistries.BIOME, CLEARING_ID, CLEARING);
		Registry.register(BuiltinRegistries.BIOME, OVERGROWN_CLEARING_ID, OVERGROWN_CLEARING);
		Registry.register(BuiltinRegistries.BIOME, OVERGROWN_CONIFEROUS_CLEARING_ID, OVERGROWN_CONIFEROUS_CLEARING);
		Registry.register(BuiltinRegistries.BIOME, FOREST_ID, FOREST);
		Registry.register(BuiltinRegistries.BIOME, DEEP_FOREST_ID, DEEP_FOREST);
		Registry.register(BuiltinRegistries.BIOME, CONIFEROUS_FOREST_ID, CONIFEROUS_FOREST);
		Registry.register(BuiltinRegistries.BIOME, DEEP_CONIFEROUS_FOREST_ID, DEEP_CONIFEROUS_FOREST);
		Registry.register(BuiltinRegistries.BIOME, BOWELS_ID, BOWELS);
	}
	
	private static Biome createTest(int grassColor) {
		GenerationSettings.Builder builder = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		return (new Biome.Builder()).precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST).depth(0.5F).scale(0.5F).temperature(0.7F).downfall(0.8F).effects((new BiomeEffects.Builder()).foliageColor(grassColor).grassColor(grassColor).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(0x000000).moodSound(BiomeMoodSound.CAVE).build()).spawnSettings(new SpawnSettings.Builder().build()).generationSettings(builder.build()).build();
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
