package moriyashiine.aylyth.datagen.mixin;

import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(ShapelessRecipeJsonBuilder.class)
public interface ShapelessRecipeJsonBuilderAccessor {
    @Accessor
    RecipeCategory getCategory();
    @Accessor
    ItemStack getOutput();
    @Accessor
    List<Ingredient> getInputs();
    @Accessor
    Map<String, AdvancementCriterion<?>> getAdvancementBuilder();
    @Accessor
    String getGroup();
}
