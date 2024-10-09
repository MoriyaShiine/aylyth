package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface AylythStatusEffectTags {

    TagKey<StatusEffect> EFFIGY_CANNOT_CURE = bind("effigy_cannot_cure");
    TagKey<StatusEffect> MILK_CANNOT_CURE = bind("milk_cannot_cure");
    TagKey<StatusEffect> PREVENTS_HEALING = bind("prevents_healing");

    private static TagKey<StatusEffect> bind(String name) {
        return TagKey.of(RegistryKeys.STATUS_EFFECT, Aylyth.id(name));
    }
}
