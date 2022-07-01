package moriyashiine.aylyth.common.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModBoatTypes {
	public static final TerraformBoatType YMPE_BOAT_TYPE;
	public static final TerraformBoatType YMPE_CHEST_BOAT_TYPE;
	
	public static void init() {}

	static {
		YMPE_BOAT_TYPE = new TerraformBoatType.Builder().item(TerraformBoatItemHelper.registerBoatItem(new Identifier(Aylyth.MOD_ID, "ympe_boat"), boatSupplier(), false, ModItems.GROUP)).build();
		YMPE_CHEST_BOAT_TYPE = new TerraformBoatType.Builder().item(TerraformBoatItemHelper.registerBoatItem(new Identifier(Aylyth.MOD_ID, "ympe_chest_boat"), boatChestSupplier(), true, ModItems.GROUP)).chestItem(Items.CHEST).build();

		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "ympe"), YMPE_BOAT_TYPE);
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "ympe_chest"), YMPE_CHEST_BOAT_TYPE);
	}

	static Supplier<TerraformBoatType> boatSupplier() {
		return () -> YMPE_BOAT_TYPE;
	}
	static Supplier<TerraformBoatType> boatChestSupplier() {
		return () -> YMPE_CHEST_BOAT_TYPE;
	}
}
