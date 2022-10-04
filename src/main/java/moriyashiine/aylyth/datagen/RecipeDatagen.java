package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class RecipeDatagen extends FabricRecipeProvider {

    public RecipeDatagen(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerSingleOutputShapelessRecipe(exporter, Items.ORANGE_DYE, ModItems.MARIGOLD, "");
    }
}
