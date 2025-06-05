package moriyashiine.aylyth.common.entity.ai.brains;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import moriyashiine.aylyth.common.entity.ai.AylythSensorTypes;
import moriyashiine.aylyth.common.entity.ai.tasks.BoltRangedAttackTask;
import moriyashiine.aylyth.common.entity.ai.tasks.GeckoMeleeAttackTask;
import moriyashiine.aylyth.common.entity.ai.tasks.RevengeTask;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.GoToLookTargetTask;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.LookAtMobTask;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.brain.task.RandomTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.ai.brain.task.UpdateLookControlTask;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.ai.brain.task.WalkTowardsLookTargetTask;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.UniformIntProvider;

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
                        new StayAboveWaterTask<>(0.6f),
                        new UpdateLookControlTask(45, 90),
                        new MoveToTargetTask(),
                        RevengeTask.create(WreathedHindBrain::shouldAttackHurtBy)
                )
        );
    }

    private static void addIdleActivities(Brain<WreathedHindEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, WalkTowardsLookTargetTask.create(living -> {
                            Optional<PlayerEntity> pledgedPlayer = living.getBrain().getOptionalMemory(AylythMemoryTypes.PLEDGED_PLAYER);
                            return pledgedPlayer == null ? Optional.empty() : pledgedPlayer.map(player -> new EntityLookTarget(player, true));
                        }, living -> true, 3, 10, 0.8f)),
                        Pair.of(1, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(GoToLookTargetTask.create(0.6F, 3), 2),
                                        Pair.of(new WaitTask(30, 60), 1),
                                        Pair.of(StrollTask.create(0.6F), 2)
                                ))),
                        Pair.of(1, UpdateAttackTargetTask.create(WreathedHindBrain::getAttackTarget))
                )
        );
    }

    private static void addFightActivities(WreathedHindEntity wreathedHindEntity, Brain<WreathedHindEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 10,
                ImmutableList.of(
                        ForgetAttackTargetTask.create((world, entity) -> !isPreferredAttackTarget(world, wreathedHindEntity, entity), BrainUtils::setTargetInvalid, false),
                        LookAtMobTask.create(mob -> BrainUtils.isTarget(wreathedHindEntity, mob), (float)wreathedHindEntity.getAttributeValue(EntityAttributes.FOLLOW_RANGE)),
                        GoToLookTargetTask.create(1, 3),
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

    private static boolean isPreferredAttackTarget(ServerWorld world, WreathedHindEntity wreathedHindEntity, LivingEntity target) {
        return getAttackTarget(world, wreathedHindEntity).filter((preferredTarget) -> preferredTarget == target).isPresent();
    }

    public static boolean isPledgedPlayerLow(Entity entity, WreathedHindEntity wreathedHindEntity) {
       return (entity instanceof PlayerEntity player && player.getUuid().equals(wreathedHindEntity.getPledgedPlayerUUID()) && player.getHealth() <= 6);
    }

    public static boolean shouldAttackHurtBy(LivingEntity hurtBy, WreathedHindEntity entity) {
        if (hurtBy.getUuid().equals(entity.getPledgedPlayerUUID())) {
            return entity.getBrain().getOptionalMemory(AylythMemoryTypes.SECOND_CHANCE).filter(SecondChance::shouldBetray).isPresent();
        }
        return true;
    }

    private static Optional<? extends LivingEntity> getAttackTarget(ServerWorld world, WreathedHindEntity wreathedHindEntity) {
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
