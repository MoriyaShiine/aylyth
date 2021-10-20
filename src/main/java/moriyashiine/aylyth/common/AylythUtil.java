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
		pos.setY(world.getTopY() - 1);
		while (world.isInBuildLimit(pos) && !world.getBlockState(pos).shouldSuffocate(world, pos)) {
			pos.setY(pos.getY() - 1);
		}
		while (world.isInBuildLimit(pos) && world.getBlockState(pos).shouldSuffocate(world, pos)) {
			pos.setY(pos.getY() + 1);
		}
		if (!world.getBlockState(pos).shouldSuffocate(world, pos)) {
			return pos.toImmutable();
		}
		return getSafePosition(world, pos.set(MathHelper.nextInt(world.random, pos.getX() - 32, pos.getX() + 32) + 0.5, world.getTopY() - 1, MathHelper.nextInt(world.random, pos.getZ() - 32, pos.getZ() + 32) + 0.5), ++tries);
	}
}
