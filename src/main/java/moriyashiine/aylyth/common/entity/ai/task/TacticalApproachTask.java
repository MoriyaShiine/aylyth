package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;

import java.util.function.Predicate;

//Modified version of https://github.com/cybercat5555/Miskatonic-Mysteries-Fabric/blob/1.19/src/main/java/com/miskatonicmysteries/common/feature/entity/ai/task/TacticalApproachTask.java
public class TacticalApproachTask<T extends MobEntity> extends Task<T> {
    private final Predicate<T> predicate;
    private final float speed;
    private final float squaredRange = 25;
    private int targetSeeingTicker;

    public TacticalApproachTask(float speed, Predicate<T> predicate) {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.REGISTERED,
                MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.REGISTERED
        ));
        this.speed = speed;
        this.predicate = predicate;
    }

    @Override
    protected void run(ServerWorld serverWorld, T mob, long l) {
        LivingEntity livingEntity = mob.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
        if (isAttackCooldown(mob) && LookTargetUtil.isVisibleInMemory(mob, livingEntity)
                && LookTargetUtil.isTargetWithinAttackRange(mob, livingEntity, 1)) {
            this.forgetWalkTarget(mob);
        } else {
            this.rememberWalkTarget(mob, livingEntity);
        }
    }

    @Override
    protected void keepRunning(ServerWorld world, T entity, long time) {
        super.keepRunning(world, entity, time);
        LivingEntity livingEntity = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
        boolean shield = entity.isHolding(Items.SHIELD);
        boolean canSee = entity.getVisibilityCache().canSee(livingEntity);
        boolean bl2 = this.targetSeeingTicker > 0;

        if (canSee != bl2) {
            this.targetSeeingTicker = 0;
        }

        if (canSee) {
            ++this.targetSeeingTicker;
        } else {
            --this.targetSeeingTicker;
        }

        entity.lookAtEntity(livingEntity, 30.0F, 30.0F);
        entity.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);

        if (entity.isUsingItem() && !canSee && this.targetSeeingTicker < -60) {
            entity.clearActiveItem();
        } else if (shield) {
            entity.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(entity, Items.SHIELD));
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, T entity, long time) {
        entity.clearActiveItem();
        super.finishRunning(world, entity, time);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, T entity, long time) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).isPresent() && (
                isAttackCooldown(entity) || predicate.test(entity)
                        || entity.age - entity.getLastAttackedTime() <= 30);
    }

    private boolean isAttackCooldown(T entity) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_COOLING_DOWN).isPresent();
    }

    private void rememberWalkTarget(T entity, LivingEntity target) {
        Brain<T> brain = (Brain<T>)entity.getBrain();
        brain.remember(MemoryModuleType.LOOK_TARGET, (new EntityLookTarget(target, true)));
        WalkTarget walkTarget = new WalkTarget(new EntityLookTarget(target, false), this.speed, 0);
        brain.remember(MemoryModuleType.WALK_TARGET, walkTarget);
    }

    private void forgetWalkTarget(LivingEntity entity) {
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }
}
