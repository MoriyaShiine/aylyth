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
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;

public class FireRitualRecipe implements Recipe<RecipeInput> {
    public final List<Ingredient> input;
    public final ItemStack output;

    public FireRitualRecipe(List<Ingredient> input, ItemStack output) {
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
    public RecipeSerializer<FireRitualRecipe> getSerializer() {
        return AylythRecipeTypes.FIRE_RITUAL_SERIALIZER;
    }

    @Override
    public RecipeType<FireRitualRecipe> getType() {
        return AylythRecipeTypes.FIRE_RITUAL_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.CAMPFIRE;
    }

    public static class Serializer implements RecipeSerializer<FireRitualRecipe> {
        public static final MapCodec<FireRitualRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(soulCampfireRecipe -> soulCampfireRecipe.input),
                        ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(soulCampfireRecipe -> soulCampfireRecipe.output)
                ).apply(instance, FireRitualRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, FireRitualRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()), soulCampfireRecipe -> soulCampfireRecipe.input,
                ItemStack.PACKET_CODEC, soulCampfireRecipe -> soulCampfireRecipe.output,
                FireRitualRecipe::new
        );

        @Override
        public MapCodec<FireRitualRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FireRitualRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
