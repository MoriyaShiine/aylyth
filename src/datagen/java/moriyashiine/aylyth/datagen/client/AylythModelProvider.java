package moriyashiine.aylyth.datagen.client;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.LargeWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.types.PomegranateLeavesBlock;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.common.block.types.StrewnLeavesBlock;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.datagen.client.model.ItemModelOverrides;
import moriyashiine.aylyth.datagen.client.model.PerspectiveModelKeys;
import moriyashiine.aylyth.datagen.client.model.PerspectiveModels;
import moriyashiine.aylyth.datagen.common.AylythBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.MultipartBlockStateSupplier;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.data.client.When;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static net.minecraft.data.client.BlockStateModelGenerator.createModelVariantWithRandomHorizontalRotations;

public class AylythModelProvider extends FabricModelProvider {
    private static final Identifier STREWN_LEAVES_TEMPLATE = blockId("strewn_leaves_template");
    private static final Identifier LEAF_PILE_1_TEMPLATE = blockId("leaf_pile_1");
    private static final Identifier LEAF_PILE_2_TEMPLATE = blockId("leaf_pile_2");
    private static final Identifier LEAF_PILE_3_TEMPLATE = blockId("leaf_pile_3");
    private static final Identifier LEAF_PILE_4_TEMPLATE = blockId("leaf_pile_4");
    private static final Identifier LEAF_PILE_5_TEMPLATE = blockId("leaf_pile_5");
    private static final Identifier LEAF_PILE_6_TEMPLATE = blockId("leaf_pile_6");
    private static final Identifier LEAF_PILE_7_TEMPLATE = blockId("leaf_pile_7");

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
    private static Model parented(Identifier parent) {
        return new Model(Optional.of(parent), Optional.empty());
    }

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
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerFlowerPotPlant(AylythBlocks.MARIGOLD, AylythBlocks.MARIGOLD_POTTED, BlockStateModelGenerator.TintType.NOT_TINTED);
        generateStrewnLeaves(blockStateModelGenerator, AylythBlocks.OAK_STREWN_LEAVES, Blocks.OAK_LEAVES, id("block/fallen_oak_leaves_01"), id("block/fallen_oak_leaves_02"), id("block/fallen_oak_leaves_03"), id("block/fallen_oak_leaves_04"), id("block/fallen_oak_leaves_05"), id("block/fallen_oak_leaves_06"), id("block/fallen_oak_leaves_07"), id("block/fallen_oak_leaves_08"), id("block/fallen_oak_leaves_09"), id("block/fallen_oak_leaves_10"));
        generateStrewnLeaves(blockStateModelGenerator, AylythBlocks.YMPE_STREWN_LEAVES, AylythBlocks.YMPE_LEAVES, id("block/fallen_ympe_leaves_01"), id("block/fallen_ympe_leaves_02"));

