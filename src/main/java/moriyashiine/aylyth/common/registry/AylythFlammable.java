package moriyashiine.aylyth.common.registry;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

import static moriyashiine.aylyth.common.registry.AylythBlocks.*;

public final class AylythFlammable {
    private AylythFlammable() {}

    public static void register() {
        var registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(YMPE_STRIPPED_LOG, 5, 5);
        registry.add(YMPE_STRIPPED_WOOD, 5, 5);
        registry.add(YMPE_LOG, 5, 5);
        registry.add(YMPE_WOOD, 5, 5);
        registry.add(YMPE_PLANKS, 5, 20);
        registry.add(YMPE_STAIRS, 5, 20);
        registry.add(YMPE_SLAB, 5, 20);
        registry.add(YMPE_FENCE, 5, 20);
        registry.add(YMPE_FENCE_GATE, 5, 20);
        registry.add(YMPE_LEAVES, 30, 60);
        registry.add(POMEGRANATE_STRIPPED_LOG, 5, 5);
        registry.add(POMEGRANATE_STRIPPED_WOOD, 5, 5);
        registry.add(POMEGRANATE_LOG, 5, 5);
        registry.add(POMEGRANATE_WOOD, 5, 5);
        registry.add(POMEGRANATE_PLANKS, 5, 20);
        registry.add(POMEGRANATE_STAIRS, 5, 20);
        registry.add(POMEGRANATE_SLAB, 5, 20);
        registry.add(POMEGRANATE_FENCE, 5, 20);
        registry.add(POMEGRANATE_FENCE_GATE, 5, 20);
        registry.add(POMEGRANATE_LEAVES, 30, 60);
        registry.add(WRITHEWOOD_STRIPPED_LOG, 5, 5);
        registry.add(WRITHEWOOD_STRIPPED_WOOD, 5, 5);
        registry.add(WRITHEWOOD_LOG, 5, 5);
        registry.add(WRITHEWOOD_WOOD, 5, 5);
        registry.add(WRITHEWOOD_PLANKS, 5, 20);
        registry.add(WRITHEWOOD_STAIRS, 5, 20);
        registry.add(WRITHEWOOD_SLAB, 5, 20);
        registry.add(WRITHEWOOD_FENCE, 5, 20);
        registry.add(WRITHEWOOD_FENCE_GATE, 5, 20);
        registry.add(WRITHEWOOD_LEAVES, 30, 60);
        registry.add(SEEPING_WOOD, 5, 5);
        registry.add(AYLYTH_BUSH, 60, 100);
    }
}
