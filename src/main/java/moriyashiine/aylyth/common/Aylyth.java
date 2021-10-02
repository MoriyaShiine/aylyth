package moriyashiine.aylyth.common;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModWorldGenerators;
import net.fabricmc.api.ModInitializer;

public class Aylyth implements ModInitializer {
	public static final String MOD_ID = "aylyth";
	@Override
	public void onInitialize() {
		ModWorldGenerators.init();
		ModBlocks.init();
	}
}
