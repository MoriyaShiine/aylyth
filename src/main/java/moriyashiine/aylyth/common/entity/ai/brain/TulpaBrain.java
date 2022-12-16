package moriyashiine.aylyth.common.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.ai.task.*;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Items;

import java.util.List;
import java.util.Optional;

public class TulpaBrain {
    private static final List<SensorType<? extends Sensor<? super TulpaEntity>>> SENSORS = List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY
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
            MemoryModuleType.HOME,
            MemoryModuleType.PACIFIED,
            MemoryModuleType.NEAREST_REPELLENT,
            MemoryModuleType.AVOID_TARGET,
            MemoryModuleType.ATE_RECENTLY
    );

    public TulpaBrain(){}

    public static Brain<?> create(TulpaEntity tulpaEntity, Dynamic<?> dynamic) {
        Brain.Profile<TulpaEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
        Brain<TulpaEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(tulpaEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<TulpaEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new InteractPlayerTask(),
                        new StayAboveWaterTask(0.6f),
                        new LookAroundTask(45, 90),
                        new WanderAroundTask(),
                        new UpdateAttackTargetTask<>(TulpaBrain::getAttackTarget),
                        new RevengeTask()
                )
        );
    }

    private static void addIdleActivities(Brain<TulpaEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(new StrollTask(0.6F), 2),
                                        Pair.of(new ConditionalTask<>(livingEntity -> true, new GoTowardsLookTarget(0.6F, 3)), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                ))),
                        Pair.of(1, new EatFoodTask())
                )
        );
    }

    private static void addFightActivities(TulpaEntity tulpaEntity, Brain<TulpaEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 10,
                ImmutableList.of(
                        new ForgetAttackTargetTask<>(entity -> !isPreferredAttackTarget(tulpaEntity, entity), TulpaBrain::setTargetInvalid, false),
                        new FollowMobTask(mob -> isTarget(tulpaEntity, mob), (float)tulpaEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                        new SwitchWeaponTask(tulpaEntity),
                        new GeckoMeleeAttackTask(10),
                        new ConditionalTask<>(TulpaBrain::isHoldingCrossbow, new AttackTask<>(5, 0.75F)),
                        new TacticalApproachTask(0.65F, LivingEntity::isAlive)
                ), MemoryModuleType.ATTACK_TARGET);
    }

    private static boolean isHoldingCrossbow(TulpaEntity tulpaEntity) {
        return tulpaEntity.isHolding(Items.CROSSBOW);
    }

    public static void updateActivities(TulpaEntity tulpaEntity) {
        tulpaEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        tulpaEntity.setAttacking(tulpaEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    private static void setTargetInvalid(TulpaEntity tulpaEntity, LivingEntity target) {
        tulpaEntity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
        tulpaEntity.getBrain().forget(MemoryModuleType.ANGRY_AT);
    }

    private static boolean isPreferredAttackTarget(TulpaEntity tulpaEntity, LivingEntity target) {
        return getAttackTarget(tulpaEntity).filter((preferredTarget) -> preferredTarget == target).isPresent();
    }

    private static Optional<? extends LivingEntity> getAttackTarget(TulpaEntity tulpaEntity) {
        Brain<TulpaEntity> brain = tulpaEntity.getBrain();
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(tulpaEntity, MemoryModuleType.ANGRY_AT);
        if(optional.isPresent()){
            return optional;
        }
        if (brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            Optional<LivingTargetCache> visibleLivingEntitiesCache = tulpaEntity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
            if(false){//TODO Add psycho mode requirement
                return visibleLivingEntitiesCache.get().findFirst(entity -> !entity.isSubmergedInWater() && tulpaEntity.getOwnerUuid() != entity.getUuid());
            }
        }
        return Optional.empty();
    }

    private static boolean isTarget(TulpaEntity tulpaEntity, LivingEntity entity) {
        return tulpaEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
    }

    public static boolean hasAteRecently(TulpaEntity tulpaEntity) {
        return tulpaEntity.getBrain().hasMemoryModule(MemoryModuleType.ATE_RECENTLY);
    }
}
