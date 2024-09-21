package moriyashiine.aylyth.common.data.world.feature;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.PlacedFeature;

public interface AylythPlacedFeatures {

    RegistryKey<PlacedFeature> AYLYTHIAN_DARK_OAK = bind("aylythian_dark_oak");
    RegistryKey<PlacedFeature> AYLYTHIAN_MEGA_DARK_OAK = bind("aylythian_mega_dark_oak");
    RegistryKey<PlacedFeature> YMPE_TREE = bind("ympe_tree");
    RegistryKey<PlacedFeature> BIG_YMPE_TREE = bind("big_ympe_tree");
    RegistryKey<PlacedFeature> POMEGRANATE_TREE = bind("pomegranate_tree");
    RegistryKey<PlacedFeature> WRITHEWOOD_TREE = bind("writhewood_tree");

    RegistryKey<PlacedFeature> SPRING = bind("spring");
    RegistryKey<PlacedFeature> BUSHES = bind("bushes");
    RegistryKey<PlacedFeature> OAK_LEAF_PILE = bind("oak_leaf_pile");
    RegistryKey<PlacedFeature> YMPE_LEAF_PILE = bind("ympe_leaf_pile");
    RegistryKey<PlacedFeature> OAK_STREWN_LEAVES = bind("oak_strewn_leaves");
    RegistryKey<PlacedFeature> YMPE_STREWN_LEAVES = bind("ympe_strewn_leaves");
    RegistryKey<PlacedFeature> AYLYTH_WEEDS = bind("aylyth_weeds");
    RegistryKey<PlacedFeature> MARIGOLDS = bind("marigolds");
    RegistryKey<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOMS = bind("shelf_jack_o_lantern_mushrooms");
    RegistryKey<PlacedFeature> ANTLER_SHOOTS_WATER = bind("antler_shoots_water");
    RegistryKey<PlacedFeature> ANTLER_SHOOTS = bind("antler_shoots");
    RegistryKey<PlacedFeature> SMALL_WOODY_GROWTH_WATER = bind("small_woody_growth_water");
    RegistryKey<PlacedFeature> LARGE_WOODY_GROWTH_WATER = bind("large_woody_growth_water");

    RegistryKey<PlacedFeature> OAK_SEEP = bind("oak_seep");
    RegistryKey<PlacedFeature> SPRUCE_SEEP = bind("spruce_seep");
    RegistryKey<PlacedFeature> DARK_OAK_SEEP = bind("dark_oak_seep");
    RegistryKey<PlacedFeature> YMPE_SEEP = bind("ympe_seep");

    RegistryKey<PlacedFeature> DEEP_ROOF_TREES_PLACED = bind("deep_roof_trees");
    RegistryKey<PlacedFeature> CONIFEROUS_DEEP_ROOF_TREES_PLACED = bind("coniferous_deep_roof_trees");
    RegistryKey<PlacedFeature> COPSE_TREES_PLACED = bind("copse_trees");
    RegistryKey<PlacedFeature> DEEPWOOD_TREES_PLACED = bind("deepwood_trees");
    RegistryKey<PlacedFeature> CONIFEROUS_COPSE_TREES_PLACED = bind("coniferous_copse_trees");
    RegistryKey<PlacedFeature> CONIFEROUS_DEEPWOOD_TREES_PLACED = bind("coniferous_deepwood_trees");
    RegistryKey<PlacedFeature> OVERGROWTH_CLEARING_TREES_PLACED = bind("overgrowth_clearing_trees");
    RegistryKey<PlacedFeature> MIRE_WATER_TREES_PLACED = bind("mire_water_trees");
    RegistryKey<PlacedFeature> MIRE_LAND_TREES_PLACED = bind("mire_land_trees");

    RegistryKey<PlacedFeature> RED_MUSHROOM_PATCHES_PLACED = bind("red_mushroom_patch");
    RegistryKey<PlacedFeature> BROWN_MUSHROOM_PATCHES_PLACED = bind("brown_mushroom_patch");
    RegistryKey<PlacedFeature> RED_MUSHROOM_PATCHES_DEEPWOOD_PLACED = bind("red_mushroom_patch_deepwood");
    RegistryKey<PlacedFeature> BROWN_MUSHROOM_PATCHES_DEEPWOOD_PLACED = bind("brown_mushroom_patch_deepwood");
    RegistryKey<PlacedFeature> GLOW_LICHEN_PLACED = bind("glow_lichen");
    RegistryKey<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_COMMON_PLACED = bind("shelf_jack_o_lantern_mushroom_common_patch");
    RegistryKey<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_DEEPWOOD_PLACED = bind("shelf_jack_o_lantern_mushroom_deepwood_patch");
    RegistryKey<PlacedFeature> GHOSTCAP_MUSHROOM_PATCHES_PLACED = bind("ghostcap_mushroom_patch");
    RegistryKey<PlacedFeature> POMEGRANATE_TREE_VEG_PLACED = bind("pomegranate_tree_placed");
    RegistryKey<PlacedFeature> WOODY_GROWTH_PATCH_PLACED = bind("woody_growth_patch");
    RegistryKey<PlacedFeature> WOODY_GROWTH_BOWELS_PATCH_PLACED = bind("woody_growth_bowels_patch");
    RegistryKey<PlacedFeature> STREWN_LEAVES_PATCH_PLACED = bind("strewn_leaves_patch");
    RegistryKey<PlacedFeature> ANTLER_SHOOTS_WATER_PATCH_PLACED = bind("antler_shoots_water_patch");
    RegistryKey<PlacedFeature> ANTLER_SHOOTS_PATCH_PLACED = bind("antler_shoots_patch");
    RegistryKey<PlacedFeature> WOODY_GROWTHS_WATER_SELECTOR_PLACED = bind("woody_growth_water_selector");

    RegistryKey<PlacedFeature> WOODY_GROWTH_WATER_PATCH_PLACED = bind("woody_growth_water_patch");

    private static RegistryKey<PlacedFeature> bind(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Aylyth.id(name));
    }
}
