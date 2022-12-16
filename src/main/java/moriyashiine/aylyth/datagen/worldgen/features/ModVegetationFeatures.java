package moriyashiine.aylyth.datagen.worldgen.features;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModVegetationFeatures {

    public static void datagenInit() {}

    private static <F extends Feature<C>, C extends FeatureConfig> RegistryEntry<ConfiguredFeature<C, ?>> registerConfigured(String id, F feature, C config) {
        return ConfiguredFeatures.register(Aylyth.MOD_ID + ":" + id, feature, config);
    }

    private static <F extends FeatureConfig> RegistryEntry<PlacedFeature> registerPlaced(String id, RegistryEntry<ConfiguredFeature<F, ?>> entry, List<PlacementModifier> modifers) {
        return PlacedFeatures.register(Aylyth.MOD_ID + ":" + id, entry, modifers);
    }

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> DEEP_ROOF_TREES = registerConfigured("deep_roof_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.AYLYTHIAN_DARK_OAK), 0.25F)), PlacedFeatures.createEntry(ModConfiguredFeatures.AYLYTHIAN_MEGA_DARK_OAK)));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CONIFEROUS_DEEP_ROOF_TREES = registerConfigured("coniferous_deep_roof_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.AYLYTHIAN_DARK_OAK), 0.15F)), TreePlacedFeatures.MEGA_SPRUCE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> COPSE_TREES = registerConfigured("copse_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.25F)), TreePlacedFeatures.DARK_OAK_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> DEEPWOOD_TREES = registerConfigured("deepwood_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.25F), new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.BIG_YMPE_TREE), 0.25F)), TreePlacedFeatures.DARK_OAK_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CONIFEROUS_COPSE_TREES = registerConfigured("coniferous_copse_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.25F)), TreePlacedFeatures.SPRUCE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CONIFEROUS_DEEPWOOD_TREES = registerConfigured("coniferous_deepwood_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.15F), new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.BIG_YMPE_TREE), 0.15F)), TreePlacedFeatures.SPRUCE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> OVERGROWTH_CLEARING_TREES = registerConfigured("overgrowth_clearing_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.5F)), TreePlacedFeatures.SPRUCE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> MIRE_WATER_TREES = registerConfigured("mire_water_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(), PlacedFeatures.createEntry(ModConfiguredFeatures.WRITHEWOOD_TREE)));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> MIRE_LAND_TREES = registerConfigured("mire_land_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(TreePlacedFeatures.SPRUCE_CHECKED, 0.25f), new RandomFeatureEntry(TreePlacedFeatures.MEGA_SPRUCE_CHECKED, 0.25f)), PlacedFeatures.createEntry(ModConfiguredFeatures.WRITHEWOOD_TREE)));

    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> RED_MUSHROOM_PATCHES = registerConfigured("red_mushroom_patch", Feature.RANDOM_PATCH, ModConfiguredFeatures.createRandomPatchFeatureConfig(BlockStateProvider.of(Blocks.RED_MUSHROOM), 96));
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> BROWN_MUSHROOM_PATCHES = registerConfigured("brown_mushroom_patch", Feature.RANDOM_PATCH, ModConfiguredFeatures.createRandomPatchFeatureConfig(BlockStateProvider.of(Blocks.BROWN_MUSHROOM), 96));
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> GLOW_LICHEN = registerConfigured("glow_lichen", Feature.RANDOM_PATCH, ModConfiguredFeatures.createRandomPatchFeatureConfig(BlockStateProvider.of(Blocks.GLOW_LICHEN), 32));
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES = registerConfigured("shelf_jack_o_lantern_mushroom_patch", Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(64, PlacedFeatures.createEntry(ModConfiguredFeatures.SHELF_JACK_O_LANTERN_MUSHROOMS)));
    public static final RegistryEntry<ConfiguredFeature<VegetationPatchFeatureConfig, ?>> GHOSTCAP_MUSHROOM_PATCHES = registerConfigured("ghostcap_mushroom_patch", Feature.VEGETATION_PATCH, new VegetationPatchFeatureConfig(ModTags.GHOSTCAP_REPLACEABLE, BlockStateProvider.of(Blocks.GRASS_BLOCK), PlacedFeatures.createEntry(ModConfiguredFeatures.GHOSTCAP_MUSHROOM), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0, 2, 0.45f, ConstantIntProvider.create(3), 0));
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> WOODY_GROWTH_PATCH = registerConfigured("woody_growth_patch", Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.LARGE_WOODY_GROWTH), 0.25F)), PlacedFeatures.createEntry(ModConfiguredFeatures.SMALL_WOODY_GROWTH)), List.of(Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.MUD), 8));



    public static final RegistryEntry<PlacedFeature> DEEP_ROOF_TREES_PLACED = registerPlaced("deep_roof_trees", DEEP_ROOF_TREES, treesSurvive(8, 0.5F, 3));
    public static final RegistryEntry<PlacedFeature> CONIFEROUS_DEEP_ROOF_TREES_PLACED = registerPlaced("coniferous_deep_roof_trees", CONIFEROUS_DEEP_ROOF_TREES, treesSurvive(7, 0.5F, 2));
    public static final RegistryEntry<PlacedFeature> COPSE_TREES_PLACED = registerPlaced("copse_trees", COPSE_TREES, treesSurvive(3, 0.1F, 1));
    public static final RegistryEntry<PlacedFeature> DEEPWOOD_TREES_PLACED = registerPlaced("deepwood_trees", DEEPWOOD_TREES, treesSurvive(4, 0.25F, 2));
    public static final RegistryEntry<PlacedFeature> CONIFEROUS_COPSE_TREES_PLACED = registerPlaced("coniferous_copse_trees", CONIFEROUS_COPSE_TREES, treesSurvive(5, 0.25F, 2));
    public static final RegistryEntry<PlacedFeature> CONIFEROUS_DEEPWOOD_TREES_PLACED = registerPlaced("coniferous_deepwood_trees", CONIFEROUS_DEEPWOOD_TREES, treesSurvive(4, 0.25F, 2));
    public static final RegistryEntry<PlacedFeature> OVERGROWTH_CLEARING_TREES_PLACED = registerPlaced("overgrowth_clearing_trees", OVERGROWTH_CLEARING_TREES, treesSurvive(1, 0.5F, 2));
    public static final RegistryEntry<PlacedFeature> MIRE_WATER_TREES_PLACED = registerPlaced("mire_water_trees", MIRE_WATER_TREES, floodedTreesSurvive(2, 0.5F, 2));
    public static final RegistryEntry<PlacedFeature> MIRE_LAND_TREES_PLACED = registerPlaced("mire_land_trees", MIRE_LAND_TREES, treesSurvive(2, 0.5F, 2));

    public static final RegistryEntry<PlacedFeature> RED_MUSHROOM_PATCHES_PLACED = registerPlaced("red_mushroom_patch", RED_MUSHROOM_PATCHES, List.of(RarityFilterPlacementModifier.of(256), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> BROWN_MUSHROOM_PATCHES_PLACED = registerPlaced("brown_mushroom_patch", BROWN_MUSHROOM_PATCHES, List.of(RarityFilterPlacementModifier.of(256), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> RED_MUSHROOM_PATCHES_DEEPWOOD_PLACED = registerPlaced("red_mushroom_patch_deepwood", RED_MUSHROOM_PATCHES, List.of(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> BROWN_MUSHROOM_PATCHES_DEEPWOOD_PLACED = registerPlaced("brown_mushroom_patch_deepwood", BROWN_MUSHROOM_PATCHES, List.of(RarityFilterPlacementModifier.of(64), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> GLOW_LICHEN_PLACED = registerPlaced("glow_lichen", GLOW_LICHEN, List.of(RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_COMMON_PLACED = registerPlaced("shelf_jack_o_lantern_mushroom_common_patch", SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES, List.of(RarityFilterPlacementModifier.of(8), CountPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_DEEPWOOD_PLACED = registerPlaced("shelf_jack_o_lantern_mushroom_deepwood_patch", SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES, List.of(RarityFilterPlacementModifier.of(4), CountPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> GHOSTCAP_MUSHROOM_PATCHES_PLACED = registerPlaced("ghostcap_mushroom_patch", GHOSTCAP_MUSHROOM_PATCHES, List.of(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> POMEGRANATE_TREE_VEG_PLACED = registerPlaced("pomegranate_tree_placed", ModConfiguredFeatures.POMEGRANATE_TREE, List.of(RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of(), PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
    public static final RegistryEntry<PlacedFeature> WOODY_GROWTH_PATCH_PLACED = registerPlaced("woody_growth_patch", WOODY_GROWTH_PATCH, List.of(SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of(), PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));



    private static List<PlacementModifier> treesSurvive(int count, float extraChance, int extraCount) {
        return List.of(PlacedFeatures.createCountExtraModifier(count, extraChance, extraCount),
                SquarePlacementModifier.of(),
                VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER,
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
                VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER,
                PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
                BiomePlacementModifier.of(),
                PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling));
    }
}
