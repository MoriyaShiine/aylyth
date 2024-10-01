package moriyashiine.aylyth.datagen.mixin;

import net.minecraft.advancement.Advancement;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ShapelessRecipeJsonBuilder.class)
public interface ShapelessRecipeJsonBuilderAccessor {
    @Accessor
    RecipeCategory getCategory();
    @Accessor
    Item getOutput();
    @Accessor
    int getCount();
    @Accessor
    List<Ingredient> getInputs();
    @Accessor
    Advancement.Builder getAdvancementBuilder();
    @Accessor
    String getGroup();
}
