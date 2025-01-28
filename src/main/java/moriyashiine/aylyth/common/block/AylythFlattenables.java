package moriyashiine.aylyth.common.block;

import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.minecraft.block.Blocks;

public final class AylythFlattenables {
    private AylythFlattenables() {}

    public static void register() {
        FlattenableBlockRegistry.register(AylythBlocks.DARK_PODZOL, Blocks.DIRT_PATH.getDefaultState());
    }
}
