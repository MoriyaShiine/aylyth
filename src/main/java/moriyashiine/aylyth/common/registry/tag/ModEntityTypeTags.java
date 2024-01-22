package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> GRIPWEED_IMMUNE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Aylyth.MOD_ID, "gripweed_immune"));
    public static final TagKey<EntityType<?>> SHUCK_BLACKLIST = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Aylyth.MOD_ID, "shuck_blacklist"));
}
