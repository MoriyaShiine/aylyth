package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.data.tag.AylythBiomeTags;
import moriyashiine.aylyth.common.data.world.AylythBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public final class AylythBiomeTagProvider extends FabricTagProvider<Biome> {
    public AylythBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, RegistryKeys.BIOME, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(AylythBiomeTags.IS_CLEARING).add(AylythBiomes.CLEARING, AylythBiomes.OVERGROWN_CLEARING);
        getOrCreateTagBuilder(AylythBiomeTags.IS_COPSE).add(AylythBiomes.COPSE, AylythBiomes.CONIFEROUS_COPSE);
        getOrCreateTagBuilder(AylythBiomeTags.IS_DEEPWOOD).add(AylythBiomes.DEEPWOOD, AylythBiomes.CONIFEROUS_DEEPWOOD);
        getOrCreateTagBuilder(AylythBiomeTags.IS_CONIFEROUS).add(AylythBiomes.CONIFEROUS_COPSE, AylythBiomes.CONIFEROUS_DEEPWOOD);
        getOrCreateTagBuilder(AylythBiomeTags.IS_FOREST_LIKE).add(AylythBiomes.OVERGROWN_CLEARING, AylythBiomes.COPSE, AylythBiomes.DEEPWOOD);
        getOrCreateTagBuilder(AylythBiomeTags.IS_TAIGA_LIKE).addTag(AylythBiomeTags.IS_CONIFEROUS).add(AylythBiomes.DEEPWOOD);
        getOrCreateTagBuilder(AylythBiomeTags.GENERATES_SEEP).addOptionalTag(ConventionalBiomeTags.FOREST).addOptionalTag(ConventionalBiomeTags.TAIGA);
        getOrCreateTagBuilder(AylythBiomeTags.IS_AYLYTH).add(AylythBiomes.DEEPWOOD, AylythBiomes.CONIFEROUS_DEEPWOOD, AylythBiomes.COPSE, AylythBiomes.CONIFEROUS_COPSE, AylythBiomes.CLEARING, AylythBiomes.OVERGROWN_CLEARING, AylythBiomes.UPLANDS, AylythBiomes.BOWELS, AylythBiomes.MIRE);
        getOrCreateTagBuilder(AylythBiomeTags.BLACK_WELL_HAS_STRUCTURE).add(AylythBiomes.COPSE, AylythBiomes.CLEARING, AylythBiomes.OVERGROWN_CLEARING);
        getOrCreateTagBuilder(AylythBiomeTags.HAS_WEAK_FOG).add(AylythBiomes.CLEARING, AylythBiomes.UPLANDS);
        getOrCreateTagBuilder(AylythBiomeTags.HAS_AVERAGE_FOG).add(AylythBiomes.OVERGROWN_CLEARING);
        getOrCreateTagBuilder(AylythBiomeTags.HAS_STRONG_FOG).add(AylythBiomes.COPSE, AylythBiomes.CONIFEROUS_COPSE, AylythBiomes.DEEPWOOD, AylythBiomes.CONIFEROUS_DEEPWOOD, AylythBiomes.MIRE, AylythBiomes.BOWELS);
        getOrCreateTagBuilder(BiomeTags.HAS_CLOSER_WATER_FOG).add(AylythBiomes.MIRE);
    }
}
