package moriyashiine.aylyth.datagen.tags;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class AylythBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public AylythBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(ModBlockTags.CARVED_NEPHRITE).add(ModBlocks.CARVED_SMOOTH_NEPHRITE, ModBlocks.CARVED_ANTLERED_NEPHRITE, ModBlocks.CARVED_NEPHRITE_PILLAR, ModBlocks.CARVED_NEPHRITE_TILES, ModBlocks.CARVED_WOODY_NEPHRITE);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).addTag(ModBlockTags.WOODY_GROWTHS).addTag(ModBlockTags.CHTHONIA_WOOD).add(ModBlocks.AYLYTH_BUSH, ModBlocks.ANTLER_SHOOTS, ModBlocks.GRIPWEED, ModBlocks.NYSIAN_GRAPE_VINE, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD, ModBlocks.SEEPING_WOOD_SEEP, ModBlocks.DARK_WOODS_TILES);
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(ModBlocks.YMPE_LEAVES, ModBlocks.POMEGRANATE_LEAVES, ModBlocks.WRITHEWOOD_LEAVES);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).addTag(ModBlockTags.CARVED_NEPHRITE).add(ModBlocks.SOUL_HEARTH, ModBlocks.VITAL_THURIBLE, ModBlocks.ESSTLINE_BLOCK, ModBlocks.NEPHRITE_BLOCK);
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).addTag(ModBlockTags.CARVED_NEPHRITE).add(ModBlocks.NEPHRITE_BLOCK, ModBlocks.ESSTLINE_BLOCK);
        getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(ModBlocks.OAK_STREWN_LEAVES, ModBlocks.YMPE_STREWN_LEAVES);
        getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(ModBlocks.NYSIAN_GRAPE_VINE);
        getOrCreateTagBuilder(ModBlockTags.SEEPS).add(ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD_SEEP);
        getOrCreateTagBuilder(ModBlockTags.WOODY_GROWTHS).add(ModBlocks.SMALL_WOODY_GROWTH, ModBlocks.LARGE_WOODY_GROWTH, ModBlocks.WOODY_GROWTH_CACHE);
        getOrCreateTagBuilder(ModBlockTags.JACK_O_LANTERN_GENERATE_ON).add(Blocks.DARK_OAK_LOG, Blocks.OAK_LOG);
        getOrCreateTagBuilder(ModBlockTags.GHOSTCAP_REPLACEABLE).add(Blocks.GRASS_BLOCK);
        getOrCreateTagBuilder(ModBlockTags.CHTHONIA_WOOD).add(ModBlocks.CHTHONIA_WOOD, ModBlocks.NEPHRITIC_CHTHONIA_WOOD);

        getOrCreateTagBuilder(ModBlockTags.YMPE_LOGS).add(ModBlocks.YMPE_LOG, ModBlocks.YMPE_WOOD, ModBlocks.YMPE_STRIPPED_LOG, ModBlocks.YMPE_STRIPPED_WOOD, ModBlocks.FRUIT_BEARING_YMPE_LOG);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.YMPE_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(ModBlocks.YMPE_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(ModBlocks.YMPE_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(ModBlocks.YMPE_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(ModBlocks.YMPE_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(ModBlocks.YMPE_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(ModBlocks.YMPE_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.YMPE_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(ModBlocks.YMPE_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.YMPE_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(ModBlocks.YMPE_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(ModBlocks.YMPE_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(ModBlocks.YMPE_SAPLING);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(ModBlocks.YMPE_POTTED_SAPLING);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(ModBlocks.YMPE_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(ModBlocks.YMPE_WALL_HANGING_SIGN);

        getOrCreateTagBuilder(ModBlockTags.POMEGRANATE_LOGS).add(ModBlocks.POMEGRANATE_LOG, ModBlocks.POMEGRANATE_WOOD, ModBlocks.POMEGRANATE_STRIPPED_LOG, ModBlocks.POMEGRANATE_STRIPPED_WOOD);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.POMEGRANATE_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(ModBlocks.POMEGRANATE_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(ModBlocks.POMEGRANATE_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(ModBlocks.POMEGRANATE_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(ModBlocks.POMEGRANATE_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(ModBlocks.POMEGRANATE_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(ModBlocks.POMEGRANATE_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.POMEGRANATE_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(ModBlocks.POMEGRANATE_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.POMEGRANATE_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(ModBlocks.POMEGRANATE_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(ModBlocks.POMEGRANATE_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(ModBlocks.POMEGRANATE_SAPLING);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(ModBlocks.POMEGRANATE_POTTED_SAPLING);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(ModBlocks.POMEGRANATE_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(ModBlocks.POMEGRANATE_WALL_HANGING_SIGN);

        getOrCreateTagBuilder(ModBlockTags.WRITHEWOOD_LOGS).add(ModBlocks.WRITHEWOOD_LOG, ModBlocks.WRITHEWOOD_WOOD, ModBlocks.WRITHEWOOD_STRIPPED_LOG, ModBlocks.WRITHEWOOD_STRIPPED_WOOD);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.WRITHEWOOD_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(ModBlocks.WRITHEWOOD_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(ModBlocks.WRITHEWOOD_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(ModBlocks.WRITHEWOOD_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(ModBlocks.WRITHEWOOD_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(ModBlocks.WRITHEWOOD_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(ModBlocks.WRITHEWOOD_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.WRITHEWOOD_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(ModBlocks.WRITHEWOOD_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.WRITHEWOOD_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(ModBlocks.WRITHEWOOD_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(ModBlocks.WRITHEWOOD_WALL_SIGN);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(ModBlocks.WRITHEWOOD_SAPLING);
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(ModBlocks.WRITHEWOOD_POTTED_SAPLING);
        getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(ModBlocks.WRITHEWOOD_HANGING_SIGN);
        getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(ModBlocks.WRITHEWOOD_WALL_HANGING_SIGN);

        getOrCreateTagBuilder(BlockTags.LEAVES).add(ModBlocks.YMPE_LEAVES, ModBlocks.POMEGRANATE_LEAVES, ModBlocks.WRITHEWOOD_LEAVES);
        getOrCreateTagBuilder(ModBlockTags.WOODY_GROWTHS_GENERATE_ON).add(Blocks.MUD);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(ModBlocks.SEEPING_WOOD);
        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS).add(ModBlocks.ESSTLINE_BLOCK, ModBlocks.NEPHRITE_BLOCK);
    }
}
