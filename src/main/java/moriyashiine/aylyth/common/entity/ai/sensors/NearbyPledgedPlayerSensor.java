package moriyashiine.aylyth.common.entity.ai.sensors;

import com.google.common.collect.ImmutableSet;
import moriyashiine.aylyth.api.interfaces.Pledgeable;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Set;

public class NearbyPledgedPlayerSensor<E extends LivingEntity & Pledgeable> extends Sensor<E> {

    @Override
    protected void sense(ServerWorld world, E entity) {
        if (entity.getPledgedPlayerUUID() != null) {
            PlayerEntity playerEntity = world.getPlayerByUuid(entity.getPledgedPlayerUUID());
            if (playerEntity != null && playerEntity.getBlockPos().isWithinDistance(entity.getBlockPos(), 64)) {
                entity.getBrain().remember(AylythMemoryTypes.PLEDGED_PLAYER, playerEntity, 300);
            }
        }
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(AylythMemoryTypes.PLEDGED_PLAYER);
    }
}
