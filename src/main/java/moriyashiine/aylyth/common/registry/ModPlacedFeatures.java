package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    public static void datagenInit() {}

    private static <F extends FeatureConfig> RegistryEntry<PlacedFeature> register(String id, RegistryEntry<ConfiguredFeature<F, ?>> entry, List<PlacementModifier> modifers) {
        return PlacedFeatures.register(Aylyth.MOD_ID + ":" + id, entry, modifers);
    }

    public static final RegistryEntry<PlacedFeature> AYLYTHIAN_DARK_OAK = register("aylythian_dark_oak", ModConfiguredFeatures.AYLYTHIAN_DARK_OAK, List.of(PlacedFeatures.createCountExtraModifier(8, 0.25F, 2), PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling))); //decorate(Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(8, 0.3F, 2)));
    public static final RegistryEntry<PlacedFeature> AYLYTHIAN_MEGA_DARK_OAK = register("aylythian_mega_dark_oak", ModConfiguredFeatures.AYLYTHIAN_MEGA_DARK_OAK, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
    public static final RegistryEntry<PlacedFeature> YMPE_TREE = register("ympe_tree", ModConfiguredFeatures.YMPE_TREE, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
    public static final RegistryEntry<PlacedFeature> BIG_YMPE_TREE = register("big_ympe_tree", ModConfiguredFeatures.BIG_YMPE_TREE, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
    public static final RegistryEntry<PlacedFeature> POMEGRANATE_TREE = register("pomegranate_tree", ModConfiguredFeatures.POMEGRANATE_TREE, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.YMPE_BLOCKS.sapling)));
    public static final RegistryEntry<PlacedFeature> WRITHEWOOD_TREE = register("writhewood_tree", ModConfiguredFeatures.WRITHEWOOD_TREE, List.of(PlacedFeatures.wouldSurvive(ModBlocks.WRITHEWOOD_BLOCKS.sapling)));

    public static final RegistryEntry<PlacedFeature> SPRING = register("spring", ModConfiguredFeatures.SPRING, List.of(CountPlacementModifier.of(1), RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));//.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).range(Decorators.TOP_TO_BOTTOM).spreadHorizontally().applyChance(8);
    public static final RegistryEntry<PlacedFeature> BUSHES = register("bushes", ModConfiguredFeatures.BUSHES, List.of(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> OAK_LEAF_PILE = register("oak_leaf_pile", ModConfiguredFeatures.OAK_LEAF_PILE, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> YMPE_LEAF_PILE = register("ympe_leaf_pile", ModConfiguredFeatures.YMPE_LEAF_PILE, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> OAK_STREWN_LEAVES = register("oak_strewn_leaves", ModConfiguredFeatures.OAK_STREWN_LEAVES, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> YMPE_STREWN_LEAVES = register("ympe_strewn_leaves", ModConfiguredFeatures.YMPE_STREWN_LEAVES, List.of(CountPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> AYLYTH_WEEDS = register("aylyth_weeds", ModConfiguredFeatures.AYLYTH_WEEDS, List.of(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));//configure(new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(ModBlocks.GRIPWEED.getDefaultState(), 2).build()), SimpleBlockPlacer.INSTANCE).tries(64).cannotProject().build()).decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE).decorate(Decorators.FOLIAGE_PLACEMENT).repeat(7));
    public static final RegistryEntry<PlacedFeature> MARIGOLDS = register("marigolds", ModConfiguredFeatures.MARIGOLDS, List.of(PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, PlacedFeatures.wouldSurvive(ModBlocks.MARIGOLD), BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOMS = register("shelf_jack_o_lantern_mushrooms", ModConfiguredFeatures.SHELF_JACK_O_LANTERN_MUSHROOMS, List.of(SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP));

    public static final RegistryEntry<PlacedFeature> OAK_SEEP = register("oak_seep", ModConfiguredFeatures.OAK_SEEP, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
    public static final RegistryEntry<PlacedFeature> SPRUCE_SEEP = register("spruce_seep", ModConfiguredFeatures.SPRUCE_SEEP, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
    public static final RegistryEntry<PlacedFeature> DARK_OAK_SEEP = register("dark_oak_seep", ModConfiguredFeatures.DARK_OAK_SEEP, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
    public static final RegistryEntry<PlacedFeature> YMPE_SEEP = register("ympe_seep", ModConfiguredFeatures.YMPE_SEEP, List.of(CountPlacementModifier.of(4), RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
}
