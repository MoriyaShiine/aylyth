package moriyashiine.aylyth.common.loot.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public interface LootDisplay {
    Codec<LootDisplay> CODEC = AylythRegistries.LOOT_TABLE_DISPLAY_TYPE.getCodec().dispatch(LootDisplay::type, Type::codec);
    Codec<LootDisplay> NETWORK_CODEC = AylythRegistries.LOOT_TABLE_DISPLAY_TYPE.getCodec().dispatch(LootDisplay::type, Type::networkCodec);

    ItemStack outputs();

    Type<? extends LootDisplay> type();

    record Type<T extends LootDisplay>(MapCodec<T> codec, MapCodec<T> networkCodec) {}
}
