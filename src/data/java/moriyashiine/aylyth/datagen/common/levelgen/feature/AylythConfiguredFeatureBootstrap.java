package moriyashiine.aylyth.datagen.common.levelgen.feature;

import com.google.common.collect.ImmutableList;
import moriyashiine.aylyth.common.block.SmallWoodyGrowthBlock;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModFeatures;
import moriyashiine.aylyth.common.data.levelgen.AylythPlacements;
import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import moriyashiine.aylyth.common.world.generator.feature.HorizontalFacingFeature;
import moriyashiine.aylyth.common.world.generator.feature.LeafPileFeature;
import moriyashiine.aylyth.common.world.generator.feature.SeepFeature;
import moriyashiine.aylyth.common.world.generator.feature.StrewnLeavesFeature;
import moriyashiine.aylyth.common.world.generator.foliageplacer.GirasolFoliagePlacer;
import moriyashiine.aylyth.common.world.generator.foliageplacer.PomegranateFoliagePlacer;
import moriyashiine.aylyth.common.world.generator.foliageplacer.WrithewoodFoliagePlacer;
import moriyashiine.aylyth.common.world.generator.treedecorator.GrapeVineDecorator;
import moriyashiine.aylyth.common.world.generator.treedecorator.RangedTreeDecorator;
import moriyashiine.aylyth.common.world.generator.trunkplacer.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import static moriyashiine.aylyth.common.data.levelgen.AylythConfiguredFeautres.*;

import java.util.List;

public final class AylythConfiguredFeatureBootstrap {
    private AylythConfiguredFeatureBootstrap() {}

