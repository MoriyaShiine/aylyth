package moriyashiine.aylyth.common.item;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public interface AylythBoatTypes {

	RegistryKey<TerraformBoatType> YMPE = TerraformBoatTypeRegistry.createKey(Aylyth.id("ympe"));
	RegistryKey<TerraformBoatType> POMEGRANATE = TerraformBoatTypeRegistry.createKey(Aylyth.id("pomegranate"));
	RegistryKey<TerraformBoatType> WRITHEWOOD = TerraformBoatTypeRegistry.createKey(Aylyth.id("writhewood"));

	static void register() {
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, YMPE, new TerraformBoatType.Builder().item(AylythItems.YMPE_BOAT).chestItem(AylythItems.YMPE_CHEST_BOAT).planks(AylythItems.YMPE_PLANKS).build());
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, POMEGRANATE, new TerraformBoatType.Builder().item(AylythItems.POMEGRANATE_BOAT).chestItem(AylythItems.POMEGRANATE_CHEST_BOAT).planks(AylythItems.POMEGRANATE_PLANKS).build());
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, WRITHEWOOD, new TerraformBoatType.Builder().item(AylythItems.WRITHEWOOD_BOAT).chestItem(AylythItems.WRITHEWOOD_CHEST_BOAT).planks(AylythItems.WRITHEWOOD_PLANKS).build());
	}
}
