package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.ai.brain.BrainUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

public class GeckoMeleeAttackTask<T extends MobEntity> extends MultiTickTask<T> {
    private final int interval;
    private final double animationTimeOfAttack;
    private long animationTime = 0;
    private final int animationDuration;
    private final RunTask<T> runTask;
    private final FinishRunningTask<T> finishRunningTask;

    /**
     * @param interval the attacks cooldown
     * @param animationTime total time of the attack animation
     * @param animationTimeOfAttack when during the attack animation the entity should execute {@link MobEntity#tryAttack(Entity)}
     */
    public GeckoMeleeAttackTask(RunTask<T> runTask, FinishRunningTask<T> finishRunningTask, int interval, double animationTime, double animationTimeOfAttack) {
        super(ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT
        ));
        this.interval = interval;
        this.animationDuration = (int) animationTime;
        this.animationTimeOfAttack = animationTimeOfAttack;
        this.runTask = runTask;
        this.finishRunningTask = finishRunningTask;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, T entity) {
        LivingEntity livingEntity = BrainUtils.getAttackTarget(entity);
        return (LookTargetUtil.isVisibleInMemory(entity, livingEntity) && entity.isInAttackRange(livingEntity));
    }

    @Override
    protected void run(ServerWorld serverWorld, T mobEntity, long time) {
        mobEntity.setAttacking(true);
        LivingEntity livingEntity = BrainUtils.getAttackTarget(mobEntity);
        mobEntity.setTarget(livingEntity);

        LookTargetUtil.lookAt(mobEntity, livingEntity);
        runTask.run(serverWorld, mobEntity, time);

        animationTime = time + animationDuration;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, T entity, long time) {
        return time < this.animationTime;
    }

    @Override
    protected void keepRunning(ServerWorld world, T entity, long time) {
        if(entity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
            if (animationTime == time + animationTimeOfAttack) {
                LivingEntity livingEntity = BrainUtils.getAttackTarget(entity);
                entity.swingHand(Hand.MAIN_HAND);
                entity.tryAttack(livingEntity);
            }
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, T entity, long time) {
        entity.setAttacking(false);

        finishRunningTask.run(world, entity, time);

        this.animationTime = 0;

        entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.interval);
    }

    public interface RunTask<T> {
        void run(ServerWorld serverWorld, T mobEntity, long time);
    }

    public interface FinishRunningTask<T> {
        void run(ServerWorld serverWorld, T mobEntity, long time);
    }
}