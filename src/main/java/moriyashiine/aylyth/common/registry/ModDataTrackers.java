package moriyashiine.aylyth.common.registry;

import net.minecraft.entity.data.TrackedDataHandler;

public class ModDataTrackers {
    public static final TrackedDataHandler<EntityMode> ENTITY_MODE = TrackedDataHandler.ofEnum(EntityMode.class);



    public enum EntityMode {
        IDLE,
        FOLLOW,
        SICKO;
    }
}
