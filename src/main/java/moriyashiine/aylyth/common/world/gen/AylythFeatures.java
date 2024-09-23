package moriyashiine.aylyth.common.world.gen;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.gen.features.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public interface AylythFeatures {

    SpringFeature SPRING_FEATURE = register("spring", new SpringFeature());
    SeepFeature SEEP_FEATURE = register("seep", new SeepFeature());
    BushFeature BUSH_FEATURE = register("bushes", new BushFeature());
    LeafPileFeature LEAF_PILE_FEATURE = register("leaf_pile", new LeafPileFeature());
    StrewnLeavesFeature STREWN_LEAVES_FEATURE = register("strewn_leaves", new StrewnLeavesFeature());
    HorizontalFacingFeature HORIZONTAL_FACING_FEATURE = register("horizontal_facing", new HorizontalFacingFeature());
    DoubleBlockFeature DOUBLE_BLOCK_FEATURE = register("double_block", new DoubleBlockFeature());
    GiantMushroomFeature GIANT_MUSHROOM = register("giant_mushroom", new GiantMushroomFeature(GiantMushroomFeature.GiantMushroomConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, Aylyth.id(name), feature);
    }

    // Load static initializer
    static void register() {}
}
