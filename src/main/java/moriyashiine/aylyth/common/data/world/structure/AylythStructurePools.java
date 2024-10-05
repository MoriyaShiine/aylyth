package moriyashiine.aylyth.common.data.world.structure;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.pool.StructurePool;

public interface AylythStructurePools {

    RegistryKey<StructurePool> BLACK_WELL = bind("black_well");

    private static RegistryKey<StructurePool> bind(String id) {
        return RegistryKey.of(RegistryKeys.TEMPLATE_POOL, Aylyth.id(id));
    }
}
