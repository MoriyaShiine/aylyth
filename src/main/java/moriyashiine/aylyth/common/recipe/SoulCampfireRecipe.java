package moriyashiine.aylyth.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import moriyashiine.aylyth.common.registry.AylythRecipeTypes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SoulCampfireRecipe implements Recipe<Inventory> {
    private final Identifier identifier;
    public final DefaultedList<Ingredient> input;
    public final ItemStack output;

    public SoulCampfireRecipe(Identifier id, DefaultedList<Ingredient> input, ItemStack output) {
        this.identifier = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        RecipeMatcher matcher = new RecipeMatcher();
        int numItems = 0;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i).copy();
            if (!stack.isEmpty()) {
                while (!stack.isEmpty()) {
                    numItems++;
                    matcher.addInput(stack.split(1));
                }
            }
        }
        return numItems == input.size() && matcher.match(this, null);
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return input;
    }

    @Override
    public Identifier getId() {
        return identifier;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AylythRecipeTypes.SOULFIRE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return AylythRecipeTypes.SOULFIRE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<SoulCampfireRecipe> {
        @Override
        public SoulCampfireRecipe read(Identifier id, JsonObject json) {
            DefaultedList<Ingredient> ingredients = getIngredients(JsonHelper.getArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for recipe");
            } else if (ingredients.size() > 4) {
                throw new JsonParseException("Too many ingredients for recipe");
            }
            return new SoulCampfireRecipe(id, ingredients, ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result")));
        }

        @Override
        public SoulCampfireRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            defaultedList.replaceAll(ignored -> Ingredient.fromPacket(buf));
            return new SoulCampfireRecipe(id, defaultedList, buf.readItemStack());

        }

        @Override
        public void write(PacketByteBuf buf, SoulCampfireRecipe recipe) {
            buf.writeVarInt(recipe.input.size());
            for (Ingredient ingredient : recipe.input) {
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.getOutput(DynamicRegistryManager.EMPTY));
        }

        public static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> ingredients = DefaultedList.of();
            for (int i = 0; i < json.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }
            return ingredients;
        }
    }
}
