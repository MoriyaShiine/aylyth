package moriyashiine.aylyth.common.item;

import net.minecraft.component.type.FoodComponent;

public interface AylythFoodComponents {

	FoodComponent YMPE_MUSH = new FoodComponent.Builder()
			.alwaysEdible()
			.nutrition(6)
			.saturationModifier(0.5f)
			.build();
	FoodComponent YMPE_FRUIT = new FoodComponent.Builder()
			.alwaysEdible()
			.nutrition(6)
			.saturationModifier(0.5f)
			.build();
	FoodComponent NYSIAN_GRAPES = new FoodComponent.Builder()
			.nutrition(2)
			.saturationModifier(0.5f)
			.build();
	FoodComponent GHOSTCAPS = new FoodComponent.Builder()
			.nutrition(1)
			.saturationModifier(0.75f)
			.build();
	FoodComponent POMEGRANATE = new FoodComponent.Builder()
			.nutrition(4)
			.saturationModifier(5)
			.build();
	FoodComponent WRONGMEAT = new FoodComponent.Builder()
			.nutrition(16)
			.saturationModifier(16)
			.build();
}
