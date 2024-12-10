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
import moriyashiine.aylyth.datagen.client.model.ModelDisplayTransforms;
import moriyashiine.aylyth.datagen.client.model.ModelDisplayType;
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
import net.minecraft.data.client.SimpleModelSupplier;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.data.client.When;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static net.minecraft.data.client.BlockStateModelGenerator.createModelVariantWithRandomHorizontalRotations;

public class AylythModelProvider extends FabricModelProvider {
    private static final Model STREWN_LEAVES_TEMPLATE = new Model(Optional.of(blockId("strewn_leaves_template")), Optional.empty(), TextureKey.TOP);
    private static final Model BRANCH_TEMPLATE = new Model(Optional.of(blockId("branch_template")), Optional.empty(), TextureKey.SIDE);
    private static final Model LEAF_PILE_1 = new Model(Optional.of(blockId("leaf_pile_1")), Optional.empty(), TextureKey.ALL);
    private static final Model LEAF_PILE_2 = new Model(Optional.of(blockId("leaf_pile_2")), Optional.empty(), TextureKey.ALL);
    private static final Model LEAF_PILE_3 = new Model(Optional.of(blockId("leaf_pile_3")), Optional.empty(), TextureKey.ALL);
    private static final Model LEAF_PILE_4 = new Model(Optional.of(blockId("leaf_pile_4")), Optional.empty(), TextureKey.ALL);
    private static final Model LEAF_PILE_5 = new Model(Optional.of(blockId("leaf_pile_5")), Optional.empty(), TextureKey.ALL);
    private static final Model LEAF_PILE_6 = new Model(Optional.of(blockId("leaf_pile_6")), Optional.empty(), TextureKey.ALL);
    private static final Model LEAF_PILE_7 = new Model(Optional.of(blockId("leaf_pile_7")), Optional.empty(), TextureKey.ALL);

