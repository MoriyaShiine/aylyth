package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

public class GeckoMeleeAttackTask extends Task<TulpaEntity> {
    private final int interval;
    private int ANIMATION_TIME = (int) (20 * 1.5);

    public GeckoMeleeAttackTask(int interval) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT));
        this.interval = interval;
    }

    protected boolean shouldRun(ServerWorld serverWorld, TulpaEntity mobEntity) {
        LivingEntity livingEntity = this.getAttackTarget(mobEntity);
        return !this.isHoldingUsableRangedWeapon(mobEntity) && LookTargetUtil.isVisibleInMemory(mobEntity, livingEntity) && mobEntity.isInAttackRange(livingEntity);
    }

    private boolean isHoldingUsableRangedWeapon(TulpaEntity entity) {
        return entity.isHolding((stack) -> {
            Item item = stack.getItem();
            return item instanceof RangedWeaponItem && entity.canUseRangedWeapon((RangedWeaponItem)item);
        });
    }

    protected void run(ServerWorld serverWorld, TulpaEntity mobEntity, long l) {
        mobEntity.setAttacking(true);
        mobEntity.getDataTracker().set(TulpaEntity.IS_ATTACKING, true);
        LivingEntity livingEntity = this.getAttackTarget(mobEntity);
        LookTargetUtil.lookAt(mobEntity, livingEntity);

        mobEntity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.interval);

    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, TulpaEntity entity, long time) {
        return this.ANIMATION_TIME > 0;
    }

    @Override
    protected void keepRunning(ServerWorld world, TulpaEntity entity, long time) {
        if(entity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)){
            LivingEntity livingEntity = this.getAttackTarget(entity);
            this.ANIMATION_TIME--;
            if(ANIMATION_TIME == 15){
                entity.swingHand(Hand.MAIN_HAND);
                entity.tryAttack(livingEntity);
            }
        }
        super.keepRunning(world, entity, time);
    }

    @Override
    protected void finishRunning(ServerWorld world, TulpaEntity entity, long time) {
        entity.setAttacking(false);
        entity.getDataTracker().set(TulpaEntity.IS_ATTACKING, false);
        this.ANIMATION_TIME = (int) (20 * 1.5);
        super.finishRunning(world, entity, time);
    }

    private LivingEntity getAttackTarget(TulpaEntity entity) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
    }
}