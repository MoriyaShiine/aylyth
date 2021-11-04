package moriyashiine.aylyth.common.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.dimension.layer.AylythBaseLayer;
import moriyashiine.aylyth.common.world.dimension.layer.ClearingLayer;
import moriyashiine.aylyth.common.world.dimension.layer.DeepForestLayer;
import moriyashiine.aylyth.common.world.dimension.layer.ForestLayer;
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
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.Map;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

public class AylythBiomeSource extends BiomeSource {
	private static Registry<Biome> biomeRegistry; //static instance for layers to grab raw ids
	public static final Codec<AylythBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((source) -> source.registry)).apply(instance, instance.stable(AylythBiomeSource::new)));
	private final BiomeLayerSampler biomeSampler;
	private final Registry<Biome> registry;
	
	public AylythBiomeSource(Registry<Biome> biomeRegistry) {
		this(0, biomeRegistry);
	}
	
	public AylythBiomeSource(long seed, Registry<Biome> biomeRegistry) {
		super(biomeRegistry.getEntries().stream().filter(entry -> entry.getKey().getValue().getNamespace().equals(Aylyth.MOD_ID)).map(Map.Entry::getValue).collect(Collectors.toList()));
		this.registry = biomeRegistry;
		this.biomeSampler = new BiomeLayerSampler(build(salt -> new CachingLayerContext(25, seed, salt)));
		AylythBiomeSource.biomeRegistry = registry;
	}
	
	private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(LongFunction<C> contextProvider) {
		LayerFactory<T> layer = AylythBaseLayer.INSTANCE.create(contextProvider.apply(1L));
		layer = ForestLayer.NORMAL.create(contextProvider.apply(777L), layer);
		layer = ForestLayer.CONIFEROUS.create(contextProvider.apply(888L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(666L), layer);
		layer = DeepForestLayer.CONIFEROUS.create(contextProvider.apply(3111L), layer);
		layer = DeepForestLayer.NORMAL.create(contextProvider.apply(3110L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(6606L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(656L), layer);
		layer = ForestLayer.NORMAL.create(contextProvider.apply(194L), layer);
		layer = ForestLayer.CONIFEROUS.create(contextProvider.apply(1188L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(6663L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(6261L), layer);
		layer = ClearingLayer.INSTANCE.create(contextProvider.apply(9909L), layer);
		layer = ScaleLayer.FUZZY.create(contextProvider.apply(60660L), layer);
		layer = ScaleLayer.NORMAL.create(contextProvider.apply(50160L), layer);
		return layer;
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
