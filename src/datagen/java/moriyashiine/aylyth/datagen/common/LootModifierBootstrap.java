package moriyashiine.aylyth.datagen.common;

import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.loot.modifier.AddPoolsModifier;
import moriyashiine.aylyth.common.loot.modifier.LootTableModifier;
import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceWithEnchantedBonusLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.FillPlayerHeadLootFunction;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.UnaryOperator;

public final class LootModifierBootstrap {
    private LootModifierBootstrap() {}

    public static void bootstrap(Registerable<LootTableModifier> registerable) {
        RegistryEntryLookup<Item> itemLookup = registerable.getRegistryLookup(RegistryKeys.ITEM);
        RegistryEntryLookup<Enchantment> enchantmentLookup = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        registerAddPools(registerable, EntityType.CREEPER, builder -> builder.addPool(head(itemLookup, enchantmentLookup, Items.CREEPER_HEAD, 0.2f)));
        registerAddPools(registerable, EntityType.PLAYER, builder -> builder.addPool(playerHead(itemLookup, enchantmentLookup)));
        registerAddPools(registerable, EntityType.SKELETON, builder -> builder.addPool(head(itemLookup, enchantmentLookup, Items.SKELETON_SKULL, 0.2f)));
        registerAddPools(registerable, EntityType.STRAY, builder -> builder.addPool(head(itemLookup, enchantmentLookup, Items.SKELETON_SKULL, 0.2f)));
        registerAddPools(registerable, EntityType.WITHER_SKELETON, builder -> builder.addPool(head(itemLookup, enchantmentLookup, Items.WITHER_SKELETON_SKULL, 0.025f)));
        registerAddPools(registerable, EntityType.HUSK, builder -> builder.addPool(head(itemLookup, enchantmentLookup, Items.ZOMBIE_HEAD, 0.2f)));
        registerAddPools(registerable, EntityType.ZOMBIE, builder -> builder.addPool(head(itemLookup, enchantmentLookup, Items.ZOMBIE_HEAD, 0.2f)));
    }

    private static void registerAddPools(Registerable<LootTableModifier> registerable, EntityType<?> type, UnaryOperator<AddPoolsModifier.Builder> builder) {
        type.getLootTableKey().ifPresent(key -> {
            registerable.register(keyFrom(key), builder.apply(AddPoolsModifier.builder()).build());
        });
    }

    private static LootPool.Builder head(RegistryEntryLookup<Item> itemLookup, RegistryEntryLookup<Enchantment> enchantmentLookup, ItemConvertible head, float chance) {
        return LootPool.builder().with(ItemEntry.builder(head))
                        .conditionally(DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                                .sourceEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .mainhand(ItemPredicate.Builder.create()
                                                        .tag(itemLookup, AylythItemTags.FLESH_HARVESTERS)
                                                ).build()
                                        )
                                )
                        ))
                        .conditionally(randomChanceWithBonus(enchantmentLookup, chance, 0.0625f));
    }

    private static LootPool.Builder playerHead(RegistryEntryLookup<Item> itemLookup, RegistryEntryLookup<Enchantment> enchantmentLookup) {
        return LootPool.builder().with(ItemEntry.builder(Items.PLAYER_HEAD))
                        .conditionally(DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                                .sourceEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .mainhand(ItemPredicate.Builder.create()
                                                        .tag(itemLookup, AylythItemTags.FLESH_HARVESTERS)
                                                ).build()
                                        )
                                )
                        ))
                        .conditionally(randomChanceWithBonus(enchantmentLookup, 0.2f, 0.0625f))
                        .apply(FillPlayerHeadLootFunction.builder(LootContext.EntityTarget.THIS));
    }

    private static RandomChanceWithEnchantedBonusLootCondition randomChanceWithBonus(RegistryEntryLookup<Enchantment> enchantmentLookup, float base, float perLevelAboveFirst) {
        return new RandomChanceWithEnchantedBonusLootCondition(
                base, new EnchantmentLevelBasedValue.Linear(base + perLevelAboveFirst, perLevelAboveFirst), enchantmentLookup.getOrThrow(Enchantments.LOOTING)
        );
    }

    private static RegistryKey<LootTableModifier> keyFrom(RegistryKey<LootTable> key) {
        return RegistryKey.of(AylythRegistryKeys.LOOT_TABLE_MODIFIER, key.getValue());
    }
}
