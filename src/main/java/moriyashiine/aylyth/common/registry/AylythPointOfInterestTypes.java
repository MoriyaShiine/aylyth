package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;

public interface AylythPointOfInterestTypes {

    RegistryKey<PointOfInterestType> SEEP = bind("seep");

    private static RegistryKey<PointOfInterestType> bind(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, AylythUtil.id(name));
    }

    static void register() {
        PointOfInterestHelper.register(AylythUtil.id("seep"), 0, 1, AylythBlocks.OAK_SEEP, AylythBlocks.SPRUCE_SEEP, AylythBlocks.DARK_OAK_SEEP, AylythBlocks.YMPE_SEEP, AylythBlocks.SEEPING_WOOD_SEEP);
    }
}
