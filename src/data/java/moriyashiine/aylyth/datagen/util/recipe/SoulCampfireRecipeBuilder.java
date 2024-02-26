package moriyashiine.aylyth.datagen.util.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModRecipeTypes;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class SoulCampfireRecipeBuilder {
    private final List<Ingredient> ingredients;
    private final ItemStack result;

    public SoulCampfireRecipeBuilder(DefaultedList<Ingredient> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    public static SoulCampfireRecipeBuilder create(List<Item> items, ItemConvertible output) {
        return create(items.stream().map(Ingredient::ofItems).toList(), new ItemStack(output.asItem()));
    }

    public static SoulCampfireRecipeBuilder createWithIngredients(List<Ingredient> items, ItemConvertible output) {
        return create(items, new ItemStack(output.asItem()));
    }

    public static SoulCampfireRecipeBuilder create(List<Ingredient> ingredients, ItemStack output) {
        if (ingredients.size() > 4) {
            throw new IllegalArgumentException("Cannot have more than 4 ingredients in soul campfire recipe");
        }
        DefaultedList<Ingredient> list = DefaultedList.ofSize(4, Ingredient.EMPTY);
        for (int i = 0; i < ingredients.size(); i++) {
            list.set(i, ingredients.get(i));
        }
        return new SoulCampfireRecipeBuilder(list, output);
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter) {
        this.offerTo(exporter, new Identifier(Aylyth.MOD_ID, "soul_campfire/%s".formatted(Registries.ITEM.getId(result.getItem()).getPath())));
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        exporter.accept(new RecipeJsonProvider() {
            @Override
            public void serialize(JsonObject json) {
                json.add("ingredients", ingredients.stream().map(Ingredient::toJson).collect(JsonArray::new, JsonArray::add, JsonArray::addAll));
                JsonObject obj = new JsonObject();
                obj.addProperty("item", Registries.ITEM.getId(result.getItem()).toString());
                if (result.getCount() > 1) {
                    obj.addProperty("count", result.getCount());
                }
                json.add("result", obj);
            }

            @Override
            public Identifier getRecipeId() {
                return recipeId;
            }

            @Override
            public RecipeSerializer<?> getSerializer() {
                return ModRecipeTypes.SOULFIRE_RECIPE_SERIALIZER;
            }

            @Nullable
            @Override
            public JsonObject toAdvancementJson() {
                return null;
            }

            @Nullable
            @Override
            public Identifier getAdvancementId() {
                return null;
            }
        });
    }
}
