package moriyashiine.aylyth.datagen;

import com.google.common.collect.ImmutableList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.block.LargeWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.PomegranateLeavesBlock;
import moriyashiine.aylyth.common.block.StrewnLeavesBlock;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AylythModelProvider extends FabricModelProvider {

    private static final BlockFamily POMEGRANATE = BlockFamilies.register(ModBlocks.POMEGRANATE_BLOCKS.planks).button(ModBlocks.POMEGRANATE_BLOCKS.button).fence(ModBlocks.POMEGRANATE_BLOCKS.fence).fenceGate(ModBlocks.POMEGRANATE_BLOCKS.fenceGate).pressurePlate(ModBlocks.POMEGRANATE_BLOCKS.pressurePlate).sign(ModBlocks.POMEGRANATE_BLOCKS.floorSign, ModBlocks.POMEGRANATE_BLOCKS.wallSign).slab(ModBlocks.POMEGRANATE_BLOCKS.slab).stairs(ModBlocks.POMEGRANATE_BLOCKS.stairs).door(ModBlocks.POMEGRANATE_BLOCKS.door).trapdoor(ModBlocks.POMEGRANATE_BLOCKS.trapdoor).group("wooden").unlockCriterionName("has_planks").build();
    private static final BlockFamily WRITHEWOOD = fromWoodSuite(ModBlocks.WRITHEWOOD_BLOCKS);

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
        blockStateModelGenerator.registerDoor(ModBlocks.YMPE_BLOCKS.door);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.MARIGOLD, ModBlocks.MARIGOLD_POTTED, BlockStateModelGenerator.TintType.NOT_TINTED);
        generateStrewnLeaves(blockStateModelGenerator, ModBlocks.OAK_STREWN_LEAVES, Blocks.OAK_LEAVES, id("block/fallen_oak_leaves_01"), id("block/fallen_oak_leaves_02"), id("block/fallen_oak_leaves_03"), id("block/fallen_oak_leaves_04"), id("block/fallen_oak_leaves_05"), id("block/fallen_oak_leaves_06"), id("block/fallen_oak_leaves_07"), id("block/fallen_oak_leaves_08"), id("block/fallen_oak_leaves_09"), id("block/fallen_oak_leaves_10"));
        generateStrewnLeaves(blockStateModelGenerator, ModBlocks.YMPE_STREWN_LEAVES, ModBlocks.YMPE_LEAVES, id("block/fallen_ympe_leaves_01"), id("block/fallen_ympe_leaves_02"));
        woodSuite(blockStateModelGenerator, ModBlocks.POMEGRANATE_BLOCKS, POMEGRANATE);
        fruitingLeaves(blockStateModelGenerator, ModBlocks.POMEGRANATE_LEAVES, blockId("pomegranate_leaves"), blockId("pomegranate_leaves_fruiting_0"), blockId("pomegranate_leaves_fruiting_1"), blockId("pomegranate_leaves_fruiting_2"));
        TexturedModel.CUBE_ALL.upload(ModBlocks.POMEGRANATE_LEAVES, blockStateModelGenerator.modelCollector);
        woodSuite(blockStateModelGenerator, ModBlocks.WRITHEWOOD_BLOCKS, WRITHEWOOD);
        variantState(blockStateModelGenerator, ModBlocks.WRITHEWOOD_LEAVES);
        blockStateModelGenerator.registerSingleton(ModBlocks.DARK_WOODS_TILES, TexturedModel.CUBE_ALL);
        Models.PARTICLE.upload(blockId("woody_growth_particles"), TextureMap.particle(blockId("aylyth_bush_trunk")), blockStateModelGenerator.modelCollector);
        woodyGrowth(blockStateModelGenerator);
        generateWoodBlock(blockStateModelGenerator, ModBlocks.SEEPING_WOOD, "block/aylyth_bush_trunk");

        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.GIRASOL_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        TextureMap girasolMap = TextureMap.plant(ModBlocks.GIRASOL_SAPLING);
        Identifier pottedSaplingId = BlockStateModelGenerator.TintType.NOT_TINTED.getFlowerPotCrossModel().upload(ModBlocks.GIRASOL_SAPLING_POTTED, girasolMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(ModBlocks.GIRASOL_SAPLING_POTTED, pottedSaplingId));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        generateStrewnLeavesItemModel(ModItems.OAK_STREWN_LEAVES, id("block/fallen_oak_leaves_01"), itemModelGenerator);
        generateStrewnLeavesItemModel(ModItems.YMPE_STREWN_LEAVES, id("block/fallen_ympe_leaves_01"), itemModelGenerator);
        itemModelGenerator.register(ModItems.POMEGRANATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.GHOSTCAP_MUSHROOM_SPORES, Models.GENERATED);
        itemModelGenerator.register(ModItems.POMEGRANATE_ITEMS.boat, Models.GENERATED);
        itemModelGenerator.register(ModItems.POMEGRANATE_ITEMS.chestBoat, Models.GENERATED);
        itemModelGenerator.register(ModItems.WRITHEWOOD_ITEMS.boat, Models.GENERATED);
        itemModelGenerator.register(ModItems.WRITHEWOOD_ITEMS.chestBoat, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEBUG_WAND, Models.HANDHELD);
        itemModelGenerator.register(ModItems.WRONGMEAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.GIRASOL_SEED, Models.GENERATED);
        itemModelGenerator.register(ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ESSTLINE, Models.GENERATED);
        itemModelGenerator.register(ModItems.LARGE_WOODY_GROWTH, Models.GENERATED);
        itemModelGenerator.register(ModItems.WOODY_GROWTH_CACHE, BUILTIN);
        itemModelGenerator.register(ModItems.SMALL_WOODY_GROWTH, Models.GENERATED);
        itemModelGenerator.register(ModItems.YMPE_CUIRASS, Models.GENERATED);
        itemModelGenerator.register(ModItems.MYSTERIOUS_SKETCH, BUILTIN);
        itemModelGenerator.register(ModItems.WREATHED_HIND_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(ModItems.FAUNAYLYTHIAN_SPAWN_EGG, SPAWN_EGG);
        Models.GENERATED.upload(AylythUtil.id("item/" + Registries.ITEM.getId(ModItems.MYSTERIOUS_SKETCH).getPath() + "_generated"), TextureMap.layer0(AylythUtil.id("item/" + Registries.ITEM.getId(ModItems.MYSTERIOUS_SKETCH).getPath())), itemModelGenerator.writer);
    }

    private void generateWoodBlock(BlockStateModelGenerator generator, Block woodBlock, String texturePath) {
        TextureMap textureMap = new TextureMap();
        textureMap.put(TextureKey.SIDE, new Identifier(Aylyth.MOD_ID, texturePath));
        textureMap.put(TextureKey.END, textureMap.getTexture(TextureKey.SIDE));
        Identifier identifier = Models.CUBE_COLUMN.upload(woodBlock, textureMap, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(woodBlock, identifier));
    }

    private void woodyGrowth(BlockStateModelGenerator generator) {
        generator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ModBlocks.LARGE_WOODY_GROWTH).
                        coordinate(BlockStateVariantMap.create(LargeWoodyGrowthBlock.HALF)
                                .register(DoubleBlockHalf.LOWER,
                                        List.of(
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_1"), VariantSettings.Rotation.R0),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_1"), VariantSettings.Rotation.R90),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_1"), VariantSettings.Rotation.R180),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_1"), VariantSettings.Rotation.R270),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_2"), VariantSettings.Rotation.R0),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_2"), VariantSettings.Rotation.R90),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_2"), VariantSettings.Rotation.R180),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_2"), VariantSettings.Rotation.R270),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_3"), VariantSettings.Rotation.R0),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_3"), VariantSettings.Rotation.R90),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_3"), VariantSettings.Rotation.R180),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_3"), VariantSettings.Rotation.R270),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_4"), VariantSettings.Rotation.R0),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_4"), VariantSettings.Rotation.R90),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_4"), VariantSettings.Rotation.R180),
                                        modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_4"), VariantSettings.Rotation.R270)
                                        )
                                )
                                .register(DoubleBlockHalf.UPPER, List.of(
                                        BlockStateVariant.create().put(VariantSettings.MODEL, blockId("woody_growth_particles"))
                                )))
        );
        generator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ModBlocks.WOODY_GROWTH_CACHE).
                        coordinate(BlockStateVariantMap.create(LargeWoodyGrowthBlock.HALF)
                                .register(DoubleBlockHalf.LOWER,
                                        List.of(
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_1"), VariantSettings.Rotation.R0),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_1"), VariantSettings.Rotation.R90),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_1"), VariantSettings.Rotation.R180),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_1"), VariantSettings.Rotation.R270),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_2"), VariantSettings.Rotation.R0),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_2"), VariantSettings.Rotation.R90),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_2"), VariantSettings.Rotation.R180),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_2"), VariantSettings.Rotation.R270),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_3"), VariantSettings.Rotation.R0),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_3"), VariantSettings.Rotation.R90),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_3"), VariantSettings.Rotation.R180),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_3"), VariantSettings.Rotation.R270),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_4"), VariantSettings.Rotation.R0),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_4"), VariantSettings.Rotation.R90),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_4"), VariantSettings.Rotation.R180),
                                                modelVariantWithYRotation(AylythUtil.id("block/large_woody_growth_4"), VariantSettings.Rotation.R270)
                                        )
                                )
                                .register(DoubleBlockHalf.UPPER, List.of(
                                        BlockStateVariant.create().put(VariantSettings.MODEL, blockId("woody_growth_particles"))
                                )))
        );
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.SMALL_WOODY_GROWTH,
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_1"), VariantSettings.Rotation.R90),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_1"), VariantSettings.Rotation.R180),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_1"), VariantSettings.Rotation.R0),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_1"), VariantSettings.Rotation.R270),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_2"), VariantSettings.Rotation.R0),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_2"), VariantSettings.Rotation.R90),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_2"), VariantSettings.Rotation.R180),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_2"), VariantSettings.Rotation.R270),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_3"), VariantSettings.Rotation.R0),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_3"), VariantSettings.Rotation.R90),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_3"), VariantSettings.Rotation.R180),
                modelVariantWithYRotation(AylythUtil.id("block/small_woody_growth_3"), VariantSettings.Rotation.R270)
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

    private void woodSuite(BlockStateModelGenerator generator, WoodSuite suite, BlockFamily family) {
        generator.registerLog(suite.strippedLog).log(suite.strippedLog).wood(suite.strippedWood);
        generator.registerLog(suite.log).log(suite.log).wood(suite.wood);
        generator.registerFlowerPotPlant(suite.sapling, suite.pottedSapling, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerCubeAllModelTexturePool(suite.planks)
                .family(family);
    }

    private void booleanState(BlockStateModelGenerator generator, Block block, BooleanProperty property, List<BlockStateVariant> onFalse, List<BlockStateVariant> onTrue) {
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(property).register(false, onFalse).register(true, onTrue)));
    }

    /** This just does a simple single model, not dependent on any states*/
    private void variantState(BlockStateModelGenerator generator, Block block) {
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, blockId(Registries.BLOCK.getId(block).getPath()))));
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

    private String strippedBlockId(Block block) {
        return ModelIds.getBlockModelId(block).getPath();
    }
    
    private static BlockFamily fromWoodSuite(WoodSuite woodSuite) {
        return new BlockFamily.Builder(woodSuite.planks).button(woodSuite.button).fence(woodSuite.fence).fenceGate(woodSuite.fenceGate).pressurePlate(woodSuite.pressurePlate).sign(woodSuite.floorSign, woodSuite.wallSign).slab(woodSuite.slab).stairs(woodSuite.stairs).door(woodSuite.door).trapdoor(woodSuite.trapdoor).group("wooden").unlockCriterionName("has_planks").build();
    }
}
