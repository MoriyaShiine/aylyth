package moriyashiine.aylyth.common.loot;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.loot.modifier.AddPoolsModifier;
import moriyashiine.aylyth.common.loot.modifier.LootTableModifier;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import net.minecraft.registry.Registry;

public interface LootTableModifiers {
    MapCodec<AddPoolsModifier> ADD_POOLS = register("add_pools", AddPoolsModifier.CODEC);

    private static <T extends LootTableModifier> MapCodec<T> register(String name, MapCodec<T> codec) {
        return Registry.register(AylythRegistries.LOOT_TABLE_MODIFIER_TYPE, Aylyth.id(name), codec);
    }

    static void register() {}
}
