package moriyashiine.aylyth.common.item.potion;

import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;

public interface AylythPotionRecipes {
    static void register() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.AWKWARD, AylythItems.NYSIAN_GRAPES, AylythPotions.MORTECHIS);
            builder.registerPotionRecipe(AylythPotions.MORTECHIS, Items.FERMENTED_SPIDER_EYE, Potions.THICK);
            builder.registerPotionRecipe(AylythPotions.MORTECHIS, Items.REDSTONE, AylythPotions.LONG_MORTECHIS);
            builder.registerPotionRecipe(AylythPotions.MORTECHIS, Items.GLOWSTONE_DUST, AylythPotions.STRONG_MORTECHIS);

            builder.registerPotionRecipe(Potions.AWKWARD, AylythItems.BLIGHTED_THORNS, AylythPotions.BLIGHT);
            builder.registerPotionRecipe(AylythPotions.BLIGHT, Items.REDSTONE, AylythPotions.LONG_BLIGHT);
            builder.registerPotionRecipe(AylythPotions.BLIGHT, Items.GLOWSTONE_DUST, AylythPotions.STRONG_BLIGHT);
        });
    }
}
