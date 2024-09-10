package moriyashiine.aylyth.common.registry;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythEntityAttributes {

    EntityAttribute MAX_VITAL_HEALTH = register("player.max_vital_health", new ClampedEntityAttribute("attribute.name.player.max_vital_health", 0, 0, 1024.0).setTracked(true));

    private static <A extends EntityAttribute> A register(String name, A attribute) {
        // TODO use mod namespace
        return Registry.register(Registries.ATTRIBUTE, name, attribute);
    }

    static void register() {}
}
