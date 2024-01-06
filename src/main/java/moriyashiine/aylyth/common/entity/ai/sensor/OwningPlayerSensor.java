package moriyashiine.aylyth.common.entity.ai.sensor;

import com.google.common.collect.ImmutableSet;
import moriyashiine.aylyth.common.entity.mob.TameableHostileEntity;
import moriyashiine.aylyth.common.registry.ModMemoryTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.Set;

public class OwningPlayerSensor<E extends LivingEntity & TameableHostileEntity> extends Sensor<E> {

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(ModMemoryTypes.OWNER_PLAYER);
    }

    @Override
    protected void sense(ServerWorld world, E entity) {
        Box box = entity.getBoundingBox().expand(this.getHorizontalExpansion(), this.getHeightExpansion(), this.getHorizontalExpansion());
        List<PlayerEntity> list = world.getEntitiesByClass(PlayerEntity.class, box, LivingEntity::isAlive);
        if(entity.getOwner() instanceof PlayerEntity player && list.contains(player)) {
            Brain<?> brain = entity.getBrain();
            brain.remember(ModMemoryTypes.OWNER_PLAYER, player);
        }
    }

    protected int getHorizontalExpansion() {
        return 24;
    }

    protected int getHeightExpansion() {
        return 16;
    }

}
