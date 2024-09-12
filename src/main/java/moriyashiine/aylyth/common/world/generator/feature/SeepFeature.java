package moriyashiine.aylyth.common.world.generator.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.block.type.SeepBlock;
import moriyashiine.aylyth.common.block.AylythBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
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

						for (BlockPos pos : BlockPos.iterateRandomly(context.getRandom(), context.getConfig().placeAroundTries, origin, 3)) {
							pos = pos.withY(world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()));
							if (context.getRandom().nextFloat() <= context.getConfig().placeAroundChance && TreeFeature.canReplace(world, pos) && AylythBlocks.MARIGOLD.getDefaultState().canPlaceAt(world, pos)) {
								setBlockState(world, pos, context.getConfig().placeAround);
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static class SeepFeatureConfig implements FeatureConfig {
		public static final Codec<SeepFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
				BlockState.CODEC.fieldOf("state").forGetter((treeFeatureConfig) -> treeFeatureConfig.state),
				BlockState.CODEC.fieldOf("seep_state").forGetter((treeFeatureConfig) -> treeFeatureConfig.seepState),
				BlockState.CODEC.fieldOf("placed_around_state").forGetter(seepFeatureConfig -> seepFeatureConfig.placeAround),
				Codec.INT.fieldOf("placed_around_tries").forGetter(seepFeatureConfig -> seepFeatureConfig.placeAroundTries),
				Codec.FLOAT.fieldOf("placed_around_chance").forGetter(seepFeatureConfig -> seepFeatureConfig.placeAroundChance)
		).apply(instance, (SeepFeatureConfig::new)));
		public final BlockState state;
		public final BlockState seepState;
		public final BlockState placeAround;
		public final int placeAroundTries;
		public final float placeAroundChance;
		
		public SeepFeatureConfig(BlockState state, BlockState seepState, BlockState placeAround, int placeAroundTries, float placeAroundChance) {
			this.state = state;
			this.seepState = seepState;
			this.placeAround = placeAround;
			this.placeAroundTries = placeAroundTries;
			this.placeAroundChance = placeAroundChance;
		}
	}
}
