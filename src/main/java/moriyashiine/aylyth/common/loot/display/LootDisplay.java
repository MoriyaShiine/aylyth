package moriyashiine.aylyth.common.loot.display;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import net.minecraft.item.ItemStack;

public interface LootDisplay {
    Codec<LootDisplay> CODEC = AylythRegistries.LOOT_TABLE_DISPLAY_TYPE.getCodec().dispatch(LootDisplay::type, Type::codec);
    Codec<LootDisplay> NETWORK_CODEC = AylythRegistries.LOOT_TABLE_DISPLAY_TYPE.getCodec().dispatch(LootDisplay::type, Type::networkCodec);

    ItemStack outputs();

    Type<? extends LootDisplay> type();

    record Type<T extends LootDisplay>(Codec<T> codec, Codec<T> networkCodec) {}
}
