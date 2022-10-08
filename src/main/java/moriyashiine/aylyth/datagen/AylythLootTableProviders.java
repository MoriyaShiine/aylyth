package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;

public class AylythLootTableProviders {

    public static void registerProviders(FabricDataGenerator dataGenerator) {
        dataGenerator.addProvider(BlockLoot::new);
    }

    public static class BlockLoot extends FabricBlockLootTableProvider {

        protected BlockLoot(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateBlockLootTables() {
            addDrop(ModBlocks.MARIGOLD);
            addPottedPlantDrop(ModBlocks.MARIGOLD_POTTED);
            addDrop(ModBlocks.OAK_STREWN_LEAVES, dropsWithShears(ModBlocks.OAK_STREWN_LEAVES).pool(LootPool.builder().conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))))).rolls(ConstantLootNumberProvider.create(1.0f)).with(ItemEntry.builder(ModBlocks.OAK_STREWN_LEAVES))));
        }
    }
}
