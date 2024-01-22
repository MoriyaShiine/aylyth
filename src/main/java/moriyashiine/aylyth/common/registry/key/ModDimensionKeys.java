package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class ModDimensionKeys {

	public static final RegistryKey<DimensionOptions> AYLYTH_DIMENSION_OPTIONS = RegistryKey.of(RegistryKeys.DIMENSION, new Identifier(Aylyth.MOD_ID, "aylyth"));
	
	public static final RegistryKey<World> AYLYTH = RegistryKey.of(RegistryKeys.WORLD, new Identifier(Aylyth.MOD_ID, "aylyth"));
}
