package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.entity.statuseffect.AylythStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public interface AylythFoods {

	FoodComponent YMPE_FRUIT = new FoodComponent.Builder().alwaysEdible().hunger(6).saturationModifier(0.5f).meat().build();
	FoodComponent NYSIAN_GRAPES = new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).build();
	FoodComponent GHOSTCAPS = new FoodComponent.Builder().hunger(1).saturationModifier(0.75f)
			.statusEffect(new StatusEffectInstance(AylythStatusEffects.CIMMERIAN, 1800, 0), 0.125f)
			.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0), 0.875f)
			.build();
	FoodComponent POMEGRANATE = new FoodComponent.Builder().hunger(4).saturationModifier(5)
			.statusEffect(new StatusEffectInstance(AylythStatusEffects.CIMMERIAN, 1800, 0), 0.5f)
			.statusEffect(new StatusEffectInstance(AylythStatusEffects.WYRDED, 6000, 0), 0.5f)
			.build();
	FoodComponent WRONGMEAT = new FoodComponent.Builder().hunger(16).saturationModifier(16)
			.statusEffect(new StatusEffectInstance(AylythStatusEffects.CIMMERIAN, 1800, 0), 0.5f)
			.statusEffect(new StatusEffectInstance(AylythStatusEffects.WYRDED, 6000, 0), 0.5f)
			.meat()
			.build();
}
