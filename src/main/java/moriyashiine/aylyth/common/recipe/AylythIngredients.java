package moriyashiine.aylyth.common.recipe;

import moriyashiine.aylyth.common.recipe.ingredient.HasComponentsIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;

public interface AylythIngredients {
    static void register() {
        CustomIngredientSerializer.register(HasComponentsIngredient.Serializer.INSTANCE);
    }
}
