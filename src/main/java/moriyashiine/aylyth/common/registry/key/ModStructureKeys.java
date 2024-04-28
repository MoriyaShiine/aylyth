package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.structure.Structure;

public class ModStructureKeys {
    public static final RegistryKey<Structure> BLACK_WELL = keyOf("black_well");

    private static RegistryKey<Structure> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, AylythUtil.id(id));
    }
}