    private static final Model BUILTIN = new Model(Optional.of(new Identifier("builtin/entity")), Optional.empty());
    private static final Model SPAWN_EGG = new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty());
    private static final Model HANDHELD_ROTATED = new Model(Optional.of(id("item/handheld_rotated")), Optional.empty(), TextureKey.LAYER0);

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
        generator.registerParentedItemModel(AylythBlocks.DARK_OAK_SEEP, blockId("dark_oak_seep_log_single"));
        generator.registerParentedItemModel(AylythBlocks.FRUIT_BEARING_YMPE_LOG, blockId("fruit_bearing_ympe_log/4"));
        registerSimpleParented(generator, AylythBlocks.GRIPWEED);
        generator.registerParentedItemModel(AylythBlocks.OAK_SEEP, blockId("seep_log_single"));
        generator.registerParentedItemModel(AylythBlocks.SEEPING_WOOD_SEEP, blockId("seeping_wood_seep_log_single"));
        generator.registerParentedItemModel(AylythBlocks.SPRUCE_SEEP, blockId("spruce_seep_log_single"));
        generator.registerParentedItemModel(AylythBlocks.YMPE_SEEP, blockId("ympe_seep_log_single"));
        registerFlowerPotPlant(generator, AylythBlocks.MARIGOLD, AylythBlocks.MARIGOLD_POTTED, BlockStateModelGenerator.TintType.NOT_TINTED);
        generateStrewnLeaves(generator, AylythBlocks.OAK_STREWN_LEAVES, Blocks.OAK_LEAVES, blockId("fallen_oak_leaves_01"), blockId("fallen_oak_leaves_02"), blockId("fallen_oak_leaves_03"), blockId("fallen_oak_leaves_04"), blockId("fallen_oak_leaves_05"), blockId("fallen_oak_leaves_06"), blockId("fallen_oak_leaves_07"), blockId("fallen_oak_leaves_08"), blockId("fallen_oak_leaves_09"), blockId("fallen_oak_leaves_10"));
        generateStrewnLeaves(generator, AylythBlocks.YMPE_STREWN_LEAVES, AylythBlocks.YMPE_LEAVES, blockId("fallen_ympe_leaves_01"), blockId("fallen_ympe_leaves_02"));

        generator.registerLog(AylythBlocks.YMPE_STRIPPED_LOG).log(AylythBlocks.YMPE_STRIPPED_LOG).wood(AylythBlocks.YMPE_STRIPPED_WOOD);
        generator.registerLog(AylythBlocks.YMPE_LOG).log(AylythBlocks.YMPE_LOG).wood(AylythBlocks.YMPE_WOOD);
        generator.registerFlowerPotPlant(AylythBlocks.YMPE_SAPLING, AylythBlocks.YMPE_POTTED_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerCubeAllModelTexturePool(AylythBlocks.YMPE_PLANKS)
                .family(AylythBlockFamilies.YMPE);
        generator.registerHangingSign(AylythBlocks.YMPE_STRIPPED_LOG, AylythBlocks.YMPE_HANGING_SIGN, AylythBlocks.YMPE_WALL_HANGING_SIGN);
        variantState(generator, AylythBlocks.YMPE_LEAVES);

        generator.registerLog(AylythBlocks.POMEGRANATE_STRIPPED_LOG).log(AylythBlocks.POMEGRANATE_STRIPPED_LOG).wood(AylythBlocks.POMEGRANATE_STRIPPED_WOOD);
        generator.registerLog(AylythBlocks.POMEGRANATE_LOG).log(AylythBlocks.POMEGRANATE_LOG).wood(AylythBlocks.POMEGRANATE_WOOD);
        generator.registerFlowerPotPlant(AylythBlocks.POMEGRANATE_SAPLING, AylythBlocks.POMEGRANATE_POTTED_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerCubeAllModelTexturePool(AylythBlocks.POMEGRANATE_PLANKS).family(AylythBlockFamilies.POMEGRANATE);
        generator.registerHangingSign(AylythBlocks.POMEGRANATE_STRIPPED_LOG, AylythBlocks.POMEGRANATE_HANGING_SIGN, AylythBlocks.POMEGRANATE_WALL_HANGING_SIGN);
        fruitingLeaves(generator, AylythBlocks.POMEGRANATE_LEAVES, blockId("pomegranate_leaves"), blockId("pomegranate_leaves_fruiting_0"), blockId("pomegranate_leaves_fruiting_1"), blockId("pomegranate_leaves_fruiting_2"));
        TexturedModel.CUBE_ALL.upload(AylythBlocks.POMEGRANATE_LEAVES, generator.modelCollector);

        generator.registerLog(AylythBlocks.WRITHEWOOD_STRIPPED_LOG).log(AylythBlocks.WRITHEWOOD_STRIPPED_LOG).wood(AylythBlocks.WRITHEWOOD_STRIPPED_WOOD);
        generator.registerLog(AylythBlocks.WRITHEWOOD_LOG).log(AylythBlocks.WRITHEWOOD_LOG).wood(AylythBlocks.WRITHEWOOD_WOOD);
        generator.registerFlowerPotPlant(AylythBlocks.WRITHEWOOD_SAPLING, AylythBlocks.WRITHEWOOD_POTTED_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerCubeAllModelTexturePool(AylythBlocks.WRITHEWOOD_PLANKS)
                .family(AylythBlockFamilies.WRITHEWOOD);
        generator.registerHangingSign(AylythBlocks.WRITHEWOOD_STRIPPED_LOG, AylythBlocks.WRITHEWOOD_HANGING_SIGN, AylythBlocks.WRITHEWOOD_WALL_HANGING_SIGN);
        variantState(generator, AylythBlocks.WRITHEWOOD_LEAVES);

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

        generator.registerTintableCrossBlockState(AylythBlocks.GIRASOL_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        TextureMap girasolMap = TextureMap.plant(AylythBlocks.GIRASOL_SAPLING);
        Identifier pottedSaplingId = BlockStateModelGenerator.TintType.NOT_TINTED.getFlowerPotCrossModel().upload(AylythBlocks.GIRASOL_SAPLING_POTTED, girasolMap, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(AylythBlocks.GIRASOL_SAPLING_POTTED, pottedSaplingId));
        variantState(generator, AylythBlocks.BLACK_WELL);

        generator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.SAPSTONE, blockId("sapstone")));
        generator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.AMBER_SAPSTONE, blockId("amber_sapstone")));
        generator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.LIGNITE_SAPSTONE, blockId("lignite_sapstone")));
        generator.blockStateCollector.accept(sapstoneBlockStates(AylythBlocks.OPALESCENT_SAPSTONE, blockId("opalescent_sapstone")));
        generator.blockStateCollector.accept(soulHearthStates(AylythBlocks.SOUL_HEARTH, blockId("soul_hearth_upper"), blockId("soul_hearth_lower"), blockId("soul_hearth_charged_lower")));

        registerBranch(generator, AylythBlocks.DARK_OAK_BRANCH);
        registerBranch(generator, AylythBlocks.BARE_DARK_OAK_BRANCH);
        registerBranch(generator, AylythBlocks.WRITHEWOOD_BRANCH);
        registerBranch(generator, AylythBlocks.BARE_WRITHEWOOD_BRANCH);
        registerBranch(generator, AylythBlocks.YMPE_BRANCH);
        registerBranch(generator, AylythBlocks.BARE_YMPE_BRANCH);

        {
            var var1 = Models.CUBE_BOTTOM_TOP.upload(AylythBlocks.DARK_PODZOL, TextureMap.topBottom(blockId("dark_podzol_top"), ModelIds.getBlockModelId(Blocks.DIRT)).put(TextureKey.SIDE, blockId("dark_podzol_side")), generator.modelCollector);
            var var2 = Models.CUBE_BOTTOM_TOP.upload(ModelIds.getBlockSubModelId(AylythBlocks.DARK_PODZOL, "_1"), TextureMap.topBottom(blockId("dark_podzol_top_1"), new Identifier("block/dirt")).put(TextureKey.SIDE, blockId("dark_podzol_side")), generator.modelCollector);
            var var3 = Models.CUBE_BOTTOM_TOP.upload(ModelIds.getBlockSubModelId(AylythBlocks.DARK_PODZOL, "_2"), TextureMap.topBottom(blockId("dark_podzol_top_2"), new Identifier("block/dirt")).put(TextureKey.SIDE, blockId("dark_podzol_side")), generator.modelCollector);
            var var4 = Models.CUBE_BOTTOM_TOP.upload(ModelIds.getBlockSubModelId(AylythBlocks.DARK_PODZOL, "_3"), TextureMap.topBottom(blockId("dark_podzol_top_3"), new Identifier("block/dirt")).put(TextureKey.SIDE, blockId("dark_podzol_side")), generator.modelCollector);
            var var5 = Models.CUBE_BOTTOM_TOP.upload(ModelIds.getBlockSubModelId(AylythBlocks.DARK_PODZOL, "_4"), TextureMap.topBottom(blockId("dark_podzol_top_4"), new Identifier("block/dirt")).put(TextureKey.SIDE, blockId("dark_podzol_side")), generator.modelCollector);
            generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(AylythBlocks.DARK_PODZOL)
                    .coordinate(BlockStateVariantMap.create(Properties.SNOWY)
                            .register(false, List.of(BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(Blocks.GRASS_BLOCK, "_snow"))))
                            .register(true, ImmutableList.<BlockStateVariant>builder()
                                    .addAll(Arrays.asList(createModelVariantWithRandomHorizontalRotations(var1)))
                                    .addAll(Arrays.asList(createModelVariantWithRandomHorizontalRotations(var2)))
                                    .addAll(Arrays.asList(createModelVariantWithRandomHorizontalRotations(var3)))
                                    .addAll(Arrays.asList(createModelVariantWithRandomHorizontalRotations(var4)))
                                    .addAll(Arrays.asList(createModelVariantWithRandomHorizontalRotations(var5)))
                                    .build()
                            )
                    )
            );
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generateStrewnLeavesItemModel(AylythItems.OAK_STREWN_LEAVES, blockId("fallen_oak_leaves_01"), generator);
        generateStrewnLeavesItemModel(AylythItems.YMPE_STREWN_LEAVES, blockId("fallen_ympe_leaves_01"), generator);
        generator.register(AylythItems.AYLYTHIAN_HEART, Models.GENERATED);
        generator.register(AylythItems.AYLYTHIAN_SPAWN_EGG, SPAWN_EGG);
        generator.register(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
        generator.register(AylythItems.BARK, Models.GENERATED);
        generator.register(AylythItems.CORIC_SEED, Models.GENERATED);
        generator.register(AylythItems.ELDER_AYLYTHIAN_SPAWN_EGG, SPAWN_EGG);
        generator.register(AylythItems.ESSTLINE, Models.GENERATED);
        generator.register(AylythItems.GHOSTCAP_MUSHROOM, Models.GENERATED);
        generator.register(AylythItems.JACK_O_LANTERN_MUSHROOM, Models.GENERATED);
        Models.GENERATED.upload(ModelIds.getItemModelId(AylythItems.MARIGOLD), TextureMap.layer0(AylythBlocks.MARIGOLD), generator.writer);
        generator.register(AylythItems.NEPHRITE, Models.GENERATED);
        Models.GENERATED_THREE_LAYERS.upload(
                ModelIds.getItemModelId(AylythItems.NYSIAN_GRAPE_VINE),
                TextureMap.layered(blockId("nysian_grape_vine/vine"), blockId("nysian_grape_vine/leaves"), blockId("nysian_grape_vine/details")),
                generator.writer
        );
        generator.register(AylythItems.NYSIAN_GRAPES, Models.GENERATED);
        generator.register(AylythItems.PILOT_LIGHT_SPAWN_EGG, SPAWN_EGG);
        generator.register(AylythItems.SCION_SPAWN_EGG, SPAWN_EGG);
        Models.GENERATED.upload(
                ModelIds.getItemModelId(AylythItems.SHUCKED_YMPE_FRUIT),
                TextureMap.layer0(AylythItems.SHUCKED_YMPE_FRUIT),
                wrapAndChain(
                        generator.writer,
                        ItemModelOverrides.builder()
                                .overrideBuilder(id("item/shucked_ympe_fruit_variant"))
                                .addPredicate(id("variant"), 1)
                                .build().finish()
                )
        );
        Models.GENERATED.upload(id("item/shucked_ympe_fruit_variant"), TextureMap.layer0(id("item/shucked_ympe_fruit_variant")), generator.writer);
        generator.register(AylythItems.YMPE_DAGGER, Models.HANDHELD);
        generator.register(AylythItems.YMPE_EFFIGY, HANDHELD_ROTATED);
        generator.register(AylythItems.YMPE_FLAMBERGE, "_gui", Models.HANDHELD);
        generator.register(AylythItems.YMPE_FRUIT, Models.GENERATED);
        generator.register(AylythItems.YMPE_GLAIVE, "_gui", Models.HANDHELD);
        generator.register(AylythItems.YMPE_LANCE, "_gui", Models.HANDHELD);
        generator.register(AylythItems.YMPE_MUSH, Models.GENERATED);
        generator.register(AylythItems.YMPE_SCYTHE, "_gui", Models.HANDHELD);
        generator.register(AylythItems.POMEGRANATE, Models.GENERATED);
        generator.register(AylythItems.GHOSTCAP_MUSHROOM_SPORES, Models.GENERATED);
        generator.register(AylythItems.YMPE_BOAT, Models.GENERATED);
        generator.register(AylythItems.YMPE_CHEST_BOAT, Models.GENERATED);
        generator.register(AylythItems.POMEGRANATE_BOAT, Models.GENERATED);
        generator.register(AylythItems.POMEGRANATE_CHEST_BOAT, Models.GENERATED);
        generator.register(AylythItems.WRITHEWOOD_BOAT, Models.GENERATED);
        generator.register(AylythItems.WRITHEWOOD_CHEST_BOAT, Models.GENERATED);
        generator.register(AylythItems.DEBUG_WAND, Models.HANDHELD);
        generator.register(AylythItems.WRONGMEAT, Models.GENERATED);
        generator.register(AylythItems.NEPHRITE_HEART, Models.GENERATED);
        generator.register(AylythItems.YHONDYTH_HEART, Models.GENERATED);
        generator.register(AylythItems.GIRASOL_SEED, Models.GENERATED);
        generator.register(AylythItems.LARGE_WOODY_GROWTH, Models.GENERATED);
        generator.register(AylythItems.WOODY_GROWTH_CACHE, BUILTIN);
        generator.register(AylythItems.SMALL_WOODY_GROWTH, Models.GENERATED);
        generator.register(AylythItems.YMPE_CUIRASS, Models.GENERATED);

        generator.register(AylythItems.WREATHED_HIND_SPAWN_EGG, SPAWN_EGG);
        generator.register(AylythItems.FAUNAYLYTHIAN_SPAWN_EGG, SPAWN_EGG);
        generator.register(AylythItems.YMPEMOULD_SPAWN_EGG, SPAWN_EGG);
        generator.register(AylythItems.BONEFLY_SPAWN_EGG, SPAWN_EGG);
        generator.register(AylythItems.TULPA_SPAWN_EGG, SPAWN_EGG);

        generator.register(AylythItems.POMEGRANATE_CASSETTE, Models.GENERATED);
        generator.register(AylythItems.BLIGHTED_THORNS, Models.GENERATED);

        generator.register(AylythItems.THORN_FLECHETTE, Models.GENERATED);
        generator.register(AylythItems.BLIGHTED_THORN_FLECHETTE, Models.GENERATED);
        generator.register(AylythItems.LANCEOLATE_DAGGER, Models.HANDHELD);
        generator.register(AylythItems.YMPE_LANCE, BUILTIN);

        generator.register(AylythItems.NEPHRITE_AXE, Models.HANDHELD);
        generator.register(AylythItems.NEPHRITE_HOE, Models.HANDHELD);
        generator.register(AylythItems.NEPHRITE_PICKAXE, Models.HANDHELD);
        generator.register(AylythItems.NEPHRITE_SHOVEL, Models.HANDHELD);
        generator.register(AylythItems.NEPHRITE_SWORD, Models.HANDHELD);

        generator.register(AylythItems.VAMPIRIC_SWORD, "_gui", Models.GENERATED);
        generator.register(AylythItems.VAMPIRIC_PICKAXE, Models.HANDHELD);
        generator.register(AylythItems.VAMPIRIC_AXE, Models.HANDHELD);
        generator.register(AylythItems.VAMPIRIC_HOE, Models.HANDHELD);

        generator.register(AylythItems.BLIGHTED_SWORD, "_gui", Models.GENERATED);
        generator.register(AylythItems.BLIGHTED_PICKAXE, Models.HANDHELD);
        generator.register(AylythItems.BLIGHTED_AXE, Models.HANDHELD);
        generator.register(AylythItems.BLIGHTED_HOE, Models.HANDHELD);

        registerBig(generator, AylythItems.YMPE_GLAIVE);
        registerBig(generator, AylythItems.YMPE_FLAMBERGE);
        registerBig(generator, AylythItems.YMPE_SCYTHE);
        registerBig(generator, AylythItems.VAMPIRIC_SWORD);
        registerBig(generator, AylythItems.BLIGHTED_SWORD);
        PerspectiveModels.BIG_HANDHELD.resolver()
                .with(PerspectiveModelKeys.GUI, ModelIds.getItemModelId(AylythItems.YMPE_LANCE).withSuffixedPath("_gui"))
                .with(PerspectiveModelKeys.HANDHELD, ModelIds.getItemModelId(AylythItems.YMPE_LANCE).withSuffixedPath("_handheld"))
                .upload(ModelIds.getItemModelId(AylythItems.YMPE_LANCE).withSuffixedPath("_spear"), generator.writer);

        registerFlask(generator, AylythItems.NEPHRITE_FLASK);
        registerFlask(generator, AylythItems.DARK_NEPHRITE_FLASK);
        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola"), TextureMap.layered(Aylyth.id("item/blight_potion"), Aylyth.id("item/blight_potion")), generator.writer);
        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola_splash"), TextureMap.layered(Aylyth.id("item/blight_potion_splash"), Aylyth.id("item/blight_potion_splash")), generator.writer);
        Models.GENERATED_TWO_LAYERS.upload(Aylyth.id("item/coker_cola_lingering"), TextureMap.layered(Aylyth.id("item/blight_potion_lingering"), Aylyth.id("item/blight_potion_lingering")), generator.writer);

        registerSimpleParented(
                ModelIds.getItemModelId(AylythItems.WRITHEWOOD_LEAVES),
                ModelIds.getBlockModelId(AylythBlocks.WRITHEWOOD_LEAVES),
                wrapAndChain(generator.writer,
                        ModelDisplayTransforms.block()
                                .transformBuilder(ModelDisplayType.GUI)
                                .rotation(30, 225, 0)
                                .translation(0, 1, 0)
                                .scale(0.475f)
                                .build()
                                .finish()
                )
        );
        registerSimpleParented(
                ModelIds.getItemModelId(AylythItems.AYLYTH_BUSH),
                ModelIds.getBlockModelId(AylythBlocks.AYLYTH_BUSH),
                wrapAndChain(generator.writer,
                        ModelDisplayTransforms.block()
                                .transformBuilder(ModelDisplayType.GUI)
                                .rotation(30, 225, 0)
                                .scale(0.5f)
                                .build()
                                .finish()
                )
        );
        registerSimpleParented(
                ModelIds.getItemModelId(AylythItems.ANTLER_SHOOTS),
                ModelIds.getBlockModelId(AylythBlocks.ANTLER_SHOOTS),
                wrapAndChain(generator.writer,
                        ModelDisplayTransforms.block()
                                .transformBuilder(ModelDisplayType.GUI)
                                .rotation(30, 225, 0)
                                .translation(0, -2, 0)
                                .scale(0.55f)
                                .build()
                                .finish()
                ));
        registerSimpleParented(
                ModelIds.getItemModelId(AylythItems.BLACK_WELL),
                blockId("black_well_inventory"),
                wrapAndChain(generator.writer,
                        ModelDisplayTransforms.block()
                                .transformBuilder(ModelDisplayType.GUI)
                                .rotation(30, 225, 0)
                                .scale(0.55f)
                                .build()
                                .finish()
                ));
        registerSimpleParented(
                ModelIds.getItemModelId(AylythItems.VITAL_THURIBLE),
                ModelIds.getBlockModelId(AylythBlocks.VITAL_THURIBLE),
                wrapAndChain(generator.writer,
                        ModelDisplayTransforms.builder()
                                .transformBuilder(ModelDisplayType.GUI)
                                .rotation(25, -35, 0)
                                .translation(0, -1, 0)
                                .scale(0.5f)
                                .build()
                                .transformBuilder(ModelDisplayType.GROUND)
                                .scale(0.5f)
                                .build()
                                .transformBuilder(ModelDisplayType.FIRST_PERSON_LEFT_HAND)
                                .translation(0, -2.75f, 0)
                                .scale(0.5f)
                                .build()
                                .transformBuilder(ModelDisplayType.FIRST_PERSON_RIGHT_HAND)
                                .translation(0, -2.75f, 0)
                                .scale(0.5f)
                                .build()
                                .transformBuilder(ModelDisplayType.THIRD_PERSON_LEFT_HAND)
                                .translation(0, -2.75f, 0)
                                .scale(0.35f)
                                .build()
                                .transformBuilder(ModelDisplayType.THIRD_PERSON_RIGHT_HAND)
                                .translation(0, -2.75f, 0)
                                .scale(0.35f)
                                .build()
                                .finish()
                ));

        Models.GENERATED.upload(ModelIds.getItemModelId(AylythItems.DARK_OAK_BRANCH), TextureMap.layer0(AylythBlocks.DARK_OAK_BRANCH), generator.writer);
        Models.GENERATED.upload(ModelIds.getItemModelId(AylythItems.BARE_DARK_OAK_BRANCH), TextureMap.layer0(AylythBlocks.BARE_DARK_OAK_BRANCH), generator.writer);
        Models.GENERATED.upload(ModelIds.getItemModelId(AylythItems.WRITHEWOOD_BRANCH), TextureMap.layer0(AylythBlocks.WRITHEWOOD_BRANCH), generator.writer);
        Models.GENERATED.upload(ModelIds.getItemModelId(AylythItems.BARE_WRITHEWOOD_BRANCH), TextureMap.layer0(AylythBlocks.BARE_WRITHEWOOD_BRANCH), generator.writer);
        Models.GENERATED.upload(ModelIds.getItemModelId(AylythItems.YMPE_BRANCH), TextureMap.layer0(AylythBlocks.YMPE_BRANCH), generator.writer);
        Models.GENERATED.upload(ModelIds.getItemModelId(AylythItems.BARE_YMPE_BRANCH), TextureMap.layer0(AylythBlocks.BARE_YMPE_BRANCH), generator.writer);
    }

    // copy without the regular cross state and model registration
    private void registerFlowerPotPlant(BlockStateModelGenerator generator, Block plantBlock, Block flowerPotBlock, BlockStateModelGenerator.TintType tintType) {
        TextureMap textureMap = TextureMap.plant(plantBlock);
        Identifier identifier = tintType.getFlowerPotCrossModel().upload(flowerPotBlock, textureMap, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(flowerPotBlock, identifier));
    }

    private void registerSimpleParented(BlockStateModelGenerator generator, Block block) {
        generator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
    }

    private void registerBig(ItemModelGenerator generator, Item item) {
        PerspectiveModels.BIG_HANDHELD.resolver()
                .with(PerspectiveModelKeys.GUI, ModelIds.getItemModelId(item).withSuffixedPath("_gui"))
                .with(PerspectiveModelKeys.HANDHELD, ModelIds.getItemModelId(item).withSuffixedPath("_handheld"))
                .upload(ModelIds.getItemModelId(item), generator.writer);
    }

    private void registerBranch(BlockStateModelGenerator generator, Block block) {
        BRANCH_TEMPLATE.upload(block, TextureMap.of(TextureKey.SIDE, ModelIds.getBlockModelId(block)), generator.modelCollector);
        generator.registerNorthDefaultHorizontalRotation(block);
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
        Models.GENERATED.upload(id, TextureMap.layer0(flask.asItem()), wrapAndChain(generator.writer, builder.finish()));
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
            STREWN_LEAVES_TEMPLATE.upload(identifier, TextureMap.of(TextureKey.TOP, identifier), blockStateModelGenerator.modelCollector);
        });
        STREWN_LEAVES_TEMPLATE.upload(strewnLeavesBlock, TextureMap.of(TextureKey.TOP, models[0]), blockStateModelGenerator.modelCollector);

        Identifier leavesModelId = ModelIds.getBlockModelId(leavesBlock);
        TextureMap pileMap = TextureMap.of(TextureKey.ALL, leavesModelId);
        LEAF_PILE_1.upload(id(leavesModelId.getPath() + "_pile_1"), pileMap, blockStateModelGenerator.modelCollector);
        LEAF_PILE_2.upload(id(leavesModelId.getPath() + "_pile_2"), pileMap, blockStateModelGenerator.modelCollector);
        LEAF_PILE_3.upload(id(leavesModelId.getPath() + "_pile_3"), pileMap, blockStateModelGenerator.modelCollector);
        LEAF_PILE_4.upload(id(leavesModelId.getPath() + "_pile_4"), pileMap, blockStateModelGenerator.modelCollector);
        LEAF_PILE_5.upload(id(leavesModelId.getPath() + "_pile_5"), pileMap, blockStateModelGenerator.modelCollector);
        LEAF_PILE_6.upload(id(leavesModelId.getPath() + "_pile_6"), pileMap, blockStateModelGenerator.modelCollector);
        LEAF_PILE_7.upload(id(leavesModelId.getPath() + "_pile_7"), pileMap, blockStateModelGenerator.modelCollector);
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

    private static void registerSimpleParented(Identifier id, Identifier parent, BiConsumer<Identifier, Supplier<JsonElement>> writer) {
        writer.accept(id, new SimpleModelSupplier(parent));
    }

    /**
     * Allows chaining multiple suppliers together, particularly helpful for the item display and model predicate systems.
     * @param suppliers The suppliers to combine
     * @return The combined supplier which runs all suppliers and merges the result
     */
    private BiConsumer<Identifier, Supplier<JsonElement>> wrapAndChain(BiConsumer<Identifier, Supplier<JsonElement>> writer, Supplier<JsonElement>... suppliers) {
        return (identifier, original) -> {
            Supplier<JsonElement> finalSupplier = () -> {
                JsonObject finalObj = new JsonObject();
                for (Map.Entry<String, JsonElement> entry : original.get().getAsJsonObject().entrySet()) {
                    finalObj.add(entry.getKey(), entry.getValue());
                }
                for (Supplier<JsonElement> supplier : suppliers) {
                    for (Map.Entry<String, JsonElement> entry : supplier.get().getAsJsonObject().entrySet()) {
                        finalObj.add(entry.getKey(), entry.getValue());
                    }
                }
                return finalObj;
            };
            writer.accept(identifier, finalSupplier);
        };
    }
}
