package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.generator.AylthianTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.BigYmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.YmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.feature.*;
import moriyashiine.aylyth.mixin.TreeDecoratorTypeAccessor;
import moriyashiine.aylyth.mixin.TrunkPlacerTypeAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModFeatures {

    public static void init() {}

    public static final BlockStateProvider YMPE_LOG_PROVIDER = new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.YMPE_SUITE.log.block().getDefaultState(), 15).add(ModBlocks.FRUIT_BEARING_YMPE_LOG.getDefaultState(), 1).build());
    public static final SpringFeature SPRING_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":spring", new SpringFeature());
    public static final SeepFeature SEEP_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":seep", new SeepFeature());
    public static final BushFeature BUSH_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":bushes", new BushFeature());
    public static final LeafPileFeature LEAF_PILE_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":leaf_pile", new LeafPileFeature());
    public static final StrewnLeavesFeature STREWN_LEAVES_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":strewn_leaves", new StrewnLeavesFeature());
    public static final TrunkPlacerType<AylthianTrunkPlacer> AYLYTHIAN_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":aylythian_trunk_placer", AylthianTrunkPlacer.CODEC);
    public static final TrunkPlacerType<YmpeTrunkPlacer> YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":ympe_trunk_placer", YmpeTrunkPlacer.CODEC);
    public static final TrunkPlacerType<BigYmpeTrunkPlacer> BIG_YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":big_ympe_trunk_placer", BigYmpeTrunkPlacer.CODEC);
    public static final TreeDecoratorType<GrapeVineDecorator> GRAPE_VINE = TreeDecoratorTypeAccessor.register(Aylyth.MOD_ID + ":grape_vine_decorator", GrapeVineDecorator.CODEC);
}
