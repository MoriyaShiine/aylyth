package moriyashiine.aylyth.datagen.common.util.recipe;

import moriyashiine.aylyth.common.recipe.types.ShuckingRecipe;
import moriyashiine.aylyth.datagen.mixin.ShapelessRecipeJsonBuilderAccessor;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;

public class ShuckingRecipeBuilder extends ShapelessRecipeJsonBuilder {
    public ShuckingRecipeBuilder(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, ItemStack output) {
        super(registryLookup, category, output);
    }

    public static ShuckingRecipeBuilder create(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, ItemConvertible output) {
        return create(registryLookup, category, output, 1);
    }

    public static ShuckingRecipeBuilder create(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, ItemConvertible output, int count) {
        return new ShuckingRecipeBuilder(registryLookup, category, new ItemStack(output, count));
    }

    @Override
    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        ShapelessRecipeJsonBuilderAccessor accessor = (ShapelessRecipeJsonBuilderAccessor) this;
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
                .rewards(net.minecraft.advancement.AdvancementRewards.Builder.recipe(recipeKey))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        exporter.accept(recipeKey,
                new ShuckingRecipe(
                        new ShapelessRecipe(
                                accessor.getGroup() == null ? "" : accessor.getGroup(),
                                CraftingRecipeJsonBuilder.toCraftingCategory(accessor.getCategory()),
                                accessor.getOutput(),
                                accessor.getInputs()
                        )
                ),
                builder.build(recipeKey.getValue().withPrefixedPath("recipes/" + accessor.getCategory().getName() + "/"))
        );
    }
}
