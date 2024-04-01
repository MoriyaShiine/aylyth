package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.potion.Potion;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface ModPotionTags {
    TagKey<Potion> BLIGHT = create("blight");

    private static TagKey<Potion> create(String tag) {
        return TagKey.of(RegistryKeys.POTION, AylythUtil.id(tag));
    }
}
