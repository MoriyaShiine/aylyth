package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.api.interfaces.Vital;
import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.entity.mob.RippedSoulEntity;
import moriyashiine.aylyth.common.registry.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "drop", at = @At("HEAD"), cancellable = true)
	private void shuckLogic(DamageSource source, CallbackInfo ci) {
		if ((LivingEntity) (Object) this instanceof MobEntity mob && ModComponents.PREVENT_DROPS.get(mob).getPreventsDrops()) {
			ci.cancel();
		}
	}
	
	@Inject(method = "eatFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyFoodEffects(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)V"))
	private void decreaseYmpeInfestationStage(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		if ((LivingEntity) (Object) this instanceof PlayerEntity player && stack.isIn(ModTags.YMPE_FOODS)) {
			ModComponents.YMPE_INFESTATION.maybeGet(player).ifPresent(ympeInfestationComponent -> {
				if (ympeInfestationComponent.getStage() > 0) {
					ympeInfestationComponent.setStage((byte) (ympeInfestationComponent.getStage() - 1));
				}
				else if (ympeInfestationComponent.getInfestationTimer() > 0) {
					ympeInfestationComponent.setInfestationTimer((short) 0);
				}
			});
		}
	}

	@Inject(method = "onDeath", at = @At("HEAD"))
	private void aylyth$onDeath(DamageSource source, CallbackInfo ci) {
		if(!world.isClient && source instanceof ModDamageSources.SoulRipDamageSource ripSource) {
			RippedSoulEntity soul = new RippedSoulEntity(ModEntityTypes.RIPPED_SOUL, this.getWorld());
			if (ripSource.getAttacker() != null) {
				soul.setOwner((PlayerEntity) ripSource.getAttacker());
			}
			soul.setPosition(this.getPos().add(0, 1, 0));
			this.world.spawnEntity(soul);
		}

		if((LivingEntity) (Object) this instanceof PlayerEntity player && AylythUtil.isSourceYmpe(source)){
			Vital.of(player).ifPresent(vital -> vital.setVitalThuribleLevel(0));
		}
	}
}
