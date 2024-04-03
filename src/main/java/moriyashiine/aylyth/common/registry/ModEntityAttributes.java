package moriyashiine.aylyth.common.registry;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntityAttributes {
    public static final EntityAttribute MAX_VITAL_HEALTH = register("player.max_vital_health", new ClampedEntityAttribute("attribute.name.player.max_vital_health", 0, 0, 1024.0).setTracked(true));

    private static <T extends EntityAttribute> T register(String id, T attribute) {
        Registry.register(Registries.ATTRIBUTE, id, attribute);
        return attribute;
    }

    public static void init() {}
}
