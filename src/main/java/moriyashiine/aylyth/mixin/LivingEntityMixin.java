package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.entity.SoulExplosionEntity;
import moriyashiine.aylyth.common.entity.mob.RippedSoulEntity;
import moriyashiine.aylyth.common.registry.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Unique
	private int soultrapTicks;

	@Inject(method = "drop", at = @At("HEAD"), cancellable = true)
	private void shuckLogic(DamageSource source, CallbackInfo ci) {
		if ((LivingEntity) (Object) this instanceof MobEntity mob && ModComponents.PREVENT_DROPS.get(mob).getPreventsDrops()) {
			ci.cancel();
		}
	}



	@Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
	private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		if((LivingEntity) (Object) this instanceof PlayerEntity player){
			if (player.getInventory().contains(ModItems.SOULTRAP_EFFIGY_ITEM.getDefaultStack()) && (source.getAttacker() instanceof PlayerEntity || source.getSource() instanceof PlayerEntity) && !(soultrapTicks >= 10)) {
				player.setHealth(1.0F);
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 400, 2, true, false));
				++this.soultrapTicks;
				cir.setReturnValue(true);
			} else {
				if(soultrapTicks >= 10) {
					this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 6.0f, Explosion.DestructionType.NONE);
					SoulExplosionEntity entity = new SoulExplosionEntity(ModEntityTypes.SOUL_EXPLOSION, this.world);
					entity.setPosition(this.getPos());
					player.world.spawnEntity(entity);
				}
			}
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
	}
}
