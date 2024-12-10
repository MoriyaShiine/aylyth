package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

import static moriyashiine.aylyth.common.block.AylythBlocks.*;

public final class AylythBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public AylythBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(AylythBlockTags.CARVED_NEPHRITE).add(CARVED_SMOOTH_NEPHRITE, CARVED_ANTLERED_NEPHRITE, CARVED_NEPHRITE_PILLAR, CARVED_NEPHRITE_TILES, CARVED_WOODY_NEPHRITE);
        getOrCreateTagBuilder(AylythBlockTags.SEEPS).add(OAK_SEEP, SPRUCE_SEEP, DARK_OAK_SEEP, YMPE_SEEP, SEEPING_WOOD_SEEP);
        getOrCreateTagBuilder(AylythBlockTags.WOODY_GROWTHS).add(SMALL_WOODY_GROWTH, LARGE_WOODY_GROWTH, WOODY_GROWTH_CACHE);
        getOrCreateTagBuilder(AylythBlockTags.JACK_O_LANTERN_GENERATE_ON).add(Blocks.DARK_OAK_LOG, Blocks.OAK_LOG);
        getOrCreateTagBuilder(AylythBlockTags.GHOSTCAP_REPLACEABLE).add(Blocks.GRASS_BLOCK);
        getOrCreateTagBuilder(AylythBlockTags.CHTHONIA_WOOD).add(CHTHONIA_WOOD, NEPHRITIC_CHTHONIA_WOOD);
        getOrCreateTagBuilder(AylythBlockTags.WOODY_GROWTHS_GENERATE_ON).add(Blocks.MUD);
        getOrCreateTagBuilder(AylythBlockTags.SAPSTONES).add(SAPSTONE, AMBER_SAPSTONE, LIGNITE_SAPSTONE, OPALESCENT_SAPSTONE);
        getOrCreateTagBuilder(AylythBlockTags.LEAFY_BRANCHES).add(DARK_OAK_BRANCH, WRITHEWOOD_BRANCH, YMPE_BRANCH, ORANGE_AYLYTHIAN_OAK_BRANCH, RED_AYLYTHIAN_OAK_BRANCH, BROWN_AYLYTHIAN_OAK_BRANCH);
        getOrCreateTagBuilder(AylythBlockTags.BARE_BRANCHES).add(BARE_DARK_OAK_BRANCH, BARE_WRITHEWOOD_BRANCH, BARE_YMPE_BRANCH);
        getOrCreateTagBuilder(AylythBlockTags.BRANCHES).addTag(AylythBlockTags.BARE_BRANCHES).addTag(AylythBlockTags.LEAFY_BRANCHES);
        // TODO: Add these storage block tags to "c:storage_blocks"
        getOrCreateTagBuilder(AylythBlockTags.STORAGE_BLOCKS_ESSTLINE).add(ESSTLINE_BLOCK);
        getOrCreateTagBuilder(AylythBlockTags.STORAGE_BLOCKS_NEPHRITE).add(NEPHRITE_BLOCK);

        getOrCreateTagBuilder(AylythBlockTags.YMPE_LOGS).add(YMPE_LOG, YMPE_WOOD, YMPE_STRIPPED_LOG, YMPE_STRIPPED_WOOD, FRUIT_BEARING_YMPE_LOG);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(AylythBlockTags.YMPE_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(YMPE_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(YMPE_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(YMPE_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(YMPE_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(YMPE_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(YMPE_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(YMPE_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(YMPE_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(YMPE_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(YMPE_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(YMPE_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(YMPE_SAPLING);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(POTTED_YMPE_SAPLING);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(YMPE_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(YMPE_WALL_HANGING_SIGN);

        getOrCreateTagBuilder(AylythBlockTags.POMEGRANATE_LOGS).add(POMEGRANATE_LOG, POMEGRANATE_WOOD, POMEGRANATE_STRIPPED_LOG, POMEGRANATE_STRIPPED_WOOD);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(AylythBlockTags.POMEGRANATE_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(POMEGRANATE_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(POMEGRANATE_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(POMEGRANATE_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(POMEGRANATE_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(POMEGRANATE_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(POMEGRANATE_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(POMEGRANATE_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(POMEGRANATE_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(POMEGRANATE_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(POMEGRANATE_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(POMEGRANATE_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(POMEGRANATE_SAPLING);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(POTTED_POMEGRANATE_SAPLING);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(POMEGRANATE_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(POMEGRANATE_WALL_HANGING_SIGN);

        getOrCreateTagBuilder(AylythBlockTags.WRITHEWOOD_LOGS).add(WRITHEWOOD_LOG, WRITHEWOOD_WOOD, WRITHEWOOD_STRIPPED_LOG, WRITHEWOOD_STRIPPED_WOOD);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(AylythBlockTags.WRITHEWOOD_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(WRITHEWOOD_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(WRITHEWOOD_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(WRITHEWOOD_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(WRITHEWOOD_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(WRITHEWOOD_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(WRITHEWOOD_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(WRITHEWOOD_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(WRITHEWOOD_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(WRITHEWOOD_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(WRITHEWOOD_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(WRITHEWOOD_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(WRITHEWOOD_SAPLING);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(POTTED_WRITHEWOOD_SAPLING);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(WRITHEWOOD_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(WRITHEWOOD_WALL_HANGING_SIGN);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).addTag(AylythBlockTags.WOODY_GROWTHS).addTag(AylythBlockTags.CHTHONIA_WOOD).add(AYLYTH_BUSH, ANTLER_SHOOTS, GRIPWEED, NYSIAN_GRAPE_VINE, OAK_SEEP, SPRUCE_SEEP, DARK_OAK_SEEP, YMPE_SEEP, SEEPING_WOOD, SEEPING_WOOD_SEEP, DARK_WOODS_TILES, JACK_O_LANTERN_MUSHROOM_STEM, JACK_O_LANTERN_MUSHROOM_BLOCK);
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(YMPE_LEAVES, POMEGRANATE_LEAVES, WRITHEWOOD_LEAVES);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).addTag(AylythBlockTags.CARVED_NEPHRITE).add(SOUL_HEARTH, VITAL_THURIBLE, ESSTLINE_BLOCK, NEPHRITE_BLOCK, SAPSTONE, AMBER_SAPSTONE, LIGNITE_SAPSTONE, OPALESCENT_SAPSTONE);
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(DARK_PODZOL);
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).addTag(AylythBlockTags.CARVED_NEPHRITE).add(NEPHRITE_BLOCK, ESSTLINE_BLOCK);
        getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(OAK_STREWN_LEAVES, YMPE_STREWN_LEAVES);
        getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(NYSIAN_GRAPE_VINE);
        getOrCreateTagBuilder(BlockTags.LEAVES).add(YMPE_LEAVES, POMEGRANATE_LEAVES, WRITHEWOOD_LEAVES, GREEN_AYLYTHIAN_OAK_LEAVES, ORANGE_AYLYTHIAN_OAK_LEAVES, RED_AYLYTHIAN_OAK_LEAVES, BROWN_AYLYTHIAN_OAK_LEAVES);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(SEEPING_WOOD);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(GIRASOL_SAPLING, BROWN_AYLYTHIAN_OAK_SAPLING, GREEN_AYLYTHIAN_OAK_SAPLING, ORANGE_AYLYTHIAN_OAK_SAPLING, RED_AYLYTHIAN_OAK_SAPLING);
        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS).add(ESSTLINE_BLOCK, NEPHRITE_BLOCK);
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(MARIGOLD);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(POTTED_MARIGOLD, POTTED_GIRASOL_SAPLING, POTTED_BROWN_AYLYTHIAN_OAK_SAPLING, POTTED_GREEN_AYLYTHIAN_OAK_SAPLING, POTTED_ORANGE_AYLYTHIAN_OAK_SAPLING, POTTED_RED_AYLYTHIAN_OAK_SAPLING);
        getOrCreateTagBuilder(BlockTags.PORTALS).addTag(AylythBlockTags.SEEPS);
        getOrCreateTagBuilder(BlockTags.DIRT).add(DARK_PODZOL);
        getOrCreateTagBuilder(BlockTags.VALID_SPAWN).add(DARK_PODZOL);
        getOrCreateTagBuilder(BlockTags.MUSHROOM_GROW_BLOCK).add(DARK_PODZOL);
        getOrCreateTagBuilder(BlockTags.FOXES_SPAWNABLE_ON).add(DARK_PODZOL);
        getOrCreateTagBuilder(BlockTags.SNIFFER_DIGGABLE_BLOCK).add(DARK_PODZOL);
    }
}
