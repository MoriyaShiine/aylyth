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
        getOrCreateTagBuilder(ModTags.GENERATES_SEEP).addTag(BiomeTags.IS_FOREST).addTag(BiomeTags.IS_TAIGA);
    }
}
