package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
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

import java.util.*;

public class SwitchWeaponTask extends Task<TulpaEntity> {
    private final TulpaEntity tulpaEntity;
    public SwitchWeaponTask(TulpaEntity tulpaEntity) {
        super(ImmutableMap.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT));
        this.tulpaEntity = tulpaEntity;
    }

    protected boolean shouldRun(ServerWorld serverWorld, TulpaEntity mobEntity) {
        return this.isAttackTargetVisible(mobEntity);
    }

    private double getWeaponDamageLazy(ItemStack itemStack, LivingEntity targetEntity, boolean isSword){
        double damage = EnchantmentHelper.getAttackDamage(itemStack, targetEntity.getGroup());
        if(isSword){
            damage = damage + (double)((SwordItem) itemStack.getItem()).getAttackDamage();
        }
        return damage;
    }

    protected void run(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        tulpaEntity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(this.getAttackTarget(tulpaEntity), true));
        LivingEntity livingEntity = getAttackTarget(tulpaEntity);
        if(livingEntity != null){
            List<Pair<ItemStack, Double>> weaponList = new ArrayList<>();
            if(isInMeleeAttackRange(livingEntity)){
                for(ItemStack newWeapon : tulpaEntity.getInventory().stacks){
                    if(newWeapon.getItem() instanceof SwordItem){
                        weaponList.add(new Pair<>(newWeapon, getWeaponDamageLazy(newWeapon, livingEntity, true)));
                    }
                }
            }else if(!isHoldingUsableRangedWeapon(tulpaEntity)){
                for(ItemStack newWeapon : tulpaEntity.getInventory().stacks){
                    if(newWeapon.getItem() instanceof RangedWeaponItem && tulpaEntity.canUseRangedWeapon((RangedWeaponItem)newWeapon.getItem())){
                        weaponList.add(new Pair<>(newWeapon, getWeaponDamageLazy(newWeapon, livingEntity, false)));
                    }
                }
            }
            getAndSwitchWeapon(weaponList);
            weaponList.clear();
        }
    }

    private void getAndSwitchWeapon(List<Pair<ItemStack, Double>> weaponList){
        if(!weaponList.isEmpty()){
            weaponList.sort(Comparator.comparingDouble(Pair::getSecond));
            if(!tulpaEntity.getEquippedStack(EquipmentSlot.MAINHAND).isOf(weaponList.get(0).getFirst().getItem())){
                int index = tulpaEntity.getInventory().stacks.indexOf(weaponList.get(0).getFirst());
                ItemStack prevWeapon = tulpaEntity.getMainHandStack();
                tulpaEntity.equipStack(EquipmentSlot.MAINHAND, weaponList.get(0).getFirst());
                tulpaEntity.getInventory().setStack(5, weaponList.get(0).getFirst());
                tulpaEntity.getInventory().setStack(index, prevWeapon);
            }
        }
    }

    private boolean isAttackTargetVisible(TulpaEntity entity) {
        Optional<LivingTargetCache> optionalLivingEntity = entity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
        return optionalLivingEntity.map(livingTargetCache -> livingTargetCache.contains(this.getAttackTarget(entity))).orElse(false);
    }

    private LivingEntity getAttackTarget(TulpaEntity entity) {
        Optional<LivingEntity> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE);
        Optional<UUID> optionalMemory2 = entity.getBrain().getOptionalMemory(MemoryModuleType.ANGRY_AT);

        if(optionalMemory.isPresent()){
            return optionalMemory.get();
        }
        if(optionalMemory2.isPresent()){
            return LookTargetUtil.getEntity(entity, MemoryModuleType.ANGRY_AT).get();
        }
        return null;
    }

    private boolean isHoldingUsableRangedWeapon(MobEntity entity) {
        return entity.isHolding((stack) -> {
            Item item = stack.getItem();
            return item instanceof RangedWeaponItem && entity.canUseRangedWeapon((RangedWeaponItem)item);
        });
    }

    public boolean isInMeleeAttackRange(LivingEntity target) {
        double d = tulpaEntity.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
        return d <= tulpaEntity.getWidth() * 4.0F * tulpaEntity.getWidth() * 4.0F + target.getWidth();
    }
}
