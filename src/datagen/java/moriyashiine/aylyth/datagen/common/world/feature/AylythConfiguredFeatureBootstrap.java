package moriyashiine.aylyth.datagen.common.world.feature;

import com.google.common.collect.ImmutableList;
import moriyashiine.aylyth.common.block.types.JackolanternMushroomBlock;
import moriyashiine.aylyth.common.block.types.LargeWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.types.SmallWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.StrewnLeavesBlock;
import moriyashiine.aylyth.common.world.gen.AylythFeatures;
import moriyashiine.aylyth.common.data.world.feature.AylythPlacedFeatures;
import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import moriyashiine.aylyth.common.world.gen.features.AllFeature;
import moriyashiine.aylyth.common.world.gen.features.GiantMushroomFeature;
import moriyashiine.aylyth.common.world.gen.features.HorizontalFacingFeature;
import moriyashiine.aylyth.common.world.gen.features.LeafPileFeature;
import moriyashiine.aylyth.common.world.gen.features.SeepFeature;
import moriyashiine.aylyth.common.world.gen.foliageplacers.GirasolFoliagePlacer;
import moriyashiine.aylyth.common.world.gen.foliageplacers.PomegranateFoliagePlacer;
import moriyashiine.aylyth.common.world.gen.foliageplacers.WrithewoodFoliagePlacer;
import moriyashiine.aylyth.common.world.gen.treedecorators.GrapeVineDecorator;
import moriyashiine.aylyth.common.world.gen.treedecorators.PlaceAroundTreeDecorator;
import moriyashiine.aylyth.common.world.gen.trunkplacers.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.RandomOffsetPlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import static moriyashiine.aylyth.common.data.world.feature.AylythConfiguredFeatures.*;

import java.util.List;

public final class AylythConfiguredFeatureBootstrap {
    private AylythConfiguredFeatureBootstrap() {}

