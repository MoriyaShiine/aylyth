package moriyashiine.aylyth.common.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.ai.task.*;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.registry.ModMemoryTypes;
import moriyashiine.aylyth.common.registry.ModSensorTypes;
import moriyashiine.aylyth.common.util.BrainUtils;
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
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.Optional;

public class TulpaBrain {
    private static final List<SensorType<? extends Sensor<? super TulpaEntity>>> SENSORS = List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY,
            ModSensorTypes.TULPA_SPECIFIC_SENSOR
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
            ModMemoryTypes.SHOULD_FOLLOW_OWNER,
            ModMemoryTypes.OWNER_PLAYER
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
                                        Pair.of(new GoTowardsLookTarget(0.6F, 3), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                ))),
                        Pair.of(1, new EatFoodTask()),
                        Pair.of(2, new FollowOwnerTask())
                )
        );
    }

    private static void addFightActivities(TulpaEntity tulpaEntity, Brain<TulpaEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 10,
                ImmutableList.of(
                        new ForgetAttackTargetTask<>(entity -> !isPreferredAttackTarget(tulpaEntity, entity), BrainUtils::setTargetInvalid, false),
                        new FollowMobTask(mob -> BrainUtils.isTarget(tulpaEntity, mob), (float)tulpaEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                        new TimeLimitedTask<>(new SwitchWeaponTask(), UniformIntProvider.create(5, 5)),
                        new CrossbowAttackTask<>(),

                        new GeckoMeleeAttackTask<>(TulpaBrain::shouldRunTask, TulpaBrain::onRunTask, TulpaBrain::onFinishRunningTask, 10, (int) (20 * 1.5), 15),
                        new TacticalApproachTask<>(0.65F, LivingEntity::isAlive)
                ), MemoryModuleType.ATTACK_TARGET);
    }

    private static boolean shouldRunTask(TulpaEntity entity) {
        return !entity.isHolding(stack -> stack.getItem() instanceof RangedWeaponItem);
    }

    private static void onRunTask(ServerWorld serverWorld, TulpaEntity tulpa, long time) {
        tulpa.getDataTracker().set(TulpaEntity.IS_ATTACKING, true);
    }

    private static void onFinishRunningTask(ServerWorld serverWorld, TulpaEntity tulpa, long time) {
        tulpa.getDataTracker().set(TulpaEntity.IS_ATTACKING, false);
    }

    private static boolean isHoldingCrossbow(TulpaEntity tulpaEntity) {
        return tulpaEntity.isHolding(Items.CROSSBOW);
    }

    public static void updateActivities(TulpaEntity tulpaEntity) {
        tulpaEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        tulpaEntity.setAttacking(tulpaEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
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
            if(tulpaEntity.getActionState() == 2){
                return visibleLivingEntitiesCache.get().findFirst(entity -> !entity.isSubmergedInWater() && tulpaEntity.getOwnerUuid() != entity.getUuid());
            }
        }
        return Optional.empty();
    }

    public static void setShouldFollowOwner(TulpaEntity tulpaEntity, boolean should) {
        tulpaEntity.getBrain().remember(ModMemoryTypes.SHOULD_FOLLOW_OWNER, should);
    }
}
