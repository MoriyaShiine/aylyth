package moriyashiine.aylyth.datagen.tags;

import moriyashiine.aylyth.common.registry.key.ModBiomeKeys;
import moriyashiine.aylyth.common.registry.tag.ModBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class AylythBiomeTagProvider extends FabricTagProvider<Biome> {
    public AylythBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, RegistryKeys.BIOME, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(ModBiomeTags.IS_CLEARING).add(ModBiomeKeys.CLEARING, ModBiomeKeys.OVERGROWN_CLEARING);
        getOrCreateTagBuilder(ModBiomeTags.IS_COPSE).add(ModBiomeKeys.COPSE, ModBiomeKeys.CONIFEROUS_COPSE);
        getOrCreateTagBuilder(ModBiomeTags.IS_DEEPWOOD).add(ModBiomeKeys.DEEPWOOD, ModBiomeKeys.CONIFEROUS_DEEPWOOD);
        getOrCreateTagBuilder(ModBiomeTags.IS_CONIFEROUS).add(ModBiomeKeys.CONIFEROUS_COPSE, ModBiomeKeys.CONIFEROUS_DEEPWOOD);
        getOrCreateTagBuilder(ModBiomeTags.IS_FOREST_LIKE).add(ModBiomeKeys.OVERGROWN_CLEARING, ModBiomeKeys.COPSE, ModBiomeKeys.DEEPWOOD);
        getOrCreateTagBuilder(ModBiomeTags.IS_TAIGA_LIKE).addTag(ModBiomeTags.IS_CONIFEROUS).add(ModBiomeKeys.DEEPWOOD);
        getOrCreateTagBuilder(ModBiomeTags.GENERATES_SEEP).addOptionalTag(ConventionalBiomeTags.FOREST).addOptionalTag(ConventionalBiomeTags.TAIGA);
        getOrCreateTagBuilder(ModBiomeTags.HAS_WEAK_FOG).add(ModBiomeKeys.CLEARING, ModBiomeKeys.UPLANDS);
        getOrCreateTagBuilder(ModBiomeTags.HAS_AVERAGE_FOG).add(ModBiomeKeys.OVERGROWN_CLEARING);
        getOrCreateTagBuilder(ModBiomeTags.HAS_STRONG_FOG).add(ModBiomeKeys.COPSE, ModBiomeKeys.CONIFEROUS_COPSE, ModBiomeKeys.DEEPWOOD, ModBiomeKeys.CONIFEROUS_DEEPWOOD, ModBiomeKeys.MIRE, ModBiomeKeys.BOWELS);
        getOrCreateTagBuilder(BiomeTags.HAS_CLOSER_WATER_FOG).add(ModBiomeKeys.MIRE);
    }
}
