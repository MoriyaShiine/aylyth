package moriyashiine.aylyth.datagen;

import com.google.common.collect.Maps;
import moriyashiine.aylyth.common.block.*;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.server.loottable.vanilla.VanillaBlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.DynamicEntry;
import net.minecraft.loot.entry.GroupEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AylythLootTableProviders {

    public static class BlockLoot extends FabricBlockLootTableProvider {

        private final LootCondition.Builder withSilkTouch = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));

        protected BlockLoot(FabricDataOutput dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.MARIGOLD);
            addPottedPlantDrops(ModBlocks.MARIGOLD_POTTED);
            addDrop(ModBlocks.OAK_STREWN_LEAVES, this::strewnLeaves);
            addDrop(ModBlocks.YMPE_STREWN_LEAVES, this::strewnLeaves);
            addDrop(ModBlocks.JACK_O_LANTERN_MUSHROOM, this::standingJackolantern);
            addDrop(ModBlocks.SHELF_JACK_O_LANTERN_MUSHROOM);
            addDrop(ModBlocks.GHOSTCAP_MUSHROOM, () -> ModItems.GHOSTCAP_MUSHROOM);
            addDrop(ModBlocks.YMPE_LEAVES, block -> leavesDrops(block, ModBlocks.YMPE_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
            addDrop(ModBlocks.POMEGRANATE_STRIPPED_LOG);
            addDrop(ModBlocks.POMEGRANATE_STRIPPED_WOOD);
            addDrop(ModBlocks.POMEGRANATE_LOG);
            addDrop(ModBlocks.POMEGRANATE_WOOD);
            addDrop(ModBlocks.POMEGRANATE_SAPLING);
            addPottedPlantDrops(ModBlocks.POMEGRANATE_POTTED_SAPLING);
            addDrop(ModBlocks.POMEGRANATE_PLANKS);
            addDrop(ModBlocks.POMEGRANATE_STAIRS);
            addDrop(ModBlocks.POMEGRANATE_SLAB, this::slabDrops);
            addDrop(ModBlocks.POMEGRANATE_FENCE);
            addDrop(ModBlocks.POMEGRANATE_FENCE_GATE);
            addDrop(ModBlocks.POMEGRANATE_PRESSURE_PLATE);
            addDrop(ModBlocks.POMEGRANATE_BUTTON);
            addDrop(ModBlocks.POMEGRANATE_DOOR, this::doorDrops);
            addDrop(ModBlocks.POMEGRANATE_TRAPDOOR);
            addDrop(ModBlocks.POMEGRANATE_SIGN);
            addDrop(ModBlocks.POMEGRANATE_LEAVES, block -> pomegranateLeavesDrop(block, ModBlocks.POMEGRANATE_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
            addDrop(ModBlocks.WRITHEWOOD_STRIPPED_LOG);
            addDrop(ModBlocks.WRITHEWOOD_STRIPPED_WOOD);
            addDrop(ModBlocks.WRITHEWOOD_LOG);
            addDrop(ModBlocks.WRITHEWOOD_WOOD);
            addDrop(ModBlocks.WRITHEWOOD_SAPLING);
            addPottedPlantDrops(ModBlocks.WRITHEWOOD_POTTED_SAPLING);
            addDrop(ModBlocks.WRITHEWOOD_PLANKS);
            addDrop(ModBlocks.WRITHEWOOD_STAIRS);
            addDrop(ModBlocks.WRITHEWOOD_SLAB, this::slabDrops);
            addDrop(ModBlocks.WRITHEWOOD_FENCE);
            addDrop(ModBlocks.WRITHEWOOD_FENCE_GATE);
            addDrop(ModBlocks.WRITHEWOOD_PRESSURE_PLATE);
            addDrop(ModBlocks.WRITHEWOOD_BUTTON);
            addDrop(ModBlocks.WRITHEWOOD_DOOR, this::doorDrops);
            addDrop(ModBlocks.WRITHEWOOD_TRAPDOOR);
            addDrop(ModBlocks.WRITHEWOOD_SIGN);
            addDrop(ModBlocks.WRITHEWOOD_LEAVES, block -> leavesDrops(block, ModBlocks.WRITHEWOOD_SAPLING, 0.05f, 0.0625f, 0.083333336f, 0.1f));
            addDrop(ModBlocks.VITAL_THURIBLE);
            addDrop(ModBlocks.SOUL_HEARTH, this::doorDrops);
            addDrop(ModBlocks.WOODY_GROWTH_CACHE, this::woodyGrowthCaches);
            addDrop(ModBlocks.SMALL_WOODY_GROWTH);
            addDrop(ModBlocks.LARGE_WOODY_GROWTH, this::woodyGrowths);
            addDrop(ModBlocks.SEEPING_WOOD);
            addDrop(ModBlocks.SEEPING_WOOD_SEEP);
            addDrop(ModBlocks.GIRASOL_SAPLING);
            addDrop(ModBlocks.DARK_WOODS_TILES);
            addDrop(ModBlocks.ESSTLINE_BLOCK);
            addDrop(ModBlocks.NEPHRITE_BLOCK);
            addDrop(ModBlocks.CARVED_SMOOTH_NEPHRITE);
            addDrop(ModBlocks.CARVED_ANTLERED_NEPHRITE);
            addDrop(ModBlocks.CARVED_NEPHRITE_PILLAR);
            addDrop(ModBlocks.CARVED_NEPHRITE_TILES);
            addDrop(ModBlocks.CARVED_WOODY_NEPHRITE);
            addDrop(ModBlocks.YMPE_HANGING_SIGN);
            addDrop(ModBlocks.POMEGRANATE_HANGING_SIGN);
            addDrop(ModBlocks.WRITHEWOOD_HANGING_SIGN);
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
                                            ItemEntry.builder(ModItems.YMPE_LOG)
                                                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                                    .conditionally(RandomChanceLootCondition.builder(0.24f)),
                                            ItemEntry.builder(ModItems.POMEGRANATE_LOG)
                                                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                                    .conditionally(RandomChanceLootCondition.builder(0.24f)),
                                            ItemEntry.builder(ModItems.WRITHEWOOD_LOG)
                                                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                                    .conditionally(RandomChanceLootCondition.builder(0.24f)),
                                            ItemEntry.builder(ModItems.YMPE_FRUIT)
                                                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                                    .conditionally(RandomChanceLootCondition.builder(0.1f))
                                    ).conditionally(BlockStatePropertyLootCondition.builder(block)
                                            .properties(StatePredicate.Builder.create()
                                                    .exactMatch(SmallWoodyGrowthBlock.NATURAL, true)
                                            )
                                    )
                            ).with(ItemEntry.builder(ModItems.LARGE_WOODY_GROWTH))
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
                            .with(applyExplosionDecay(leaves, ItemEntry.builder(ModItems.POMEGRANATE)
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
                                                    withSilkTouch,
                                                    MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ConventionalItemTags.SHEARS)),
                                                    MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.HOES))
                                                    )
                                            )
                            )
                    )
            );
        }
    }

    public static class EntityLoot extends SimpleFabricLootTableProvider {

        private final Map<Identifier, LootTable.Builder> loot = Maps.newHashMap();

        public EntityLoot(FabricDataOutput dataGenerator) {
            super(dataGenerator, LootContextTypes.ENTITY);
        }

        protected void generateLoot() {
            addDrop(ModEntityTypes.AYLYTHIAN, this::aylythianLoot);
            addDrop(ModEntityTypes.ELDER_AYLYTHIAN, this::elderAylythianLoot);
            addDrop(ModEntityTypes.SCION, this::scionLoot);
            if (false) { // TODO: these drops are specified in the constructs doc. Normal loot that drops before post-death?
                addDrop(ModEntityTypes.SOULMOULD, this::mouldOfSoulsLoot);
                addDrop(ModEntityTypes.BONEFLY, this::boneflyLoot);
                addDrop(ModEntityTypes.TULPA, this::tulpaLoot);
            }
        }

        private LootTable.Builder aylythianLoot(EntityType<?> type) {
            return LootTable.builder()
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.BONE)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.STICK)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                    .pool(LootPool.builder().with(ItemEntry.builder(ModItems.YMPE_FRUIT)).conditionally(KilledByPlayerLootCondition.builder().build()).conditionally(RandomChanceWithLootingLootCondition.builder(0.25f, 0.01f)));
        }

        private LootTable.Builder elderAylythianLoot(EntityType<?> type) {
            return LootTable.builder()
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.BONE)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.STICK)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                    .pool(LootPool.builder().with(ItemEntry.builder(ModItems.YMPE_FRUIT)).conditionally(KilledByPlayerLootCondition.builder().build()).conditionally(RandomChanceWithLootingLootCondition.builder(0.25f, 0.01f)));
        }

        private LootTable.Builder scionLoot(EntityType<?> type) {
            return LootTable.builder()
                    .pool(LootPool.builder().with(GroupEntry.create(ItemEntry.builder(ModItems.POMEGRANATE), ItemEntry.builder(ModItems.NYSIAN_GRAPES))))
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.ROTTEN_FLESH).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3)))))
                    .pool(LootPool.builder().with(ItemEntry.builder(ModItems.YMPE_SAPLING)));
        }

        private LootTable.Builder mouldOfSoulsLoot(EntityType<?> type) {
            return LootTable.builder()
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.SOUL_SOIL)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))));
        }

        private LootTable.Builder boneflyLoot(EntityType<?> type) {
            return LootTable.builder()
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.SOUL_SOIL)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))));
        }

        private LootTable.Builder tulpaLoot(EntityType<?> type) {
            return LootTable.builder()
                    .pool(LootPool.builder().with(ItemEntry.builder(Items.SOUL_SOIL)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))));
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
