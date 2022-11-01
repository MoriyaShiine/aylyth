package moriyashiine.aylyth.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import moriyashiine.aylyth.common.registry.ModRecipeTypes;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

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
        return matches(inventory, input);
    }

    public static boolean matches(Inventory inv, DefaultedList<Ingredient> input) {
        List<ItemStack> checklist = new ArrayList<>();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (!stack.isEmpty()) {
                checklist.add(stack);
            }
        }
        if (input.size() != checklist.size()) {
            return false;
        }
        for (Ingredient ingredient : input) {
            boolean found = false;
            for (ItemStack stack : checklist) {
                if (ingredient.test(stack)) {
                    found = true;
                    checklist.remove(stack);
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public Identifier getId() {
        return identifier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.SOULFIRE_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.SOULFIRE_RECIPE_TYPE;
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
            buf.writeItemStack(recipe.getOutput());
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
