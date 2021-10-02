package moriyashiine.aylyth.common;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModBoatTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModWorldGenerators;
import net.fabricmc.api.ModInitializer;

public class Aylyth implements ModInitializer {
	public static final String MOD_ID = "aylyth";
	
	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModItems.init();
		ModBoatTypes.init();
		ModWorldGenerators.init();
	}
}
