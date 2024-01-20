package moriyashiine.aylyth.datagen.worldgen.biomes;

import com.chocohead.mm.api.ClassTinkerers;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModParticles;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import moriyashiine.aylyth.datagen.worldgen.biomes.util.BiomeBuilder;
import moriyashiine.aylyth.datagen.worldgen.biomes.util.SpawnSettingsBuilder;
import moriyashiine.aylyth.datagen.worldgen.features.ModCarvers;
import moriyashiine.aylyth.datagen.worldgen.features.ModConfiguredFeatures;
import moriyashiine.aylyth.datagen.worldgen.features.ModPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.*;

import static moriyashiine.aylyth.common.registry.ModBiomeKeys.*;

public class ModBiomes {
	public static final SpawnSettings COPSE_MOBS = SpawnSettingsBuilder.builder()
			.spawnCost(ModEntityTypes.AYLYTHIAN, 0.7, 0.13)
			.spawnCost(ModEntityTypes.FAUNAYLYTHIAN, 0.7, 0.13)
			.spawnCost(EntityType.ENDERMAN, 0.7, 0.13)
			.spawnCost(ModEntityTypes.PILOT_LIGHT, 0.7, 0.13)
			.monster(ModEntityTypes.AYLYTHIAN, 20, 1, 2)
			.monster(ModEntityTypes.FAUNAYLYTHIAN, 10, 1, 3)
			.monster(EntityType.ENDERMAN, 1, 1, 4)
			.ambient(ModEntityTypes.PILOT_LIGHT, 5, 1, 1)
			.spawnChance(0.5f)
			.build();
	public static final SpawnSettings DEEPWOOD_MOBS = SpawnSettingsBuilder.builder()
			.spawnCost(ModEntityTypes.AYLYTHIAN, 0.7, 0.13)
			.spawnCost(ModEntityTypes.FAUNAYLYTHIAN, 0.7, 0.13)
			.spawnCost(ModEntityTypes.SCION, 0.9, 0.13)
			.spawnCost(ModEntityTypes.ELDER_AYLYTHIAN, 0.7, 0.12)
			.spawnCost(EntityType.ENDERMAN, 0.7, 0.12)
			.spawnCost(ModEntityTypes.WREATHED_HIND_ENTITY, 4.5, 0.8)
			.spawnCost(ModEntityTypes.PILOT_LIGHT, 0.7, 0.1)
			.monster(ModEntityTypes.AYLYTHIAN, 50, 1, 3)
			.monster(ModEntityTypes.SCION, 15, 1, 1)
			.monster(ModEntityTypes.ELDER_AYLYTHIAN, 2, 1, 1)
			.monster(ModEntityTypes.FAUNAYLYTHIAN, 20, 1, 3)
			.monster(EntityType.ENDERMAN, 1, 1, 4)
			.monster(ModEntityTypes.WREATHED_HIND_ENTITY, 1, 1, 1)
			.ambient(ModEntityTypes.PILOT_LIGHT, 10, 1, 1)
			.spawnChance(0.5f)
			.build();
	public static final SpawnSettings UPLANDS_MOBS = SpawnSettingsBuilder.none();
	public static final SpawnSettings MIRE_MOBS = SpawnSettingsBuilder.builder()
			.spawnCost(ModEntityTypes.AYLYTHIAN, 0.7, 0.13)
			.spawnCost(ModEntityTypes.SCION, 0.9, 0.13)
			.spawnCost(ModEntityTypes.WREATHED_HIND_ENTITY, 0.7, 0.12)
			.spawnCost(EntityType.ENDERMAN, 0.7, 0.12)
			.spawnCost(ModEntityTypes.PILOT_LIGHT, 0.7, 0.12)
			.monster(ModEntityTypes.AYLYTHIAN, 20, 1, 1)
			.monster(ModEntityTypes.SCION, 5, 1, 1)
			.monster(ModEntityTypes.WREATHED_HIND_ENTITY, 2, 1, 1)
			.monster(EntityType.ENDERMAN, 1, 1, 4)
			.ambient(ModEntityTypes.PILOT_LIGHT, 5, 1, 1)
			.build();
	public static final SpawnSettings BOWELS_MOBS = SpawnSettingsBuilder.builder()
			.spawnCost(EntityType.ENDERMAN, 0.7, 0.09)
			.monster(EntityType.ENDERMAN, 1, 1, 4)
			.build();

