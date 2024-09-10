package moriyashiine.aylyth.common.data.levelgen;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.poi.PointOfInterestType;

public interface AylythConfiguredFeautres {

    RegistryKey<ConfiguredFeature<?, ?>> AYLYTHIAN_DARK_OAK = bind("aylythian_dark_oak");
    RegistryKey<ConfiguredFeature<?, ?>> AYLYTHIAN_MEGA_DARK_OAK = bind("aylythian_mega_dark_oak");
    RegistryKey<ConfiguredFeature<?, ?>> YMPE_TREE = bind("ympe_tree");
    RegistryKey<ConfiguredFeature<?, ?>> BIG_YMPE_TREE = bind("big_ympe_tree");
    RegistryKey<ConfiguredFeature<?, ?>> POMEGRANATE_TREE = bind("pomegranate_tree");
    RegistryKey<ConfiguredFeature<?, ?>> WRITHEWOOD_TREE = bind("writhewood_tree");
    RegistryKey<ConfiguredFeature<?, ?>> GIRASOL_TREE = bind("seeping_tree");

    RegistryKey<ConfiguredFeature<?, ?>> SPRING = bind("spring");
    RegistryKey<ConfiguredFeature<?, ?>> BUSHES = bind("bushes");
    RegistryKey<ConfiguredFeature<?, ?>> OAK_LEAF_PILE = bind("oak_leaf_pile");
    RegistryKey<ConfiguredFeature<?, ?>> YMPE_LEAF_PILE = bind("ympe_leaf_pile");
    RegistryKey<ConfiguredFeature<?, ?>> OAK_STREWN_LEAVES = bind("oak_strewn_leaves");
    RegistryKey<ConfiguredFeature<?, ?>> YMPE_STREWN_LEAVES = bind("ympe_strewn_leaves");
    RegistryKey<ConfiguredFeature<?, ?>> AYLYTH_WEEDS = bind("aylyth_weeds");
    RegistryKey<ConfiguredFeature<?, ?>> MARIGOLDS = bind("marigolds");
    RegistryKey<ConfiguredFeature<?, ?>> SHELF_JACK_O_LANTERN_MUSHROOMS = bind("shelf_jack_o_lantern_mushrooms");
    RegistryKey<ConfiguredFeature<?, ?>> GHOSTCAP_MUSHROOM = bind("ghostcap_mushroom");
    RegistryKey<ConfiguredFeature<?, ?>> SMALL_WOODY_GROWTH = bind("small_woody_growth");
    RegistryKey<ConfiguredFeature<?, ?>> LARGE_WOODY_GROWTH = bind("large_woody_growth");
    RegistryKey<ConfiguredFeature<?, ?>> SMALL_WOODY_GROWTH_WATER = bind("small_woody_growth_water");
    RegistryKey<ConfiguredFeature<?, ?>> LARGE_WOODY_GROWTH_WATER = bind("large_woody_growth_water");
    RegistryKey<ConfiguredFeature<?, ?>> ANTLER_SHOOTS = bind("antler_shoots");
    RegistryKey<ConfiguredFeature<?, ?>> ANTLER_SHOOTS_WATER = bind("antler_shoots_water");
    //ConfiguredFeature<?, ?> CLEARING_FLOWERS = todo flower generators

    RegistryKey<ConfiguredFeature<?, ?>> OAK_SEEP = bind("oak_seep");
    RegistryKey<ConfiguredFeature<?, ?>> SPRUCE_SEEP = bind("spruce_seep");
    RegistryKey<ConfiguredFeature<?, ?>> DARK_OAK_SEEP = bind("dark_oak_seep");
    RegistryKey<ConfiguredFeature<?, ?>> YMPE_SEEP = bind("ympe_seep");

    RegistryKey<ConfiguredFeature<?, ?>> DEEP_ROOF_TREES = bind("deep_roof_trees");
    RegistryKey<ConfiguredFeature<?, ?>> CONIFEROUS_DEEP_ROOF_TREES = bind("coniferous_deep_roof_trees");
    RegistryKey<ConfiguredFeature<?, ?>> COPSE_TREES = bind("copse_trees");
    RegistryKey<ConfiguredFeature<?, ?>> DEEPWOOD_TREES = bind("deepwood_trees");
    RegistryKey<ConfiguredFeature<?, ?>> CONIFEROUS_COPSE_TREES = bind("coniferous_copse_trees");
    RegistryKey<ConfiguredFeature<?, ?>> CONIFEROUS_DEEPWOOD_TREES = bind("coniferous_deepwood_trees");
    RegistryKey<ConfiguredFeature<?, ?>> OVERGROWTH_CLEARING_TREES = bind("overgrowth_clearing_trees");
    RegistryKey<ConfiguredFeature<?, ?>> MIRE_WATER_TREES = bind("mire_water_trees");
    RegistryKey<ConfiguredFeature<?, ?>> MIRE_LAND_TREES = bind("mire_land_trees");

    RegistryKey<ConfiguredFeature<?, ?>> RED_MUSHROOM_PATCHES = bind("red_mushroom_patch");
    RegistryKey<ConfiguredFeature<?, ?>> BROWN_MUSHROOM_PATCHES = bind("brown_mushroom_patch");
    RegistryKey<ConfiguredFeature<?, ?>> GLOW_LICHEN = bind("glow_lichen");
    RegistryKey<ConfiguredFeature<?, ?>> SHELF_JACK_O_LANTERN_MUSHROOM_PATCHES = bind("shelf_jack_o_lantern_mushroom_patch");
    RegistryKey<ConfiguredFeature<?, ?>> GHOSTCAP_MUSHROOM_PATCHES = bind("ghostcap_mushroom_patch");
    RegistryKey<ConfiguredFeature<?, ?>> WOODY_GROWTH_PATCH = bind("woody_growth_patch");
    RegistryKey<ConfiguredFeature<?, ?>> WOODY_GROWTH_BOWELS_PATCH = bind("woody_growth_bowels_patch");
    RegistryKey<ConfiguredFeature<?, ?>> STREWN_LEAVES_PATCH = bind("strewn_leaves_patch");
    RegistryKey<ConfiguredFeature<?, ?>> ANTLER_SHOOTS_WATER_PATCH = bind("antler_shoots_water_patch");
    RegistryKey<ConfiguredFeature<?, ?>> ANTLER_SHOOTS_PATCH = bind("antler_shoots_patch");
    RegistryKey<ConfiguredFeature<?, ?>> WOODY_GROWTHS_WATER_SELECTOR = bind("woody_growth_water_selector");

    RegistryKey<ConfiguredFeature<?, ?>> WOODY_GROWTH_WATER_PATCH = bind("woody_growth_water_patch");

    private static RegistryKey<ConfiguredFeature<?, ?>> bind(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, AylythUtil.id(name));
    }
}
