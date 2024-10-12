package moriyashiine.aylyth.common.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;

public final class AylythModifyLootTableHandler {
    private AylythModifyLootTableHandler() {}

    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (!id.getPath().startsWith("additions/")) {
                LootTable table = lootManager.getLootTable(id.withPrefixedPath("additions/"));
                if (table != LootTable.EMPTY) {
                    for (LootPool pool : table.pools) {
                        tableBuilder.pool(pool);
                    }
                }
            }
        });
    }
}
