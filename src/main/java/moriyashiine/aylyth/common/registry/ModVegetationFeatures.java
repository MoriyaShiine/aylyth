package moriyashiine.aylyth.common.registry;

import com.google.common.collect.ImmutableList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.generator.AylthianTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.BigYmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.YmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.feature.GrapeVineDecorator;
import moriyashiine.aylyth.mixin.TreeDecoratorTypeAccessor;
import moriyashiine.aylyth.mixin.TrunkPlacerTypeAccessor;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;

public class ModVegetationFeatures {

    public static final TrunkPlacerType<AylthianTrunkPlacer> AYLYTHIAN_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":aylythian_trunk_placer", AylthianTrunkPlacer.CODEC);
    public static final TrunkPlacerType<YmpeTrunkPlacer> YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":ympe_trunk_placer", YmpeTrunkPlacer.CODEC);
    public static final TrunkPlacerType<BigYmpeTrunkPlacer> BIG_YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":big_ympe_trunk_placer", BigYmpeTrunkPlacer.CODEC);
    public static final TreeDecoratorType<GrapeVineDecorator> GRAPE_VINE = TreeDecoratorTypeAccessor.register(Aylyth.MOD_ID + ":grape_vine_decorator", GrapeVineDecorator.CODEC);

    public static void init() {}

    private static <F extends Feature<C>, C extends FeatureConfig> RegistryEntry<ConfiguredFeature<C, ?>> registerConfigured(String id, F feature, C config) {
        return ConfiguredFeatures.register(Aylyth.MOD_ID + ":" + id, feature, config);
    }

    private static <F extends FeatureConfig> RegistryEntry<PlacedFeature> registerPlaced(String id, RegistryEntry<ConfiguredFeature<F, ?>> entry, List<PlacementModifier> modifers) {
        return PlacedFeatures.register(Aylyth.MOD_ID + ":" + id, entry, modifers);
    }

    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> DEEP_ROOF_TREES = registerConfigured("deep_roof_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(ModPlacedFeatures.AYLYTHIAN_DARK_OAK, 0.25F)), ModPlacedFeatures.AYLYTHIAN_MEGA_DARK_OAK));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> DEEP_CONIFEROUS_ROOF_TREES = registerConfigured("deep_coniferous_roof_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(ModPlacedFeatures.AYLYTHIAN_DARK_OAK, 0.5F)), TreePlacedFeatures.MEGA_SPRUCE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> FOREST_TREES = registerConfigured("forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.25F)), TreePlacedFeatures.DARK_OAK_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> DEEP_FOREST_TREES = registerConfigured("deep_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.25F), new RandomFeatureEntry(ModPlacedFeatures.BIG_YMPE_TREE, 0.25F)), TreePlacedFeatures.DARK_OAK_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> CONIFEROUS_FOREST_TREES = registerConfigured("coniferous_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.25F)), TreePlacedFeatures.SPRUCE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> DEEP_CONIFEROUS_FOREST_TREES = registerConfigured("deep_coniferous_forest_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.25F), new RandomFeatureEntry(ModPlacedFeatures.BIG_YMPE_TREE, 0.25F)), TreePlacedFeatures.SPRUCE_CHECKED));
    public static final RegistryEntry<ConfiguredFeature<RandomFeatureConfig, ?>> OVERGROWTH_CLEARING_TREES = registerConfigured("overgrowth_clearing_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfig(List.of(new RandomFeatureEntry(PlacedFeatures.createEntry(ModConfiguredFeatures.YMPE_TREE), 0.5F)), TreePlacedFeatures.SPRUCE_CHECKED));

    public static final RegistryEntry<PlacedFeature> DEEP_ROOF_TREES_PLACED = registerPlaced("deep_roof_trees", DEEP_ROOF_TREES, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(8, 0.5f, 3)));
    public static final RegistryEntry<PlacedFeature> DEEP_CONIFEROUS_ROOF_TREES_PLACED = registerPlaced("deep_coniferous_roof_trees", DEEP_CONIFEROUS_ROOF_TREES, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(7, 0.5f, 2)));
    public static final RegistryEntry<PlacedFeature> FOREST_TREES_PLACED = registerPlaced("forest_trees", FOREST_TREES, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(3, 0.1f, 1)));
    public static final RegistryEntry<PlacedFeature> DEEP_FOREST_TREES_PLACED = registerPlaced("deep_forest_trees", DEEP_FOREST_TREES, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(4, 0.25f, 2)));
    public static final RegistryEntry<PlacedFeature> CONIFEROUS_FOREST_TREES_PLACED = registerPlaced("coniferous_forest_trees", CONIFEROUS_FOREST_TREES, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(5, 0.25f, 2)));
    public static final RegistryEntry<PlacedFeature> DEEP_CONIFEROUS_FOREST_TREES_PLACED = registerPlaced("deep_coniferous_forest_trees", DEEP_CONIFEROUS_FOREST_TREES, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(4, 0.25f, 2)));
    public static final RegistryEntry<PlacedFeature> OVERGROWTH_CLEARING_TREES_PLACED = registerPlaced("overgrowth_clearing_trees", OVERGROWTH_CLEARING_TREES, VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.5f, 2)));

}
