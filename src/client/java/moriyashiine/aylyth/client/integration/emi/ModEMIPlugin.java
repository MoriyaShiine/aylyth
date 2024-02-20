package moriyashiine.aylyth.client.integration.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModRecipeTypes;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.recipe.RecipeManager;

public class ModEMIPlugin implements EmiPlugin {
    private static final EmiStack ICON = EmiStack.of(ModItems.YMPE_DAGGER);
    public static final EmiRecipeCategory YMPE_DAGGER_CATEGORY = new EmiRecipeCategory(
            AylythUtil.id("ympe_dagger_drops"), ICON
    );


    @Override
    public void register(EmiRegistry emiRegistry) {
        emiRegistry.addCategory(YMPE_DAGGER_CATEGORY);
        RecipeManager manager = emiRegistry.getRecipeManager();
        for (YmpeDaggerDropRecipe recipe : manager.listAllOfType(ModRecipeTypes.YMPE_DAGGER_DROP_RECIPE_TYPE)) {
            emiRegistry.addRecipe(new YmpeDaggerEMIRecipe(recipe));
        }
    }
}
