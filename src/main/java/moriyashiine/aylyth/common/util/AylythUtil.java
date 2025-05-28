package moriyashiine.aylyth.common.util;

import moriyashiine.aylyth.common.data.tag.AylythDamageTypeTags;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.world.AylythPointOfInterestTypes;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.poi.PointOfInterestStorage;

import java.util.function.BiFunction;

public class AylythUtil {
	public static void teleportToShucked(LivingEntity living, ServerWorld newWorld) {
		living.getWorld().playSoundFromEntity(living instanceof PlayerEntity player ? player : null, living, AylythSoundEvents.ENTITY_GENERIC_SHUCKED.value(), SoundCategory.PLAYERS, 1, living.getSoundPitch());
		teleportTo(newWorld, living, living.getBlockPos(), AylythUtil::findTeleportPosition, (serverWorld, blockPos, entity) -> {
			serverWorld.playSoundFromEntity(null, entity, AylythSoundEvents.ENTITY_GENERIC_SHUCKED.value(), SoundCategory.PLAYERS, 1, 1);
		});
	}

	public static void teleportTo(ServerWorld toWorld, Entity entity, BlockPos startPos, BiFunction<ServerWorld, BlockPos, BlockPos> positionFinder) {
		teleportTo(toWorld, entity, startPos, positionFinder, (serverWorld, blockPos, entity1) -> {});
	}

	public static <E extends Entity> void teleportTo(ServerWorld toWorld, E entity, BlockPos startPos, BiFunction<ServerWorld, BlockPos, BlockPos> positionFinder, TeleportCallback<E> onTeleport) {
		ChunkPos chunkPos = new ChunkPos(startPos);
		toWorld.getChunkManager().addTicket(ChunkTicketType.PORTAL, chunkPos, 3, startPos);
		E teleportedEntity = (E) entity.teleportTo(new TeleportTarget(toWorld, entity.getPos(), Vec3d.ZERO, entity.getYaw(), entity.getPitch(), TeleportTarget.NO_OP));
		if (teleportedEntity != null) {
			toWorld.getChunkManager().getChunkFutureSyncOnMainThread(chunkPos.x, chunkPos.z, ChunkStatus.EMPTY, true)
					.thenRun(() -> {
						BlockPos newPos = positionFinder.apply(toWorld, startPos);
						teleportedEntity.refreshPositionAfterTeleport(newPos.getX() + 0.5, newPos.getY() + 0.1, newPos.getZ() + 0.5);
						onTeleport.onTeleport(toWorld, newPos, teleportedEntity);
					});
		}
	}

	public interface TeleportCallback<E extends Entity> {
		void onTeleport(ServerWorld world, BlockPos pos, E entity);
	}

	/**
	 * Attempts to find a random location near the given x and z position at the world surface
	 */
	public static BlockPos findTeleportPosition(ServerWorld toWorld, BlockPos startPos) {
		ChunkPos spawnChunkPos = new ChunkPos(startPos);
		BlockPos spawnPoint = spawnChunkPos.getCenterAtY(startPos.getY());
		toWorld.getChunkManager().addTicket(ChunkTicketType.PORTAL, spawnChunkPos, 3, spawnPoint);
		for (BlockPos pos : BlockPos.iterateRandomly(toWorld.random, 5, spawnPoint, 7)) {
			pos = toWorld.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
			if (canTeleportToPos(toWorld, pos)) {
				return pos;
			}
		}

		return startPos;
	}

	public static boolean canTeleportToPos(ServerWorld toWorld, BlockPos pos) {
		return toWorld.testBlockState(pos, state -> state.getBlock().canMobSpawnInside(state)) && toWorld.testBlockState(pos.up(), state -> state.getBlock().canMobSpawnInside(state));
	}

	public static boolean shouldUndeadAttack(LivingEntity target, LivingEntity attacker) {
		return attacker.getAttacker() != target && target.hasStatusEffect(AylythStatusEffects.CIMMERIAN) && isUndead(attacker);
	}

	// TODO: add effigy check
	public static boolean isUndead(LivingEntity entity) {
		return entity.getType().isIn(EntityTypeTags.UNDEAD);
	}

	public static boolean isNearSeep(ServerWorld serverWorld, LivingEntity livingEntity, int radius) {
		return serverWorld.getPointOfInterestStorage().getNearestPosition(entry -> entry.matchesKey(AylythPointOfInterestTypes.SEEP), livingEntity.getBlockPos(), radius, PointOfInterestStorage.OccupationStatus.ANY).isPresent();
	}

	public static double distanceToSeep(ServerWorld serverWorld, LivingEntity livingEntity, int radius) {
		return serverWorld.getPointOfInterestStorage().getNearestPosition(entry -> entry.matchesKey(AylythPointOfInterestTypes.SEEP), livingEntity.getBlockPos(), radius, PointOfInterestStorage.OccupationStatus.ANY)
				.map(blockPos -> Math.sqrt(blockPos.getSquaredDistance(livingEntity.getBlockPos())))
				.orElse(-1d);
	}

	/**
	 * Check if the DamageSource is to be considered all forms of ympe
	 * @param source source of damage
	 * @return true if the source is some for of ympe
	 */
	public static boolean isSourceYmpe(DamageSource source) {
		if (source.getSource() instanceof LivingEntity livingEntity && livingEntity.getMainHandStack().isIn(AylythItemTags.YMPE_WEAPONS)) {
			return true;
		}
		return source.isIn(AylythDamageTypeTags.IS_YMPE);
	}
}
