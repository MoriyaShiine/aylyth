package moriyashiine.aylyth.common.entity.ai.brains;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import moriyashiine.aylyth.common.entity.ai.AylythSensorTypes;
import moriyashiine.aylyth.common.entity.ai.tasks.BowAttackTask;
import moriyashiine.aylyth.common.entity.ai.tasks.EatFoodTask;
import moriyashiine.aylyth.common.entity.ai.tasks.InteractPlayerTask;
import moriyashiine.aylyth.common.entity.ai.tasks.RootAttackTask;
import moriyashiine.aylyth.common.entity.ai.tasks.SwitchWeaponTask;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.CrossbowAttackTask;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.util.Unit;

import java.util.List;
import java.util.Optional;

public class TulpaBrain {
    private static final List<SensorType<? extends Sensor<? super TulpaEntity>>> SENSORS = List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY,
            AylythSensorTypes.TULPA_SPECIFIC
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
            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.INTERACTION_TARGET,
            AylythMemoryTypes.SHOULD_FOLLOW_OWNER,
            AylythMemoryTypes.OWNER_PLAYER,
            AylythMemoryTypes.ROOT_ATTACK_COOLDOWN,
            AylythMemoryTypes.ROOT_ATTACK_DELAY
    );

    public TulpaBrain() {}

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
                        new LookAroundTask(45, 90)
//                        new ConditionalTask<>(
//                                entity -> !entity.shouldStay(), new WanderAroundTask(), true
//                        ),
//                        new ConditionalTask<>(
//                                Map.of(MemoryModuleType.HURT_BY_ENTITY, MemoryModuleState.VALUE_PRESENT),
//                                TulpaBrain::shouldAttackHurtBy, new RevengeTask(), false
//                        )
                )
        );
    }

    private static void addIdleActivities(Brain<TulpaEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
//                        Pair.of(0, new ConditionalTask<>(
//                                Map.of(ModMemoryTypes.SHOULD_FOLLOW_OWNER, MemoryModuleState.VALUE_ABSENT),
//                                e -> !e.shouldStay(),
//                                new RandomTask<>(
//                                        ImmutableList.of(
//                                                Pair.of(new StrollTask(0.6F), 2),
//                                                Pair.of(new GoTowardsLookTarget(0.6F, 3), 2),
//                                                Pair.of(new WaitTask(30, 60), 1)
//                                        )),
//                                true
//                        )),
                        Pair.of(1, new EatFoodTask()),
//                        Pair.of(2, new ConditionalTask<>(
//                                Map.of(
//                                        ModMemoryTypes.OWNER_PLAYER, MemoryModuleState.VALUE_PRESENT,
//                                        ModMemoryTypes.SHOULD_FOLLOW_OWNER, MemoryModuleState.VALUE_PRESENT
//                                ),
//                                e -> !e.shouldStay(),
//                                new WalkTowardsLookTargetTask<>(living -> {
//                                    Optional<PlayerEntity> owner = brain.getOptionalMemory(ModMemoryTypes.OWNER_PLAYER);
//                                    return owner.map(player -> new EntityLookTarget(player, true));
//                                }, 3, 1, 0.85f), true
//                        )),
                        Pair.of(3, UpdateAttackTargetTask.create(TulpaBrain::getAttackTarget))
                )
        );
    }

    private static void addFightActivities(TulpaEntity tulpaEntity, Brain<TulpaEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 10,
                ImmutableList.of(
                        ForgetAttackTargetTask.create(entity -> !isPreferredAttackTarget(tulpaEntity, entity), BrainUtils::setTargetInvalid, false),
                        new SwitchWeaponTask(),
//                        new ConditionalTask<>(TulpaBrain::canUseRangedAttack, new AttackTask<>(5, 0.55f)),
//                        new ConditionalTask<>(entity -> !canUseRangedAttack(entity), new RangedApproachTask(1.0f)),
                        new CrossbowAttackTask<>(),
                        new BowAttackTask<>(),
                        new RootAttackTask<>()
//                        new ConditionalTask<>(
//                                entity -> !entity.isHolding(stack -> stack.getItem() instanceof RangedWeaponItem),
//                                new GeckoMeleeAttackTask<>(
//                                        (serverWorld, tulpa, time) -> tulpa.getDataTracker().set(TulpaEntity.ATTACK_TYPE, BasicAttackType.MELEE),
//                                        (serverWorld, tulpa, time) -> tulpa.getDataTracker().set(TulpaEntity.ATTACK_TYPE, BasicAttackType.NONE),
//                                        10, (int) (20 * 1.5), 15),
//                                true
//                        )
                ), MemoryModuleType.ATTACK_TARGET);
    }

    public static void updateActivities(TulpaEntity tulpaEntity) {
        tulpaEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        tulpaEntity.setAttacking(tulpaEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    private static boolean isPreferredAttackTarget(TulpaEntity tulpaEntity, LivingEntity target) {
        return getAttackTarget(tulpaEntity)
                .filter((preferredTarget) -> preferredTarget == target)
                .isPresent();
    }

    private static Optional<? extends LivingEntity> getAttackTarget(TulpaEntity tulpaEntity) {
        Brain<TulpaEntity> brain = tulpaEntity.getBrain();
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(tulpaEntity, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent()) {
            return optional;
        }
        if (brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            LivingTargetCache visibleLivingEntitiesCache = tulpaEntity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get();
            if(tulpaEntity.getActionState() == TulpaEntity.ActionState.SICKO) {
                return visibleLivingEntitiesCache.findFirst(entity -> !entity.isSubmergedInWater() && !tulpaEntity.isOwner(entity));
            }
        }
        return Optional.empty();
    }

    private static boolean canUseRangedAttack(TulpaEntity entity) {
        return BrainUtils.isHoldingUsableRangedWeapon(entity) || !entity.getBrain().hasMemoryModule(AylythMemoryTypes.ROOT_ATTACK_COOLDOWN);
    }

    private static boolean shouldAttackHurtBy(TulpaEntity entity) {
        LivingEntity attackedBy = entity.getBrain().getOptionalMemory(MemoryModuleType.HURT_BY_ENTITY).get();
        return !entity.isOwner(attackedBy);
    }

    public static void setShouldFollowOwner(TulpaEntity tulpaEntity, boolean should) {
        if (should) {
            tulpaEntity.getBrain().remember(AylythMemoryTypes.SHOULD_FOLLOW_OWNER, Unit.INSTANCE);
            tulpaEntity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        } else {
            tulpaEntity.getBrain().forget(AylythMemoryTypes.SHOULD_FOLLOW_OWNER);
        }
    }
}
