package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

//TODO: springs and clearing flowers still need fixed
public class ModBiomes {
	public static final SpawnSettings.Builder COPSE_MOBS = new SpawnSettings.Builder().spawnCost(ModEntityTypes.AYLYTHIAN, 2, 1).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntityTypes.AYLYTHIAN, 20, 1, 2)).spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(ModEntityTypes.PILOT_LIGHT, 5, 1, 1)).creatureSpawnProbability(0.5F);
	public static final SpawnSettings.Builder DEEPWOOD_MOBS = new SpawnSettings.Builder().spawnCost(ModEntityTypes.AYLYTHIAN, 3, 1).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntityTypes.AYLYTHIAN, 25, 1, 3)).spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntityTypes.ELDER_AYLYTHIAN, 2, 1, 1)).spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(ModEntityTypes.PILOT_LIGHT, 10, 1, 1));
	public static final BiomeAdditionsSound OVERGROWN_CLEARING_AMBIANCE = new BiomeAdditionsSound(ModSoundEvents.AMBIENT_FOREST_ADDITIONS, 0.001);
	public static final BiomeAdditionsSound FOREST_AMBIANCE = new BiomeAdditionsSound(ModSoundEvents.AMBIENT_FOREST_ADDITIONS, 0.005);
	public static final RegistryKey<Biome> CLEARING_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "clearing"));
	public static final RegistryKey<Biome> OVERGROWN_CLEARING_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "overgrown_clearing"));
	public static final RegistryKey<Biome> COPSE_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "copse"));
	public static final RegistryKey<Biome> DEEPWOOD_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "deepwood"));
	public static final RegistryKey<Biome> CONIFEROUS_COPSE_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "coniferous_copse"));
	public static final RegistryKey<Biome> CONIFEROUS_DEEPWOOD_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "coniferous_deepwood"));
	public static final RegistryEntry<Biome> CLEARING = BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, CLEARING_ID.getValue().toString(), createClearing(false, new SpawnSettings.Builder()));
	public static final RegistryEntry<Biome> OVERGROWN_CLEARING = BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, OVERGROWN_CLEARING_ID.getValue().toString(), createClearing(true, new SpawnSettings.Builder().spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(ModEntityTypes.PILOT_LIGHT, 1, 1, 1)).creatureSpawnProbability(0.1F)));
	public static final RegistryEntry<Biome> COPSE = BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, COPSE_ID.getValue().toString(), createForest(false, COPSE_MOBS));
	public static final RegistryEntry<Biome> DEEPWOOD = BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, DEEPWOOD_ID.getValue().toString(), createForest(true, DEEPWOOD_MOBS));
	public static final RegistryEntry<Biome> CONIFEROUS_COPSE = BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, CONIFEROUS_COPSE_ID.getValue().toString(), createConiferousForest(false, COPSE_MOBS));
	public static final RegistryEntry<Biome> CONIFEROUS_DEEPWOOD = BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, CONIFEROUS_DEEPWOOD_ID.getValue().toString(), createConiferousForest(true, DEEPWOOD_MOBS));
	private static final int AYLYTHIAN_FOLIAGE_COLOR = 0x627F38;
	private static final int DEEP_AYLYTHIAN_FOLIAGE_COLOR = 0x9E811A;
	private static final int WATER_COLOR = 4159204;
	private static final int UNDERWATER_COLOR = 329011;
	private static final int FOG_COLOR = 0x666666;
	private static final int SKY_COLOR = 0x000000;
	
	public static void init() {}
	
	private static Biome createClearing(boolean overgrown, SpawnSettings.Builder spawnSettings) {
		GenerationSettings.Builder builder = new GenerationSettings.Builder();
		DefaultBiomeFeatures.addLandCarvers(builder);
//		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CLEARING_FLOWERS);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_PLAIN);
		if (overgrown) {
			builder.feature(GenerationStep.Feature.LAKES, ModPlacedFeatures.SPRING);
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationFeatures.OVERGROWTH_CLEARING_TREES_PLACED);
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BUSHES);
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS);
		}
		DefaultBiomeFeatures.addSprings(builder);
		DefaultBiomeFeatures.addFrozenTopLayer(builder);
		BiomeEffects.Builder effects = new BiomeEffects.Builder().foliageColor(AYLYTHIAN_FOLIAGE_COLOR).grassColor(overgrown ? 0xBC953A : 0xA1BA48).waterColor(WATER_COLOR).waterFogColor(UNDERWATER_COLOR).fogColor(FOG_COLOR).skyColor(SKY_COLOR).moodSound(BiomeMoodSound.CAVE).particleConfig(new BiomeParticleConfig(ModParticles.AMBIENT_PILOT_LIGHT, 0.0025F));
		if (overgrown) {
			effects = effects.additionsSound(OVERGROWN_CLEARING_AMBIANCE);
		}
		return new Biome.Builder().precipitation(Biome.Precipitation.RAIN).temperature(0.7F).downfall(0.8F).effects(effects.build()).spawnSettings(spawnSettings.build()).generationSettings(builder.build()).build();
	}
	
	private static Biome createForest(boolean deep, SpawnSettings.Builder spawnSettings) {
		GenerationSettings.Builder builder = new GenerationSettings.Builder();
		DefaultBiomeFeatures.addLandCarvers(builder);
		builder.feature(GenerationStep.Feature.LAKES, ModPlacedFeatures.SPRING);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ModVegetationFeatures.DEEP_ROOF_TREES_PLACED : ModPlacedFeatures.AYLYTHIAN_DARK_OAK);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ModVegetationFeatures.DEEPWOOD_TREES_PLACED : ModVegetationFeatures.COPSE_TREES_PLACED);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BUSHES);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.AYLYTH_WEEDS);
		if (deep) {
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS_2);
			DefaultBiomeFeatures.addLargeFerns(builder);
		}
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addSprings(builder);
		DefaultBiomeFeatures.addFrozenTopLayer(builder);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.YMPE_SEEP);
		return new Biome.Builder().precipitation(Biome.Precipitation.RAIN).temperature(0.7F).downfall(0.8F).effects(new BiomeEffects.Builder().foliageColor(deep ? DEEP_AYLYTHIAN_FOLIAGE_COLOR : AYLYTHIAN_FOLIAGE_COLOR).grassColor(deep ? 0xAD6903 : 0xB5883B).waterColor(WATER_COLOR).waterFogColor(UNDERWATER_COLOR).fogColor(FOG_COLOR).skyColor(SKY_COLOR).moodSound(BiomeMoodSound.CAVE).particleConfig(new BiomeParticleConfig(ParticleTypes.MYCELIUM, deep ? 0.1F : 0.025F)).additionsSound(FOREST_AMBIANCE).build()).spawnSettings(spawnSettings.build()).generationSettings(builder.build()).build();
	}
	
	private static Biome createConiferousForest(boolean deep, SpawnSettings.Builder spawnSettings) {
		GenerationSettings.Builder builder = new GenerationSettings.Builder();
		DefaultBiomeFeatures.addLandCarvers(builder);
		DefaultBiomeFeatures.addForestFlowers(builder);
		builder.feature(GenerationStep.Feature.LAKES, ModPlacedFeatures.SPRING);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_TAIGA);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.AYLYTH_WEEDS);
		if (deep) {
			builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationFeatures.CONIFEROUS_DEEP_ROOF_TREES_PLACED);
			DefaultBiomeFeatures.addLargeFerns(builder);
		}
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, deep ? ModVegetationFeatures.CONIFEROUS_DEEPWOOD_TREES_PLACED : ModVegetationFeatures.CONIFEROUS_COPSE_TREES_PLACED);
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		DefaultBiomeFeatures.addSprings(builder);
		DefaultBiomeFeatures.addFrozenTopLayer(builder);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.YMPE_SEEP);
		return new Biome.Builder().precipitation(Biome.Precipitation.RAIN).temperature(0.7F).downfall(0.8F).effects(new BiomeEffects.Builder().foliageColor(deep ? DEEP_AYLYTHIAN_FOLIAGE_COLOR : AYLYTHIAN_FOLIAGE_COLOR).grassColor(deep ? 0x3E682B : 0x4D7C44).waterColor(WATER_COLOR).waterFogColor(UNDERWATER_COLOR).fogColor(FOG_COLOR).skyColor(SKY_COLOR).moodSound(BiomeMoodSound.CAVE).particleConfig(new BiomeParticleConfig(ParticleTypes.MYCELIUM, deep ? 0.1F : 0.025F)).additionsSound(FOREST_AMBIANCE).build()).spawnSettings(spawnSettings.build()).generationSettings(builder.build()).build();
	}
}
