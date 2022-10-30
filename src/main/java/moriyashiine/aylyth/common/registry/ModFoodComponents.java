package moriyashiine.aylyth.common.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
	public static final FoodComponent YMPE_FRUIT = new FoodComponent.Builder().alwaysEdible().hunger(6).saturationModifier(0.5f).meat().build();
	public static final FoodComponent NYSIAN_GRAPES = new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).build();
	public static final FoodComponent GHOSTCAPS = new FoodComponent.Builder().hunger(1).saturationModifier(0.75f).statusEffect(new StatusEffectInstance(ModPotions.CIMMERIAN_EFFECT, 1800, 0), 0.125f).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0), 0.875f).build();
	public static final FoodComponent POMEGRANATE = new FoodComponent.Builder().hunger(4).saturationModifier(5).statusEffect(new StatusEffectInstance(ModPotions.CIMMERIAN_EFFECT, 1800, 0), 0.5f).statusEffect(new StatusEffectInstance(ModPotions.WYRDED_EFFECT, 6000, 0), 0.5f).build();
}
