package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBiomes;
import moriyashiine.aylyth.common.registry.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.BiomeTagProvider;
import net.minecraft.tag.BiomeTags;

public class ModBiomeTagProvider extends BiomeTagProvider {
    private boolean genVanilla;
    public ModBiomeTagProvider(DataGenerator dataGenerator, boolean genVanilla) {
        super(dataGenerator);
        this.genVanilla = genVanilla;
    }

    @Override
    protected void configure() {
        if (genVanilla)
            super.configure();
        getOrCreateTagBuilder(BiomeTags.IS_FOREST).add(ModBiomes.FOREST.value(), ModBiomes.CONIFEROUS_FOREST.value(), ModBiomes.DEEP_CONIFEROUS_FOREST.value(), ModBiomes.DEEP_FOREST.value());
        getOrCreateTagBuilder(BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE).add(ModBiomes.CLEARING.value(), ModBiomes.OVERGROWN_CLEARING.value());
        getOrCreateTagBuilder(ModTags.GENERATES_OAK_SEEP).addTag(BiomeTags.IS_FOREST).addTag(BiomeTags.IS_TAIGA);
        getOrCreateTagBuilder(ModTags.GENERATES_SPRUCE_SEEP).addTag(BiomeTags.IS_FOREST).addTag(BiomeTags.IS_TAIGA);
        getOrCreateTagBuilder(ModTags.GENERATES_DARK_OAK_SEEP).addTag(BiomeTags.IS_FOREST).addTag(BiomeTags.IS_TAIGA);
    }
}