	private static final int AYLYTHIAN_FOLIAGE_COLOR = 0x627F38;
	private static final int DEEP_AYLYTHIAN_FOLIAGE_COLOR = 0x9E811A;
	private static final int MIRE_FOLIAGE_COLOR = 0x5C5D00;
	private static final int WATER_COLOR = 0x3F76E4;
	private static final int UNDERWATER_COLOR = 0x050533;
	private static final int MIRE_WATER_COLOR = 0x334553;
	private static final int MIRE_UNDERWATER_COLOR = 0x000000;
	private static final int FOG_COLOR = 0x666666;
	private static final int SKY_COLOR = 0x000000;
	public static final BiomeEffects.GrassColorModifier AYLYTH_NOISE = ClassTinkerers.getEnum(BiomeEffects.GrassColorModifier.class, "AYLYTH_NOISE");

	public static void bootstrap(Registerable<Biome> context) {
		var sounds = context.getRegistryLookup(RegistryKeys.SOUND_EVENT);
		var placedFeatures = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
		var configuredCarvers = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);

		// TODO bad
		var overgrownClearingAmbience = new BiomeAdditionsSound(sounds.getOrThrow(RegistryKey.of(RegistryKeys.SOUND_EVENT, ModSoundEvents.AMBIENT_FOREST_ADDITIONS.getId())), 0.001);
		var forestAmbiance = new BiomeAdditionsSound(sounds.getOrThrow(RegistryKey.of(RegistryKeys.SOUND_EVENT, ModSoundEvents.AMBIENT_FOREST_ADDITIONS.getId())), 0.005);

