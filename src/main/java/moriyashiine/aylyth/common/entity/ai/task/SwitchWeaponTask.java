package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.common.entity.ai.brain.TulpaBrain;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.util.BrainUtils;
import moriyashiine.aylyth.mixin.MobEntityAccessor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;

import java.util.*;
import java.util.stream.Stream;

public class SwitchWeaponTask extends MultiTickTask<TulpaEntity> {
    public SwitchWeaponTask() {
        super(ImmutableMap.of(
                MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT
        ));
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, TulpaEntity tulpaEntity) {
        if (!this.isAttackTargetVisible(tulpaEntity)) {
            return false;
        }
//        if (isTooCloseForComfort(tulpaEntity, getAttackTarget(tulpaEntity)) != isHoldingUsableRangedWeapon(tulpaEntity)) {
//            return false;
//        }

        return true;
    }

    private double getWeaponDamage(ItemStack itemStack, LivingEntity targetEntity) {
        double damage = EnchantmentHelper.getAttackDamage(itemStack, targetEntity.getGroup());
        if (itemStack.getItem() instanceof ToolItem toolItem) {
            if (toolItem instanceof SwordItem swordItem) {
                damage = damage + (double)swordItem.getAttackDamage();
            } else {
                damage = damage + (double)toolItem.getMaterial().getAttackDamage();
            }
        }
        return damage;
    }

    @Override
    protected void run(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        LivingEntity target = getAttackTarget(tulpaEntity);
        boolean ranged = !isTooCloseForComfort(tulpaEntity, target);
        // if ranged, check if holding a ranged weapon and do nothing, otherwise search for the first ranged weapon. If
        //  a ranged weapon is not found, fall through to searching for the strongest melee weapon.
        // if melee, check if the inventory has changed since the last time this task has checked, then search through
        //  to find the highest damage dealing melee weapon

        // collect all items
        // track whether the target is in melee attack range or not
        // if it is, we prioritize higher-damage melee items over ranged, but will use ranged if a melee weapon does not exist
        // otherwise, we want to get a ranged weapon, if one exists
        if (ranged && BrainUtils.isHoldingUsableRangedWeapon(tulpaEntity)) {
            return;
        }

        List<StackReference> references = getReferences(tulpaEntity.getInventory());
        references.add(StackReference.of(tulpaEntity, EquipmentSlot.MAINHAND));
        references.add(StackReference.of(tulpaEntity, EquipmentSlot.OFFHAND));

        PossibleWeapon possibleWeapon = null;
        for (StackReference reference : references) {
            ItemStack stack = reference.get();
            if (stack.isEmpty()) {
                continue;
            }
            double damage = getWeaponDamage(stack, target);
            if (possibleWeapon == null) {
                possibleWeapon = new PossibleWeapon(reference, damage);
                continue;
            }

            if (ranged && !possibleWeapon.isRanged() && BrainUtils.isUsableRangedWeapon(stack, tulpaEntity)) {
                possibleWeapon = new PossibleWeapon(reference, damage);
            } else if (possibleWeapon.damage < damage) {
                if (ranged) {
                    if (BrainUtils.isUsableRangedWeapon(stack, tulpaEntity)) {
                        possibleWeapon = new PossibleWeapon(reference, damage);
                    }
                } else {
                    possibleWeapon = new PossibleWeapon(reference, damage);
                }
            }
        }

        if (possibleWeapon != null) {
            if (tulpaEntity.getEquippedStack(EquipmentSlot.MAINHAND) != possibleWeapon.stackReference.get()) {
                ItemStack prevItem = tulpaEntity.getMainHandStack();
                tulpaEntity.equipStack(EquipmentSlot.MAINHAND, possibleWeapon.stackReference.get());
                possibleWeapon.stackReference.set(prevItem);
            }
        }
    }

    private boolean isAttackTargetVisible(TulpaEntity entity) {
        return entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_MOBS)
                .map(livingTargetCache -> livingTargetCache.contains(getAttackTarget(entity)))
                .orElse(false);
    }

    private List<StackReference> getReferences(Inventory inventory) {
        List<StackReference> list = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            list.add(StackReference.of(inventory, i));
        }
        return list;
    }

    private LivingEntity getAttackTarget(TulpaEntity entity) {
        return BrainUtils.getAttackTarget(entity);
    }

    private boolean isTooCloseForComfort(TulpaEntity tulpaEntity, LivingEntity target) {
        double sqDist = tulpaEntity.squaredDistanceTo(target);
        double comfortableRange = tulpaEntity.squaredAttackRange(target) * 2;
        return sqDist <= comfortableRange;
    }

    record PossibleWeapon(StackReference stackReference, double damage) {
        public boolean isRanged() {
            return stackReference.get().getItem() instanceof RangedWeaponItem;
        }
    }
}
