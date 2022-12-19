package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.registry.ModMemoryTypes;
import moriyashiine.aylyth.common.util.BrainUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class AttackHostileIfPlayerNear extends Task<WreathedHindEntity> {
    public AttackHostileIfPlayerNear() {
        super(ImmutableMap.of(
                ModMemoryTypes.NEAREST_PLEDGED_PLAYERS, MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT
                )
        );
    }

    @Override
    protected boolean shouldRun(ServerWorld world, WreathedHindEntity wreathedHindEntity) {
        LivingEntity livingEntity = BrainUtils.getAttackTarget(wreathedHindEntity);
        return LookTargetUtil.isVisibleInMemory(wreathedHindEntity, livingEntity) && wreathedHindEntity.isInAttackRange(livingEntity);
    }
}
