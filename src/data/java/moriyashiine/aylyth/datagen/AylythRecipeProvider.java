package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.tag.ModItemTags;
import moriyashiine.aylyth.datagen.util.recipe.SoulCampfireRecipeBuilder;
import moriyashiine.aylyth.datagen.util.recipe.YmpeDaggerRecipeJsonBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class AylythRecipeProvider extends FabricRecipeProvider {

    public AylythRecipeProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSingleOutputShapelessRecipe(exporter, Items.ORANGE_DYE, ModItems.MARIGOLD, "");
        createTwoByTwo(exporter, RecipeCategory.DECORATIONS, Items.SHROOMLIGHT, 1, ModItems.JACK_O_LANTERN_MUSHROOM, "shroomlight_from_jack_o_lantern_mushroom");

        offerChestBoatRecipe(exporter, ModItems.YMPE_CHEST_BOAT, ModItems.YMPE_BOAT);

        offerBarkBlockRecipe(exporter, ModItems.POMEGRANATE_STRIPPED_WOOD, ModItems.POMEGRANATE_STRIPPED_LOG);
        offerBarkBlockRecipe(exporter, ModItems.POMEGRANATE_WOOD, ModItems.POMEGRANATE_LOG);
        offerPlanksRecipe(exporter, ModItems.POMEGRANATE_PLANKS, ModItemTags.POMEGRANATE_LOGS, 4);
        createStairsRecipe(ModItems.POMEGRANATE_STAIRS, Ingredient.ofItems(ModItems.POMEGRANATE_PLANKS)).group("wooden_stairs").criterion(RecipeProvider.hasItem(ModItems.POMEGRANATE_PLANKS), conditionsFromItem(ModItems.POMEGRANATE_PLANKS)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.POMEGRANATE_SLAB, Ingredient.ofItems(ModItems.POMEGRANATE_PLANKS)).group("wooden_slab").criterion(RecipeProvider.hasItem(ModItems.POMEGRANATE_PLANKS), conditionsFromItem(ModItems.POMEGRANATE_PLANKS)).offerTo(exporter);
        createFenceRecipe(ModItems.POMEGRANATE_FENCE, Ingredient.ofItems(ModItems.POMEGRANATE_PLANKS)).group("wooden_fence").criterion(RecipeProvider.hasItem(ModItems.POMEGRANATE_PLANKS), conditionsFromItem(ModItems.POMEGRANATE_PLANKS)).offerTo(exporter);
        createFenceGateRecipe(ModItems.POMEGRANATE_FENCE_GATE, Ingredient.ofItems(ModItems.POMEGRANATE_PLANKS)).group("wooden_fence_gate").criterion(RecipeProvider.hasItem(ModItems.POMEGRANATE_PLANKS), conditionsFromItem(ModItems.POMEGRANATE_PLANKS)).offerTo(exporter);
        createPressurePlateRecipe(RecipeCategory.REDSTONE, ModItems.POMEGRANATE_PRESSURE_PLATE, Ingredient.ofItems(ModItems.POMEGRANATE_PLANKS)).group("wooden_pressure_plate").criterion(RecipeProvider.hasItem(ModItems.POMEGRANATE_PLANKS), conditionsFromItem(ModItems.POMEGRANATE_PLANKS)).offerTo(exporter);
        offerShapeless(exporter, RecipeCategory.REDSTONE, ModItems.POMEGRANATE_BUTTON, 1, ModItems.POMEGRANATE_PLANKS, "wooden_button");
        createTrapdoorRecipe(ModItems.POMEGRANATE_TRAPDOOR, Ingredient.ofItems(ModItems.POMEGRANATE_PLANKS)).group("wooden_trapdoor").criterion(RecipeProvider.hasItem(ModItems.POMEGRANATE_PLANKS), conditionsFromItem(ModItems.POMEGRANATE_PLANKS)).offerTo(exporter);
        createDoorRecipe(ModItems.POMEGRANATE_DOOR, Ingredient.ofItems(ModItems.POMEGRANATE_PLANKS)).group("wooden_door").criterion(RecipeProvider.hasItem(ModItems.POMEGRANATE_PLANKS), conditionsFromItem(ModItems.POMEGRANATE_PLANKS)).offerTo(exporter);
        createSignRecipe(ModItems.POMEGRANATE_SIGN, Ingredient.ofItems(ModItems.POMEGRANATE_PLANKS)).group("wooden_sign").criterion(RecipeProvider.hasItem(ModItems.POMEGRANATE_PLANKS), conditionsFromItem(ModItems.POMEGRANATE_PLANKS)).offerTo(exporter);
        offerBoatRecipe(exporter, ModItems.POMEGRANATE_BOAT, ModItems.POMEGRANATE_PLANKS);
        offerChestBoatRecipe(exporter, ModItems.POMEGRANATE_CHEST_BOAT, ModItems.POMEGRANATE_BOAT);

        offerBarkBlockRecipe(exporter, ModItems.WRITHEWOOD_STRIPPED_WOOD, ModItems.WRITHEWOOD_STRIPPED_LOG);
        offerBarkBlockRecipe(exporter, ModItems.WRITHEWOOD_WOOD, ModItems.WRITHEWOOD_LOG);
        offerPlanksRecipe(exporter, ModItems.WRITHEWOOD_PLANKS, ModItemTags.WRITHEWOOD_LOGS, 4);
        createStairsRecipe(ModItems.WRITHEWOOD_STAIRS, Ingredient.ofItems(ModItems.WRITHEWOOD_PLANKS)).group("wooden_stairs").criterion(RecipeProvider.hasItem(ModItems.WRITHEWOOD_PLANKS), conditionsFromItem(ModItems.WRITHEWOOD_PLANKS)).offerTo(exporter);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModItems.WRITHEWOOD_SLAB, Ingredient.ofItems(ModItems.WRITHEWOOD_PLANKS)).group("wooden_slab").criterion(RecipeProvider.hasItem(ModItems.WRITHEWOOD_PLANKS), conditionsFromItem(ModItems.WRITHEWOOD_PLANKS)).offerTo(exporter);
        createFenceRecipe(ModItems.WRITHEWOOD_FENCE, Ingredient.ofItems(ModItems.WRITHEWOOD_PLANKS)).group("wooden_fence").criterion(RecipeProvider.hasItem(ModItems.WRITHEWOOD_PLANKS), conditionsFromItem(ModItems.WRITHEWOOD_PLANKS)).offerTo(exporter);
        createFenceGateRecipe(ModItems.WRITHEWOOD_FENCE_GATE, Ingredient.ofItems(ModItems.WRITHEWOOD_PLANKS)).group("wooden_fence_gate").criterion(RecipeProvider.hasItem(ModItems.WRITHEWOOD_PLANKS), conditionsFromItem(ModItems.WRITHEWOOD_PLANKS)).offerTo(exporter);
        createPressurePlateRecipe(RecipeCategory.REDSTONE, ModItems.WRITHEWOOD_PRESSURE_PLATE, Ingredient.ofItems(ModItems.WRITHEWOOD_PLANKS)).group("wooden_pressure_plate").criterion(RecipeProvider.hasItem(ModItems.WRITHEWOOD_PLANKS), conditionsFromItem(ModItems.WRITHEWOOD_PLANKS)).offerTo(exporter);
        offerShapeless(exporter, RecipeCategory.REDSTONE, ModItems.WRITHEWOOD_BUTTON, 1, ModItems.WRITHEWOOD_PLANKS, "wooden_button");
        createTrapdoorRecipe(ModItems.WRITHEWOOD_TRAPDOOR, Ingredient.ofItems(ModItems.WRITHEWOOD_PLANKS)).group("wooden_trapdoor").criterion(RecipeProvider.hasItem(ModItems.WRITHEWOOD_PLANKS), conditionsFromItem(ModItems.WRITHEWOOD_PLANKS)).offerTo(exporter);
        createDoorRecipe(ModItems.WRITHEWOOD_DOOR, Ingredient.ofItems(ModItems.WRITHEWOOD_PLANKS)).group("wooden_door").criterion(RecipeProvider.hasItem(ModItems.WRITHEWOOD_PLANKS), conditionsFromItem(ModItems.WRITHEWOOD_PLANKS)).offerTo(exporter);
        createSignRecipe(ModItems.WRITHEWOOD_SIGN, Ingredient.ofItems(ModItems.WRITHEWOOD_PLANKS)).group("wooden_sign").criterion(RecipeProvider.hasItem(ModItems.WRITHEWOOD_PLANKS), conditionsFromItem(ModItems.WRITHEWOOD_PLANKS)).offerTo(exporter);
        offerBoatRecipe(exporter, ModItems.WRITHEWOOD_BOAT, ModItems.WRITHEWOOD_PLANKS);
        offerChestBoatRecipe(exporter, ModItems.WRITHEWOOD_CHEST_BOAT, ModItems.WRITHEWOOD_BOAT);
        
        offerShapeless(exporter, RecipeCategory.MISC, ModItems.GHOSTCAP_MUSHROOM_SPORES, 1, ModItems.GHOSTCAP_MUSHROOM, null);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_WOODS_TILES, 8)
                .input('Y', ModBlocks.YMPE_PLANKS)
                .input('W', ModBlocks.WRITHEWOOD_PLANKS)
                .pattern("YW")
                .pattern("WY")
                .criterion("has_writhe", conditionsFromItem(ModBlocks.WRITHEWOOD_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_WOODS_TILES, 8)
                .input('Y', ModBlocks.YMPE_PLANKS)
                .input('W', ModBlocks.WRITHEWOOD_PLANKS)
                .pattern("WY")
                .pattern("YW")
                .criterion("has_writhe", conditionsFromItem(ModBlocks.WRITHEWOOD_PLANKS))
                .offerTo(exporter, "aylyth:dark_woods_tiles_2");

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.YMPE_GLAIVE)
                .input('D', ModItems.YMPE_DAGGER)
                .input('S', ModItems.YMPE_SAPLING)
                .input('C', ModItems.CORIC_SEED)
                .pattern(" CD")
                .pattern(" SS")
                .pattern("S  ")
                .criterion("has_dagger", conditionsFromItem(ModItems.YMPE_DAGGER))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.YMPE_EFFIGY)
                .input('D', Items.SOUL_SOIL)
                .input('S', ModItems.YMPE_SAPLING)
                .input('E', ModItems.ESSTLINE)
                .input('C', ModItems.CORIC_SEED)
                .pattern("DED")
                .pattern("ECE")
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
                .pattern("PEP")
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
                .input('Y', ModItems.YMPE_SAPLING)
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.NEPHRITE_SWORD)
                .input('N', ModItems.NEPHRITE)
                .input('S', Items.STICK)
                .pattern("N")
                .pattern("N")
                .pattern("S")
                .criterion("has_nephrite", conditionsFromItem(ModItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.NEPHRITE_SHOVEL)
                .input('N', ModItems.NEPHRITE)
                .input('S', Items.STICK)
                .pattern("N")
                .pattern("S")
                .pattern("S")
                .criterion("has_nephrite", conditionsFromItem(ModItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.NEPHRITE_PICKAXE)
                .input('N', ModItems.NEPHRITE)
                .input('S', Items.STICK)
                .pattern("NNN")
                .pattern(" S ")
                .pattern(" S ")
                .criterion("has_nephrite", conditionsFromItem(ModItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.NEPHRITE_AXE)
                .input('N', ModItems.NEPHRITE)
                .input('S', Items.STICK)
                .pattern("NN")
                .pattern("NS")
                .pattern(" S")
                .criterion("has_nephrite", conditionsFromItem(ModItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.NEPHRITE_HOE)
                .input('N', ModItems.NEPHRITE)
                .input('S', Items.STICK)
                .pattern("NN")
                .pattern(" S")
                .pattern(" S")
                .criterion("has_nephrite", conditionsFromItem(ModItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BREWING, ModItems.NEPHRITE_FLASK)
                .input('N', ModItems.NEPHRITE)
                .input('E', Items.STICK)
                .pattern("NEN")
                .pattern(" N ")
                .criterion("has_nephrite", conditionsFromItem(ModItems.NEPHRITE))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, ModItems.NEPHRITE_FLASK)
                .input(DefaultCustomIngredients.nbt(new ItemStack(ModItems.DARK_NEPHRITE_FLASK), true))
                .input(ModItems.AYLYTHIAN_HEART)
                .group("flask_conversion")
                .criterion("has_nephrite_flask", conditionsFromTag(ModItemTags.NEPHRITE_FLASKS))
                .offerTo(exporter, "nephrite_flask_from_dark_nephrite_flask");

        ItemStack flask = new ItemStack(ModItems.NEPHRITE_FLASK);
        flask.getOrCreateSubNbt("tag").putInt("uses", 0);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, ModItems.DARK_NEPHRITE_FLASK)
                .input(DefaultCustomIngredients.nbt(new ItemStack(ModItems.NEPHRITE_FLASK), true))
                .input(ModItems.BLIGHTED_THORNS)
                .group("flask_conversion")
                .criterion("has_nephrite_flask", conditionsFromTag(ModItemTags.NEPHRITE_FLASKS))
                .offerTo(exporter, "dark_nephrite_flask_from_nephrite_flask");

        offerReversibleCompactingRecipesWithReverseRecipeGroup(exporter, RecipeCategory.MISC, ModItems.ESSTLINE, RecipeCategory.BUILDING_BLOCKS, ModItems.ESSTLINE_BLOCK, "esstline_from_esstline_block", "esstline");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(exporter, RecipeCategory.MISC, ModItems.NEPHRITE, RecipeCategory.BUILDING_BLOCKS, ModItems.NEPHRITE_BLOCK, "nephrite_from_nephrite_block", "nephrite");
        createTwoByTwo(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVED_SMOOTH_NEPHRITE, 8, ModItems.NEPHRITE, "carved_smooth_nephrite");
        createStonecutting(exporter, ModItemTags.CARVED_NEPHRITE, ModBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVED_SMOOTH_NEPHRITE);
        createStonecutting(exporter, ModItemTags.CARVED_NEPHRITE, ModBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVED_ANTLERED_NEPHRITE);
        createStonecutting(exporter, ModItemTags.CARVED_NEPHRITE, ModBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVED_NEPHRITE_PILLAR);
        createStonecutting(exporter, ModItemTags.CARVED_NEPHRITE, ModBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVED_NEPHRITE_TILES);
        createStonecutting(exporter, ModItemTags.CARVED_NEPHRITE, ModBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVED_WOODY_NEPHRITE);

        offerHangingSignRecipe(exporter, ModItems.YMPE_HANGING_SIGN, ModItems.YMPE_STRIPPED_LOG);
        offerHangingSignRecipe(exporter, ModItems.POMEGRANATE_HANGING_SIGN, ModItems.POMEGRANATE_STRIPPED_LOG);
        offerHangingSignRecipe(exporter, ModItems.WRITHEWOOD_HANGING_SIGN, ModItems.WRITHEWOOD_STRIPPED_LOG);

        createSmithingUpgrade(exporter, ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, ModItems.NEPHRITE_SWORD, ModItems.BLIGHTED_THORNS, ModItems.BLIGHTED_SWORD, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, ModItems.NEPHRITE_PICKAXE, ModItems.BLIGHTED_THORNS, ModItems.BLIGHTED_PICKAXE, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, ModItems.NEPHRITE_AXE, ModItems.BLIGHTED_THORNS, ModItems.BLIGHTED_AXE, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, ModItems.NEPHRITE_HOE, ModItems.BLIGHTED_THORNS, ModItems.BLIGHTED_HOE, RecipeCategory.COMBAT);

        createSmithingUpgrade(exporter, ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, ModItems.NEPHRITE_SWORD, ModItems.AYLYTHIAN_HEART, ModItems.VAMPIRIC_SWORD, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, ModItems.NEPHRITE_PICKAXE, ModItems.AYLYTHIAN_HEART, ModItems.VAMPIRIC_PICKAXE, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, ModItems.NEPHRITE_AXE, ModItems.AYLYTHIAN_HEART, ModItems.VAMPIRIC_AXE, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, ModItems.NEPHRITE_HOE, ModItems.AYLYTHIAN_HEART, ModItems.VAMPIRIC_HOE, RecipeCategory.COMBAT);
        
        YmpeDaggerRecipeJsonBuilder.create(ModEntityTypes.WREATHED_HIND_ENTITY, ModItems.WRONGMEAT, 0.2f, 3, 5)
                .offerTo(exporter);

        NbtCompound shucked = new NbtCompound();
        shucked.put("StoredEntity", new NbtCompound());
        SoulCampfireRecipeBuilder.createWithIngredients(
                List.of(
                        Ingredient.ofItems(ModItems.AYLYTHIAN_HEART),
                        Ingredient.ofItems(ModItems.WRONGMEAT),
                        Ingredient.ofItems(ModItems.ESSTLINE),
                        DefaultCustomIngredients.nbt(Ingredient.ofItems(ModItems.SHUCKED_YMPE_FRUIT), shucked, false)
                ),
                ModItems.CORIC_SEED
        ).offerTo(exporter);
    }

    private void createSmithingUpgrade(Consumer<RecipeJsonProvider> exporter, ItemConvertible template, ItemConvertible base, ItemConvertible addition, ItemConvertible result, RecipeCategory category) {
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(template), Ingredient.ofItems(base), Ingredient.ofItems(addition), category, result.asItem())
                .criterion("has_" + RecipeProvider.getItemPath(result), RecipeProvider.conditionsFromItem(addition)).offerTo(exporter, RecipeProvider.getItemPath(result) + "_smithing");
    }

    private void createStonecutting(Consumer<RecipeJsonProvider> exporter, TagKey<Item> inputTag, ItemConvertible baseItem, RecipeCategory recipeCategory, ItemConvertible output) {
        SingleItemRecipeJsonBuilder.createStonecutting(DefaultCustomIngredients.difference(Ingredient.fromTag(inputTag), Ingredient.ofItems(output)), recipeCategory, output)
        .criterion(RecipeProvider.hasItem(baseItem), RecipeProvider.conditionsFromItem(baseItem)).offerTo(exporter, convertBetweenTag(output, inputTag) + "_stonecutting");
    }

    private String convertBetweenTag(ItemConvertible to, TagKey<Item> from) {
        return RecipeProvider.getItemPath(to) + "_from_" + from.id().getPath();
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
}
