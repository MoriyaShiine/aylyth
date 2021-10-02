package moriyashiine.aylyth.common;

import moriyashiine.aylyth.common.registry.ModWorldGenerators;
import net.fabricmc.api.ModInitializer;

public class Aylyth implements ModInitializer {
	@Override
	public void onInitialize() {
		ModWorldGenerators.init();
	}
}
