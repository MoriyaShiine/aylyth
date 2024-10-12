package moriyashiine.aylyth.client.integration.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import moriyashiine.aylyth.client.integration.rei.category.DaggerDropCategory;
import moriyashiine.aylyth.client.integration.rei.display.DaggerDropDisplay;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import moriyashiine.aylyth.common.loot.display.DaggerLootDisplay;
import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModREIPlugin implements REIClientPlugin {
	public static final CategoryIdentifier<DaggerDropDisplay> DAGGER_DROPS = CategoryIdentifier.of(Aylyth.id("dagger_drops"));
	
	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new DaggerDropCategory());
		registry.addWorkstations(DAGGER_DROPS, DaggerDropCategory.ICON);
		registry.setPlusButtonArea(DAGGER_DROPS, bounds -> null);
	}
	
	@Override
	public void registerDisplays(DisplayRegistry registry) {
		DynamicRegistryManager registryManager = MinecraftClient.getInstance().world.getRegistryManager();
		for (RegistryEntry<LootDisplay> display : registryManager.get(AylythRegistryKeys.LOOT_TABLE_DISPLAY).getIndexedEntries()) {
			if (display.value() instanceof DaggerLootDisplay daggerLootDisplay) {
				Identifier id = display.getKey().orElseThrow().getValue();
				registry.add(new DaggerDropDisplay(id, daggerLootDisplay.entity(), daggerLootDisplay.chance(), daggerLootDisplay.outputs()));
			}
		}
	}
}
