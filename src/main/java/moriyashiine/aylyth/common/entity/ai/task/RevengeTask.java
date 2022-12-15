package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.mob.TameableHostileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;

public class RevengeTask extends Task<MobEntity> {
    public RevengeTask() {
        super(ImmutableMap.of(MemoryModuleType.ANGRY_AT, MemoryModuleState.VALUE_ABSENT));
    }

    @Override
    protected boolean shouldRun(ServerWorld world, MobEntity entity) {
        return wasHurt(entity);
    }

    @Override
    protected void run(ServerWorld world, MobEntity entity, long time) {
        Optional<DamageSource> damageSource = entity.getBrain().getOptionalMemory(MemoryModuleType.HURT_BY);
        if(damageSource.isPresent() && damageSource.get().getAttacker() instanceof LivingEntity livingEntity){
            if(entity instanceof TameableHostileEntity tameableHostileEntity && tameableHostileEntity.getOwner() == livingEntity){
                return;
            }
            entity.getBrain().remember(MemoryModuleType.ANGRY_AT, livingEntity.getUuid(), 600L);
        }
    }

    public static boolean wasHurt(LivingEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.HURT_BY);
    }
}
