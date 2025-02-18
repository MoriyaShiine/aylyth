package moriyashiine.aylyth.common.item;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

import static moriyashiine.aylyth.common.item.AylythItems.*;

public final class AylythCompostingChances {
    private AylythCompostingChances() {}

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
        registry.add(GREEN_AYLYTHIAN_OAK_SAPLING, 0.3f);
        registry.add(GREEN_AYLYTHIAN_OAK_LEAVES, 0.3f);
        registry.add(ORANGE_AYLYTHIAN_OAK_SAPLING, 0.3f);
        registry.add(ORANGE_AYLYTHIAN_OAK_LEAVES, 0.3f);
        registry.add(RED_AYLYTHIAN_OAK_SAPLING, 0.3f);
        registry.add(RED_AYLYTHIAN_OAK_LEAVES, 0.3f);
        registry.add(BROWN_AYLYTHIAN_OAK_SAPLING, 0.3f);
        registry.add(BROWN_AYLYTHIAN_OAK_LEAVES, 0.3f);
    }
}
