package moriyashiine.aylyth.common.recipe.types;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.recipe.AylythRecipeTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ShuckingRecipe implements CraftingRecipe {
	private final ShapelessRecipe recipe;

	public ShuckingRecipe(ShapelessRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
		DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);
		for (int i = 0; i < defaultedList.size(); i++) {
			ItemStack stack = input.getStackInSlot(i);
			if (stack.isOf(AylythItems.YMPE_DAGGER)) {
				ItemStack dagger = stack.copy();
				dagger.setDamage(dagger.getDamage()+1);
				if (dagger.getDamage() >= dagger.getMaxDamage()) {
					dagger = ItemStack.EMPTY;
				}
				defaultedList.set(i, dagger);
			}
		}
		return defaultedList;
	}

	@Override
	public boolean matches(CraftingRecipeInput input, World world) {
		return recipe.matches(input, world);
	}

	@Override
	public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
		return recipe.craft(input, registries);
	}

	@Override
	public RecipeSerializer<? extends CraftingRecipe> getSerializer() {
		return AylythRecipeTypes.SHUCKING_SERIALIZER;
	}

	@Override
	public IngredientPlacement getIngredientPlacement() {
		return recipe.getIngredientPlacement();
	}

	@Override
	public CraftingRecipeCategory getCategory() {
		return recipe.getCategory();
	}

	@Override
	public String getGroup() {
		return recipe.getGroup();
	}

	public static class Serializer implements RecipeSerializer<ShuckingRecipe> {
		@Override
		public MapCodec<ShuckingRecipe> codec() {
			return SHAPELESS.codec().xmap(ShuckingRecipe::new, shuckingRecipe -> shuckingRecipe.recipe);
		}

		@Override
		public PacketCodec<RegistryByteBuf, ShuckingRecipe> packetCodec() {
			return SHAPELESS.packetCodec().xmap(ShuckingRecipe::new, shuckingRecipe -> shuckingRecipe.recipe);
		}
	}
}
