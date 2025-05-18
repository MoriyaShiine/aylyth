package moriyashiine.aylyth.common.item.types;

import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PomegranateItem extends Item {
    public PomegranateItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        FoodComponent food = stack.get(DataComponentTypes.FOOD);
        if (food != null && world.getRegistryKey() != AylythDimensionData.WORLD) {
            stack.set(DataComponentTypes.FOOD, new FoodComponent(food.nutrition() / 2, food.saturation() / 2f, food.canAlwaysEat()));
            ItemStack newStack = super.finishUsing(stack, world, user);
            newStack.set(DataComponentTypes.FOOD, food);
            return newStack;
        }
        return super.finishUsing(stack, world, user);
    }
}
