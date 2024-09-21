package moriyashiine.aylyth.datagen.common.loot;

import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public final class AylythHarvestLootProvider extends SimpleFabricLootTableProvider {
    public AylythHarvestLootProvider(FabricDataOutput output) {
        super(output, AylythLootContextTypes.HARVEST);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {
        generate((id, builder) -> exporter.accept(id.withPrefixedPath("harvest/"), builder));
    }

    private void generate(BiConsumer<Identifier, LootTable.Builder> exporter) {
        exporter.accept(AylythUtil.id("fruit_bearing_ympe_log"), fruitBearingYmpeLog());
        exporter.accept(AylythUtil.id("nephritic_chthonia_wood"), nephriticChthoniaWood());
    }

    private LootTable.Builder fruitBearingYmpeLog() {
        return LootTable.builder().type(AylythLootContextTypes.HARVEST).pool(
                LootPool.builder()
                        .with(AlternativeEntry.builder(
                                ItemEntry.builder(AylythItems.YMPE_FRUIT)
                                        .conditionally(MatchToolLootCondition.builder(
                                                ItemPredicate.Builder.create().tag(AylythItemTags.YMPE_FRUIT_HARVESTERS)
                                        )),
                                ItemEntry.builder(AylythItems.YMPE_MUSH)
                        ))
        );
    }

    private LootTable.Builder nephriticChthoniaWood() {
        return LootTable.builder().type(AylythLootContextTypes.HARVEST).pool(
                LootPool.builder()
                        .with(ItemEntry.builder(AylythItems.NEPHRITE))
        );
    }
}
