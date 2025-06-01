package moriyashiine.aylyth.common.event;

import moriyashiine.aylyth.common.block.types.VitalThuribleBlock;
import moriyashiine.aylyth.common.entity.AylythAttributes;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.server.network.ServerPlayerEntity;

public class VitalHealthEvents {
    public static void init() {
        ServerPlayerEvents.COPY_FROM.register(VitalHealthEvents::retainVitalHealthAttribute);
    }

    /**
     * Copies the max vital health attribute from the vital thurible as long as the damage source was not from ympe
     */
    private static void retainVitalHealthAttribute(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if (!alive && (oldPlayer.getRecentDamageSource() == null || !AylythUtil.isSourceYmpe(oldPlayer.getRecentDamageSource()))) {
            EntityAttributeInstance oldInstance = oldPlayer.getAttributeInstance(AylythAttributes.MAX_VITAL_HEALTH);
            EntityAttributeInstance newInstance = newPlayer.getAttributeInstance(AylythAttributes.MAX_VITAL_HEALTH);
            if (oldInstance != null && newInstance != null && oldInstance.getModifier(VitalThuribleBlock.MAX_VITAL_MODIFIER) != null) {
                newInstance.addPersistentModifier(oldInstance.getModifier(VitalThuribleBlock.MAX_VITAL_MODIFIER));
            }
        }
    }
}
