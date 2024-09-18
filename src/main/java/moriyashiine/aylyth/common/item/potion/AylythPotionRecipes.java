package moriyashiine.aylyth.common.item.potion;

import moriyashiine.aylyth.common.item.AylythItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;

import static net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry.registerPotionRecipe;
import static net.minecraft.recipe.Ingredient.ofItems;

public final class AylythPotionRecipes {
    private AylythPotionRecipes() {}

    public static void register() {
        register(Potions.AWKWARD, AylythItems.NYSIAN_GRAPES, AylythPotions.MORTECHIS);
        register(AylythPotions.MORTECHIS, Items.FERMENTED_SPIDER_EYE, Potions.THICK);
        register(AylythPotions.MORTECHIS, Items.REDSTONE, AylythPotions.LONG_MORTECHIS);
        register(AylythPotions.MORTECHIS, Items.GLOWSTONE_DUST, AylythPotions.STRONG_MORTECHIS);

        register(Potions.AWKWARD, AylythItems.BLIGHTED_THORNS, AylythPotions.BLIGHT);
        register(AylythPotions.BLIGHT, Items.REDSTONE, AylythPotions.LONG_BLIGHT);
        register(AylythPotions.BLIGHT, Items.GLOWSTONE_DUST, AylythPotions.STRONG_BLIGHT);
    }

    private static void register(Potion potion, Item ingredient, Potion result) {
        registerPotionRecipe(potion, ofItems(ingredient), result);
    }
}
