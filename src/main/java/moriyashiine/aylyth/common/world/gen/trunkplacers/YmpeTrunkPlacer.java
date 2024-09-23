package moriyashiine.aylyth.common.world.gen.trunkplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.world.gen.AylythTrunkPlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class YmpeTrunkPlacer extends StraightTrunkPlacer {
	public static final Codec<YmpeTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).apply(instance, YmpeTrunkPlacer::new));
	
	public YmpeTrunkPlacer(int heightMin, int heightVar, int heightVar2) {
		super(heightMin, heightVar, heightVar2);
	}
	
	@Override
	protected TrunkPlacerType<YmpeTrunkPlacer> getType() {
		return AylythTrunkPlacerTypes.YMPE;
	}
	
	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		List<FoliagePlacer.TreeNode> list = new ObjectArrayList<>();
		list.addAll(super.generate(world, replacer, random, height, startPos, config));
		Direction lastDirection = Direction.DOWN;
		for (int y = 0; y < height - 1; y++) {
			float ratio = y / (float) height;
			int branches = ratio == 0 ? 4 : ratio < 0.4F && random.nextBoolean() ? 2 : 1;
			for (int i = 0; i < branches; i++) {
				Direction direction = Direction.values()[2 + random.nextInt(4)];
				if (direction != lastDirection) {
					BlockPos blockPos = startPos.add(0, y, 0).offset(direction);
					getAndSetState(world, replacer, random, blockPos, config, (state) -> {
						if (state.contains(Properties.AXIS)) {
							return state.with(Properties.AXIS, direction.getAxis());
						}
						return state;
					});
					lastDirection = direction;
					if (ratio > 0.2) {
						list.add(new FoliagePlacer.TreeNode(blockPos.up().offset(direction), ratio < 0.4F ? -2 : -1, false));
					}
				}
			}
		}
		
		return list;
	}
}
