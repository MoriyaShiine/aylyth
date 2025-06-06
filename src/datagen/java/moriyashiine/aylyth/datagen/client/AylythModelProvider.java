package moriyashiine.aylyth.datagen.client;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.client.render.item.property.FlaskChargesProperty;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.GrowingHarvestablePillarBlock;
import moriyashiine.aylyth.common.block.types.LargeWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.types.NysianGrapeVineBlock;
import moriyashiine.aylyth.common.block.types.PomegranateLeavesBlock;
import moriyashiine.aylyth.common.block.types.SeepBlock;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.common.block.types.LeafPileBlock;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.datagen.common.AylythBlockFamilies;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MultifaceBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateSupplier;
import net.minecraft.client.data.BlockStateVariant;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.ModelSupplier;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.MultipartBlockStateSupplier;
import net.minecraft.client.data.SimpleModelSupplier;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.data.VariantSettings;
import net.minecraft.client.data.VariantsBlockStateSupplier;
import net.minecraft.client.data.When;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.RangeDispatchItemModel;
import net.minecraft.client.render.item.tint.ConstantTintSource;
import net.minecraft.client.render.item.tint.GrassTintSource;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.FoliageColors;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static net.minecraft.client.data.BlockStateModelGenerator.createModelVariantWithRandomHorizontalRotations;

public class AylythModelProvider extends FabricModelProvider {
    public AylythModelProvider(FabricDataOutput output) {
        super(output);
    }

    private static Identifier blockId(String id) {
        return id("block/" + id);
    }

