package moriyashiine.aylyth.common.world.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.registry.ModBiomes;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import static net.minecraft.world.biome.source.util.MultiNoiseUtil.createNoiseHypercube;
import static net.minecraft.world.biome.source.util.MultiNoiseUtil.ParameterRange.of;

public class AylythBiomeSource extends BiomeSource {
    public static long SEED_HOLDER = 0L;
    public static final Codec<AylythBiomeSource> CODEC =
            RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.LONG.fieldOf("seed").orElseGet(() -> SEED_HOLDER).stable().forGetter(source -> source.seed),
                    RegistryOps.createRegistryCodec(Registry.BIOME_KEY).forGetter((biomeSource) -> biomeSource.biomeRegistry))
                    .apply(instance, instance.stable(AylythBiomeSource::new)));
    private final MultiNoiseUtil.Entries<RegistryEntry<Biome>> biomeEntries;
    private final Registry<Biome> biomeRegistry;
    private final long seed;

    public AylythBiomeSource(long seed, Registry<Biome> biomeRegistry) {
        this(biomeRegistry, seed, getEntries(biomeRegistry));
    }

    private AylythBiomeSource(Registry<Biome> biomeRegistry, long seed, MultiNoiseUtil.Entries<RegistryEntry<Biome>> biomeEntries) {
        super(biomeEntries.getEntries().stream().map(Pair::getSecond));
        this.biomeRegistry = biomeRegistry;
        this.seed = seed;
        this.biomeEntries = biomeEntries;
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(seed));
        chunkRandom.skip(17292);
    }

    private static MultiNoiseUtil.Entries<RegistryEntry<Biome>> getEntries(Registry<Biome> registry) {
        ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryEntry<Biome>>> builder = new ImmutableList.Builder();
        // uses the first params I found for plains for the clearing types, forest for forest/deep forest, and taiga for the coniferous variants.
        //                                       temperature         humidity           continentalness   erosion                depth           weirdness          offset
        builder.add(Pair.of(createNoiseHypercube(of(-0.45F, -0.15F), of(-1.0F, -0.35F), of(-0.11F, 0.3F), of(-0.7799F, -0.375F), of(0.0F, 0.0F), of(-1.0F, -0.9333F), 0.0F), registry.getEntry(ModBiomes.CLEARING_ID).get()));
        builder.add(Pair.of(createNoiseHypercube(of(-0.45F, -0.15F), of(-1.0F, -0.35F), of(-0.11F, 0.3F), of(-0.7799F, -0.375F), of(0.0F, 0.0F), of(-1.0F, -0.9333F), 0.0F), registry.getEntry(ModBiomes.OVERGROWN_CLEARING_ID).get()));
        builder.add(Pair.of(createNoiseHypercube(of(-0.45F, -0.15F), of(-0.1F, 0.1F), of(-0.11F, 0.3F), of(-0.7799F, -0.375F), of(0.0F, 0.0F), of(-1.0F, -0.9333F), 0.0F), registry.getEntry(ModBiomes.COPSE_ID).get()));
        builder.add(Pair.of(createNoiseHypercube(of(-0.45F, -0.15F), of(-0.1F, 0.1F), of(-0.11F, 0.3F), of(-0.7799F, -0.375F), of(0.0F, 0.0F), of(-1.0F, -0.9333F), 0.0F), registry.getEntry(ModBiomes.DEEPWOOD_ID).get()));
        builder.add(Pair.of(createNoiseHypercube(of(-1.0F, -0.45F), of(0.3F, 1.0F), of(-0.11F, 0.3F), of(-0.375F, -0.2225F), of(0.0F, 0.0F), of(-1.0F, -0.9333F), 0.0F), registry.getEntry(ModBiomes.CONIFEROUS_FOREST_ID).get()));
        builder.add(Pair.of(createNoiseHypercube(of(-1.0F, -0.45F), of(0.3F, 1.0F), of(-0.11F, 0.3F), of(-0.375F, -0.2225F), of(0.0F, 0.0F), of(-1.0F, -0.9333F), 0.0F), registry.getEntry(ModBiomes.DEEP_CONIFEROUS_FOREST_ID).get()));
        return new MultiNoiseUtil.Entries<>(builder.build());
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        return this.biomeEntries.method_39530(noise.sample(x, y, z));
    }

    public boolean matches(long seed) {
        return this.seed == seed;
    }
}
