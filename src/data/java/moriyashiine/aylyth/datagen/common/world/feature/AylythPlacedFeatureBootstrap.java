package moriyashiine.aylyth.datagen.common.world.feature;

import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.data.world.feature.AylythConfiguredFeatures;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import static moriyashiine.aylyth.common.data.world.feature.AylythPlacedFeatures.*;

import java.util.List;

public final class AylythPlacedFeatureBootstrap {
    private AylythPlacedFeatureBootstrap() {}

    private static final PlacementModifier NOT_IN_SURFACE_WATER_MODIFIER = SurfaceWaterDepthFilterPlacementModifier.of(0);

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var features = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        var aylythianDarkOak = features.getOrThrow(AylythConfiguredFeatures.AYLYTHIAN_DARK_OAK);
        var aylythianMegaDarkOak = features.getOrThrow(AylythConfiguredFeatures.AYLYTHIAN_MEGA_DARK_OAK);
        var ympe = features.getOrThrow(AylythConfiguredFeatures.YMPE_TREE);
        var bigYmpe = features.getOrThrow(AylythConfiguredFeatures.BIG_YMPE_TREE);
        var pomegranate = features.getOrThrow(AylythConfiguredFeatures.POMEGRANATE_TREE);
        var writhewood = features.getOrThrow(AylythConfiguredFeatures.WRITHEWOOD_TREE);
        var spring = features.getOrThrow(AylythConfiguredFeatures.SPRING);
        var bushes = features.getOrThrow(AylythConfiguredFeatures.BUSHES);
        var oakLeafPile = features.getOrThrow(AylythConfiguredFeatures.OAK_LEAF_PILE);
        var ympeLeafPile = features.getOrThrow(AylythConfiguredFeatures.YMPE_LEAF_PILE);
        var oakStrewnLeaves = features.getOrThrow(AylythConfiguredFeatures.OAK_STREWN_LEAVES);
        var ympeStrewnLeaves = features.getOrThrow(AylythConfiguredFeatures.YMPE_STREWN_LEAVES);
        var aylythWeeds = features.getOrThrow(AylythConfiguredFeatures.AYLYTH_WEEDS);
        var marigolds = features.getOrThrow(AylythConfiguredFeatures.MARIGOLDS);
        var shelfJackOlanternMushrooms = features.getOrThrow(AylythConfiguredFeatures.SHELF_JACK_O_LANTERN_MUSHROOMS);
        var antlerShootsWater = features.getOrThrow(AylythConfiguredFeatures.ANTLER_SHOOTS_WATER);
        var antlerShoots = features.getOrThrow(AylythConfiguredFeatures.ANTLER_SHOOTS);
        var smallWoodyGrowthWater = features.getOrThrow(AylythConfiguredFeatures.SMALL_WOODY_GROWTH_WATER);
        var largeWoodyGrowthWater = features.getOrThrow(AylythConfiguredFeatures.LARGE_WOODY_GROWTH_WATER);
        var oakSeep = features.getOrThrow(AylythConfiguredFeatures.OAK_SEEP);
        var spruceSeep = features.getOrThrow(AylythConfiguredFeatures.SPRUCE_SEEP);
        var darkOakSeep = features.getOrThrow(AylythConfiguredFeatures.DARK_OAK_SEEP);
        var ympeSeep = features.getOrThrow(AylythConfiguredFeatures.YMPE_SEEP);
        var deepRoofTrees = features.getOrThrow(AylythConfiguredFeatures.DEEP_ROOF_TREES);
        var coniferousDeepRoofTrees = features.getOrThrow(AylythConfiguredFeatures.CONIFEROUS_DEEP_ROOF_TREES);
        var copseTrees = features.getOrThrow(AylythConfiguredFeatures.COPSE_TREES);
        var deepWoodTrees = features.getOrThrow(AylythConfiguredFeatures.DEEPWOOD_TREES);
        var coniferousCopseTrees = features.getOrThrow(AylythConfiguredFeatures.CONIFEROUS_COPSE_TREES);
        var coniferousDeepWoodTrees = features.getOrThrow(AylythConfiguredFeatures.CONIFEROUS_DEEPWOOD_TREES);
        var overgrowthClearingTrees = features.getOrThrow(AylythConfiguredFeatures.OVERGROWTH_CLEARING_TREES);
        var mireWaterTrees = features.getOrThrow(AylythConfiguredFeatures.MIRE_WATER_TREES);
        var mireLandTrees = features.getOrThrow(AylythConfiguredFeatures.MIRE_LAND_TREES);
        var redMushroomPatches = features.getOrThrow(AylythConfiguredFeatures.RED_MUSHROOM_PATCHES);
        var brownMushroomPatches = features.getOrThrow(AylythConfiguredFeatures.BROWN_MUSHROOM_PATCHES);
        var glowLichen = features.getOrThrow(AylythConfiguredFeatures.GLOW_LICHEN);
        var shelfJackOLanternMushroomPatches = features.getOrThrow(AylythConfiguredFeatures.SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES);
        var ghostcapMushroomPatches = features.getOrThrow(AylythConfiguredFeatures.GHOSTCAP_MUSHROOM_PATCHES);
        var pomegranateTree = features.getOrThrow(AylythConfiguredFeatures.POMEGRANATE_TREE);
        var woodyGrowthPatch = features.getOrThrow(AylythConfiguredFeatures.WOODY_GROWTH_PATCH);
        var woodyGrowthBowelsPatch = features.getOrThrow(AylythConfiguredFeatures.WOODY_GROWTH_BOWELS_PATCH);
        var strewnLeavesPatch = features.getOrThrow(AylythConfiguredFeatures.STREWN_LEAVES_PATCH);
        var antlerShootsWaterPatch = features.getOrThrow(AylythConfiguredFeatures.ANTLER_SHOOTS_WATER_PATCH);
        var antlerShootsPatch = features.getOrThrow(AylythConfiguredFeatures.ANTLER_SHOOTS_PATCH);
        var woodyGrowthsWaterSelector = features.getOrThrow(AylythConfiguredFeatures.WOODY_GROWTHS_WATER_SELECTOR);
        var woodyGrowthsWaterPatch = features.getOrThrow(AylythConfiguredFeatures.WOODY_GROWTH_WATER_PATCH);