    private static Identifier id(String id) {
        return Aylyth.id(id);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerParentedItemModel(AylythBlocks.SOUL_HEARTH, blockId("soul_hearth_item"));
        registerFlowerPot(generator, AylythBlocks.MARIGOLD, AylythBlocks.POTTED_MARIGOLD, BlockStateModelGenerator.CrossType.NOT_TINTED);
        generateStrewnLeaves(generator, AylythBlocks.OAK_STREWN_LEAVES, List.of(blockId("fallen_oak_leaves_01"), blockId("fallen_oak_leaves_02"), blockId("fallen_oak_leaves_03"), blockId("fallen_oak_leaves_04"), blockId("fallen_oak_leaves_05"), blockId("fallen_oak_leaves_06"), blockId("fallen_oak_leaves_07"), blockId("fallen_oak_leaves_08"), blockId("fallen_oak_leaves_09"), blockId("fallen_oak_leaves_10")));
        generateStrewnLeaves(generator, AylythBlocks.YMPE_STREWN_LEAVES, List.of(blockId("fallen_ympe_leaves_01"), blockId("fallen_ympe_leaves_02")));
        generateLeafPiles(generator, AylythBlocks.OAK_LEAF_PILE, Blocks.OAK_LEAVES, true);
        generateLeafPiles(generator, AylythBlocks.YMPE_LEAF_PILE, AylythBlocks.YMPE_LEAVES, false);

        generator.registerTintedItemModel(AylythBlocks.AYLYTH_BUSH, ModelIds.getBlockModelId(AylythBlocks.AYLYTH_BUSH), new ConstantTintSource(FoliageColors.DEFAULT));
        generator.registerTintedItemModel(AylythBlocks.ANTLER_SHOOTS, ModelIds.getBlockModelId(AylythBlocks.ANTLER_SHOOTS), new GrassTintSource());
        generator.registerTintedItemModel(AylythBlocks.GRIPWEED, ModelIds.getBlockModelId(AylythBlocks.GRIPWEED), new GrassTintSource());

        generator.registerLog(AylythBlocks.YMPE_STRIPPED_LOG).log(AylythBlocks.YMPE_STRIPPED_LOG).wood(AylythBlocks.YMPE_STRIPPED_WOOD);
        generator.registerLog(AylythBlocks.YMPE_LOG).log(AylythBlocks.YMPE_LOG).wood(AylythBlocks.YMPE_WOOD);
        generator.registerFlowerPotPlantAndItem(AylythBlocks.YMPE_SAPLING, AylythBlocks.POTTED_YMPE_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
        generator.registerCubeAllModelTexturePool(AylythBlocks.YMPE_PLANKS).family(AylythBlockFamilies.YMPE);
        generator.registerHangingSign(AylythBlocks.YMPE_STRIPPED_LOG, AylythBlocks.YMPE_HANGING_SIGN, AylythBlocks.YMPE_WALL_HANGING_SIGN);
        generator.registerSimpleCubeAll(AylythBlocks.YMPE_LEAVES);

        generator.registerLog(AylythBlocks.POMEGRANATE_STRIPPED_LOG).log(AylythBlocks.POMEGRANATE_STRIPPED_LOG).wood(AylythBlocks.POMEGRANATE_STRIPPED_WOOD);
        generator.registerLog(AylythBlocks.POMEGRANATE_LOG).log(AylythBlocks.POMEGRANATE_LOG).wood(AylythBlocks.POMEGRANATE_WOOD);
        generator.registerFlowerPotPlantAndItem(AylythBlocks.POMEGRANATE_SAPLING, AylythBlocks.POTTED_POMEGRANATE_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
        generator.registerCubeAllModelTexturePool(AylythBlocks.POMEGRANATE_PLANKS).family(AylythBlockFamilies.POMEGRANATE);
        generator.registerHangingSign(AylythBlocks.POMEGRANATE_STRIPPED_LOG, AylythBlocks.POMEGRANATE_HANGING_SIGN, AylythBlocks.POMEGRANATE_WALL_HANGING_SIGN);
        fruitingLeaves(generator, AylythBlocks.POMEGRANATE_LEAVES, blockId("pomegranate_leaves"), blockId("pomegranate_leaves_fruiting_0"), blockId("pomegranate_leaves_fruiting_1"), blockId("pomegranate_leaves_fruiting_2"));
        TexturedModel.CUBE_ALL.upload(AylythBlocks.POMEGRANATE_LEAVES, generator.modelCollector);

        generator.registerLog(AylythBlocks.WRITHEWOOD_STRIPPED_LOG).log(AylythBlocks.WRITHEWOOD_STRIPPED_LOG).wood(AylythBlocks.WRITHEWOOD_STRIPPED_WOOD);
        generator.registerLog(AylythBlocks.WRITHEWOOD_LOG).log(AylythBlocks.WRITHEWOOD_LOG).wood(AylythBlocks.WRITHEWOOD_WOOD);
        generator.registerFlowerPotPlantAndItem(AylythBlocks.WRITHEWOOD_SAPLING, AylythBlocks.POTTED_WRITHEWOOD_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
        generator.registerCubeAllModelTexturePool(AylythBlocks.WRITHEWOOD_PLANKS).family(AylythBlockFamilies.WRITHEWOOD);
        generator.registerHangingSign(AylythBlocks.WRITHEWOOD_STRIPPED_LOG, AylythBlocks.WRITHEWOOD_HANGING_SIGN, AylythBlocks.WRITHEWOOD_WALL_HANGING_SIGN);
        singleton(generator, AylythBlocks.WRITHEWOOD_LEAVES);

        Models.TEMPLATE_SINGLE_FACE.upload(blockId("jack_o_lantern_mushroom_block_inner"), TextureMap.texture(blockId("jack_o_lantern_mushroom_block_inner")), generator.modelCollector);
        registerMushroomBlock(generator, AylythBlocks.JACK_O_LANTERN_MUSHROOM_STEM, blockId("jack_o_lantern_mushroom_block_inner"));
        registerMushroomBlock(generator, AylythBlocks.JACK_O_LANTERN_MUSHROOM_BLOCK, blockId("jack_o_lantern_mushroom_block_inner"));

        generator.registerAxisRotated(AylythBlocks.CHTHONIA_WOOD, TexturedModel.makeFactory(block -> new TextureMap().put(TextureKey.SIDE, ModelIds.getBlockModelId(AylythBlocks.CHTHONIA_WOOD)).put(TextureKey.END, ModelIds.getBlockModelId(AylythBlocks.CHTHONIA_WOOD)), Models.CUBE_COLUMN));
        generator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(AylythBlocks.NEPHRITIC_CHTHONIA_WOOD, ModelIds.getBlockModelId(AylythBlocks.NEPHRITIC_CHTHONIA_WOOD)));

        generator.registerSingleton(AylythBlocks.DARK_WOODS_TILES, TexturedModel.CUBE_ALL);

        generator.registerSingleton(AylythBlocks.ESSTLINE_BLOCK, TexturedModel.CUBE_ALL);
        generator.registerSingleton(AylythBlocks.NEPHRITE_BLOCK, TexturedModel.CUBE_ALL);

        generator.registerMirrorable(AylythBlocks.CARVED_SMOOTH_NEPHRITE);
        generator.registerSingleton(AylythBlocks.CARVED_ANTLERED_NEPHRITE, TexturedModel.CUBE_ALL);
        generator.registerAxisRotated(AylythBlocks.CARVED_NEPHRITE_PILLAR, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        generator.registerSingleton(AylythBlocks.CARVED_NEPHRITE_TILES, TexturedModel.CUBE_ALL);
        generator.registerSingleton(AylythBlocks.CARVED_WOODY_NEPHRITE, TexturedModel.CUBE_ALL);

        Models.PARTICLE.upload(blockId("woody_growth_particles"), TextureMap.particle(blockId("aylyth_bush_trunk")), generator.modelCollector);
        smallWoodyGrowth(generator.blockStateCollector, AylythBlocks.SMALL_WOODY_GROWTH);
        largeWoodyGrowth(generator.blockStateCollector, AylythBlocks.LARGE_WOODY_GROWTH);
        largeWoodyGrowth(generator.blockStateCollector, AylythBlocks.WOODY_GROWTH_CACHE);
        Identifier seepingWoodTexture = blockId("aylyth_bush_trunk");
        Identifier seepingWoodModel = Models.CUBE_COLUMN.upload(AylythBlocks.SEEPING_WOOD, TextureMap.sideEnd(seepingWoodTexture, seepingWoodTexture), generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(AylythBlocks.SEEPING_WOOD, seepingWoodModel));

        generator.registerFlowerPotPlant(AylythBlocks.GIRASOL_SAPLING, AylythBlocks.POTTED_GIRASOL_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
        singleton(generator, AylythBlocks.BLACK_WELL);

        generator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.SAPSTONE, blockId("sapstone")));
        generator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.AMBER_SAPSTONE, blockId("amber_sapstone")));
        generator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.LIGNITE_SAPSTONE, blockId("lignite_sapstone")));
        generator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.OPALESCENT_SAPSTONE, blockId("opalescent_sapstone")));
        generator.blockStateCollector.accept(soulHearthStates(AylythBlocks.SOUL_HEARTH, blockId("soul_hearth_upper"), blockId("soul_hearth_lower"), blockId("soul_hearth_charged_lower")));

        registerBranchAndItem(generator, AylythBlocks.DARK_OAK_BRANCH);
        registerBranchAndItem(generator, AylythBlocks.BARE_DARK_OAK_BRANCH);
        registerBranchAndItem(generator, AylythBlocks.WRITHEWOOD_BRANCH);
        registerBranchAndItem(generator, AylythBlocks.BARE_WRITHEWOOD_BRANCH);
        registerBranchAndItem(generator, AylythBlocks.YMPE_BRANCH);
        registerBranchAndItem(generator, AylythBlocks.BARE_YMPE_BRANCH);
        registerBranchAndItem(generator, AylythBlocks.ORANGE_AYLYTHIAN_OAK_BRANCH);
        registerBranchAndItem(generator, AylythBlocks.RED_AYLYTHIAN_OAK_BRANCH);
        registerBranchAndItem(generator, AylythBlocks.BROWN_AYLYTHIAN_OAK_BRANCH);

        registerDarkPodzol(generator, AylythBlocks.DARK_PODZOL, Blocks.DIRT);

        generator.registerFlowerPotPlantAndItem(AylythBlocks.BROWN_AYLYTHIAN_OAK_SAPLING, AylythBlocks.POTTED_BROWN_AYLYTHIAN_OAK_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
        generator.registerFlowerPotPlantAndItem(AylythBlocks.GREEN_AYLYTHIAN_OAK_SAPLING, AylythBlocks.POTTED_GREEN_AYLYTHIAN_OAK_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
        generator.registerFlowerPotPlantAndItem(AylythBlocks.ORANGE_AYLYTHIAN_OAK_SAPLING, AylythBlocks.POTTED_ORANGE_AYLYTHIAN_OAK_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);
        generator.registerFlowerPotPlantAndItem(AylythBlocks.RED_AYLYTHIAN_OAK_SAPLING, AylythBlocks.POTTED_RED_AYLYTHIAN_OAK_SAPLING, BlockStateModelGenerator.CrossType.NOT_TINTED);

        registerCubeAllWithNumberedVariantsAndItem(generator, AylythBlocks.GREEN_AYLYTHIAN_OAK_LEAVES, 3);
        registerCubeAllWithNumberedVariantsAndItem(generator, AylythBlocks.ORANGE_AYLYTHIAN_OAK_LEAVES, 3);
        registerCubeAllWithNumberedVariantsAndItem(generator, AylythBlocks.RED_AYLYTHIAN_OAK_LEAVES, 3);
        registerCubeAllWithNumberedVariantsAndItem(generator, AylythBlocks.BROWN_AYLYTHIAN_OAK_LEAVES, 3);

        registerSeep(generator, AylythBlocks.OAK_SEEP, Blocks.OAK_LOG);
        registerSeep(generator, AylythBlocks.DARK_OAK_SEEP, Blocks.DARK_OAK_LOG);
        registerSeep(generator, AylythBlocks.SPRUCE_SEEP, Blocks.SPRUCE_LOG);
        registerSeep(generator, AylythBlocks.YMPE_SEEP, AylythBlocks.YMPE_LOG);
        registerSeep(generator, AylythBlocks.SEEPING_WOOD_SEEP, blockId("aylyth_bush_trunk"), blockId("aylyth_bush_trunk"));

        singleton(generator, AylythBlocks.ANTLER_SHOOTS);
        singleton(generator, AylythBlocks.GRIPWEED);

        registerFruitBearingYmpeBlock(generator, AylythBlocks.FRUIT_BEARING_YMPE_LOG, AylythBlocks.YMPE_LOG);

        registerNysianGrapeVine(generator, AylythBlocks.NYSIAN_GRAPE_VINE);

        registerMarigolds(generator, AylythBlocks.MARIGOLD);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(AylythItems.AYLYTHIAN_HEART, Models.GENERATED);
        generator.register(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
        generator.register(AylythItems.BARK, Models.GENERATED);
        generator.register(AylythItems.CORIC_SEED, Models.GENERATED);
        generator.register(AylythItems.ESSTLINE, Models.GENERATED);
        generator.register(AylythItems.GHOSTCAP_MUSHROOM, Models.GENERATED);
        generator.register(AylythItems.JACK_O_LANTERN_MUSHROOM, Models.GENERATED);
        generator.register(AylythItems.NEPHRITE, Models.GENERATED);
        generator.register(AylythItems.NYSIAN_GRAPES, Models.GENERATED);
        generator.register(AylythItems.YMPE_FRUIT, Models.GENERATED);
        generator.register(AylythItems.YMPE_MUSH, Models.GENERATED);
        generator.register(AylythItems.POMEGRANATE, Models.GENERATED);
        generator.register(AylythItems.GHOSTCAP_MUSHROOM_SPORES, Models.GENERATED);
        generator.register(AylythItems.YMPE_BOAT, Models.GENERATED);
        generator.register(AylythItems.YMPE_CHEST_BOAT, Models.GENERATED);
        generator.register(AylythItems.POMEGRANATE_BOAT, Models.GENERATED);
        generator.register(AylythItems.POMEGRANATE_CHEST_BOAT, Models.GENERATED);
        generator.register(AylythItems.WRITHEWOOD_BOAT, Models.GENERATED);
        generator.register(AylythItems.WRITHEWOOD_CHEST_BOAT, Models.GENERATED);
        generator.register(AylythItems.WRONGMEAT, Models.GENERATED);
        generator.register(AylythItems.NEPHRITE_HEART, Models.GENERATED);
        generator.register(AylythItems.YHONDYTH_HEART, Models.GENERATED);
        generator.register(AylythItems.GIRASOL_SEED, Models.GENERATED);
        generator.register(AylythItems.SMALL_WOODY_GROWTH, Models.GENERATED);
        generator.register(AylythItems.LARGE_WOODY_GROWTH, Models.GENERATED);
        generator.register(AylythItems.YMPE_CUIRASS, Models.GENERATED);
        generator.register(AylythItems.POMEGRANATE_CASSETTE, Models.GENERATED);
        generator.register(AylythItems.BLIGHTED_THORNS, Models.GENERATED);
        generator.register(AylythItems.THORN_FLECHETTE, Models.GENERATED);
        generator.register(AylythItems.BLIGHTED_THORN_FLECHETTE, Models.GENERATED);

        generator.output.accept(AylythItems.WOODY_GROWTH_CACHE, ItemModels.basic(ModelIds.getItemModelId(AylythItems.LARGE_WOODY_GROWTH)));

        generator.register(AylythItems.YMPE_DAGGER, Models.HANDHELD);
        generator.register(AylythItems.LANCEOLATE_DAGGER, Models.HANDHELD);
        generator.register(AylythItems.DEBUG_WAND, Models.HANDHELD);
        generator.register(AylythItems.NEPHRITE_AXE, Models.HANDHELD);
        generator.register(AylythItems.NEPHRITE_HOE, Models.HANDHELD);
        generator.register(AylythItems.NEPHRITE_PICKAXE, Models.HANDHELD);
        generator.register(AylythItems.NEPHRITE_SHOVEL, Models.HANDHELD);
        generator.register(AylythItems.NEPHRITE_SWORD, Models.HANDHELD);
        generator.register(AylythItems.VAMPIRIC_PICKAXE, Models.HANDHELD);
        generator.register(AylythItems.VAMPIRIC_AXE, Models.HANDHELD);
        generator.register(AylythItems.VAMPIRIC_HOE, Models.HANDHELD);
        generator.register(AylythItems.BLIGHTED_PICKAXE, Models.HANDHELD);
        generator.register(AylythItems.BLIGHTED_AXE, Models.HANDHELD);
        generator.register(AylythItems.BLIGHTED_HOE, Models.HANDHELD);

        generator.register(AylythItems.YMPE_EFFIGY, AylythModels.HANDHELD_ROTATED);

        generator.registerSpawnEgg(AylythItems.AYLYTHIAN_SPAWN_EGG, 0x6A4831, 0xE58E03);
        generator.registerSpawnEgg(AylythItems.ELDER_AYLYTHIAN_SPAWN_EGG, 0x513425, 0xFFDC9B);
        generator.registerSpawnEgg(AylythItems.PILOT_LIGHT_SPAWN_EGG, 0xFFD972, 0x9FD9F6);
        generator.registerSpawnEgg(AylythItems.SCION_SPAWN_EGG, 0x463428, 0xE58E03);
        generator.registerSpawnEgg(AylythItems.WREATHED_HIND_SPAWN_EGG, 0x5C4F42, 0xE1B886);
        generator.registerSpawnEgg(AylythItems.FAUNAYLYTHIAN_SPAWN_EGG, 0x6A4831, 0xE1AC20);
        generator.registerSpawnEgg(AylythItems.YMPEMOULD_SPAWN_EGG, 0x42423E, 0xE58E03);
        generator.registerSpawnEgg(AylythItems.BONEFLY_SPAWN_EGG, 0xE2E2D6, 0x3A2E2B);
        generator.registerSpawnEgg(AylythItems.TULPA_SPAWN_EGG, 0xE2E2D6, 0x73868F);

        generator.output.accept(
                AylythItems.SHUCKED_YMPE_FRUIT,
                ItemModels.condition(
                        ItemModels.hasComponentProperty(DataComponentTypes.ENTITY_DATA),
                        ItemModels.basic(generator.registerSubModel(AylythItems.SHUCKED_YMPE_FRUIT, "_variant", Models.GENERATED)),
                        ItemModels.basic(generator.upload(AylythItems.SHUCKED_YMPE_FRUIT, Models.GENERATED))
                )
        );

        generator.registerWithInHandModel(AylythItems.YMPE_GLAIVE);
        generator.registerWithInHandModel(AylythItems.YMPE_FLAMBERGE);
        generator.registerWithInHandModel(AylythItems.YMPE_SCYTHE);
        generator.registerWithInHandModel(AylythItems.VAMPIRIC_SWORD);
        generator.registerWithInHandModel(AylythItems.BLIGHTED_SWORD);
        registerThrowableWithInHandModel(generator, AylythItems.YMPE_LANCE);

        registerFlask(generator, AylythItems.NEPHRITE_FLASK);
        registerFlask(generator, AylythItems.DARK_NEPHRITE_FLASK);
        // TODO: Setup blight potion
//        generator.registerSubModel(Items.POTION, "_blight", Models.GENERATED);
//        generator.registerSubModel(Items.SPLASH_POTION, "_blight", Models.GENERATED);
//        generator.registerSubModel(Items.LINGERING_POTION, "_blight", Models.GENERATED);
//        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola"), TextureMap.layered(Aylyth.id("item/blight_potion"), Aylyth.id("item/blight_potion")), generator.writer);
//        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola_splash"), TextureMap.layered(Aylyth.id("item/blight_potion_splash"), Aylyth.id("item/blight_potion_splash")), generator.writer);
//        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola_lingering"), TextureMap.layered(Aylyth.id("item/blight_potion_lingering"), Aylyth.id("item/blight_potion_lingering")), generator.writer);

        registerSimpleParented(ModelIds.getItemModelId(AylythItems.GREEN_AYLYTHIAN_OAK_LEAVES), ModelIds.getBlockSubModelId(AylythBlocks.GREEN_AYLYTHIAN_OAK_LEAVES, "_1"), generator.modelCollector);
        registerSimpleParented(ModelIds.getItemModelId(AylythItems.ORANGE_AYLYTHIAN_OAK_LEAVES), ModelIds.getBlockSubModelId(AylythBlocks.ORANGE_AYLYTHIAN_OAK_LEAVES, "_1"), generator.modelCollector);
        registerSimpleParented(ModelIds.getItemModelId(AylythItems.RED_AYLYTHIAN_OAK_LEAVES), ModelIds.getBlockSubModelId(AylythBlocks.RED_AYLYTHIAN_OAK_LEAVES, "_1"), generator.modelCollector);
        registerSimpleParented(ModelIds.getItemModelId(AylythItems.BROWN_AYLYTHIAN_OAK_LEAVES), ModelIds.getBlockSubModelId(AylythBlocks.BROWN_AYLYTHIAN_OAK_LEAVES, "_1"), generator.modelCollector);
    }

    private void registerThrowableWithInHandModel(ItemModelGenerator generator, Item item) {
        ItemModel.Unbaked fallback = ItemModels.basic(generator.upload(item, Models.GENERATED));
        ItemModel.Unbaked variants = ItemModels.condition(
                ItemModels.usingItemProperty(),
                ItemModels.basic(ModelIds.getItemSubModelId(item, "_throwing")),
                ItemModels.basic(ModelIds.getItemSubModelId(item, "_in_hand"))
        );
        generator.output.accept(item, ItemModelGenerator.createModelWithInHandVariant(fallback, variants));
    }

    private void registerMarigolds(BlockStateModelGenerator generator, Block block) {
        registerModelWithNumberedVariantsAndItem(generator, block, Models.CROSS, i -> TextureMap.cross(ModelIds.getBlockSubModelId(block, "_" + i)), 5);
    }

    private void registerNysianGrapeVine(BlockStateModelGenerator generator, Block block) {
        Identifier[] ids = {
                AylythModels.NYSIAN_GRAPE_VINE_BASE.upload(block, "_0", TextureMap.of(AylythModels.FRUIT, blockId("nysian_grape_vine_fruit_0")), generator.modelCollector),
                AylythModels.NYSIAN_GRAPE_VINE_BASE.upload(block, "_1", TextureMap.of(AylythModels.FRUIT, blockId("nysian_grape_vine_fruit_1")), generator.modelCollector),
                AylythModels.NYSIAN_GRAPE_VINE_BASE.upload(block, "_2", TextureMap.of(AylythModels.FRUIT, blockId("nysian_grape_vine_fruit_2")), generator.modelCollector),
                AylythModels.NYSIAN_GRAPE_VINE_BASE.upload(block, "_3", TextureMap.of(AylythModels.FRUIT, blockId("nysian_grape_vine_fruit_3")), generator.modelCollector)
        };

        MultipartBlockStateSupplier multipart = MultipartBlockStateSupplier.create(block);

        for (int i = 0; i < 4; i++) {
            Identifier id = ids[i];
            for (Pair<Direction, Function<Identifier, BlockStateVariant>> pair : BlockStateModelGenerator.CONNECTION_VARIANT_FUNCTIONS) {
                When.PropertyCondition propertyCondition = Util.make(
                        When.create(), propertyConditionx -> BlockStateModelGenerator.CONNECTION_VARIANT_FUNCTIONS.stream().map(Pair::getFirst).map(MultifaceBlock::getProperty).forEach(property -> {
                            if (block.getDefaultState().contains(property)) {
                                propertyConditionx.set(property, false);
                            }
                        })
                );

                BooleanProperty direction = MultifaceBlock.getProperty(pair.getFirst());
                Function<Identifier, BlockStateVariant> variantGetter = pair.getSecond();
                if (block.getDefaultState().contains(direction)) {
                    BlockStateVariant variant = variantGetter.apply(id);
                    multipart.with(When.create().set(direction, true).set(NysianGrapeVineBlock.AGE, i), variant);
                    multipart.with(propertyCondition.set(NysianGrapeVineBlock.AGE, i), variant);
                }
            }
        }

        generator.blockStateCollector.accept(multipart);
        generator.itemModelOutput.accept(block.asItem(),
                ItemModels.basic(
                        Models.GENERATED_THREE_LAYERS.upload(
                                ModelIds.getItemModelId(block.asItem()),
                                TextureMap.layered(blockId("nysian_grape_vine"), blockId("nysian_grape_vine_leaves"), blockId("nysian_grape_vine_details")),
                                generator.modelCollector
                        )
                ));
    }

    private void registerFruitBearingYmpeBlock(BlockStateModelGenerator generator, Block block, Block logBlock) {
        Identifier normalSideTexture = ModelIds.getBlockSubModelId(block, "_side");
        Identifier topTexture = ModelIds.getBlockSubModelId(logBlock, "_top");
        Identifier age0 = Models.CUBE_COLUMN.upload(
                ModelIds.getBlockSubModelId(block, "_0"),
                TextureMap.sideEnd(ModelIds.getBlockSubModelId(block, "_bleeding_side"), topTexture),
                generator.modelCollector);
        Identifier age1 = Models.CUBE_COLUMN.upload(
                ModelIds.getBlockSubModelId(block, "_1"),
                TextureMap.sideEnd(normalSideTexture, topTexture),
                generator.modelCollector);
        Identifier age2 = AylythModels.FRUIT_BEARING_YMPE_LOG_BASE.upload(
                ModelIds.getBlockSubModelId(block, "_2"),
                TextureMap.of(TextureKey.SIDE, normalSideTexture)
                        .put(TextureKey.END, topTexture)
                        .put(AylythModels.FRUIT, blockId("ympe_fruit_unripe")),
                generator.modelCollector);
        Identifier age3 = AylythModels.FRUIT_BEARING_YMPE_LOG_BASE.upload(
                ModelIds.getBlockSubModelId(block, "_3"),
                TextureMap.of(TextureKey.SIDE, normalSideTexture)
                        .put(TextureKey.END, topTexture)
                        .put(AylythModels.FRUIT, blockId("ympe_fruit_ripening")),
                generator.modelCollector);
        Identifier age4 = AylythModels.FRUIT_BEARING_YMPE_LOG_BASE.upload(
                ModelIds.getBlockSubModelId(block, "_4"),
                TextureMap.of(TextureKey.SIDE, normalSideTexture)
                        .put(TextureKey.END, topTexture)
                        .put(AylythModels.FRUIT, blockId("ympe_fruit_ripe")),
                generator.modelCollector);
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(GrowingHarvestablePillarBlock.AGE, GrowingHarvestablePillarBlock.AXIS)
                        .register((integer, axis) -> {
                            BlockStateVariant variant = BlockStateVariant.create();
                            variant.put(VariantSettings.MODEL,
                                    switch (integer) {
                                        case 0 -> age0;
                                        case 1 -> age1;
                                        case 2 -> age2;
                                        case 3 -> age3;
                                        case 4 -> age4;
                                        default -> throw new IllegalStateException("No model for given age: " + integer);
                                    });
                            switch (axis) {
                                case X -> {
                                    variant.put(VariantSettings.X, VariantSettings.Rotation.R90);
                                    variant.put(VariantSettings.Y, VariantSettings.Rotation.R90);
                                }
                                case Y -> {}
                                case Z -> variant.put(VariantSettings.X, VariantSettings.Rotation.R90);
                            }
                            return variant;
                        })
                )
        );
        generator.registerParentedItemModel(AylythBlocks.FRUIT_BEARING_YMPE_LOG, age4);
    }

    private void registerDarkPodzol(BlockStateModelGenerator generator, Block block, Block dirtBlock) {
        List<BlockStateVariant> variants = new ObjectArrayList<>();
        for (int i = 1; i <= 4; i++) {
            TextureMap map = TextureMap.of(TextureKey.TOP, blockId("dark_podzol_top_" + i))
                    .put(TextureKey.BOTTOM, ModelIds.getBlockModelId(dirtBlock))
                    .put(TextureKey.SIDE, ModelIds.getBlockModelId(dirtBlock))
                    .put(AylythModels.OVERLAY, blockId("dark_podzol_side_overlay"))
                    .put(TextureKey.PARTICLE, ModelIds.getBlockModelId(dirtBlock));
            Identifier id = AylythModels.CUBE_BOTTOM_TOP_WITH_OVERLAY.upload(ModelIds.getBlockSubModelId(block, "_" + i), map, generator.modelCollector);
            variants.addAll(List.of(createModelVariantWithRandomHorizontalRotations(id)));
        }
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(Properties.SNOWY)
                        .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(Blocks.GRASS_BLOCK, "_snow")))
                        .register(false, variants)
                )
        );
        generator.registerParentedItemModel(block, ModelIds.getBlockSubModelId(block, "_1"));
    }

    private void registerSeep(BlockStateModelGenerator generator, Block block, Block logBlock) {
        registerSeep(generator, block, ModelIds.getBlockModelId(logBlock), ModelIds.getBlockSubModelId(logBlock, "_top"));
    }

    private void registerSeep(BlockStateModelGenerator generator, Block block, Identifier side, Identifier end) {
        TextureMap map = TextureMap.sideEnd(side, end);
        Identifier singleId = AylythModels.SEEP_LOG_SINGLE.upload(block, map, generator.modelCollector);
        generator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(block)
                        .coordinate(BlockStateVariantMap.create(SeepBlock.CONNECTION)
                                .register(SeepBlock.Connection.NONE, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, singleId))
                                .register(SeepBlock.Connection.UP, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, AylythModels.SEEP_LOG_BOTTOM.upload(block, map, generator.modelCollector)))
                                .register(SeepBlock.Connection.DOWN, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, AylythModels.SEEP_LOG_TOP.upload(block, map, generator.modelCollector)))
                        )
        );
        generator.registerParentedItemModel(block, singleId);
    }

    private Identifier registerCubeAllWithNumberedVariantsAndItem(BlockStateModelGenerator generator, Block block, int variants) {
        return registerModelWithNumberedVariantsAndItem(generator, block, Models.CUBE_ALL, key -> TextureMap.all(ModelIds.getBlockSubModelId(block, "_" + key)), variants);
    }

    private Identifier registerModelWithNumberedVariantsAndItem(BlockStateModelGenerator generator, Block block, Model model, Int2ObjectFunction<TextureMap> textureMapProvider, int variants) {
        BlockStateVariant[] stateVariants = new BlockStateVariant[variants];
        Identifier firstId = ModelIds.getBlockSubModelId(block, "_" + 1);
        for (int i = 1; i <= variants; i++) {
            Identifier identifier = model.upload(ModelIds.getBlockSubModelId(block, "_" + i), textureMapProvider.apply(i), generator.modelCollector);
            stateVariants[i-1] = BlockStateVariant.create().put(VariantSettings.MODEL, identifier);
        }
        generator.registerItemModel(block.asItem(), firstId);
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, stateVariants));
        return firstId;
    }

    private VariantsBlockStateSupplier numberedVariants(Block block, int variants) {
        return numberedVariants(block, ModelIds.getBlockModelId(block), variants);
    }

    private VariantsBlockStateSupplier numberedVariants(Block block, Identifier modelId, int variants) {
        return VariantsBlockStateSupplier.create(block, Util.make(new BlockStateVariant[variants], stateVariants -> {
            for (int i = 1; i <= variants; i++) {
                stateVariants[i-1] = BlockStateVariant.create().put(VariantSettings.MODEL, modelId.withSuffixedPath("_" + i));
            }
        }));
    }

    // copy without the regular cross state and model registration
    private void registerFlowerPot(BlockStateModelGenerator generator, Block plantBlock, Block flowerPotBlock, BlockStateModelGenerator.CrossType tintType) {
        TextureMap textureMap = TextureMap.plant(plantBlock);
        Identifier identifier = tintType.getFlowerPotCrossModel().upload(flowerPotBlock, textureMap, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(flowerPotBlock, identifier));
    }

    private void registerBranchAndItem(BlockStateModelGenerator generator, Block block) {
        AylythModels.BRANCH_TEMPLATE.upload(block, TextureMap.of(TextureKey.SIDE, ModelIds.getBlockModelId(block)), generator.modelCollector);
        generator.registerNorthDefaultHorizontalRotation(block);
        generator.registerItemModel(block.asItem(), generator.uploadBlockItemModel(block.asItem(), block));
    }

    private VariantsBlockStateSupplier sapstoneBlockStates(Block sapstoneBlock, Identifier verticalModel) {
        return VariantsBlockStateSupplier.create(
                sapstoneBlock,
                createModelVariantWithRandomHorizontalRotations(verticalModel)
        );
    }

    private VariantsBlockStateSupplier soulHearthStates(Block block, Identifier topModelId, Identifier lowerModelId, Identifier lowerChargedModelId) {
        BlockStateVariant topModel = BlockStateVariant.create().put(VariantSettings.MODEL, topModelId);
        BlockStateVariant lowerModel = BlockStateVariant.create().put(VariantSettings.MODEL, lowerModelId);
        BlockStateVariant lowerChargedModel = BlockStateVariant.create().put(VariantSettings.MODEL, lowerChargedModelId);
        return VariantsBlockStateSupplier.create(block)
                .coordinate(
                        BlockStateVariantMap.create(SoulHearthBlock.HALF, SoulHearthBlock.CHARGES)
                                .register((doubleBlockHalf, integer) -> {
                                    if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
                                        return topModel;
                                    }
                                    if (integer > 0) {
                                        return lowerChargedModel;
                                    }
                                    return lowerModel;
                                })
                );
    }

    private void registerFlask(ItemModelGenerator generator, ItemConvertible flask) {
        List<RangeDispatchItemModel.Entry> entries = new ObjectArrayList<>();
        for (int i = 1; i <= 6; i++) {
            entries.add(
                    ItemModels.rangeDispatchEntry(
                            ItemModels.basic(
                                    generator.registerSubModel(flask.asItem(), "_%s_charges".formatted(i), Models.GENERATED)
                            ),
                            i
                    )
            );
        }
        generator.output.accept(
                flask.asItem(),
                ItemModels.rangeDispatch(
                        new FlaskChargesProperty(),
                        ItemModels.basic(generator.upload(flask.asItem(), Models.GENERATED)),
                        entries
                )
        );
    }


    private void largeWoodyGrowth(Consumer<BlockStateSupplier> collector, Block block) {
        collector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(LargeWoodyGrowthBlock.HALF)
                                .registerVariants(doubleBlockHalf -> {
                                    if (doubleBlockHalf == DoubleBlockHalf.LOWER) {
                                        List<BlockStateVariant> variants = new ObjectArrayList<>();
                                        Identifier id = ModelIds.getBlockModelId(AylythBlocks.LARGE_WOODY_GROWTH);
                                        // Generate for each of the four models with every rotation value
                                        for (int i = 1; i <= 4; i++) {
                                            Collections.addAll(variants, createModelVariantWithRandomHorizontalRotations(id.withSuffixedPath("_" + i)));
                                        }
                                        return variants;
                                    } else {
                                        return ObjectArrayList.of(BlockStateVariant.create().put(VariantSettings.MODEL, blockId("woody_growth_particles")));
                                    }
                                })
                )
        );
    }

    private void smallWoodyGrowth(Consumer<BlockStateSupplier> collector, Block block) {
        List<BlockStateVariant> variants = new ObjectArrayList<>();
        Identifier id = ModelIds.getBlockModelId(block);
        for (int i = 1; i <= 3; i++) {
            Collections.addAll(variants, createModelVariantWithRandomHorizontalRotations(id.withSuffixedPath("_" + i)));
        }

        collector.accept(VariantsBlockStateSupplier.create(block, variants.toArray(BlockStateVariant[]::new)));
    }

    private void fruitingLeaves(BlockStateModelGenerator generator, Block block, Identifier stage0, Identifier stage1, Identifier stage2, Identifier stage3) {
        VariantsBlockStateSupplier variants = VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(LeavesBlock.DISTANCE).register(integer -> BlockStateVariant.create().put(VariantSettings.MODEL, stage0)))
                .coordinate(BlockStateVariantMap.create(LeavesBlock.PERSISTENT).register(aBoolean -> BlockStateVariant.create().put(VariantSettings.MODEL, stage0)))
                .coordinate(BlockStateVariantMap.create(LeavesBlock.WATERLOGGED).register(aBoolean -> BlockStateVariant.create().put(VariantSettings.MODEL, stage0)))
                .coordinate(BlockStateVariantMap.create(PomegranateLeavesBlock.FRUITING).register(0, BlockStateVariant.create().put(VariantSettings.MODEL, stage0)).register(1, BlockStateVariant.create().put(VariantSettings.MODEL, stage1)).register(2, BlockStateVariant.create().put(VariantSettings.MODEL, stage2)).register(3, BlockStateVariant.create().put(VariantSettings.MODEL, stage3)));
        generator.blockStateCollector.accept(variants);
    }

    /** This just registers a single variant block state */
    private void singleton(BlockStateModelGenerator generator, Block block) {
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, ModelIds.getBlockModelId(block)));
    }

    private void generateStrewnLeaves(BlockStateModelGenerator generator, Block strewnLeavesBlock, List<Identifier> models) {
        generator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(strewnLeavesBlock, models.stream()
                        .flatMap(id -> Stream.of(BlockStateModelGenerator.createModelVariantWithRandomHorizontalRotations(id)))
                        .toArray(BlockStateVariant[]::new)
                ));
        models.forEach(identifier -> {
            AylythModels.STREWN_LEAVES_TEMPLATE.upload(identifier, TextureMap.of(TextureKey.TOP, identifier), generator.modelCollector);
        });
        AylythModels.STREWN_LEAVES_TEMPLATE.upload(strewnLeavesBlock, TextureMap.of(TextureKey.TOP, models.getFirst()), generator.modelCollector);

        generator.registerItemModel(strewnLeavesBlock.asItem(), Models.GENERATED.upload(ModelIds.getItemModelId(strewnLeavesBlock.asItem()), TextureMap.layer0(models.getFirst()), generator.modelCollector));
    }

    private void generateLeafPiles(BlockStateModelGenerator generator, Block leafPile, Block leaves, boolean tinted) {
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(leafPile)
                .coordinate(
                        BlockStateVariantMap.create(LeafPileBlock.LEAVES)
                                .register(i ->
                                        BlockStateVariant.create()
                                                .put(VariantSettings.MODEL, ModelIds.getBlockModelId(leafPile).withSuffixedPath("_" + i))
                                )
                ));
        Identifier leafPileId = ModelIds.getBlockModelId(leafPile);
        TextureMap pileMap = TextureMap.all(leaves);
        LeafPileBlock.LEAVES.getValues().forEach(i -> {
            Identifier templateId = blockId("leaf_pile_" + i);
            Model model = new Model(Optional.of(templateId), Optional.empty(), TextureKey.ALL);
            model.upload(leafPileId.withSuffixedPath("_" + i), pileMap, generator.modelCollector);
        });
        if (tinted) {
            generator.registerTintedItemModel(leafPile, leafPileId.withSuffixedPath("_1"), new ConstantTintSource(FoliageColors.DEFAULT));
        } else {
            generator.registerItemModel(leafPile.asItem(), leafPileId.withSuffixedPath("_1"));
        }
    }

    /** From vanilla {@link BlockStateModelGenerator#registerMushroomBlock}, modified for Aylyth usage */
    private void registerMushroomBlock(BlockStateModelGenerator generator, Block mushroomBlock, Identifier insideTexture) {
        Identifier modelId = Models.TEMPLATE_SINGLE_FACE.upload(mushroomBlock, TextureMap.texture(mushroomBlock), generator.modelCollector);
        generator.blockStateCollector.accept(MultipartBlockStateSupplier.create(mushroomBlock).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with(When.create().set(Properties.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture)).with((When)When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.UP, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.DOWN, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)));
        generator.registerParentedItemModel(mushroomBlock, TexturedModel.CUBE_ALL.upload(mushroomBlock, "_inventory", generator.modelCollector));
    }

    private static void registerSimpleParented(Identifier id, Identifier parent, BiConsumer<Identifier, ModelSupplier> writer) {
        writer.accept(id, new SimpleModelSupplier(parent));
    }
}
