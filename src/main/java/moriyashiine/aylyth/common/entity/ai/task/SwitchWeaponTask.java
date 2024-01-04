package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.util.BrainUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;

import java.util.*;

public class SwitchWeaponTask extends Task<TulpaEntity> {
    public SwitchWeaponTask() {
        super(ImmutableMap.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, TulpaEntity mobEntity) {
        ItemStack attackingItem = mobEntity.getMainHandStack();
        // check if sword & far away or bow and close
        return this.isAttackTargetVisible(mobEntity);
    }

    private double getWeaponDamage(ItemStack itemStack, LivingEntity targetEntity) {
        double damage = EnchantmentHelper.getAttackDamage(itemStack, targetEntity.getGroup());
        if (itemStack.getItem() instanceof SwordItem swordItem) {
            damage = damage + (double)swordItem.getAttackDamage();
        }
        return damage;
    }

    @Override
    protected void run(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        LivingEntity target = getAttackTarget(tulpaEntity);

        // collect all items
        // track whether the target is in melee attack range or not
        // if it is, we prioritize higher-damage melee items over ranged, but will use ranged if a melee weapon does not exist
        // otherwise, we want to get a ranged weapon, if one exists
        boolean favorRanged = !tulpaEntity.isInAttackRange(target) && !isHoldingUsableRangedWeapon(tulpaEntity);
        DefaultedList<ItemStack> stacks = tulpaEntity.getInventory().stacks;
        PossibleWeapon possibleWeapon = null;
        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            double damage = getWeaponDamage(stack, target);
            if (possibleWeapon == null) {
                possibleWeapon = new PossibleWeapon(stack, i, damage);
                continue;
            }

            if (favorRanged && !possibleWeapon.isRanged() && isUsableRangedWeapon(stack, tulpaEntity)) {
                possibleWeapon = new PossibleWeapon(stack, i, damage);
            } else if (possibleWeapon.damage < damage) {
                if (favorRanged) {
                    if (isUsableRangedWeapon(stack, tulpaEntity)) {
                        possibleWeapon = new PossibleWeapon(stack, i, damage);
                    }
                } else {
                    possibleWeapon = new PossibleWeapon(stack, i, damage);
                }
            }
        }

        if (possibleWeapon != null) {
            if (tulpaEntity.getEquippedStack(EquipmentSlot.MAINHAND) != possibleWeapon.stack) {
                int index = possibleWeapon.index();
                ItemStack prevItem = tulpaEntity.getMainHandStack();
                tulpaEntity.equipStack(EquipmentSlot.MAINHAND, possibleWeapon.stack);
                tulpaEntity.getInventory().setStack(index, prevItem);
            }
        }
    }

    private boolean isUsableRangedWeapon(ItemStack stack, MobEntity entity) {
        return stack.getItem() instanceof RangedWeaponItem rangedWeaponItem && entity.canUseRangedWeapon(rangedWeaponItem);
    }

    private boolean isAttackTargetVisible(TulpaEntity entity) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS)
                .map(livingTargetCache -> livingTargetCache.contains(getAttackTarget(entity)))
                .orElse(false);
    }

    private LivingEntity getAttackTarget(TulpaEntity entity) {
        return BrainUtils.getAttackTarget(entity);
    }

    private boolean isHoldingUsableRangedWeapon(MobEntity entity) {
        return entity.isHolding(stack -> isUsableRangedWeapon(stack, entity));
    }

    record PossibleWeapon(ItemStack stack, int index, double damage) {
        public boolean isRanged() {
            return stack.getItem() instanceof RangedWeaponItem;
        }
    }
}
