package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModDamageTypeTags {
    public static final TagKey<DamageType> IS_YMPE = create("is_ympe");
    public static final TagKey<DamageType> BYPASSES_CUIRASS = create("bypasses_cuirass");

    private static TagKey<DamageType> create(String tag) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id(tag));
    }
}
