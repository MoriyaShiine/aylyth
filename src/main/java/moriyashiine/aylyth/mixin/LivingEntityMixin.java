package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.api.interfaces.ProlongedDeath;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.registry.tag.ModItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow public int deathTime;

	@Shadow public abstract int getArmor();

	@Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

	@Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

	@Shadow public abstract void setHealth(float health);

	@Shadow public abstract float getHealth();

	@Shadow public abstract boolean isHolding(Item item);

	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

	@Shadow public abstract boolean isHolding(Predicate<ItemStack> predicate);

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	@ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
	float damage(float value, DamageSource source) {
		if(source.getAttacker() instanceof LivingEntity entity && !source.getAttacker().getWorld().isClient) {
			// #TODO add particles
			double attkDMG = entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
			boolean usingVampiric = Stream.of(ModItems.VAMPIRIC_SWORD, ModItems.VAMPIRIC_HOE, ModItems.VAMPIRIC_AXE, ModItems.VAMPIRIC_PICKAXE).anyMatch(item -> entity.getMainHandStack().isOf(item));
			boolean usingBlight = Stream.of(ModItems.BLIGHTED_SWORD, ModItems.BLIGHTED_HOE, ModItems.BLIGHTED_AXE, ModItems.BLIGHTED_PICKAXE).anyMatch(item -> entity.getMainHandStack().isOf(item));

            if (value < attkDMG) {
                return value; // if not a normal attack or higher...
            }

            if(usingVampiric) {
				boolean isSword = entity.getMainHandStack().isOf(ModItems.VAMPIRIC_SWORD);
				boolean isPickaxe = entity.getMainHandStack().isOf(ModItems.VAMPIRIC_PICKAXE);
				boolean isHoe = entity.getMainHandStack().isOf(ModItems.VAMPIRIC_HOE);

				if(entity.getRandom().nextFloat() <= 0.8)
					return value; // 20% chance of healing by 50%. all the other effects

				entity.heal(value * 0.5f);
				if(isSword && entity.getAbsorptionAmount() > 0) {
					entity.setAbsorptionAmount(entity.getAbsorptionAmount() <= 1 ? entity.getAbsorptionAmount() / 2f : 0);
					return value;
				}

				if(isHoe) {
					this.addStatusEffect(new StatusEffectInstance(ModPotions.CRIMSON_CURSE_EFFECT, 20 * 10, 0));
				}

				return value * (isPickaxe && this.getArmor() > 10f ? 1.2f : 1f);
			}

			if(usingBlight) {
				boolean isPickaxe = entity.getMainHandStack().isOf(ModItems.BLIGHTED_PICKAXE);
				boolean isHoe = entity.getMainHandStack().isOf(ModItems.BLIGHTED_HOE);
				boolean isSword = entity.getMainHandStack().isOf(ModItems.VAMPIRIC_SWORD);

				if(entity.getRandom().nextFloat() <= 0.75)
					return value;

				int amplifier = 0;
				if(entity.getRandom().nextFloat() <= 0.85 && this.hasStatusEffect(ModPotions.BLIGHT_EFFECT))
					amplifier = 1;

				this.addStatusEffect(new StatusEffectInstance(ModPotions.BLIGHT_EFFECT, 20 * 4, amplifier));

				if(isSword && entity.getAbsorptionAmount() > 0) {
					entity.setAbsorptionAmount(entity.getAbsorptionAmount() <= 1 ? entity.getAbsorptionAmount() / 2f : 0);
					return value;
				}

				if(isHoe)
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20 * 2, 0));

				return value * (isPickaxe && this.getArmor() > 10f ? 1.2f : 1f);
			}

		}

		return value;
	}

	@Inject(method = "heal", at = @At("HEAD"), cancellable = true)
	private void preventHeal(float amount, CallbackInfo callbackInfo) {
		if(this.hasStatusEffect(ModPotions.CRIMSON_CURSE_EFFECT)) {
			callbackInfo.cancel();
		}
	}

	@Inject(method = "drop", at = @At("HEAD"), cancellable = true)
	private void aylyth_shuckLogic(DamageSource source, CallbackInfo ci) {
		if ((LivingEntity) (Object) this instanceof MobEntity mob && ModComponents.PREVENT_DROPS.get(mob).getPreventsDrops()) {
			ci.cancel();
		}
	}
	
	@Inject(method = "eatFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyFoodEffects(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)V"))
	private void aylyth_decreaseYmpeInfestationStage(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		if ((LivingEntity) (Object) this instanceof PlayerEntity player && stack.isIn(ModItemTags.YMPE_FOODS)) {
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

	@ModifyConstant(method = "updatePostDeath", constant = @Constant(intValue = 20))
	private int aylyth_updatePostDeath(int constant){
		LivingEntity living = (LivingEntity) (Object) this;
		return ProlongedDeath.of(living).map(ProlongedDeath::getDeathAnimationTime).orElse(constant);
	}

	@Inject(method = "updatePostDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;sendEntityStatus(Lnet/minecraft/entity/Entity;B)V"))
	private void aylyth_injectLootDrop(CallbackInfo ci){
		LivingEntity living = (LivingEntity) (Object) this;
		if(living instanceof ProlongedDeath){
			ItemScatterer.spawn(living.getWorld(), living.getX(), living.getY() + 1.5D, living.getZ(), ModItems.CORIC_SEED.getDefaultStack());
		}
	}


	private static final StatusEffect[] persistentStatusEffects = { ModPotions.CRIMSON_CURSE_EFFECT,
			StatusEffects.WITHER, StatusEffects.INSTANT_DAMAGE, StatusEffects.INSTANT_HEALTH };

	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	public void aylyth_canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (!this.isPlayer()) {
            return;
		}

        LivingEntity entity = ((LivingEntity) (Object) this);
        if(entity instanceof PlayerEntity && ((PlayerEntity) entity).getInventory().contains(ModItems.YMPE_EFFIGY_ITEM.getDefaultStack()))
            Arrays.stream(persistentStatusEffects).filter(entity::hasStatusEffect).map(persistentStatusEffect -> false).forEach(cir::setReturnValue);
	}
}
