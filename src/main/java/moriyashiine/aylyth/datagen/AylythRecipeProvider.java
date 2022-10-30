package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class AylythRecipeProvider extends FabricRecipeProvider {

    public AylythRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerSingleOutputShapelessRecipe(exporter, Items.ORANGE_DYE, ModItems.MARIGOLD, "");

        ShapedRecipeJsonBuilder.create(ModItems.BONEFLY_SKULL)
                .input('b', Items.BONE_MEAL)
                .input('n', Items.BONE_BLOCK)
                .input('s', Items.NETHERITE_BLOCK)
                .pattern("nnn")
                .pattern("nsn")
                .pattern("bnb")
                .criterion("has_bonemeal", conditionsFromItem(Items.BONE_MEAL))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(ModItems.GLAIVE)
                .input('s', Items.NETHERITE_SWORD)
                .input('g', Items.GOLD_BLOCK)
                .input('i', Items.STICK)
                .input('m', ModItems.SOULTRAP_EFFIGY_ITEM)
                .pattern(" gs")
                .pattern(" im")
                .pattern("i  ")
                .criterion("has_soultrap", conditionsFromItem(ModItems.SOULTRAP_EFFIGY_ITEM))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(ModItems.SOULMOULD_ITEM)
                .input('d', Items.POLISHED_DEEPSLATE)
                .input('n', Items.NETHERITE_INGOT)
                .input('s', Items.SOUL_SAND)
                .pattern("dnd")
                .pattern("nsn")
                .pattern("ddd")
                .criterion("has_soulsand", conditionsFromItem(Items.SOUL_SAND))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(ModItems.SOULTRAP_EFFIGY_ITEM)
                .input('d', Items.SOUL_SOIL)
                .input('a', Items.NETHERITE_SCRAP)
                .input('n', Items.NETHERITE_INGOT)
                .input('s', ModItems.SOULMOULD_ITEM)
                .pattern("dnd")
                .pattern("nsn")
                .pattern("ada")
                .criterion("has_soulmould", conditionsFromItem(ModItems.SOULMOULD_ITEM))
                .offerTo(exporter);
    }
}
