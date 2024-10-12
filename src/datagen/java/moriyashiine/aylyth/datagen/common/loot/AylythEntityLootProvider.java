package moriyashiine.aylyth.datagen.common.loot;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.loot.conditions.ScionIsPlayerLootCondition;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.GroupEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AylythEntityLootProvider extends SimpleFabricLootTableProvider {

    private final Map<Identifier, LootTable.Builder> loot = new Object2ObjectOpenHashMap<>();

    public AylythEntityLootProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator, LootContextTypes.ENTITY);
    }

    protected void generateLoot() {
        addDrop(AylythEntityTypes.AYLYTHIAN, this::aylythian);
        addDrop(AylythEntityTypes.ELDER_AYLYTHIAN, this::elderAylythian);
        addDrop(AylythEntityTypes.SCION, this::scion);
        addDrop(AylythEntityTypes.WREATHED_HIND_ENTITY, this::wreathedHind);
        if (false) { // TODO: these drops are specified in the constructs doc. Normal loot that drops before post-death?
            addDrop(AylythEntityTypes.YMPEMOULD, this::ympeMouldLoot);
            addDrop(AylythEntityTypes.BONEFLY, this::boneflyLoot);
            addDrop(AylythEntityTypes.TULPA, this::tulpaLoot);
        }
    }

    private LootTable.Builder aylythian(EntityType<?> type) {
        return LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(Items.BONE)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                .pool(LootPool.builder().with(ItemEntry.builder(Items.STICK)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                .pool(LootPool.builder().with(ItemEntry.builder(AylythItems.YMPE_SAPLING)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                .pool(LootPool.builder().with(ItemEntry.builder(AylythItems.YMPE_FRUIT)).conditionally(KilledByPlayerLootCondition.builder().build()).conditionally(RandomChanceWithLootingLootCondition.builder(0.25f, 0.01f)))
                .pool(LootPool.builder().with(ItemEntry.builder(AylythItems.WRONGMEAT)).conditionally(DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                        .sourceEntity(EntityPredicate.Builder.create()
                                .equipment(EntityEquipmentPredicate.Builder.create()
                                        .mainhand(ItemPredicate.Builder.create()
                                                .tag(AylythItemTags.FLESH_HARVESTERS).build()
                                        ).build()
                                )
                        )
                )).conditionally(RandomChanceWithLootingLootCondition.builder(0.15f, 0.0625f)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2))));
    }

    private LootTable.Builder elderAylythian(EntityType<?> type) {
        return LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(Items.BONE)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                .pool(LootPool.builder().with(ItemEntry.builder(Items.STICK)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 2))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                .pool(LootPool.builder().with(ItemEntry.builder(AylythItems.YMPE_SAPLING)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0, 1))))
                .pool(LootPool.builder().with(ItemEntry.builder(AylythItems.YMPE_FRUIT)).conditionally(KilledByPlayerLootCondition.builder().build()).conditionally(RandomChanceWithLootingLootCondition.builder(0.25f, 0.01f)))
                .pool(LootPool.builder()
                        .with(ItemEntry.builder(AylythItems.WRONGMEAT))
                        .with(ItemEntry.builder(AylythItems.AYLYTHIAN_HEART))
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
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 5))));
    }

    private LootTable.Builder scion(EntityType<?> type) {
        return LootTable.builder()
                .pool(LootPool.builder().with(GroupEntry.create(ItemEntry.builder(AylythItems.POMEGRANATE), ItemEntry.builder(AylythItems.NYSIAN_GRAPES))))
                .pool(LootPool.builder().with(ItemEntry.builder(Items.ROTTEN_FLESH).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3)))))
                .pool(LootPool.builder().with(ItemEntry.builder(AylythItems.YMPE_SAPLING)))
                .pool(LootPool.builder().with(ItemEntry.builder(AylythItems.WRONGMEAT)).conditionally(DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                        .sourceEntity(EntityPredicate.Builder.create()
                                .equipment(EntityEquipmentPredicate.Builder.create()
                                        .mainhand(ItemPredicate.Builder.create()
                                                .tag(AylythItemTags.FLESH_HARVESTERS).build()
                                        ).build()
                                )
                        )
                ))
                        .conditionally(RandomChanceWithLootingLootCondition.builder(0.15f, 0.0625f))
                        .conditionally(InvertedLootCondition.builder(ScionIsPlayerLootCondition::new))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2))));
    }

    private LootTable.Builder wreathedHind(EntityType<?> type) {
        return LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(AylythItems.WRONGMEAT)).conditionally(DamageSourcePropertiesLootCondition.builder(DamageSourcePredicate.Builder.create()
                                .sourceEntity(EntityPredicate.Builder.create()
                                        .equipment(EntityEquipmentPredicate.Builder.create()
                                                .mainhand(ItemPredicate.Builder.create()
                                                        .tag(AylythItemTags.FLESH_HARVESTERS).build()
                                                ).build()
                                        )
                                )
                        ))
                        .conditionally(RandomChanceWithLootingLootCondition.builder(0.2f, 0.0625f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 5))));
    }

    private LootTable.Builder ympeMouldLoot(EntityType<?> type) {
        return LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(Items.SOUL_SOIL)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))));
    }

    private LootTable.Builder boneflyLoot(EntityType<?> type) {
        return LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(Items.SOUL_SOIL)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 3))));
    }

    private LootTable.Builder tulpaLoot(EntityType<?> type) {
        return LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(Items.SOUL_SOIL)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2))));
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