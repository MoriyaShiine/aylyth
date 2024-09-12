package moriyashiine.aylyth.common.entity.ai.task;

import moriyashiine.aylyth.common.entity.RootPropEntity;
import moriyashiine.aylyth.common.entity.ai.BasicAttackType;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.registry.AylythMemoryTypes;
import moriyashiine.aylyth.common.util.BrainUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

import java.util.Map;

public class RootAttackTask<E extends TulpaEntity> extends MultiTickTask<E> {
    public RootAttackTask() {
        super(Map.of(
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
                AylythMemoryTypes.ROOT_ATTACK_COOLDOWN, MemoryModuleState.VALUE_ABSENT,
                AylythMemoryTypes.ROOT_ATTACK_DELAY, MemoryModuleState.REGISTERED
        ), 21);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, E entity) {
        return BrainUtils.getAttackTarget(entity).isAlive() && entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET)
                .map(entity::squaredDistanceTo)
                .map(dist -> dist >= 9)
                .orElse(false);
    }

    @Override
    protected void run(ServerWorld world, E entity, long time) {
        entity.playSound(SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK, 1.0F, 1.0F);
        entity.getBrain().remember(AylythMemoryTypes.ROOT_ATTACK_DELAY, Unit.INSTANCE, 20);
        entity.getDataTracker().set(TulpaEntity.ATTACK_TYPE, BasicAttackType.RANGED);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, E entity, long time) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    protected void keepRunning(ServerWorld world, E entity, long time) {
        if (!entity.getBrain().hasMemoryModule(AylythMemoryTypes.ROOT_ATTACK_DELAY)) {
            castSpell(world, entity);
            entity.playSound(SoundEvents.ENTITY_TURTLE_EGG_BREAK, 1.0F, 1.0F);
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, E entity, long time) {
        entity.getBrain().remember(AylythMemoryTypes.ROOT_ATTACK_COOLDOWN, Unit.INSTANCE, 100);
        entity.getDataTracker().set(TulpaEntity.ATTACK_TYPE, BasicAttackType.NONE);
    }


    protected void castSpell(ServerWorld world, E entity) {
        LivingEntity livingEntity = BrainUtils.getAttackTarget(entity);
        double d = Math.min(livingEntity.getY(), entity.getY());
        double e = Math.max(livingEntity.getY(), entity.getY()) + 1.0;
        float f = (float) MathHelper.atan2(livingEntity.getZ() - entity.getZ(), livingEntity.getX() - entity.getX());

        int i;
        for (i = 0; i < 8; i++) {
            float angle = 2 * MathHelper.PI * (i / 8F);
            this.spawnRoot(
                    world,
                    entity,
                    livingEntity.getX() + (double) MathHelper.cos(angle) ,
                    livingEntity.getZ() + (double) MathHelper.sin(angle) ,
                    livingEntity.getY() + 2,
                    d,
                    f + (float)i * MathHelper.PI * 0.4F,
                    0);
        }
    }

    private void spawnRoot(ServerWorld world, E entity, double x, double z, double maxY, double y, float yaw, int warmup) {
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        boolean bl = false;
        double d = 0.0;

        do {
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState = world.getBlockState(blockPos2);
            if (blockState.isSideSolidFullSquare(world, blockPos2, Direction.UP)) {
                if (!world.isAir(blockPos)) {
                    BlockState blockState2 = world.getBlockState(blockPos);
                    VoxelShape voxelShape = blockState2.getCollisionShape(world, blockPos);
                    if (!voxelShape.isEmpty()) {
                        d = voxelShape.getMax(Direction.Axis.Y);
                    }
                }

                bl = true;
                break;
            }

            blockPos = blockPos.down();
        } while(blockPos.getY() >= MathHelper.floor(maxY) - 1);

        if (bl) {
            world.spawnEntity(new RootPropEntity(world, x, (double)blockPos.getY() + d, z, yaw, warmup, entity));
        }

    }
}
