package moriyashiine.aylyth.common.world.dimension.layer;

import moriyashiine.aylyth.common.registry.ModBiomes;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum ForestLayer implements ParentedLayer {
	NORMAL, CONIFEROUS {
		@Override
		double getChance() {
			return 0.15F;
		}
		
		@Override
		int getBiome() {
			return AylythBiomeSource.getId(ModBiomes.CONIFEROUS_FOREST_ID);
		}
	};
	
	@Override
	public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
		int base = parent.sample(this.transformX(x), this.transformZ(z));
		int biome = getBiome();
		int[] surroundings = {parent.sample(x - 1, z + 1), parent.sample(x, z + 1), parent.sample(x + 1, z + 1), parent.sample(x - 1, z), parent.sample(x, z), parent.sample(x + 1, z), parent.sample(x - 1, z - 1), parent.sample(x, z - 1), parent.sample(x + 1, z - 1)};
		
		int baseId = AylythBiomeSource.getId(ModBiomes.OVERGROWN_CLEARING_ID);
		if (base != baseId) {
			return base;
		}
		int surroundingsCount = 0;
		for (int surrounding : surroundings) {
			if (surrounding == baseId || surrounding == biome) {
				surroundingsCount++;
			}
		}
		if (surroundingsCount < 7) {
			return base;
		}
		
		double noise = Math.abs(context.getNoiseSampler().sample(x, 63, z)) % 0.2;
		if (noise < getChance()) {
			return biome;
		}
		return base;
	}
	
	int getBiome() {
		return AylythBiomeSource.getId(ModBiomes.FOREST_ID);
	}
	
	double getChance() {
		return 0.08D;
	}
	
	@Override
	public int transformX(int x) {
		return x;
	}
	
	@Override
	public int transformZ(int z) {
		return z;
	}
}
