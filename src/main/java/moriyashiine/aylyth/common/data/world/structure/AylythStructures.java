package moriyashiine.aylyth.common.data.world.structure;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.structure.Structure;

public interface AylythStructures {

    RegistryKey<Structure> BLACK_WELL = bind("black_well");

    private static RegistryKey<Structure> bind(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, Aylyth.id(id));
    }
}
