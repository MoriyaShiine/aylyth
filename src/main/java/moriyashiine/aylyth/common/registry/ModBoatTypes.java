package moriyashiine.aylyth.common.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBoatTypes {
	public static final TerraformBoatType YMPE_BOAT_TYPE = new TerraformBoatType.Builder().item(ModItems.YMPE_ITEMS.boat).chestItem(ModItems.YMPE_ITEMS.chestBoat).planks(ModItems.YMPE_ITEMS.planks).build();
	public static final TerraformBoatType POMEGRANATE_BOAT_TYPE = new TerraformBoatType.Builder().item(ModItems.POMEGRANATE_ITEMS.boat).chestItem(ModItems.POMEGRANATE_ITEMS.chestBoat).planks(ModItems.POMEGRANATE_ITEMS.planks).build();
	public static final TerraformBoatType WRITHEWOOD_BOAT_TYPE = new TerraformBoatType.Builder().item(ModItems.WRITHEWOOD_ITEMS.boat).chestItem(ModItems.WRITHEWOOD_ITEMS.chestBoat).planks(ModItems.WRITHEWOOD_ITEMS.planks).build();

	public static void init() {}

	static {
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "ympe"), YMPE_BOAT_TYPE);
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "pomegranate"), POMEGRANATE_BOAT_TYPE);
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "writhewood"), WRITHEWOOD_BOAT_TYPE);
	}
}
