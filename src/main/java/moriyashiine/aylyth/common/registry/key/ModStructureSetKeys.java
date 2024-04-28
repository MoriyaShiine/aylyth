package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureSet;

public class ModStructureSetKeys {
    public static final RegistryKey<StructureSet> BLACK_WELL = keyOf("black_well");

    private static RegistryKey<StructureSet> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE_SET, AylythUtil.id(id));
    }
}
