package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;


public class InteractPlayerTask extends MultiTickTask<TulpaEntity> {

    public InteractPlayerTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.INTERACTION_TARGET, MemoryModuleState.VALUE_PRESENT
        ), Integer.MAX_VALUE);
    }

    protected boolean shouldRun(ServerWorld serverWorld, TulpaEntity tulpaEntity) {
        return tulpaEntity.isAlive() && tulpaEntity.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET)
                .map(tulpaEntity::squaredDistanceTo)
                .map(dist -> dist <= 16.0)
                .orElse(false);
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        return this.shouldRun(serverWorld, tulpaEntity);
    }

    protected void run(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        this.update(tulpaEntity);
    }

    protected void finishRunning(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        Brain<?> brain = tulpaEntity.getBrain();
        brain.forget(MemoryModuleType.WALK_TARGET);
        brain.forget(MemoryModuleType.LOOK_TARGET);
    }

    protected void keepRunning(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        this.update(tulpaEntity);
    }

    protected boolean isTimeLimitExceeded(long time) {
        return false;
    }

    private void update(TulpaEntity tulpaEntity) {
        Brain<?> brain = tulpaEntity.getBrain();
        brain.forget(MemoryModuleType.WALK_TARGET);
        brain.forget(MemoryModuleType.ATTACK_TARGET);
        brain.remember(MemoryModuleType.LOOK_TARGET, (new EntityLookTarget(tulpaEntity.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).get(), true)));
    }
}
