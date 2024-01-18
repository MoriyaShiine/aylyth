package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.registry.ModMemoryTypes;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;

public class FollowOwnerTask extends Task<TulpaEntity> {
    public FollowOwnerTask() {
        super(ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED,
                ModMemoryTypes.OWNER_PLAYER, MemoryModuleState.VALUE_PRESENT,
                ModMemoryTypes.SHOULD_FOLLOW_OWNER, MemoryModuleState.VALUE_PRESENT
        ));
    }

    @Override
    protected boolean isTimeLimitExceeded(long time) {
        return false;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, TulpaEntity entity) {
        return entity.getBrain().getOptionalMemory(ModMemoryTypes.SHOULD_FOLLOW_OWNER).get();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, TulpaEntity entity, long time) {
        return shouldRun(world, entity);
    }

    @Override
    protected void keepRunning(ServerWorld world, TulpaEntity entity, long time) {
        Brain<?> brain = entity.getBrain();
        PlayerEntity owner = brain.getOptionalMemory(ModMemoryTypes.OWNER_PLAYER).get();
        LookTargetUtil.walkTowards(entity, owner, 0.85f, 3);
        super.keepRunning(world, entity, time);
    }
}
