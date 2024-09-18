package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public final class AylythBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public AylythBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(AylythBlockTags.CARVED_NEPHRITE).add(AylythBlocks.CARVED_SMOOTH_NEPHRITE, AylythBlocks.CARVED_ANTLERED_NEPHRITE, AylythBlocks.CARVED_NEPHRITE_PILLAR, AylythBlocks.CARVED_NEPHRITE_TILES, AylythBlocks.CARVED_WOODY_NEPHRITE);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).addTag(AylythBlockTags.WOODY_GROWTHS).addTag(AylythBlockTags.CHTHONIA_WOOD).add(AylythBlocks.AYLYTH_BUSH, AylythBlocks.ANTLER_SHOOTS, AylythBlocks.GRIPWEED, AylythBlocks.NYSIAN_GRAPE_VINE, AylythBlocks.OAK_SEEP, AylythBlocks.SPRUCE_SEEP, AylythBlocks.DARK_OAK_SEEP, AylythBlocks.YMPE_SEEP, AylythBlocks.SEEPING_WOOD, AylythBlocks.SEEPING_WOOD_SEEP, AylythBlocks.DARK_WOODS_TILES);
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(AylythBlocks.YMPE_LEAVES, AylythBlocks.POMEGRANATE_LEAVES, AylythBlocks.WRITHEWOOD_LEAVES);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).addTag(AylythBlockTags.CARVED_NEPHRITE).add(AylythBlocks.SOUL_HEARTH, AylythBlocks.VITAL_THURIBLE, AylythBlocks.ESSTLINE_BLOCK, AylythBlocks.NEPHRITE_BLOCK);
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).addTag(AylythBlockTags.CARVED_NEPHRITE).add(AylythBlocks.NEPHRITE_BLOCK, AylythBlocks.ESSTLINE_BLOCK);
        getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(AylythBlocks.OAK_STREWN_LEAVES, AylythBlocks.YMPE_STREWN_LEAVES);
        getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(AylythBlocks.NYSIAN_GRAPE_VINE);
        getOrCreateTagBuilder(AylythBlockTags.SEEPS).add(AylythBlocks.OAK_SEEP, AylythBlocks.SPRUCE_SEEP, AylythBlocks.DARK_OAK_SEEP, AylythBlocks.YMPE_SEEP, AylythBlocks.SEEPING_WOOD_SEEP);
        getOrCreateTagBuilder(AylythBlockTags.WOODY_GROWTHS).add(AylythBlocks.SMALL_WOODY_GROWTH, AylythBlocks.LARGE_WOODY_GROWTH, AylythBlocks.WOODY_GROWTH_CACHE);
        getOrCreateTagBuilder(AylythBlockTags.JACK_O_LANTERN_GENERATE_ON).add(Blocks.DARK_OAK_LOG, Blocks.OAK_LOG);
        getOrCreateTagBuilder(AylythBlockTags.GHOSTCAP_REPLACEABLE).add(Blocks.GRASS_BLOCK);
        getOrCreateTagBuilder(AylythBlockTags.CHTHONIA_WOOD).add(AylythBlocks.CHTHONIA_WOOD, AylythBlocks.NEPHRITIC_CHTHONIA_WOOD);

        getOrCreateTagBuilder(AylythBlockTags.YMPE_LOGS).add(AylythBlocks.YMPE_LOG, AylythBlocks.YMPE_WOOD, AylythBlocks.YMPE_STRIPPED_LOG, AylythBlocks.YMPE_STRIPPED_WOOD, AylythBlocks.FRUIT_BEARING_YMPE_LOG);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(AylythBlockTags.YMPE_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(AylythBlocks.YMPE_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(AylythBlocks.YMPE_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(AylythBlocks.YMPE_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(AylythBlocks.YMPE_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(AylythBlocks.YMPE_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(AylythBlocks.YMPE_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(AylythBlocks.YMPE_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(AylythBlocks.YMPE_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(AylythBlocks.YMPE_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(AylythBlocks.YMPE_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(AylythBlocks.YMPE_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(AylythBlocks.YMPE_SAPLING);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(AylythBlocks.YMPE_POTTED_SAPLING);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(AylythBlocks.YMPE_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(AylythBlocks.YMPE_WALL_HANGING_SIGN);

        getOrCreateTagBuilder(AylythBlockTags.POMEGRANATE_LOGS).add(AylythBlocks.POMEGRANATE_LOG, AylythBlocks.POMEGRANATE_WOOD, AylythBlocks.POMEGRANATE_STRIPPED_LOG, AylythBlocks.POMEGRANATE_STRIPPED_WOOD);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(AylythBlockTags.POMEGRANATE_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(AylythBlocks.POMEGRANATE_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(AylythBlocks.POMEGRANATE_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(AylythBlocks.POMEGRANATE_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(AylythBlocks.POMEGRANATE_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(AylythBlocks.POMEGRANATE_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(AylythBlocks.POMEGRANATE_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(AylythBlocks.POMEGRANATE_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(AylythBlocks.POMEGRANATE_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(AylythBlocks.POMEGRANATE_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(AylythBlocks.POMEGRANATE_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(AylythBlocks.POMEGRANATE_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(AylythBlocks.POMEGRANATE_SAPLING);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(AylythBlocks.POMEGRANATE_POTTED_SAPLING);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(AylythBlocks.POMEGRANATE_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(AylythBlocks.POMEGRANATE_WALL_HANGING_SIGN);

        getOrCreateTagBuilder(AylythBlockTags.WRITHEWOOD_LOGS).add(AylythBlocks.WRITHEWOOD_LOG, AylythBlocks.WRITHEWOOD_WOOD, AylythBlocks.WRITHEWOOD_STRIPPED_LOG, AylythBlocks.WRITHEWOOD_STRIPPED_WOOD);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(AylythBlockTags.WRITHEWOOD_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(AylythBlocks.WRITHEWOOD_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(AylythBlocks.WRITHEWOOD_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(AylythBlocks.WRITHEWOOD_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(AylythBlocks.WRITHEWOOD_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(AylythBlocks.WRITHEWOOD_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(AylythBlocks.WRITHEWOOD_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(AylythBlocks.WRITHEWOOD_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(AylythBlocks.WRITHEWOOD_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(AylythBlocks.WRITHEWOOD_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(AylythBlocks.WRITHEWOOD_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(AylythBlocks.WRITHEWOOD_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(AylythBlocks.WRITHEWOOD_SAPLING);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(AylythBlocks.WRITHEWOOD_POTTED_SAPLING);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(AylythBlocks.WRITHEWOOD_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(AylythBlocks.WRITHEWOOD_WALL_HANGING_SIGN);

        getOrCreateTagBuilder(BlockTags.LEAVES).add(AylythBlocks.YMPE_LEAVES, AylythBlocks.POMEGRANATE_LEAVES, AylythBlocks.WRITHEWOOD_LEAVES);
        getOrCreateTagBuilder(AylythBlockTags.WOODY_GROWTHS_GENERATE_ON).add(Blocks.MUD);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(AylythBlocks.SEEPING_WOOD);
        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS).add(AylythBlocks.ESSTLINE_BLOCK, AylythBlocks.NEPHRITE_BLOCK);
    }
}
