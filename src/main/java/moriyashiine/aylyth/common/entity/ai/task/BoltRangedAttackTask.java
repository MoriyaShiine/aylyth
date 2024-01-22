package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.entity.projectile.SphereEntity;
import moriyashiine.aylyth.common.util.BrainUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public class BoltRangedAttackTask extends MultiTickTask<WreathedHindEntity> {
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

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, WreathedHindEntity mobEntity) {
        LivingEntity livingEntity = BrainUtils.getAttackTarget(mobEntity);
        return mobEntity.getAttackType() == WreathedHindEntity.AttackType.RANGED
                && LookTargetUtil.isVisibleInMemory(mobEntity, livingEntity)
                && LookTargetUtil.isTargetWithinAttackRange(mobEntity, livingEntity, 0)
                && !isInMeleeAttackRange(mobEntity);
    }

    @Override
    protected void run(ServerWorld world, WreathedHindEntity entity, long time) {
        SphereEntity sphereEntity = new SphereEntity(entity.getWorld(), entity);
        world.spawnEntity(sphereEntity);
        LivingEntity target = BrainUtils.getAttackTarget(entity);

        double diffX = target.getX() - entity.getX();
        double diffY = target.getBodyY(0.3333333333333333) - sphereEntity.getY();
        double diffZ = target.getZ() - entity.getZ();
        double range = Math.sqrt(diffX * diffX + diffZ * diffZ) * 0.2F;
        sphereEntity.setVelocity(diffX, diffY + range, diffZ, 0.5F, 10.0F);

        super.run(world, entity, time);
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, WreathedHindEntity mobEntity, long l) {
        mobEntity.setAttackType(WreathedHindEntity.AttackType.NONE);
    }
}
