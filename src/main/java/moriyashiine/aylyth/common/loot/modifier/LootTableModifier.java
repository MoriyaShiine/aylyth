package moriyashiine.aylyth.common.loot.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryWrapper;

import java.util.function.Function;

public interface LootTableModifier {
    Codec<LootTableModifier> CODEC = AylythRegistries.LOOT_TABLE_MODIFIER_TYPE.getCodec().dispatch(LootTableModifier::getCodec, Function.identity());

    void modifyTable(LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries);

    MapCodec<? extends LootTableModifier> getCodec();
}
