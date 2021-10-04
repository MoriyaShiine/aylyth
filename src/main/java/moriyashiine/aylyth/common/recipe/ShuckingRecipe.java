package moriyashiine.aylyth.common.recipe;

import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModRecipeTypes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Random;

public class ShuckingRecipe extends SpecialCraftingRecipe {
	private static final Random RANDOM = new Random();
	
	public ShuckingRecipe(Identifier id) {
		super(id);
	}
	
	@Override
	public boolean matches(CraftingInventory inv, World world) {
		boolean foundDagger = false, foundFruit = false;
		int foundItems = 0;
		for (int i = 0; i < inv.size(); i++) {
			ItemStack stack = inv.getStack(i);
			if (stack.isOf(ModItems.YMPE_DAGGER)) {
				if (!foundDagger) {
					foundDagger = true;
				}
				foundItems++;
			}
			else if (stack.isOf(ModItems.YMPE_FRUIT)) {
				if (!foundFruit) {
					foundFruit = true;
				}
				foundItems++;
			}
		}
		return foundDagger && foundFruit && foundItems == 2;
	}
	
	@Override
	public ItemStack craft(CraftingInventory inv) {
		//todo make this work
		return ItemStack.EMPTY;
		//		ItemStack dagger = null, fruit = null;
		//		for (int i = 0; i < inv.size(); i++) {
		//			ItemStack stack = inv.getStack(i);
		//			if (stack.isOf(ModItems.YMPE_DAGGER)) {
		//				dagger = stack.copy();
		//				dagger.damage(1, RANDOM, null);
		//			}
		//			else if (stack.isOf(ModItems.YMPE_FRUIT)) {
		//				fruit = stack.copy();
		//				fruit.decrement(1);
		//			}
		//		}
		//		return new ItemStack(ModItems.SHUCKED_YMPE_FRUIT);
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeTypes.SHUCKING_RECIPE_SERIALIZER;
	}
}
