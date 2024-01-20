package moriyashiine.aylyth.datagen.worldgen.features;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    private static final PlacementModifier NOT_IN_SURFACE_WATER_MODIFIER = SurfaceWaterDepthFilterPlacementModifier.of(0);

    public static final RegistryKey<PlacedFeature> AYLYTHIAN_DARK_OAK = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("aylythian_dark_oak"));
    public static final RegistryKey<PlacedFeature> AYLYTHIAN_MEGA_DARK_OAK = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("aylythian_mega_dark_oak"));
    public static final RegistryKey<PlacedFeature> YMPE_TREE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("ympe_tree"));
    public static final RegistryKey<PlacedFeature> BIG_YMPE_TREE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("big_ympe_tree"));
    public static final RegistryKey<PlacedFeature> POMEGRANATE_TREE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("pomegranate_tree"));
    public static final RegistryKey<PlacedFeature> WRITHEWOOD_TREE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("writhewood_tree"));

    public static final RegistryKey<PlacedFeature> SPRING = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("spring"));
    public static final RegistryKey<PlacedFeature> BUSHES = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("bushes"));
    public static final RegistryKey<PlacedFeature> OAK_LEAF_PILE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("oak_leaf_pile"));
    public static final RegistryKey<PlacedFeature> YMPE_LEAF_PILE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("ympe_leaf_pile"));
    public static final RegistryKey<PlacedFeature> OAK_STREWN_LEAVES = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("oak_strewn_leaves"));
    public static final RegistryKey<PlacedFeature> YMPE_STREWN_LEAVES = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("ympe_strewn_leaves"));
    public static final RegistryKey<PlacedFeature> AYLYTH_WEEDS = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("aylyth_weeds"));
    public static final RegistryKey<PlacedFeature> MARIGOLDS = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("marigolds"));
    public static final RegistryKey<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOMS = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("shelf_jack_o_lantern_mushrooms"));
    public static final RegistryKey<PlacedFeature> ANTLER_SHOOTS_WATER = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("antler_shoots_water"));
    public static final RegistryKey<PlacedFeature> ANTLER_SHOOTS = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("antler_shoots"));
    public static final RegistryKey<PlacedFeature> SMALL_WOODY_GROWTH_WATER = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("small_woody_growth_water"));
    public static final RegistryKey<PlacedFeature> LARGE_WOODY_GROWTH_WATER = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("large_woody_growth_water"));

    public static final RegistryKey<PlacedFeature> OAK_SEEP = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("oak_seep"));
    public static final RegistryKey<PlacedFeature> SPRUCE_SEEP = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("spruce_seep"));
    public static final RegistryKey<PlacedFeature> DARK_OAK_SEEP = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("dark_oak_seep"));
    public static final RegistryKey<PlacedFeature> YMPE_SEEP = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("ympe_seep"));

    public static final RegistryKey<PlacedFeature> DEEP_ROOF_TREES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("deep_roof_trees"));
    public static final RegistryKey<PlacedFeature> CONIFEROUS_DEEP_ROOF_TREES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("coniferous_deep_roof_trees"));
    public static final RegistryKey<PlacedFeature> COPSE_TREES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("copse_trees"));
    public static final RegistryKey<PlacedFeature> DEEPWOOD_TREES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("deepwood_trees"));
    public static final RegistryKey<PlacedFeature> CONIFEROUS_COPSE_TREES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("coniferous_copse_trees"));
    public static final RegistryKey<PlacedFeature> CONIFEROUS_DEEPWOOD_TREES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("coniferous_deepwood_trees"));
    public static final RegistryKey<PlacedFeature> OVERGROWTH_CLEARING_TREES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("overgrowth_clearing_trees"));
    public static final RegistryKey<PlacedFeature> MIRE_WATER_TREES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("mire_water_trees"));
    public static final RegistryKey<PlacedFeature> MIRE_LAND_TREES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("mire_land_trees"));

    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_PATCHES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("red_mushroom_patch"));
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_PATCHES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("brown_mushroom_patch"));
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_PATCHES_DEEPWOOD_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("red_mushroom_patch_deepwood"));
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_PATCHES_DEEPWOOD_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("brown_mushroom_patch_deepwood"));
    public static final RegistryKey<PlacedFeature> GLOW_LICHEN_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("glow_lichen"));
    public static final RegistryKey<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_COMMON_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("shelf_jack_o_lantern_mushroom_common_patch"));
    public static final RegistryKey<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_DEEPWOOD_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("shelf_jack_o_lantern_mushroom_deepwood_patch"));
    public static final RegistryKey<PlacedFeature> GHOSTCAP_MUSHROOM_PATCHES_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("ghostcap_mushroom_patch"));
    public static final RegistryKey<PlacedFeature> POMEGRANATE_TREE_VEG_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("pomegranate_tree_placed"));
    public static final RegistryKey<PlacedFeature> WOODY_GROWTH_PATCH_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("woody_growth_patch"));
    public static final RegistryKey<PlacedFeature> WOODY_GROWTH_BOWELS_PATCH_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("woody_growth_bowels_patch"));
    public static final RegistryKey<PlacedFeature> STREWN_LEAVES_PATCH_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("strewn_leaves_patch"));
    public static final RegistryKey<PlacedFeature> ANTLER_SHOOTS_WATER_PATCH_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("antler_shoots_water_patch"));
    public static final RegistryKey<PlacedFeature> ANTLER_SHOOTS_PATCH_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("antler_shoots_patch"));
    public static final RegistryKey<PlacedFeature> WOODY_GROWTHS_WATER_SELECTOR_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("woody_growth_water_selector"));

    public static final RegistryKey<PlacedFeature> WOODY_GROWTH_WATER_PATCH_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, AylythUtil.id("woody_growth_water_patch"));

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var features = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        var aylythianDarkOak = features.getOrThrow(ModConfiguredFeatures.AYLYTHIAN_DARK_OAK);
        var aylythianMegaDarkOak = features.getOrThrow(ModConfiguredFeatures.AYLYTHIAN_MEGA_DARK_OAK);
        var ympe = features.getOrThrow(ModConfiguredFeatures.YMPE_TREE);
        var bigYmpe = features.getOrThrow(ModConfiguredFeatures.BIG_YMPE_TREE);
        var pomegranate = features.getOrThrow(ModConfiguredFeatures.POMEGRANATE_TREE);
        var writhewood = features.getOrThrow(ModConfiguredFeatures.WRITHEWOOD_TREE);
        var spring = features.getOrThrow(ModConfiguredFeatures.SPRING);
        var bushes = features.getOrThrow(ModConfiguredFeatures.BUSHES);
        var oakLeafPile = features.getOrThrow(ModConfiguredFeatures.OAK_LEAF_PILE);
        var ympeLeafPile = features.getOrThrow(ModConfiguredFeatures.YMPE_LEAF_PILE);
        var oakStrewnLeaves = features.getOrThrow(ModConfiguredFeatures.OAK_STREWN_LEAVES);
        var ympeStrewnLeaves = features.getOrThrow(ModConfiguredFeatures.YMPE_STREWN_LEAVES);
        var aylythWeeds = features.getOrThrow(ModConfiguredFeatures.AYLYTH_WEEDS);
        var marigolds = features.getOrThrow(ModConfiguredFeatures.MARIGOLDS);
        var shelfJackOlanternMushrooms = features.getOrThrow(ModConfiguredFeatures.SHELF_JACK_O_LANTERN_MUSHROOMS);
        var antlerShootsWater = features.getOrThrow(ModConfiguredFeatures.ANTLER_SHOOTS_WATER);
        var antlerShoots = features.getOrThrow(ModConfiguredFeatures.ANTLER_SHOOTS);
        var smallWoodyGrowthWater = features.getOrThrow(ModConfiguredFeatures.SMALL_WOODY_GROWTH_WATER);
        var largeWoodyGrowthWater = features.getOrThrow(ModConfiguredFeatures.LARGE_WOODY_GROWTH_WATER);
        var oakSeep = features.getOrThrow(ModConfiguredFeatures.OAK_SEEP);
        var spruceSeep = features.getOrThrow(ModConfiguredFeatures.SPRUCE_SEEP);
        var darkOakSeep = features.getOrThrow(ModConfiguredFeatures.DARK_OAK_SEEP);
        var ympeSeep = features.getOrThrow(ModConfiguredFeatures.YMPE_SEEP);
        var deepRoofTrees = features.getOrThrow(ModConfiguredFeatures.DEEP_ROOF_TREES);
        var coniferousDeepRoofTrees = features.getOrThrow(ModConfiguredFeatures.CONIFEROUS_DEEP_ROOF_TREES);
        var copseTrees = features.getOrThrow(ModConfiguredFeatures.COPSE_TREES);
        var deepWoodTrees = features.getOrThrow(ModConfiguredFeatures.DEEPWOOD_TREES);
        var coniferousCopseTrees = features.getOrThrow(ModConfiguredFeatures.CONIFEROUS_COPSE_TREES);
        var coniferousDeepWoodTrees = features.getOrThrow(ModConfiguredFeatures.CONIFEROUS_DEEPWOOD_TREES);
        var overgrowthClearingTrees = features.getOrThrow(ModConfiguredFeatures.OVERGROWTH_CLEARING_TREES);
        var mireWaterTrees = features.getOrThrow(ModConfiguredFeatures.MIRE_WATER_TREES);
        var mireLandTrees = features.getOrThrow(ModConfiguredFeatures.MIRE_LAND_TREES);
        var redMushroomPatches = features.getOrThrow(ModConfiguredFeatures.RED_MUSHROOM_PATCHES);
        var brownMushroomPatches = features.getOrThrow(ModConfiguredFeatures.BROWN_MUSHROOM_PATCHES);
        var glowLichen = features.getOrThrow(ModConfiguredFeatures.GLOW_LICHEN);
        var shelfJackOLanternMushroomPatches = features.getOrThrow(ModConfiguredFeatures.SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES);
        var ghostcapMushroomPatches = features.getOrThrow(ModConfiguredFeatures.GHOSTCAP_MUSHROOM_PATCHES);
        var pomegranateTree = features.getOrThrow(ModConfiguredFeatures.POMEGRANATE_TREE);
        var woodyGrowthPatch = features.getOrThrow(ModConfiguredFeatures.WOODY_GROWTH_PATCH);
        var woodyGrowthBowelsPatch = features.getOrThrow(ModConfiguredFeatures.WOODY_GROWTH_BOWELS_PATCH);
        var strewnLeavesPatch = features.getOrThrow(ModConfiguredFeatures.STREWN_LEAVES_PATCH);
        var antlerShootsWaterPatch = features.getOrThrow(ModConfiguredFeatures.ANTLER_SHOOTS_WATER_PATCH);
        var antlerShootsPatch = features.getOrThrow(ModConfiguredFeatures.ANTLER_SHOOTS_PATCH);
        var woodyGrowthsWaterSelector = features.getOrThrow(ModConfiguredFeatures.WOODY_GROWTHS_WATER_SELECTOR);
        var woodyGrowthsWaterPatch = features.getOrThrow(ModConfiguredFeatures.WOODY_GROWTH_WATER_PATCH);

        PlacedFeatures.register(context, AYLYTHIAN_DARK_OAK, aylythianDarkOak, List.of(PlacedFeatures.createCountExtraModifier(8, 0.25F, 2), PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling))); //decorate(Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(8, 0.3F, 2)));
        PlacedFeatures.register(context, AYLYTHIAN_MEGA_DARK_OAK, aylythianMegaDarkOak, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
        PlacedFeatures.register(context, YMPE_TREE, ympe, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
        PlacedFeatures.register(context, BIG_YMPE_TREE, bigYmpe, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
        PlacedFeatures.register(context, POMEGRANATE_TREE, pomegranate, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
        PlacedFeatures.register(context, WRITHEWOOD_TREE, writhewood, List.of(PlacedFeatures.wouldSurvive(ModBlocks.WRITHEWOOD_BLOCKS.sapling)));

        PlacedFeatures.register(context, SPRING, spring, List.of(CountPlacementModifier.of(1), RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));//.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).range(Decorators.TOP_TO_BOTTOM).spreadHorizontally().applyChance(8);
        PlacedFeatures.register(context, BUSHES, bushes, List.of(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, OAK_LEAF_PILE, oakLeafPile, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, YMPE_LEAF_PILE, ympeLeafPile, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, OAK_STREWN_LEAVES, oakStrewnLeaves, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, YMPE_STREWN_LEAVES, ympeStrewnLeaves, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
        PlacedFeatures.register(context, AYLYTH_WEEDS, aylythWeeds, List.of(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));//configure(new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(ModBlocks.GRIPWEED.getDefaultState(), 2).build()), SimpleBlockPlacer.INSTANCE).tries(64).cannotProject().build()).decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE).decorate(Decorators.FOLIAGE_PLACEMENT).repeat(7));
        PlacedFeatures.register(context, MARIGOLDS, marigolds, List.of(PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.MARIGOLD), BiomePlacementModifier.of()));
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
        PlacedFeatures.register(context, POMEGRANATE_TREE_VEG_PLACED, pomegranateTree, List.of(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of(), PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
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
                PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling));
    }

    private static List<PlacementModifier> floodedTreesSurvive(int count, float extraChance, int extraCount) {
        return List.of(PlacedFeatures.createCountExtraModifier(count, extraChance, extraCount),
                SquarePlacementModifier.of(),
                SurfaceWaterDepthFilterPlacementModifier.of(3),
                PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
                BiomePlacementModifier.of(),
                PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling));
    }

    private static List<PlacementModifier> treesSurvive() {
        return List.of(SquarePlacementModifier.of(),
                NOT_IN_SURFACE_WATER_MODIFIER,
                PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
                BiomePlacementModifier.of(),
                PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling));
    }
}
