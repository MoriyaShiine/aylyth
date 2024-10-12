package moriyashiine.aylyth.common.loot.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.loot.LootDisplayTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.entry.RegistryEntryListCodec;
import net.minecraft.registry.tag.TagKey;

public record DaggerLootDisplay(EntityType<?> entity, float chance, RegistryEntryList<Item> weapons, ItemStack outputs) implements EntityLootDisplay {
    public static final Codec<DaggerLootDisplay> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Registries.ENTITY_TYPE.getCodec().fieldOf("entity").forGetter(DaggerLootDisplay::entity),
                    Codec.FLOAT.fieldOf("chance").forGetter(DaggerLootDisplay::chance),
                    RegistryEntryListCodec.create(RegistryKeys.ITEM, Registries.ITEM.createEntryCodec(), false).fieldOf("weapons").forGetter(DaggerLootDisplay::weapons),
                    ItemStack.CODEC.fieldOf("output").forGetter(DaggerLootDisplay::outputs)
            ).apply(instance, DaggerLootDisplay::new)
    );
    public static final Codec<DaggerLootDisplay> NETWORK_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Registries.ENTITY_TYPE.getCodec().fieldOf("entity").forGetter(DaggerLootDisplay::entity),
                    Codec.FLOAT.fieldOf("chance").forGetter(DaggerLootDisplay::chance),
                    RegistryEntryListCodec.create(RegistryKeys.ITEM, Registries.ITEM.createEntryCodec(), false).fieldOf("weapons").forGetter(DaggerLootDisplay::weapons),
                    ItemStack.CODEC.fieldOf("output").forGetter(DaggerLootDisplay::outputs)
            ).apply(instance, DaggerLootDisplay::new)
    );

    public static DaggerLootDisplay create(EntityType<?> entity, float chance, TagKey<Item> weaponsTag, ItemConvertible output) {
        return new DaggerLootDisplay(entity, chance, RegistryEntryList.of(Registries.ITEM.getEntryOwner(), weaponsTag), new ItemStack(output));
    }

    @Override
    public EntityType<?> entity() {
        return entity;
    }

    @Override
    public ItemStack outputs() {
        return outputs;
    }

    @Override
    public Type<? extends LootDisplay> type() {
        return LootDisplayTypes.YMPE_DAGGER;
    }
}
