package moriyashiine.aylyth.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.api.interfaces.ProlongedDeath;
import moriyashiine.aylyth.common.data.AylythDamageTypes;
import moriyashiine.aylyth.common.data.tag.AylythStatusEffectTags;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.YmpeThorns;
import moriyashiine.aylyth.common.entity.types.mob.BoneflyEntity;
import moriyashiine.aylyth.common.item.AylythDataComponentTypes;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.item.components.AttackEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow public abstract Collection<StatusEffectInstance> getStatusEffects();

	@Shadow public abstract @Nullable EntityAttributeInstance getAttributeInstance(RegistryEntry<EntityAttribute> attribute);

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo ci) {
		if (!getEntityWorld().isClient) {
			YmpeThorns ympeThorns = this.getAttached(AylythEntityAttachmentTypes.YMPE_THORNS);
			if (ympeThorns != null) {
				EntityAttributeInstance speedInst = this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);

				if (speedInst != null && speedInst.hasModifier(YmpeThorns.SPEED_MODIFIER))
					speedInst.removeModifier(YmpeThorns.SPEED_MODIFIER);

				if (ympeThorns.getStage() > 0) {
					if (speedInst != null && !speedInst.hasModifier(YmpeThorns.SPEED_MODIFIER)) {
						speedInst.addTemporaryModifier(
								new EntityAttributeModifier(
										YmpeThorns.SPEED_MODIFIER,
										-0.1 * ympeThorns.getStage(),
										EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
								)
						);
					}

					if (ympeThorns.getStageTimer() > 0 && ympeThorns.getStageTimer() % 60 == 0) {
						ympeThorns.addStage(-1);
					}
				}

				if (ympeThorns.getStage() > 0) {
					ympeThorns.addStageTimer(1);
				} else {
					ympeThorns.setStageTimer(0);
				}
				this.setAttached(AylythEntityAttachmentTypes.YMPE_THORNS, ympeThorns);
			}
		}
	}

	@ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
	private float applyAttackEffects(float amount, @Local(argsOnly = true) ServerWorld world, @Local(argsOnly = true) DamageSource source) {
		ItemStack stack = source.getWeaponStack();
		if (stack != null) {
			AttackEffects attackEffects = stack.get(AylythDataComponentTypes.ATTACK_EFFECTS);
			if (attackEffects != null) {
				return attackEffects.apply(world, (LivingEntity) (Object) this, source, amount);
			}
		}
		return amount;
	}

	@Inject(method = "heal", at = @At("HEAD"), cancellable = true)
	private void preventHeal(float amount, CallbackInfo callbackInfo) {
		for (StatusEffectInstance effect : this.getStatusEffects()) {
			if (effect.getEffectType().isIn(AylythStatusEffectTags.PREVENTS_HEALING)) {
				callbackInfo.cancel();
			}
		}
	}

	@Inject(method = "drop", at = @At("HEAD"), cancellable = true)
	private void shuckLogic(ServerWorld world, DamageSource damageSource, CallbackInfo ci) {
		if (this.hasAttached(AylythEntityAttachmentTypes.PREVENT_DROPS)) {
			ci.cancel();
		}
	}

	@WrapWithCondition(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;dropInventory(Lnet/minecraft/server/world/ServerWorld;)V"))
	private boolean keepPledgedInv(LivingEntity instance, ServerWorld world, @Local(argsOnly = true) DamageSource damageSource) {
        return !(instance instanceof PlayerEntity player) || !damageSource.isOf(AylythDamageTypes.YMPE) || ((HindPledgeHolder) player).getHindUuid() == null;
    }

	@Inject(method = "stopRiding", at = @At("HEAD"))
	private void dismountAllFromBonefly(CallbackInfo ci) {

		if ((LivingEntity) (Object) this instanceof PlayerEntity && this.getVehicle() instanceof BoneflyEntity fly) {
			fly.getPassengerList().forEach(Entity::dismountVehicle);
		}
	}

	// TODO: Reimplement similar functionality by finding vanilla locations where the "undead" tag is used.
	//  Reimplement when Trinkets is added back.
//	@Inject(method = "getGroup", at = @At("HEAD"), cancellable = true)
//	private void makeUndeadWithEffigy(CallbackInfoReturnable<EntityGroup> cir) {
//		if (AylythItems.YMPE_EFFIGY.isEquipped((LivingEntity)(Object)this)) {
//			cir.setReturnValue(EntityGroup.UNDEAD);
//		}
//	}

	// TODO: Reimplement when Trinkets is added back
//	@Inject(method = "hurtByWater", at = @At("HEAD"), cancellable = true)
//	private void waterHurtsWithEffigy(CallbackInfoReturnable<Boolean> cir) {
//		if (AylythItems.YMPE_EFFIGY.isEquipped((LivingEntity)(Object)this) && (this.getWorld().getBiome(this.getBlockPos()).isIn(BiomeTags.IS_RIVER) || fluidHeight.getDouble(FluidTags.WATER) > 0)) {
//			cir.setReturnValue(true);
//		}
//	}

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

	// TODO: Reimplement when Trinkets is added back
//	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
//	public void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
//        if (this.isPlayer()) {
//            LivingEntity entity = ((LivingEntity) (Object) this);
//
//            if (!effect.getEffectType().isIn(AylythStatusEffectTags.EFFIGY_CANNOT_CURE) && AylythItems.YMPE_EFFIGY.isEquipped(entity)) {
//                cir.setReturnValue(false);
//            }
//        }
//	}
}
