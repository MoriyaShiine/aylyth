package moriyashiine.aylyth.common.registry;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

import static moriyashiine.aylyth.common.registry.AylythItems.*;

// TODO better name
public final class AylythCompositing {
    private AylythCompositing() {}

    public static void register() {
        var registry = CompostingChanceRegistry.INSTANCE;

        registry.add(YMPE_LEAVES, 0.3f);
        registry.add(YMPE_SAPLING, 0.3f);
        registry.add(POMEGRANATE_LEAVES, 0.3f);
        registry.add(POMEGRANATE_SAPLING, 0.3f);
        registry.add(WRITHEWOOD_LEAVES, 0.3f);
        registry.add(WRITHEWOOD_SAPLING, 0.3f);
        registry.add(OAK_STREWN_LEAVES, 0.3f);
        registry.add(YMPE_STREWN_LEAVES, 0.3f);
        registry.add(JACK_O_LANTERN_MUSHROOM, 0.3f);
        registry.add(GHOSTCAP_MUSHROOM, 0.3f);
        registry.add(POMEGRANATE, 0.3f);
    }
}
