package moriyashiine.aylyth.datagen.common.loot;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

public final class AylythHarvestLootGenerator extends SimpleLootGenerator {
    public AylythHarvestLootGenerator(RegistryWrapper.WrapperLookup registries) {
        super(registries);
    }

    @Override
    protected void generateLoot() {
        addDrop(Aylyth.id("fruit_bearing_ympe_log"), fruitBearingYmpeLog());
        addDrop(Aylyth.id("nephritic_chthonia_wood"), nephriticChthoniaWood());
    }

    private LootTable.Builder fruitBearingYmpeLog() {
        return LootTable.builder().type(AylythLootContextTypes.HARVEST).pool(
                LootPool.builder()
                        .with(AlternativeEntry.builder(
                                ItemEntry.builder(AylythItems.YMPE_FRUIT)
                                        .conditionally(MatchToolLootCondition.builder(
                                                ItemPredicate.Builder.create().tag(registries.getOrThrow(RegistryKeys.ITEM), AylythItemTags.YMPE_FRUIT_HARVESTERS)
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

    @Override
    protected void addDrop(Identifier id, LootTable.Builder builder) {
        super.addDrop(id.withPrefixedPath("harvest/"), builder);
    }
}
