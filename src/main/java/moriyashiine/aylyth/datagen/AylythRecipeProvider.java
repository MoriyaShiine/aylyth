package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.util.ItemWoodSuite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

import java.util.function.Consumer;

public class AylythRecipeProvider extends FabricRecipeProvider {

    public AylythRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerSingleOutputShapelessRecipe(exporter, Items.ORANGE_DYE, ModItems.MARIGOLD, "");
        createTwoByTwo(exporter, Items.SHROOMLIGHT, 1, ModItems.JACK_O_LANTERN_MUSHROOM, "shroomlight_from_jack_o_lantern_mushroom");
        woodSuiteRecipes(exporter, ModItems.POMEGRANATE_ITEMS);
    }

    private void woodSuiteRecipes(Consumer<RecipeJsonProvider> exporter, ItemWoodSuite suite) {
        createTwoByTwo(exporter, suite.strippedWood, 3, suite.strippedLog, "bark", getRecipeName(suite.strippedWood));
        createTwoByTwo(exporter, suite.wood, 3, suite.log, "bark", getRecipeName(suite.wood));
        offerShapelessRecipe(exporter, suite.planks, suite.log, "planks", 4);
        createStairsRecipe(suite.stairs, Ingredient.ofItems(suite.planks)).group("wooden_stairs").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createSlabRecipe(suite.slab, Ingredient.ofItems(suite.planks)).group("wooden_slab").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createFenceRecipe(suite.fence, Ingredient.ofItems(suite.planks)).group("wooden_fence").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createFenceGateRecipe(suite.fenceGate, Ingredient.ofItems(suite.planks)).group("wooden_fence_gate").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createPressurePlateRecipe(suite.pressurePlate, Ingredient.ofItems(suite.planks)).group("wooden_pressure_plate").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        offerShapelessRecipe(exporter, suite.button, suite.planks, "wooden_button", 1);
        createTrapdoorRecipe(suite.trapdoor, Ingredient.ofItems(suite.planks)).group("wooden_trapdoor").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createDoorRecipe(suite.door, Ingredient.ofItems(suite.planks)).group("wooden_door").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createSignRecipe(suite.sign, Ingredient.ofItems(suite.planks)).group("wooden_sign").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        offerBoatRecipe(exporter, suite.boat, suite.planks);
        offerChestBoatRecipe(exporter, suite.chestBoat, suite.planks);
    }

    private void createTwoByTwo(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, int outputCount, ItemConvertible input, String fileId) {
        ShapedRecipeJsonBuilder.create(output, outputCount).input('#', input).pattern("## ").pattern("## ").criterion(RecipeProvider.hasItem(input), conditionsFromItem(input)).offerTo(exporter, fileId);
    }

    private void createTwoByTwo(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, int outputCount, ItemConvertible input, String group, String fileId) {
        ShapedRecipeJsonBuilder.create(output, outputCount).input('#', input).pattern("## ").pattern("## ").group(group).criterion(RecipeProvider.hasItem(input), conditionsFromItem(input)).offerTo(exporter, fileId);
    }

    private ShapedRecipeJsonBuilder shaped(ItemConvertible output, int outputCount) {
        return ShapedRecipeJsonBuilder.create(output, outputCount);
    }
}
