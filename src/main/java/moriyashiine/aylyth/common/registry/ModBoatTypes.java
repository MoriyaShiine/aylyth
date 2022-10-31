package moriyashiine.aylyth.common.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModBoatTypes {
	public static final TerraformBoatType YMPE_BOAT_TYPE = new TerraformBoatType.Builder().item(ModItems.YMPE_ITEMS.boat).build();
	public static final TerraformBoatType YMPE_CHEST_BOAT_TYPE = new TerraformBoatType.Builder().item(ModItems.YMPE_ITEMS.chestBoat).chestItem(Items.CHEST).build();
	public static final TerraformBoatType POMEGRANATE_BOAT_TYPE = new TerraformBoatType.Builder().item(ModItems.POMEGRANATE_ITEMS.boat).build();
	public static final TerraformBoatType POMEGRANATE_CHEST_BOAT_TYPE = new TerraformBoatType.Builder().item(ModItems.POMEGRANATE_ITEMS.chestBoat).chestItem(Items.CHEST).build();
	public static final TerraformBoatType WRITHEWOOD_BOAT_TYPE = new TerraformBoatType.Builder().item(ModItems.WRITHEWOOD_ITEMS.boat).build();
	public static final TerraformBoatType WRITHEWOOD_CHEST_BOAT_TYPE = new TerraformBoatType.Builder().item(ModItems.WRITHEWOOD_ITEMS.chestBoat).chestItem(Items.CHEST).build();
	
	public static void init() {}

	static {
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "ympe"), YMPE_BOAT_TYPE);
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "ympe_chest"), YMPE_CHEST_BOAT_TYPE);
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "pomegranate"), POMEGRANATE_BOAT_TYPE);
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "pomegranate_chest"), POMEGRANATE_CHEST_BOAT_TYPE);
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "writhewood"), WRITHEWOOD_BOAT_TYPE);
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "writhewood_chest"), WRITHEWOOD_CHEST_BOAT_TYPE);
	}
}
