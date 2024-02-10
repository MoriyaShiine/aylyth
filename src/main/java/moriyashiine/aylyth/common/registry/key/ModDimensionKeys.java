package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class ModDimensionKeys {
	public static final RegistryKey<DimensionOptions> AYLYTH_DIMENSION_OPTIONS = RegistryKey.of(RegistryKeys.DIMENSION, new Identifier(Aylyth.MOD_ID, "aylyth"));
	public static final RegistryKey<DimensionType> AYLYTH_DIMENSION_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, AylythUtil.id("aylyth"));

	public static final RegistryKey<World> AYLYTH = RegistryKey.of(RegistryKeys.WORLD, new Identifier(Aylyth.MOD_ID, "aylyth"));
}
