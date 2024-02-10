package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> GRIPWEED_IMMUNE = create("gripweed_immune");
    public static final TagKey<EntityType<?>> NON_SHUCKABLE = create("non_shuckable");

    private static TagKey<EntityType<?>> create(String tag) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, AylythUtil.id(tag));
    }
}
