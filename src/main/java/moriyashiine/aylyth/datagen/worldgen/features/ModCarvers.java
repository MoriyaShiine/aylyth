package moriyashiine.aylyth.datagen.worldgen.features;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.TrapezoidFloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

public class ModCarvers {

    public static final RegistryKey<ConfiguredCarver<?>> CAVES = RegistryKey.of(RegistryKeys.CONFIGURED_CARVER, AylythUtil.id("caves"));
    public static final RegistryKey<ConfiguredCarver<?>> CANYONS = RegistryKey.of(RegistryKeys.CONFIGURED_CARVER, AylythUtil.id("canyons"));

    public static void bootstrap(Registerable<ConfiguredCarver<?>> context) {
        context.register(CAVES, Carver.CAVE.configure(
                new CaveCarverConfig(
                        0.35F,
                        UniformHeightProvider.create(YOffset.aboveBottom(8), YOffset.fixed(180)),
                        UniformFloatProvider.create(0.1F, 1.9F),
                        YOffset.getBottom(),
                        CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.getDefaultState()),
                        Registries.BLOCK.getOrCreateEntryList(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
                        UniformFloatProvider.create(0.7F, 1.4F),
                        UniformFloatProvider.create(0.8F, 1.3F),
                        UniformFloatProvider.create(-1.0F, -0.4F)
                )
        ));
        context.register(CANYONS, Carver.RAVINE.configure(
                new RavineCarverConfig(
                        0.01F,
                        UniformHeightProvider.create(YOffset.fixed(10), YOffset.fixed(67)),
                        ConstantFloatProvider.create(3.0F),
                        YOffset.getBottom(),
                        CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()),
                        Registries.BLOCK.getOrCreateEntryList(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
                        UniformFloatProvider.create(-0.125F, 0.125F),
                        new RavineCarverConfig.Shape(
                                UniformFloatProvider.create(0.75F, 1.0F), TrapezoidFloatProvider.create(0.0F, 6.0F, 2.0F), 3, UniformFloatProvider.create(0.75F, 1.0F), 1.0F, 0.0F
                        )
                )
        ));
    }
}
