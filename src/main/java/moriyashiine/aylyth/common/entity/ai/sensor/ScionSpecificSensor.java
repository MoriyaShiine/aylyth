package moriyashiine.aylyth.common.entity.ai.sensor;

import com.google.common.collect.ImmutableSet;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import moriyashiine.aylyth.common.registry.ModMemoryTypes;
import moriyashiine.aylyth.common.registry.tag.ModBlockTags;
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
                ModMemoryTypes.NEAREST_VISIBLE_PLAYER_NEMESIS
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
        brain.remember(ModMemoryTypes.NEAREST_VISIBLE_PLAYER_NEMESIS, optional);
    }

    private static Optional<BlockPos> findRepellent(ServerWorld world, LivingEntity entity) {
        return BlockPos.findClosest(entity.getBlockPos(), 8, 4, (pos) -> isRepellent(world, pos));
    }

    private static boolean isRepellent(ServerWorld world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isIn(ModBlockTags.SCION_REPELLENT);
    }

}
