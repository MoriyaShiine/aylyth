package moriyashiine.aylyth.common.util;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.network.packets.SpawnParticlesAroundPacketS2C;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModParticles;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import moriyashiine.aylyth.common.registry.ModStatusEffects;
import moriyashiine.aylyth.common.data.AylythPointsOfInterestTypes;
import moriyashiine.aylyth.common.data.tag.AylythDamageTypeTags;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AylythUtil {
	public static final int MAX_TRIES = 8;

	public static Identifier id(String string){
		return new Identifier(Aylyth.MOD_ID, string);
	}

	public static void decreaseStack(ItemStack stack, @Nullable LivingEntity living) {
		if (living instanceof PlayerEntity player && player.getAbilities().creativeMode) {
			return;
		}
		stack.decrement(1);
	}

	public static BlockPos getSafePosition(World world, BlockPos.Mutable pos, int tries) {
		if (tries >= MAX_TRIES) {
			return world.getSpawnPos();
		}
		pos.setY(world.getTopY() - 1);
		while (world.isInBuildLimit(pos) && !world.getBlockState(pos).shouldSuffocate(world, pos)) {
			pos.setY(pos.getY() - 1);
		}
		while (world.isInBuildLimit(pos) && world.getBlockState(pos).shouldSuffocate(world, pos)) {
			pos.setY(pos.getY() + 2);
		}
		if (world.getBlockState(pos).isReplaceable() && world.getFluidState(pos).getFluid() == Fluids.EMPTY) {
			return pos.toImmutable();
		}
		return getSafePosition(world, pos.set(MathHelper.nextInt(world.random, pos.getX() - 32, pos.getX() + 32) + 0.5, world.getTopY() - 1, MathHelper.nextInt(world.random, pos.getZ() - 32, pos.getZ() + 32) + 0.5), ++tries);
	}

	public static void teleportTo(LivingEntity living, ServerWorld newWorld, int tries) {
		living.getWorld().playSoundFromEntity(living instanceof PlayerEntity player ? player : null, living, ModSoundEvents.ENTITY_GENERIC_SHUCKED.value(), SoundCategory.PLAYERS, 1, living.getSoundPitch());
		FabricDimensions.teleport(living, newWorld, new TeleportTarget(Vec3d.of(AylythUtil.getSafePosition(newWorld, living.getBlockPos().mutableCopy(), tries)), Vec3d.ZERO, living.headYaw, living.getPitch()));
		newWorld.playSoundFromEntity(null, living, ModSoundEvents.ENTITY_GENERIC_SHUCKED.value(), SoundCategory.PLAYERS, 1, living.getSoundPitch());
	}

	public static void teleportTo(RegistryKey<World> world, LivingEntity living, int tries) {
		living.getWorld().playSoundFromEntity(living instanceof PlayerEntity player ? player : null, living, ModSoundEvents.ENTITY_GENERIC_SHUCKED.value(), SoundCategory.PLAYERS, 1, living.getSoundPitch());
		ServerWorld toWorld = living.getWorld().getServer().getWorld(world);
		FabricDimensions.teleport(living, toWorld, new TeleportTarget(Vec3d.of(AylythUtil.getSafePosition(toWorld, living.getBlockPos().mutableCopy(), tries)), Vec3d.ZERO, living.headYaw, living.getPitch()));
		toWorld.playSoundFromEntity(null, living, ModSoundEvents.ENTITY_GENERIC_SHUCKED.value(), SoundCategory.PLAYERS, 1, living.getSoundPitch());
	}

	public static boolean shouldUndeadAttack(LivingEntity target, LivingEntity attacker) {
		return attacker.getAttacker() != target && target.hasStatusEffect(ModStatusEffects.CIMMERIAN) && attacker.getGroup() == EntityGroup.UNDEAD;
	}

	public static boolean isNearSeep(ServerWorld serverWorld, LivingEntity livingEntity, int radius) {
		return serverWorld.getPointOfInterestStorage().getNearestPosition(entry -> entry.matchesKey(AylythPointsOfInterestTypes.SEEP), livingEntity.getBlockPos(), radius, PointOfInterestStorage.OccupationStatus.ANY).isPresent();
	}

	public static double distanceToSeep(ServerWorld serverWorld, LivingEntity livingEntity, int radius) {
		return serverWorld.getPointOfInterestStorage().getNearestPosition(entry -> entry.matchesKey(AylythPointsOfInterestTypes.SEEP), livingEntity.getBlockPos(), radius, PointOfInterestStorage.OccupationStatus.ANY)
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

	public static float getVampiricWeaponEffect(LivingEntity attacker, LivingEntity target, ItemStack stack, float originalValue) {
		boolean isSword = stack.isOf(ModItems.VAMPIRIC_SWORD);
		boolean isPickaxe = stack.isOf(ModItems.VAMPIRIC_PICKAXE);
		boolean isHoe = stack.isOf(ModItems.VAMPIRIC_HOE);

        if (attacker.getRandom().nextFloat() >= 0.8) {
            attacker.heal(originalValue * 0.5f);

			PlayerLookup.tracking(attacker).forEach(trackingPlayer -> {
				ServerPlayNetworking.send(trackingPlayer, new SpawnParticlesAroundPacketS2C(attacker.getId(), 32, List.of(ModParticles.VAMPIRIC_DRIP)));
			});

			if (attacker instanceof ServerPlayerEntity player) {
				ServerPlayNetworking.send(player, new SpawnParticlesAroundPacketS2C(player.getId(), 32, List.of(ModParticles.VAMPIRIC_DRIP)));
			}

            if (isSword && target.getAbsorptionAmount() > 0) {
				target.setAbsorptionAmount(target.getAbsorptionAmount() <= 1 ? target.getAbsorptionAmount() / 2f : 0);
            }

            if (isHoe) {
				target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.CRIMSON_CURSE, 20 * 10, 0));
            }

            return originalValue * (isPickaxe && target.getArmor() > 10f ? 1.2f : 1f);
        }

		return originalValue;
    }

	public static float getBlightedWeaponEffect(LivingEntity attacker, LivingEntity target, ItemStack stack, float originalValue) {
		boolean isSword = stack.isOf(ModItems.BLIGHTED_SWORD);
		boolean isPickaxe = stack.isOf(ModItems.BLIGHTED_PICKAXE);
		boolean isHoe = stack.isOf(ModItems.BLIGHTED_HOE);

		if (attacker.getRandom().nextFloat() >= 0.75) {
			int amplifier = attacker.getRandom().nextFloat() <= 0.85 && target.hasStatusEffect(ModStatusEffects.BLIGHT) ? 1 : 0;
			target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.BLIGHT, 20 * 4, amplifier));

			PlayerLookup.tracking(target).forEach(trackingPlayer -> {
				ServerPlayNetworking.send(trackingPlayer, new SpawnParticlesAroundPacketS2C(target.getId(), 32, List.of(ModParticles.BLIGHT_DRIP)));
			});

			if (isSword && target.getAbsorptionAmount() > 0) {
				target.setAbsorptionAmount(target.getAbsorptionAmount() <= 1 ? target.getAbsorptionAmount() / 2f : 0);
			}

			if (isHoe) {
				target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 2, 0));
			}

			return originalValue * (isPickaxe && target.getArmor() > 10f ? 1.2f : 1f);
		}

		return originalValue;
	}
}
