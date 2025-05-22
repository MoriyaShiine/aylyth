package moriyashiine.aylyth.client.integration.rei.display;

import it.unimi.dsi.fastutil.objects.ObjectLists;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import moriyashiine.aylyth.client.integration.rei.ModREIPlugin;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DaggerDropDisplay implements Display {
	private final Identifier id;
	private final List<EntryIngredient> input;
	private final List<EntryIngredient> output;
	
	public DaggerDropDisplay(Identifier id, EntityType<?> entity, float chance, ItemStack output) {
		this.id = id;
		input = ObjectLists.singleton(EntryIngredients.of(new ItemStack(Items.SPAWNER.getRegistryEntry(), 1, ComponentChanges.builder().add(DataComponentTypes.CUSTOM_NAME, entity.getName()).build())));
		this.output = ObjectLists.singleton(EntryIngredients.of(output));
	}

	@Override
	public Optional<Identifier> getDisplayLocation() {
		return Optional.of(id);
	}

	// TODO: Consider
	@Override
	public @Nullable DisplaySerializer<? extends Display> getSerializer() {
		return null;
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
		return ModREIPlugin.DAGGER_DROPS;
	}
}
