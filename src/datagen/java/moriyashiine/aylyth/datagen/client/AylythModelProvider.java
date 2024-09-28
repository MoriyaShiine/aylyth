package moriyashiine.aylyth.datagen.client;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.types.LargeWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.types.PomegranateLeavesBlock;
import moriyashiine.aylyth.common.block.types.StrewnLeavesBlock;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.client.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AylythModelProvider extends FabricModelProvider {

    private static final BlockFamily POMEGRANATE = BlockFamilies.register(AylythBlocks.POMEGRANATE_PLANKS).button(AylythBlocks.POMEGRANATE_BUTTON).fence(AylythBlocks.POMEGRANATE_FENCE).fenceGate(AylythBlocks.POMEGRANATE_FENCE_GATE).pressurePlate(AylythBlocks.POMEGRANATE_PRESSURE_PLATE).sign(AylythBlocks.POMEGRANATE_SIGN, AylythBlocks.POMEGRANATE_WALL_SIGN).slab(AylythBlocks.POMEGRANATE_SLAB).stairs(AylythBlocks.POMEGRANATE_STAIRS).door(AylythBlocks.POMEGRANATE_DOOR).trapdoor(AylythBlocks.POMEGRANATE_TRAPDOOR).group("wooden").unlockCriterionName("has_planks").build();
    private static final BlockFamily WRITHEWOOD = BlockFamilies.register(AylythBlocks.WRITHEWOOD_PLANKS).button(AylythBlocks.WRITHEWOOD_BUTTON).fence(AylythBlocks.WRITHEWOOD_FENCE).fenceGate(AylythBlocks.WRITHEWOOD_FENCE_GATE).pressurePlate(AylythBlocks.WRITHEWOOD_PRESSURE_PLATE).sign(AylythBlocks.WRITHEWOOD_SIGN, AylythBlocks.WRITHEWOOD_WALL_SIGN).slab(AylythBlocks.WRITHEWOOD_SLAB).stairs(AylythBlocks.WRITHEWOOD_STAIRS).door(AylythBlocks.WRITHEWOOD_DOOR).trapdoor(AylythBlocks.WRITHEWOOD_TRAPDOOR).group("wooden").unlockCriterionName("has_planks").build();

    private static final Identifier STREWN_LEAVES_TEMPLATE = new Identifier(Aylyth.MOD_ID, "block/strewn_leaves_template");
    private static final Identifier LEAF_PILE_1_TEMPLATE = new Identifier(Aylyth.MOD_ID, "block/leaf_pile_1");
    private static final Identifier LEAF_PILE_2_TEMPLATE = new Identifier(Aylyth.MOD_ID, "block/leaf_pile_2");
    private static final Identifier LEAF_PILE_3_TEMPLATE = new Identifier(Aylyth.MOD_ID, "block/leaf_pile_3");
    private static final Identifier LEAF_PILE_4_TEMPLATE = new Identifier(Aylyth.MOD_ID, "block/leaf_pile_4");
    private static final Identifier LEAF_PILE_5_TEMPLATE = new Identifier(Aylyth.MOD_ID, "block/leaf_pile_5");
    private static final Identifier LEAF_PILE_6_TEMPLATE = new Identifier(Aylyth.MOD_ID, "block/leaf_pile_6");
    private static final Identifier LEAF_PILE_7_TEMPLATE = new Identifier(Aylyth.MOD_ID, "block/leaf_pile_7");

    private static final Model STREWN_LEAVES_MODEL = new Model(Optional.of(STREWN_LEAVES_TEMPLATE), Optional.empty(), TextureKey.TOP, TextureKey.PARTICLE);
    private static final Model LEAF_PILE_1_MODEL = new Model(Optional.of(LEAF_PILE_1_TEMPLATE), Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE);
    private static final Model LEAF_PILE_2_MODEL = new Model(Optional.of(LEAF_PILE_2_TEMPLATE), Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE);
    private static final Model LEAF_PILE_3_MODEL = new Model(Optional.of(LEAF_PILE_3_TEMPLATE), Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE);
    private static final Model LEAF_PILE_4_MODEL = new Model(Optional.of(LEAF_PILE_4_TEMPLATE), Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE);
    private static final Model LEAF_PILE_5_MODEL = new Model(Optional.of(LEAF_PILE_5_TEMPLATE), Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE);
    private static final Model LEAF_PILE_6_MODEL = new Model(Optional.of(LEAF_PILE_6_TEMPLATE), Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE);
    private static final Model LEAF_PILE_7_MODEL = new Model(Optional.of(LEAF_PILE_7_TEMPLATE), Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE);

    private static final Model BUILTIN = new Model(Optional.of(new Identifier("builtin/entity")), Optional.empty());
    private static final Model SPAWN_EGG = new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty());

    public AylythModelProvider(FabricDataOutput output) {
        super(output);
    }

    private Identifier blockId(String id) {
        return id("block/" + id);
    }

    private Identifier id(String id) {
        return new Identifier(Aylyth.MOD_ID, id);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerDoor(AylythBlocks.YMPE_DOOR);
        blockStateModelGenerator.registerFlowerPotPlant(AylythBlocks.MARIGOLD, AylythBlocks.MARIGOLD_POTTED, BlockStateModelGenerator.TintType.NOT_TINTED);
        generateStrewnLeaves(blockStateModelGenerator, AylythBlocks.OAK_STREWN_LEAVES, Blocks.OAK_LEAVES, id("block/fallen_oak_leaves_01"), id("block/fallen_oak_leaves_02"), id("block/fallen_oak_leaves_03"), id("block/fallen_oak_leaves_04"), id("block/fallen_oak_leaves_05"), id("block/fallen_oak_leaves_06"), id("block/fallen_oak_leaves_07"), id("block/fallen_oak_leaves_08"), id("block/fallen_oak_leaves_09"), id("block/fallen_oak_leaves_10"));
        generateStrewnLeaves(blockStateModelGenerator, AylythBlocks.YMPE_STREWN_LEAVES, AylythBlocks.YMPE_LEAVES, id("block/fallen_ympe_leaves_01"), id("block/fallen_ympe_leaves_02"));

        blockStateModelGenerator.registerHangingSign(AylythBlocks.YMPE_STRIPPED_LOG, AylythBlocks.YMPE_HANGING_SIGN, AylythBlocks.YMPE_WALL_HANGING_SIGN);

        blockStateModelGenerator.registerLog(AylythBlocks.POMEGRANATE_STRIPPED_LOG).log(AylythBlocks.POMEGRANATE_STRIPPED_LOG).wood(AylythBlocks.POMEGRANATE_STRIPPED_WOOD);
        blockStateModelGenerator.registerLog(AylythBlocks.POMEGRANATE_LOG).log(AylythBlocks.POMEGRANATE_LOG).wood(AylythBlocks.POMEGRANATE_WOOD);
        blockStateModelGenerator.registerFlowerPotPlant(AylythBlocks.POMEGRANATE_SAPLING, AylythBlocks.POMEGRANATE_POTTED_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerCubeAllModelTexturePool(AylythBlocks.POMEGRANATE_PLANKS).family(POMEGRANATE);
        blockStateModelGenerator.registerHangingSign(AylythBlocks.POMEGRANATE_STRIPPED_LOG, AylythBlocks.POMEGRANATE_HANGING_SIGN, AylythBlocks.POMEGRANATE_WALL_HANGING_SIGN);
        fruitingLeaves(blockStateModelGenerator, AylythBlocks.POMEGRANATE_LEAVES, blockId("pomegranate_leaves"), blockId("pomegranate_leaves_fruiting_0"), blockId("pomegranate_leaves_fruiting_1"), blockId("pomegranate_leaves_fruiting_2"));
        TexturedModel.CUBE_ALL.upload(AylythBlocks.POMEGRANATE_LEAVES, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.registerLog(AylythBlocks.WRITHEWOOD_STRIPPED_LOG).log(AylythBlocks.WRITHEWOOD_STRIPPED_LOG).wood(AylythBlocks.WRITHEWOOD_STRIPPED_WOOD);
        blockStateModelGenerator.registerLog(AylythBlocks.WRITHEWOOD_LOG).log(AylythBlocks.WRITHEWOOD_LOG).wood(AylythBlocks.WRITHEWOOD_WOOD);
        blockStateModelGenerator.registerFlowerPotPlant(AylythBlocks.WRITHEWOOD_SAPLING, AylythBlocks.WRITHEWOOD_POTTED_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerCubeAllModelTexturePool(AylythBlocks.WRITHEWOOD_PLANKS)
                .family(WRITHEWOOD);
        blockStateModelGenerator.registerHangingSign(AylythBlocks.WRITHEWOOD_STRIPPED_LOG, AylythBlocks.WRITHEWOOD_HANGING_SIGN, AylythBlocks.WRITHEWOOD_WALL_HANGING_SIGN);
        variantState(blockStateModelGenerator, AylythBlocks.WRITHEWOOD_LEAVES);

        Models.TEMPLATE_SINGLE_FACE.upload(blockId("jack_o_lantern_mushroom_block_inner"), TextureMap.texture(blockId("jack_o_lantern_mushroom_block_inner")), blockStateModelGenerator.modelCollector);
        registerMushroomBlock(blockStateModelGenerator, AylythBlocks.JACK_O_LANTERN_MUSHROOM_STEM, blockId("jack_o_lantern_mushroom_block_inner"));
        registerMushroomBlock(blockStateModelGenerator, AylythBlocks.JACK_O_LANTERN_MUSHROOM_BLOCK, blockId("jack_o_lantern_mushroom_block_inner"));

        blockStateModelGenerator.registerAxisRotated(AylythBlocks.CHTHONIA_WOOD, TexturedModel.makeFactory(block -> new TextureMap().put(TextureKey.SIDE, ModelIds.getBlockModelId(AylythBlocks.CHTHONIA_WOOD)).put(TextureKey.END, ModelIds.getBlockModelId(AylythBlocks.CHTHONIA_WOOD)), Models.CUBE_COLUMN));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(AylythBlocks.NEPHRITIC_CHTHONIA_WOOD, ModelIds.getBlockModelId(AylythBlocks.NEPHRITIC_CHTHONIA_WOOD)));

        blockStateModelGenerator.registerSingleton(AylythBlocks.DARK_WOODS_TILES, TexturedModel.CUBE_ALL);

        blockStateModelGenerator.registerSingleton(AylythBlocks.ESSTLINE_BLOCK, TexturedModel.CUBE_ALL);
        blockStateModelGenerator.registerSingleton(AylythBlocks.NEPHRITE_BLOCK, TexturedModel.CUBE_ALL);

        blockStateModelGenerator.registerMirrorable(AylythBlocks.CARVED_SMOOTH_NEPHRITE);
        blockStateModelGenerator.registerSingleton(AylythBlocks.CARVED_ANTLERED_NEPHRITE, TexturedModel.CUBE_ALL);
        blockStateModelGenerator.registerAxisRotated(AylythBlocks.CARVED_NEPHRITE_PILLAR, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        blockStateModelGenerator.registerSingleton(AylythBlocks.CARVED_NEPHRITE_TILES, TexturedModel.CUBE_ALL);
        blockStateModelGenerator.registerSingleton(AylythBlocks.CARVED_WOODY_NEPHRITE, TexturedModel.CUBE_ALL);

        Models.PARTICLE.upload(blockId("woody_growth_particles"), TextureMap.particle(blockId("aylyth_bush_trunk")), blockStateModelGenerator.modelCollector);
        woodyGrowth(blockStateModelGenerator);
        generateWoodBlock(blockStateModelGenerator, AylythBlocks.SEEPING_WOOD, "block/aylyth_bush_trunk");

        blockStateModelGenerator.registerTintableCrossBlockState(AylythBlocks.GIRASOL_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        TextureMap girasolMap = TextureMap.plant(AylythBlocks.GIRASOL_SAPLING);
        Identifier pottedSaplingId = BlockStateModelGenerator.TintType.NOT_TINTED.getFlowerPotCrossModel().upload(AylythBlocks.GIRASOL_SAPLING_POTTED, girasolMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(AylythBlocks.GIRASOL_SAPLING_POTTED, pottedSaplingId));
        variantState(blockStateModelGenerator, AylythBlocks.BLACK_WELL);

        blockStateModelGenerator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.SAPSTONE, blockId("sapstone")));
        blockStateModelGenerator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.AMBER_SAPSTONE, blockId("amber_sapstone")));
        blockStateModelGenerator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.LIGNITE_SAPSTONE, blockId("lignite_sapstone")));
        blockStateModelGenerator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.OPALESCENT_SAPSTONE, blockId("opalescent_sapstone")));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        generateStrewnLeavesItemModel(AylythItems.OAK_STREWN_LEAVES, id("block/fallen_oak_leaves_01"), itemModelGenerator);
        generateStrewnLeavesItemModel(AylythItems.YMPE_STREWN_LEAVES, id("block/fallen_ympe_leaves_01"), itemModelGenerator);
        itemModelGenerator.register(AylythItems.YMPE_MUSH, Models.GENERATED);
        itemModelGenerator.register(AylythItems.POMEGRANATE, Models.GENERATED);
        itemModelGenerator.register(AylythItems.GHOSTCAP_MUSHROOM_SPORES, Models.GENERATED);
        itemModelGenerator.register(AylythItems.POMEGRANATE_BOAT, Models.GENERATED);
        itemModelGenerator.register(AylythItems.POMEGRANATE_CHEST_BOAT, Models.GENERATED);
        itemModelGenerator.register(AylythItems.WRITHEWOOD_BOAT, Models.GENERATED);
        itemModelGenerator.register(AylythItems.WRITHEWOOD_CHEST_BOAT, Models.GENERATED);
        itemModelGenerator.register(AylythItems.DEBUG_WAND, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.WRONGMEAT, Models.GENERATED);
        itemModelGenerator.register(AylythItems.NEPHRITE_HEART, Models.GENERATED);
        itemModelGenerator.register(AylythItems.YHONDYTH_HEART, Models.GENERATED);
        itemModelGenerator.register(AylythItems.GIRASOL_SEED, Models.GENERATED);
        itemModelGenerator.register(AylythItems.LARGE_WOODY_GROWTH, Models.GENERATED);
        itemModelGenerator.register(AylythItems.WOODY_GROWTH_CACHE, BUILTIN);
        itemModelGenerator.register(AylythItems.SMALL_WOODY_GROWTH, Models.GENERATED);
        itemModelGenerator.register(AylythItems.YMPE_CUIRASS, Models.GENERATED);
