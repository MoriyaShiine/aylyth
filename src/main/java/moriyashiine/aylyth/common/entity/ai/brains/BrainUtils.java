package moriyashiine.aylyth.common.entity.ai.brains;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;

public class BrainUtils {

    public static LivingEntity getAttackTarget(MobEntity entity) {
        return entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).get();
    }

    public static boolean isTarget(MobEntity mobEntity, LivingEntity entity) {
        return mobEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
    }

    public static void setTargetInvalid(MobEntity mobEntity, LivingEntity target) {
        mobEntity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
        mobEntity.getBrain().forget(MemoryModuleType.ANGRY_AT);
    }

    public static boolean isUsableRangedWeapon(ItemStack stack, MobEntity entity) {
        return stack.getItem() instanceof RangedWeaponItem rangedWeaponItem && entity.canUseRangedWeapon(rangedWeaponItem);
    }

    public static boolean isHoldingUsableRangedWeapon(MobEntity entity) {
        return entity.isHolding(stack -> isUsableRangedWeapon(stack, entity));
    }
}
