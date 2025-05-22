package moriyashiine.aylyth.common.loot;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

public final class AylythModifyLootTableHandler {
    private AylythModifyLootTableHandler() {}

    public static void register() {
        // TODO: Figure out how to mend this
//        LootTableEvents.MODIFY.register((RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) -> {
//            if (!key.getValue().getPath().startsWith("additions/")) {
//                LootTable table = lootManager.getLootTable(id.withPrefixedPath("additions/"));
//                if (table != LootTable.EMPTY) {
//                    for (LootPool pool : table.pools) {
//                        tableBuilder.pool(pool);
//                    }
//                }
//            }
//        });
    }
}
