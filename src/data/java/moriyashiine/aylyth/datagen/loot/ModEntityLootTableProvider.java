package moriyashiine.aylyth.datagen.loot;

import com.google.common.collect.Maps;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.GroupEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ModEntityLootTableProvider extends SimpleFabricLootTableProvider {

    private final Map<Identifier, LootTable.Builder> loot = Maps.newHashMap();

    public ModEntityLootTableProvider(FabricDataOutput dataGenerator) {
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