package moriyashiine.aylyth.common.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import moriyashiine.aylyth.common.registry.ModMemoryTypes;
import moriyashiine.aylyth.common.registry.ModSensorTypes;
import moriyashiine.aylyth.common.util.BrainUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ScionBrain {
    private static final UniformIntProvider GO_TO_NEMESIS_MEMORY_DURATION = TimeHelper.betweenSeconds(5, 7);
    public static final Predicate<ScionEntity> VALID_ENTITY = Entity::isAlive;
    private static final List<SensorType<? extends Sensor<? super ScionEntity>>> SENSORS = List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY,
            ModSensorTypes.SCION_SPECIFIC_SENSOR
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
            ModMemoryTypes.NEAREST_VISIBLE_PLAYER_NEMESIS
    );

    public ScionBrain(){}

    public static Brain<?> create(ScionEntity scionEntity, Dynamic<?> dynamic) {
        Brain.Profile<ScionEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
        Brain<ScionEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(scionEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<ScionEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        MemoryTransferTask.create(VALID_ENTITY, ModMemoryTypes.NEAREST_VISIBLE_PLAYER_NEMESIS, MemoryModuleType.AVOID_TARGET, GO_TO_NEMESIS_MEMORY_DURATION),
                        new StayAboveWaterTask(0.6f),
                        new LookAroundTask(45, 90),
                        new WanderAroundTask(),
                        UpdateAttackTargetTask.create(ScionBrain::getAttackTarget)
                )
        );
    }

    private static void addIdleActivities(Brain<ScionEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(StrollTask.create(0.6F), 2),
                                        Pair.of(GoTowardsLookTargetTask.create(0.6F, 3), 2),
                                        Pair.of(GoToNearbyPositionTask.create(MemoryModuleType.HOME, 0.6F, 2, 100), 2),
                                        Pair.of(GoToIfNearbyTask.create(MemoryModuleType.HOME, 0.6F, 5), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                )))
                )
        );
    }

    private static void addFightActivities(ScionEntity scionEntity, Brain<ScionEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        ForgetAttackTargetTask.create(entity -> !scionEntity.isEnemy(entity), BrainUtils::setTargetInvalid, false),
                        LookAtMobTask.create(mob -> BrainUtils.isTarget(scionEntity, mob), (float)scionEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                        RangedApproachTask.create(1.2F),
                        MeleeAttackTask.create(18)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    public static void updateActivities(ScionEntity scionEntity) {
        scionEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        scionEntity.setAttacking(scionEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    public static void setCurrentPosAsHome(ScionEntity scionEntity) {
        GlobalPos globalPos = GlobalPos.create(scionEntity.getWorld().getRegistryKey(), scionEntity.getBlockPos());
        scionEntity.getBrain().remember(MemoryModuleType.HOME, globalPos);
    }

    private static Optional<? extends LivingEntity> getAttackTarget(ScionEntity scionEntity) {
        Brain<ScionEntity> brain = scionEntity.getBrain();
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(scionEntity, MemoryModuleType.ANGRY_AT);
        if(optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(scionEntity, optional.get())){
            return optional;
        }
        Optional<PlayerEntity> optional2 = brain.getOptionalRegisteredMemory(ModMemoryTypes.NEAREST_VISIBLE_PLAYER_NEMESIS);
        if (optional2.isPresent()) {
            return optional2;
        }
        if (brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)) {
            return brain.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
        }
        if (brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            Optional<LivingTargetCache> visibleLivingEntitiesCache = scionEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_MOBS);
            if(visibleLivingEntitiesCache.isPresent()){
                return visibleLivingEntitiesCache.get().findFirst(entity -> entity.getType() == EntityType.PLAYER && !entity.isSubmergedInWater());
            }
        }
        return Optional.empty();
    }
}
