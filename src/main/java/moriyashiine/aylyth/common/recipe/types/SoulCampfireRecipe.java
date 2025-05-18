package moriyashiine.aylyth.common.recipe.types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.recipe.AylythRecipeTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;

public class SoulCampfireRecipe implements Recipe<RecipeInput> {
    public final List<Ingredient> input;
    public final ItemStack output;

    public SoulCampfireRecipe(List<Ingredient> input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(RecipeInput inventory, World world) {
        RecipeMatcher<ItemStack> matcher = new RecipeMatcher<>();
        int numItems = 0;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStackInSlot(i).copy();
            if (!stack.isEmpty()) {
                while (!stack.isEmpty()) {
                    numItems++;
                    matcher.add(stack.split(1), 1);
                }
            }
        }
        return numItems == input.size() && matcher.match(
                this.input.stream()
                        .map(ingredient -> (RecipeMatcher.RawIngredient<ItemStack>) ingredient::test)
                        .toList(),
                1, null);
    }

    @Override
    public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return output.copy();
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public RecipeSerializer<SoulCampfireRecipe> getSerializer() {
        return AylythRecipeTypes.SOULFIRE_SERIALIZER;
    }

    @Override
    public RecipeType<SoulCampfireRecipe> getType() {
        return AylythRecipeTypes.SOULFIRE_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return null;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<SoulCampfireRecipe> {
        public static final MapCodec<SoulCampfireRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(soulCampfireRecipe -> soulCampfireRecipe.input),
                        ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(soulCampfireRecipe -> soulCampfireRecipe.output)
                ).apply(instance, SoulCampfireRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, SoulCampfireRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()), soulCampfireRecipe -> soulCampfireRecipe.input,
                ItemStack.PACKET_CODEC, soulCampfireRecipe -> soulCampfireRecipe.output,
                SoulCampfireRecipe::new
        );

        @Override
        public MapCodec<SoulCampfireRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, SoulCampfireRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
