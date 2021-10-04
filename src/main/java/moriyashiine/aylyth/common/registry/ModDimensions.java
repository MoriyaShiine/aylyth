package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class ModDimensions {
	//refer to json files in data
	public static final RegistryKey<DimensionOptions> AYLYTH_DIMENSION_KEY = RegistryKey.of(
			Registry.DIMENSION_KEY,
			new Identifier(Aylyth.MOD_ID, "aylyth")
	);

	private static RegistryKey<World> AYLYTH = RegistryKey.of(
			Registry.WORLD_KEY,
			AYLYTH_DIMENSION_KEY.getValue()
	);

	private static final RegistryKey<DimensionType> AYLTH_DIMENSION_TYPE = RegistryKey.of(
			Registry.DIMENSION_TYPE_KEY,
			new Identifier(Aylyth.MOD_ID, "aylyth_type")
	);
}