		context.register(CLEARING_ID, createClearing(false, SpawnSettingsBuilder.none(), overgrownClearingAmbience, placedFeatures, configuredCarvers));
		context.register(OVERGROWN_CLEARING_ID, createClearing(true, SpawnSettingsBuilder.builder().ambient(ModEntityTypes.PILOT_LIGHT, 1, 1, 1).spawnChance(0.1F).build(), overgrownClearingAmbience, placedFeatures, configuredCarvers));
		context.register(COPSE_ID, createForest(false, COPSE_MOBS, forestAmbiance, placedFeatures, configuredCarvers));
		context.register(DEEPWOOD_ID, createForest(true, DEEPWOOD_MOBS, forestAmbiance, placedFeatures, configuredCarvers));
		context.register(CONIFEROUS_COPSE_ID, createConiferousForest(false, COPSE_MOBS, forestAmbiance, placedFeatures, configuredCarvers));
		context.register(CONIFEROUS_DEEPWOOD_ID, createConiferousForest(true, DEEPWOOD_MOBS, forestAmbiance, placedFeatures, configuredCarvers));
		context.register(UPLANDS_ID, createUplands(UPLANDS_MOBS, placedFeatures, configuredCarvers));
		context.register(MIRE_ID, createMire(MIRE_MOBS, placedFeatures, configuredCarvers));
		context.register(BOWELS_ID, createBowels(BOWELS_MOBS, placedFeatures, configuredCarvers));
	}
	
	private static Biome createClearing(boolean overgrown, SpawnSettings spawnSettings, BiomeAdditionsSound overgrownClearingAmbience, RegistryEntryLookup<PlacedFeature> placedFeatures, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers) {
		return BiomeBuilder.builder(Biome.Precipitation.RAIN, 0.7F, 0.8F)
				.biomeEffects(FOG_COLOR, WATER_COLOR, UNDERWATER_COLOR, SKY_COLOR, biomeEffectsBuilder -> {
					biomeEffectsBuilder.foliageColor(AYLYTHIAN_FOLIAGE_COLOR)
							.grassColor(overgrown ? 0xBC953A : 0xA1BA48)
							.grassColorModifier(AYLYTH_NOISE)
							.moodSound(BiomeMoodSound.CAVE)
							.particleConfig(ModParticles.AMBIENT_PILOT_LIGHT, 0.0025F);
					if (overgrown) {
						biomeEffectsBuilder.additionsSound(overgrownClearingAmbience);
					}
				})
				.spawnSettings(spawnSettings)
				.generationSettings(generationSettingsBuilder -> {
					generationSettingsBuilder
							.add(ModBiomes::addLandCarversNotLavaLakes)
							.add(ModBiomes::addBasicVanillaOres)
							.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_GRASS_PLAIN)
							.vegetalDecoFeature(ModPlacedFeatures.POMEGRANATE_TREE_VEG_PLACED)
							.add(ModBiomes::addMarigolds)
							.add(ModBiomes::addWaterSprings)
							.add(DefaultBiomeFeatures::addFrozenTopLayer);
					if (overgrown) {
						generationSettingsBuilder.lakesFeature(ModPlacedFeatures.SPRING)
								.vegetalDecoFeature(ModPlacedFeatures.OVERGROWTH_CLEARING_TREES_PLACED)
								.vegetalDecoFeature(ModPlacedFeatures.BUSHES)
								.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS)
								.add(ModBiomes::addStrewnLeaves);
					}
				}, placedFeatures, configuredCarvers).build();
	}
	
	private static Biome createForest(boolean deep, SpawnSettings spawnSettings, BiomeAdditionsSound forestAmbiance, RegistryEntryLookup<PlacedFeature> placedFeatures, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers) {
		return BiomeBuilder.builder(Biome.Precipitation.RAIN, 0.7F, 0.8F)
				.biomeEffects(FOG_COLOR, WATER_COLOR, UNDERWATER_COLOR, SKY_COLOR, biomeEffectsBuilder -> {
					biomeEffectsBuilder.foliageColor(deep ? DEEP_AYLYTHIAN_FOLIAGE_COLOR : AYLYTHIAN_FOLIAGE_COLOR)
							.grassColor(deep ? 0xAD6903 : 0xB5883B)
							.grassColorModifier(deep ? AYLYTH_NOISE : BiomeEffects.GrassColorModifier.NONE)
							.moodSound(BiomeMoodSound.CAVE)
							.particleConfig(ParticleTypes.MYCELIUM, deep ? 0.1F : 0.025F)
							.additionsSound(forestAmbiance);
				})
				.spawnSettings(spawnSettings)
				.generationSettings(generationSettingsBuilder -> {
					generationSettingsBuilder.add(ModBiomes::addLandCarversNotLavaLakes)
							.add(ModBiomes::addBasicVanillaOres)
							.lakesFeature(ModPlacedFeatures.SPRING)
							.vegetalDecoFeature(deep ? ModPlacedFeatures.DEEP_ROOF_TREES_PLACED : ModPlacedFeatures.AYLYTHIAN_DARK_OAK)
							.vegetalDecoFeature(deep ? ModPlacedFeatures.DEEPWOOD_TREES_PLACED : ModPlacedFeatures.COPSE_TREES_PLACED)
							.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_GRASS_TAIGA)
							.vegetalDecoFeature(ModPlacedFeatures.BUSHES)
							.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS)
							.vegetalDecoFeature(ModPlacedFeatures.AYLYTH_WEEDS)
							.add(deep ? ModBiomes::addMushroomsDeepwood : ModBiomes::addMushroomsCommon)
							.vegetalDecoFeature(deep ? ModPlacedFeatures.SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_DEEPWOOD_PLACED : ModPlacedFeatures.SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_COMMON_PLACED)
							.vegetalDecoFeature(ModPlacedFeatures.GHOSTCAP_MUSHROOM_PATCHES_PLACED)
							.add(ModBiomes::addStrewnLeaves)
							.add(ModBiomes::addWaterSprings)
							.add(DefaultBiomeFeatures::addFrozenTopLayer)
							.vegetalDecoFeature(ModPlacedFeatures.YMPE_SEEP);
					if (deep) {
						generationSettingsBuilder.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS_2)
								.add(ModBiomes::addWoodyGrowths)
								.add(ModBiomes::addLeafPiles)
								.add(DefaultBiomeFeatures::addLargeFerns);
					} else {
						generationSettingsBuilder.vegetalDecoFeature(ModPlacedFeatures.POMEGRANATE_TREE_VEG_PLACED);
					}
				}, placedFeatures, configuredCarvers).build();
	}
	
	private static Biome createConiferousForest(boolean deep, SpawnSettings spawnSettings, BiomeAdditionsSound forestAmbiance, RegistryEntryLookup<PlacedFeature> placedFeatures, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers) {
		return BiomeBuilder.builder(Biome.Precipitation.RAIN, 0.7F, 0.8F)
				.biomeEffects(FOG_COLOR, WATER_COLOR, UNDERWATER_COLOR, SKY_COLOR, biomeEffectsBuilder -> {
					biomeEffectsBuilder.foliageColor(deep ? DEEP_AYLYTHIAN_FOLIAGE_COLOR : AYLYTHIAN_FOLIAGE_COLOR)
							.grassColor(deep ? 0x3E682B : 0x4D7C44)
							.grassColorModifier(deep ? AYLYTH_NOISE : BiomeEffects.GrassColorModifier.NONE)
							.moodSound(BiomeMoodSound.CAVE)
							.particleConfig(ParticleTypes.MYCELIUM, deep ? 0.1F : 0.025F)
							.additionsSound(forestAmbiance);
				})
				.spawnSettings(spawnSettings)
				.generationSettings(builder -> {
					builder.add(ModBiomes::addLandCarversNotLavaLakes)
							.add(ModBiomes::addBasicVanillaOres)
							.add(DefaultBiomeFeatures::addForestFlowers)
							.lakesFeature(ModPlacedFeatures.SPRING)
							.vegetalDecoFeature(VegetationPlacedFeatures.PATCH_GRASS_TAIGA)
							.vegetalDecoFeature(ModPlacedFeatures.AYLYTH_WEEDS)
							.vegetalDecoFeature(deep ? ModPlacedFeatures.CONIFEROUS_DEEPWOOD_TREES_PLACED : ModPlacedFeatures.CONIFEROUS_COPSE_TREES_PLACED)
							.add(deep ? ModBiomes::addMushroomsDeepwood : ModBiomes::addMushroomsCommon)
							.vegetalDecoFeature(ModPlacedFeatures.GHOSTCAP_MUSHROOM_PATCHES_PLACED)
							.add(ModBiomes::addStrewnLeaves)
							.add(ModBiomes::addWaterSprings)
							.add(DefaultBiomeFeatures::addFrozenTopLayer)
							.vegetalDecoFeature(ModPlacedFeatures.YMPE_SEEP);
					if (deep) {
						builder.vegetalDecoFeature(ModPlacedFeatures.CONIFEROUS_DEEP_ROOF_TREES_PLACED)
								.vegetalDecoFeature(ModPlacedFeatures.SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_DEEPWOOD_PLACED)
								.add(ModBiomes::addWoodyGrowths)
								.add(ModBiomes::addLeafPiles)
								.add(DefaultBiomeFeatures::addLargeFerns);
					}
				}, placedFeatures, configuredCarvers).build();
	}

	private static Biome createUplands(SpawnSettings spawnSettings, RegistryEntryLookup<PlacedFeature> placedFeatures, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers) {
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
				}, placedFeatures, configuredCarvers).build();
	}

	private static Biome createMire(SpawnSettings spawnSettings, RegistryEntryLookup<PlacedFeature> placedFeatures, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers) {
		return BiomeBuilder.builder(Biome.Precipitation.RAIN, 0.8F, 0.3F)
				.biomeEffects(FOG_COLOR, MIRE_WATER_COLOR, MIRE_UNDERWATER_COLOR, SKY_COLOR, biomeEffectsBuilder -> {
					biomeEffectsBuilder.foliageColor(MIRE_FOLIAGE_COLOR)
							.grassColor(MIRE_FOLIAGE_COLOR)
							.grassColorModifier(AYLYTH_NOISE);
				})
				.spawnSettings(spawnSettings)
				.generationSettings(builder -> {
					builder.vegetalDecoFeature(ModPlacedFeatures.WOODY_GROWTH_WATER_PATCH_PLACED)
							.vegetalDecoFeature(ModPlacedFeatures.ANTLER_SHOOTS_WATER_PATCH_PLACED)
							.vegetalDecoFeature(ModPlacedFeatures.ANTLER_SHOOTS_PATCH_PLACED)
							.vegetalDecoFeature(ModPlacedFeatures.STREWN_LEAVES_PATCH_PLACED)
							.vegetalDecoFeature(ModPlacedFeatures.AYLYTH_WEEDS)
							.vegetalDecoFeature(ModPlacedFeatures.MIRE_WATER_TREES_PLACED)
							.vegetalDecoFeature(ModPlacedFeatures.MIRE_LAND_TREES_PLACED)
							.add(ModBiomes::addBasicVanillaOres)
							.add(ModBiomes::addWoodyGrowths)
							.add(DefaultBiomeFeatures::addLargeFerns)
							.add(DefaultBiomeFeatures::addDefaultGrass);
				}, placedFeatures, configuredCarvers)
				.build();
	}

	private static Biome createBowels(SpawnSettings spawnSettings, RegistryEntryLookup<PlacedFeature> placedFeatures, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers) {
		return BiomeBuilder.builder(Biome.Precipitation.NONE, 0.5f, 0.0f)
				.biomeEffects(FOG_COLOR, WATER_COLOR, UNDERWATER_COLOR, SKY_COLOR)
				.spawnSettings(spawnSettings)
				.generationSettings(builder -> {
					builder.vegetalDecoFeature(ModPlacedFeatures.WOODY_GROWTH_BOWELS_PATCH_PLACED)
							.vegetalDecoFeature(NetherPlacedFeatures.PATCH_SOUL_FIRE);
				}, placedFeatures, configuredCarvers)
				.build();
	}

	private static void addLandCarversNotLavaLakes(GenerationSettings.LookupBackedBuilder builder) {
		builder.carver(GenerationStep.Carver.AIR, ModCarvers.CAVES);
		builder.carver(GenerationStep.Carver.AIR, ModCarvers.CANYONS);
	}

	private static void addWaterSprings(GenerationSettings.LookupBackedBuilder builder) {
		builder.feature(GenerationStep.Feature.FLUID_SPRINGS, MiscPlacedFeatures.SPRING_WATER);
	}

	private static void addBasicVanillaOres(GenerationSettings.LookupBackedBuilder builder) {
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_IRON_UPPER);
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_IRON_MIDDLE);
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_IRON_SMALL);
		builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_COPPER);
	}

	private static void addMarigolds(GenerationSettings.LookupBackedBuilder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.MARIGOLDS);
	}

	private static void addLeafPiles(GenerationSettings.LookupBackedBuilder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OAK_LEAF_PILE);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.YMPE_LEAF_PILE);
	}

	private static void addStrewnLeaves(GenerationSettings.LookupBackedBuilder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OAK_STREWN_LEAVES);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.YMPE_STREWN_LEAVES);
	}

	private static void addMushroomsCommon(GenerationSettings.LookupBackedBuilder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RED_MUSHROOM_PATCHES_PLACED);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BROWN_MUSHROOM_PATCHES_PLACED);
	}

	private static void addMushroomsDeepwood(GenerationSettings.LookupBackedBuilder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RED_MUSHROOM_PATCHES_DEEPWOOD_PLACED);
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BROWN_MUSHROOM_PATCHES_DEEPWOOD_PLACED);
	}

	private static void addWoodyGrowths(GenerationSettings.LookupBackedBuilder builder) {
		builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WOODY_GROWTH_PATCH_PLACED);
	}
}
