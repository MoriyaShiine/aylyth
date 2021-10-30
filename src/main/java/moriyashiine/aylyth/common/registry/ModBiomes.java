package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

public class ModBiomes {
	public static final SpawnSettings.Builder DEEP_FOREST_MOBS = new SpawnSettings.Builder().spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntityTypes.AYLYTHIAN, 25, 1, 3)).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntityTypes.ELDER_AYLYTHIAN, 2, 1, 1)).spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(ModEntityTypes.PILOT_LIGHT, 10, 1, 1));
	public static final SpawnSettings.Builder FOREST_MOBS = new SpawnSettings.Builder().spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntityTypes.AYLYTHIAN, 20, 1, 2)).spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(ModEntityTypes.PILOT_LIGHT, 5, 1, 1)).creatureSpawnProbability(0.5F);
	public static final BiomeAdditionsSound FOREST_AMBIANCE = new BiomeAdditionsSound(ModSoundEvents.AMBIENT_FOREST_ADDITIONS, 0.005);
	public static final BiomeAdditionsSound OVERGROWN_CLEARING_AMBIANCE = new BiomeAdditionsSound(ModSoundEvents.AMBIENT_FOREST_ADDITIONS, 0.001);
	public static final Identifier CLEARING_ID = new Identifier(Aylyth.MOD_ID, "clearing");
	public static final Identifier OVERGROWN_CLEARING_ID = new Identifier(Aylyth.MOD_ID, "overgrown_clearing");
	public static final Identifier FOREST_ID = new Identifier(Aylyth.MOD_ID, "forest");
	public static final Identifier DEEP_FOREST_ID = new Identifier(Aylyth.MOD_ID, "deep_forest");
	public static final Identifier CONIFEROUS_FOREST_ID = new Identifier(Aylyth.MOD_ID, "coniferous_forest");
	public static final Identifier DEEP_CONIFEROUS_FOREST_ID = new Identifier(Aylyth.MOD_ID, "deep_coniferous_forest");
	public static final Biome CLEARING = createClearing(false, new SpawnSettings.Builder());
	public static final Biome OVERGROWN_CLEARING = createClearing(true, new SpawnSettings.Builder().spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(ModEntityTypes.PILOT_LIGHT, 1, 1, 1)).creatureSpawnProbability(0.1F));
	public static final Biome FOREST = createForest(false, FOREST_MOBS);
	public static final Biome DEEP_FOREST = createForest(true, DEEP_FOREST_MOBS);
	public static final Biome CONIFEROUS_FOREST = createConiferousForest(false, FOREST_MOBS);
	public static final Biome DEEP_CONIFEROUS_FOREST = createConiferousForest(true, DEEP_FOREST_MOBS);
	private static final int AYLYITHAN_FOLIAGE_COLOR = 0x627F38;
	private static final int DEEP_AYLYITHAN_FOLIAGE_COLOR = 0x9E811A;
	private static final int WATER_COLOR = 4159204;
	private static final int UNDERWATER_COLOR = 329011;
	private static final int FOG_COLOR = 0x666666;
	private static final int SKY_COLOR = 0x000000;
	
	public static void init() {
		Registry.register(BuiltinRegistries.BIOME, CLEARING_ID, CLEARING);
		Registry.register(BuiltinRegistries.BIOME, OVERGROWN_CLEARING_ID, OVERGROWN_CLEARING);
		Registry.register(BuiltinRegistries.BIOME, FOREST_ID, FOREST);
		Registry.register(BuiltinRegistries.BIOME, DEEP_FOREST_ID, DEEP_FOREST);
		Registry.register(BuiltinRegistries.BIOME, CONIFEROUS_FOREST_ID, CONIFEROUS_FOREST);
		Registry.register(BuiltinRegistries.BIOME, DEEP_CONIFEROUS_FOREST_ID, DEEP_CONIFEROUS_FOREST);
	}
	
	private static Biome createClearing(boolean overgrown, SpawnSettings.Builder spawnSettings) {
		GenerationSettings.Builder builder = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		//DefaultBiomeFeatures.addLandCarvers(builder);
		//builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.CLEARING_FLOWERS);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_PLAIN);
		if (overgrown) {
			builder.feature(GenerationStep.Feature.LAKES, ModWorldGenerators.SPRING);
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.OVERGROWTH_CLEARING_TREES);
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_TALL_GRASS);
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.BUSHES);
		}
		DefaultBiomeFeatures.addSprings(builder);
		DefaultBiomeFeatures.addFrozenTopLayer(builder);
		BiomeEffects.Builder effects = new BiomeEffects.Builder().foliageColor(AYLYITHAN_FOLIAGE_COLOR).grassColor(overgrown ? 0xBC953A : 0xA1BA48).waterColor(WATER_COLOR).waterFogColor(UNDERWATER_COLOR).fogColor(FOG_COLOR).skyColor(SKY_COLOR).moodSound(BiomeMoodSound.CAVE).particleConfig(new BiomeParticleConfig(ModParticles.AMBIENT_PILOT_LIGHT, 0.0025F));
		if (overgrown) {
			effects = effects.additionsSound(OVERGROWN_CLEARING_AMBIANCE);
		}
		return new Biome.Builder().precipitation(Biome.Precipitation.RAIN).category(Biome.Category.PLAINS).depth(0.1F).scale(0.5F).temperature(0.7F).downfall(0.8F).effects(effects.build()).spawnSettings(spawnSettings.build()).generationSettings(builder.build()).build();
	}
	
	private static Biome createForest(boolean deep, SpawnSettings.Builder spawnSettings) {
		GenerationSettings.Builder builder = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		//DefaultBiomeFeatures.addLandCarvers(builder);
		builder.feature(GenerationStep.Feature.LAKES, ModWorldGenerators.SPRING);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.BUSHES);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ModWorldGenerators.DEEP_ROOF_TREES : ModWorldGenerators.ROOF_TREES);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ModWorldGenerators.DEEP_FOREST_TREES : ModWorldGenerators.FOREST_TREES);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_TAIGA);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_TALL_GRASS);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.AYLYTH_WEEDS);
		if (deep) {
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_TALL_GRASS_2);
			DefaultBiomeFeatures.addLargeFerns(builder);
		}
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addSprings(builder);
		DefaultBiomeFeatures.addFrozenTopLayer(builder);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.YMPE_SEEP);
		return new Biome.Builder().precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST).depth(deep ? 0.5F : 0.3F).scale(deep ? 0.3F : 0.2F).temperature(0.7F).downfall(0.8F).effects((new BiomeEffects.Builder()).foliageColor(deep ? DEEP_AYLYITHAN_FOLIAGE_COLOR : AYLYITHAN_FOLIAGE_COLOR).grassColor(deep ? 0xAD6903 : 0xB5883B).waterColor(WATER_COLOR).waterFogColor(UNDERWATER_COLOR).fogColor(FOG_COLOR).skyColor(SKY_COLOR).moodSound(BiomeMoodSound.CAVE).particleConfig(new BiomeParticleConfig(ParticleTypes.MYCELIUM, deep ? 0.1F : 0.025F)).additionsSound(FOREST_AMBIANCE).build()).spawnSettings(spawnSettings.build()).generationSettings(builder.build()).build();
	}
	
	private static Biome createConiferousForest(boolean deep, SpawnSettings.Builder spawnSettings) {
		GenerationSettings.Builder builder = (new GenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
		//DefaultBiomeFeatures.addLandCarvers(builder);
		DefaultBiomeFeatures.addForestFlowers(builder);
		builder.feature(GenerationStep.Feature.LAKES, ModWorldGenerators.SPRING);
		if (deep) {
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.DEEP_CONIFEROUS_ROOF_TREES);
			DefaultBiomeFeatures.addLargeFerns(builder);
		}
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ModWorldGenerators.DEEP_CONIFEROUS_FOREST_TREES : ModWorldGenerators.CONIFEROUS_FOREST_TREES);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.PATCH_GRASS_TAIGA);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.AYLYTH_WEEDS);
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addSprings(builder);
		DefaultBiomeFeatures.addFrozenTopLayer(builder);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModWorldGenerators.YMPE_SEEP);
		return new Biome.Builder().precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST).depth(deep ? 0.4F : 0.3F).scale(deep ? 0.3F : 0.2F).temperature(0.7F).downfall(0.8F).effects((new BiomeEffects.Builder()).foliageColor(deep ? DEEP_AYLYITHAN_FOLIAGE_COLOR : AYLYITHAN_FOLIAGE_COLOR).grassColor(deep ? 0x3E682B : 0x4D7C44).waterColor(WATER_COLOR).waterFogColor(UNDERWATER_COLOR).fogColor(FOG_COLOR).skyColor(SKY_COLOR).moodSound(BiomeMoodSound.CAVE).particleConfig(new BiomeParticleConfig(ParticleTypes.MYCELIUM, deep ? 0.1F : 0.025F)).additionsSound(FOREST_AMBIANCE).build()).spawnSettings(spawnSettings.build()).generationSettings(builder.build()).build();
	}
}
