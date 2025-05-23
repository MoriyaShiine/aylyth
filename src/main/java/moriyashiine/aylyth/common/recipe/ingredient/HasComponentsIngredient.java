package moriyashiine.aylyth.common.recipe.ingredient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Stream;

public record HasComponentsIngredient(Ingredient base, List<ComponentType<?>> componentTypes) implements CustomIngredient {
    public static Ingredient of(Ingredient base, ComponentType<?>... componentTypes) {
        return new HasComponentsIngredient(base, List.of(componentTypes)).toVanilla();
    }

    @Override
    public boolean test(ItemStack stack) {
        if (!base.test(stack)){
            return false;
        }

        for (ComponentType<?> componentType : componentTypes) {
            if (!stack.contains(componentType)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Stream<RegistryEntry<Item>> getMatchingItems() {
        return base.getMatchingItems();
    }

    @Override
    public boolean requiresTesting() {
        return true;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements CustomIngredientSerializer<HasComponentsIngredient> {
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = Aylyth.id("has_components");
        public static final MapCodec<HasComponentsIngredient> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC.fieldOf("base").forGetter(HasComponentsIngredient::base),
                        ComponentType.CODEC.listOf().fieldOf("component_types").forGetter(HasComponentsIngredient::componentTypes)
                ).apply(instance, HasComponentsIngredient::new)
        );
        public static final PacketCodec<RegistryByteBuf, HasComponentsIngredient> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC, HasComponentsIngredient::base,
                ComponentType.PACKET_CODEC.collect(PacketCodecs.toList()), HasComponentsIngredient::componentTypes,
                HasComponentsIngredient::new
        );

        @Override
        public Identifier getIdentifier() {
            return ID;
        }

        @Override
        public MapCodec<HasComponentsIngredient> getCodec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, HasComponentsIngredient> getPacketCodec() {
            return PACKET_CODEC;
        }
    }
}
