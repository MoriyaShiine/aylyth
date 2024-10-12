package moriyashiine.aylyth.common.loot;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.loot.conditions.ScionIsPlayerLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.JsonSerializer;

public interface AylythLootConditionTypes {

    LootConditionType SCION_IS_PLAYER = register("scion_is_player", new ScionIsPlayerLootCondition.Serializer());

    private static <C extends LootCondition> LootConditionType register(String name, JsonSerializer<C> serializer) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, Aylyth.id(name), new LootConditionType(serializer));
    }

    // Load static initializer
    static void register() {}
}
