package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;

public class ModPOITypes {

    public static void init() {
        PointOfInterestHelper.register(SEEP.getValue(), 0, 1, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD_SEEP);
    }

    public static final RegistryKey<PointOfInterestType> SEEP = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, AylythUtil.id("seep"));
}
