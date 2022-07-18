package moriyashiine.aylyth.common.registry;

import com.google.common.collect.ImmutableList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.generator.AylthianTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.BigYmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.YmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.feature.BushFeature;
import moriyashiine.aylyth.common.world.generator.feature.GrapeVineDecorator;
import moriyashiine.aylyth.common.world.generator.feature.SeepFeature;
import moriyashiine.aylyth.common.world.generator.feature.SpringFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class ModConfiguredFeatures {

    private static final BlockStateProvider YMPE_LOG_PROVIDER = new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.YMPE_LOG.getDefaultState(), 15).add(ModBlocks.FRUIT_BEARING_YMPE_LOG.getDefaultState(), 1).build());
    public static final SpringFeature SPRING_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":spring", new SpringFeature());
    public static final SeepFeature SEEP_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":seep", new SeepFeature());
    public static final BushFeature BUSH_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":bushes", new BushFeature());

    public static void init() {}

    private static <F extends Feature<C>, C extends FeatureConfig> RegistryEntry<ConfiguredFeature<C, ?>> register(String id, F feature, C config) {
        return ConfiguredFeatures.register(Aylyth.MOD_ID + ":" + id, feature, config);
    }

    private static RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> registerTree(String id, TreeFeatureConfig config) {
        return register(id, Feature.TREE, config);
    }

    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> AYLYTHIAN_DARK_OAK = registerTree("aylythian_dark_oak", new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(), SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().decorators(ImmutableList.of(new GrapeVineDecorator())).build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> AYLYTHIAN_MEGA_DARK_OAK = registerTree("aylythian_mega_dark_oak", new TreeFeatureConfig.Builder(SimpleBlockStateProvider.of(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(18, 6, 7), SimpleBlockStateProvider.of(Blocks.DARK_OAK_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new GrapeVineDecorator())).ignoreVines().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> YMPE_TREE = registerTree("ympe_tree", new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new YmpeTrunkPlacer(), SimpleBlockStateProvider.of(ModBlocks.YMPE_LEAVES.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
    public static final RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>> BIG_YMPE_TREE = registerTree("big_ympe_tree", new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new BigYmpeTrunkPlacer(), SimpleBlockStateProvider.of(ModBlocks.YMPE_LEAVES.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());

    public static final RegistryEntry<ConfiguredFeature<SingleStateFeatureConfig, ?>> SPRING = register("spring", SPRING_FEATURE, new SingleStateFeatureConfig(Blocks.WATER.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<DefaultFeatureConfig, ?>> BUSHES = register("bushes", BUSH_FEATURE, FeatureConfig.DEFAULT);
    public static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> AYLYTH_WEEDS = register("aylyth_weeds", Feature.FLOWER, createRandomPatchFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ANTLER_SHOOTS.getDefaultState(), 5).add(ModBlocks.GRIPWEED.getDefaultState(), 2).build()), 64));
    //public static final ConfiguredFeature<?, ?> CLEARING_FLOWERS = todo flower generators

    public static final RegistryEntry<ConfiguredFeature<SeepFeature.SeepFeatureConfig, ?>> OAK_SEEP = register("oak_seep", SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.OAK_LOG.getDefaultState(), ModBlocks.OAK_SEEP.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<SeepFeature.SeepFeatureConfig, ?>> SPRUCE_SEEP = register("spruce_seep", SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.SPRUCE_LOG.getDefaultState(), ModBlocks.SPRUCE_SEEP.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<SeepFeature.SeepFeatureConfig, ?>> DARK_OAK_SEEP = register("dark_oak_seep", SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(Blocks.DARK_OAK_LOG.getDefaultState(), ModBlocks.DARK_OAK_SEEP.getDefaultState()));
    public static final RegistryEntry<ConfiguredFeature<SeepFeature.SeepFeatureConfig, ?>> YMPE_SEEP = register("ympe_seep", SEEP_FEATURE, new SeepFeature.SeepFeatureConfig(ModBlocks.YMPE_LOG.getDefaultState(), ModBlocks.YMPE_SEEP.getDefaultState()));

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatures.createRandomPatchFeatureConfig(tries, PlacedFeatures.createEntry((Feature)Feature.SIMPLE_BLOCK, (FeatureConfig)(new SimpleBlockFeatureConfig(block))));
    }
}
