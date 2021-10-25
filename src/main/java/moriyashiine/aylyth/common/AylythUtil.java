package moriyashiine.aylyth.common;

import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class AylythUtil {
	public static final int MAX_TRIES = 8;
	
	public static BlockPos getSafePosition(World world, BlockPos.Mutable pos, int tries) {
		if (tries >= MAX_TRIES) {
			return ((ServerWorld) world).getSpawnPos();
		}
		pos.setY(world.getTopY() - 1);
		while (world.isInBuildLimit(pos) && !world.getBlockState(pos).shouldSuffocate(world, pos)) {
			pos.setY(pos.getY() - 1);
		}
		while (world.isInBuildLimit(pos) && world.getBlockState(pos).shouldSuffocate(world, pos)) {
			pos.setY(pos.getY() + 1);
		}
		if (world.getBlockState(pos).getMaterial().isReplaceable() && world.getFluidState(pos).getFluid() == Fluids.EMPTY) {
			return pos.toImmutable();
		}
		return getSafePosition(world, pos.set(MathHelper.nextInt(world.random, pos.getX() - 32, pos.getX() + 32) + 0.5, world.getTopY() - 1, MathHelper.nextInt(world.random, pos.getZ() - 32, pos.getZ() + 32) + 0.5), ++tries);
	}
	
	public static void teleportTo(RegistryKey<World> world, LivingEntity living, int tries) {
		living.world.playSoundFromEntity(living instanceof PlayerEntity player ? player : null, living, ModSoundEvents.ENTITY_GENERIC_SHUCKED, SoundCategory.PLAYERS, 1, living.getSoundPitch());
		ServerWorld toWorld = living.world.getServer().getWorld(world);
		FabricDimensions.teleport(living, toWorld, new TeleportTarget(Vec3d.of(AylythUtil.getSafePosition(toWorld, living.getBlockPos().mutableCopy(), tries)), Vec3d.ZERO, living.headYaw, living.getPitch()));
		toWorld.playSoundFromEntity(null, living, ModSoundEvents.ENTITY_GENERIC_SHUCKED, SoundCategory.PLAYERS, 1, living.getSoundPitch());
	}
}
