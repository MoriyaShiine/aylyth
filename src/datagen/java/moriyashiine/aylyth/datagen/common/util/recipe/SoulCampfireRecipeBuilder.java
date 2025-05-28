package moriyashiine.aylyth.datagen.common.util.recipe;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.recipe.types.FireRitualRecipe;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.List;

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

    public SoulCampfireRecipeBuilder with(Ingredient ingredient) {
        checkSize();
        ingredients.add(ingredient);
        return this;
    }

    private void checkSize() {
        Preconditions.checkState(ingredients.size() < 5, "Campfire recipes may only have 4 ingredients");
    }

    public void offerTo(RecipeExporter exporter) {
        this.offerTo(exporter, Aylyth.id("soul_campfire/%s".formatted(Registries.ITEM.getId(result.getItem()).getPath())));
    }

    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        this.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, recipeId));
    }

    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> key) {
        Preconditions.checkState(!ingredients.isEmpty(), "Must have positive number of ingredients");
        exporter.accept(key, new FireRitualRecipe(ingredients, result), null);
    }
}
