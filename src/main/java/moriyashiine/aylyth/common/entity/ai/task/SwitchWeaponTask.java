package moriyashiine.aylyth.common.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SwitchWeaponTask extends Task<TulpaEntity> {
    private TulpaEntity tulpaEntity;
    public SwitchWeaponTask(TulpaEntity tulpaEntity) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT));
        this.tulpaEntity = tulpaEntity;
    }

    protected boolean shouldRun(ServerWorld serverWorld, TulpaEntity mobEntity) {
        return this.isAttackTargetVisible(mobEntity);
    }

    protected void run(ServerWorld serverWorld, TulpaEntity tulpaEntity, long l) {
        tulpaEntity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(this.getAttackTarget(tulpaEntity), true));
        LivingEntity livingEntity = this.getAttackTarget(tulpaEntity);
        if(isInMeleeAttackRange(livingEntity)){
            List<Pair<ItemStack, Double>> meleeWeaponList = new ArrayList<>();
            for(ItemStack newWeapon : tulpaEntity.getInventory().stacks){
                if(newWeapon.getItem() instanceof SwordItem){
                    meleeWeaponList.add(new Pair<>(newWeapon, (double)((SwordItem) newWeapon.getItem()).getAttackDamage()));
                }
            }
            if(!meleeWeaponList.isEmpty()){
                meleeWeaponList.sort(Comparator.comparingDouble(Pair::getSecond));
                switchWeapons(meleeWeaponList.get(0).getFirst());
            }
        }else if(!isHoldingUsableRangedWeapon(tulpaEntity)){
            for(ItemStack newWeapon : tulpaEntity.getInventory().stacks){
                if(newWeapon.getItem() instanceof RangedWeaponItem && tulpaEntity.canUseRangedWeapon((RangedWeaponItem)newWeapon.getItem())){
                    switchWeapons(newWeapon);
                }
            }
        }
    }

    private void switchWeapons(ItemStack newWeapon){
        int index = tulpaEntity.getInventory().stacks.indexOf(newWeapon);
        ItemStack prevWeapon = tulpaEntity.getMainHandStack();
        tulpaEntity.equipStack(EquipmentSlot.MAINHAND, newWeapon);
        tulpaEntity.getInventory().setStack(index, prevWeapon);
    }

    private boolean isAttackTargetVisible(TulpaEntity entity) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get().contains(this.getAttackTarget(entity));
    }

    private LivingEntity getAttackTarget(TulpaEntity entity) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
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
