package moriyashiine.aylyth.common.world.dimension.layer;

import moriyashiine.aylyth.common.registry.ModBiomes;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum DeepForestLayer implements CrossSamplingLayer {
	INSTANCE;

	@Override
	public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
		return Math.abs(context.getNoiseSampler().sample(x, 0, z)) % 0.1D < 0.08D ? parent.sample(x, z) : CrossSamplingLayer.super.sample(context, parent, x, z);
	}

	@Override
	public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
		int forestId = AylythBiomeSource.getId(ModBiomes.AYLYTHIAN_FOREST_ID);
		int deepForestId = AylythBiomeSource.getId(ModBiomes.DEEP_AYLYTHIAN_FOREST_ID);
		int[] sides = {n, e, s, w};
		int sidesCount = 0;
		if (center == forestId) {
			for (int side : sides) {
				if (side == forestId) {
					sidesCount++;
				}
			}
			if (sidesCount >= 3) {
				return deepForestId;
			}
		}
		return center;
	}
}
