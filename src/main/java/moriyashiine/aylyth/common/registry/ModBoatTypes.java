package moriyashiine.aylyth.common.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class ModBoatTypes {
	public static final RegistryKey<TerraformBoatType> YMPE_BOAT_TYPE = TerraformBoatTypeRegistry.createKey(AylythUtil.id("ympe"));
	public static final RegistryKey<TerraformBoatType> POMEGRANATE_BOAT_TYPE = TerraformBoatTypeRegistry.createKey(AylythUtil.id("pomegranate"));
	public static final RegistryKey<TerraformBoatType> WRITHEWOOD_BOAT_TYPE = TerraformBoatTypeRegistry.createKey(AylythUtil.id("writhewood"));

	public static void init() {
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, YMPE_BOAT_TYPE, new TerraformBoatType.Builder().item(ModItems.YMPE_BOAT).chestItem(ModItems.YMPE_CHEST_BOAT).planks(ModItems.YMPE_PLANKS).build());
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, POMEGRANATE_BOAT_TYPE, new TerraformBoatType.Builder().item(ModItems.POMEGRANATE_BOAT).chestItem(ModItems.POMEGRANATE_CHEST_BOAT).planks(ModItems.POMEGRANATE_PLANKS).build());
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, WRITHEWOOD_BOAT_TYPE, new TerraformBoatType.Builder().item(ModItems.WRITHEWOOD_BOAT).chestItem(ModItems.WRITHEWOOD_CHEST_BOAT).planks(ModItems.WRITHEWOOD_PLANKS).build());
	}
}
