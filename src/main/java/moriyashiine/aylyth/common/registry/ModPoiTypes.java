package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.world.poi.PointOfInterestType;

public class ModPoiTypes {

    public static final PointOfInterestType SEEP = PointOfInterestHelper.register(AylythUtil.id("seep"), 0, 1, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD_SEEP);;

    public static void init() {}
}
