package moriyashiine.aylyth.common.item;

import net.fabricmc.fabric.api.registry.FuelRegistryEvents;

import static moriyashiine.aylyth.common.item.AylythItems.*;

public interface AylythFuels {
    static void register() {
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(YMPE_FENCE, 300);
            builder.add(YMPE_FENCE_GATE, 300);
            builder.add(POMEGRANATE_FENCE, 300);
            builder.add(POMEGRANATE_FENCE_GATE, 300);
            builder.add(WRITHEWOOD_FENCE, 300);
            builder.add(WRITHEWOOD_FENCE_GATE, 300);
            builder.add(BARK, 100);
            builder.add(DARK_OAK_BRANCH, 100);
            builder.add(BARE_DARK_OAK_BRANCH, 100);
            builder.add(ORANGE_AYLYTHIAN_OAK_BRANCH, 100);
            builder.add(RED_AYLYTHIAN_OAK_BRANCH, 100);
            builder.add(BROWN_AYLYTHIAN_OAK_BRANCH, 100);
            builder.add(WRITHEWOOD_BRANCH, 100);
            builder.add(BARE_WRITHEWOOD_BRANCH, 100);
            builder.add(YMPE_BRANCH, 100);
            builder.add(BARE_YMPE_BRANCH, 100);
        });
    }
}
