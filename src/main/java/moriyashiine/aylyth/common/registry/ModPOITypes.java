package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;

public class ModPOITypes {

    public static void init() {}

    public static final RegistryKey<PointOfInterestType> SEEP = RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(Aylyth.MOD_ID, "seep"));

    static {
        PointOfInterestHelper.register(SEEP.getValue(), 0, 1, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD_SEEP);
    }
}
