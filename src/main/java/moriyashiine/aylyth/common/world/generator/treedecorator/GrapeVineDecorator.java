package moriyashiine.aylyth.common.world.generator.treedecorator;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class GrapeVineDecorator extends TreeDecorator {
	public static final Codec<GrapeVineDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			IntProvider.VALUE_CODEC.fieldOf("chance_range").forGetter(grapeVineDecorator -> grapeVineDecorator.chances),
			Codec.INT.fieldOf("chance_point").forGetter(grapeVineDecorator -> grapeVineDecorator.chancePoint)
	).apply(instance, GrapeVineDecorator::new));

	private final IntProvider chances;
	private final int chancePoint;

	public GrapeVineDecorator(IntProvider chances, int chancePoint) {
		this.chances = chances;
		this.chancePoint = chancePoint;
	}
	
	@Override
	protected TreeDecoratorType<?> getType() {
		return ModFeatures.GRAPE_VINE;
	}
	
	@Override
	public void generate(Generator generator) {
		if (chances.get(generator.getRandom()) <= chancePoint) {
			BlockPos rootPos = generator.getLogPositions().get(0);
			List<Pair<BlockPos, BlockState>> vines = new ArrayList<>();
			for (int i = generator.getLogPositions().size() / 2; i < generator.getLogPositions().size(); i++) {
				BlockPos pos = generator.getLogPositions().get(i);
				double xDistance = pos.getSquaredDistance(rootPos.getX() - 0.5F, pos.getY(), rootPos.getZ() - 0.5F);
				if (xDistance > 1) {
					Direction direction = Direction.values()[2 + generator.getRandom().nextInt(4)];
					pos = pos.offset(direction);
					if (generator.isAir(pos)) {
						vines.add(Pair.of(pos, placeGrapeVine(generator::replace, pos, VineBlock.getFacingProperty(direction.getOpposite()))));
						if (vines.size() > 5) {
							break;
						}
					}
				}
			}

			for (Pair<BlockPos, BlockState> vine : vines) {
				int length = 1 + generator.getRandom().nextInt(3);
				BlockPos pos = vine.getFirst();
				BlockState vineState = vine.getSecond();
				for (int i = 0; i < length; i++) {
					pos = pos.down();
					if (generator.isAir(pos)) {
						generator.replace(pos, vineState);
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
