package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class AylythTagProviders {

    public static void registerTagProviders(FabricDataGenerator dataGenerator) {
        dataGenerator.addProvider(ModBiomeTags::new);
        dataGenerator.addProvider(ModBlockTags::new);
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
        }
    }
}
