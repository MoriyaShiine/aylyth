package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

public class ModDimensionKeys {
	public static final RegistryKey<DimensionOptions> AYLYTH_DIMENSION_OPTIONS = RegistryKey.of(RegistryKeys.DIMENSION, AylythUtil.id("aylyth"));
	public static final RegistryKey<DimensionType> AYLYTH_DIMENSION_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, AylythUtil.id("aylyth"));
	public static final RegistryKey<ChunkGeneratorSettings> AYLYTH_CHUNK_GEN_SETTINGS = RegistryKey.of(RegistryKeys.CHUNK_GENERATOR_SETTINGS, AylythUtil.id("aylyth_settings"));

	public static final RegistryKey<World> AYLYTH = RegistryKey.of(RegistryKeys.WORLD, AylythUtil.id("aylyth"));
}
