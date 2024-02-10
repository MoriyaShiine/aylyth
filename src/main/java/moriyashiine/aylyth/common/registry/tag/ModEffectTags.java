package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModEffectTags {
    public static final TagKey<StatusEffect> EFFIGY_BYPASSING = TagKey.of(RegistryKeys.STATUS_EFFECT, new Identifier(Aylyth.MOD_ID, "effigy_bypassing"));
    public static final TagKey<StatusEffect> MILK_BYPASSING = TagKey.of(RegistryKeys.STATUS_EFFECT, new Identifier(Aylyth.MOD_ID, "milk_bypassing"));

}
