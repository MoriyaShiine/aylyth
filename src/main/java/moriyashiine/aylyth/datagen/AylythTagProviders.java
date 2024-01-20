package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class AylythTagProviders {

    public static void registerTagProviders(FabricDataGenerator dataGenerator) {
        dataGenerator.addProvider(ModBiomeTags::new);
        ModBlockTags blockTags = new ModBlockTags(dataGenerator);
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
            getOrCreateTagBuilder(BiomeTags.HAS_CLOSER_WATER_FOG).add(ModBiomeKeys.MIRE_ID);
        }
    }

    public static class ModBlockTags extends FabricTagProvider.BlockTagProvider {

        public ModBlockTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).addTag(ModTags.WOODY_GROWTHS).add(ModBlocks.AYLYTH_BUSH, ModBlocks.ANTLER_SHOOTS, ModBlocks.GRIPWEED, ModBlocks.NYSIAN_GRAPE_VINE, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD, ModBlocks.SEEPING_WOOD_SEEP);
            getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(ModBlocks.YMPE_LEAVES, ModBlocks.POMEGRANATE_LEAVES, ModBlocks.WRITHEWOOD_LEAVES);
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.SOUL_HEARTH).add(ModBlocks.VITAL_THURIBLE);
            getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(ModBlocks.OAK_STREWN_LEAVES, ModBlocks.YMPE_STREWN_LEAVES);
            getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(ModBlocks.NYSIAN_GRAPE_VINE);
            getOrCreateTagBuilder(ModTags.SEEPS).add(ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD_SEEP);
            getOrCreateTagBuilder(ModTags.WOODY_GROWTHS).add(ModBlocks.SMALL_WOODY_GROWTH, ModBlocks.LARGE_WOODY_GROWTH, ModBlocks.WOODY_GROWTH_CACHE);
            getOrCreateTagBuilder(ModTags.JACK_O_LANTERN_GENERATE_ON).add(Blocks.DARK_OAK_LOG, Blocks.OAK_LOG);
            getOrCreateTagBuilder(ModTags.GHOSTCAP_REPLACEABLE).add(Blocks.GRASS_BLOCK);
            addSuite(ModBlocks.YMPE_BLOCKS, ModTags.YMPE_LOGS);
            getOrCreateTagBuilder(ModTags.YMPE_LOGS).add(ModBlocks.FRUIT_BEARING_YMPE_LOG);
            addSuite(ModBlocks.POMEGRANATE_BLOCKS, ModTags.POMEGRANATE_LOGS);
            addSuite(ModBlocks.WRITHEWOOD_BLOCKS, ModTags.WRITHEWOOD_LOGS);
            getOrCreateTagBuilder(BlockTags.LEAVES).add(ModBlocks.YMPE_LEAVES, ModBlocks.POMEGRANATE_LEAVES, ModBlocks.WRITHEWOOD_LEAVES);
            getOrCreateTagBuilder(ModTags.WOODY_GROWTHS_GENERATE_ON).add(Blocks.MUD);
            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(ModBlocks.SEEPING_WOOD);
        }

        private void addSuite(WoodSuite suite, TagKey<Block> logTag) {
            getOrCreateTagBuilder(logTag).add(suite.log, suite.wood, suite.strippedLog, suite.strippedWood);

            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(logTag);
            getOrCreateTagBuilder(BlockTags.PLANKS).add(suite.planks);
            getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(suite.stairs);
            getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(suite.slab);
            getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(suite.fence);
            getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(suite.fenceGate);
            getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(suite.button);
            getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(suite.pressurePlate);
            getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(suite.door);
            getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(suite.trapdoor);
            getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(suite.floorSign);
            getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(suite.wallSign);
            getOrCreateTagBuilder(BlockTags.SAPLINGS).add(suite.sapling);
            getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(suite.pottedSapling);
        }
    }

    public static class ModItemTags extends FabricTagProvider.ItemTagProvider {

        public ModItemTags(FabricDataGenerator dataGenerator, @Nullable BlockTagProvider blockTagProvider) {
            super(dataGenerator, blockTagProvider);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(ItemTags.BOATS).add(ModItems.YMPE_ITEMS.boat, ModItems.POMEGRANATE_ITEMS.boat, ModItems.WRITHEWOOD_ITEMS.boat);
            getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(ModItems.YMPE_ITEMS.chestBoat, ModItems.POMEGRANATE_ITEMS.chestBoat, ModItems.WRITHEWOOD_ITEMS.chestBoat);
            getOrCreateTagBuilder(ModTags.YMPE_FOODS).add(ModItems.YMPE_FRUIT);
            copy(ModTags.YMPE_LOGS, ModTags.YMPE_LOGS_ITEM);
            copy(ModTags.POMEGRANATE_LOGS, ModTags.POMEGRANATE_LOGS_ITEM);
            copy(ModTags.WRITHEWOOD_LOGS, ModTags.WRITHEWOOD_LOGS_ITEM);
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
            copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
            copy(ModTags.WOODY_GROWTHS, ModTags.WOODY_GROWTHS_ITEM);
            copy(ModTags.SEEPS, ModTags.SEEP_ITEMS);
            getOrCreateTagBuilder(ModTags.PLEDGE_ITEMS).add(ModItems.NYSIAN_GRAPES);
        }
    }

    public static class ModEntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {

        public ModEntityTypeTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(ModTags.GRIPWEED_IMMUNE).add(ModEntityTypes.AYLYTHIAN, ModEntityTypes.ELDER_AYLYTHIAN);
            getOrCreateTagBuilder(ModTags.SHUCK_BLACKLIST).forceAddTag(ConventionalEntityTypeTags.BOSSES).add(EntityType.ELDER_GUARDIAN);
        }
    }
}
