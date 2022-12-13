package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class ModDimensionKeys {

	public static final RegistryKey<DimensionOptions> AYLYTH_DIMENSION_OPTIONS = RegistryKey.of(Registry.DIMENSION_KEY, new Identifier(Aylyth.MOD_ID, "aylyth"));
	
	public static final RegistryKey<World> AYLYTH = RegistryKey.of(Registry.WORLD_KEY, new Identifier(Aylyth.MOD_ID, "aylyth"));
}
