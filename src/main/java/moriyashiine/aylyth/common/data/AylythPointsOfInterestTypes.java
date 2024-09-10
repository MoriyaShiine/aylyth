package moriyashiine.aylyth.common.data;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;

public interface AylythPointsOfInterestTypes {

    RegistryKey<PointOfInterestType> SEEP = bind("seep");

    private static RegistryKey<PointOfInterestType> bind(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, AylythUtil.id(name));
    }
}
