package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModTags;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class AylythTagProviders {

    public static void registerTagProviders(FabricDataGenerator dataGenerator) {
        dataGenerator.addProvider(ModBiomeTags::new);
        var blockTags = new ModBlockTags(dataGenerator);
        dataGenerator.addProvider(blockTags);
        dataGenerator.addProvider(new ModItemTags(dataGenerator, blockTags));
        dataGenerator.addProvider(ModEntityTypeTags::new);
    }

    public static class ModBiomeTags extends FabricTagProvider.DynamicRegistryTagProvider<Biome> {
        public ModBiomeTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator, Registry.BIOME_KEY);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(ModTags.GENERATES_SEEP).forceAddTag(BiomeTags.IS_FOREST).forceAddTag(BiomeTags.IS_TAIGA);
        }
    }

    public static class ModBlockTags extends FabricTagProvider.BlockTagProvider {

        public ModBlockTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(ModBlocks.OAK_STREWN_LEAVES, ModBlocks.YMPE_STREWN_LEAVES);
            getOrCreateTagBuilder(ModTags.JACK_O_LANTERN_GENERATE_ON).add(Blocks.DARK_OAK_LOG, Blocks.OAK_LOG);
            getOrCreateTagBuilder(ModTags.GHOSTCAP_REPLACEABLE).add(Blocks.GRASS_BLOCK);
            getOrCreateTagBuilder(ModTags.YMPE_LOGS).add(ModBlocks.YMPE_LOG, ModBlocks.YMPE_WOOD, ModBlocks.STRIPPED_YMPE_LOG, ModBlocks.STRIPPED_YMPE_WOOD);
            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(ModTags.YMPE_LOGS);
            getOrCreateTagBuilder(BlockTags.PLANKS).add(ModBlocks.YMPE_PLANKS);
            getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(ModBlocks.YMPE_STAIRS);
            getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(ModBlocks.YMPE_SLAB);
            getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(ModBlocks.YMPE_FENCE);
            getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(ModBlocks.YMPE_BUTTON);
            getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.YMPE_PRESSURE_PLATE);
            getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(ModBlocks.YMPE_DOOR);
            getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.YMPE_TRAPDOOR);
            getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(ModBlocks.YMPE_SIGN);
            getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(ModBlocks.YMPE_WALL_SIGN);
            addSuite(ModBlocks.POMEGRANATE_BLOCKS, ModTags.POMEGRANATE_LOGS);
            getOrCreateTagBuilder(BlockTags.LEAVES).add(ModBlocks.YMPE_LEAVES, ModBlocks.POMEGRANATE_LEAVES);
        }

        private void addSuite(WoodSuite suite, TagKey<Block> logTag) {
            getOrCreateTagBuilder(logTag).add(suite.log, suite.wood, suite.strippedLog, suite.strippedWood);

            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(logTag);
            getOrCreateTagBuilder(BlockTags.PLANKS).add(suite.planks);
            getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(suite.stairs);
            getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(suite.slab);
            getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(suite.fence);
            getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(suite.button);
            getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(suite.pressurePlate);
            getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(suite.door);
            getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(suite.trapdoor);
            getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(suite.floorSign);
            getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(suite.wallSign);

        }
    }

    public static class ModItemTags extends FabricTagProvider.ItemTagProvider {

        public ModItemTags(FabricDataGenerator dataGenerator, @Nullable BlockTagProvider blockTagProvider) {
            super(dataGenerator, blockTagProvider);
        }

        @Override
        protected void generateTags() {
            copy(ModTags.YMPE_LOGS, ModTags.YMPE_LOGS_ITEM);
            copy(ModTags.POMEGRANATE_LOGS, ModTags.POMEGRANATE_LOGS_ITEM);
            copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
            copy(BlockTags.PLANKS, ItemTags.PLANKS);
            copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
            copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
            copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
            copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
            copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
            copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
            copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
            copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
            copy(BlockTags.LEAVES, ItemTags.LEAVES);
        }
    }

    public static class ModEntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {

        public ModEntityTypeTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
        }
    }
}
