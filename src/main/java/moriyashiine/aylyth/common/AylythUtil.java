package moriyashiine.aylyth.common;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AylythUtil {
	public static BlockPos getSafePosition(World world, BlockPos.Mutable pos, int tries) {
		if (tries >= 8) {
			return ((ServerWorld) world).getSpawnPos();
		}
		//		while (pos.getY() >= world.getBottomY()) {
		//			BlockState state = world.getBlockState(pos);
		//			if (state.shouldSuffocate(world, pos) && !BlockTags.LOGS.contains(state.getBlock())) {
		//				return pos.up();
		//			}
		//			pos.setY(pos.getY() - 1);
		//		}
		return getSafePosition(world, pos.set(MathHelper.nextInt(world.random, pos.getX() - 32, pos.getX() + 32) + 0.5, world.getTopY(), MathHelper.nextInt(world.random, pos.getZ() - 32, pos.getZ() + 32) + 0.5), ++tries);
	}
}
