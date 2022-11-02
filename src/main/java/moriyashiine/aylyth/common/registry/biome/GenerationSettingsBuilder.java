package moriyashiine.aylyth.common.registry.biome;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class GenerationSettingsBuilder {

    private static final Object2IntMap<RegistryEntry<PlacedFeature>> FEATURE_PLACE = new Object2IntOpenHashMap<>();
    GenerationSettings.Builder delegate;
    List<Pair<Integer, RegistryEntry<PlacedFeature>>> features = new LinkedList<>();

    GenerationSettingsBuilder(@NotNull GenerationSettings.Builder builder) {
        this.delegate = builder;
    }

    /**
     *
     * @return a new GenerationSettingsBuilder with nothing added
     */
    public static GenerationSettingsBuilder builder() {
        return new GenerationSettingsBuilder(new GenerationSettings.Builder());
    }

    /**
     *
     * @return GenerationSettings with nothing added
     */
    public static GenerationSettings none() {
        return GenerationSettings.INSTANCE;
    }

    public GenerationSettingsBuilder rawGenFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.RAW_GENERATION, featureEntry);
    }

    public GenerationSettingsBuilder lakesFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.LAKES, featureEntry);
    }

    public GenerationSettingsBuilder localModFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS, featureEntry);
    }

    public GenerationSettingsBuilder undergroudStructFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.UNDERGROUND_STRUCTURES, featureEntry);
    }

    public GenerationSettingsBuilder surfaceStructFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, featureEntry);
    }

    public GenerationSettingsBuilder strongholdsFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.STRONGHOLDS, featureEntry);
    }

    public GenerationSettingsBuilder undergroundOresFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.UNDERGROUND_ORES, featureEntry);
    }

    public GenerationSettingsBuilder undergroundDecoFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, featureEntry);
    }

    public GenerationSettingsBuilder fluidSpringsFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.FLUID_SPRINGS, featureEntry);
    }

    public GenerationSettingsBuilder vegetalDecoFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.VEGETAL_DECORATION, featureEntry);
    }

    public GenerationSettingsBuilder topLayerFeature(@NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, featureEntry);
    }

    public GenerationSettingsBuilder feature(@NonNegative int step, @NotNull RegistryEntry<PlacedFeature> featureEntry) {
        FEATURE_PLACE.putIfAbsent(featureEntry, FEATURE_PLACE.size());
        features.add(Pair.of(step, featureEntry));
        return this;
    }

    protected GenerationSettingsBuilder addFeature(@NotNull GenerationStep.Feature step, @NotNull RegistryEntry<PlacedFeature> featureEntry) {
        return feature(step.ordinal(), featureEntry);
    }

    public GenerationSettingsBuilder add(Consumer<GenerationSettings.Builder> builderConsumer) {
        var temp = new GenerationSettings.Builder();
        builderConsumer.accept(temp);
        var tempBuilt = temp.build();
        var featureList = tempBuilt.getFeatures();
        for (int i = 0; i < featureList.size(); i++) {
            for (RegistryEntry<PlacedFeature> feature : featureList.get(i)) {
                feature(i, feature);
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

    public GenerationSettingsBuilder airCarver(@NotNull RegistryEntry<? extends ConfiguredCarver<?>> configuredCarverEntry) {
        delegate.carver(GenerationStep.Carver.AIR, configuredCarverEntry);
        return this;
    }

    public GenerationSettingsBuilder liquidCarver(@NotNull RegistryEntry<? extends ConfiguredCarver<?>> configuredCarverEntry) {
        delegate.carver(GenerationStep.Carver.LIQUID, configuredCarverEntry);
        return this;
    }

    public GenerationSettings build() {
        features.stream().sorted(Comparator.comparingInt(o -> FEATURE_PLACE.getInt(o.getSecond()))).forEach(pair -> delegate.feature(pair.getFirst(), pair.getSecond()));
        return delegate.build();
    }
}
