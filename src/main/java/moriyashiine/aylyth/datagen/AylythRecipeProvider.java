package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModTags;
import moriyashiine.aylyth.common.registry.util.ItemWoodSuite;
import moriyashiine.aylyth.datagen.recipe.YmpeDaggerRecipeJsonBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AylythRecipeProvider extends FabricRecipeProvider {

    public AylythRecipeProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSingleOutputShapelessRecipe(exporter, Items.ORANGE_DYE, ModItems.MARIGOLD, "");
        createTwoByTwo(exporter, RecipeCategory.DECORATIONS, Items.SHROOMLIGHT, 1, ModItems.JACK_O_LANTERN_MUSHROOM, "shroomlight_from_jack_o_lantern_mushroom");
        woodSuiteRecipes(exporter, ModItems.POMEGRANATE_ITEMS, ModTags.POMEGRANATE_LOGS_ITEM);
        woodSuiteRecipes(exporter, ModItems.WRITHEWOOD_ITEMS, ModTags.WRITHEWOOD_LOGS_ITEM);
        offerShapeless(exporter, RecipeCategory.MISC, ModItems.GHOSTCAP_MUSHROOM_SPORES, 1, ModItems.GHOSTCAP_MUSHROOM, null);
        offerChestBoatRecipe(exporter, ModItems.YMPE_ITEMS.chestBoat, ModItems.YMPE_ITEMS.boat);


        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_WOODS_TILES, 8)
                .input('Y', ModBlocks.YMPE_BLOCKS.planks)
                .input('W', ModBlocks.WRITHEWOOD_BLOCKS.planks)
                .pattern("YW")
                .pattern("WY")
                .criterion("has_writhe", conditionsFromItem(ModBlocks.WRITHEWOOD_BLOCKS.planks))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_WOODS_TILES, 8)
                .input('Y', ModBlocks.YMPE_BLOCKS.planks)
                .input('W', ModBlocks.WRITHEWOOD_BLOCKS.planks)
                .pattern("WY")
                .pattern("YW")
                .criterion("has_writhe", conditionsFromItem(ModBlocks.WRITHEWOOD_BLOCKS.planks))
                .offerTo(exporter, "aylyth:dark_woods_tiles_2");

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.YMPE_GLAIVE)
                .input('D', ModItems.YMPE_DAGGER)
                .input('S', ModItems.YMPE_ITEMS.sapling)
                .input('C', ModItems.CORIC_SEED)
                .pattern(" CD")
                .pattern(" SS")
                .pattern("S  ")
                .criterion("has_dagger", conditionsFromItem(ModItems.YMPE_DAGGER))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.YMPEMOULD_ITEM)
                .input('D', Items.POLISHED_DEEPSLATE)
                .input('E', ModItems.ESSTLINE)
                .input('S', ModItems.CORIC_SEED)
                .pattern("DND")
                .pattern("NSN")
                .pattern("DDD")
                .criterion("has_soulsand", conditionsFromItem(Items.SOUL_SAND))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.YMPE_EFFIGY_ITEM)
                .input('D', Items.SOUL_SOIL)
                .input('S', ModItems.YMPE_ITEMS.sapling)
                .input('E', ModItems.ESSTLINE)
                .input('C', ModItems.CORIC_SEED)
                .pattern("DND")
                .pattern("NCN")
                .pattern("SDS")
                .criterion("has_coric_seed", conditionsFromItem(ModItems.CORIC_SEED))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.VITAL_THURIBLE)
                .input('S', Items.POLISHED_DEEPSLATE_SLAB)
                .input('H', ModItems.AYLYTHIAN_HEART)
                .input('P', Items.POLISHED_DEEPSLATE_WALL)
                .input('E', ModItems.ESSTLINE)
                .input('C', Items.SOUL_CAMPFIRE)
                .pattern("SHS")
                .pattern("PNP")
                .pattern("PCP")
                .criterion("has_heart", conditionsFromItem(ModItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SOUL_HEARTH)
                .input('S', Items.POLISHED_DEEPSLATE_SLAB)
                .input('W', Items.POLISHED_DEEPSLATE_WALL)
                .input('C', Items.SOUL_CAMPFIRE)
                .input('H', ModItems.AYLYTHIAN_HEART)
                .pattern(" S ")
                .pattern("WCW")
                .pattern("WHW")
                .criterion("has_heart", conditionsFromItem(ModItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GIRASOL_SEED)
                .input('Y', ModItems.YMPE_ITEMS.sapling)
                .input('H', ModItems.AYLYTHIAN_HEART)
                .input('S', Ingredient.ofItems(Items.SOUL_SAND, Items.SOUL_SOIL))
                .input('E', Items.ENDER_PEARL)
                .pattern("YHY")
                .pattern("SES")
                .pattern("YSY")
                .criterion("has_heart", conditionsFromItem(ModItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.YMPE_CUIRASS)
                .input('W', ModItems.WRONGMEAT)
                .input('H', ModItems.AYLYTHIAN_HEART)
                .input('C', ModItems.CORIC_SEED)
                .input('G', ModItems.LARGE_WOODY_GROWTH)
                .pattern("GHG")
                .pattern("WCW")
                .pattern("G G")
                .criterion("has_heart", conditionsFromItem(ModItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        YmpeDaggerRecipeJsonBuilder.create(ModEntityTypes.WREATHED_HIND_ENTITY, ModItems.WRONGMEAT, 0.2f, 3, 5)
                .offerTo(exporter);
    }

    private void woodSuiteRecipes(Consumer<RecipeJsonProvider> exporter, ItemWoodSuite suite, TagKey<Item> logTag) {
        offerBarkBlockRecipe(exporter, suite.strippedWood, suite.strippedLog);
        offerBarkBlockRecipe(exporter, suite.wood, suite.log);
        offerPlanksRecipe(exporter, suite.planks, logTag, 4);
        createStairsRecipe(suite.stairs, Ingredient.ofItems(suite.planks)).group("wooden_stairs").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, suite.slab, Ingredient.ofItems(suite.planks)).group("wooden_slab").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createFenceRecipe(suite.fence, Ingredient.ofItems(suite.planks)).group("wooden_fence").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createFenceGateRecipe(suite.fenceGate, Ingredient.ofItems(suite.planks)).group("wooden_fence_gate").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createPressurePlateRecipe(RecipeCategory.REDSTONE, suite.pressurePlate, Ingredient.ofItems(suite.planks)).group("wooden_pressure_plate").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        offerShapeless(exporter, RecipeCategory.REDSTONE, suite.button, 1, suite.planks, "wooden_button");
        createTrapdoorRecipe(suite.trapdoor, Ingredient.ofItems(suite.planks)).group("wooden_trapdoor").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createDoorRecipe(suite.door, Ingredient.ofItems(suite.planks)).group("wooden_door").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        createSignRecipe(suite.sign, Ingredient.ofItems(suite.planks)).group("wooden_sign").criterion(RecipeProvider.hasItem(suite.planks), conditionsFromItem(suite.planks)).offerTo(exporter);
        offerBoatRecipe(exporter, suite.boat, suite.planks);
        offerChestBoatRecipe(exporter, suite.chestBoat, suite.boat);
    }

    private void createTwoByTwo(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input, String recipeId) {
        ShapedRecipeJsonBuilder.create(category, output, outputCount).input('#', input).pattern("## ").pattern("## ").criterion(RecipeProvider.hasItem(input), conditionsFromItem(input)).offerTo(exporter, recipeId);
    }

    private void createTwoByTwo(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input, String group, String recipeId) {
        ShapedRecipeJsonBuilder.create(category, output, outputCount).input('#', input).pattern("## ").pattern("## ").group(group).criterion(RecipeProvider.hasItem(input), conditionsFromItem(input)).offerTo(exporter, recipeId);
    }

    private void offerShapeless(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input, @Nullable String group) {
        offerShapeless(exporter, category, output, outputCount, input, group, convertBetween(output, input));
    }

    private void offerShapeless(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input, @Nullable String group, @Nullable String recipeId) {
        ShapelessRecipeJsonBuilder recipe = shapeless(output, category, outputCount).input(input).criterion(RecipeProvider.hasItem(input), conditionsFromItem(input));
        if (group != null) {
            recipe.group(group);
        }
        if (recipeId != null) {
            recipe.offerTo(exporter, new Identifier(this.output.getModId(), recipeId));
        } else {
            recipe.offerTo(exporter);
        }
    }

    private ShapelessRecipeJsonBuilder shapeless(ItemConvertible output, RecipeCategory category, int outputCount) {
        return ShapelessRecipeJsonBuilder.create(category, output, outputCount);
    }

    private ShapedRecipeJsonBuilder shaped(ItemConvertible output, RecipeCategory category, int outputCount) {
        return ShapedRecipeJsonBuilder.create(category, output, outputCount);
    }
}
