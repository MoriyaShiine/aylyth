package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.block.StrewnLeavesBlock;
import moriyashiine.aylyth.common.registry.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;

public class AylythLootTableProviders {

    public static void registerProviders(FabricDataGenerator dataGenerator) {
        dataGenerator.addProvider(BlockLoot::new);
    }


    public static class BlockLoot extends FabricBlockLootTableProvider {

        private final LootCondition.Builder withSilkTouch = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));

        protected BlockLoot(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateBlockLootTables() {
            addDrop(ModBlocks.MARIGOLD);
            addPottedPlantDrop(ModBlocks.MARIGOLD_POTTED);
            addDrop(ModBlocks.OAK_STREWN_LEAVES, this::strewnLeaves);
            addDrop(ModBlocks.YMPE_STREWN_LEAVES, this::strewnLeaves);
        }

        private LootTable.Builder strewnLeaves(Block block) {
            return LootTable.builder()
                    .pool(
                            LootPool.builder()
                            .conditionally(EntityPropertiesLootCondition.create(LootContext.EntityTarget.THIS))
                                    .with(
                                            AlternativeEntry.builder(
                                                    AlternativeEntry.builder(StrewnLeavesBlock.LEAVES.getValues(),
                                                            integer -> ItemEntry.builder(block)
                                                                    .conditionally(BlockStatePropertyLootCondition.builder(block)
                                                                            .properties(StatePredicate.Builder.create().exactMatch(StrewnLeavesBlock.LEAVES, integer))
                                                                    )
                                                                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(integer+1)))
                                                                    .conditionally(withSilkTouch)
                                                    ),
                                                    AlternativeEntry.builder(StrewnLeavesBlock.LEAVES.getValues(),
                                                            integer -> ItemEntry.builder(block)
                                                                    .conditionally(BlockStatePropertyLootCondition.builder(block)
                                                                            .properties(StatePredicate.Builder.create().exactMatch(StrewnLeavesBlock.LEAVES, integer))
                                                                    )
                                                                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(integer+1)))
                                                                    .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS)))
                                                    ),
                                                    AlternativeEntry.builder(StrewnLeavesBlock.LEAVES.getValues(),
                                                            integer -> ItemEntry.builder(block)
                                                                    .conditionally(BlockStatePropertyLootCondition.builder(block)
                                                                            .properties(StatePredicate.Builder.create().exactMatch(StrewnLeavesBlock.LEAVES, integer))
                                                                    )
                                                                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(integer+1)))
                                                                    .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ConventionalItemTags.HOES)))
                                                    )
                                            )
                                    )
                    );
        }
    }
}
