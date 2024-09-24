package moriyashiine.aylyth.datagen.common.loot;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
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
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public final class AylythStripLootProvider extends SimpleFabricLootTableProvider {
    public AylythStripLootProvider(FabricDataOutput output) {
        super(output, AylythLootContextTypes.STRIP);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {
        generate((id, builder) -> exporter.accept(id.withPrefixedPath("strip/"), builder));
    }

    private void generate(BiConsumer<Identifier, LootTable.Builder> exporter) {
        exporter.accept(Aylyth.id("bark_from_logs_or_wood"), barkFromLogsOrWood());
    }

    private LootTable.Builder barkFromLogsOrWood() {
        return LootTable.builder().type(AylythLootContextTypes.STRIP).pool(
                LootPool.builder()
                        .with(ItemEntry.builder(AylythItems.BARK)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                .conditionally(MatchToolLootCondition.builder(
                                        ItemPredicate.Builder.create().tag(AylythItemTags.DAGGERS)
                                ))
                                .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().block(BlockPredicate.Builder.create().tag(BlockTags.LOGS).build()))))
        );
    }
}
