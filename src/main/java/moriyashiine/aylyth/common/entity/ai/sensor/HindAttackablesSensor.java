package moriyashiine.aylyth.common.entity.ai.sensor;

import com.google.common.collect.ImmutableSet;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.registry.ModMemoryTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;
import java.util.Set;

public class HindAttackablesSensor extends Sensor<WreathedHindEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(
                ModMemoryTypes.PLEDGED_PLAYER,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.ANGRY_AT,
                MemoryModuleType.NEAREST_ATTACKABLE
        );
    }

    @Override
    protected void sense(ServerWorld world, WreathedHindEntity entity) {
        Brain<?> brain = entity.getBrain();

        Optional<LivingEntity> enemyOptional = Optional.empty();
        if (brain.hasMemoryModule(MemoryModuleType.ANGRY_AT)) {
            enemyOptional = LookTargetUtil.getEntity(entity, MemoryModuleType.ANGRY_AT);
        } else if (brain.hasMemoryModule(ModMemoryTypes.PLEDGED_PLAYER)) {
            PlayerEntity pledgedPlayer = brain.getOptionalMemory(ModMemoryTypes.PLEDGED_PLAYER).get();
            if (pledgedPlayer.getHealth() <= 6) {
                enemyOptional = Optional.of(pledgedPlayer);
            } else {
                LivingTargetCache livingTargetCache = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(LivingTargetCache.empty());
                enemyOptional = livingTargetCache.findFirst(livingEntity -> livingEntity instanceof HostileEntity && livingEntity.squaredDistanceTo(pledgedPlayer) <= 256);
            }
        }
        brain.remember(MemoryModuleType.NEAREST_ATTACKABLE, enemyOptional);
    }
}
