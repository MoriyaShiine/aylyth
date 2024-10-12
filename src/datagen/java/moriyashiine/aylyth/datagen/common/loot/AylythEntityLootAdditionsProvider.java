package moriyashiine.aylyth.datagen.common.loot;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.FillPlayerHeadLootFunction;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AylythEntityLootAdditionsProvider extends SimpleFabricLootTableProvider {
    private final Map<Identifier, LootTable.Builder> loot = new Object2ObjectOpenHashMap<>();

    public AylythEntityLootAdditionsProvider(FabricDataOutput output) {
        super(output, LootContextTypes.ENTITY);
    }

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
                                                        .tag(AylythItemTags.FLESH_HARVESTERS).build()
                                                ).build()
                                        )
                                )
                        ))
                        .conditionally(RandomChanceWithLootingLootCondition.builder(chance, 0.0625f)));
    }

    private LootTable.Builder playerHead(EntityType<PlayerEntity> entityType) {
        return LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(Items.PLAYER_HEAD))
                        .conditionally(DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                                .sourceEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .mainhand(ItemPredicate.Builder.create()
                                                        .tag(AylythItemTags.FLESH_HARVESTERS).build()
                                                ).build()
                                        )
                                )
                        ))
                        .conditionally(RandomChanceWithLootingLootCondition.builder(0.2f, 0.0625f))
                        .apply(FillPlayerHeadLootFunction.builder(LootContext.EntityTarget.THIS)));
    }

    public <T extends Entity> void addDrop(EntityType<T> type, Function<EntityType<T>, LootTable.Builder> function) {
        loot.put(type.getLootTableId().withPrefixedPath("additions/"), function.apply(type));
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> consumer) {
        this.generateLoot();
        for (Map.Entry<Identifier, LootTable.Builder> entry : loot.entrySet()) {
            consumer.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String getName() {
        return "entity loot additions";
    }
}
