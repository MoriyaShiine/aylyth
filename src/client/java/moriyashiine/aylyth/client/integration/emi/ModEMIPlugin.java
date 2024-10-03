package moriyashiine.aylyth.client.integration.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.recipe.AylythRecipeTypes;
import moriyashiine.aylyth.common.recipe.types.YmpeDaggerDropRecipe;
import net.minecraft.recipe.RecipeManager;

public class ModEMIPlugin implements EmiPlugin {
    private static final EmiStack ICON = EmiStack.of(AylythItems.YMPE_DAGGER);
    public static final EmiRecipeCategory YMPE_DAGGER_CATEGORY = new EmiRecipeCategory(
            Aylyth.id("ympe_dagger_drops"), ICON
    );


    @Override
    public void register(EmiRegistry emiRegistry) {
        emiRegistry.addCategory(YMPE_DAGGER_CATEGORY);
        RecipeManager manager = emiRegistry.getRecipeManager();
        for (YmpeDaggerDropRecipe recipe : manager.listAllOfType(AylythRecipeTypes.YMPE_DAGGER_DROP_TYPE)) {
            emiRegistry.addRecipe(new YmpeDaggerEMIRecipe(recipe));
        }
    }
}
