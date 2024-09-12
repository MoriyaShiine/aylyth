package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import moriyashiine.aylyth.common.entity.ai.brain.BrainUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

public class BowAttackTask<E extends MobEntity & RangedAttackMob> extends MultiTickTask<E> {
    public BowAttackTask() {
        super(ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT
        ), 1200);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, E entity) {
        LivingEntity target = BrainUtils.getAttackTarget(entity);
        return isHoldingUsableBow(entity) && LookTargetUtil.isVisibleInMemory(entity, target) && LookTargetUtil.isTargetWithinAttackRange(entity, target, 0);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, E entity, long time) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET) && shouldRun(world, entity);
    }

    @Override
    protected void keepRunning(ServerWorld world, E entity, long time) {
        LivingEntity target = BrainUtils.getAttackTarget(entity);
        entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(target, true));

        if (!entity.isUsingItem()) {
            Hand hand = isUsableBow(entity, entity.getMainHandStack()) ? Hand.MAIN_HAND : Hand.OFF_HAND;
            entity.setCurrentHand(hand);
        } else if (entity.getItemUseTime() >= 20) {
            int useTime = entity.getItemUseTime();
            entity.stopUsingItem();
            entity.attack(target, BowItem.getPullProgress(useTime));
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, E entity, long time) {
        if (entity.isUsingItem()) {
            entity.clearActiveItem();
        }
    }

    private boolean isHoldingUsableBow(E entity) {
        return entity.isHolding(itemStack -> isUsableBow(entity, itemStack));
    }

    private boolean isUsableBow(E entity, ItemStack stack) {
        // item is in the bows tag. if the item is a ranged weapon item, then check that the entity can use the ranged weapon.
        // TODO: Change this back to a tag check for "c:bows" when crossbows are removed from the tag
        return stack.isOf(Items.BOW) && (!(stack.getItem() instanceof RangedWeaponItem rangedWeaponItem) || entity.canUseRangedWeapon(rangedWeaponItem));
    }
}
