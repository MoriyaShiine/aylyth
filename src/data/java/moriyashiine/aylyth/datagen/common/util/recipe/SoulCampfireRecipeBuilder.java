package moriyashiine.aylyth.datagen.common.util.recipe;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModRecipeTypes;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class SoulCampfireRecipeBuilder {
    private final List<Ingredient> ingredients;
    private final ItemStack result;

    public SoulCampfireRecipeBuilder(ItemStack result) {
        this.ingredients = new ObjectArrayList<>();
        this.result = result;
    }

    public static SoulCampfireRecipeBuilder create(ItemConvertible output) {
        return create(new ItemStack(output.asItem()));
    }

    public static SoulCampfireRecipeBuilder create(ItemStack output) {
        return new SoulCampfireRecipeBuilder(output);
    }

    public SoulCampfireRecipeBuilder with(ItemConvertible itemConvertible) {
        return with(Ingredient.ofItems(itemConvertible));
    }

    public SoulCampfireRecipeBuilder with(TagKey<Item> tag) {
        return with(Ingredient.fromTag(tag));
    }

    public SoulCampfireRecipeBuilder with(Ingredient ingredient) {
        checkSize();
        ingredients.add(ingredient);
        return this;
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter) {
        this.offerTo(exporter, new Identifier(Aylyth.MOD_ID, "soul_campfire/%s".formatted(Registries.ITEM.getId(result.getItem()).getPath())));
    }

    private void checkSize() {
        Preconditions.checkState(ingredients.size() < 5, "Campfire recipes may only have 4 ingredients");
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        Preconditions.checkState(!ingredients.isEmpty(), "Must have positive number of ingredients");
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
