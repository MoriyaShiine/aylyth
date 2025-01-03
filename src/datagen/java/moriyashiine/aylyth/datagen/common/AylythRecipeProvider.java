package moriyashiine.aylyth.datagen.common;

import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.datagen.common.util.recipe.ShuckingRecipeBuilder;
import moriyashiine.aylyth.datagen.common.util.recipe.SoulCampfireRecipeBuilder;
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
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class AylythRecipeProvider extends FabricRecipeProvider {
    public AylythRecipeProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSingleOutputShapelessRecipe(exporter, Items.ORANGE_DYE, AylythItems.MARIGOLD, "");
        createTwoByTwo(exporter, RecipeCategory.DECORATIONS, Items.SHROOMLIGHT, 1, AylythItems.JACK_O_LANTERN_MUSHROOM, "shroomlight_from_jack_o_lantern_mushroom");

        generateFamily(exporter, AylythBlockFamilies.YMPE);
        offerBarkBlockRecipe(exporter, AylythItems.YMPE_STRIPPED_WOOD, AylythItems.YMPE_STRIPPED_LOG);
        offerBarkBlockRecipe(exporter, AylythItems.YMPE_WOOD, AylythItems.YMPE_LOG);
        offerPlanksRecipe(exporter, AylythItems.YMPE_PLANKS, AylythItemTags.YMPE_LOGS, 4);
        offerHangingSignRecipe(exporter, AylythItems.YMPE_HANGING_SIGN, AylythItems.YMPE_STRIPPED_LOG);
        offerBoatRecipe(exporter, AylythItems.YMPE_BOAT, AylythItems.YMPE_PLANKS);
        offerChestBoatRecipe(exporter, AylythItems.YMPE_CHEST_BOAT, AylythItems.YMPE_BOAT);

        generateFamily(exporter, AylythBlockFamilies.POMEGRANATE);
        offerBarkBlockRecipe(exporter, AylythItems.POMEGRANATE_STRIPPED_WOOD, AylythItems.POMEGRANATE_STRIPPED_LOG);
        offerBarkBlockRecipe(exporter, AylythItems.POMEGRANATE_WOOD, AylythItems.POMEGRANATE_LOG);
        offerPlanksRecipe(exporter, AylythItems.POMEGRANATE_PLANKS, AylythItemTags.POMEGRANATE_LOGS, 4);
        offerHangingSignRecipe(exporter, AylythItems.POMEGRANATE_HANGING_SIGN, AylythItems.POMEGRANATE_STRIPPED_LOG);
        offerBoatRecipe(exporter, AylythItems.POMEGRANATE_BOAT, AylythItems.POMEGRANATE_PLANKS);
        offerChestBoatRecipe(exporter, AylythItems.POMEGRANATE_CHEST_BOAT, AylythItems.POMEGRANATE_BOAT);

        generateFamily(exporter, AylythBlockFamilies.WRITHEWOOD);
        offerBarkBlockRecipe(exporter, AylythItems.WRITHEWOOD_STRIPPED_WOOD, AylythItems.WRITHEWOOD_STRIPPED_LOG);
        offerBarkBlockRecipe(exporter, AylythItems.WRITHEWOOD_WOOD, AylythItems.WRITHEWOOD_LOG);
        offerPlanksRecipe(exporter, AylythItems.WRITHEWOOD_PLANKS, AylythItemTags.WRITHEWOOD_LOGS, 4);
        offerHangingSignRecipe(exporter, AylythItems.WRITHEWOOD_HANGING_SIGN, AylythItems.WRITHEWOOD_STRIPPED_LOG);
        offerBoatRecipe(exporter, AylythItems.WRITHEWOOD_BOAT, AylythItems.WRITHEWOOD_PLANKS);
        offerChestBoatRecipe(exporter, AylythItems.WRITHEWOOD_CHEST_BOAT, AylythItems.WRITHEWOOD_BOAT);
        
        offerShapeless(exporter, RecipeCategory.MISC, AylythItems.GHOSTCAP_MUSHROOM_SPORES, 1, AylythItems.GHOSTCAP_MUSHROOM, null);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, AylythBlocks.DARK_WOODS_TILES, 8)
                .input('Y', AylythBlocks.YMPE_PLANKS)
                .input('W', AylythBlocks.WRITHEWOOD_PLANKS)
                .pattern("YW")
                .pattern("WY")
                .criterion("has_writhewood", conditionsFromItem(AylythBlocks.WRITHEWOOD_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, AylythBlocks.DARK_WOODS_TILES, 8)
                .input('Y', AylythBlocks.YMPE_PLANKS)
                .input('W', AylythBlocks.WRITHEWOOD_PLANKS)
                .pattern("WY")
                .pattern("YW")
                .criterion("has_writhe", conditionsFromItem(AylythBlocks.WRITHEWOOD_PLANKS))
                .offerTo(exporter, "aylyth:dark_woods_tiles_alt");

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, AylythItems.YMPE_GLAIVE)
                .input('D', AylythItems.YMPE_DAGGER)
                .input('S', AylythItems.YMPE_SAPLING)
                .input('C', AylythItems.CORIC_SEED)
                .pattern(" CD")
                .pattern(" SS")
                .pattern("S  ")
                .criterion("has_ympe_dagger", conditionsFromItem(AylythItems.YMPE_DAGGER))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, AylythItems.YMPE_EFFIGY)
                .input('D', Items.SOUL_SOIL)
                .input('S', AylythItems.YMPE_SAPLING)
                .input('E', AylythItems.ESSTLINE)
                .input('H', AylythItemTags.BOSS_HEARTS)
                .pattern("DED")
                .pattern("EHE")
                .pattern("SDS")
                .criterion("has_boss_heart", conditionsFromTag(AylythItemTags.BOSS_HEARTS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, AylythItems.VITAL_THURIBLE)
                .input('S', Items.POLISHED_DEEPSLATE_SLAB)
                .input('H', AylythItemTags.BOSS_HEARTS)
                .input('P', Items.POLISHED_DEEPSLATE_WALL)
                .input('E', AylythItems.ESSTLINE)
                .input('C', Items.SOUL_CAMPFIRE)
                .pattern("SHS")
                .pattern("PEP")
                .pattern("PCP")
                .criterion("has_boss_heart", conditionsFromTag(AylythItemTags.BOSS_HEARTS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, AylythItems.SOUL_HEARTH)
                .input('S', Items.POLISHED_DEEPSLATE_SLAB)
                .input('W', Items.POLISHED_DEEPSLATE_WALL)
                .input('C', Items.SOUL_CAMPFIRE)
                .input('H', AylythItems.AYLYTHIAN_HEART)
                .pattern(" S ")
                .pattern("WCW")
                .pattern("WHW")
                .criterion("has_aylythian_heart", conditionsFromItem(AylythItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, AylythItems.GIRASOL_SEED)
                .input('Y', AylythItems.YMPE_SAPLING)
                .input('H', AylythItems.AYLYTHIAN_HEART)
                .input('S', Ingredient.ofItems(Items.SOUL_SAND, Items.SOUL_SOIL))
                .input('E', Items.ENDER_PEARL)
                .pattern("YHY")
                .pattern("SES")
                .pattern("YSY")
                .criterion("has_aylythian_heart", conditionsFromItem(AylythItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, AylythItems.YMPE_CUIRASS)
                .input('W', AylythItems.WRONGMEAT)
                .input('H', AylythItems.AYLYTHIAN_HEART)
                .input('C', AylythItems.CORIC_SEED)
                .input('G', AylythItems.LARGE_WOODY_GROWTH)
                .pattern("GHG")
                .pattern("WCW")
                .pattern("G G")
                .criterion("has_aylythian_heart", conditionsFromItem(AylythItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, AylythItems.NEPHRITE_SWORD)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("N")
                .pattern("N")
                .pattern("S")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, AylythItems.NEPHRITE_SHOVEL)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("N")
                .pattern("S")
                .pattern("S")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, AylythItems.NEPHRITE_PICKAXE)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("NNN")
                .pattern(" S ")
                .pattern(" S ")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, AylythItems.NEPHRITE_AXE)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("NN")
                .pattern("NS")
                .pattern(" S")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, AylythItems.NEPHRITE_HOE)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("NN")
                .pattern(" S")
                .pattern(" S")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BREWING, AylythItems.NEPHRITE_FLASK)
                .input('N', AylythItems.NEPHRITE)
                .input('E', Items.STICK)
                .pattern("NEN")
                .pattern(" N ")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, Items.TORCH)
                .input('S', Items.STICK)
                .input('C', AylythItems.BARK)
                .pattern("C")
                .pattern("S")
                .criterion("has_bark", conditionsFromItem(AylythItems.BARK))
                .offerTo(exporter, "torch_from_bark");

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, AylythItems.YMPE_LANCE)
                .input('E', AylythItems.ESSTLINE)
                .input('S', AylythItems.YMPE_SAPLING)
                .input('C', AylythItems.CORIC_SEED)
                .criterion("has_coric_seed", conditionsFromItem(AylythItems.CORIC_SEED));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, AylythItems.NEPHRITE_FLASK)
                .input(DefaultCustomIngredients.nbt(new ItemStack(AylythItems.DARK_NEPHRITE_FLASK), true))
                .input(AylythItems.AYLYTHIAN_HEART)
                .group("flask_conversion")
                .criterion("has_nephrite_flask", conditionsFromTag(AylythItemTags.NEPHRITE_FLASKS))
                .offerTo(exporter, "nephrite_flask_from_dark_nephrite_flask");

        ItemStack flask = new ItemStack(AylythItems.NEPHRITE_FLASK);
        flask.getOrCreateSubNbt("tag").putInt("uses", 0);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BREWING, AylythItems.DARK_NEPHRITE_FLASK)
                .input(DefaultCustomIngredients.nbt(new ItemStack(AylythItems.NEPHRITE_FLASK), true))
                .input(AylythItems.BLIGHTED_THORNS)
                .group("flask_conversion")
                .criterion("has_nephrite_flask", conditionsFromTag(AylythItemTags.NEPHRITE_FLASKS))
                .offerTo(exporter, "dark_nephrite_flask_from_nephrite_flask");

        ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, Items.PAPER, 2)
                .input(AylythItems.BARK, 2)
                .criterion("has_bark", conditionsFromItem(AylythItems.BARK))
                .offerTo(exporter, "paper_from_bark");

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE)
                .input(AylythItems.YMPE_FRUIT)
                .input(AylythItems.ESSTLINE)
                .input(Items.COBBLED_DEEPSLATE)
                .input(ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .criterion("has_esstline", conditionsFromItem(AylythItems.ESSTLINE))
                .offerTo(exporter);

        createSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.YMPE_SAPLING, AylythItems.ESSTLINE, AylythItems.YMPE_DAGGER, RecipeCategory.COMBAT)
                .criterion("has_ympe_sapling", conditionsFromItem(AylythItems.YMPE_SAPLING))
                .offerTo(exporter, RecipeProvider.getItemPath(AylythItems.YMPE_DAGGER) + "_smithing");
        ShuckingRecipeBuilder.create(RecipeCategory.TOOLS, AylythItems.SHUCKED_YMPE_FRUIT)
                .input(AylythItems.YMPE_FRUIT)
                .input(AylythItems.YMPE_DAGGER)
                .criterion("has_ympe_fruit", conditionsFromItem(AylythItems.YMPE_FRUIT))
                .offerTo(exporter);
        offerShapeless(exporter, RecipeCategory.COMBAT, AylythItems.BLIGHTED_THORN_FLECHETTE, 4, AylythItems.BLIGHTED_THORNS);

        offerReversibleCompactingRecipesWithReverseRecipeGroup(exporter, RecipeCategory.MISC, AylythItems.ESSTLINE, RecipeCategory.BUILDING_BLOCKS, AylythItems.ESSTLINE_BLOCK, "esstline_from_esstline_block", "esstline");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(exporter, RecipeCategory.MISC, AylythItems.NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythItems.NEPHRITE_BLOCK, "nephrite_from_nephrite_block", "nephrite");
        createTwoByTwo(exporter, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_SMOOTH_NEPHRITE, 8, AylythItems.NEPHRITE, "carved_smooth_nephrite");
        createStonecutting(exporter, AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_SMOOTH_NEPHRITE);
        createStonecutting(exporter, AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_ANTLERED_NEPHRITE);
        createStonecutting(exporter, AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_NEPHRITE_PILLAR);
        createStonecutting(exporter, AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_NEPHRITE_TILES);
        createStonecutting(exporter, AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_WOODY_NEPHRITE);

        createSmithingUpgrade(exporter, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_SWORD, AylythItems.BLIGHTED_THORNS, AylythItems.BLIGHTED_SWORD, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_PICKAXE, AylythItems.BLIGHTED_THORNS, AylythItems.BLIGHTED_PICKAXE, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_AXE, AylythItems.BLIGHTED_THORNS, AylythItems.BLIGHTED_AXE, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_HOE, AylythItems.BLIGHTED_THORNS, AylythItems.BLIGHTED_HOE, RecipeCategory.COMBAT);

        createSmithingUpgrade(exporter, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_SWORD, AylythItems.AYLYTHIAN_HEART, AylythItems.VAMPIRIC_SWORD, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_PICKAXE, AylythItems.AYLYTHIAN_HEART, AylythItems.VAMPIRIC_PICKAXE, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_AXE, AylythItems.AYLYTHIAN_HEART, AylythItems.VAMPIRIC_AXE, RecipeCategory.COMBAT);
        createSmithingUpgrade(exporter, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_HOE, AylythItems.AYLYTHIAN_HEART, AylythItems.VAMPIRIC_HOE, RecipeCategory.COMBAT);

        NbtCompound shucked = new NbtCompound();
        shucked.put("StoredEntity", new NbtCompound());
        SoulCampfireRecipeBuilder.create(AylythItems.CORIC_SEED)
                .with(AylythItems.AYLYTHIAN_HEART)
                .with(AylythItems.WRONGMEAT)
                .with(AylythItems.ESSTLINE)
                .with(DefaultCustomIngredients.nbt(Ingredient.ofItems(AylythItems.SHUCKED_YMPE_FRUIT), shucked, false))
                .offerTo(exporter);
    }

    private void createSmithingUpgrade(Consumer<RecipeJsonProvider> exporter, ItemConvertible template, ItemConvertible base, ItemConvertible addition, ItemConvertible result, RecipeCategory category) {
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(template), Ingredient.ofItems(base), Ingredient.ofItems(addition), category, result.asItem())
                .criterion("has_" + RecipeProvider.getItemPath(addition), RecipeProvider.conditionsFromItem(addition)).offerTo(exporter, RecipeProvider.getItemPath(result) + "_smithing");
    }

    private SmithingTransformRecipeJsonBuilder createSmithingUpgrade(ItemConvertible template, ItemConvertible base, ItemConvertible addition, ItemConvertible result, RecipeCategory category) {
        return SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(template), Ingredient.ofItems(base), Ingredient.ofItems(addition), category, result.asItem());
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

    private void offerShapeless(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input) {
        offerShapeless(exporter, category, output, outputCount, input, null);
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
