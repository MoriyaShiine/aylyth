package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModEffectTags {
    public static final TagKey<StatusEffect> BYPASSES_EFFIGY = create("bypasses_effigy");
    public static final TagKey<StatusEffect> BYPASSES_MILK = create("bypasses_milk");

    private static TagKey<StatusEffect> create(String tag) {
        return TagKey.of(RegistryKeys.STATUS_EFFECT, AylythUtil.id(tag));
    }

}
