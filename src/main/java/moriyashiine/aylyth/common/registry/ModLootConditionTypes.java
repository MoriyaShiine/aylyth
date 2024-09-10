package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.lootcondition.ScionIsPlayerLootCondition;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.JsonSerializer;

public interface ModLootConditionTypes {

    LootConditionType SCION_IS_PLAYER = register("scion_is_player", new ScionIsPlayerLootCondition.Serializer());

    private static <C extends LootCondition> LootConditionType register(String name, JsonSerializer<C> serializer) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, AylythUtil.id(name), new LootConditionType(serializer));
    }

    static void register() {}
}