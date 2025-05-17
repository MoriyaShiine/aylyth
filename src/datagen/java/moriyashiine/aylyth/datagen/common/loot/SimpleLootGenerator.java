package moriyashiine.aylyth.datagen.common.loot;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.data.loottable.LootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.BiConsumer;

public abstract class SimpleLootGenerator implements LootTableGenerator {
    protected final RegistryWrapper.WrapperLookup registries;
    private final Map<Identifier, LootTable.Builder> loot;

    public SimpleLootGenerator(RegistryWrapper.WrapperLookup registries) {
        this.registries = registries;
        this.loot = new Object2ObjectOpenHashMap<>();
    }

    protected abstract void generateLoot();

    protected void addDrop(Identifier id, LootTable.Builder builder) {
        loot.put(id, builder);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> consumer) {
        this.generateLoot();
        for (Map.Entry<Identifier, LootTable.Builder> entry : loot.entrySet()) {
            consumer.accept(RegistryKey.of(RegistryKeys.LOOT_TABLE, entry.getKey()), entry.getValue());
        }
    }
}
