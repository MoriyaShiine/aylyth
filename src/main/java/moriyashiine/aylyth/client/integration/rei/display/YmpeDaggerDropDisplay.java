package moriyashiine.aylyth.client.integration.rei.display;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import moriyashiine.aylyth.client.integration.rei.ModREIPlugin;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;

import java.util.Collections;
import java.util.List;

public class YmpeDaggerDropDisplay implements Display {
	private final List<EntryIngredient> input;
	private final List<EntryIngredient> output;
	
	public YmpeDaggerDropDisplay(YmpeDaggerDropRecipe recipe) {
		input = Collections.singletonList(EntryIngredients.of(new ItemStack(Items.SPAWNER).setCustomName(recipe.entity_type.getName())));
		output = Collections.singletonList(EntryIngredients.of(recipe.getOutput(DynamicRegistryManager.EMPTY)));
	}
	
	@Override
	public List<EntryIngredient> getInputEntries() {
		return input;
	}
	
	@Override
	public List<EntryIngredient> getOutputEntries() {
		return output;
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return ModREIPlugin.YMPE_DAGGER_DROPS;
	}
}
