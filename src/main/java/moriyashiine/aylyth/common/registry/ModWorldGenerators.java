package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.generator.AylthianTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.BigYmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.YmpeTrunkPlacer;
import moriyashiine.aylyth.mixin.TrunkPlacerTypeAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModWorldGenerators {
	private static final BlockStateProvider YMPE_LOG_PROVIDER= new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.YMPE_LOG.getDefaultState(), 15).add(ModBlocks.FRUIT_BEARING_YMPE_LOG.getDefaultState(), 1).build());
	public static final TrunkPlacerType<AylthianTrunkPlacer> AYLYTHIAN_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":aylythian_trunk_placer", AylthianTrunkPlacer.CODEC);
	public static final TrunkPlacerType<YmpeTrunkPlacer> YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":ympe_trunk_placer", YmpeTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BigYmpeTrunkPlacer> BIG_YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":big_ympe_trunk_placer", BigYmpeTrunkPlacer.CODEC);
	
	public static final ConfiguredFeature<TreeFeatureConfig, ?> AYLYTHIAN_DARK_OAK = Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(), new SimpleBlockStateProvider(Blocks.DARK_OAK_LEAVES.getDefaultState()), new SimpleBlockStateProvider(Blocks.DARK_OAK_SAPLING.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> YMPE_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new YmpeTrunkPlacer(), new SimpleBlockStateProvider(ModBlocks.YMPE_LEAVES.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.YMPE_SAPLING.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> BIG_YMPE_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new BigYmpeTrunkPlacer(), new SimpleBlockStateProvider(ModBlocks.YMPE_LEAVES.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.YMPE_SAPLING.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
	
	public static void init() {
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "aylythian_dark_oak"), AYLYTHIAN_DARK_OAK);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "ympe_tree"), YMPE_TREE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "big_ympe_tree"), BIG_YMPE_TREE);
	}
}
