package moriyashiine.aylyth.common.loot;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import moriyashiine.aylyth.common.loot.display.DaggerLootDisplay;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import net.minecraft.registry.Registry;

public interface LootDisplayTypes {

    LootDisplay.Type<DaggerLootDisplay> YMPE_DAGGER = register("ympe_dagger", DaggerLootDisplay.CODEC, DaggerLootDisplay.NETWORK_CODEC);

    private static <T extends LootDisplay> LootDisplay.Type<T> register(String name, Codec<T> codec, Codec<T> networkCodec) {
        return Registry.register(AylythRegistries.LOOT_TABLE_DISPLAY_TYPE, Aylyth.id(name), new LootDisplay.Type<>(codec, networkCodec));
    }

    static void register() {}
}
