package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface AylythStatusEffectTags {

    TagKey<StatusEffect> BYPASSES_EFFIGY = bind("bypasses_effigy");
    TagKey<StatusEffect> BYPASSES_MILK = bind("bypasses_milk");

    private static TagKey<StatusEffect> bind(String name) {
        return TagKey.of(RegistryKeys.STATUS_EFFECT, Aylyth.id(name));
    }
}
