package moriyashiine.aylyth.common.block.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public interface SeepTeleportable {
    static Optional<SeepTeleportable> of(Entity entity) {
        if (entity instanceof SeepTeleportable teleportable) {
            return Optional.of(teleportable);
        }
        return Optional.empty();
    }

    void setInSeep(BlockPos pos);

    boolean isInSeep();

    BlockPos getLastSeepPos();
}
