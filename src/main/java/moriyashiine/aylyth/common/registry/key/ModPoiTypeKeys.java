package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;

public class ModPoiTypeKeys {
    public static final RegistryKey<PointOfInterestType> SEEP = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, AylythUtil.id("seep"));
}
