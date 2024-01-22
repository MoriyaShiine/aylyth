package moriyashiine.aylyth.api.interfaces;

import moriyashiine.aylyth.common.world.ModWorldState;
import net.minecraft.world.World;

import java.util.UUID;

public interface Pledgeable {
    UUID getPledgedPlayerUUID();

    void removePledge();

    default void addPledgeToRemove(World world) {
        if (!world.isClient) {
            ModWorldState worldState = ModWorldState.get(world);
            if (getPledgedPlayerUUID() != null) {
                worldState.addPledgeToRemove(getPledgedPlayerUUID());
                removePledge();
            }
        }
    }
}
