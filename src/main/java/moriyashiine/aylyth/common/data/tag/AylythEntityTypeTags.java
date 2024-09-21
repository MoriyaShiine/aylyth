package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface AylythEntityTypeTags {

    TagKey<EntityType<?>> GRIPWEED_IMMUNE = bind("gripweed_immune");
    TagKey<EntityType<?>> NON_SHUCKABLE = bind("non_shuckable");

    private static TagKey<EntityType<?>> bind(String name) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Aylyth.id(name));
    }
}
