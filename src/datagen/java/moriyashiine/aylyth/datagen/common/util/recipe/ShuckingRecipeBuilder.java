package moriyashiine.aylyth.datagen.common.util.recipe;

import moriyashiine.aylyth.common.recipe.AylythRecipeTypes;
import moriyashiine.aylyth.datagen.mixin.ShapelessRecipeJsonBuilderAccessor;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ShuckingRecipeBuilder extends ShapelessRecipeJsonBuilder {
    public ShuckingRecipeBuilder(RecipeCategory category, ItemConvertible output, int count) {
        super(category, output, count);
    }

    public static ShuckingRecipeBuilder create(RecipeCategory category, ItemConvertible output) {
        return create(category, output, 1);
    }

    public static ShuckingRecipeBuilder create(RecipeCategory category, ItemConvertible output, int count) {
        return new ShuckingRecipeBuilder(category, output, count);
    }

    @Override
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        ShapelessRecipeJsonBuilderAccessor accessor = (ShapelessRecipeJsonBuilderAccessor) this;
        accessor.getAdvancementBuilder()
                .parent(ROOT)
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(CriterionMerger.OR);
        exporter.accept(
                new ShuckingRecipeJsonProvider(
                        recipeId,
                        accessor.getOutput(),
                        accessor.getCount(),
                        accessor.getGroup() == null ? "" : accessor.getGroup(),
                        getCraftingCategory(accessor.getCategory()),
                        accessor.getInputs(),
                        accessor.getAdvancementBuilder(),
                        recipeId.withPrefixedPath("recipes/" + accessor.getCategory().getName() + "/")
                )
        );
    }

    public static class ShuckingRecipeJsonProvider extends ShapelessRecipeJsonProvider {
        public ShuckingRecipeJsonProvider(Identifier recipeId, Item output, int outputCount, String group, CraftingRecipeCategory craftingCategory, List<Ingredient> inputs, Advancement.Builder advancementBuilder, Identifier advancementId) {
            super(recipeId, output, outputCount, group, craftingCategory, inputs, advancementBuilder, advancementId);
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return AylythRecipeTypes.SHUCKING_SERIALIZER;
        }
    }
}
