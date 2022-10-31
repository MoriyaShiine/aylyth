package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModTags;
import moriyashiine.aylyth.common.registry.util.ItemWoodSuite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

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
        woodSuiteRecipes(exporter, ModItems.WRITHEWOOD_ITEMS);
        offerShapeless(exporter, ModItems.GHOSTCAP_MUSHROOM_SPORES, 1, ModItems.GHOSTCAP_MUSHROOM, null);

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

    private void woodSuiteRecipes(Consumer<RecipeJsonProvider> exporter, ItemWoodSuite suite) {
        offerBarkBlockRecipe(exporter, suite.strippedWood, suite.strippedLog);
        offerBarkBlockRecipe(exporter, suite.wood, suite.log);
        offerPlanksRecipe(exporter, suite.planks, ModTags.POMEGRANATE_LOGS_ITEM);
        createStairsRecipe(suite.stairs, Ingredient.ofItems(suite.planks)).group("wooden_stairs").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createSlabRecipe(suite.slab, Ingredient.ofItems(suite.planks)).group("wooden_slab").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createFenceRecipe(suite.fence, Ingredient.ofItems(suite.planks)).group("wooden_fence").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createFenceGateRecipe(suite.fenceGate, Ingredient.ofItems(suite.planks)).group("wooden_fence_gate").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createPressurePlateRecipe(suite.pressurePlate, Ingredient.ofItems(suite.planks)).group("wooden_pressure_plate").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        offerShapeless(exporter, suite.button, 1, suite.planks, "wooden_button");
        createTrapdoorRecipe(suite.trapdoor, Ingredient.ofItems(suite.planks)).group("wooden_trapdoor").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createDoorRecipe(suite.door, Ingredient.ofItems(suite.planks)).group("wooden_door").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createSignRecipe(suite.sign, Ingredient.ofItems(suite.planks)).group("wooden_sign").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        offerBoatRecipe(exporter, suite.boat, suite.planks);
        offerChestBoatRecipe(exporter, suite.chestBoat, suite.planks);
    }

    private void createTwoByTwo(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, int outputCount, ItemConvertible input, String recipeId) {
        ShapedRecipeJsonBuilder.create(output, outputCount).input('#', input).pattern("## ").pattern("## ").criterion(RecipeProvider.hasItem(input), conditionsFromItem(input)).offerTo(exporter, recipeId);
    }

    private void createTwoByTwo(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, int outputCount, ItemConvertible input, String group, String recipeId) {
        ShapedRecipeJsonBuilder.create(output, outputCount).input('#', input).pattern("## ").pattern("## ").group(group).criterion(RecipeProvider.hasItem(input), conditionsFromItem(input)).offerTo(exporter, recipeId);
    }

    private void offerShapeless(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, int outputCount, ItemConvertible input, @Nullable String group) {
        offerShapeless(exporter, output, outputCount, input, group, convertBetween(output, input));
    }

    private void offerShapeless(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, int outputCount, ItemConvertible input, @Nullable String group, @Nullable String recipeId) {
        var recipe = shapeless(output, outputCount).input(input).criterion(RecipeProvider.hasItem(input), conditionsFromItem(input));
        if (group != null) {
            recipe.group(group);
        }
        if (recipeId != null) {
            recipe.offerTo(exporter, new Identifier(dataGenerator.getModId(), recipeId));
        } else {
            recipe.offerTo(exporter);
        }
    }

    private ShapelessRecipeJsonBuilder shapeless(ItemConvertible output, int outputCount) {
        return ShapelessRecipeJsonBuilder.create(output, outputCount);
    }

    private ShapedRecipeJsonBuilder shaped(ItemConvertible output, int outputCount) {
        return ShapedRecipeJsonBuilder.create(output, outputCount);
    }
}
