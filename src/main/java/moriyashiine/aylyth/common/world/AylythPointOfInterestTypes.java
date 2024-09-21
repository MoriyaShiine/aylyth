package moriyashiine.aylyth.common.world;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlocks;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;

public interface AylythPointOfInterestTypes {

    RegistryKey<PointOfInterestType> SEEP = bind("seep");

    private static RegistryKey<PointOfInterestType> bind(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Aylyth.id(name));
    }

    static void register() {
        PointOfInterestHelper.register(Aylyth.id("seep"), 0, 1, AylythBlocks.OAK_SEEP, AylythBlocks.SPRUCE_SEEP, AylythBlocks.DARK_OAK_SEEP, AylythBlocks.YMPE_SEEP, AylythBlocks.SEEPING_WOOD_SEEP);
    }
}
