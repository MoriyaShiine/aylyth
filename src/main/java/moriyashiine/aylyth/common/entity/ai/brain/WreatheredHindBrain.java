package moriyashiine.aylyth.common.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.ai.task.AttackHostileIfPlayerNear;
import moriyashiine.aylyth.common.entity.ai.task.GeckoMeleeAttackTask;
import moriyashiine.aylyth.common.entity.mob.WreatheredHindEntity;
import moriyashiine.aylyth.common.registry.ModMemoryTypes;
import moriyashiine.aylyth.common.registry.ModSensorTypes;
import moriyashiine.aylyth.common.util.BrainUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class WreatheredHindBrain {
    private static final List<SensorType<? extends Sensor<? super WreatheredHindEntity>>> SENSORS = List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY,
            ModSensorTypes.HIND_SPECIFIC_SENSOR
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
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.AVOID_TARGET,
            ModMemoryTypes.NEAREST_PLEDGED_PLAYERS
    );

    public WreatheredHindBrain(){}

    public static Brain<?> create(WreatheredHindEntity wreathedHindEntity, Dynamic<?> dynamic) {
        Brain.Profile<WreatheredHindEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
        Brain<WreatheredHindEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(wreathedHindEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<WreatheredHindEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new StayAboveWaterTask(0.6f),
                        new LookAroundTask(45, 90),
                        new WanderAroundTask(),
                        new UpdateAttackTargetTask<>(WreatheredHindBrain::getAttackTarget)
                )
        );
    }

    private static void addIdleActivities(Brain<WreatheredHindEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(new StrollTask(0.6F), 2),
                                        Pair.of(new ConditionalTask<>(livingEntity -> true, new GoTowardsLookTarget(0.6F, 3)), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                )))
                )
        );
    }

    private static void addFightActivities(WreatheredHindEntity wreathedHindEntity, Brain<WreatheredHindEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 10,
                ImmutableList.of(
                        new ForgetAttackTargetTask<>(entity -> !isPreferredAttackTarget(wreathedHindEntity, entity), BrainUtils::setTargetInvalid, false),
                        new FollowMobTask(mob -> BrainUtils.isTarget(wreathedHindEntity, mob), (float)wreathedHindEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                        new GeckoMeleeAttackTask(ENTITY_PREDICATE::test, 10,20 * 2,20 * 0.7D),
                        new AttackHostileIfPlayerNear()
                ), MemoryModuleType.ATTACK_TARGET);
    }

    private static final Predicate<Entity> ENTITY_PREDICATE = entity -> {
        return true;
    };

    public static void updateActivities(WreatheredHindEntity wreathedHindEntity) {
        wreathedHindEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        wreathedHindEntity.setAttacking(wreathedHindEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    private static boolean isPreferredAttackTarget(WreatheredHindEntity wreathedHindEntity, LivingEntity target) {
        return getAttackTarget(wreathedHindEntity).filter((preferredTarget) -> preferredTarget == target).isPresent();
    }

    private static Optional<? extends LivingEntity> getAttackTarget(WreatheredHindEntity wreathedHindEntity) {
        Brain<WreatheredHindEntity> brain = wreathedHindEntity.getBrain();
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(wreathedHindEntity, MemoryModuleType.ANGRY_AT);
        if(optional.isPresent()){
            return optional;
        }
        if (brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            Optional<LivingTargetCache> visibleLivingEntitiesCache = wreathedHindEntity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
            /*
            if(wreathedHindEntity.getActionState() == 2){
                return visibleLivingEntitiesCache.get().findFirst(entity -> !entity.isSubmergedInWater() && wreathedHindEntity.getOwnerUuid() != entity.getUuid());
            }

             */
        }
        return Optional.empty();
    }
}
