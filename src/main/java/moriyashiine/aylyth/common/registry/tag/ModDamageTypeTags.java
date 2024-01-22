package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModDamageTypeTags {
    public static final TagKey<DamageType> IS_YMPE = TagKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id("is_ympe"));
}
