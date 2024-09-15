package moriyashiine.aylyth.common.world.gen.features;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpringFeature extends Feature<SingleStateFeatureConfig> {
	public SpringFeature() {
		super(SingleStateFeatureConfig.CODEC);
	}
	
	@Override
	public boolean generate(FeatureContext<SingleStateFeatureConfig> context) {
		BlockPos origin = context.getOrigin();
		StructureWorldAccess world = context.getWorld();
		Random random = context.getRandom();
		int size = 10 + random.nextInt(8);
		int depth = 6;
		boolean[][] blocks = new boolean[size][size];
		defineShape(random, size, (size - 2) / 2, size / 2, blocks);
		origin = findPosition(origin, world, blocks);
		if (origin.getY() <= world.getBottomY() + 30) {
			return false;
		}
		//	fillShapeWithBlocks(origin, world, blocks, 0, context.getGenerator().getBlockSource().get(origin));
		fillShape(blocks);
		for (int y = 0; y < depth; y++) {
			blocks = shrink(random, y, blocks);
			fillShapeWithBlocks(origin, world, blocks, y, context.getConfig().state);
		}
		return true;
	}
	
	private void fillShapeWithBlocks(BlockPos origin, StructureWorldAccess world, boolean[][] blocks, int y, BlockState state) {
		for (int x = 0; x < blocks.length; x++) {
			for (int z = 0; z < blocks[x].length; z++) {
				if (blocks[x][z]) {
					world.setBlockState(origin.add(x, -y, z), state, Block.NOTIFY_LISTENERS);
				}
			}
		}
	}
	
	@NotNull
	private BlockPos findPosition(BlockPos origin, StructureWorldAccess world, boolean[][] blocks) {
		while (origin.getY() > world.getBottomY() + 30) {
			boolean invalid = false;
			for (int x = 0; x < blocks.length; x++) {
				for (int z = 0; z < blocks[x].length; z++) {
					if (blocks[x][z] && (world.isAir(origin.add(x, 0, z)) || !world.isAir(origin.add(x, 1, z)))) {
						invalid = true;
						break;
					}
				}
				if (invalid) {
					break;
				}
			}
			if (!invalid) {
				break;
			}
			origin = origin.down();
		}
		return origin;
	}
	
	private boolean[][] shrink(Random random, int y, boolean[][] blocks) {
		boolean[][] newBlocks = new boolean[blocks.length][blocks[blocks.length - 1].length];
		for (int x = 0; x < blocks.length; x++) {
			for (int z = 0; z < blocks[x].length; z++) {
				if (blocks[x][z]) {
					newBlocks[x][z] = getFilledNeighbors(x, z, blocks) >= (y > 2 && y < 6 ? (random.nextBoolean() ? 1 : 0) : 3);
				}
			}
		}
		return (y > 0 && y < 3) ? shrink(random, y + 1, newBlocks) : newBlocks;
	}
	
	private void defineShape(Random random, int nodes, int minRadius, int maxRadius, boolean[][] blocks) {
		double angleStep = Math.PI * 2 / nodes;
		List<Pair<Integer, Integer>> nodeList = new ArrayList<>();
		for (int i = 0; i < nodes; ++i) {
			double targetAngle = angleStep * i;
			double angle = targetAngle + (random.nextDouble() - 0.5) * angleStep * 0.25; // add a random factor to the angle, which is +- 25% of the angle step
			double radius = minRadius + random.nextDouble() * (maxRadius - minRadius); // make the radius random but within minRadius to maxRadius
			// calculate x and z positions of the part point
			int x = maxRadius + (int) (Math.cos(angle) * radius);
			int z = maxRadius + (int) (Math.sin(angle) * radius);
			nodeList.add(new Pair<>(x, z));
		}
		//connect the nodes
		for (int i = 0; i < nodeList.size(); i++) {
			Pair<Integer, Integer> node = nodeList.get(i);
			Pair<Integer, Integer> nextNode = nodeList.get((i + 1) % nodeList.size());
			int start = node.getLeft() > nextNode.getLeft() ? nextNode.getLeft() : node.getLeft();
			int end = start == node.getLeft() ? nextNode.getLeft() : node.getLeft();
			int startY = start == node.getLeft() ? node.getRight() : nextNode.getRight();
			int yDiff = (start == node.getLeft() ? nextNode.getRight() : node.getRight()) - startY;
			int diff = end - start;
			for (int x = start; x <= end; x++) {
				blocks[x][startY + (int) (((x - start) / (float) diff) * yDiff)] = true;
			}
		}
	}
	
	private void fillShape(boolean[][] blocks) {
		//fill the space
		for (boolean[] row : blocks) {
			int sourceCount = 0;
			for (boolean b : row) {
				if (b) {
					sourceCount++;
				}
			}
			if (sourceCount > 1) {
				boolean sourceFound = false;
				for (int i = 0; i < row.length; i++) {
					if (row[i]) {
						if (sourceFound) {
							break;
						}
						sourceFound = true;
					}
					else if (sourceFound) {
						row[i] = true;
					}
				}
			}
		}
	}
	
	private int getFilledNeighbors(int x, int z, boolean[][] blocks) {
		boolean[] directions = {z + 1 < blocks[x].length && blocks[z][z + 1], z - 1 > 0 && blocks[x][z - 1], x + 1 < blocks.length && blocks[x + 1][z], x - 1 > 0 && blocks[x - 1][z]};
		int count = 0;
		for (boolean direction : directions) {
			if (direction) {
				count++;
			}
		}
		return count;
	}
}
