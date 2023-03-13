package moriyashiine.aylyth.datagen.worldgen.features;

import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.floatprovider.ConstantFloatProvider;
import net.minecraft.util.math.floatprovider.TrapezoidFloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

public class ModCarvers {

    public static void datagenInit() {}

    private static <T extends CarverConfig> RegistryEntry<ConfiguredCarver<T>> register(String id, ConfiguredCarver<T> carver) {
        return BuiltinRegistries.addCasted(BuiltinRegistries.CONFIGURED_CARVER, "aylyth:" + id, carver);
    }

    public static final RegistryEntry<ConfiguredCarver<CaveCarverConfig>> CAVES = register(
            "caves",
            Carver.CAVE.configure(
                    new CaveCarverConfig(
                            0.35F,
                            UniformHeightProvider.create(YOffset.aboveBottom(8), YOffset.fixed(180)),
                            UniformFloatProvider.create(0.1F, 1.9F),
                            YOffset.getBottom(),
                            CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.getDefaultState()),
                            Registry.BLOCK.getOrCreateEntryList(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
                            UniformFloatProvider.create(0.7F, 1.4F),
                            UniformFloatProvider.create(0.8F, 1.3F),
                            UniformFloatProvider.create(-1.0F, -0.4F)
                    )
            )
    );

    public static final RegistryEntry<ConfiguredCarver<RavineCarverConfig>> CANYONS = register(
            "canyons",
            Carver.RAVINE.configure(
                    new RavineCarverConfig(
                            0.01F,
                            UniformHeightProvider.create(YOffset.fixed(10), YOffset.fixed(67)),
                            ConstantFloatProvider.create(3.0F),
                            YOffset.getBottom(),
                            CarverDebugConfig.create(false, Blocks.WARPED_BUTTON.getDefaultState()),
                            Registry.BLOCK.getOrCreateEntryList(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
                            UniformFloatProvider.create(-0.125F, 0.125F),
                            new RavineCarverConfig.Shape(
                                    UniformFloatProvider.create(0.75F, 1.0F), TrapezoidFloatProvider.create(0.0F, 6.0F, 2.0F), 3, UniformFloatProvider.create(0.75F, 1.0F), 1.0F, 0.0F
                            )
                    )
            )
    );
}
