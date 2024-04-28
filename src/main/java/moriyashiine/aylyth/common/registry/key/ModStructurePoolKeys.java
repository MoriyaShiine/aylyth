package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.pool.StructurePool;

public class ModStructurePoolKeys {
    public static final RegistryKey<StructurePool> BLACK_WELL = keyOf("black_well");

    private static RegistryKey<StructurePool> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.TEMPLATE_POOL, AylythUtil.id(id));
    }
}
