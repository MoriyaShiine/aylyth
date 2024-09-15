package moriyashiine.aylyth.common.entity.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public class RevengeTask extends MultiTickTask<MobEntity> {
    public RevengeTask() {
        super(ImmutableMap.of(
                MemoryModuleType.HURT_BY_ENTITY, MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.ANGRY_AT, MemoryModuleState.REGISTERED
        ));
    }

    @Override
    protected void run(ServerWorld world, MobEntity entity, long time) {
        LivingEntity attackedBy = entity.getBrain().getOptionalMemory(MemoryModuleType.HURT_BY_ENTITY).get();
        entity.getBrain().remember(MemoryModuleType.ANGRY_AT, attackedBy.getUuid(), 600L);
    }
}
