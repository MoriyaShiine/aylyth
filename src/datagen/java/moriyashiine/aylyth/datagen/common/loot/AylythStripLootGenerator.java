package moriyashiine.aylyth.datagen.common.loot;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

public final class AylythStripLootGenerator extends SimpleLootGenerator {
    public AylythStripLootGenerator(RegistryWrapper.WrapperLookup registries) {
        super(registries);
    }

    @Override
    protected void generateLoot() {
        addDrop(Aylyth.id("bark_from_logs_or_wood"), barkFromLogsOrWood());
    }

    private LootTable.Builder barkFromLogsOrWood() {
        return LootTable.builder().type(AylythLootContextTypes.STRIP).pool(
                LootPool.builder()
                        .with(ItemEntry.builder(AylythItems.BARK)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                .conditionally(MatchToolLootCondition.builder(
                                        ItemPredicate.Builder.create().tag(registries.getOrThrow(RegistryKeys.ITEM), AylythItemTags.DAGGERS)
                                ))
                                .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().block(BlockPredicate.Builder.create().tag(registries.getOrThrow(RegistryKeys.BLOCK), BlockTags.LOGS)))))
        );
    }

    @Override
    protected void addDrop(Identifier id, LootTable.Builder builder) {
        super.addDrop(id.withPrefixedPath("strip/"), builder);
    }
}
