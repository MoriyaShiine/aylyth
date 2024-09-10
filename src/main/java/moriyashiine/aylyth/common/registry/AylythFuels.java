package moriyashiine.aylyth.common.registry;

import net.fabricmc.fabric.api.registry.FuelRegistry;

import static moriyashiine.aylyth.common.registry.AylythItems.*;

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
    }
}
