package moriyashiine.aylyth.common.loot;

import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

public final class AylythModifyLootTableHandler {
    private AylythModifyLootTableHandler() {}

    public static void register() {
        LootTableEvents.MODIFY.register((RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) -> {
            registries.getOrThrow(AylythRegistryKeys.LOOT_TABLE_MODIFIER)
                    .getOptional(RegistryKey.of(AylythRegistryKeys.LOOT_TABLE_MODIFIER, key.getValue()))
                    .ifPresent(ref -> {
                        ref.value().modifyTable(tableBuilder, source, registries);
                    });
        });
    }
}
