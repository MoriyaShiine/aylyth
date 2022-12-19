package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.ai.brain.WreathedHindBrain;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.util.BrainUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

import java.util.function.Predicate;

public class GeckoMeleeAttackTask extends Task<MobEntity> {
    private final Predicate<LivingEntity> predicate;
    private final int interval;
    private final double animationTimeOfAttack;
    private double animationTicker = 0;
    private final double animationTime;

    /**
     * @param interval the attacks cooldown
     * @param animationTime total time of the attack animation
     * @param animationTimeOfAttack when during the attack animation the entity should execute {@link MobEntity#tryAttack(Entity)}
     */
    public GeckoMeleeAttackTask(Predicate<LivingEntity> predicate, int interval, double animationTime, double animationTimeOfAttack) {
        super(ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT
        ));
        this.interval = interval;
        this.animationTime = animationTime;
        this.animationTimeOfAttack = animationTimeOfAttack;
        this.predicate = predicate;
    }

    protected boolean shouldRun(ServerWorld serverWorld, MobEntity mobEntity) {
        LivingEntity livingEntity = BrainUtils.getAttackTarget(mobEntity);
        return predicate.test(mobEntity) && (LookTargetUtil.isVisibleInMemory(mobEntity, livingEntity) && mobEntity.isInAttackRange(livingEntity));
    }

    protected void run(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        mobEntity.setAttacking(true);
        LivingEntity livingEntity = BrainUtils.getAttackTarget(mobEntity);
        if(mobEntity instanceof TulpaEntity tulpaEntity){
            tulpaEntity.getDataTracker().set(TulpaEntity.IS_ATTACKING, true);
        }else if(mobEntity instanceof WreathedHindEntity wreathedHindEntity){
            if(WreathedHindBrain.isPledgedPlayerLow(livingEntity, wreathedHindEntity)){
                wreathedHindEntity.setAttackType(WreathedHindEntity.KILLING_ATTACK);
            }else{
                wreathedHindEntity.setAttackType(WreathedHindEntity.MELEE_ATTACK);
            }

        }
        LookTargetUtil.lookAt(mobEntity, livingEntity);
        mobEntity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.interval);

    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, MobEntity entity, long time) {
        return this.animationTicker < animationTime;
    }

    @Override
    protected void keepRunning(ServerWorld world, MobEntity entity, long time) {
        if(entity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)){
            LivingEntity livingEntity = BrainUtils.getAttackTarget(entity);
            this.animationTicker--;
            if(animationTicker == animationTimeOfAttack){
                entity.swingHand(Hand.MAIN_HAND);
                entity.tryAttack(livingEntity);
            }
        }
        super.keepRunning(world, entity, time);
    }

    @Override
    protected void finishRunning(ServerWorld world, MobEntity entity, long time) {
        entity.setAttacking(false);
        if(entity instanceof TulpaEntity tulpaEntity){
            tulpaEntity.getDataTracker().set(TulpaEntity.IS_ATTACKING, false);
        }else if(entity instanceof WreathedHindEntity wreathedHindEntity){
            wreathedHindEntity.setAttackType(WreathedHindEntity.NONE);
        }

        this.animationTicker = animationTimeOfAttack;
        super.finishRunning(world, entity, time);
    }
}