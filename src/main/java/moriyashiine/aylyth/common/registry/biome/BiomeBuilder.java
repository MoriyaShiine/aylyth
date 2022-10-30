package moriyashiine.aylyth.common.registry.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BiomeBuilder {

    Biome.Builder delegate;

    BiomeBuilder(Biome.Builder builder) {
        this.delegate = builder;
    }

    /**
     * @return A new BiomeBuilder
     * @exception IllegalStateException if the necessary precipitation, temperature, downfall, BiomeEffects,
     * SpawnSettings, and GenerationSettings values are not filled
     */
    public static BiomeBuilder builder() {
        return new BiomeBuilder(new Biome.Builder());
    }

    /**
     * Only sets these related aspects
     * @param precipitation Determines things like weather & entity variants
     * @param temperature Determines if the biome is cold or hot, if ice can form, if snow can fall, etc
     * @param downfall Determines the humidity of the biome, which determines fire spread
     * @return A half-baked builder. MUST still fill BiomeEffects, SpawnSettings, and GenerationSettings
     * @exception IllegalStateException if the remaining necessary BiomeEffects, SpawnSettings, and GenerationSettings
     * values are not filled
     */
    public static BiomeBuilder builder(@NotNull Biome.Precipitation precipitation, float temperature, float downfall) {
        var builder = new Biome.Builder();
        builder.precipitation(precipitation);
        builder.temperature(temperature);
        builder.downfall(downfall);
        return new BiomeBuilder(builder);
    }

    /**
     *
     * @param precipitation Determines things like weather & entity variants
     * @param temperature Determines if the biome is cold or hot, if ice can form, if snow can fall, etc
     * @param downfall Determines the humidity of the biome, which determines fire spread
     * @param biomeEffects BiomeEffects of the biome
     * @param spawnSettings SpawnSettings of the biome
     * @param generationSettings GenerationSettings of the biome
     * @see Biome#getPrecipitation() For further searching on how precipitation is used
     * @see Biome#getTemperature() For further searching on how temperature is used
     * @see net.minecraft.world.World#hasHighHumidity(BlockPos) Downfall explanation
     * @return A BiomeBuilder with all the necessary values filled
     */
    public static BiomeBuilder builder(@NotNull Biome.Precipitation precipitation, float temperature, float downfall,
                                       @NotNull BiomeEffects biomeEffects, @NotNull SpawnSettings spawnSettings,
                                       @NotNull GenerationSettings generationSettings) {
        var builder = new Biome.Builder();
        builder.precipitation(precipitation);
        builder.temperature(temperature);
        builder.downfall(downfall);
        builder.effects(biomeEffects);
        builder.spawnSettings(spawnSettings);
        builder.generationSettings(generationSettings);
        return new BiomeBuilder(builder);
    }

    /**
     * Makes a BiomeBuilder with the following defaults:
     * Precipitation: NONE,
     * Temperature: 0.0F,
     * Downfall: 0.0F,
     * Special Effects: Defaulted values from BiomeEffectsBuilder,
     * Spawn Settings: Empty,
     * Generation Settings: Empty
     * @see BiomeEffectsBuilder
     * @see SpawnSettingsBuilder
     * @see GenerationSettingsBuilder
     * @return A defaulted BiomeBuilder
     */
    public static BiomeBuilder defaultedBuilder() {
        return builder(Biome.Precipitation.NONE, 0.0F, 0.0F, BiomeEffectsBuilder.defaulted(),
                SpawnSettingsBuilder.none(), GenerationSettingsBuilder.none());
    }

    public BiomeBuilder precipitation(@NotNull Biome.Precipitation precipitation) {
        delegate.precipitation(precipitation);
        return this;
    }

    public BiomeBuilder noPrecipitation() {
        return precipitation(Biome.Precipitation.NONE);
    }

    public BiomeBuilder rainy() {
        return precipitation(Biome.Precipitation.RAIN);
    }

    public BiomeBuilder snowy() {
        return precipitation(Biome.Precipitation.SNOW);
    }

    public BiomeBuilder temperature(float temperature) {
        delegate.temperature(temperature);
        return this;
    }

    public BiomeBuilder temperature(double temperature) {
        delegate.temperature((float)temperature);
        return this;
    }

    public BiomeBuilder downfall(float downfall) {
        delegate.downfall(downfall);
        return this;
    }

    public BiomeBuilder downfall(double downfall) {
        delegate.downfall((float)downfall);
        return this;
    }

    public BiomeBuilder biomeEffects(@NotNull BiomeEffects biomeEffects) {
        delegate.effects(biomeEffects);
        return this;
    }

    public BiomeBuilder biomeEffects(@NonNegative int fogColor, @NonNegative int waterColor, @NonNegative int waterFogColor, @NonNegative int skyColor) {
        return biomeEffects(BiomeEffectsBuilder.builder(fogColor, waterColor, waterFogColor, skyColor).build());
    }

    public BiomeBuilder biomeEffects(@NonNegative int fogColor, @NonNegative int waterColor,
                                     @NonNegative int waterFogColor, @NonNegative int skyColor,
                                     @NotNull Consumer<BiomeEffectsBuilder> builderConsumer) {
        var builder = BiomeEffectsBuilder.builder(fogColor, waterColor, waterFogColor, skyColor);
        builderConsumer.accept(builder);
        return biomeEffects(builder.build());
    }

    public BiomeBuilder biomeEffects(@NotNull Consumer<BiomeEffectsBuilder> builderConsumer) {
        var builder = BiomeEffectsBuilder.builder();
        builderConsumer.accept(builder);
        delegate.effects(builder.build());
        return this;
    }

    public BiomeBuilder defaultedBiomeEffects(@NotNull Consumer<BiomeEffectsBuilder> builderConsumer) {
        var builder = BiomeEffectsBuilder.defaultedBuilder();
        builderConsumer.accept(builder);
        delegate.effects(builder.build());
        return this;
    }

    public BiomeBuilder noSpawnSettings() {
        delegate.spawnSettings(SpawnSettingsBuilder.none());
        return this;
    }

    public BiomeBuilder spawnSettings(@NotNull SpawnSettings spawnSettings) {
        delegate.spawnSettings(spawnSettings);
        return this;
    }

    public BiomeBuilder spawnSettings(@NotNull Consumer<SpawnSettingsBuilder> builderConsumer) {
        var builder = SpawnSettingsBuilder.builder();
        builderConsumer.accept(builder);
        delegate.spawnSettings(builder.build());
        return this;
    }

    public BiomeBuilder noGenerationSettings() {
        delegate.generationSettings(GenerationSettingsBuilder.none());
        return this;
    }

    public BiomeBuilder generationSettings(@NotNull GenerationSettings generationSettings) {
        delegate.generationSettings(generationSettings);
        return this;
    }

    public BiomeBuilder generationSettings(@NotNull Consumer<GenerationSettingsBuilder> builderConsumer) {
        var builder = GenerationSettingsBuilder.builder();
        builderConsumer.accept(builder);
        delegate.generationSettings(builder.build());
        return this;
    }

    public Biome build() {
        return delegate.build();
    }
}
