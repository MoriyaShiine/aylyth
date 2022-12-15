package moriyashiine.aylyth.datagen.worldgen.biomes.util;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.NotNull;

// NOTE: could maybe also extend BiomeEffects itself to add special abilities via mixin
public class BiomeEffectsBuilder {

    BiomeEffects.Builder delegate;

    BiomeEffectsBuilder(@NotNull BiomeEffects.Builder builder) {
        this.delegate = builder;
    }

    /**
     * @return A new BiomeEffectsBuilder
     * @exception IllegalStateException if the necessary fog, water, water fog, and sky color values are not filled
     */
    public static BiomeEffectsBuilder builder() {
        return new BiomeEffectsBuilder(new BiomeEffects.Builder());
    }

    /**
     * Default values are as follows:
     * Fog Color: 0x000000
     * Water Color: 0x000000
     * Water Fog Color: 0x000000
     * Sky Color: 0x000000
     * @return BiomeEffectsBuilder with defaulted values
     */
    public static BiomeEffectsBuilder defaultedBuilder() {
        return builder(0x000000, 0x000000, 0x000000, 0x000000);
    }

    /**
     *
     * @param fogColor Color of the fog when in the biome
     * @param waterColor Color of the water when in the biome
     * @param waterFogColor Color of the fog underwater when in the biome
     * @param skyColor Color of the sky when in the biome
     * @return BiomeEffectsBuilder with given required values
     */
    public static BiomeEffectsBuilder builder(@NonNegative int fogColor, @NonNegative int waterColor, @NonNegative int waterFogColor, @NonNegative int skyColor) {
        var builder = new BiomeEffects.Builder();
        builder.fogColor(fogColor);
        builder.waterColor(waterColor);
        builder.waterFogColor(waterFogColor);
        builder.skyColor(skyColor);
        return new BiomeEffectsBuilder(builder);
    }

    /**
     *
     * @return BiomeEffects returns the result of a defaulted builder
     */
    public static BiomeEffects defaulted() {
        return builder().build();
    }

    public BiomeEffectsBuilder fogColor(@NonNegative int fogColor) {
        delegate.fogColor(fogColor);
        return this;
    }

    public BiomeEffectsBuilder waterColor(@NonNegative int waterColor) {
        delegate.waterColor(waterColor);
        return this;
    }

    public BiomeEffectsBuilder waterFogColor(@NonNegative int waterFogColor) {
        delegate.waterFogColor(waterFogColor);
        return this;
    }

    public BiomeEffectsBuilder skyColor(@NonNegative int skyColor) {
        delegate.skyColor(skyColor);
        return this;
    }

    public BiomeEffectsBuilder foliageColor(@NonNegative int foliageColor) {
        delegate.foliageColor(foliageColor);
        return this;
    }

    public BiomeEffectsBuilder grassColor(@NonNegative int grassColor) {
        delegate.grassColor(grassColor);
        return this;
    }

    public BiomeEffectsBuilder grassColorModifier(@NotNull BiomeEffects.GrassColorModifier grassColorModifier) {
        delegate.grassColorModifier(grassColorModifier);
        return this;
    }

    public BiomeEffectsBuilder particleConfig(@NotNull BiomeParticleConfig particleConfig) {
        delegate.particleConfig(particleConfig);
        return this;
    }

    public BiomeEffectsBuilder particleConfig(@NotNull ParticleEffect effect, float probability) {
        delegate.particleConfig(new BiomeParticleConfig(effect, probability));
        return this;
    }

    public BiomeEffectsBuilder loopSound(@NotNull SoundEvent sound) {
        delegate.loopSound(sound);
        return this;
    }

    public BiomeEffectsBuilder moodSound(@NotNull BiomeMoodSound moodSound) {
        delegate.moodSound(moodSound);
        return this;
    }

    public BiomeEffectsBuilder additionsSound(@NotNull BiomeAdditionsSound additionsSound) {
        delegate.additionsSound(additionsSound);
        return this;
    }

    public BiomeEffectsBuilder music(@NotNull MusicSound music) {
        delegate.music(music);
        return this;
    }

    public BiomeEffects build() {
        return delegate.build();
    }
}
