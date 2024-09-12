package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythAttributes {

    EntityAttribute MAX_VITAL_HEALTH = register("player.max_vital_health", new ClampedEntityAttribute("attribute.name.player.max_vital_health", 0, 0, 1024.0).setTracked(true));

    private static <A extends EntityAttribute> A register(String name, A attribute) {
        return Registry.register(Registries.ATTRIBUTE, AylythUtil.id(name), attribute);
    }

    static void register() {}
}
