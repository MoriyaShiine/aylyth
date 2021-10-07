package moriyashiine.aylyth.common.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.dimension.layer.AylythBaseLayer;
import moriyashiine.aylyth.common.world.dimension.layer.DeepForestLayer;
import moriyashiine.aylyth.mixin.BiomeLayerSamplerAccessor;
import net.minecraft.SharedConstants;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.Map;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

public class AylythBiomeSource extends BiomeSource {
	private static Registry<Biome> biomeRegistry; //static instance for layers to grab raw ids
	public static final Codec<AylythBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance
			.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((source) -> source.registry))
			.apply(instance, instance.stable(AylythBiomeSource::new)));
	private final BiomeLayerSampler biomeSampler;
	private final long seed; //set some seeds later
	private final Registry<Biome> registry;

	public AylythBiomeSource(Registry<Biome> biomeRegistry) {
		this(0, biomeRegistry);
	}

	public AylythBiomeSource(long seed, Registry<Biome> biomeRegistry) {
		super(biomeRegistry.getEntries().stream().filter(entry -> entry.getKey().getValue().getNamespace()
				.equals(Aylyth.MOD_ID)).map(Map.Entry::getValue).collect(Collectors.toList()));
		this.seed = seed;
		this.registry = biomeRegistry;
		this.biomeSampler = build(seed, 4, 4);
		AylythBiomeSource.biomeRegistry = registry;
	}

	public static BiomeLayerSampler build(long seed, int biomeSize, int riverSize) {
		LayerFactory<CachingLayerSampler> layerFactory = build(biomeSize, riverSize, (salt) -> new CachingLayerContext(25, seed, salt));
		return new BiomeLayerSampler(layerFactory);
	}

	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(int biomeSize, int riverSize, LongFunction<C> contextProvider) {
		LayerFactory<T> layer = AylythBaseLayer.INSTANCE.create(contextProvider.apply(1L));
		layer = DeepForestLayer.INSTANCE.create(contextProvider.apply(3110L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(666L), layer);
		layer = ScaleLayer.FUZZY.create(contextProvider.apply(919L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(166L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(616L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(661L), layer);

		//add more and more layers to add depth to the world
		return layer;
	}

	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long seed, ParentedLayer layer, LayerFactory<T> parent, int count, LongFunction<C> contextProvider) {
		LayerFactory<T> layerFactory = parent;

		for (int i = 0; i < count; ++i) {
			layerFactory = layer.create(contextProvider.apply(seed + (long) i), layerFactory);
		}

		return layerFactory;
	}

	public static int getId(Identifier biomeId) {
		return biomeRegistry.getRawId(biomeRegistry.get(biomeId));
	}

	protected Codec<? extends BiomeSource> getCodec() {
		return CODEC;
	}

	@Override
	public BiomeSource withSeed(long seed) {
		return new AylythBiomeSource(seed, registry);
	}

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
		int biomeId = ((BiomeLayerSamplerAccessor) biomeSampler).aylyth_getSampler().sample(biomeX, biomeZ);
		Biome biome = registry.get(biomeId);
		if (biome == null) {
			if (SharedConstants.isDevelopment) {
				throw Util.throwOrPause(new IllegalStateException("Unknown biome id: " + biomeId));
			}
			else {
				RegistryKey<Biome> backup = BuiltinBiomes.fromRawId(0);
				return registry.get(backup);
			}
		}
		return biome;
	}
}
