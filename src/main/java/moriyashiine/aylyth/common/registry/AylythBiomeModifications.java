package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.data.tag.AylythBiomeTags;
import moriyashiine.aylyth.common.data.world.AylythPlacedFeatures;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.world.gen.GenerationStep;

public final class AylythBiomeModifications {
    private AylythBiomeModifications() {}

    public static void register() {
        // TODO these need to be changed, it replaces structure logs too, obv.
        BiomeModifications.create(AylythUtil.id("world_features"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.tag(AylythBiomeTags.GENERATES_SEEP),
                        context -> {
                            context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, AylythPlacedFeatures.OAK_SEEP);
                            context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, AylythPlacedFeatures.SPRUCE_SEEP);
                            context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, AylythPlacedFeatures.DARK_OAK_SEEP);
                        }
                );
    }
}
