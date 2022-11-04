package moriyashiine.aylyth.common.registry;

import com.google.common.collect.ImmutableList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.generator.*;
import moriyashiine.aylyth.common.world.generator.feature.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class ModConfiguredFeatures {

    public static void datagenInit() {}

    private static <F extends Feature<C>, C extends FeatureConfig> RegistryEntry<ConfiguredFeature<C, ?>> register(String id, F feature, C config) {
        return ConfiguredFeatures.register(Aylyth.MOD_ID + ":" + id, feature, config);
    }

    private static RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> registerTree(String id, TreeFeatureConfig config) {
        return register(id, Feature.TREE, config);
    }

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> AYLYTHIAN_DARK_OAK = registerTree("aylythian_dark_oak", new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(), SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().decorators(ImmutableList.of(new GrapeVineDecorator())).build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> AYLYTHIAN_MEGA_DARK_OAK = registerTree("aylythian_mega_dark_oak", new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(18, 6, 7), SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new GrapeVineDecorator())).ignoreVines().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> YMPE_TREE = registerTree("ympe_tree", new TreeFeatureConfig.Builder(ModFeatures.YMPE_LOG_PROVIDER, new YmpeTrunkPlacer(), SimpleBlockStateProvider.of(ModBlocks.YMPE_LEAVES.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> BIG_YMPE_TREE = registerTree("big_ympe_tree", new TreeFeatureConfig.Builder(ModFeatures.YMPE_LOG_PROVIDER, new BigYmpeTrunkPlacer(), SimpleBlockStateProvider.of(ModBlocks.YMPE_LEAVES.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> POMEGRANATE_TREE = registerTree("pomegranate_tree", new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(ModBlocks.POMEGRANATE_BLOCKS.log), new PomegranateTrunkPlacer(5, 0, 0), SimpleBlockStateProvider.of(ModBlocks.POMEGRANATE_LEAVES.getDefaultState().with(Properties.PERSISTENT, false)), new PomegranateFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> WRITHEWOOD_TREE = registerTree("writhewood_tree", new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(ModBlocks.WRITHEWOOD_BLOCKS.log), new WrithewoodTrunkPlacer(6, 4, 14), SimpleBlockStateProvider.of(ModBlocks.WRITHEWOOD_LEAVES), new WrithewoodFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(1)), new TwoLayersFeatureSize(2, 1, 1)).ignoreVines().build());

    public static final RegistryEntry<ConfiguredFeature<SingleStateFeatureConfig, ?>> SPRING = register("spring", ModFeatures.SPRING_FEATURE, new SingleStateFeatureConfig(Blocks.WATER.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> BUSHES = register("bushes", ModFeatures.BUSH_FEATURE, FeatureConfig.DEFAULT);
    public static final RegistryEntry<ConfiguredFeature<LeafPileFeature.LeafPileConfig, ?>> OAK_LEAF_PILE = register("oak_leaf_pile", ModFeatures.LEAF_PILE_FEATURE, new LeafPileFeature.LeafPileConfig(Blocks.DARK_OAK_LEAVES, ModBlocks.OAK_STREWN_LEAVES));
    public static final RegistryEntry<ConfiguredFeature<LeafPileFeature.LeafPileConfig, ?>> YMPE_LEAF_PILE = register("ympe_leaf_pile", ModFeatures.LEAF_PILE_FEATURE, new LeafPileFeature.LeafPileConfig(ModBlocks.YMPE_LEAVES, ModBlocks.YMPE_STREWN_LEAVES));
    public static final RegistryEntry<ConfiguredFeature<StrewnLeavesFeature.StrewnLeavesConfig, ?>> OAK_STREWN_LEAVES = register("oak_strewn_leaves", ModFeatures.STREWN_LEAVES_FEATURE, new StrewnLeavesFeature.StrewnLeavesConfig(Blocks.DARK_OAK_LEAVES, ModBlocks.OAK_STREWN_LEAVES.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<StrewnLeavesFeature.StrewnLeavesConfig, ?>> YMPE_STREWN_LEAVES = register("ympe_strewn_leaves", ModFeatures.STREWN_LEAVES_FEATURE, new StrewnLeavesFeature.StrewnLeavesConfig(ModBlocks.YMPE_LEAVES, ModBlocks.YMPE_STREWN_LEAVES.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> AYLYTH_WEEDS = register("aylyth_weeds", Feature.FLOWER, createRandomPatchFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(ModBlocks.GRIPWEED.getDefaultState(), 2).build()), 64));
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> MARIGOLDS = register("marigolds", Feature.FLOWER, createRandomPatchFeatureConfig(BlockStateProvider.of(ModBlocks.MARIGOLD), 64));
    public static final RegistryEntry<ConfiguredFeature<HorizontalFacingFeature.HorizontalFacingBlockFeatureConfig, ?>> SHELF_JACK_O_LANTERN_MUSHROOMS = register("shelf_jack_o_lantern_mushrooms", ModFeatures.HORIZONTAL_FACING_FEATURE, new HorizontalFacingFeature.HorizontalFacingBlockFeatureConfig(ModBlocks.SHELF_JACK_O_LANTERN_MUSHROOM, ModTags.JACK_O_LANTERN_GENERATE_ON));
    public static final RegistryEntry<ConfiguredFeature<SimpleBlockFeatureConfig, ?>> GHOSTCAP_MUSHROOM = register("ghostcap_mushroom", Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.GHOSTCAP_MUSHROOM)));
    //public static final ConfiguredFeature<?, ?> CLEARING_FLOWERS = todo flower generators

    public static final RegistryEntry<ConfiguredFeature<SeepFeature.SeepFeatureConfig, ?>> OAK_SEEP = register("oak_seep", ModFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.OAK_LOG.getDefaultState(), ModBlocks.OAK_SEEP.getDefaultState(), ModBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));
    public static final RegistryEntry<ConfiguredFeature<SeepFeature.SeepFeatureConfig, ?>> SPRUCE_SEEP = register("spruce_seep", ModFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), ModBlocks.SPRUCE_SEEP.getDefaultState(), ModBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));
    public static final RegistryEntry<ConfiguredFeature<SeepFeature.SeepFeatureConfig, ?>> DARK_OAK_SEEP = register("dark_oak_seep", ModFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.DARK_OAK_LOG.getDefaultState(), ModBlocks.DARK_OAK_SEEP.getDefaultState(), ModBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));
    public static final RegistryEntry<ConfiguredFeature<SeepFeature.SeepFeatureConfig, ?>> YMPE_SEEP = register("ympe_seep", ModFeatures.SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(ModBlocks.YMPE_BLOCKS.log.getDefaultState(), ModBlocks.YMPE_SEEP.getDefaultState(), ModBlocks.MARIGOLD.getDefaultState(), 5, 0.5F));

    static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }
}
