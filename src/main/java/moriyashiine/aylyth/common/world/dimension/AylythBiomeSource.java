package moriyashiine.aylyth.common.world.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.registry.ModBiomes;
import moriyashiine.aylyth.common.registry.ModWorldGenerators;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;

import java.util.function.Supplier;

public class AylythBiomeSource extends BiomeSource {
    public static long SEED_HOLDER = 0L;
    public static final Codec<AylythBiomeSource> CODEC =
            RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.LONG.fieldOf("seed").orElseGet(() -> SEED_HOLDER).stable().forGetter(source -> source.seed),
                    RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((biomeSource) -> biomeSource.biomeRegistry))
                    .apply(instance, instance.stable(AylythBiomeSource::new)));
    private final MultiNoiseUtil.Entries<Supplier<Biome>> biomeEntries;
    private final Registry<Biome> biomeRegistry;
    private final long seed;

    public AylythBiomeSource(long seed, Registry<Biome> biomeRegistry) {
        this(biomeRegistry, seed, getEntries(biomeRegistry));
    }

    private AylythBiomeSource(Registry<Biome> biomeRegistry, long seed, MultiNoiseUtil.Entries<Supplier<Biome>> biomeEntries) {
        super(biomeEntries.getEntries().stream().map(Pair::getSecond));
        this.biomeRegistry = biomeRegistry;
        this.seed = seed;
        this.biomeEntries = null;
        ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(seed));
        chunkRandom.skip(17292);
    }

    private static MultiNoiseUtil.Entries<Supplier<Biome>> getEntries(Registry<Biome> registry) {
        ImmutableList.Builder builder = ImmutableList.builder();
       //do stuff
        return new MultiNoiseUtil.Entries<Supplier<Biome>>(builder.build());
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new AylythBiomeSource(seed, this.biomeRegistry);
    }

    @Override
    public Biome getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        return this.biomeEntries.method_39530(noise.sample(x, y, z), () -> biomeRegistry.get(ModBiomes.FOREST_ID)).get();
    }

    public boolean matches(long seed) {
        return this.seed == seed;
    }
}