//        itemModelGenerator.register(ModItems.MYSTERIOUS_SKETCH, BUILTIN);
        itemModelGenerator.register(AylythItems.WREATHED_HIND_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(AylythItems.FAUNAYLYTHIAN_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(AylythItems.YMPEMOULD_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(AylythItems.BONEFLY_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(AylythItems.TULPA_SPAWN_EGG, SPAWN_EGG);
//        Models.GENERATED.upload(AylythUtil.id("item/" + Registries.ITEM.getId(ModItems.MYSTERIOUS_SKETCH).getPath() + "_generated"), TextureMap.layer0(AylythUtil.id("item/" + Registries.ITEM.getId(ModItems.MYSTERIOUS_SKETCH).getPath())), itemModelGenerator.writer);
        itemModelGenerator.register(AylythItems.POMEGRANATE_CASSETTE, Models.GENERATED);
        itemModelGenerator.register(AylythItems.BLIGHTED_THORNS, Models.GENERATED);
        itemModelGenerator.register(AylythItems.YMPE_FLAMBERGE, BUILTIN);
        itemModelGenerator.register(AylythItems.YMPE_SCYTHE, BUILTIN);
        itemModelGenerator.register(AylythItems.THORN_FLECHETTE, Models.GENERATED);
        itemModelGenerator.register(AylythItems.BLIGHTED_THORN_FLECHETTE, Models.GENERATED);
        itemModelGenerator.register(AylythItems.LANCEOLATE_DAGGER, Models.HANDHELD);

        itemModelGenerator.register(AylythItems.VAMPIRIC_SWORD, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.VAMPIRIC_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.VAMPIRIC_AXE, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.VAMPIRIC_HOE, Models.HANDHELD);

        itemModelGenerator.register(AylythItems.BLIGHTED_SWORD, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.BLIGHTED_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.BLIGHTED_AXE, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.BLIGHTED_HOE, Models.HANDHELD);

        registerFlask(itemModelGenerator, AylythItems.NEPHRITE_FLASK);
        registerFlask(itemModelGenerator, AylythItems.DARK_NEPHRITE_FLASK);
        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola"), TextureMap.layered(Aylyth.id("item/blight_potion"), Aylyth.id("item/blight_potion")), itemModelGenerator.writer);
        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola_splash"), TextureMap.layered(Aylyth.id("item/blight_potion_splash"), Aylyth.id("item/blight_potion_splash")), itemModelGenerator.writer);
        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola_lingering"), TextureMap.layered(Aylyth.id("item/blight_potion_lingering"), Aylyth.id("item/blight_potion_lingering")), itemModelGenerator.writer);
    }

    private VariantsBlockStateSupplier sapstoneBlockStates(Block sapstoneBlock, Identifier verticalModel) {
        return VariantsBlockStateSupplier.create(
                sapstoneBlock,
                BlockStateVariant.create().put(VariantSettings.MODEL, verticalModel),
                BlockStateVariant.create().put(VariantSettings.MODEL, verticalModel).put(VariantSettings.Y, VariantSettings.Rotation.R90),
                BlockStateVariant.create().put(VariantSettings.MODEL, verticalModel).put(VariantSettings.Y, VariantSettings.Rotation.R180),
                BlockStateVariant.create().put(VariantSettings.MODEL, verticalModel).put(VariantSettings.Y, VariantSettings.Rotation.R270)
        );
    }

    private void registerFlask(ItemModelGenerator generator, ItemConvertible flask) {
        Identifier id = Registries.ITEM.getId(flask.asItem()).withPrefixedPath("item/");
        Models.GENERATED.upload(id, TextureMap.layer0(flask.asItem()), generator.writer, (id1, textures) -> {
            JsonObject main = Models.GENERATED.createJson(id1, textures);
            JsonArray overrides = new JsonArray();
            for (int i = 1; i < 7; i++) {
                JsonObject predicate = new JsonObject();
                JsonObject overrideEntry = new JsonObject();
                predicate.addProperty("aylyth:uses", i / 6f);
                overrideEntry.add("predicate", predicate);
                overrideEntry.addProperty("model", id.withSuffixedPath("_" + i + "_charges").toString());
                overrides.add(overrideEntry);
            }

            main.add("overrides", overrides);
            return main;
        });

        for (int i = 1; i < 7; i++) {
            generator.register(flask.asItem(), "_" + i + "_charges", Models.GENERATED);
        }
    }

    private void generateWoodBlock(BlockStateModelGenerator generator, Block woodBlock, String texturePath) {
        TextureMap textureMap = new TextureMap();
        textureMap.put(TextureKey.SIDE, id(texturePath));
        textureMap.put(TextureKey.END, textureMap.getTexture(TextureKey.SIDE));
        Identifier identifier = Models.CUBE_COLUMN.upload(woodBlock, textureMap, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(woodBlock, identifier));
    }

    private void woodyGrowth(BlockStateModelGenerator generator) {
        generator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(AylythBlocks.LARGE_WOODY_GROWTH).
                        coordinate(BlockStateVariantMap.create(LargeWoodyGrowthBlock.HALF)
                                .register(DoubleBlockHalf.LOWER,
                                        List.of(
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_1"), VariantSettings.Rotation.R0),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_1"), VariantSettings.Rotation.R90),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_1"), VariantSettings.Rotation.R180),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_1"), VariantSettings.Rotation.R270),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_2"), VariantSettings.Rotation.R0),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_2"), VariantSettings.Rotation.R90),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_2"), VariantSettings.Rotation.R180),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_2"), VariantSettings.Rotation.R270),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_3"), VariantSettings.Rotation.R0),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_3"), VariantSettings.Rotation.R90),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_3"), VariantSettings.Rotation.R180),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_3"), VariantSettings.Rotation.R270),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_4"), VariantSettings.Rotation.R0),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_4"), VariantSettings.Rotation.R90),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_4"), VariantSettings.Rotation.R180),
                                        modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_4"), VariantSettings.Rotation.R270)
                                        )
                                )
                                .register(DoubleBlockHalf.UPPER, List.of(
                                        BlockStateVariant.create().put(VariantSettings.MODEL, blockId("woody_growth_particles"))
                                )))
        );
        generator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(AylythBlocks.WOODY_GROWTH_CACHE).
                        coordinate(BlockStateVariantMap.create(LargeWoodyGrowthBlock.HALF)
                                .register(DoubleBlockHalf.LOWER,
                                        List.of(
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_1"), VariantSettings.Rotation.R0),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_1"), VariantSettings.Rotation.R90),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_1"), VariantSettings.Rotation.R180),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_1"), VariantSettings.Rotation.R270),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_2"), VariantSettings.Rotation.R0),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_2"), VariantSettings.Rotation.R90),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_2"), VariantSettings.Rotation.R180),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_2"), VariantSettings.Rotation.R270),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_3"), VariantSettings.Rotation.R0),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_3"), VariantSettings.Rotation.R90),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_3"), VariantSettings.Rotation.R180),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_3"), VariantSettings.Rotation.R270),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_4"), VariantSettings.Rotation.R0),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_4"), VariantSettings.Rotation.R90),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_4"), VariantSettings.Rotation.R180),
                                                modelVariantWithYRotation(Aylyth.id("block/large_woody_growth_4"), VariantSettings.Rotation.R270)
                                        )
                                )
                                .register(DoubleBlockHalf.UPPER, List.of(
                                        BlockStateVariant.create().put(VariantSettings.MODEL, blockId("woody_growth_particles"))
                                )))
        );
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(AylythBlocks.SMALL_WOODY_GROWTH,
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_1"), VariantSettings.Rotation.R90),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_1"), VariantSettings.Rotation.R180),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_1"), VariantSettings.Rotation.R0),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_1"), VariantSettings.Rotation.R270),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_2"), VariantSettings.Rotation.R0),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_2"), VariantSettings.Rotation.R90),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_2"), VariantSettings.Rotation.R180),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_2"), VariantSettings.Rotation.R270),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_3"), VariantSettings.Rotation.R0),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_3"), VariantSettings.Rotation.R90),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_3"), VariantSettings.Rotation.R180),
                modelVariantWithYRotation(Aylyth.id("block/small_woody_growth_3"), VariantSettings.Rotation.R270)
                )
        );
    }

    private void fruitingLeaves(BlockStateModelGenerator generator, Block block, Identifier stage0, Identifier stage1, Identifier stage2, Identifier stage3) {
        VariantsBlockStateSupplier variants = VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(LeavesBlock.DISTANCE).register(integer -> BlockStateVariant.create().put(VariantSettings.MODEL, stage0)))
                .coordinate(BlockStateVariantMap.create(LeavesBlock.PERSISTENT).register(aBoolean -> BlockStateVariant.create().put(VariantSettings.MODEL, stage0)))
                .coordinate(BlockStateVariantMap.create(LeavesBlock.WATERLOGGED).register(aBoolean -> BlockStateVariant.create().put(VariantSettings.MODEL, stage0)))
                .coordinate(BlockStateVariantMap.create(PomegranateLeavesBlock.FRUITING).register(0, BlockStateVariant.create().put(VariantSettings.MODEL, stage0)).register(1, BlockStateVariant.create().put(VariantSettings.MODEL, stage1)).register(2, BlockStateVariant.create().put(VariantSettings.MODEL, stage2)).register(3, BlockStateVariant.create().put(VariantSettings.MODEL, stage3)));
        generator.blockStateCollector.accept(variants);
    }

    private void booleanState(BlockStateModelGenerator generator, Block block, BooleanProperty property, List<BlockStateVariant> onFalse, List<BlockStateVariant> onTrue) {
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(property).register(false, onFalse).register(true, onTrue)));
    }

    /** This just does a simple single model, not dependent on any states*/
    private void variantState(BlockStateModelGenerator generator, Block block) {
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, blockId(Registries.BLOCK.getId(block).getPath())));
    }

    private void generated(ItemModelGenerator generator, Item item) {
        generator.register(item, Models.GENERATED);
    }

    private void slab(BlockStateModelGenerator generator, Block block, Block slabBlock) {
        TextureMap textureMap = TextureMap.all(block);
        TextureMap textureMap2 = TextureMap.sideEnd(TextureMap.getSubId(slabBlock, "_side"), textureMap.getTexture(TextureKey.TOP));
        Identifier identifier = Models.SLAB.upload(slabBlock, textureMap2, generator.modelCollector);
        Identifier identifier2 = Models.SLAB_TOP.upload(slabBlock, textureMap2, generator.modelCollector);
        Identifier identifier3 = Models.CUBE_COLUMN.uploadWithoutVariant(slabBlock, "_double", textureMap2, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slabBlock, identifier, identifier2, identifier3));
    }

    private void generateStrewnLeavesItemModel(Item item, Identifier texture, ItemModelGenerator itemModelGenerator) {
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(texture), itemModelGenerator.writer);
    }

    private void generateStrewnLeaves(BlockStateModelGenerator blockStateModelGenerator, Block strewnLeavesBlock, Block leavesBlock, Identifier... models) {
        List<BlockStateVariant> flatVariants = allFlatModels(models);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(strewnLeavesBlock).coordinate(leavesPropertyVariants(flatVariants, strewnLeavesBlock, leavesBlock)));
        Stream.of(models).forEach(identifier -> {
            STREWN_LEAVES_MODEL.upload(identifier, TextureMap.of(TextureKey.TOP, identifier).put(TextureKey.PARTICLE, models[0]), blockStateModelGenerator.modelCollector);
        });
        STREWN_LEAVES_MODEL.upload(strewnLeavesBlock, TextureMap.of(TextureKey.TOP, models[0]).put(TextureKey.PARTICLE, models[0]), blockStateModelGenerator.modelCollector);
        Identifier leavesModelId = ModelIds.getBlockModelId(leavesBlock);
        LEAF_PILE_1_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_1"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_2_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_2"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_3_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_3"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_4_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_4"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_5_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_5"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_6_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_6"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_7_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_7"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
    }

    private BlockStateVariant modelVariantWithYRotation(Identifier model, VariantSettings.Rotation rotation) {
        return BlockStateVariant.create().put(VariantSettings.Y, rotation).put(VariantSettings.MODEL, model);
    }

    private List<BlockStateVariant> allFlatModels(Identifier... models) {
        ImmutableList.Builder<BlockStateVariant> builder = ImmutableList.builder();
        for (Identifier id : models) {
            builder.addAll(List.of(BlockStateModelGenerator.createModelVariantWithRandomHorizontalRotations(id)));
        }
        return builder.build();
    }

    private BlockStateVariantMap leavesPropertyVariants(List<BlockStateVariant> flatVariants, Block strewnLeavesBlock, Block leavesBlock) {
        return BlockStateVariantMap.create(StrewnLeavesBlock.LEAVES)
                .register(0, flatVariants)
                .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, id(strippedBlockId(leavesBlock) + "_pile_1")))
                .register(2, BlockStateVariant.create().put(VariantSettings.MODEL, id(strippedBlockId(leavesBlock) + "_pile_2")))
                .register(3, BlockStateVariant.create().put(VariantSettings.MODEL, id(strippedBlockId(leavesBlock) + "_pile_3")))
                .register(4, BlockStateVariant.create().put(VariantSettings.MODEL, id(strippedBlockId(leavesBlock) + "_pile_4")))
                .register(5, BlockStateVariant.create().put(VariantSettings.MODEL, id(strippedBlockId(leavesBlock) + "_pile_5")))
                .register(6, BlockStateVariant.create().put(VariantSettings.MODEL, id(strippedBlockId(leavesBlock) + "_pile_6")))
                .register(7, BlockStateVariant.create().put(VariantSettings.MODEL, id(strippedBlockId(leavesBlock) + "_pile_7")));
    }

    /** From vanilla {@link BlockStateModelGenerator#registerMushroomBlock}, modified for Aylyth usage */
    private void registerMushroomBlock(BlockStateModelGenerator generator, Block mushroomBlock, Identifier insideTexture) {
        Identifier modelId = Models.TEMPLATE_SINGLE_FACE.upload(mushroomBlock, TextureMap.texture(mushroomBlock), generator.modelCollector);
        generator.blockStateCollector.accept(MultipartBlockStateSupplier.create(mushroomBlock).with((When)When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId)).with((When)When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture)).with((When)When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.UP, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.DOWN, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)));
        generator.registerParentedItemModel(mushroomBlock, TexturedModel.CUBE_ALL.upload(mushroomBlock, "_inventory", generator.modelCollector));
    }

    private String strippedBlockId(Block block) {
        return ModelIds.getBlockModelId(block).getPath();
    }
}
