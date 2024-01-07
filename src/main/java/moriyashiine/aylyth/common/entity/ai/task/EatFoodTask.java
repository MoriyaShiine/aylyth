package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.ai.brain.TulpaBrain;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

public class EatFoodTask extends Task<TulpaEntity> {
    private int foodSlot = -1;

    public EatFoodTask() {
        super(ImmutableMap.of(MemoryModuleType.ATE_RECENTLY, MemoryModuleState.VALUE_ABSENT));
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, TulpaEntity tulpaEntity) {
        return tulpaEntity.getHealth() < tulpaEntity.getMaxHealth()
                && (hasFoodInInventory(tulpaEntity) || hasFoodInHands(tulpaEntity));
    }

    private boolean hasFoodInInventory(TulpaEntity tulpaEntity) {
        for(int i = 0; i < tulpaEntity.getInventory().size(); ++i) {
            ItemStack itemStack = tulpaEntity.getInventory().getStack(i);
            if (itemStack.isFood()) {
                foodSlot = i;
                return true;
            }
        }
        return false;
    }

    private boolean hasFoodInHands(TulpaEntity tulpaEntity) {
        return tulpaEntity.getMainHandStack().isFood() || tulpaEntity.getOffHandStack().isFood();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        return shouldRun(serverWorld, tulpaEntity);
    }

    @Override
    protected void run(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        if (tulpaEntity.getMainHandStack().isFood()) {
            tulpaEntity.setCurrentHand(Hand.MAIN_HAND);
        } else if (tulpaEntity.getOffHandStack().isFood()) {
            tulpaEntity.setCurrentHand(Hand.OFF_HAND);
        } else if (foodSlot > -1) {
             {
                ItemStack food = tulpaEntity.getInventory().getStack(foodSlot);
                tulpaEntity.getInventory().setStack(foodSlot, tulpaEntity.getMainHandStack());
                tulpaEntity.equipStack(EquipmentSlot.MAINHAND, food);
                tulpaEntity.setCurrentHand(Hand.MAIN_HAND);
            }
        }
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        if (!tulpaEntity.isUsingItem()) {
            tulpaEntity.getBrain().remember(MemoryModuleType.ATE_RECENTLY, true, 200L);
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, TulpaEntity entity, long time) {
        super.finishRunning(world, entity, time);
        foodSlot = -1;
        entity.stopUsingItem();
    }
}
