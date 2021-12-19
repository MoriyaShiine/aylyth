package moriyashiine.aylyth.common.world.generator.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.SeepBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class SeepFeature extends Feature<SeepFeature.SeepFeatureConfig> {
	public SeepFeature() {
		super(SeepFeatureConfig.CODEC);
	}
	
	@Override
	public boolean generate(FeatureContext<SeepFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos origin;
		for (int xOffset = -2; xOffset < 2; xOffset++) {
			for (int zOffset = -2; zOffset < 2; zOffset++) {
				origin = context.getOrigin();
				int topY = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, origin.getX(), origin.getZ()) + 5;
				for (origin = origin.add(xOffset, -2, zOffset); origin.getY() <= topY; origin = origin.up()) {
					if (world.getBlockState(origin) == context.getConfig().state && Feature.isSoil(world.getBlockState(origin.down()))) {
						boolean doubleUp = (world.getBlockState(origin.up()) == context.getConfig().state && context.getRandom().nextBoolean());
						world.setBlockState(origin, context.getConfig().seepState.with(SeepBlock.CONNECTION, doubleUp ? SeepBlock.Connection.UP : SeepBlock.Connection.NONE), Block.NOTIFY_LISTENERS);
						if (doubleUp) {
							world.setBlockState(origin.up(), context.getConfig().seepState.with(SeepBlock.CONNECTION, SeepBlock.Connection.DOWN), Block.NOTIFY_LISTENERS);
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static class SeepFeatureConfig implements FeatureConfig {
		public static final Codec<SeepFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockState.CODEC.fieldOf("state").forGetter((treeFeatureConfig) -> treeFeatureConfig.state), BlockState.CODEC.fieldOf("seep_state").forGetter((treeFeatureConfig) -> treeFeatureConfig.seepState)).apply(instance, (SeepFeatureConfig::new)));
		public final BlockState state;
		public final BlockState seepState;
		
		public SeepFeatureConfig(BlockState state, BlockState seepState) {
			this.state = state;
			this.seepState = seepState;
		}
	}
}