        PlacedFeatures.register(context, AYLYTHIAN_DARK_OAK, aylythianDarkOak, List.of(PlacedFeatures.createCountExtraModifier(8, 0.25F, 2), PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_SAPLING), BiomePlacementModifier.of())); //decorate(Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(8, 0.3F, 2)));
        PlacedFeatures.register(context, AYLYTHIAN_MEGA_DARK_OAK, aylythianMegaDarkOak, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_SAPLING)));
        PlacedFeatures.register(context, YMPE_TREE, ympe, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_SAPLING)));
        PlacedFeatures.register(context, BIG_YMPE_TREE, bigYmpe, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_SAPLING)));
        PlacedFeatures.register(context, POMEGRANATE_TREE, pomegranate, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_SAPLING)));
        PlacedFeatures.register(context, WRITHEWOOD_TREE, writhewood, List.of(PlacedFeatures.wouldSurvive(AylythBlocks.WRITHEWOOD_SAPLING)));

        PlacedFeatures.register(context, SPRING, spring, List.of(CountPlacementModifier.of(1), RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));//.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).range(Decorators.TOP_TO_BOTTOM).spreadHorizontally().applyChance(8);
        PlacedFeatures.register(context, BUSHES, bushes, List.of(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, OAK_LEAF_PILE, oakLeafPile, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, YMPE_LEAF_PILE, ympeLeafPile, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, OAK_STREWN_LEAVES, oakStrewnLeaves, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, YMPE_STREWN_LEAVES, ympeStrewnLeaves, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, AYLYTH_WEEDS, aylythWeeds, List.of(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));//configure(new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(ModBlocks.GRIPWEED.getDefaultState(), 2).build()), SimpleBlockPlacer.INSTANCE).tries(64).cannotProject().build()).decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE).decorate(Decorators.FOLIAGE_PLACEMENT).repeat(7));
        PlacedFeatures.register(context, MARIGOLDS, marigolds, List.of(PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(AylythBlocks.MARIGOLD), BiomePlacementModifier.of()));
        PlacedFeatures.register(context, SHELF_JACK_O_LANTERN_MUSHROOMS, shelfJackOlanternMushrooms, List.of(SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP));
        PlacedFeatures.register(context, ANTLER_SHOOTS_WATER, antlerShootsWater, List.of(BlockFilterPlacementModifier.of(BlockPredicate.matchingFluids(Fluids.WATER))));
        PlacedFeatures.register(context, ANTLER_SHOOTS, antlerShoots, List.of(PlacedFeatures.isAir()));
        PlacedFeatures.register(context, SMALL_WOODY_GROWTH_WATER, smallWoodyGrowthWater, List.of(BlockFilterPlacementModifier.of(BlockPredicate.matchingFluids(Fluids.WATER))));
        PlacedFeatures.register(context, LARGE_WOODY_GROWTH_WATER, largeWoodyGrowthWater, List.of(BlockFilterPlacementModifier.of(BlockPredicate.matchingFluids(Fluids.WATER))));

        PlacedFeatures.register(context, OAK_SEEP, oakSeep, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
        PlacedFeatures.register(context, SPRUCE_SEEP, spruceSeep, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
        PlacedFeatures.register(context, DARK_OAK_SEEP, darkOakSeep, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
        PlacedFeatures.register(context, YMPE_SEEP, ympeSeep, List.of(CountPlacementModifier.of(4), RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);

        PlacedFeatures.register(context, DEEP_ROOF_TREES_PLACED, deepRoofTrees, treesSurvive(8, 0.5F, 3));
        PlacedFeatures.register(context, CONIFEROUS_DEEP_ROOF_TREES_PLACED, coniferousDeepRoofTrees, treesSurvive(7, 0.5F, 2));
        PlacedFeatures.register(context, COPSE_TREES_PLACED, copseTrees, treesSurvive(3, 0.1F, 1));
        PlacedFeatures.register(context, DEEPWOOD_TREES_PLACED, deepWoodTrees, treesSurvive(4, 0.25F, 2));
        PlacedFeatures.register(context, CONIFEROUS_COPSE_TREES_PLACED, coniferousCopseTrees, treesSurvive(5, 0.25F, 2));
        PlacedFeatures.register(context, CONIFEROUS_DEEPWOOD_TREES_PLACED, coniferousDeepWoodTrees, treesSurvive(4, 0.25F, 2));
        PlacedFeatures.register(context, OVERGROWTH_CLEARING_TREES_PLACED, overgrowthClearingTrees, treesSurvive(1, 0.5F, 2));
        PlacedFeatures.register(context, MIRE_WATER_TREES_PLACED, mireWaterTrees, floodedTreesSurvive(2, 0.5F, 2));
        PlacedFeatures.register(context, MIRE_LAND_TREES_PLACED, mireLandTrees, treesSurvive(2, 0.5F, 2));

        PlacedFeatures.register(context, RED_MUSHROOM_PATCHES_PLACED, redMushroomPatches, List.of(RarityFilterPlacementModifier.of(256), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, BROWN_MUSHROOM_PATCHES_PLACED, brownMushroomPatches, List.of(RarityFilterPlacementModifier.of(256), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, RED_MUSHROOM_PATCHES_DEEPWOOD_PLACED, redMushroomPatches, List.of(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, BROWN_MUSHROOM_PATCHES_DEEPWOOD_PLACED, brownMushroomPatches, List.of(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, GLOW_LICHEN_PLACED, glowLichen, List.of(RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), BiomePlacementModifier.of()));
        PlacedFeatures.register(context, SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_COMMON_PLACED, shelfJackOLanternMushroomPatches, List.of(RarityFilterPlacementModifier.of(8), CountPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_DEEPWOOD_PLACED, shelfJackOLanternMushroomPatches, List.of(RarityFilterPlacementModifier.of(4), CountPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, GHOSTCAP_MUSHROOM_PATCHES_PLACED, ghostcapMushroomPatches, List.of(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, POMEGRANATE_TREE_VEG_PLACED, pomegranateTree, List.of(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of(), PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_SAPLING)));
        PlacedFeatures.register(context, WOODY_GROWTH_PATCH_PLACED, woodyGrowthPatch, List.of(SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, WOODY_GROWTH_BOWELS_PATCH_PLACED, woodyGrowthBowelsPatch, List.of(CountPlacementModifier.of(128), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, STREWN_LEAVES_PATCH_PLACED, strewnLeavesPatch, List.of(RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, ANTLER_SHOOTS_WATER_PATCH_PLACED, antlerShootsWaterPatch, List.of(SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(2), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, ANTLER_SHOOTS_PATCH_PLACED, antlerShootsPatch, List.of(SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, WOODY_GROWTHS_WATER_SELECTOR_PLACED, woodyGrowthsWaterSelector, List.of(PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP));

        PlacedFeatures.register(context, WOODY_GROWTH_WATER_PATCH_PLACED, woodyGrowthsWaterPatch, List.of(SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(2), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of()));
    }

    private static List<PlacementModifier> treesSurvive(int count, float extraChance, int extraCount) {
        return List.of(PlacedFeatures.createCountExtraModifier(count, extraChance, extraCount),
                SquarePlacementModifier.of(),
                NOT_IN_SURFACE_WATER_MODIFIER,
                PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
                BiomePlacementModifier.of(),
                PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_SAPLING));
    }

    private static List<PlacementModifier> floodedTreesSurvive(int count, float extraChance, int extraCount) {
        return List.of(PlacedFeatures.createCountExtraModifier(count, extraChance, extraCount),
                SquarePlacementModifier.of(),
                SurfaceWaterDepthFilterPlacementModifier.of(3),
                PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
                BiomePlacementModifier.of(),
                PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_SAPLING));
    }

    private static List<PlacementModifier> treesSurvive() {
        return List.of(SquarePlacementModifier.of(),
                NOT_IN_SURFACE_WATER_MODIFIER,
                PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
                BiomePlacementModifier.of(),
                PlacedFeatures.wouldSurvive(AylythBlocks.YMPE_SAPLING));
    }
}
