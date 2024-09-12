package moriyashiine.aylyth.common.block;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;

import static moriyashiine.aylyth.common.block.AylythBlocks.*;

public final class AylythStrippables {
    private AylythStrippables() {}

    public static void register() {
        StrippableBlockRegistry.register(YMPE_LOG, YMPE_STRIPPED_LOG);
        StrippableBlockRegistry.register(YMPE_WOOD, YMPE_STRIPPED_WOOD);
        StrippableBlockRegistry.register(FRUIT_BEARING_YMPE_LOG, YMPE_STRIPPED_LOG);

        StrippableBlockRegistry.register(POMEGRANATE_LOG, POMEGRANATE_STRIPPED_LOG);
        StrippableBlockRegistry.register(POMEGRANATE_WOOD, POMEGRANATE_STRIPPED_WOOD);

        StrippableBlockRegistry.register(WRITHEWOOD_LOG, WRITHEWOOD_STRIPPED_LOG);
        StrippableBlockRegistry.register(WRITHEWOOD_WOOD, WRITHEWOOD_STRIPPED_WOOD);
    }
}
