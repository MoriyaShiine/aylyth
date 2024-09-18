package moriyashiine.aylyth.common.data.world;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public interface AylythDimensionData {

	RegistryKey<DimensionType> DIMENSION_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, AylythUtil.id("aylyth"));
	RegistryKey<DimensionOptions> DIMENSION_OPTIONS = RegistryKey.of(RegistryKeys.DIMENSION, AylythUtil.id("aylyth"));
	RegistryKey<ChunkGeneratorSettings> CHUNK_GEN_SETTINGS = RegistryKey.of(RegistryKeys.CHUNK_GENERATOR_SETTINGS, AylythUtil.id("aylyth_settings"));
	RegistryKey<World> WORLD = RegistryKey.of(RegistryKeys.WORLD, AylythUtil.id("aylyth"));
}
