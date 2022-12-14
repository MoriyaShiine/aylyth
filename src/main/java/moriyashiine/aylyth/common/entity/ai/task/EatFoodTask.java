package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import moriyashiine.aylyth.common.entity.ai.brain.TulpaBrain;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.Map;

public class EatFoodTask extends Task<TulpaEntity> {
    private long eatEndTime;

    public EatFoodTask() {
        super(ImmutableMap.of(MemoryModuleType.ATE_RECENTLY, MemoryModuleState.VALUE_PRESENT));
    }

    protected boolean shouldRun(ServerWorld serverWorld, TulpaEntity tulpaEntity) {
        return tulpaEntity.getHealth() < tulpaEntity.getMaxHealth()
                && hasFoodInInventoryAndSwitch(tulpaEntity) && !TulpaBrain.hasAteRecently(tulpaEntity);
    }

    private boolean hasFoodInInventoryAndSwitch(TulpaEntity tulpaEntity) {
        for(int i = 0; i < tulpaEntity.getInventory().size(); ++i) {
            ItemStack itemStack = tulpaEntity.getInventory().getStack(i);
            if (!itemStack.isEmpty()) {
                tulpaEntity.getInventory().setStack(i, tulpaEntity.getMainHandStack());
                tulpaEntity.equipStack(EquipmentSlot.MAINHAND, itemStack);
                return true;
            }
        }
        return false;
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        return shouldRun(serverWorld, tulpaEntity);
    }

    protected void run(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        int i = 275 + tulpaEntity.getRandom().nextInt(50);
        this.eatEndTime = l + (long)i;
    }

    protected void keepRunning(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        if (l >= this.eatEndTime) {
            tulpaEntity.eatFood(serverWorld, tulpaEntity.getMainHandStack());
            tulpaEntity.getBrain().remember(MemoryModuleType.ATE_RECENTLY, true, 200L);
        } else {
            tulpaEntity.consumeItem();
        }
    }
}
