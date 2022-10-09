package moriyashiine.aylyth.datagen;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.StrewnLeavesBlock;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class AylythModelProvider extends FabricModelProvider {

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

    public AylythModelProvider(FabricDataGenerator generator) {
        super(generator);
    }

    private Identifier id(String id) {
        return new Identifier(Aylyth.MOD_ID, id);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerDoor(ModBlocks.YMPE_DOOR);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.MARIGOLD, ModBlocks.MARIGOLD_POTTED, BlockStateModelGenerator.TintType.NOT_TINTED);
        generateStrewnLeaves(blockStateModelGenerator, ModBlocks.OAK_STREWN_LEAVES, Blocks.OAK_LEAVES, id("block/fallen_oak_leaves_01"), id("block/fallen_oak_leaves_02"), id("block/fallen_oak_leaves_03"), id("block/fallen_oak_leaves_04"), id("block/fallen_oak_leaves_05"), id("block/fallen_oak_leaves_06"), id("block/fallen_oak_leaves_07"), id("block/fallen_oak_leaves_08"), id("block/fallen_oak_leaves_09"), id("block/fallen_oak_leaves_10"));
        generateStrewnLeaves(blockStateModelGenerator, ModBlocks.YMPE_STREWN_LEAVES, ModBlocks.YMPE_LEAVES, id("block/fallen_ympe_leaves_01"), id("block/fallen_ympe_leaves_02"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        generateStrewnLeavesItemModel(ModItems.OAK_STREWN_LEAVES, id("block/fallen_oak_leaves_01"), itemModelGenerator);
        generateStrewnLeavesItemModel(ModItems.YMPE_STREWN_LEAVES, id("block/fallen_ympe_leaves_01"), itemModelGenerator);
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
        var leavesModelId = ModelIds.getBlockModelId(leavesBlock);
        LEAF_PILE_1_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_1"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_2_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_2"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_3_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_3"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_4_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_4"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_5_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_5"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_6_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_6"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
        LEAF_PILE_7_MODEL.upload(id(strippedBlockId(leavesBlock) + "_pile_7"), TextureMap.of(TextureKey.ALL, leavesModelId).put(TextureKey.PARTICLE, leavesModelId), blockStateModelGenerator.modelCollector);
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
}
