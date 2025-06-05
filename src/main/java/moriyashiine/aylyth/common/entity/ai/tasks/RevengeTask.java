package moriyashiine.aylyth.common.entity.ai.tasks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.MobEntity;

public class RevengeTask {
    public static <E extends MobEntity> Task<E> create(AttackPredicate<LivingEntity, E> predicate) {
        return TaskTriggerer.task(
                context -> context.group(
                        context.queryMemoryValue(MemoryModuleType.HURT_BY_ENTITY), context.queryMemoryOptional(MemoryModuleType.ANGRY_AT)
                ).apply(context, (hurtBy, angryAt) -> (world, entity, time) -> {
                    LivingEntity attackedBy = context.getValue(hurtBy);
                    if (predicate.shouldAttack(attackedBy, entity)) {
                        angryAt.remember(attackedBy.getUuid(), 600);
                        return true;
                    }
                    return false;
                })
        );
    }

    public interface AttackPredicate<H, E> {
        boolean shouldAttack(H hurtBy, E entity);
    }
}
