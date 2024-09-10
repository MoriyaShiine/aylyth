package moriyashiine.aylyth.datagen.common.util.biome;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class GenerationSettingsBuilder {

    private static final Reference2IntMap<RegistryKey<PlacedFeature>> FEATURE_PLACE = new Reference2IntOpenHashMap<>();
    GenerationSettings.LookupBackedBuilder delegate;
    List<Pair<Integer, RegistryKey<PlacedFeature>>> features = new LinkedList<>();

    GenerationSettingsBuilder(@NotNull GenerationSettings.LookupBackedBuilder builder) {
        this.delegate = builder;
    }

    /**
     *
     * @return a new GenerationSettingsBuilder with nothing added
     */
    public static GenerationSettingsBuilder builder(RegistryEntryLookup<PlacedFeature> placedFeatures, RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers) {
        return new GenerationSettingsBuilder(new GenerationSettings.LookupBackedBuilder(placedFeatures, configuredCarvers));
    }

    /**
     *
     * @return GenerationSettings with nothing added
     */
    public static GenerationSettings none() {
        return GenerationSettings.INSTANCE;
    }

    public GenerationSettingsBuilder rawGenFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.RAW_GENERATION, featureEntry);
    }

    public GenerationSettingsBuilder lakesFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.LAKES, featureEntry);
    }

    public GenerationSettingsBuilder localModFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS, featureEntry);
    }

    public GenerationSettingsBuilder undergroudStructFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, featureEntry);
    }

    public GenerationSettingsBuilder surfaceStructFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, featureEntry);
    }

    public GenerationSettingsBuilder strongholdsFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.STRONGHOLDS, featureEntry);
    }

    public GenerationSettingsBuilder undergroundOresFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.UNDERGROUND_ORES, featureEntry);
    }

    public GenerationSettingsBuilder undergroundDecoFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, featureEntry);
    }

    public GenerationSettingsBuilder fluidSpringsFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.FLUID_SPRINGS, featureEntry);
    }

    public GenerationSettingsBuilder vegetalDecoFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.VEGETAL_DECORATION, featureEntry);
    }

    public GenerationSettingsBuilder topLayerFeature(@NotNull RegistryKey<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, featureEntry);
    }

    public GenerationSettingsBuilder feature(int step, @NotNull RegistryKey<PlacedFeature> featureEntry) {
        FEATURE_PLACE.putIfAbsent(featureEntry, FEATURE_PLACE.size());
        features.add(Pair.of(step, featureEntry));
        return this;
    }

    protected GenerationSettingsBuilder addFeature(@NotNull GenerationStep.Feature step, @NotNull RegistryKey<PlacedFeature> featureEntry) {
        return feature(step.ordinal(), featureEntry);
    }

    public GenerationSettingsBuilder add(Consumer<GenerationSettings.LookupBackedBuilder> builderConsumer) {
        GenerationSettings.LookupBackedBuilder temp = new GenerationSettings.LookupBackedBuilder(delegate.placedFeatureLookup, delegate.configuredCarverLookup);
        builderConsumer.accept(temp);
        GenerationSettings tempBuilt = temp.build();
        List<RegistryEntryList<PlacedFeature>> featureList = tempBuilt.getFeatures();
        for (int i = 0; i < featureList.size(); i++) {
            for (RegistryEntry<PlacedFeature> feature : featureList.get(i)) {
                feature(i, feature.getKey().get());
            }
        }
        for (RegistryEntry<ConfiguredCarver<?>> carver : tempBuilt.getCarversForStep(GenerationStep.Carver.AIR)) {
            airCarver(carver);
        }
        for (RegistryEntry<ConfiguredCarver<?>> carver : tempBuilt.getCarversForStep(GenerationStep.Carver.LIQUID)) {
            liquidCarver(carver);
        }
        return this;
    }

    public GenerationSettingsBuilder airCarver(@NotNull RegistryEntry<ConfiguredCarver<?>> configuredCarverEntry) {
        delegate.carver(GenerationStep.Carver.AIR, configuredCarverEntry);
        return this;
    }

    public GenerationSettingsBuilder liquidCarver(@NotNull RegistryEntry<ConfiguredCarver<?>> configuredCarverEntry) {
        delegate.carver(GenerationStep.Carver.LIQUID, configuredCarverEntry);
        return this;
    }

    public GenerationSettings build() {
        features.stream().sorted(Comparator.comparingInt(o -> FEATURE_PLACE.getInt(o.getSecond()))).forEach(pair -> delegate.addFeature(pair.getFirst(), delegate.placedFeatureLookup.getOrThrow(pair.getSecond())));
        return delegate.build();
    }
}
