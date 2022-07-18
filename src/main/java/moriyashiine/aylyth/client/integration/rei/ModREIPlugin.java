package moriyashiine.aylyth.client.integration.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import moriyashiine.aylyth.client.integration.rei.category.YmpeDaggerDropCategory;
import moriyashiine.aylyth.client.integration.rei.display.YmpeDaggerDropDisplay;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import net.minecraft.util.Identifier;

public class ModREIPlugin implements REIClientPlugin {
	public static final CategoryIdentifier<YmpeDaggerDropDisplay> YMPE_DAGGER_DROPS = CategoryIdentifier.of(new Identifier(Aylyth.MOD_ID, "ympe_dagger_drops"));
	
	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new YmpeDaggerDropCategory());
		registry.addWorkstations(YMPE_DAGGER_DROPS, YmpeDaggerDropCategory.ICON);
		registry.setPlusButtonArea(YMPE_DAGGER_DROPS, bounds -> null);
	}
	
	@Override
	public void registerDisplays(DisplayRegistry registry) {
		registry.registerFiller(YmpeDaggerDropRecipe.class, YmpeDaggerDropDisplay::new);
	}
}
