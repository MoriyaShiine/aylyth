package moriyashiine.aylyth.common.registry;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
	public static final FoodComponent YMPE_FRUIT = new FoodComponent.Builder().alwaysEdible().hunger(6).saturationModifier(0.5f).meat().build();
	public static final FoodComponent NYSIAN_GRAPES = new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).build();
}
