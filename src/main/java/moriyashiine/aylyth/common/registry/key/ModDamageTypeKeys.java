package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModDamageTypeKeys {
    public static final RegistryKey<DamageType> YMPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id("ympe"));
    public static final RegistryKey<DamageType> YMPE_ENTITY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id("ympe_entity"));
    public static final RegistryKey<DamageType> UNBLOCKABLE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id("unblockable"));
    public static final RegistryKey<DamageType> SOUL_RIP = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id("soul_rip"));
}
