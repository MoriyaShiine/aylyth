package moriyashiine.aylyth.common.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBoatTypes {
	public static TerraformBoatType ympe;
	
	public static void init() {
		Item ympe_boat = TerraformBoatItemHelper.registerBoatItem(new Identifier(Aylyth.MOD_ID, "ympe_boat"), () -> ympe, ModItems.GROUP);
		ympe = new TerraformBoatType.Builder().item(ympe_boat).build();
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(Aylyth.MOD_ID, "ympe"), ympe);
	}
}
