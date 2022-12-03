package moriyashiine.aylyth.datagen;

import com.google.common.collect.Maps;
import moriyashiine.aylyth.common.block.*;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.*;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AylythLootTableProviders {

    private static final LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));
    private static final LootCondition.Builder WITHOUT_SILK_TOUCH = WITH_SILK_TOUCH.invert();
    private static final LootCondition.Builder WITH_SHEARS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS));
    private static final LootCondition.Builder WITH_SILK_TOUCH_OR_SHEARS = WITH_SHEARS.or(WITH_SILK_TOUCH);
    private static final LootCondition.Builder WITHOUT_SILK_TOUCH_NOR_SHEARS = WITH_SILK_TOUCH_OR_SHEARS.invert();

    public static void registerProviders(FabricDataGenerator dataGenerator) {
        dataGenerator.addProvider(BlockLoot::new);
        dataGenerator.addProvider(EntityLoot::new);
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
            addDrop(ModBlocks.JACK_O_LANTERN_MUSHROOM, this::standingJackolantern);
            addDrop(ModBlocks.SHELF_JACK_O_LANTERN_MUSHROOM);
            addDrop(ModBlocks.GHOSTCAP_MUSHROOM, () -> ModItems.GHOSTCAP_MUSHROOM);
            woodSuiteDrops(ModBlocks.POMEGRANATE_BLOCKS);
            addDrop(ModBlocks.POMEGRANATE_LEAVES, block -> pomegranateLeavesDrop(block, ModBlocks.POMEGRANATE_BLOCKS.sapling, 0.05f, 0.0625f, 0.083333336f, 0.1f));
            woodSuiteDrops(ModBlocks.WRITHEWOOD_BLOCKS);
            addDrop(ModBlocks.WRITHEWOOD_LEAVES, block -> leavesDrop(block, ModBlocks.WRITHEWOOD_BLOCKS.sapling, 0.05f, 0.0625f, 0.083333336f, 0.1f));
            addDrop(ModBlocks.VITAL_THURIBLE);
            addDrop(ModBlocks.SOUL_HEARTH, BlockLootTableGenerator::doorDrops);
            addDrop(ModBlocks.WOODY_GROWTH_CACHE, block -> dropsNothing());
            addDrop(ModBlocks.SMALL_WOODY_GROWTH);
            addDrop(ModBlocks.LARGE_WOODY_GROWTH, this::woodyGrowths);
        }

        private void woodSuiteDrops(WoodSuite suite) {
            addDrop(suite.strippedLog);
            addDrop(suite.strippedWood);
            addDrop(suite.log);
            addDrop(suite.wood);
            addDrop(suite.sapling);
            addPottedPlantDrop(suite.pottedSapling);
            addDrop(suite.planks);
            addDrop(suite.stairs);
            addDrop(suite.slab, BlockLootTableGenerator::slabDrops);
            addDrop(suite.fence);
            addDrop(suite.fenceGate);
            addDrop(suite.pressurePlate);
            addDrop(suite.button);
            addDrop(suite.trapdoor);
            addDrop(suite.door, BlockLootTableGenerator::doorDrops);
            addDrop(suite.floorSign);
            addDrop(suite.wallSign);
        }

        private LootTable.Builder woodyGrowths(Block block) {
            return LootTable.builder().pool(
                    LootPool.builder().with(
                            GroupEntry.create(ItemEntry.builder(Items.STICK),
                                    ItemEntry.builder(Items.ROTTEN_FLESH),
                                    ItemEntry.builder(Items.BONE),
                                    ItemEntry.builder(Items.SKELETON_SKULL),
                                    ItemEntry.builder(Items.REDSTONE),
                                    ItemEntry.builder(ModItems.YMPE_ITEMS.sapling),
                                    ItemEntry.builder(ModItems.YMPE_FRUIT),
                                    ItemEntry.builder(ModItems.NYSIAN_GRAPES),
                                    ItemEntry.builder(ModItems.POMEGRANATE))
                                    .conditionally(BlockStatePropertyLootCondition.builder(block)
                                            .properties(StatePredicate.Builder.create()
                                                    .exactMatch(SmallWoodyGrowthBlock.NATURAL, true)
                                            )
                                    )
                    ).conditionally(BlockStatePropertyLootCondition.builder(block)
                            .properties(StatePredicate.Builder.create().exactMatch(LargeWoodyGrowthBlock.HALF, DoubleBlockHalf.LOWER))
                    ).rolls(UniformLootNumberProvider.create(0, 3))
            ).pool(LootPool.builder()
                    .with(ItemEntry.builder(block))
                    .conditionally(BlockStatePropertyLootCondition.builder(block)
                            .properties(StatePredicate.Builder.create()
                                    .exactMatch(SmallWoodyGrowthBlock.NATURAL, false)
                            )
                    ).conditionally(BlockStatePropertyLootCondition.builder(block)
                            .properties(StatePredicate.Builder.create().exactMatch(LargeWoodyGrowthBlock.HALF, DoubleBlockHalf.LOWER))
                    )
            );
        }

        private LootTable.Builder pomegranateLeavesDrop(Block leaves, Block drop, float ... chance) {
            return BlockLootTableGenerator
                    .dropsWithSilkTouchOrShears(leaves, BlockLootTableGenerator.addSurvivesExplosionCondition(leaves, ItemEntry.builder(drop))
                            .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, chance)))
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS)
                            .with(BlockLootTableGenerator.applyExplosionDecay(leaves, ItemEntry.builder(Items.STICK)
                                                                                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f))))
                                    .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f)))
                            .with(BlockLootTableGenerator.applyExplosionDecay(leaves, ItemEntry.builder(ModItems.POMEGRANATE)
                                                                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f))))
                                    .conditionally(BlockStatePropertyLootCondition.builder(leaves).properties(StatePredicate.Builder.create().exactMatch(PomegranateLeavesBlock.FRUITING, 3)))));
        }

        private LootTable.Builder standingJackolantern(Block block) {
            return LootTable.builder().pool(
                    LootPool.builder()
                            .with(AlternativeEntry.builder(JackolanternMushroomBlock.STAGE.getValues(), integer -> ItemEntry.builder(block)
                                    .conditionally(BlockStatePropertyLootCondition.builder(block)
                                            .properties(StatePredicate.Builder.create().exactMatch(StagedMushroomPlantBlock.STAGE, integer)))
                                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(integer)))))
            );
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

    public static class EntityLoot extends SimpleFabricLootTableProvider {

        private final Map<Identifier, LootTable.Builder> loot = Maps.newHashMap();

        public EntityLoot(FabricDataGenerator dataGenerator) {
            super(dataGenerator, LootContextTypes.ENTITY);
        }

        protected void generateLoot() {
            addDrop(ModEntityTypes.SCION, this::scionLoot);
        }

        private LootTable.Builder scionLoot(EntityType<?> type) {
            return LootTable.builder()
                    .pool(LootPool.builder().with(GroupEntry.create(ItemEntry.builder(ModItems.POMEGRANATE), ItemEntry.builder(ModItems.NYSIAN_GRAPES))))
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.ROTTEN_FLESH).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3)))))
                    .pool(LootPool.builder().with(ItemEntry.builder(ModItems.YMPE_ITEMS.sapling)));
        }

        public <T extends Entity> void addDrop(EntityType<T> type, Function<EntityType<T>, LootTable.Builder> function) {
            loot.put(type.getLootTableId(), function.apply(type));
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> consumer) {
            this.generateLoot();
            for (Map.Entry<Identifier, LootTable.Builder> entry : loot.entrySet()) {
                consumer.accept(entry.getKey(), entry.getValue());
            }
        }


    }


}
