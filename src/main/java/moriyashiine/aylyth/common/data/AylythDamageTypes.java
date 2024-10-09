package moriyashiine.aylyth.common.data;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface AylythDamageTypes {

    RegistryKey<DamageType> YMPE = bind("ympe");
    RegistryKey<DamageType> YMPE_ENTITY = bind("ympe_entity");
    RegistryKey<DamageType> KILLING_BLOW = bind("wreathed_hind_killing_blow");
    RegistryKey<DamageType> SOUL_RIP = bind("soul_rip");
    RegistryKey<DamageType> BLIGHT = bind("blight");
    RegistryKey<DamageType> SHUCKING = bind("shucking");

    private static RegistryKey<DamageType> bind(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Aylyth.id(name));
    }
}
