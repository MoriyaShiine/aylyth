package moriyashiine.aylyth.common.registry;

import com.google.common.collect.ImmutableList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import moriyashiine.aylyth.common.world.generator.AylthianTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.BigYmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.YmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.feature.BushFeature;
import moriyashiine.aylyth.common.world.generator.feature.GrapeVineDecorator;
import moriyashiine.aylyth.common.world.generator.feature.SeepFeature;
import moriyashiine.aylyth.common.world.generator.feature.SpringFeature;
import moriyashiine.aylyth.mixin.TreeDecoratorTypeAccessor;
import moriyashiine.aylyth.mixin.TrunkPlacerTypeAccessor;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModWorldGenerators extends DefaultBiomeFeatures {
	private static final BlockStateProvider YMPE_LOG_PROVIDER = new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.YMPE_LOG.getDefaultState(), 15).add(ModBlocks.FRUIT_BEARING_YMPE_LOG.getDefaultState(), 1).build());
	public static final TrunkPlacerType<AylthianTrunkPlacer> AYLYTHIAN_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":aylythian_trunk_placer", AylthianTrunkPlacer.CODEC);
	public static final TrunkPlacerType<YmpeTrunkPlacer> YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":ympe_trunk_placer", YmpeTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BigYmpeTrunkPlacer> BIG_YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":big_ympe_trunk_placer", BigYmpeTrunkPlacer.CODEC);
	public static final TreeDecoratorType<GrapeVineDecorator> GRAPE_VINE = TreeDecoratorTypeAccessor.register(Aylyth.MOD_ID + ":grape_vine_decorator", GrapeVineDecorator.CODEC);
	public static final SpringFeature SPRING_FEATURE = new SpringFeature();
	public static final SeepFeature SEEP_FEATURE = new SeepFeature();
	public static final BushFeature BUSH_FEATURE = new BushFeature();

	static class Configured {
		public static final ConfiguredFeature<TreeFeatureConfig, ?> AYLYTHIAN_DARK_OAK = Feature.TREE.configure(new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(), SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().decorators(ImmutableList.of(new GrapeVineDecorator())).build());
		public static final ConfiguredFeature<TreeFeatureConfig, ?> AYLYTHIAN_MEGA_DARK_OAK = Feature.TREE.configure(new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(18, 6, 7), SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new GrapeVineDecorator())).ignoreVines().build());
		public static final ConfiguredFeature<TreeFeatureConfig, ?> YMPE_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new YmpeTrunkPlacer(), SimpleBlockStateProvider.of(ModBlocks.YMPE_LEAVES.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
		public static final ConfiguredFeature<TreeFeatureConfig, ?> BIG_YMPE_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new BigYmpeTrunkPlacer(), SimpleBlockStateProvider.of(ModBlocks.YMPE_LEAVES.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
		//public static final ConfiguredFeature<?, ?> SPRING = SPRING_FEATURE.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).range(Decorators.TOP_TO_BOTTOM).spreadHorizontally().applyChance(8);
		//perhaps all the configured features don't need to be registered??? can just chain call .withPlacement
		public static final ConfiguredFeature<?, ?> BUSHES = BUSH_FEATURE.configure(FeatureConfig.DEFAULT);
		public static final ConfiguredFeature<?, ?> AYLYTH_WEEDS = Feature.FLOWER.configure(createRandomPatchFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(ModBlocks.GRIPWEED.getDefaultState(), 2).build()), 64));

		public static final ConfiguredFeature<?, ?> OAK_SEEP = SEEP_FEATURE.configure(new SeepFeature.SeepFeatureConfig(Blocks.OAK_LOG.getDefaultState(), ModBlocks.OAK_SEEP.getDefaultState()));
		public static final ConfiguredFeature<?, ?> SPRUCE_SEEP = SEEP_FEATURE.configure(new SeepFeature.SeepFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), ModBlocks.SPRUCE_SEEP.getDefaultState()));
		public static final ConfiguredFeature<?, ?> DARK_OAK_SEEP = SEEP_FEATURE.configure(new SeepFeature.SeepFeatureConfig(Blocks.DARK_OAK_LOG.getDefaultState(), ModBlocks.DARK_OAK_SEEP.getDefaultState()));
		public static final ConfiguredFeature<?, ?> YMPE_SEEP = SEEP_FEATURE.configure(new SeepFeature.SeepFeatureConfig(ModBlocks.YMPE_LOG.getDefaultState(), ModBlocks.YMPE_SEEP.getDefaultState()));
	}

	static class Placed {
		public static final PlacedFeature AYLYTHIAN_DARK_OAK = Configured.AYLYTHIAN_DARK_OAK.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(8, 0.25F, 2))); //decorate(Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(8, 0.3F, 2)));
		public static final PlacedFeature AYLYTHIAN_MEGA_DARK_OAK = Configured.AYLYTHIAN_MEGA_DARK_OAK.withPlacement(VegetationPlacedFeatures.modifiers(1));
		public static final PlacedFeature YMPE_TREE = Configured.YMPE_TREE.withPlacement(VegetationPlacedFeatures.modifiers(1));
		public static final PlacedFeature BIG_YMPE_TREE = Configured.BIG_YMPE_TREE.withPlacement(VegetationPlacedFeatures.modifiers(1));
		public static final PlacedFeature SPRING = SPRING_FEATURE.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(CountPlacementModifier.of(25), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(192)), BiomePlacementModifier.of());//.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).range(Decorators.TOP_TO_BOTTOM).spreadHorizontally().applyChance(8);
		public static final PlacedFeature BUSHES = Configured.BUSHES.withPlacement(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
		public static final PlacedFeature AYLYTH_WEEDS = Configured.AYLYTH_WEEDS.withPlacement(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());//configure(new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(ModBlocks.GRIPWEED.getDefaultState(), 2).build()), SimpleBlockPlacer.INSTANCE).tries(64).cannotProject().build()).decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE).decorate(Decorators.FOLIAGE_PLACEMENT).repeat(7));

		public static final PlacedFeature OAK_SEEP = Configured.OAK_SEEP.withPlacement(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
		public static final PlacedFeature SPRUCE_SEEP = Configured.SPRUCE_SEEP.withPlacement(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
		public static final PlacedFeature DARK_OAK_SEEP = Configured.DARK_OAK_SEEP.withPlacement(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
		public static final PlacedFeature YMPE_SEEP = Configured.YMPE_SEEP.withPlacement(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
	}

	static class Mixed {
		public static final ConfiguredFeature<?, ?> DEEP_ROOF_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(new RandomFeatureEntry(Placed.AYLYTHIAN_DARK_OAK, 0.25F)), Placed.AYLYTHIAN_MEGA_DARK_OAK));
		public static final ConfiguredFeature<?, ?> DEEP_CONIFEROUS_ROOF_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(new RandomFeatureEntry(Placed.AYLYTHIAN_DARK_OAK, 0.5F)), TreePlacedFeatures.MEGA_SPRUCE_CHECKED));
		public static final ConfiguredFeature<?, ?> FOREST_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(new RandomFeatureEntry(Placed.YMPE_TREE, 0.25F)), TreePlacedFeatures.DARK_OAK_CHECKED));
		public static final ConfiguredFeature<?, ?> DEEP_FOREST_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(new RandomFeatureEntry(Placed.YMPE_TREE, 0.25F), new RandomFeatureEntry(Placed.BIG_YMPE_TREE, 0.25F)), TreePlacedFeatures.DARK_OAK_CHECKED));
		public static final ConfiguredFeature<?, ?> CONIFEROUS_FOREST_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(new RandomFeatureEntry(Placed.YMPE_TREE, 0.25F)), TreePlacedFeatures.SPRUCE_CHECKED));
		public static final ConfiguredFeature<?, ?> DEEP_CONIFEROUS_FOREST_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(new RandomFeatureEntry(Placed.YMPE_TREE, 0.25F), new RandomFeatureEntry(Placed.BIG_YMPE_TREE, 0.25F)), TreePlacedFeatures.SPRUCE_CHECKED));
		public static final ConfiguredFeature<?, ?> OVERGROWTH_CLEARING_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(new RandomFeatureEntry(Placed.YMPE_TREE, 0.5F)), TreePlacedFeatures.SPRUCE_CHECKED));

		public static final PlacedFeature DEEP_ROOF_TREES_PLACED = DEEP_ROOF_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(8, 0.5f, 3)));
		public static final PlacedFeature DEEP_CONIFEROUS_ROOF_TREES_PLACED = DEEP_CONIFEROUS_ROOF_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(7, 0.5f, 2)));
		public static final PlacedFeature FOREST_TREES_PLACED = FOREST_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(3, 0.1f, 1)));
		public static final PlacedFeature DEEP_FOREST_TREES_PLACED = DEEP_FOREST_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(4, 0.25f, 2)));
		public static final PlacedFeature CONIFEROUS_FOREST_TREES_PLACED = CONIFEROUS_FOREST_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(5, 0.25f, 2)));
		public static final PlacedFeature DEEP_CONIFEROUS_FOREST_TREES_PLACED = DEEP_CONIFEROUS_FOREST_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(4, 0.25f, 2)));
		public static final PlacedFeature OVERGROWTH_CLEARING_TREES_PLACED = OVERGROWTH_CLEARING_TREES.withPlacement(VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.5f, 2)));

	}

	//public static final ConfiguredFeature<?, ?> CLEARING_FLOWERS = todo flower generators
	
	public static void init() {
		//register configured features statically, register placed features here
		Registry.register(Registry.FEATURE, new Identifier(Aylyth.MOD_ID, "spring"), SPRING_FEATURE);
		Registry.register(Registry.FEATURE, new Identifier(Aylyth.MOD_ID, "seep"), SEEP_FEATURE);
		Registry.register(Registry.FEATURE, new Identifier(Aylyth.MOD_ID, "bush"), BUSH_FEATURE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "aylythian_dark_oak"), Configured.AYLYTHIAN_DARK_OAK);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "aylythian_mega_dark_oak"), Configured.AYLYTHIAN_MEGA_DARK_OAK);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "ympe_tree"), Configured.YMPE_TREE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "big_ympe_tree"), Configured.BIG_YMPE_TREE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "bushes"), Configured.BUSHES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "oak_seep"), Configured.OAK_SEEP);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "dark_oak_seep"), Configured.DARK_OAK_SEEP);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "spruce_seep"), Configured.SPRUCE_SEEP);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "ympe_seep"), Configured.YMPE_SEEP);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "aylyth_weeds"), Configured.AYLYTH_WEEDS);


		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "deep_roof_trees"), Mixed.DEEP_ROOF_TREES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "deep_coniferous_roof_trees"), Mixed.DEEP_CONIFEROUS_ROOF_TREES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "forest_trees"), Mixed.FOREST_TREES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "deep_forest_trees"), Mixed.DEEP_FOREST_TREES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "coniferous_forest_trees"), Mixed.CONIFEROUS_FOREST_TREES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "deep_coniferous_forest_trees"), Mixed.DEEP_CONIFEROUS_FOREST_TREES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "overgrowth_clearing_trees"), Mixed.OVERGROWTH_CLEARING_TREES);
	//	Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "roof_trees"), Placed.ROOF_TREES);


		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "aylythian_dark_oak"), Placed.AYLYTHIAN_DARK_OAK);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "aylythian_mega_dark_oak"), Placed.AYLYTHIAN_MEGA_DARK_OAK);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "ympe_tree"), Placed.YMPE_TREE);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "big_ympe_tree"), Placed.BIG_YMPE_TREE);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "bushes"), Placed.BUSHES);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "oak_seep"), Placed.OAK_SEEP);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "dark_oak_seep"), Placed.DARK_OAK_SEEP);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "spruce_seep"), Placed.SPRUCE_SEEP);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "ympe_seep"), Placed.YMPE_SEEP);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "aylyth_weeds"), Placed.AYLYTH_WEEDS);


		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "deep_roof_trees"), Mixed.DEEP_ROOF_TREES_PLACED);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "deep_coniferous_roof_trees"), Mixed.DEEP_CONIFEROUS_ROOF_TREES_PLACED);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "forest_trees"), Mixed.FOREST_TREES_PLACED);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "deep_forest_trees"), Mixed.DEEP_FOREST_TREES_PLACED);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "coniferous_forest_trees"), Mixed.CONIFEROUS_FOREST_TREES_PLACED);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "deep_coniferous_forest_trees"), Mixed.DEEP_CONIFEROUS_FOREST_TREES_PLACED);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "overgrowth_clearing_trees"), Mixed.OVERGROWTH_CLEARING_TREES_PLACED);

		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Aylyth.MOD_ID, "spring"), Placed.SPRING); //special test - configured feature is not registered

		Registry.register(Registry.BIOME_SOURCE, new Identifier(Aylyth.MOD_ID, "aylyth_biome_provider"), AylythBiomeSource.CODEC);
		
		BiomeModification worldGen = BiomeModifications.create(new Identifier(Aylyth.MOD_ID, "world_features"));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(Biome.Category.FOREST, Biome.Category.TAIGA), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, Placed.OAK_SEEP));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(Biome.Category.FOREST, Biome.Category.TAIGA), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, Placed.SPRUCE_SEEP));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.categories(Biome.Category.FOREST, Biome.Category.TAIGA), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, Placed.DARK_OAK_SEEP));
	}

	private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
		return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(block)).withInAirFilter());
	}
}
