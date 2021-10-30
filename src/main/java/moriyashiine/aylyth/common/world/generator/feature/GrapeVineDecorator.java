package moriyashiine.aylyth.common.world.generator.feature;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModWorldGenerators;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class GrapeVineDecorator extends TreeDecorator {
	public static final GrapeVineDecorator INSTANCE = new GrapeVineDecorator();
	public static final Codec<GrapeVineDecorator> CODEC = Codec.unit(() -> INSTANCE);
	
	@Override
	protected TreeDecoratorType<?> getType() {
		return ModWorldGenerators.GRAPE_VINE;
	}
	
	@Override
	public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
		if (random.nextInt(10) == 0) {
			BlockPos rootPos = logPositions.get(0);
			List<Pair<BlockPos, BlockState>> vines = new ArrayList<>();
			for (int i = logPositions.size() / 2; i < logPositions.size(); i++) {
				BlockPos pos = logPositions.get(i);
				double xDistance = pos.getSquaredDistance(rootPos.getX() - 0.5F, pos.getY(), rootPos.getZ() - 0.5F, false);
				if (xDistance > 1) {
					Direction direction = Direction.values()[2 + random.nextInt(4)];
					pos = pos.offset(direction);
					if (Feature.isAir(world, pos)) {
						vines.add(new Pair<>(pos, placeGrapeVine(replacer, pos, VineBlock.getFacingProperty(direction.getOpposite()))));
						if (vines.size() > 5) {
							break;
						}
					}
				}
			}
			for (Pair<BlockPos, BlockState> vine : vines) {
				int length = 1 + random.nextInt(3);
				BlockPos pos = vine.getLeft();
				BlockState vineState = vine.getRight();
				for (int i = 0; i < length; i++) {
					pos = pos.down();
					if (Feature.isAir(world, pos)) {
						replacer.accept(pos, vineState);
					}
					else {
						break;
					}
				}
			}
		}
	}
	
	protected static BlockState placeGrapeVine(BiConsumer<BlockPos, BlockState> replacer, BlockPos pos, BooleanProperty facing) {
		BlockState state = ModBlocks.NYSIAN_GRAPE_VINE.getDefaultState().with(facing, true);
		replacer.accept(pos, state);
		return state;
	}
}
