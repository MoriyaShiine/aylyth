package moriyashiine.aylyth.datagen.common;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.datagen.common.util.recipe.ShuckingRecipeBuilder;
import moriyashiine.aylyth.datagen.common.util.recipe.SoulCampfireRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.data.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class AylythRecipeGenerator extends RecipeGenerator {
    AylythRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        super(registries, exporter);
    }
    
    @Override
    public void generate() {
        offerSingleOutputShapelessRecipe(Items.ORANGE_DYE, AylythItems.MARIGOLD, "");
        createTwoByTwo(RecipeCategory.DECORATIONS, Items.SHROOMLIGHT, 1, AylythItems.JACK_O_LANTERN_MUSHROOM, "shroomlight_from_jack_o_lantern_mushroom");

        generateFamily(AylythBlockFamilies.YMPE, FeatureSet.of(FeatureFlags.VANILLA));
        offerBarkBlockRecipe(AylythItems.YMPE_STRIPPED_WOOD, AylythItems.YMPE_STRIPPED_LOG);
        offerBarkBlockRecipe(AylythItems.YMPE_WOOD, AylythItems.YMPE_LOG);
        offerPlanksRecipe(AylythItems.YMPE_PLANKS, AylythItemTags.YMPE_LOGS, 4);
        offerHangingSignRecipe(AylythItems.YMPE_HANGING_SIGN, AylythItems.YMPE_STRIPPED_LOG);
        offerBoatRecipe(AylythItems.YMPE_BOAT, AylythItems.YMPE_PLANKS);
        offerChestBoatRecipe(AylythItems.YMPE_CHEST_BOAT, AylythItems.YMPE_BOAT);

        generateFamily(AylythBlockFamilies.POMEGRANATE, FeatureSet.of(FeatureFlags.VANILLA));
        offerBarkBlockRecipe(AylythItems.POMEGRANATE_STRIPPED_WOOD, AylythItems.POMEGRANATE_STRIPPED_LOG);
        offerBarkBlockRecipe(AylythItems.POMEGRANATE_WOOD, AylythItems.POMEGRANATE_LOG);
        offerPlanksRecipe(AylythItems.POMEGRANATE_PLANKS, AylythItemTags.POMEGRANATE_LOGS, 4);
        offerHangingSignRecipe(AylythItems.POMEGRANATE_HANGING_SIGN, AylythItems.POMEGRANATE_STRIPPED_LOG);
        offerBoatRecipe(AylythItems.POMEGRANATE_BOAT, AylythItems.POMEGRANATE_PLANKS);
        offerChestBoatRecipe(AylythItems.POMEGRANATE_CHEST_BOAT, AylythItems.POMEGRANATE_BOAT);

        generateFamily(AylythBlockFamilies.WRITHEWOOD, FeatureSet.of(FeatureFlags.VANILLA));
        offerBarkBlockRecipe(AylythItems.WRITHEWOOD_STRIPPED_WOOD, AylythItems.WRITHEWOOD_STRIPPED_LOG);
        offerBarkBlockRecipe(AylythItems.WRITHEWOOD_WOOD, AylythItems.WRITHEWOOD_LOG);
        offerPlanksRecipe(AylythItems.WRITHEWOOD_PLANKS, AylythItemTags.WRITHEWOOD_LOGS, 4);
        offerHangingSignRecipe(AylythItems.WRITHEWOOD_HANGING_SIGN, AylythItems.WRITHEWOOD_STRIPPED_LOG);
        offerBoatRecipe(AylythItems.WRITHEWOOD_BOAT, AylythItems.WRITHEWOOD_PLANKS);
        offerChestBoatRecipe(AylythItems.WRITHEWOOD_CHEST_BOAT, AylythItems.WRITHEWOOD_BOAT);
        
        offerShapeless(RecipeCategory.MISC, AylythItems.GHOSTCAP_MUSHROOM_SPORES, 1, AylythItems.GHOSTCAP_MUSHROOM, null);

        createShaped(RecipeCategory.BUILDING_BLOCKS, AylythBlocks.DARK_WOODS_TILES, 8)
                .input('Y', AylythBlocks.YMPE_PLANKS)
                .input('W', AylythBlocks.WRITHEWOOD_PLANKS)
                .pattern("YW")
                .pattern("WY")
                .criterion("has_writhewood", conditionsFromItem(AylythBlocks.WRITHEWOOD_PLANKS))
                .offerTo(exporter);

        createShaped(RecipeCategory.BUILDING_BLOCKS, AylythBlocks.DARK_WOODS_TILES, 8)
                .input('Y', AylythBlocks.YMPE_PLANKS)
                .input('W', AylythBlocks.WRITHEWOOD_PLANKS)
                .pattern("WY")
                .pattern("YW")
                .criterion("has_writhe", conditionsFromItem(AylythBlocks.WRITHEWOOD_PLANKS))
                .offerTo(exporter, key("dark_woods_tiles_alt"));

        createShaped(RecipeCategory.COMBAT, AylythItems.YMPE_GLAIVE)
                .input('D', AylythItems.YMPE_DAGGER)
                .input('S', AylythItems.YMPE_SAPLING)
                .input('C', AylythItems.CORIC_SEED)
                .pattern(" CD")
                .pattern(" SS")
                .pattern("S  ")
                .criterion("has_ympe_dagger", conditionsFromItem(AylythItems.YMPE_DAGGER))
                .offerTo(exporter);

        createShaped(RecipeCategory.FOOD, AylythItems.YMPE_EFFIGY)
                .input('D', Items.SOUL_SOIL)
                .input('S', AylythItems.YMPE_SAPLING)
                .input('E', AylythItems.ESSTLINE)
                .input('H', AylythItemTags.BOSS_HEARTS)
                .pattern("DED")
                .pattern("EHE")
                .pattern("SDS")
                .criterion("has_boss_heart", conditionsFromTag(AylythItemTags.BOSS_HEARTS))
                .offerTo(exporter);

        createShaped(RecipeCategory.MISC, AylythItems.VITAL_THURIBLE)
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

        createShaped(RecipeCategory.MISC, AylythItems.SOUL_HEARTH)
                .input('S', Items.POLISHED_DEEPSLATE_SLAB)
                .input('W', Items.POLISHED_DEEPSLATE_WALL)
                .input('C', Items.SOUL_CAMPFIRE)
                .input('H', AylythItems.AYLYTHIAN_HEART)
                .pattern(" S ")
                .pattern("WCW")
                .pattern("WHW")
                .criterion("has_aylythian_heart", conditionsFromItem(AylythItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        createShaped(RecipeCategory.MISC, AylythItems.GIRASOL_SEED)
                .input('Y', AylythItems.YMPE_SAPLING)
                .input('H', AylythItems.AYLYTHIAN_HEART)
                .input('S', Ingredient.ofItems(Items.SOUL_SAND, Items.SOUL_SOIL))
                .input('E', Items.ENDER_PEARL)
                .pattern("YHY")
                .pattern("SES")
                .pattern("YSY")
                .criterion("has_aylythian_heart", conditionsFromItem(AylythItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        createShaped(RecipeCategory.MISC, AylythItems.YMPE_CUIRASS)
                .input('W', AylythItems.WRONGMEAT)
                .input('H', AylythItems.AYLYTHIAN_HEART)
                .input('C', AylythItems.CORIC_SEED)
                .input('G', AylythItems.LARGE_WOODY_GROWTH)
                .pattern("GHG")
                .pattern("WCW")
                .pattern("G G")
                .criterion("has_aylythian_heart", conditionsFromItem(AylythItems.AYLYTHIAN_HEART))
                .offerTo(exporter);

        createShaped(RecipeCategory.COMBAT, AylythItems.NEPHRITE_SWORD)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("N")
                .pattern("N")
                .pattern("S")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        createShaped(RecipeCategory.COMBAT, AylythItems.NEPHRITE_SHOVEL)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("N")
                .pattern("S")
                .pattern("S")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        createShaped(RecipeCategory.COMBAT, AylythItems.NEPHRITE_PICKAXE)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("NNN")
                .pattern(" S ")
                .pattern(" S ")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        createShaped(RecipeCategory.COMBAT, AylythItems.NEPHRITE_AXE)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("NN")
                .pattern("NS")
                .pattern(" S")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        createShaped(RecipeCategory.COMBAT, AylythItems.NEPHRITE_HOE)
                .input('N', AylythItemTags.NEPHRITE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .pattern("NN")
                .pattern(" S")
                .pattern(" S")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        createShaped(RecipeCategory.BREWING, AylythItems.NEPHRITE_FLASK)
                .input('N', AylythItems.NEPHRITE)
                .input('E', Items.STICK)
                .pattern("NEN")
                .pattern(" N ")
                .criterion("has_nephrite", conditionsFromItem(AylythItems.NEPHRITE))
                .offerTo(exporter);

        createShaped(RecipeCategory.DECORATIONS, Items.TORCH)
                .input('S', Items.STICK)
                .input('C', AylythItems.BARK)
                .pattern("C")
                .pattern("S")
                .criterion("has_bark", conditionsFromItem(AylythItems.BARK))
                .offerTo(exporter, key("torch_from_bark"));

        createShaped(RecipeCategory.COMBAT, AylythItems.YMPE_LANCE)
                .input('E', AylythItems.ESSTLINE)
                .input('S', AylythItems.YMPE_SAPLING)
                .input('C', AylythItems.CORIC_SEED)
                .pattern(" EC")
                .pattern(" SE")
                .pattern("S  ")
                .criterion("has_coric_seed", conditionsFromItem(AylythItems.CORIC_SEED))
                .offerTo(exporter);

        createShapeless(RecipeCategory.BREWING, AylythItems.NEPHRITE_FLASK)
                .input(AylythItems.DARK_NEPHRITE_FLASK)
                .input(AylythItems.AYLYTHIAN_HEART)
                .group("flask_conversion")
                .criterion("has_nephrite_flask", conditionsFromTag(AylythItemTags.NEPHRITE_FLASKS))
                .offerTo(exporter, key("nephrite_flask_from_dark_nephrite_flask"));

        createShapeless(RecipeCategory.BREWING, AylythItems.DARK_NEPHRITE_FLASK)
                .input(AylythItems.NEPHRITE_FLASK)
                .input(AylythItems.BLIGHTED_THORNS)
                .group("flask_conversion")
                .criterion("has_nephrite_flask", conditionsFromTag(AylythItemTags.NEPHRITE_FLASKS))
                .offerTo(exporter, "dark_nephrite_flask_from_nephrite_flask");

        createShapeless(RecipeCategory.DECORATIONS, Items.PAPER, 2)
                .input(AylythItems.BARK, 2)
                .criterion("has_bark", conditionsFromItem(AylythItems.BARK))
                .offerTo(exporter, key("paper_from_bark"));

        createShapeless(RecipeCategory.MISC, AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE)
                .input(AylythItems.YMPE_FRUIT)
                .input(AylythItems.ESSTLINE)
                .input(Items.COBBLED_DEEPSLATE)
                .input(ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .criterion("has_esstline", conditionsFromItem(AylythItems.ESSTLINE))
                .offerTo(exporter);

        createSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.YMPE_SAPLING, AylythItems.ESSTLINE, AylythItems.YMPE_DAGGER, RecipeCategory.COMBAT)
                .criterion("has_ympe_sapling", conditionsFromItem(AylythItems.YMPE_SAPLING))
                .offerTo(exporter, key(RecipeGenerator.getItemPath(AylythItems.YMPE_DAGGER) + "_smithing"));

        ShuckingRecipeBuilder.create(this.registries.getOrThrow(RegistryKeys.ITEM), RecipeCategory.TOOLS, AylythItems.SHUCKED_YMPE_FRUIT)
                .input(AylythItems.YMPE_FRUIT)
                .input(AylythItems.YMPE_DAGGER)
                .criterion("has_ympe_fruit", conditionsFromItem(AylythItems.YMPE_FRUIT))
                .offerTo(exporter);
        offerShapeless(RecipeCategory.COMBAT, AylythItems.BLIGHTED_THORN_FLECHETTE, 4, AylythItems.BLIGHTED_THORNS);

        offerReversibleCompactingRecipesWithReverseRecipeGroup(RecipeCategory.MISC, AylythItems.ESSTLINE, RecipeCategory.BUILDING_BLOCKS, AylythItems.ESSTLINE_BLOCK, "esstline_from_esstline_block", "esstline");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(RecipeCategory.MISC, AylythItems.NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythItems.NEPHRITE_BLOCK, "nephrite_from_nephrite_block", "nephrite");
        createTwoByTwo(RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_SMOOTH_NEPHRITE, 8, AylythItems.NEPHRITE, "carved_smooth_nephrite");
        offerStonecutting(AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_SMOOTH_NEPHRITE);
        offerStonecutting(AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_ANTLERED_NEPHRITE);
        offerStonecutting(AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_NEPHRITE_PILLAR);
        offerStonecutting(AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_NEPHRITE_TILES);
        offerStonecutting(AylythItemTags.CARVED_NEPHRITE, AylythBlocks.CARVED_SMOOTH_NEPHRITE, RecipeCategory.BUILDING_BLOCKS, AylythBlocks.CARVED_WOODY_NEPHRITE);

        offerSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_SWORD, AylythItems.BLIGHTED_THORNS, AylythItems.BLIGHTED_SWORD, RecipeCategory.COMBAT);
        offerSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_PICKAXE, AylythItems.BLIGHTED_THORNS, AylythItems.BLIGHTED_PICKAXE, RecipeCategory.COMBAT);
        offerSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_AXE, AylythItems.BLIGHTED_THORNS, AylythItems.BLIGHTED_AXE, RecipeCategory.COMBAT);
        offerSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_HOE, AylythItems.BLIGHTED_THORNS, AylythItems.BLIGHTED_HOE, RecipeCategory.COMBAT);

        offerSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_SWORD, AylythItems.AYLYTHIAN_HEART, AylythItems.VAMPIRIC_SWORD, RecipeCategory.COMBAT);
        offerSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_PICKAXE, AylythItems.AYLYTHIAN_HEART, AylythItems.VAMPIRIC_PICKAXE, RecipeCategory.COMBAT);
        offerSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_AXE, AylythItems.AYLYTHIAN_HEART, AylythItems.VAMPIRIC_AXE, RecipeCategory.COMBAT);
        offerSmithingUpgrade(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, AylythItems.NEPHRITE_HOE, AylythItems.AYLYTHIAN_HEART, AylythItems.VAMPIRIC_HOE, RecipeCategory.COMBAT);

        SoulCampfireRecipeBuilder.create(AylythItems.CORIC_SEED)
                .with(AylythItems.AYLYTHIAN_HEART)
                .with(AylythItems.WRONGMEAT)
                .with(AylythItems.ESSTLINE)
                .with(DefaultCustomIngredients.components(
                        Ingredient.ofItems(AylythItems.SHUCKED_YMPE_FRUIT),
                        builder -> builder.add(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT)
                ))
                .offerTo(exporter);
    }

    private void offerSmithingUpgrade(ItemConvertible template, ItemConvertible base, ItemConvertible addition, ItemConvertible result, RecipeCategory category) {
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(template), Ingredient.ofItems(base), Ingredient.ofItems(addition), category, result.asItem())
                .criterion("has_" + RecipeGenerator.getItemPath(addition), conditionsFromItem(addition))
                .offerTo(exporter, key(RecipeGenerator.getItemPath(result) + "_smithing"));
    }

    private SmithingTransformRecipeJsonBuilder createSmithingUpgrade(ItemConvertible template, ItemConvertible base, ItemConvertible addition, ItemConvertible result, RecipeCategory category) {
        return SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(template), Ingredient.ofItems(base), Ingredient.ofItems(addition), category, result.asItem());
    }

    private void offerStonecutting(TagKey<Item> inputTag, ItemConvertible baseItem, RecipeCategory recipeCategory, ItemConvertible output) {
        StonecuttingRecipeJsonBuilder.createStonecutting(DefaultCustomIngredients.difference(ingredientFromTag(inputTag), Ingredient.ofItems(output)), recipeCategory, output)
                .criterion(RecipeGenerator.hasItem(baseItem), conditionsFromItem(baseItem))
                .offerTo(exporter, key(convertBetweenTag(output, inputTag) + "_stonecutting"));
    }

    private String convertBetweenTag(ItemConvertible to, TagKey<Item> from) {
        return RecipeGenerator.getItemPath(to) + "_from_" + from.id().getPath();
    }

    private void createTwoByTwo(RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input, String recipeId) {
        createShaped(category, output, outputCount)
                .input('#', input)
                .pattern("## ")
                .pattern("## ")
                .criterion(RecipeGenerator.hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, key(recipeId));
    }

    private void createTwoByTwo(RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input, String group, String recipeId) {
        createShaped(category, output, outputCount)
                .input('#', input)
                .pattern("## ")
                .pattern("## ")
                .group(group)
                .criterion(RecipeGenerator.hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, key(recipeId));
    }

    private void offerShapeless(RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input) {
        offerShapeless(category, output, outputCount, input, null);
    }

    private void offerShapeless(RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input, @Nullable String group) {
        offerShapeless(category, output, outputCount, input, group, convertBetween(output, input));
    }

    private void offerShapeless(RecipeCategory category, ItemConvertible output, int outputCount, ItemConvertible input, @Nullable String group, @Nullable String recipeId) {
        ShapelessRecipeJsonBuilder recipe = shapeless(output, category, outputCount).input(input).criterion(RecipeGenerator.hasItem(input), conditionsFromItem(input));
        if (group != null) {
            recipe.group(group);
        }
        if (recipeId != null) {
            recipe.offerTo(exporter, key(recipeId));
        } else {
            recipe.offerTo(exporter);
        }
    }

    public RegistryKey<Recipe<?>> key(String id) {
        return RegistryKey.of(RegistryKeys.RECIPE, Aylyth.id(id));
    }

    private ShapelessRecipeJsonBuilder shapeless(ItemConvertible output, RecipeCategory category, int outputCount) {
        return createShapeless(category, output, outputCount);
    }

    public static class Provider extends FabricRecipeProvider {
        public Provider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
            return new AylythRecipeGenerator(registryLookup, exporter);
        }

        @Override
        public String getName() {
            return "Aylyth Recipes";
        }
    }
}
