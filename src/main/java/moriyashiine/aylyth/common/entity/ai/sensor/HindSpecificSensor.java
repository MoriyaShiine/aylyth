package moriyashiine.aylyth.common.entity.ai.sensor;

import com.google.common.collect.ImmutableSet;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.registry.ModMemoryTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HindSpecificSensor extends Sensor<WreathedHindEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(
                ModMemoryTypes.PLEDGED_PLAYERS,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.NEAREST_ATTACKABLE
        );
    }

    @Override
    protected void sense(ServerWorld world, WreathedHindEntity entity) {
        List<PlayerEntity> list = world.getPlayers()
                .stream()
                .filter(EntityPredicates.EXCEPT_SPECTATOR)
                .filter(player -> entity.isInRange(player, 24.0))
                .filter(player -> entity.getPledgedPlayerUUIDs().contains(player.getUuid()))
                .sorted(Comparator.comparingDouble(entity::squaredDistanceTo))
                .collect(Collectors.toList());
        Brain<?> brain = entity.getBrain();
        brain.remember(ModMemoryTypes.PLEDGED_PLAYERS, list);

        Optional<HostileEntity> optional2 = Optional.empty();
        if(brain.hasMemoryModule(ModMemoryTypes.PLEDGED_PLAYERS)){
            LivingTargetCache livingTargetCache = brain.getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_MOBS).orElse(LivingTargetCache.empty());
            for(LivingEntity livingEntity : livingTargetCache.iterate(livingEntity -> true)) {
                if (livingEntity instanceof HostileEntity hostileEntity) {
                    if (optional2.isEmpty()) {
                        optional2 = Optional.of(hostileEntity);
                    }
                }
            }
        }
        brain.remember(MemoryModuleType.NEAREST_ATTACKABLE, optional2);
    }
}
