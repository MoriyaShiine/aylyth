package moriyashiine.aylyth.common.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModBoatTypes {
	public static final RegistryKey<TerraformBoatType> YMPE_BOAT_TYPE = TerraformBoatTypeRegistry.createKey(AylythUtil.id("ympe"));
	public static final RegistryKey<TerraformBoatType> POMEGRANATE_BOAT_TYPE = TerraformBoatTypeRegistry.createKey(AylythUtil.id("pomegranate"));
	public static final RegistryKey<TerraformBoatType> WRITHEWOOD_BOAT_TYPE = TerraformBoatTypeRegistry.createKey(AylythUtil.id("writhewood"));

	public static void init() {
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, YMPE_BOAT_TYPE, new TerraformBoatType.Builder().item(ModItems.YMPE_ITEMS.boat).chestItem(ModItems.YMPE_ITEMS.chestBoat).planks(ModItems.YMPE_ITEMS.planks).build());
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, POMEGRANATE_BOAT_TYPE, new TerraformBoatType.Builder().item(ModItems.POMEGRANATE_ITEMS.boat).chestItem(ModItems.POMEGRANATE_ITEMS.chestBoat).planks(ModItems.POMEGRANATE_ITEMS.planks).build());
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, WRITHEWOOD_BOAT_TYPE, new TerraformBoatType.Builder().item(ModItems.WRITHEWOOD_ITEMS.boat).chestItem(ModItems.WRITHEWOOD_ITEMS.chestBoat).planks(ModItems.WRITHEWOOD_ITEMS.planks).build());
	}
}
