package moriyashiine.aylyth.datagen.common.loot;

import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.function.Function;

public class AylythEntityLootAdditionsGenerator extends SimpleLootGenerator {
    public AylythEntityLootAdditionsGenerator(RegistryWrapper.WrapperLookup registries) {
        super(registries);
    }

    @Override
    protected void generateLoot() {
        addDrop(EntityType.CREEPER, entityType -> head(entityType, Items.CREEPER_HEAD, 0.2f));
        addDrop(EntityType.PLAYER, this::playerHead);
        addDrop(EntityType.SKELETON, entityType -> head(entityType, Items.SKELETON_SKULL, 0.2f));
        addDrop(EntityType.STRAY, entityType -> head(entityType, Items.SKELETON_SKULL, 0.2f));
        addDrop(EntityType.WITHER_SKELETON, entityType -> head(entityType, Items.WITHER_SKELETON_SKULL, 0.025f));
        addDrop(EntityType.HUSK, entityType -> head(entityType, Items.ZOMBIE_HEAD, 0.2f));
        addDrop(EntityType.ZOMBIE, entityType -> head(entityType, Items.ZOMBIE_HEAD, 0.2f));
    }

    private <T extends Entity> LootTable.Builder head(EntityType<T> entityType, ItemConvertible head, float chance) {
        return LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(head))
                        .conditionally(DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                                .sourceEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .mainhand(ItemPredicate.Builder.create()
                                                        .tag(registries.getOrThrow(RegistryKeys.ITEM), AylythItemTags.FLESH_HARVESTERS)
                                                ).build()
                                        )
                                )
                        ))
                        .conditionally(RandomChanceWithEnchantedBonusLootCondition.builder(registries, chance, 0.0625f)));
    }

    private LootTable.Builder playerHead(EntityType<PlayerEntity> entityType) {
        return LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(Items.PLAYER_HEAD))
                        .conditionally(DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                                .sourceEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .mainhand(ItemPredicate.Builder.create()
                                                        .tag(registries.getOrThrow(RegistryKeys.ITEM), AylythItemTags.FLESH_HARVESTERS)
                                                ).build()
                                        )
                                )
                        ))
                        .conditionally(RandomChanceWithEnchantedBonusLootCondition.builder(registries, 0.2f, 0.0625f))
                        .apply(FillPlayerHeadLootFunction.builder(LootContext.EntityTarget.THIS)));
    }

    public <T extends Entity> void addDrop(EntityType<T> type, Function<EntityType<T>, LootTable.Builder> function) {
        addDrop(type.getLootTableKey().get().getValue().withPrefixedPath("additions/"), function.apply(type));
    }
}
