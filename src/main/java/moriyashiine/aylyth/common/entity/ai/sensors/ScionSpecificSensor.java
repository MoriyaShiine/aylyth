package moriyashiine.aylyth.common.entity.ai.sensors;

import com.google.common.collect.ImmutableSet;
import moriyashiine.aylyth.common.entity.types.mob.ScionEntity;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Set;

public class ScionSpecificSensor extends Sensor<ScionEntity> {

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.NEAREST_REPELLENT,
                AylythMemoryTypes.NEAREST_VISIBLE_PLAYER_NEMESIS
        );
    }

    @Override
    protected void sense(ServerWorld world, ScionEntity entity) {
        Brain<?> brain = entity.getBrain();
        brain.remember(MemoryModuleType.NEAREST_REPELLENT, findRepellent(world, entity));
        Optional<PlayerEntity> optional = Optional.empty();
        LivingTargetCache livingTargetCache = brain.getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_MOBS).orElse(LivingTargetCache.empty());
        for (LivingEntity livingEntity : livingTargetCache.iterate((livingEntityx) -> true)) {
            if (optional.isEmpty() && livingEntity instanceof PlayerEntity playerEntity && playerEntity.getUuid() == entity.getStoredPlayerUUID()) {
                optional = Optional.of(playerEntity);
            }
        }
        brain.remember(AylythMemoryTypes.NEAREST_VISIBLE_PLAYER_NEMESIS, optional);
    }

    private static Optional<BlockPos> findRepellent(ServerWorld world, LivingEntity entity) {
        return BlockPos.findClosest(entity.getBlockPos(), 8, 4, (pos) -> isRepellent(world, pos));
    }

    private static boolean isRepellent(ServerWorld world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isIn(AylythBlockTags.SCION_REPELLENT);
    }

}
