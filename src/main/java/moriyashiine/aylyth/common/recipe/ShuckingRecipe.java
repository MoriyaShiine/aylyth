package moriyashiine.aylyth.common.recipe;

import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModRecipeTypes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
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
		return new ItemStack(ModItems.SHUCKED_YMPE_FRUIT);
	}

	@Override
	public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
		DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

		for(int i = 0; i < defaultedList.size(); ++i) {
			ItemStack stack = inventory.getStack(i);
			if (stack.isOf(ModItems.YMPE_DAGGER)) {
				ItemStack dagger = stack.copy();
				dagger.damage(1, RANDOM, null);
				defaultedList.set(i, dagger);
			}
		}

		return defaultedList;
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
