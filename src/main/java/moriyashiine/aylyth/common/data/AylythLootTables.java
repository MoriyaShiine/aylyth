package moriyashiine.aylyth.common.data;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface AylythLootTables {
    RegistryKey<LootTable> FRUIT_BEARING_YMPE_LOG = bind("harvest/fruit_bearing_ympe_log");
    RegistryKey<LootTable> NEPHRITIC_CHTHONIA_WOOD = bind("harvest/nephritic_chthonia_wood");

    private static RegistryKey<LootTable> bind(String name) {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, Aylyth.id(name));
    }
}
