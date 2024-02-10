package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.generator.feature.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModFeatures {
    public static void init() {}

    public static final SpringFeature SPRING_FEATURE = Registry.register(Registries.FEATURE, AylythUtil.id("spring"), new SpringFeature());
    public static final SeepFeature SEEP_FEATURE = Registry.register(Registries.FEATURE, AylythUtil.id("seep"), new SeepFeature());
    public static final BushFeature BUSH_FEATURE = Registry.register(Registries.FEATURE, AylythUtil.id("bushes"), new BushFeature());
    public static final LeafPileFeature LEAF_PILE_FEATURE = Registry.register(Registries.FEATURE, AylythUtil.id("leaf_pile"), new LeafPileFeature());
    public static final StrewnLeavesFeature STREWN_LEAVES_FEATURE = Registry.register(Registries.FEATURE, AylythUtil.id("strewn_leaves"), new StrewnLeavesFeature());
    public static final HorizontalFacingFeature HORIZONTAL_FACING_FEATURE = Registry.register(Registries.FEATURE, AylythUtil.id("horizontal_facing_feature"), new HorizontalFacingFeature());
    public static final DoubleBlockFeature DOUBLE_BLOCK_FEATURE = Registry.register(Registries.FEATURE, AylythUtil.id("double_block_feature"), new DoubleBlockFeature());
}