    public static final BlockStateProvider YMPE_LOG_PROVIDER = new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.YMPE_LOG.getDefaultState(), 15).add(ModBlocks.FRUIT_BEARING_YMPE_LOG.getDefaultState(), 1).build());

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        var features = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        var placements = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        var aylythianDarkOak = features.getOrThrow(AYLYTHIAN_DARK_OAK);
        var aylythianMegaDarkOak = features.getOrThrow(AYLYTHIAN_MEGA_DARK_OAK);
        var ympe = features.getOrThrow(YMPE_TREE);
        var bigYmpe = features.getOrThrow(BIG_YMPE_TREE);
        var writhewood = features.getOrThrow(WRITHEWOOD_TREE);
        var shelfJackOlanternMushrooms = features.getOrThrow(SHELF_JACK_O_LANTERN_MUSHROOMS);
        var ghostcapMushroomPatches = features.getOrThrow(GHOSTCAP_MUSHROOM);
        var largeWoodyGrowthConfigured = features.getOrThrow(LARGE_WOODY_GROWTH);
        var smallWoodyGrowthConfigured = features.getOrThrow(SMALL_WOODY_GROWTH);

        var spruceChecked = placements.getOrThrow(TreePlacedFeatures.SPRUCE_CHECKED);
        var megaSpruceChecked = placements.getOrThrow(TreePlacedFeatures.MEGA_SPRUCE_CHECKED);
        var darkOakChecked = placements.getOrThrow(TreePlacedFeatures.DARK_OAK_CHECKED);
        var antlerShootsWater = placements.getOrThrow(AylythPlacements.ANTLER_SHOOTS_WATER);
        var antlerShoots = placements.getOrThrow(AylythPlacements.ANTLER_SHOOTS);
        var largeWoodyGrowthWater = placements.getOrThrow(AylythPlacements.LARGE_WOODY_GROWTH_WATER);
        var smallWoodyGrowthWater = placements.getOrThrow(AylythPlacements.SMALL_WOODY_GROWTH_WATER);
        var woodyGrowthsWaterSelector = placements.getOrThrow(AylythPlacements.WOODY_GROWTHS_WATER_SELECTOR_PLACED);

        ConfiguredFeatures.register(context, AYLYTHIAN_DARK_OAK, Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(), SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().decorators(ImmutableList.of(new GrapeVineDecorator(UniformIntProvider.create(0, 9), 1))).build());
        ConfiguredFeatures.register(context, AYLYTHIAN_MEGA_DARK_OAK, Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(18, 6, 7), SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new GrapeVineDecorator(UniformIntProvider.create(0, 9), 1))).ignoreVines().build());
        ConfiguredFeatures.register(context, YMPE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new YmpeTrunkPlacer(), SimpleBlockStateProvider.of(ModBlocks.YMPE_LEAVES.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
        ConfiguredFeatures.register(context, BIG_YMPE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new BigYmpeTrunkPlacer(), SimpleBlockStateProvider.of(ModBlocks.YMPE_LEAVES.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
        ConfiguredFeatures.register(context, POMEGRANATE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(ModBlocks.POMEGRANATE_LOG), new PomegranateTrunkPlacer(5, 0, 0), SimpleBlockStateProvider.of(ModBlocks.POMEGRANATE_LEAVES.getDefaultState().with(Properties.PERSISTENT, false)), new PomegranateFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
        ConfiguredFeatures.register(context, WRITHEWOOD_TREE, Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(ModBlocks.WRITHEWOOD_LOG), new WrithewoodTrunkPlacer(6, 4, 14), SimpleBlockStateProvider.of(ModBlocks.WRITHEWOOD_LEAVES), new WrithewoodFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1)), new TwoLayersFeatureSize(2, 1, 1)).ignoreVines().build());
        ConfiguredFeatures.register(context, GIRASOL_TREE, Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(ModBlocks.SEEPING_WOOD), new GirasolTrunkPlacer(6, 1, 3, ModBlocks.SEEPING_WOOD_SEEP.getDefaultState(), 6), SimpleBlockStateProvider.of(ModBlocks.YMPE_LEAVES), new GirasolFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1)), new TwoLayersFeatureSize(2, 1, 1)).ignoreVines().decorators(ImmutableList.of(new GrapeVineDecorator(UniformIntProvider.create(0, 9), 3), new RangedTreeDecorator(List.of(ModBlocks.SMALL_WOODY_GROWTH.getDefaultState(), ModBlocks.LARGE_WOODY_GROWTH.getDefaultState()), 12, 4), new RangedTreeDecorator(List.of(ModBlocks.OAK_STREWN_LEAVES.getDefaultState(), ModBlocks.YMPE_STREWN_LEAVES.getDefaultState()), 32, 6))).build());

        ConfiguredFeatures.register(context, SPRING, ModFeatures.SPRING_FEATURE, new SingleStateFeatureConfig(Blocks.WATER.getDefaultState()));
        ConfiguredFeatures.register(context, BUSHES, ModFeatures.BUSH_FEATURE, FeatureConfig.DEFAULT);
        ConfiguredFeatures.register(context, OAK_LEAF_PILE, ModFeatures.LEAF_PILE_FEATURE, new LeafPileFeature.LeafPileConfig(Blocks.DARK_OAK_LEAVES, ModBlocks.OAK_STREWN_LEAVES));
        ConfiguredFeatures.register(context, YMPE_LEAF_PILE, ModFeatures.LEAF_PILE_FEATURE, new LeafPileFeature.LeafPileConfig(ModBlocks.YMPE_LEAVES, ModBlocks.YMPE_STREWN_LEAVES));
        ConfiguredFeatures.register(context, OAK_STREWN_LEAVES, ModFeatures.STREWN_LEAVES_FEATURE, new StrewnLeavesFeature.StrewnLeavesConfig(Blocks.DARK_OAK_LEAVES, ModBlocks.OAK_STREWN_LEAVES.getDefaultState()));
        ConfiguredFeatures.register(context, YMPE_STREWN_LEAVES, ModFeatures.STREWN_LEAVES_FEATURE, new StrewnLeavesFeature.StrewnLeavesConfig(ModBlocks.YMPE_LEAVES, ModBlocks.YMPE_STREWN_LEAVES.getDefaultState()));
        ConfiguredFeatures.register(context, AYLYTH_WEEDS, Feature.FLOWER, createRandomPatchFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(ModBlocks.GRIPWEED.getDefaultState(), 2).build()), 64));
        ConfiguredFeatures.register(context, MARIGOLDS, Feature.FLOWER, createRandomPatchFeatureConfig(BlockStateProvider.of(ModBlocks.MARIGOLD), 64));
        ConfiguredFeatures.register(context, SHELF_JACK_O_LANTERN_MUSHROOMS, ModFeatures.HORIZONTAL_FACING_FEATURE, new HorizontalFacingFeature.HorizontalFacingBlockFeatureConfig(ModBlocks.SHELF_JACK_O_LANTERN_MUSHROOM, AylythBlockTags.JACK_O_LANTERN_GENERATE_ON));
        ConfiguredFeatures.register(context, GHOSTCAP_MUSHROOM, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.GHOSTCAP_MUSHROOM)));
        ConfiguredFeatures.register(context, SMALL_WOODY_GROWTH, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SMALL_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true))));
        ConfiguredFeatures.register(context, LARGE_WOODY_GROWTH, ModFeatures.DOUBLE_BLOCK_FEATURE, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.LARGE_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true))));
        ConfiguredFeatures.register(context, SMALL_WOODY_GROWTH_WATER, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SMALL_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true).with(Properties.WATERLOGGED, true))));
        ConfiguredFeatures.register(context, LARGE_WOODY_GROWTH_WATER, ModFeatures.DOUBLE_BLOCK_FEATURE, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.LARGE_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true).with(Properties.WATERLOGGED, true))));
        ConfiguredFeatures.register(context, ANTLER_SHOOTS, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.ANTLER_SHOOTS)));
        ConfiguredFeatures.register(context, ANTLER_SHOOTS_WATER, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.ANTLER_SHOOTS.getDefaultState().with(Properties.WATERLOGGED, true))));

        ConfiguredFeatures.register(context, OAK_SEEP, ModFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.OAK_LOG.getDefaultState(), ModBlocks.OAK_SEEP.getDefaultState(), ModBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));
        ConfiguredFeatures.register(context, SPRUCE_SEEP, ModFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), ModBlocks.SPRUCE_SEEP.getDefaultState(), ModBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));
        ConfiguredFeatures.register(context, DARK_OAK_SEEP, ModFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.DARK_OAK_LOG.getDefaultState(), ModBlocks.DARK_OAK_SEEP.getDefaultState(), ModBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));
        ConfiguredFeatures.register(context, YMPE_SEEP, ModFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(ModBlocks.YMPE_LOG.getDefaultState(), ModBlocks.YMPE_SEEP.getDefaultState(), ModBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));

        ConfiguredFeatures.register(context, DEEP_ROOF_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(aylythianDarkOak), 0.25F)), PlacedFeatures.createEntry(aylythianMegaDarkOak)));
        ConfiguredFeatures.register(context, CONIFEROUS_DEEP_ROOF_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(aylythianMegaDarkOak), 0.15F)), megaSpruceChecked));
        ConfiguredFeatures.register(context, COPSE_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ympe), 0.25F)), darkOakChecked));
        ConfiguredFeatures.register(context, DEEPWOOD_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ympe), 0.25F), new RandomFeatureEntry(PlacedFeatures.createEntry(bigYmpe), 0.25F)), darkOakChecked));
        ConfiguredFeatures.register(context, CONIFEROUS_COPSE_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ympe), 0.25F)), spruceChecked));
        ConfiguredFeatures.register(context, CONIFEROUS_DEEPWOOD_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ympe), 0.15F), new RandomFeatureEntry(PlacedFeatures.createEntry(bigYmpe), 0.15F)), spruceChecked));
        ConfiguredFeatures.register(context, OVERGROWTH_CLEARING_TREES,Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ympe), 0.5F)), spruceChecked));
        ConfiguredFeatures.register(context, MIRE_WATER_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(), PlacedFeatures.createEntry(writhewood)));
        ConfiguredFeatures.register(context, MIRE_LAND_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(spruceChecked, 0.25f), new RandomFeatureEntry(megaSpruceChecked, 0.25f)), PlacedFeatures.createEntry(writhewood)));

        ConfiguredFeatures.register(context, RED_MUSHROOM_PATCHES, Feature.RANDOM_PATCH, AylythConfiguredFeatureBootstrap.createRandomPatchFeatureConfig(BlockStateProvider.of(Blocks.RED_MUSHROOM), 96));
        ConfiguredFeatures.register(context, BROWN_MUSHROOM_PATCHES, Feature.RANDOM_PATCH, AylythConfiguredFeatureBootstrap.createRandomPatchFeatureConfig(BlockStateProvider.of(Blocks.BROWN_MUSHROOM), 96));
        ConfiguredFeatures.register(context, GLOW_LICHEN, Feature.RANDOM_PATCH, AylythConfiguredFeatureBootstrap.createRandomPatchFeatureConfig(BlockStateProvider.of(Blocks.GLOW_LICHEN), 32));
        ConfiguredFeatures.register(context, SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(shelfJackOlanternMushrooms)));
        ConfiguredFeatures.register(context, GHOSTCAP_MUSHROOM_PATCHES,Feature.VEGETATION_PATCH, new VegetationPatchFeatureConfig(AylythBlockTags.GHOSTCAP_REPLACEABLE, BlockStateProvider.of(Blocks.GRASS_BLOCK), PlacedFeatures.createEntry(ghostcapMushroomPatches), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0, 2, 0.2f, ConstantIntProvider.create(3), 0));
        ConfiguredFeatures.register(context, WOODY_GROWTH_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(largeWoodyGrowthConfigured), 0.25F)), PlacedFeatures.createEntry(smallWoodyGrowthConfigured)), List.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MUD, Blocks.SOUL_SOIL), 8));
        ConfiguredFeatures.register(context, WOODY_GROWTH_BOWELS_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(largeWoodyGrowthConfigured), 0.25F)), PlacedFeatures.createEntry(smallWoodyGrowthConfigured)), List.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MUD, Blocks.SOUL_SOIL), 8));
        ConfiguredFeatures.register(context, STREWN_LEAVES_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.YMPE_STREWN_LEAVES))), 0.25F)), PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.OAK_STREWN_LEAVES))))));
        ConfiguredFeatures.register(context, ANTLER_SHOOTS_WATER_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(32, antlerShootsWater));
        ConfiguredFeatures.register(context, ANTLER_SHOOTS_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(32, antlerShoots));
        ConfiguredFeatures.register(context, WOODY_GROWTHS_WATER_SELECTOR, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(largeWoodyGrowthWater, 0.25f)), smallWoodyGrowthWater));

        ConfiguredFeatures.register(context, WOODY_GROWTH_WATER_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(4, woodyGrowthsWaterSelector));
    }

    static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }
}
