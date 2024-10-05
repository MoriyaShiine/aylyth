package moriyashiine.aylyth.common.data.world.structure;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;

public interface AylythStructureSets {

    RegistryKey<StructureSet> BLACK_WELL = keyOf("black_well");

    private static RegistryKey<StructureSet> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE_SET, Aylyth.id(id));
    }
}
