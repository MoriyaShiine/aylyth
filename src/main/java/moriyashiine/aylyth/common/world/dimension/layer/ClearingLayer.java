package moriyashiine.aylyth.common.world.dimension.layer;

import moriyashiine.aylyth.common.registry.ModBiomes;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.type.DiagonalCrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum ClearingLayer implements CrossSamplingLayer {
	INSTANCE;


	@Override
	public int sample(LayerRandomnessSource context,  int n, int e, int s, int w, int center) {
		int[] directions = {n, e, s, w};
		return shouldTransition(center, directions) ? getTransitionBiome() : center;
	}

	int getTransitionBiome() {
		return AylythBiomeSource.getId(ModBiomes.CLEARING_ID);
	}

	boolean shouldTransition(int value, int[] directions) {
		int overgrownId = AylythBiomeSource.getId(ModBiomes.OVERGROWN_CLEARING_ID);
		if (value != overgrownId) {
			return false;
		}
		int surrounding = 0;
		for (int direction : directions) {
			if (direction == overgrownId) {
				surrounding++;
			}
		}
		return surrounding >= 4;
	}
}