    public static final BlockStateProvider YMPE_LOG_PROVIDER = new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(AylythBlocks.YMPE_LOG.getDefaultState(), 15).add(AylythBlocks.FRUIT_BEARING_YMPE_LOG.getDefaultState(), 1).build());

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        var placedFeatures = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        var shelfJackOlanternMushrooms = configuredFeatures.getOrThrow(SHELF_JACK_O_LANTERN_MUSHROOMS);
        var ghostcapMushroomPatches = configuredFeatures.getOrThrow(GHOSTCAP_MUSHROOM);
        var oakStrewnLeavesConfigured = configuredFeatures.getOrThrow(OAK_STREWN_LEAVES);
        var ympeStrewnLeavesConfigured = configuredFeatures.getOrThrow(YMPE_STREWN_LEAVES);
        var oakLeafPileConfigured = configuredFeatures.getOrThrow(OAK_LEAF_PILE);
        var ympeLeafPileConfigured = configuredFeatures.getOrThrow(YMPE_LEAF_PILE);
        var largeWoodyGrowthConfigured = configuredFeatures.getOrThrow(LARGE_WOODY_GROWTH);
        var smallWoodyGrowthConfigured = configuredFeatures.getOrThrow(SMALL_WOODY_GROWTH);
        var largeGiantJackOLanternMushroomWithPatch = configuredFeatures.getOrThrow(LARGE_GIANT_JACK_O_LANTERN_MUSHROOM_WITH_PATCH);

        var spruceChecked = placedFeatures.getOrThrow(TreePlacedFeatures.SPRUCE_CHECKED);
        var megaSpruceChecked = placedFeatures.getOrThrow(TreePlacedFeatures.MEGA_SPRUCE_CHECKED);
        var aylythianDarkOak = placedFeatures.getOrThrow(AylythPlacedFeatures.AYLYTHIAN_DARK_OAK);
        var aylythianMegaDarkOak = placedFeatures.getOrThrow(AylythPlacedFeatures.AYLYTHIAN_MEGA_DARK_OAK);
        var ympe = placedFeatures.getOrThrow(AylythPlacedFeatures.YMPE_TREE);
        var bigYmpe = placedFeatures.getOrThrow(AylythPlacedFeatures.BIG_YMPE_TREE);
        var writhewood = placedFeatures.getOrThrow(AylythPlacedFeatures.WRITHEWOOD_TREE);
        var antlerShootsWater = placedFeatures.getOrThrow(AylythPlacedFeatures.ANTLER_SHOOTS_WATER);
        var antlerShoots = placedFeatures.getOrThrow(AylythPlacedFeatures.ANTLER_SHOOTS);
        var largeWoodyGrowthWater = placedFeatures.getOrThrow(AylythPlacedFeatures.LARGE_WOODY_GROWTH_WATER);
        var smallWoodyGrowthWater = placedFeatures.getOrThrow(AylythPlacedFeatures.SMALL_WOODY_GROWTH_WATER);
        var woodyGrowthsWaterSelector = placedFeatures.getOrThrow(AylythPlacedFeatures.WOODY_GROWTHS_WATER_SELECTOR);
        var jackOLanternMushroomPatch = placedFeatures.getOrThrow(AylythPlacedFeatures.JACK_O_LANTERN_MUSHROOM_PATCH);
        var smallGiantJackOLanternMushroom = placedFeatures.getOrThrow(AylythPlacedFeatures.SMALL_GIANT_JACK__O_LANTERN_MUSHROOM);
        var largeGiantJackOLanternMushroom = placedFeatures.getOrThrow(AylythPlacedFeatures.LARGE_GIANT_JACK__O_LANTERN_MUSHROOM);

        ConfiguredFeatures.register(context, SMALL_GIANT_JACK_O_LANTERN_MUSHROOM, AylythFeatures.GIANT_MUSHROOM, new GiantMushroomFeature.GiantMushroomConfig(BlockStateProvider.of(AylythBlocks.JACK_O_LANTERN_MUSHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)), BlockStateProvider.of(AylythBlocks.JACK_O_LANTERN_MUSHROOM_STEM), ConstantIntProvider.create(2), UniformIntProvider.create(1, 3)));
        ConfiguredFeatures.register(context, LARGE_GIANT_JACK_O_LANTERN_MUSHROOM, AylythFeatures.GIANT_MUSHROOM, new GiantMushroomFeature.GiantMushroomConfig(BlockStateProvider.of(AylythBlocks.JACK_O_LANTERN_MUSHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)), BlockStateProvider.of(AylythBlocks.JACK_O_LANTERN_MUSHROOM_STEM), UniformIntProvider.create(3, 4), UniformIntProvider.create(5, 8)));

        ConfiguredFeatures.register(context, SPRING, AylythFeatures.SPRING_FEATURE, new SingleStateFeatureConfig(Blocks.WATER.getDefaultState()));
        ConfiguredFeatures.register(context, BUSHES, AylythFeatures.BUSH_FEATURE, FeatureConfig.DEFAULT);
        ConfiguredFeatures.register(context, OAK_LEAF_PILE, AylythFeatures.LEAF_PILE_FEATURE, new LeafPileFeature.LeafPileConfig(new RandomizedIntBlockStateProvider(BlockStateProvider.of(AylythBlocks.OAK_STREWN_LEAVES), StrewnLeavesBlock.LEAVES, UniformIntProvider.create(5, 7)), new RandomizedIntBlockStateProvider(BlockStateProvider.of(AylythBlocks.OAK_STREWN_LEAVES), StrewnLeavesBlock.LEAVES, UniformIntProvider.create(0, 5))));
        ConfiguredFeatures.register(context, YMPE_LEAF_PILE, AylythFeatures.LEAF_PILE_FEATURE, new LeafPileFeature.LeafPileConfig(new RandomizedIntBlockStateProvider(BlockStateProvider.of(AylythBlocks.YMPE_STREWN_LEAVES), StrewnLeavesBlock.LEAVES, UniformIntProvider.create(5, 7)), new RandomizedIntBlockStateProvider(BlockStateProvider.of(AylythBlocks.YMPE_STREWN_LEAVES), StrewnLeavesBlock.LEAVES, UniformIntProvider.create(0, 5))));
        ConfiguredFeatures.register(context, OAK_STREWN_LEAVES, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.OAK_STREWN_LEAVES)));
        ConfiguredFeatures.register(context, YMPE_STREWN_LEAVES, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.YMPE_STREWN_LEAVES)));
        ConfiguredFeatures.register(context, AYLYTH_WEEDS, Feature.FLOWER, createRandomPatchFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(AylythBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(AylythBlocks.GRIPWEED.getDefaultState(), 2).build()), 64));
        ConfiguredFeatures.register(context, MARIGOLDS, Feature.FLOWER, createRandomPatchFeatureConfig(BlockStateProvider.of(AylythBlocks.MARIGOLD), 64));
        ConfiguredFeatures.register(context, JACK_O_LANTERN_MUSHROOM, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new RandomizedIntBlockStateProvider(BlockStateProvider.of(AylythBlocks.JACK_O_LANTERN_MUSHROOM), JackolanternMushroomBlock.STAGE, UniformIntProvider.create(1, 3))));
        ConfiguredFeatures.register(context, SHELF_JACK_O_LANTERN_MUSHROOMS, AylythFeatures.HORIZONTAL_FACING_FEATURE, new HorizontalFacingFeature.HorizontalFacingBlockFeatureConfig(AylythBlocks.SHELF_JACK_O_LANTERN_MUSHROOM, AylythBlockTags.JACK_O_LANTERN_GENERATE_ON));
        ConfiguredFeatures.register(context, GHOSTCAP_MUSHROOM, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.GHOSTCAP_MUSHROOM)));
        ConfiguredFeatures.register(context, SMALL_WOODY_GROWTH, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.SMALL_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true))));
        ConfiguredFeatures.register(context, LARGE_WOODY_GROWTH, AylythFeatures.DOUBLE_BLOCK_FEATURE, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.LARGE_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true))));
        ConfiguredFeatures.register(context, SMALL_WOODY_GROWTH_WATER, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.SMALL_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true).with(Properties.WATERLOGGED, true))));
        ConfiguredFeatures.register(context, LARGE_WOODY_GROWTH_WATER, AylythFeatures.DOUBLE_BLOCK_FEATURE, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.LARGE_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true).with(Properties.WATERLOGGED, true))));
        ConfiguredFeatures.register(context, ANTLER_SHOOTS, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.ANTLER_SHOOTS)));
        ConfiguredFeatures.register(context, ANTLER_SHOOTS_WATER, Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.ANTLER_SHOOTS.getDefaultState().with(Properties.WATERLOGGED, true))));

        ConfiguredFeatures.register(context, OAK_SEEP, AylythFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.OAK_LOG.getDefaultState(), AylythBlocks.OAK_SEEP.getDefaultState(), AylythBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));
        ConfiguredFeatures.register(context, SPRUCE_SEEP, AylythFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), AylythBlocks.SPRUCE_SEEP.getDefaultState(), AylythBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));
        ConfiguredFeatures.register(context, DARK_OAK_SEEP, AylythFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.DARK_OAK_LOG.getDefaultState(), AylythBlocks.DARK_OAK_SEEP.getDefaultState(), AylythBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));
        ConfiguredFeatures.register(context, YMPE_SEEP, AylythFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(AylythBlocks.YMPE_LOG.getDefaultState(), AylythBlocks.YMPE_SEEP.getDefaultState(), AylythBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));

        ConfiguredFeatures.register(context, AYLYTHIAN_DARK_OAK, Feature.TREE, new TreeFeatureConfig.Builder(
                SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()),
                new AylthianTrunkPlacer(12, 2, 5),
                SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()),
                new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
                new TwoLayersFeatureSize(1, 1, 2))
                .ignoreVines()
                .decorators(ImmutableList.of(
                        new GrapeVineDecorator(UniformIntProvider.create(0, 9), 1),
                        new PlaceAroundTreeDecorator(RegistryEntryList.of(
                                RegistryEntry.of(new PlacedFeature(oakStrewnLeavesConfigured, List.of(
                                                CountPlacementModifier.of(UniformIntProvider.create(4, 8)),
                                                RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 6)),
                                                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                                PlacedFeatures.wouldSurvive(AylythBlocks.OAK_STREWN_LEAVES)
                                ))),
                                RegistryEntry.of(new PlacedFeature(oakLeafPileConfigured, List.of(
                                                CountPlacementModifier.of(UniformIntProvider.create(0, 2)),
                                                RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 6)),
                                                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                                PlacedFeatures.wouldSurvive(AylythBlocks.OAK_STREWN_LEAVES)
                                )))
                        ))
                )).build());
        ConfiguredFeatures.register(context, AYLYTHIAN_MEGA_DARK_OAK, Feature.TREE, new TreeFeatureConfig.Builder(
                SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()),
                new AylthianTrunkPlacer(18, 6, 7),
                SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()),
                new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
                new TwoLayersFeatureSize(1, 1, 2))
                .ignoreVines()
                .decorators(ImmutableList.of(
                        new GrapeVineDecorator(UniformIntProvider.create(0, 9), 1),
                        new PlaceAroundTreeDecorator(RegistryEntryList.of(
                                RegistryEntry.of(new PlacedFeature(oakStrewnLeavesConfigured, List.of(
                                        CountPlacementModifier.of(UniformIntProvider.create(4, 8)),
                                        RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 6)),
                                        PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                        PlacedFeatures.wouldSurvive(AylythBlocks.OAK_STREWN_LEAVES)
                                ))),
                                RegistryEntry.of(new PlacedFeature(oakLeafPileConfigured, List.of(
                                        CountPlacementModifier.of(UniformIntProvider.create(0, 2)),
                                        RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 6)),
                                        PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                        PlacedFeatures.wouldSurvive(AylythBlocks.OAK_STREWN_LEAVES)
                                )))
                        ))
                )).build());
        ConfiguredFeatures.register(context, YMPE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(
                YMPE_LOG_PROVIDER,
                new YmpeTrunkPlacer(3, 1, 4),
                SimpleBlockStateProvider.of(AylythBlocks.YMPE_LEAVES.getDefaultState()),
                new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .decorators(List.of(
                        new PlaceAroundTreeDecorator(RegistryEntryList.of(
                                RegistryEntry.of(new PlacedFeature(ympeStrewnLeavesConfigured, List.of(
                                        CountPlacementModifier.of(UniformIntProvider.create(4, 8)),
                                        RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 6)),
                                        PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                        PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_STREWN_LEAVES)
                                ))),
                                RegistryEntry.of(new PlacedFeature(ympeLeafPileConfigured, List.of(
                                        CountPlacementModifier.of(UniformIntProvider.create(0, 2)),
                                        RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 6)),
                                        PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                        PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_STREWN_LEAVES)
                                )))
                        ))
                ))
                .build());
        ConfiguredFeatures.register(context, BIG_YMPE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(
                YMPE_LOG_PROVIDER,
                new BigYmpeTrunkPlacer(6, 3, 4),
                SimpleBlockStateProvider.of(AylythBlocks.YMPE_LEAVES.getDefaultState()),
                new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),
                new TwoLayersFeatureSize(1, 1, 1))
                .ignoreVines()
                .decorators(List.of(
                        new PlaceAroundTreeDecorator(RegistryEntryList.of(
                                RegistryEntry.of(new PlacedFeature(ympeStrewnLeavesConfigured, List.of(
                                        CountPlacementModifier.of(UniformIntProvider.create(4, 8)),
                                        RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 6)),
                                        PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                        PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_STREWN_LEAVES)
                                ))),
                                RegistryEntry.of(new PlacedFeature(ympeLeafPileConfigured, List.of(
                                        CountPlacementModifier.of(UniformIntProvider.create(0, 2)),
                                        RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 6)),
                                        PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                        PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_STREWN_LEAVES)
                                )))
                        ))
                ))
                .build());
        ConfiguredFeatures.register(context, POMEGRANATE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(AylythBlocks.POMEGRANATE_LOG), new PomegranateTrunkPlacer(5, 0, 0), SimpleBlockStateProvider.of(AylythBlocks.POMEGRANATE_LEAVES.getDefaultState().with(Properties.PERSISTENT, false)), new PomegranateFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
        ConfiguredFeatures.register(context, WRITHEWOOD_TREE, Feature.TREE, new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(AylythBlocks.WRITHEWOOD_LOG), new WrithewoodTrunkPlacer(6, 4, 14), SimpleBlockStateProvider.of(AylythBlocks.WRITHEWOOD_LEAVES), new WrithewoodFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1)), new TwoLayersFeatureSize(2, 1, 1)).ignoreVines().build());
        ConfiguredFeatures.register(context, GIRASOL_TREE, Feature.TREE, new TreeFeatureConfig.Builder(
                SimpleBlockStateProvider.of(AylythBlocks.SEEPING_WOOD),
                new GirasolTrunkPlacer(6, 1, 3, AylythBlocks.SEEPING_WOOD_SEEP.getDefaultState(), 6),
                SimpleBlockStateProvider.of(AylythBlocks.YMPE_LEAVES),
                new GirasolFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1)),
                new TwoLayersFeatureSize(2, 1, 1))
                .ignoreVines()
                .decorators(ImmutableList.of(
                        new GrapeVineDecorator(UniformIntProvider.create(0, 9), 3),
                        new PlaceAroundTreeDecorator(
                                RegistryEntryList.of(
                                        RegistryEntry.of(new PlacedFeature(largeWoodyGrowthConfigured, List.of(
                                                CountPlacementModifier.of(UniformIntProvider.create(0, 6)),
                                                RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 8)),
                                                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                                PlacedFeatures.wouldSurvive(AylythBlocks.LARGE_WOODY_GROWTH)))),
                                        RegistryEntry.of(new PlacedFeature(smallWoodyGrowthConfigured, List.of(
                                                CountPlacementModifier.of(UniformIntProvider.create(0, 6)),
                                                RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 8)),
                                                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                                PlacedFeatures.wouldSurvive(AylythBlocks.SMALL_WOODY_GROWTH)))),
                                        RegistryEntry.of(new PlacedFeature(oakStrewnLeavesConfigured, List.of(
                                                CountPlacementModifier.of(UniformIntProvider.create(4, 20)),
                                                RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 8)),
                                                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                                PlacedFeatures.wouldSurvive(AylythBlocks.OAK_STREWN_LEAVES)))),
                                        RegistryEntry.of(new PlacedFeature(ympeStrewnLeavesConfigured, List.of(
                                                CountPlacementModifier.of(UniformIntProvider.create(4, 20)),
                                                RandomOffsetPlacementModifier.horizontally(UniformIntProvider.create(2, 8)),
                                                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                                                PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_STREWN_LEAVES))))
                                ))
                )).build());

        ConfiguredFeatures.register(context, DEEP_ROOF_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(aylythianDarkOak, 0.25F)), aylythianMegaDarkOak));
        ConfiguredFeatures.register(context, CONIFEROUS_DEEP_ROOF_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(aylythianMegaDarkOak, 0.65F)), megaSpruceChecked));
        ConfiguredFeatures.register(context, COPSE_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(ympe, 0.25F)), aylythianDarkOak));
        ConfiguredFeatures.register(context, DEEPWOOD_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(ympe, 0.25F), new RandomFeatureEntry(bigYmpe, 0.25F)), aylythianDarkOak));
        ConfiguredFeatures.register(context, CONIFEROUS_COPSE_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(ympe, 0.25F)), spruceChecked));
        ConfiguredFeatures.register(context, CONIFEROUS_DEEPWOOD_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(ympe, 0.15F), new RandomFeatureEntry(bigYmpe, 0.15F)), spruceChecked));
        ConfiguredFeatures.register(context, OVERGROWTH_CLEARING_TREES,Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(ympe, 0.5F)), spruceChecked));
        ConfiguredFeatures.register(context, MIRE_WATER_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(), writhewood));
        ConfiguredFeatures.register(context, MIRE_LAND_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(spruceChecked, 0.25f), new RandomFeatureEntry(megaSpruceChecked, 0.25f)), writhewood));
        ConfiguredFeatures.register(context, LARGE_GIANT_JACK_O_LANTERN_MUSHROOM_WITH_PATCH, AylythFeatures.ALL, new AllFeature.AllFeatureConfig(RegistryEntryList.of(largeGiantJackOLanternMushroom, jackOLanternMushroomPatch)));
        ConfiguredFeatures.register(context, GIANT_JACK_O_LANTERN_MUSHROOMS, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(smallGiantJackOLanternMushroom, 0.5f)), PlacedFeatures.createEntry(largeGiantJackOLanternMushroomWithPatch)));

        ConfiguredFeatures.register(context, RED_MUSHROOM_PATCHES, Feature.RANDOM_PATCH, AylythConfiguredFeatureBootstrap.createRandomPatchFeatureConfig(BlockStateProvider.of(Blocks.RED_MUSHROOM), 96));
        ConfiguredFeatures.register(context, BROWN_MUSHROOM_PATCHES, Feature.RANDOM_PATCH, AylythConfiguredFeatureBootstrap.createRandomPatchFeatureConfig(BlockStateProvider.of(Blocks.BROWN_MUSHROOM), 96));
        ConfiguredFeatures.register(context, GLOW_LICHEN, Feature.RANDOM_PATCH, AylythConfiguredFeatureBootstrap.createRandomPatchFeatureConfig(BlockStateProvider.of(Blocks.GLOW_LICHEN), 32));
        ConfiguredFeatures.register(context, SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(shelfJackOlanternMushrooms)));
        ConfiguredFeatures.register(context, GHOSTCAP_MUSHROOM_PATCHES,Feature.VEGETATION_PATCH, new VegetationPatchFeatureConfig(AylythBlockTags.GHOSTCAP_REPLACEABLE, BlockStateProvider.of(Blocks.GRASS_BLOCK), PlacedFeatures.createEntry(ghostcapMushroomPatches), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0, 2, 0.2f, ConstantIntProvider.create(3), 0));
        ConfiguredFeatures.register(context, WOODY_GROWTH_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(largeWoodyGrowthConfigured), 0.25F)), PlacedFeatures.createEntry(smallWoodyGrowthConfigured)), List.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MUD, Blocks.SOUL_SOIL), 8));
        ConfiguredFeatures.register(context, WOODY_GROWTH_BOWELS_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(largeWoodyGrowthConfigured), 0.25F)), PlacedFeatures.createEntry(smallWoodyGrowthConfigured)), List.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MUD, Blocks.SOUL_SOIL), 8));
        ConfiguredFeatures.register(context, STREWN_LEAVES_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.YMPE_STREWN_LEAVES))), 0.25F)), PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.OAK_STREWN_LEAVES))))));
        ConfiguredFeatures.register(context, ANTLER_SHOOTS_WATER_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(32, antlerShootsWater));
        ConfiguredFeatures.register(context, ANTLER_SHOOTS_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(32, antlerShoots));
        ConfiguredFeatures.register(context, WOODY_GROWTHS_WATER_SELECTOR, Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(largeWoodyGrowthWater, 0.25f)), smallWoodyGrowthWater));

        ConfiguredFeatures.register(context, WOODY_GROWTH_WATER_PATCH, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(4, woodyGrowthsWaterSelector));
        ConfiguredFeatures.register(context, TWISTED_YMPE_GROWTH, AylythFeatures.ALL, new AllFeature.AllFeatureConfig(
                RegistryEntryList.of(
                        PlacedFeatures.createEntry(AylythFeatures.DOUBLE_BLOCK_FEATURE, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.LARGE_WOODY_GROWTH.getDefaultState().with(LargeWoodyGrowthBlock.NATURAL, true)))),
                        PlacedFeatures.createEntry(RegistryEntry.of(new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(4, 1, 1, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(AylythBlocks.SMALL_WOODY_GROWTH.getDefaultState().with(SmallWoodyGrowthBlock.NATURAL, true))), PlacedFeatures.isAir(), PlacedFeatures.wouldSurvive(AylythBlocks.SMALL_WOODY_GROWTH))))))
                )));
    }

    static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }
}
