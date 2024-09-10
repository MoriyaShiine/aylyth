package moriyashiine.aylyth.common.world.generator.feature;

import moriyashiine.aylyth.common.block.BushBlock;
import moriyashiine.aylyth.common.registry.AylythBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BushFeature extends Feature<DefaultFeatureConfig> {
	public BushFeature() {
		super(DefaultFeatureConfig.CODEC);
	}
	
	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		Random random = context.getRandom();
		StructureWorldAccess structureWorldAccess = context.getWorld();
		BlockPos blockPos = structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, context.getOrigin());
		int clusters = 2 + random.nextInt(2);
		for (int i = 0; i < clusters; ++i) {
			int j = random.nextInt(2);
			int l = random.nextInt(2);
			float f = (float) (j + 1 + l) * 0.333F + 0.5F;
			Iterable<BlockPos> positions = BlockPos.iterate(blockPos.add(-j, 0, -l), blockPos.add(j, 0, l));
			for (BlockPos position : positions) {
				if (position.getSquaredDistance(blockPos) <= (double) (f * f) && structureWorldAccess.testBlockState(position, blockState -> blockState.isReplaceable() && AylythBlocks.AYLYTH_BUSH.getDefaultState().canPlaceAt(structureWorldAccess, position))) {
					structureWorldAccess.setBlockState(position, AylythBlocks.AYLYTH_BUSH.getDefaultState(), Block.NO_REDRAW);
					if (structureWorldAccess.isAir(position.up())) {
						structureWorldAccess.setBlockState(position.up(), AylythBlocks.AYLYTH_BUSH.getDefaultState().with(BushBlock.BUSHY, true), Block.NO_REDRAW);
					}
				}
			}
			blockPos = blockPos.add(-1 + random.nextInt(2), 0, -1 + random.nextInt(2));
		}
		return true;
	}
}
