package moriyashiine.aylyth.common.data.world.structure;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.processor.StructureProcessorList;

public interface AylythStructureProcessorLists {

    RegistryKey<StructureProcessorList> BLACK_WELL = bind("black_well");

    private static RegistryKey<StructureProcessorList> bind(String id) {
        return RegistryKey.of(RegistryKeys.PROCESSOR_LIST, Aylyth.id(id));
    }
}
