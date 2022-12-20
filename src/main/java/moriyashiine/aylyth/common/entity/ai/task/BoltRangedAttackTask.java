package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.entity.projectile.SphereEntity;
import moriyashiine.aylyth.common.util.BrainUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

import static moriyashiine.aylyth.common.util.BrainUtils.getAttackTarget;

public class BoltRangedAttackTask extends Task<WreathedHindEntity> {
    public BoltRangedAttackTask() {
        super(ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
        );
    }

    public boolean isInMeleeAttackRange(MobEntity mob) {
        LivingEntity target = BrainUtils.getAttackTarget(mob);

        double d = mob.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
        return d <= mob.getWidth() * 4.0F * mob.getWidth() * 4.0F + target.getWidth();
    }

    protected boolean shouldRun(ServerWorld serverWorld, WreathedHindEntity mobEntity) {
        LivingEntity livingEntity = getAttackTarget(mobEntity);
        return mobEntity.getAttackType() == WreathedHindEntity.RANGE_ATTACK
                && LookTargetUtil.isVisibleInMemory(mobEntity, livingEntity)
                && LookTargetUtil.isTargetWithinAttackRange(mobEntity, livingEntity, 0)
                && !isInMeleeAttackRange(mobEntity);
    }

    @Override
    protected void run(ServerWorld world, WreathedHindEntity entity, long time) {
        SphereEntity sphereEntity = new SphereEntity(entity.world, entity);
        LivingEntity target = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();

        double d = target.getX() - entity.getX();
        double e = target.getBodyY(0.3333333333333333) - sphereEntity.getY();
        double f = target.getZ() - entity.getZ();
        double g = Math.sqrt(d * d + f * f) * 0.2F;
        sphereEntity.setVelocity(d, e + g, f, 0.5F, 10.0F);
        entity.world.spawnEntity(sphereEntity);

        super.run(world, entity, time);
    }

    protected void finishRunning(ServerWorld serverWorld, WreathedHindEntity mobEntity, long l) {
        mobEntity.setAttackType(WreathedHindEntity.NONE);
    }
}