        blockStateModelGenerator.registerLog(AylythBlocks.YMPE_STRIPPED_LOG).log(AylythBlocks.YMPE_STRIPPED_LOG).wood(AylythBlocks.YMPE_STRIPPED_WOOD);
        blockStateModelGenerator.registerLog(AylythBlocks.YMPE_LOG).log(AylythBlocks.YMPE_LOG).wood(AylythBlocks.YMPE_WOOD);
        blockStateModelGenerator.registerFlowerPotPlant(AylythBlocks.YMPE_SAPLING, AylythBlocks.YMPE_POTTED_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerCubeAllModelTexturePool(AylythBlocks.YMPE_PLANKS)
                .family(AylythBlockFamilies.YMPE);
        blockStateModelGenerator.registerHangingSign(AylythBlocks.YMPE_STRIPPED_LOG, AylythBlocks.YMPE_HANGING_SIGN, AylythBlocks.YMPE_WALL_HANGING_SIGN);
        variantState(blockStateModelGenerator, AylythBlocks.YMPE_LEAVES);

        blockStateModelGenerator.registerLog(AylythBlocks.POMEGRANATE_STRIPPED_LOG).log(AylythBlocks.POMEGRANATE_STRIPPED_LOG).wood(AylythBlocks.POMEGRANATE_STRIPPED_WOOD);
        blockStateModelGenerator.registerLog(AylythBlocks.POMEGRANATE_LOG).log(AylythBlocks.POMEGRANATE_LOG).wood(AylythBlocks.POMEGRANATE_WOOD);
        blockStateModelGenerator.registerFlowerPotPlant(AylythBlocks.POMEGRANATE_SAPLING, AylythBlocks.POMEGRANATE_POTTED_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerCubeAllModelTexturePool(AylythBlocks.POMEGRANATE_PLANKS).family(AylythBlockFamilies.POMEGRANATE);
        blockStateModelGenerator.registerHangingSign(AylythBlocks.POMEGRANATE_STRIPPED_LOG, AylythBlocks.POMEGRANATE_HANGING_SIGN, AylythBlocks.POMEGRANATE_WALL_HANGING_SIGN);
        fruitingLeaves(blockStateModelGenerator, AylythBlocks.POMEGRANATE_LEAVES, blockId("pomegranate_leaves"), blockId("pomegranate_leaves_fruiting_0"), blockId("pomegranate_leaves_fruiting_1"), blockId("pomegranate_leaves_fruiting_2"));
        TexturedModel.CUBE_ALL.upload(AylythBlocks.POMEGRANATE_LEAVES, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.registerLog(AylythBlocks.WRITHEWOOD_STRIPPED_LOG).log(AylythBlocks.WRITHEWOOD_STRIPPED_LOG).wood(AylythBlocks.WRITHEWOOD_STRIPPED_WOOD);
        blockStateModelGenerator.registerLog(AylythBlocks.WRITHEWOOD_LOG).log(AylythBlocks.WRITHEWOOD_LOG).wood(AylythBlocks.WRITHEWOOD_WOOD);
        blockStateModelGenerator.registerFlowerPotPlant(AylythBlocks.WRITHEWOOD_SAPLING, AylythBlocks.WRITHEWOOD_POTTED_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerCubeAllModelTexturePool(AylythBlocks.WRITHEWOOD_PLANKS)
                .family(AylythBlockFamilies.WRITHEWOOD);
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
        smallWoodyGrowth(blockStateModelGenerator.blockStateCollector, AylythBlocks.SMALL_WOODY_GROWTH);
        largeWoodyGrowth(blockStateModelGenerator.blockStateCollector, AylythBlocks.LARGE_WOODY_GROWTH);
        largeWoodyGrowth(blockStateModelGenerator.blockStateCollector, AylythBlocks.WOODY_GROWTH_CACHE);
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
        blockStateModelGenerator.blockStateCollector.accept(soulHearthStates(AylythBlocks.SOUL_HEARTH, blockId("soul_hearth_upper"), blockId("soul_hearth_lower"), blockId("soul_hearth_charged_lower")));
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
        itemModelGenerator.register(AylythItems.WREATHED_HIND_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(AylythItems.FAUNAYLYTHIAN_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(AylythItems.YMPEMOULD_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(AylythItems.BONEFLY_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(AylythItems.TULPA_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(AylythItems.POMEGRANATE_CASSETTE, Models.GENERATED);
        itemModelGenerator.register(AylythItems.BLIGHTED_THORNS, Models.GENERATED);
        itemModelGenerator.register(AylythItems.SOUL_HEARTH, parented(blockId("soul_hearth_item")));

        itemModelGenerator.register(AylythItems.THORN_FLECHETTE, Models.GENERATED);
        itemModelGenerator.register(AylythItems.BLIGHTED_THORN_FLECHETTE, Models.GENERATED);
        itemModelGenerator.register(AylythItems.LANCEOLATE_DAGGER, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.YMPE_LANCE, BUILTIN);

        itemModelGenerator.register(AylythItems.VAMPIRIC_SWORD, "_gui", Models.GENERATED);
        itemModelGenerator.register(AylythItems.VAMPIRIC_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.VAMPIRIC_AXE, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.VAMPIRIC_HOE, Models.HANDHELD);

        itemModelGenerator.register(AylythItems.BLIGHTED_SWORD, "_gui", Models.GENERATED);
        itemModelGenerator.register(AylythItems.BLIGHTED_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.BLIGHTED_AXE, Models.HANDHELD);
        itemModelGenerator.register(AylythItems.BLIGHTED_HOE, Models.HANDHELD);

        registerBig(itemModelGenerator, AylythItems.YMPE_GLAIVE);
        registerBig(itemModelGenerator, AylythItems.YMPE_FLAMBERGE);
        registerBig(itemModelGenerator, AylythItems.YMPE_SCYTHE);
        registerBig(itemModelGenerator, AylythItems.VAMPIRIC_SWORD);
        registerBig(itemModelGenerator, AylythItems.BLIGHTED_SWORD);
        PerspectiveModels.BIG_HANDHELD.resolver()
                .with(PerspectiveModelKeys.GUI, ModelIds.getItemModelId(AylythItems.YMPE_LANCE).withSuffixedPath("_gui"))
                .with(PerspectiveModelKeys.HANDHELD, ModelIds.getItemModelId(AylythItems.YMPE_LANCE).withSuffixedPath("_handheld"))
                .upload(ModelIds.getItemModelId(AylythItems.YMPE_LANCE).withSuffixedPath("_spear"), itemModelGenerator.writer);

        registerFlask(itemModelGenerator, AylythItems.NEPHRITE_FLASK);
        registerFlask(itemModelGenerator, AylythItems.DARK_NEPHRITE_FLASK);
        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola"), TextureMap.layered(Aylyth.id("item/blight_potion"), Aylyth.id("item/blight_potion")), itemModelGenerator.writer);
        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola_splash"), TextureMap.layered(Aylyth.id("item/blight_potion_splash"), Aylyth.id("item/blight_potion_splash")), itemModelGenerator.writer);
        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola_lingering"), TextureMap.layered(Aylyth.id("item/blight_potion_lingering"), Aylyth.id("item/blight_potion_lingering")), itemModelGenerator.writer);
    }

    private void registerBig(ItemModelGenerator generator, Item item) {
        PerspectiveModels.BIG_HANDHELD.resolver()
                .with(PerspectiveModelKeys.GUI, ModelIds.getItemModelId(item).withSuffixedPath("_gui"))
                .with(PerspectiveModelKeys.HANDHELD, ModelIds.getItemModelId(item).withSuffixedPath("_handheld"))
                .upload(ModelIds.getItemModelId(item), generator.writer);
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
        Identifier id = ModelIds.getItemModelId(flask.asItem());
        ItemModelOverrides.Builder builder = ItemModelOverrides.builder();
        for (int i = 1; i <= 6; i++) {
            generator.register(flask.asItem(), "_" + i + "_charges", Models.GENERATED);
            builder.overrideBuilder(id.withSuffixedPath("_%s_charges".formatted(i)))
                    .addPredicate(Aylyth.id("uses"), i / 6f)
                    .build();
        }
        Models.GENERATED.upload(id, TextureMap.layer0(flask.asItem()), generator.writer, chain(Models.GENERATED::createJson, builder.build()));
    }

    private void generateWoodBlock(BlockStateModelGenerator generator, Block woodBlock, String texturePath) {
        TextureMap textureMap = new TextureMap();
        textureMap.put(TextureKey.SIDE, id(texturePath));
        textureMap.put(TextureKey.END, textureMap.getTexture(TextureKey.SIDE));
        Identifier identifier = Models.CUBE_COLUMN.upload(woodBlock, textureMap, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(woodBlock, identifier));
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

    /** This just does a simple single model, not dependent on any states*/
    private void variantState(BlockStateModelGenerator generator, Block block) {
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, ModelIds.getBlockModelId(block)));
    }

    private void generateStrewnLeavesItemModel(Item item, Identifier texture, ItemModelGenerator itemModelGenerator) {
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(texture), itemModelGenerator.writer);
    }

    private void generateStrewnLeaves(BlockStateModelGenerator blockStateModelGenerator, Block strewnLeavesBlock, Block leavesBlock, Identifier... models) {
        List<BlockStateVariant> flatVariants = allFlatModels(models);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(strewnLeavesBlock).coordinate(leavesPropertyVariants(flatVariants, leavesBlock)));
        Stream.of(models).forEach(identifier -> {
            STREWN_LEAVES_MODEL.upload(identifier, TextureMap.of(TextureKey.TOP, identifier).put(TextureKey.PARTICLE, models[0]), blockStateModelGenerator.modelCollector);
        });
        STREWN_LEAVES_MODEL.upload(strewnLeavesBlock, TextureMap.of(TextureKey.TOP, models[0]).put(TextureKey.PARTICLE, models[0]), blockStateModelGenerator.modelCollector);
        Identifier leavesModelId = ModelIds.getBlockModelId(leavesBlock);
        LEAF_PILE_1_MODEL.upload(id(leavesModelId.getPath() + "_pile_1"), TextureMap.of(TextureKey.ALL, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_2_MODEL.upload(id(leavesModelId.getPath() + "_pile_2"), TextureMap.of(TextureKey.ALL, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_3_MODEL.upload(id(leavesModelId.getPath() + "_pile_3"), TextureMap.of(TextureKey.ALL, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_4_MODEL.upload(id(leavesModelId.getPath() + "_pile_4"), TextureMap.of(TextureKey.ALL, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_5_MODEL.upload(id(leavesModelId.getPath() + "_pile_5"), TextureMap.of(TextureKey.ALL, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_6_MODEL.upload(id(leavesModelId.getPath() + "_pile_6"), TextureMap.of(TextureKey.ALL, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_7_MODEL.upload(id(leavesModelId.getPath() + "_pile_7"), TextureMap.of(TextureKey.ALL, leavesModelId), blockStateModelGenerator.modelCollector);
    }

    private List<BlockStateVariant> allFlatModels(Identifier... models) {
        ImmutableList.Builder<BlockStateVariant> builder = ImmutableList.builder();
        for (Identifier id : models) {
            builder.addAll(List.of(BlockStateModelGenerator.createModelVariantWithRandomHorizontalRotations(id)));
        }
        return builder.build();
    }

    private BlockStateVariantMap leavesPropertyVariants(List<BlockStateVariant> flatVariants, Block leavesBlock) {
        return BlockStateVariantMap.create(StrewnLeavesBlock.LEAVES)
                .registerVariants(integer -> {
                    if (integer == 0) {
                        return flatVariants;
                    }

                    return ObjectArrayList.of(
                            BlockStateVariant.create()
                                    .put(VariantSettings.MODEL, id(ModelIds.getBlockModelId(leavesBlock).getPath()).withSuffixedPath("_pile_" + integer))
                    );
                });
    }

    /** From vanilla {@link BlockStateModelGenerator#registerMushroomBlock}, modified for Aylyth usage */
    private void registerMushroomBlock(BlockStateModelGenerator generator, Block mushroomBlock, Identifier insideTexture) {
        Identifier modelId = Models.TEMPLATE_SINGLE_FACE.upload(mushroomBlock, TextureMap.texture(mushroomBlock), generator.modelCollector);
        generator.blockStateCollector.accept(MultipartBlockStateSupplier.create(mushroomBlock).with((When)When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId)).with((When)When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.UP, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.DOWN, true), BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true)).with((When)When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture)).with((When)When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.UP, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, false)).with((When)When.create().set(Properties.DOWN, false), BlockStateVariant.create().put(VariantSettings.MODEL, insideTexture).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, false)));
        generator.registerParentedItemModel(mushroomBlock, TexturedModel.CUBE_ALL.upload(mushroomBlock, "_inventory", generator.modelCollector));
    }

    /**
     * Allows chaining multiple factories together, particularly helpful for the item display and model predicate systems.
     * @param factories The factories to combine
     * @return The combined factory which runs all factories and merges the result
     */
    private Model.JsonFactory chain(Model.JsonFactory... factories) {
        if (factories.length == 1) {
            return factories[0];
        }
        return (id, textures) -> {
            JsonObject finalObj = new JsonObject();
            for (Model.JsonFactory factory : factories) {
                for (Map.Entry<String, JsonElement> entry : factory.create(id, textures).entrySet()) {
                    finalObj.add(entry.getKey(), entry.getValue());
                }
            }
            return finalObj;
        };
    }
}
