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

    RegistryKey<PlacedFeature> SMALL_GIANT_JACK__O_LANTERN_MUSHROOM = bind("small_giant_jack_o_lantern_mushroom");
    RegistryKey<PlacedFeature> LARGE_GIANT_JACK__O_LANTERN_MUSHROOM = bind("large_giant_jack_o_lantern_mushroom");

    RegistryKey<PlacedFeature> SPRING = bind("spring");
    RegistryKey<PlacedFeature> BUSHES = bind("bushes");
    RegistryKey<PlacedFeature> AYLYTH_WEEDS = bind("aylyth_weeds");
    RegistryKey<PlacedFeature> MARIGOLDS = bind("marigolds");
    RegistryKey<PlacedFeature> JACK_O_LANTERN_MUSHROOM_PATCH = bind("jack_o_lantern_mushroom_patch");
    RegistryKey<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOMS = bind("shelf_jack_o_lantern_mushrooms");
    RegistryKey<PlacedFeature> ANTLER_SHOOTS_WATER = bind("antler_shoots_water");
    RegistryKey<PlacedFeature> ANTLER_SHOOTS = bind("antler_shoots");
    RegistryKey<PlacedFeature> SMALL_WOODY_GROWTH_WATER = bind("small_woody_growth_water");
    RegistryKey<PlacedFeature> LARGE_WOODY_GROWTH_WATER = bind("large_woody_growth_water");

    RegistryKey<PlacedFeature> OAK_SEEP = bind("oak_seep");
    RegistryKey<PlacedFeature> SPRUCE_SEEP = bind("spruce_seep");
    RegistryKey<PlacedFeature> DARK_OAK_SEEP = bind("dark_oak_seep");
    RegistryKey<PlacedFeature> YMPE_SEEP = bind("ympe_seep");

    RegistryKey<PlacedFeature> DEEP_ROOF_TREES = bind("deep_roof_trees");
    RegistryKey<PlacedFeature> CONIFEROUS_DEEP_ROOF_TREES = bind("coniferous_deep_roof_trees");
    RegistryKey<PlacedFeature> COPSE_TREES = bind("copse_trees");
    RegistryKey<PlacedFeature> DEEPWOOD_TREES = bind("deepwood_trees");
    RegistryKey<PlacedFeature> CONIFEROUS_COPSE_TREES = bind("coniferous_copse_trees");
    RegistryKey<PlacedFeature> CONIFEROUS_DEEPWOOD_TREES = bind("coniferous_deepwood_trees");
    RegistryKey<PlacedFeature> OVERGROWTH_CLEARING_TREES = bind("overgrowth_clearing_trees");
    RegistryKey<PlacedFeature> MIRE_WATER_TREES = bind("mire_water_trees");
    RegistryKey<PlacedFeature> MIRE_LAND_TREES = bind("mire_land_trees");
    RegistryKey<PlacedFeature> GIANT_JACK_O_LANTERN_MUSHROOMS = bind("giant_jack_o_lantern_mushrooms");

    RegistryKey<PlacedFeature> RED_MUSHROOM_PATCHES = bind("red_mushroom_patch");
    RegistryKey<PlacedFeature> BROWN_MUSHROOM_PATCHES = bind("brown_mushroom_patch");
    RegistryKey<PlacedFeature> RED_MUSHROOM_PATCHES_DEEPWOOD = bind("red_mushroom_patch_deepwood");
    RegistryKey<PlacedFeature> BROWN_MUSHROOM_PATCHES_DEEPWOOD = bind("brown_mushroom_patch_deepwood");
    RegistryKey<PlacedFeature> GLOW_LICHEN = bind("glow_lichen");
    RegistryKey<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_COMMON = bind("shelf_jack_o_lantern_mushroom_common_patch");
    RegistryKey<PlacedFeature> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES_DEEPWOOD = bind("shelf_jack_o_lantern_mushroom_deepwood_patch");
    RegistryKey<PlacedFeature> GHOSTCAP_MUSHROOM_PATCHES = bind("ghostcap_mushroom_patch");
    RegistryKey<PlacedFeature> POMEGRANATE_TREE_CHECKED = bind("pomegranate_tree_checked");
    RegistryKey<PlacedFeature> WOODY_GROWTH_PATCH = bind("woody_growth_patch");
    RegistryKey<PlacedFeature> WOODY_GROWTH_BOWELS_PATCH = bind("woody_growth_bowels_patch");
    RegistryKey<PlacedFeature> STREWN_LEAVES_PATCH = bind("strewn_leaves_patch");
    RegistryKey<PlacedFeature> ANTLER_SHOOTS_WATER_PATCH = bind("antler_shoots_water_patch");
    RegistryKey<PlacedFeature> ANTLER_SHOOTS_PATCH = bind("antler_shoots_patch");
    RegistryKey<PlacedFeature> WOODY_GROWTHS_WATER_SELECTOR = bind("woody_growth_water_selector");

    RegistryKey<PlacedFeature> WOODY_GROWTH_WATER_PATCH = bind("woody_growth_water_patch");

    private static RegistryKey<PlacedFeature> bind(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Aylyth.id(name));
    }
}
