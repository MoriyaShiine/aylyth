package moriyashiine.aylyth.common.world.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.data.world.AylythBiomes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.stream.Stream;

import static net.minecraft.world.biome.source.util.MultiNoiseUtil.ParameterRange.of;
import static net.minecraft.world.biome.source.util.MultiNoiseUtil.createNoiseHypercube;

public class AylythBiomeSource extends BiomeSource {
    public static long SEED_HOLDER = 0L;
    public static final Codec<AylythBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.LONG.fieldOf("seed").orElse(SEED_HOLDER).stable().forGetter(source -> source.seed),
            RegistryOps.getEntryCodec(RegistryKeys.BIOME)
    ).apply(instance, instance.stable(AylythBiomeSource::new)));
    private final MultiNoiseUtil.Entries<RegistryEntry<Biome>> biomeEntries;
    private final Registry<Biome> biomeRegistry;
    private final long seed;

    public AylythBiomeSource(long seed, RegistryEntry.Reference<Registry<Biome>> biomeRegistry) {
        this(biomeRegistry.value(), seed, getEntries(biomeRegistry.value()));
    }

    private AylythBiomeSource(Registry<Biome> biomeRegistry, long seed, MultiNoiseUtil.Entries<RegistryEntry<Biome>> biomeEntries) {
        this.biomeRegistry = biomeRegistry;
        this.seed = seed;
        this.biomeEntries = biomeEntries;
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(seed));
        chunkRandom.skip(17292);
    }

    private static MultiNoiseUtil.Entries<RegistryEntry<Biome>> getEntries(Registry<Biome> registry) {
        ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryEntry<Biome>>> builder = new ImmutableList.Builder();
        //                                       temperature         humidity           continentalness   erosion                depth           weirdness          offset
        builder.add(Pair.of(createNoiseHypercube(of(-0.15f, 0.2f), of(-1.0f, -0.3f), of(-0.11f, 0.3f), of(-0.7799f, -0.375f), of(0.0f, 0.0f), of(-1.0f, -0.9333f), 0.0f), registry.getEntry(AylythBiomes.CLEARING).get()));
        builder.add(Pair.of(createNoiseHypercube(of(0.2f, 0.55f), of(-1.0f, -0.3f), of(-0.11f, 0.3f), of(-0.7799f, -0.375f), of(0.0f, 0.0f), of(-1.0f, -0.9333f), 0.0f), registry.getEntry(AylythBiomes.OVERGROWN_CLEARING).get()));
        builder.add(Pair.of(createNoiseHypercube(of(-0.45f, -0.15f), of(-0.1f, 0.1f), of(-0.11f, 0.3f), of(-0.7799f, -0.375f), of(0.0f, 0.0f), of(-1.0f, -0.9333f), 0.0f), registry.getEntry(AylythBiomes.COPSE).get()));
        builder.add(Pair.of(createNoiseHypercube(of(-0.15f, 0.2f), of(-0.1f, 0.1f), of(-0.11f, 0.3f), of(-0.7799f, -0.375f), of(0.0f, 0.0f), of(-1.0f, -0.9333f), 0.0f), registry.getEntry(AylythBiomes.DEEPWOOD).get()));
        builder.add(Pair.of(createNoiseHypercube(of(-1.0f, -0.45f), of(0.3f, 1.0f), of(-0.11f, 0.3f), of(-0.375f, -0.2225f), of(0.0f, 0.0f), of(-1.0f, -0.9333f), 0.0f), registry.getEntry(AylythBiomes.CONIFEROUS_COPSE).get()));
        builder.add(Pair.of(createNoiseHypercube(of(-0.45f, -0.15f), of(0.3f, 1.0f), of(-0.11f, 0.3f), of(-0.375f, -0.2225f), of(0.0f, 0.0f), of(-1.0f, -0.9333f), 0.0f), registry.getEntry(AylythBiomes.CONIFEROUS_DEEPWOOD).get()));
        builder.add(Pair.of(createNoiseHypercube(of(0.55f, 1.0f), of(-1.0f, 1.0f), of(0.3f, 1.0f), of(-1.0f, -0.78f), of(1.0f, 1.0f), of(-0.9333f, -0.44f), 0.0f), registry.getEntry(AylythBiomes.UPLANDS).get()));
        return new MultiNoiseUtil.Entries<>(builder.build());
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return biomeEntries.getEntries().stream().map(Pair::getSecond);
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        return this.biomeEntries.get(noise.sample(x, y, z));
    }

    public boolean matches(long seed) {
        return this.seed == seed;
    }
}
