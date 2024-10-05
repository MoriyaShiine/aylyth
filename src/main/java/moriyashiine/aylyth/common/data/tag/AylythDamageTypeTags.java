package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface AylythDamageTypeTags {

    TagKey<DamageType> IS_YMPE = bind("is_ympe");
    TagKey<DamageType> BYPASSES_CUIRASS = bind("bypasses_cuirass");
    /** Death by these damage types can send the player to Aylyth if they are in a biome that also sends to Aylyth. */
    TagKey<DamageType> DEATH_SENDS_TO_AYLYTH = bind("death_sends_to_aylyth");

    private static TagKey<DamageType> bind(String name) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, Aylyth.id(name));
    }
}
