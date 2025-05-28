package moriyashiine.aylyth.common.loot;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.loot.condition.ArmorLootCondition;
import moriyashiine.aylyth.common.loot.condition.CriticalHitLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythLootConditionTypes {

    LootConditionType ARMOR = register("armor", ArmorLootCondition.CODEC);
    LootConditionType CRITICAL_HIT = register("critical_hit", CriticalHitLootCondition.CODEC);

    private static <C extends LootCondition> LootConditionType register(String name, MapCodec<C> codec) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, Aylyth.id(name), new LootConditionType(codec));
    }

    // Load static initializer
    static void register() {}
}
