package moriyashiine.aylyth.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.api.interfaces.ProlongedDeath;
import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import moriyashiine.aylyth.common.item.YmpeEffigyItem;
import moriyashiine.aylyth.common.registry.ModEntityComponents;
import moriyashiine.aylyth.common.registry.AylythItems;
import moriyashiine.aylyth.common.registry.AylythEntityStatusEffects;
import moriyashiine.aylyth.common.data.AylythDamageTypes;
import moriyashiine.aylyth.common.data.tag.AylythStatusEffectTags;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
	private float applySpecialDamage(float value, DamageSource source) {
		if (source.getAttacker() instanceof LivingEntity entity && !source.getAttacker().getWorld().isClient) {
			double attkDMG = entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
			ItemStack stack = entity.getMainHandStack();
			boolean usingVampiric = stack.isIn(AylythItemTags.VAMPIRIC_WEAPON);
			boolean usingBlight =  stack.isIn(AylythItemTags.BLIGHTED_WEAPON);

            if (value >= attkDMG) { // Prevents using non-critical attacks to spam the weapons
				if (usingVampiric) return AylythUtil.getVampiricWeaponEffect(entity, (LivingEntity) (Object) this, stack, value);
				if (usingBlight) return AylythUtil.getBlightedWeaponEffect(entity, (LivingEntity) (Object) this, stack, value);
            }

		}

		return value;
	}

	@Inject(method = "heal", at = @At("HEAD"), cancellable = true)
	private void preventHeal(float amount, CallbackInfo callbackInfo) {
		if (this.hasStatusEffect(AylythEntityStatusEffects.CRIMSON_CURSE)) {
			callbackInfo.cancel();
		}
	}

	@Inject(method = "drop", at = @At("HEAD"), cancellable = true)
	private void shuckLogic(DamageSource source, CallbackInfo ci) {
		if ((LivingEntity) (Object) this instanceof MobEntity mob && ModEntityComponents.PREVENT_DROPS.get(mob).getPreventsDrops()) {
			ci.cancel();
		}
	}

	@WrapWithCondition(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;dropInventory()V"))
	private boolean keepPledgedInv(LivingEntity instance, @Local(argsOnly = true) DamageSource damageSource) {
        return !(instance instanceof PlayerEntity player) || !damageSource.isOf(AylythDamageTypes.YMPE) || ((HindPledgeHolder) player).getHindUuid() == null;
    }
	
	@Inject(method = "eatFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyFoodEffects(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)V"))
	private void decreaseYmpeInfestationStage(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		if ((LivingEntity) (Object) this instanceof PlayerEntity player && stack.isIn(AylythItemTags.DECREASES_BRANCHES)) {
			ModEntityComponents.YMPE_INFESTATION.maybeGet(player).ifPresent(ympeInfestationComponent -> {
				if (ympeInfestationComponent.getStage() > 0) {
					ympeInfestationComponent.setStage((byte) (ympeInfestationComponent.getStage() - 1));
				}
				else if (ympeInfestationComponent.getInfestationTimer() > 0) {
					ympeInfestationComponent.setInfestationTimer((short) 0);
				}
			});
		}
	}

	@Inject(method = "stopRiding", at = @At("HEAD"))
	private void dismountAllFromBonefly(CallbackInfo ci) {
		if ((LivingEntity) (Object) this instanceof PlayerEntity && this.getVehicle() instanceof BoneflyEntity fly) {
			fly.getPassengerList().forEach(Entity::dismountVehicle);
		}
	}

	@Inject(method = "getGroup", at = @At("HEAD"), cancellable = true)
	private void makeUndeadWithEffigy(CallbackInfoReturnable<EntityGroup> cir) {
		if (YmpeEffigyItem.isEquipped((LivingEntity)(Object)this)) {
			cir.setReturnValue(EntityGroup.UNDEAD);
		}
	}

	@Inject(method = "hurtByWater", at = @At("HEAD"), cancellable = true)
	private void waterHurtsWithEffigy(CallbackInfoReturnable<Boolean> cir) {
		if (YmpeEffigyItem.isEquipped((LivingEntity)(Object)this) && (this.getWorld().getBiome(this.getBlockPos()).isIn(BiomeTags.IS_RIVER) || fluidHeight.getDouble(FluidTags.WATER) > 0)) {
			cir.setReturnValue(true);
		}
	}

	@ModifyConstant(method = "updatePostDeath", constant = @Constant(intValue = 20))
	private int updatePostDeath(int constant){
		LivingEntity living = (LivingEntity) (Object) this;
		return ProlongedDeath.of(living).map(ProlongedDeath::getDeathAnimationTime).orElse(constant);
	}

	@Inject(method = "updatePostDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;sendEntityStatus(Lnet/minecraft/entity/Entity;B)V"))
	private void injectLootDrop(CallbackInfo ci){
		LivingEntity living = (LivingEntity) (Object) this;
		if(living instanceof ProlongedDeath){
			ItemScatterer.spawn(living.getWorld(), living.getX(), living.getY() + 1.5D, living.getZ(), AylythItems.CORIC_SEED.getDefaultStack());
		}
	}

	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	public void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (this.isPlayer()) {
            LivingEntity entity = ((LivingEntity) (Object) this);

			boolean bypassesEffigy = TagUtil.isIn(AylythStatusEffectTags.BYPASSES_EFFIGY, effect.getEffectType());
            if (!bypassesEffigy && YmpeEffigyItem.isEquipped(entity)) {
                cir.setReturnValue(false);
            }
        }
	}
}
