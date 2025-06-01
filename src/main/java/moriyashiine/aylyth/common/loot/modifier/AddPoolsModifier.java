package moriyashiine.aylyth.common.loot.modifier;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.loot.LootTableModifiers;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;

public record AddPoolsModifier(List<LootPool> pools) implements LootTableModifier {
    public static final MapCodec<AddPoolsModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    LootPool.CODEC.listOf().fieldOf("pools").forGetter(AddPoolsModifier::pools)
            ).apply(instance, AddPoolsModifier::new)
    );

    @Override
    public void modifyTable(LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        for (LootPool pool : this.pools) {
            tableBuilder.pool(pool);
        }
    }

    @Override
    public MapCodec<? extends LootTableModifier> getCodec() {
        return LootTableModifiers.ADD_POOLS;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<LootPool> pools;

        Builder() {
            this.pools = new ObjectArrayList<>();
        }

        public Builder addPool(LootPool pool) {
            this.pools.add(pool);
            return this;
        }

        public Builder addPool(LootPool.Builder builder) {
            return addPool(builder.build());
        }

        public AddPoolsModifier build() {
            return new AddPoolsModifier(this.pools);
        }
    }
}
