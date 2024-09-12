package moriyashiine.aylyth.common.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.ai.task.BoltRangedAttackTask;
import moriyashiine.aylyth.common.entity.ai.task.GeckoMeleeAttackTask;
import moriyashiine.aylyth.common.entity.type.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import moriyashiine.aylyth.common.entity.ai.AylythSensorTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.Optional;

public class WreathedHindBrain {

    private static final List<SensorType<? extends Sensor<? super WreathedHindEntity>>> SENSORS = List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY,
            AylythSensorTypes.NEARBY_PLEDGED_PLAYER,
            AylythSensorTypes.HIND_ATTACKABLES

    );

    private static final List<MemoryModuleType<?>> MEMORIES = List.of(
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.AVOID_TARGET,
            AylythMemoryTypes.PLEDGED_PLAYER,
            AylythMemoryTypes.SECOND_CHANCE
    );

    public WreathedHindBrain() {}

    public static Brain<?> create(WreathedHindEntity wreathedHindEntity, Dynamic<?> dynamic) {
        Brain.Profile<WreathedHindEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
        Brain<WreathedHindEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(wreathedHindEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<WreathedHindEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new StayAboveWaterTask(0.6f),
                        new LookAroundTask(45, 90),
                        new WanderAroundTask()
//                        new ConditionalTask<>(
//                                Map.of(MemoryModuleType.HURT_BY_ENTITY, MemoryModuleState.VALUE_PRESENT),
//                                WreathedHindBrain::shouldAttackHurtBy, new RevengeTask(), false
//                        )
                )
        );
    }

    private static void addIdleActivities(Brain<WreathedHindEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, WalkTowardsLookTargetTask.create(living -> {
                            Optional<PlayerEntity> pledgedPlayer = living.getBrain().getOptionalMemory(AylythMemoryTypes.PLEDGED_PLAYER);
                            return pledgedPlayer.map(player -> new EntityLookTarget(player, true));
                        }, living -> true, 3, 10, 0.8f)),
                        Pair.of(1, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(StrollTask.create(0.6F), 2),
                                        Pair.of(GoTowardsLookTargetTask.create(0.6F, 3), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                ))),
                        Pair.of(1, UpdateAttackTargetTask.create(WreathedHindBrain::getAttackTarget))
                )
        );
    }

    private static void addFightActivities(WreathedHindEntity wreathedHindEntity, Brain<WreathedHindEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 10,
                ImmutableList.of(
                        ForgetAttackTargetTask.create(entity -> !isPreferredAttackTarget(wreathedHindEntity, entity), BrainUtils::setTargetInvalid, false),
                        LookAtMobTask.create(mob -> BrainUtils.isTarget(wreathedHindEntity, mob), (float)wreathedHindEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                        GoTowardsLookTargetTask.create(1, 3),
                        new GeckoMeleeAttackTask<>(
                                (serverWorld, hind, time) -> {
                                    LivingEntity livingEntity = BrainUtils.getAttackTarget(hind);
                                    if (WreathedHindBrain.isPledgedPlayerLow(livingEntity, hind)) {
                                        hind.setAttackType(WreathedHindEntity.AttackType.KILLING);
                                    } else {
                                        hind.setAttackType(WreathedHindEntity.AttackType.MELEE);
                                    }
                                },
                                (serverWorld, hind, time) -> {
                                    hind.getDataTracker().set(WreathedHindEntity.ATTACK_TYPE, WreathedHindEntity.AttackType.NONE);
                                },
                                10,20 * 2,20 * 0.7D),
                        new BoltRangedAttackTask()
                ), MemoryModuleType.ATTACK_TARGET);
    }

    public static void updateActivities(WreathedHindEntity wreathedHindEntity) {
        wreathedHindEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        wreathedHindEntity.setAttacking(wreathedHindEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    private static boolean isPreferredAttackTarget(WreathedHindEntity wreathedHindEntity, LivingEntity target) {
        return getAttackTarget(wreathedHindEntity).filter((preferredTarget) -> preferredTarget == target).isPresent();
    }

    public static boolean isPledgedPlayerLow(Entity entity, WreathedHindEntity wreathedHindEntity) {
       return (entity instanceof PlayerEntity player && player.getUuid().equals(wreathedHindEntity.getPledgedPlayerUUID()) && player.getHealth() <= 6);
    }

    public static boolean shouldAttackHurtBy(WreathedHindEntity entity) {
        Entity attackedBy = entity.getBrain().getOptionalMemory(MemoryModuleType.HURT_BY_ENTITY).get();
        if (attackedBy.getUuid().equals(entity.getPledgedPlayerUUID())) {
            return entity.getBrain().getOptionalMemory(AylythMemoryTypes.SECOND_CHANCE).filter(SecondChance::shouldBetray).isPresent();
        }
        return true;
    }

    private static Optional<? extends LivingEntity> getAttackTarget(WreathedHindEntity wreathedHindEntity) {
        return wreathedHindEntity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    public enum SecondChance {
        WARNING,
        BETRAY;

        public boolean shouldBetray() {
            return this == BETRAY;
        }
    }
}
