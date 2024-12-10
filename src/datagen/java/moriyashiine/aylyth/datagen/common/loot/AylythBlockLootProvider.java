package moriyashiine.aylyth.datagen.common.loot;

import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.*;
import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.server.loottable.vanilla.VanillaBlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.DynamicEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.ItemTags;

public class AylythBlockLootProvider extends FabricBlockLootTableProvider {
    public AylythBlockLootProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generate() {
        addDrop(AylythBlocks.MARIGOLD);
        addPottedPlantDrops(AylythBlocks.POTTED_MARIGOLD);
        addDrop(AylythBlocks.OAK_STREWN_LEAVES, this::strewnLeaves);
        addDrop(AylythBlocks.YMPE_STREWN_LEAVES, this::strewnLeaves);
        addDrop(AylythBlocks.JACK_O_LANTERN_MUSHROOM, this::standingJackolantern);
        addDrop(AylythBlocks.SHELF_JACK_O_LANTERN_MUSHROOM);
        addDrop(AylythBlocks.GHOSTCAP_MUSHROOM, () -> AylythItems.GHOSTCAP_MUSHROOM);
        addDrop(AylythBlocks.YMPE_LEAVES, block -> leavesDrops(block, AylythBlocks.YMPE_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
        addDrop(AylythBlocks.POMEGRANATE_STRIPPED_LOG);
        addDrop(AylythBlocks.POMEGRANATE_STRIPPED_WOOD);
        addDrop(AylythBlocks.POMEGRANATE_LOG);
        addDrop(AylythBlocks.POMEGRANATE_WOOD);
        addDrop(AylythBlocks.POMEGRANATE_SAPLING);
        addPottedPlantDrops(AylythBlocks.POTTED_POMEGRANATE_SAPLING);
        addDrop(AylythBlocks.POMEGRANATE_PLANKS);
        addDrop(AylythBlocks.POMEGRANATE_STAIRS);
        addDrop(AylythBlocks.POMEGRANATE_SLAB, this::slabDrops);
        addDrop(AylythBlocks.POMEGRANATE_FENCE);
        addDrop(AylythBlocks.POMEGRANATE_FENCE_GATE);
        addDrop(AylythBlocks.POMEGRANATE_PRESSURE_PLATE);
        addDrop(AylythBlocks.POMEGRANATE_BUTTON);
        addDrop(AylythBlocks.POMEGRANATE_DOOR, this::doorDrops);
        addDrop(AylythBlocks.POMEGRANATE_TRAPDOOR);
        addDrop(AylythBlocks.POMEGRANATE_SIGN);
        addDrop(AylythBlocks.POMEGRANATE_LEAVES, block -> pomegranateLeavesDrop(block, AylythBlocks.POMEGRANATE_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
        addDrop(AylythBlocks.WRITHEWOOD_STRIPPED_LOG);
        addDrop(AylythBlocks.WRITHEWOOD_STRIPPED_WOOD);
        addDrop(AylythBlocks.WRITHEWOOD_LOG);
        addDrop(AylythBlocks.WRITHEWOOD_WOOD);
        addDrop(AylythBlocks.WRITHEWOOD_SAPLING);
        addPottedPlantDrops(AylythBlocks.POTTED_WRITHEWOOD_SAPLING);
        addDrop(AylythBlocks.WRITHEWOOD_PLANKS);
        addDrop(AylythBlocks.WRITHEWOOD_STAIRS);
        addDrop(AylythBlocks.WRITHEWOOD_SLAB, this::slabDrops);
        addDrop(AylythBlocks.WRITHEWOOD_FENCE);
        addDrop(AylythBlocks.WRITHEWOOD_FENCE_GATE);
        addDrop(AylythBlocks.WRITHEWOOD_PRESSURE_PLATE);
        addDrop(AylythBlocks.WRITHEWOOD_BUTTON);
        addDrop(AylythBlocks.WRITHEWOOD_DOOR, this::doorDrops);
        addDrop(AylythBlocks.WRITHEWOOD_TRAPDOOR);
        addDrop(AylythBlocks.WRITHEWOOD_SIGN);
        addDrop(AylythBlocks.WRITHEWOOD_LEAVES, block -> ympeLeaves(block, Items.STICK, 0.05f, 0.0625f, 0.083333336f, 0.1f));
        addDrop(AylythBlocks.VITAL_THURIBLE);
        addDrop(AylythBlocks.SOUL_HEARTH, this::doorDrops);
        addDrop(AylythBlocks.WOODY_GROWTH_CACHE, this::woodyGrowthCaches);
        addDrop(AylythBlocks.SMALL_WOODY_GROWTH);
        addDrop(AylythBlocks.LARGE_WOODY_GROWTH, this::woodyGrowths);
        addDrop(AylythBlocks.SEEPING_WOOD);
        addDrop(AylythBlocks.SEEPING_WOOD_SEEP);
        addDrop(AylythBlocks.GIRASOL_SAPLING);
        addDrop(AylythBlocks.DARK_WOODS_TILES);
        addDrop(AylythBlocks.ESSTLINE_BLOCK);
        addDrop(AylythBlocks.NEPHRITE_BLOCK);
        addDrop(AylythBlocks.CARVED_SMOOTH_NEPHRITE);
        addDrop(AylythBlocks.CARVED_ANTLERED_NEPHRITE);
        addDrop(AylythBlocks.CARVED_NEPHRITE_PILLAR);
        addDrop(AylythBlocks.CARVED_NEPHRITE_TILES);
        addDrop(AylythBlocks.CARVED_WOODY_NEPHRITE);
        addDrop(AylythBlocks.YMPE_HANGING_SIGN);
        addDrop(AylythBlocks.POMEGRANATE_HANGING_SIGN);
        addDrop(AylythBlocks.WRITHEWOOD_HANGING_SIGN);
        addDrop(AylythBlocks.CHTHONIA_WOOD);
        addDrop(AylythBlocks.NEPHRITIC_CHTHONIA_WOOD, block -> nephriticChthoniaWood(block, AylythBlocks.CHTHONIA_WOOD));
        addDrop(AylythBlocks.JACK_O_LANTERN_MUSHROOM_BLOCK, block -> mushroomBlockDrops(block, AylythItems.JACK_O_LANTERN_MUSHROOM));
        addDropWithSilkTouch(AylythBlocks.JACK_O_LANTERN_MUSHROOM_STEM);
        addDrop(AylythBlocks.SAPSTONE);
        addDrop(AylythBlocks.AMBER_SAPSTONE);
        addDrop(AylythBlocks.LIGNITE_SAPSTONE);
        addDrop(AylythBlocks.OPALESCENT_SAPSTONE);
        addDrop(AylythBlocks.DARK_OAK_BRANCH, block -> leafyBranch(block, AylythItems.OAK_STREWN_LEAVES));
        addDrop(AylythBlocks.BARE_DARK_OAK_BRANCH, block -> dropsWithSilkTouchOrShears(block, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2)))));
        addDrop(AylythBlocks.WRITHEWOOD_BRANCH, block -> dropsWithSilkTouchOrShears(block, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2)))));
        addDrop(AylythBlocks.BARE_WRITHEWOOD_BRANCH, block -> dropsWithSilkTouchOrShears(block, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2)))));
        addDrop(AylythBlocks.YMPE_BRANCH, block -> leafyBranch(block, AylythItems.YMPE_STREWN_LEAVES));
        addDrop(AylythBlocks.BARE_YMPE_BRANCH, block -> dropsWithSilkTouchOrShears(block, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2)))));
        addDrop(AylythBlocks.DARK_PODZOL, block -> drops(block, Blocks.DIRT));
        addDrop(AylythBlocks.BROWN_AYLYTHIAN_OAK_SAPLING);
        addPottedPlantDrops(AylythBlocks.POTTED_BROWN_AYLYTHIAN_OAK_SAPLING);
        addDrop(AylythBlocks.GREEN_AYLYTHIAN_OAK_SAPLING);
        addPottedPlantDrops(AylythBlocks.POTTED_GREEN_AYLYTHIAN_OAK_SAPLING);
        addDrop(AylythBlocks.ORANGE_AYLYTHIAN_OAK_SAPLING);
        addPottedPlantDrops(AylythBlocks.POTTED_ORANGE_AYLYTHIAN_OAK_SAPLING);
        addDrop(AylythBlocks.RED_AYLYTHIAN_OAK_SAPLING);
        addPottedPlantDrops(AylythBlocks.POTTED_RED_AYLYTHIAN_OAK_SAPLING);
        addDrop(AylythBlocks.ORANGE_AYLYTHIAN_OAK_BRANCH, block -> leafyBranch(block, AylythItems.OAK_STREWN_LEAVES));
        addDrop(AylythBlocks.RED_AYLYTHIAN_OAK_BRANCH, block -> leafyBranch(block, AylythItems.OAK_STREWN_LEAVES));
        addDrop(AylythBlocks.BROWN_AYLYTHIAN_OAK_BRANCH, block -> leafyBranch(block, AylythItems.OAK_STREWN_LEAVES));
        addDrop(AylythBlocks.GREEN_AYLYTHIAN_OAK_LEAVES, block -> leavesDrops(block, AylythBlocks.GREEN_AYLYTHIAN_OAK_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
        addDrop(AylythBlocks.ORANGE_AYLYTHIAN_OAK_LEAVES, block -> leavesDrops(block, AylythBlocks.ORANGE_AYLYTHIAN_OAK_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
        addDrop(AylythBlocks.RED_AYLYTHIAN_OAK_LEAVES, block -> leavesDrops(block, AylythBlocks.RED_AYLYTHIAN_OAK_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
        addDrop(AylythBlocks.BROWN_AYLYTHIAN_OAK_LEAVES, block -> leavesDrops(block, AylythBlocks.BROWN_AYLYTHIAN_OAK_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
    }

    private LootTable.Builder leafyBranch(Block branch, ItemConvertible strewnLeaves) {
        return dropsWithSilkTouchOrShears(branch,
                ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2)))
                        .groupEntry(ItemEntry.builder(strewnLeaves).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
        );
    }

    private LootTable.Builder ympeLeaves(Block leaves, ItemConvertible sticks, float... chances) {
        return LootTable.builder().type(LootContextTypes.BLOCK)
                .pool(
                        addSurvivesExplosionCondition(leaves, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(leaves)))
                                .conditionally(WITH_SILK_TOUCH_OR_SHEARS)
                                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, chances))
                ).pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS)
                                .with(
                                        this.applyExplosionDecay(leaves, ItemEntry.builder(sticks).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F))))
                                                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, LEAVES_STICK_DROP_CHANCE))
                                )
                );
    }

    private LootTable.Builder nephriticChthoniaWood(Block block, Block emptyLog) {
        return LootTable.builder().type(LootContextTypes.BLOCK)
                .pool(
                        LootPool.builder().with(
                                ItemEntry.builder(emptyLog)
                        )
                ).pool(
                        LootPool.builder().with(
                                ItemEntry.builder(AylythItems.NEPHRITE)
                                        .conditionally(
                                                BlockStatePropertyLootCondition.builder(block)
                                                        .properties(
                                                                StatePredicate.Builder.create()
                                                                        .exactMatch(OneTimeHarvestablePillarBlock.HARVESTABLE, true)
                                                        )
                                        )
                        )
                );
    }

    private LootTable.Builder woodyGrowthCaches(Block block) {
        return LootTable.builder().type(LootContextTypes.BLOCK)
                .pool(
                        LootPool.builder().with(
                                DynamicEntry.builder(WoodyGrowthCacheBlock.CONTENTS)
                        ).conditionally(
                                BlockStatePropertyLootCondition.builder(block)
                                        .properties(
                                                StatePredicate.Builder.create()
                                                        .exactMatch(LargeWoodyGrowthBlock.HALF, DoubleBlockHalf.LOWER)
                                        )
                        )
                );
    }

    private LootTable.Builder woodyGrowths(Block block) {
        return LootTable.builder().pool(LootPool.builder().with(
                                AlternativeEntry.builder(
                                        ItemEntry.builder(Items.STICK)
                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 3)))
                                                .conditionally(RandomChanceLootCondition.builder(0.7f)),
                                        ItemEntry.builder(Items.DARK_OAK_LOG)
                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                                .conditionally(RandomChanceLootCondition.builder(0.24f)),
                                        ItemEntry.builder(Items.SPRUCE_LOG)
                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                                .conditionally(RandomChanceLootCondition.builder(0.24f)),
                                        ItemEntry.builder(AylythItems.YMPE_LOG)
                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                                .conditionally(RandomChanceLootCondition.builder(0.24f)),
                                        ItemEntry.builder(AylythItems.POMEGRANATE_LOG)
                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                                .conditionally(RandomChanceLootCondition.builder(0.24f)),
                                        ItemEntry.builder(AylythItems.WRITHEWOOD_LOG)
                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                                .conditionally(RandomChanceLootCondition.builder(0.24f)),
                                        ItemEntry.builder(AylythItems.YMPE_FRUIT)
                                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                                .conditionally(RandomChanceLootCondition.builder(0.1f))
                                ).conditionally(BlockStatePropertyLootCondition.builder(block)
                                        .properties(StatePredicate.Builder.create()
                                                .exactMatch(SmallWoodyGrowthBlock.NATURAL, true)
                                        )
                                )
                        ).with(ItemEntry.builder(AylythItems.LARGE_WOODY_GROWTH))
                        .conditionally(BlockStatePropertyLootCondition.builder(block)
                                .properties(StatePredicate.Builder.create().exactMatch(LargeWoodyGrowthBlock.HALF, DoubleBlockHalf.LOWER))
                        )
        );
    }

    private LootTable.Builder pomegranateLeavesDrop(Block leaves, Block drop, float ... chance) {
        return VanillaBlockLootTableGenerator
                .dropsWithSilkTouchOrShears(leaves, addSurvivesExplosionCondition(leaves, ItemEntry.builder(drop))
                        .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, chance)))
                .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                        .conditionally(WITHOUT_SILK_TOUCH_NOR_SHEARS)
                        .with(applyExplosionDecay(leaves, ItemEntry.builder(Items.STICK)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f))))
                                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f)))
                        .with(applyExplosionDecay(leaves, ItemEntry.builder(AylythItems.POMEGRANATE)
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
        return LootTable.builder().pool(
                LootPool.builder().with(
                        AlternativeEntry.builder(StrewnLeavesBlock.LEAVES.getValues(),
                                integer -> ItemEntry.builder(block)
                                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(integer+1)))
                                        .conditionally(BlockStatePropertyLootCondition.builder(block)
                                                .properties(StatePredicate.Builder.create()
                                                        .exactMatch(StrewnLeavesBlock.LEAVES, integer)
                                                )
                                        )
                                        .conditionally(AnyOfLootCondition.builder(
                                                        WITH_SILK_TOUCH,
                                                        MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ConventionalItemTags.SHEARS)),
                                                        MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.HOES))
                                                )
                                        )
                        )
                )
        );
    }
}
