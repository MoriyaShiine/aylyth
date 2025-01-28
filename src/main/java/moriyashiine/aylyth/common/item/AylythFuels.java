package moriyashiine.aylyth.common.item;

import net.fabricmc.fabric.api.registry.FuelRegistry;

import static moriyashiine.aylyth.common.item.AylythItems.*;

public final class AylythFuels {
    private AylythFuels() {}

    public static void register() {
        var registry = FuelRegistry.INSTANCE;

        registry.add(YMPE_FENCE, 300);
        registry.add(YMPE_FENCE_GATE, 300);
        registry.add(POMEGRANATE_FENCE, 300);
        registry.add(POMEGRANATE_FENCE_GATE, 300);
        registry.add(WRITHEWOOD_FENCE, 300);
        registry.add(WRITHEWOOD_FENCE_GATE, 300);
        registry.add(BARK, 100);
        registry.add(DARK_OAK_BRANCH, 100);
        registry.add(BARE_DARK_OAK_BRANCH, 100);
        registry.add(ORANGE_AYLYTHIAN_OAK_BRANCH, 100);
        registry.add(RED_AYLYTHIAN_OAK_BRANCH, 100);
        registry.add(BROWN_AYLYTHIAN_OAK_BRANCH, 100);
        registry.add(WRITHEWOOD_BRANCH, 100);
        registry.add(BARE_WRITHEWOOD_BRANCH, 100);
        registry.add(YMPE_BRANCH, 100);
        registry.add(BARE_YMPE_BRANCH, 100);
    }
}
