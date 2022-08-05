package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    public static void init() {}

    private static <F extends FeatureConfig> RegistryEntry<PlacedFeature> register(String id, RegistryEntry<ConfiguredFeature<F, ?>> entry, List<PlacementModifier> modifers) {
        return PlacedFeatures.register(Aylyth.MOD_ID + ":" + id, entry, modifers);
    }

    public static final RegistryEntry<PlacedFeature> AYLYTHIAN_DARK_OAK = register("aylythian_dark_oak", ModConfiguredFeatures.AYLYTHIAN_DARK_OAK, List.of(PlacedFeatures.createCountExtraModifier(8, 0.25F, 2), PlacedFeatures.wouldSurvive(ModBlocks.YMPE_SAPLING))); //decorate(Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(8, 0.3F, 2)));
    public static final RegistryEntry<PlacedFeature> AYLYTHIAN_MEGA_DARK_OAK = register("aylythian_mega_dark_oak", ModConfiguredFeatures.AYLYTHIAN_MEGA_DARK_OAK, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), PlacedFeatures.wouldSurvive(ModBlocks.YMPE_SAPLING)));
    public static final RegistryEntry<PlacedFeature> YMPE_TREE = register("ympe_tree", ModConfiguredFeatures.YMPE_TREE, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), PlacedFeatures.wouldSurvive(ModBlocks.YMPE_SAPLING)));
    public static final RegistryEntry<PlacedFeature> BIG_YMPE_TREE = register("big_ympe_tree", ModConfiguredFeatures.BIG_YMPE_TREE, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), PlacedFeatures.wouldSurvive(ModBlocks.YMPE_SAPLING)));
    public static final RegistryEntry<PlacedFeature> SPRING = register("spring", ModConfiguredFeatures.SPRING, List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(), RarityFilterPlacementModifier.of(5), HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(192)), BiomePlacementModifier.of()));//.configure(new SingleStateFeatureConfig(Blocks.WATER.getDefaultState())).range(Decorators.TOP_TO_BOTTOM).spreadHorizontally().applyChance(8);
    public static final RegistryEntry<PlacedFeature> BUSHES = register("bushes", ModConfiguredFeatures.BUSHES, List.of(RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    public static final RegistryEntry<PlacedFeature> AYLYTH_WEEDS = register("aylyth_weeds", ModConfiguredFeatures.AYLYTH_WEEDS, List.of(RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));//configure(new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(ModBlocks.GRIPWEED.getDefaultState(), 2).build()), SimpleBlockPlacer.INSTANCE).tries(64).cannotProject().build()).decorate(Decorator.SPREAD_32_ABOVE.configure(NopeDecoratorConfig.INSTANCE).decorate(Decorators.FOLIAGE_PLACEMENT).repeat(7));

    public static final RegistryEntry<PlacedFeature> OAK_SEEP = register("oak_seep", ModConfiguredFeatures.OAK_SEEP, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
    public static final RegistryEntry<PlacedFeature> SPRUCE_SEEP = register("spruce_seep", ModConfiguredFeatures.SPRUCE_SEEP, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
    public static final RegistryEntry<PlacedFeature> DARK_OAK_SEEP = register("dark_oak_seep", ModConfiguredFeatures.DARK_OAK_SEEP, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
    public static final RegistryEntry<PlacedFeature> YMPE_SEEP = register("ympe_seep", ModConfiguredFeatures.YMPE_SEEP, List.of(RarityFilterPlacementModifier.of(10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of())); //.spreadHorizontally().applyChance(10).repeatRandomly(4);
}
