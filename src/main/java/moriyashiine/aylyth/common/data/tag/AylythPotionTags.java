package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.potion.Potion;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface AylythPotionTags {

    TagKey<Potion> BLIGHT = create("blight");

    private static TagKey<Potion> create(String name) {
        return TagKey.of(RegistryKeys.POTION, Aylyth.id(name));
    }
}
