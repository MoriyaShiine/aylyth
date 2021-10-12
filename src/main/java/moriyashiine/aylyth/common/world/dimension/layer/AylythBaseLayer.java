package moriyashiine.aylyth.common.world.dimension.layer;

import moriyashiine.aylyth.common.registry.ModBiomes;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum AylythBaseLayer implements InitLayer {
	INSTANCE;

	@Override
	public int sample(LayerRandomnessSource context, int x, int z) {
		return AylythBiomeSource.getId(ModBiomes.OVERGROWN_CLEARING_ID);
	}
}
