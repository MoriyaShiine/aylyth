package moriyashiine.aylyth.common.entity.statuseffect.type;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class MortechisStatusEffect extends StatusEffect {
	public MortechisStatusEffect() {
		super(StatusEffectCategory.BENEFICIAL, 0x870043);
	}
	
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		if (!entity.getWorld().isClient && entity.age % 10 == 0) {
			float healthPercentage = entity.getHealth() / entity.getMaxHealth();
			if (healthPercentage < 0.75F) {
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, (healthPercentage < 0.45F ? 1 : 0) + amplifier, true, false, false));
				if (healthPercentage < 0.5F) {
					entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, (healthPercentage < 0.25F ? 1 : 0) + amplifier, true, false, false));
				}
			}
		}
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}
}
