package moriyashiine.aylyth.common.world.generator;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.registry.ModWorldGenerators;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class BigYmpeTrunkPlacer extends GiantTrunkPlacer {
	public static final Codec<BigYmpeTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).apply(instance, BigYmpeTrunkPlacer::new));
	
	public BigYmpeTrunkPlacer(int heightMin, int heightVar, int heightVar2) {
		super(heightMin, heightVar, heightVar2);
	}
	
	public BigYmpeTrunkPlacer() {
		this(6, 3, 4);
	}
	
	@Override
	protected TrunkPlacerType<BigYmpeTrunkPlacer> getType() {
		return ModWorldGenerators.BIG_YMPE_TRUNK_PLACER;
	}
	
	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
		list.addAll(super.generate(world, replacer, random, height, startPos, config));
		placeBranches(world, replacer, random, startPos, config, list, height, 0, 0, Direction.NORTH, Direction.WEST);
		placeBranches(world, replacer, random, startPos, config, list, height, 1, 0, Direction.NORTH, Direction.EAST);
		placeBranches(world, replacer, random, startPos, config, list, height, 1, 1, Direction.SOUTH, Direction.EAST);
		placeBranches(world, replacer, random, startPos, config, list, height, 0, 1, Direction.SOUTH, Direction.WEST);
		
		return list;
	}
	
	private void placeBranches(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config, List<FoliagePlacer.TreeNode> list, int height, int xOffset, int zOffset, Direction... availableOffsets) {
		Direction lastDirection = Direction.DOWN;
		for (int y = 0; y < height - 1; y++) {
			float ratio = y / (float) height;
			int branches = ratio == 0 ? 3 : random.nextBoolean() ? 1 : 0;
			for (int i = 0; i < branches; i++) {
				Direction direction = availableOffsets[random.nextInt(availableOffsets.length)];
				if (direction != lastDirection) {
					BlockPos blockPos = startPos.add(xOffset, y, zOffset).offset(direction);
					getAndSetState(world, replacer, random, blockPos, config, (state) -> {
						if (state.contains(Properties.AXIS)) {
							return state.with(Properties.AXIS, direction.getAxis());
						}
						return state;
					});
					if (ratio > 0.2) {
						list.add(new FoliagePlacer.TreeNode(blockPos.up().offset(direction), ratio < 0.4F ? -2 : -1, false));
					}
					lastDirection = direction;
				}
			}
		}
	}
}
