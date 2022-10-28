package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.biome.BiomeBuilder;
import moriyashiine.aylyth.common.registry.biome.SpawnSettingsBuilder;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

//TODO: clearing flowers still need fixed
public class ModBiomes {
	public static final SpawnSettings COPSE_MOBS = SpawnSettingsBuilder.builder().spawnCost(ModEntityTypes.AYLYTHIAN, 2, 1).monster(ModEntityTypes.AYLYTHIAN, 20, 1, 2).ambient(ModEntityTypes.PILOT_LIGHT, 5, 1, 1).spawnChance(0.5F).build();
	public static final SpawnSettings DEEPWOOD_MOBS = SpawnSettingsBuilder.builder().spawnCost(ModEntityTypes.AYLYTHIAN, 3, 1).monster(ModEntityTypes.AYLYTHIAN, 25, 1, 3).monster(ModEntityTypes.ELDER_AYLYTHIAN, 2, 1, 1).ambient(ModEntityTypes.PILOT_LIGHT, 10, 1, 1).build();
	public static final BiomeAdditionsSound OVERGROWN_CLEARING_AMBIANCE = new BiomeAdditionsSound(ModSoundEvents.AMBIENT_FOREST_ADDITIONS, 0.001);
	public static final BiomeAdditionsSound FOREST_AMBIANCE = new BiomeAdditionsSound(ModSoundEvents.AMBIENT_FOREST_ADDITIONS, 0.005);
	public static final RegistryKey<Biome> CLEARING_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "clearing"));
	public static final RegistryKey<Biome> OVERGROWN_CLEARING_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "overgrown_clearing"));
	public static final RegistryKey<Biome> COPSE_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "copse"));
	public static final RegistryKey<Biome> DEEPWOOD_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "deepwood"));
	public static final RegistryKey<Biome> CONIFEROUS_COPSE_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "coniferous_copse"));
	public static final RegistryKey<Biome> CONIFEROUS_DEEPWOOD_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "coniferous_deepwood"));
	public static final RegistryKey<Biome> UPLANDS_ID = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Aylyth.MOD_ID, "uplands"));
	private static final int AYLYTHIAN_FOLIAGE_COLOR = 0x627F38;
	private static final int DEEP_AYLYTHIAN_FOLIAGE_COLOR = 0x9E811A;
	private static final int WATER_COLOR = 0x3F76E4;
	private static final int UNDERWATER_COLOR = 0x050533;
	private static final int FOG_COLOR = 0x666666;
	private static final int SKY_COLOR = 0x000000;
	
	public static void datagenInit() {
		BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, CLEARING_ID.getValue().toString(), createClearing(false, SpawnSettingsBuilder.none()));
		BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, OVERGROWN_CLEARING_ID.getValue().toString(), createClearing(true, SpawnSettingsBuilder.builder().ambient(ModEntityTypes.PILOT_LIGHT, 1, 1, 1).spawnChance(0.1F).build()));
		BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, COPSE_ID.getValue().toString(), createForest(false, COPSE_MOBS));
		BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, DEEPWOOD_ID.getValue().toString(), createForest(true, DEEPWOOD_MOBS));
		BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, CONIFEROUS_COPSE_ID.getValue().toString(), createConiferousForest(false, COPSE_MOBS));
		BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, CONIFEROUS_DEEPWOOD_ID.getValue().toString(), createConiferousForest(true, DEEPWOOD_MOBS));
		BuiltinRegistries.addCasted(BuiltinRegistries.BIOME, UPLANDS_ID.getValue().toString(), createUplands(SpawnSettingsBuilder.none()));
	}
	
	private static Biome createClearing(boolean overgrown, SpawnSettings spawnSettings) {
		return BiomeBuilder.builder(Biome.Precipitation.RAIN, 0.7F, 0.8F)
				.biomeEffects(FOG_COLOR, WATER_COLOR, UNDERWATER_COLOR, SKY_COLOR, biomeEffectsBuilder -> {
					biomeEffectsBuilder.foliageColor(AYLYTHIAN_FOLIAGE_COLOR)
							.grassColor(overgrown ? 0xBC953A : 0xA1BA48)
							.moodSound(BiomeMoodSound.CAVE)
							.particleConfig(ModParticles.AMBIENT_PILOT_LIGHT, 0.0025F);
					if (overgrown) {
						biomeEffectsBuilder.additionsSound(OVERGROWN_CLEARING_AMBIANCE);
					}
				})
				.spawnSettings(spawnSettings)
				.generationSettings(generationSettingsBuilder -> {
					generationSettingsBuilder
							.add(ModBiomes::addLandCarversNotLavaLakes)
							.add(ModBiomes::addBasicVanillaOres)
							.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_GRASS_PLAIN)
							.add(ModBiomes::addMarigolds)
							.add(ModBiomes::addWaterSprings)
							.add(DefaultBiomeFeatures::addFrozenTopLayer);
					if (overgrown) {
						generationSettingsBuilder.lakesFeature(ModPlacedFeatures.SPRING)
								.vegetalDecoFeature(ModVegetationFeatures.OVERGROWTH_CLEARING_TREES_PLACED)
								.vegetalDecoFeature(ModPlacedFeatures.BUSHES)
								.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS)
								.add(ModBiomes::addStrewnLeaves);
					}
				}).build();
	}
	
	private static Biome createForest(boolean deep, SpawnSettings spawnSettings) {
		return BiomeBuilder.builder(Biome.Precipitation.RAIN, 0.7F, 0.8F)
				.biomeEffects(FOG_COLOR, WATER_COLOR, UNDERWATER_COLOR, SKY_COLOR, biomeEffectsBuilder -> {
					biomeEffectsBuilder.foliageColor(deep ? DEEP_AYLYTHIAN_FOLIAGE_COLOR : AYLYTHIAN_FOLIAGE_COLOR)
							.grassColor(deep ? 0xAD6903 : 0xB5883B)
							.moodSound(BiomeMoodSound.CAVE)
							.particleConfig(ParticleTypes.MYCELIUM, deep ? 0.1F : 0.025F)
							.additionsSound(FOREST_AMBIANCE);
				})
				.spawnSettings(spawnSettings)
				.generationSettings(generationSettingsBuilder -> {
					generationSettingsBuilder.add(ModBiomes::addLandCarversNotLavaLakes)
							.add(ModBiomes::addBasicVanillaOres)
							.lakesFeature(ModPlacedFeatures.SPRING)
							.vegetalDecoFeature(deep ? ModVegetationFeatures.DEEP_ROOF_TREES_PLACED : ModPlacedFeatures.AYLYTHIAN_DARK_OAK)
							.vegetalDecoFeature(deep ? ModVegetationFeatures.DEEPWOOD_TREES_PLACED : ModVegetationFeatures.COPSE_TREES_PLACED)
							.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_GRASS_TAIGA)
							.vegetalDecoFeature(ModPlacedFeatures.BUSHES)
							.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS)
							.vegetalDecoFeature(ModPlacedFeatures.AYLYTH_WEEDS)
							.add(deep ? ModBiomes::addMushroomsDeepwood : ModBiomes::addMushroomsCommon)
							.vegetalDecoFeature(ModVegetationFeatures.SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_PLACED)
							.vegetalDecoFeature(ModVegetationFeatures.GHOSTCAP_MUSHROOM_PATCHES_PLACED)
							.add(ModBiomes::addStrewnLeaves)
							.add(ModBiomes::addWaterSprings)
							.add(DefaultBiomeFeatures::addFrozenTopLayer)
							.vegetalDecoFeature(ModPlacedFeatures.YMPE_SEEP);
					if (deep) {
						generationSettingsBuilder.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS_2)
								.add(ModBiomes::addLeafPiles)
								.add(DefaultBiomeFeatures::addLargeFerns);
					}
				}).build();
	}
	
	private static Biome createConiferousForest(boolean deep, SpawnSettings spawnSettings) {
		return BiomeBuilder.builder(Biome.Precipitation.RAIN, 0.7F, 0.8F)
				.biomeEffects(FOG_COLOR, WATER_COLOR, UNDERWATER_COLOR, SKY_COLOR, biomeEffectsBuilder -> {
					biomeEffectsBuilder.foliageColor(deep ? DEEP_AYLYTHIAN_FOLIAGE_COLOR : AYLYTHIAN_FOLIAGE_COLOR)
							.grassColor(deep ? 0x3E682B : 0x4D7C44)
							.moodSound(BiomeMoodSound.CAVE)
							.particleConfig(ParticleTypes.MYCELIUM, deep ? 0.1F : 0.025F)
							.additionsSound(FOREST_AMBIANCE);
				})
				.spawnSettings(spawnSettings)
				.generationSettings(builder -> {
					builder.add(ModBiomes::addLandCarversNotLavaLakes)
							.add(ModBiomes::addBasicVanillaOres)
							.add(DefaultBiomeFeatures::addForestFlowers)
							.lakesFeature(ModPlacedFeatures.SPRING)
							.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_GRASS_TAIGA)
							.vegetalDecoFeature(ModPlacedFeatures.AYLYTH_WEEDS)
							.vegetalDecoFeature(deep ? ModVegetationFeatures.CONIFEROUS_DEEPWOOD_TREES_PLACED : ModVegetationFeatures.CONIFEROUS_COPSE_TREES_PLACED)
							.add(deep ? ModBiomes::addMushroomsDeepwood : ModBiomes::addMushroomsCommon)
							.vegetalDecoFeature(ModVegetationFeatures.GHOSTCAP_MUSHROOM_PATCHES_PLACED)
							.add(ModBiomes::addStrewnLeaves)
							.add(ModBiomes::addWaterSprings)
							.add(DefaultBiomeFeatures::addFrozenTopLayer)
							.vegetalDecoFeature(ModPlacedFeatures.YMPE_SEEP);
					if (deep) {
						builder.vegetalDecoFeature(ModVegetationFeatures.CONIFEROUS_DEEP_ROOF_TREES_PLACED)
								.vegetalDecoFeature(ModVegetationFeatures.SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_PLACED)
								.add(ModBiomes::addLeafPiles)
								.add(DefaultBiomeFeatures::addLargeFerns);
					}
				}).build();
	}

	private static Biome createUplands(SpawnSettings spawnSettings) {
		return BiomeBuilder.builder(Biome.Precipitation.NONE, 0.8F, 0.3F)
				.biomeEffects(FOG_COLOR, WATER_COLOR, UNDERWATER_COLOR, SKY_COLOR, biomeEffectsBuilder -> {
					biomeEffectsBuilder.foliageColor(AYLYTHIAN_FOLIAGE_COLOR)
							.grassColor(0xB5883B)
							.moodSound(BiomeMoodSound.CAVE);
				})
				.spawnSettings(spawnSettings)
				.generationSettings(builder -> {
					builder.add(ModBiomes::addLandCarversNotLavaLakes)
							.add(ModBiomes::addBasicVanillaOres)
							.add(ModBiomes::addWaterSprings);
				}).build();
	}

	private static void addLandCarversNotLavaLakes(GenerationSettings.Builder builder) {
		builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
		builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE_EXTRA_UNDERGROUND);
		builder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
	}

	private static void addWaterSprings(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.FLUID_SPRINGS, MiscPlacedFeatures.SPRING_WATER);
	}

	private static void addBasicVanillaOres(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_IRON_UPPER);
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_IRON_MIDDLE);
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_IRON_SMALL);
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_COPPER);
	}

	private static void addMarigolds(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.MARIGOLDS);
	}

	private static void addLeafPiles(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OAK_LEAF_PILE);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.YMPE_LEAF_PILE);
	}

	private static void addStrewnLeaves(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OAK_STREWN_LEAVES);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.YMPE_STREWN_LEAVES);
	}

	private static void addMushroomsCommon(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationFeatures.RED_MUSHROOM_PATCHES_PLACED);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationFeatures.BROWN_MUSHROOM_PATCHES_PLACED);
	}

	private static void addMushroomsDeepwood(GenerationSettings.Builder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationFeatures.RED_MUSHROOM_PATCHES_DEEPWOOD_PLACED);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationFeatures.BROWN_MUSHROOM_PATCHES_DEEPWOOD_PLACED);
	}
}
