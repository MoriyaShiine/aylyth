package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.generator.*;
import moriyashiine.aylyth.common.world.generator.feature.*;
import moriyashiine.aylyth.mixin.FoliagePlacerTypeAccessor;
import moriyashiine.aylyth.mixin.TreeDecoratorTypeAccessor;
import moriyashiine.aylyth.mixin.TrunkPlacerTypeAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModFeatures {

    public static void init() {}

    public static final BlockStateProvider YMPE_LOG_PROVIDER = new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.YMPE_BLOCKS.log.getDefaultState(), 15).add(ModBlocks.FRUIT_BEARING_YMPE_LOG.getDefaultState(), 1).build());
    public static final SpringFeature SPRING_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":spring", new SpringFeature());
    public static final SeepFeature SEEP_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":seep", new SeepFeature());
    public static final BushFeature BUSH_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":bushes", new BushFeature());
    public static final LeafPileFeature LEAF_PILE_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":leaf_pile", new LeafPileFeature());
    public static final StrewnLeavesFeature STREWN_LEAVES_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":strewn_leaves", new StrewnLeavesFeature());
    public static final HorizontalFacingFeature HORIZONTAL_FACING_FEATURE = Registry.register(Registry.FEATURE, Aylyth.MOD_ID + ":horizontal_facing_feature", new HorizontalFacingFeature());

    public static final TrunkPlacerType<AylthianTrunkPlacer> AYLYTHIAN_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":aylythian_trunk_placer", AylthianTrunkPlacer.CODEC);
    public static final TrunkPlacerType<YmpeTrunkPlacer> YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":ympe_trunk_placer", YmpeTrunkPlacer.CODEC);
    public static final TrunkPlacerType<BigYmpeTrunkPlacer> BIG_YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":big_ympe_trunk_placer", BigYmpeTrunkPlacer.CODEC);
    public static final TrunkPlacerType<PomegranateTrunkPlacer> POMEGRANATE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":pomegranate_trunk_placer", PomegranateTrunkPlacer.CODEC);
    public static final TrunkPlacerType<WrithewoodTrunkPlacer> WRITHEWOOD_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":writhewood_trunk_placer", WrithewoodTrunkPlacer.CODEC);
    public static final TreeDecoratorType<GrapeVineDecorator> GRAPE_VINE = TreeDecoratorTypeAccessor.register(Aylyth.MOD_ID + ":grape_vine_decorator", GrapeVineDecorator.CODEC);
    public static final FoliagePlacerType<PomegranateFoliagePlacer> POMEGRANATE_FOLIAGE_PLACER = FoliagePlacerTypeAccessor.register(Aylyth.MOD_ID + ":pomegranate_foliage_placer", PomegranateFoliagePlacer.CODEC);
}
