package moriyashiine.aylyth.common.entity;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public interface AylythAttributes {
    Identifier BASE_BLOCK_INTERACTION_RANGE = Aylyth.id("block_interaction_range");
    Identifier BASE_ENTITY_INTERACTION_RANGE = Aylyth.id("entity_interaction_range");

    RegistryEntry<EntityAttribute> MAX_VITAL_HEALTH = register("max_vital_health", new ClampedEntityAttribute("attribute.name.max_vital_health", 0, 0, 1024.0).setTracked(true));

    private static <A extends EntityAttribute> RegistryEntry<EntityAttribute> register(String name, A attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, Aylyth.id(name), attribute);
    }

    // Load static initializer
    static void register() {}
}
