package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface AylythEnchantmentTags {
    TagKey<Enchantment> FAST_IN_GRIPWEED = bind("fast_in_gripweed");

    private static TagKey<Enchantment> bind(String name) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Aylyth.id(name));
    }
}
