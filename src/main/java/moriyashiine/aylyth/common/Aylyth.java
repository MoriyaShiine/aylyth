package moriyashiine.aylyth.common;

import moriyashiine.aylyth.client.network.packet.SpawnShuckParticlesPacket;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import software.bernie.geckolib3.GeckoLib;

public class Aylyth implements ModInitializer {
	public static final String MOD_ID = "aylyth";

	static final boolean DEBUG_MODE = true;
	public static boolean isDebugMode() {
		return DEBUG_MODE && FabricLoader.getInstance().isDevelopmentEnvironment();
	}
	
	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		ModParticles.init();
		ModBlocks.init();
		ModItems.init();
		ModBlockEntityTypes.init();
		ModEntityTypes.init();
		ModPotions.init();
		ModSoundEvents.init();
		ModRecipeTypes.init();
		ModFeatures.init();
		ModBoatTypes.init();
		biomeModifications();
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
			if (entity instanceof LivingEntity living && living.getMainHandStack().isOf(ModItems.YMPE_DAGGER)) {
				boolean shucked = false;
				if (killedEntity instanceof MobEntity mob) {
					ItemStack offhand = living.getOffHandStack();
					if (offhand.isOf(ModItems.SHUCKED_YMPE_FRUIT)) {
						if (!offhand.hasNbt() || !offhand.getNbt().contains("StoredEntity")) {
							killedEntity.setHealth(killedEntity.getMaxHealth());
							killedEntity.clearStatusEffects();
							killedEntity.extinguish();
							killedEntity.setFrozenTicks(0);
							killedEntity.setVelocity(Vec3d.ZERO);
							killedEntity.fallDistance = 0;
							killedEntity.knockbackVelocity = 0;
							ModComponents.PREVENT_DROPS.get(mob).setPreventsDrops(true);
							PlayerLookup.tracking(killedEntity).forEach(trackingPlayer -> SpawnShuckParticlesPacket.send(trackingPlayer, killedEntity));
							world.playSound(null, killedEntity.getBlockPos(), ModSoundEvents.ENTITY_GENERIC_SHUCKED, killedEntity.getSoundCategory(), 1, killedEntity.getSoundPitch());
							NbtCompound entityCompound = new NbtCompound();
							killedEntity.saveSelfNbt(entityCompound);
							offhand.getOrCreateNbt().put("StoredEntity", entityCompound);
							killedEntity.remove(Entity.RemovalReason.DISCARDED);
							shucked = true;
						}
					}
				}
				if (!shucked) {
					for (YmpeDaggerDropRecipe recipe : world.getRecipeManager().listAllOfType(ModRecipeTypes.YMPE_DAGGER_DROP_RECIPE_TYPE)) {
						if (recipe.entity_type.equals(killedEntity.getType()) && world.random.nextFloat() < recipe.chance * (EnchantmentHelper.getLooting(living) + 1)) {
							ItemStack drop = recipe.getOutput().copy();
							if (recipe.entity_type == EntityType.PLAYER) {
								drop.getOrCreateNbt().putString("SkullOwner", killedEntity.getName().getString());
							}
							ItemScatterer.spawn(world, killedEntity.getX() + 0.5, killedEntity.getY() + 0.5, killedEntity.getZ() + 0.5, drop);
						}
					}
				}
			}
		});
		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			if (oldPlayer.world.getRegistryKey().equals(ModDimensions.AYLYTH)) {
				AylythUtil.teleportTo(ModDimensions.AYLYTH, newPlayer, 0);
			}
		});
		ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
			if (damageSource.isOutOfWorld() && damageSource != ModDamageSources.YMPE) {
				return true;
			}
			RegistryKey<World> toWorld = null;
			if (player.world.getRegistryKey() != ModDimensions.AYLYTH) {
				boolean teleport = false;
				float chance = switch (player.world.getDifficulty()) {
					case PEACEFUL -> 0;
					case EASY -> 0.1f;
					case NORMAL -> 0.2f;
					case HARD -> 0.3f;
				};
				if (player.getRandom().nextFloat() <= chance) {
					if (damageSource.getAttacker() instanceof WitchEntity) {
						teleport = true;
					}
					if (!teleport) {
						RegistryEntry<Biome> biome = player.world.getBiome(player.getBlockPos());
						if (biome.isIn(BiomeTags.IS_TAIGA) || biome.isIn(BiomeTags.IS_FOREST)) {
							if (damageSource.isFromFalling() || damageSource == DamageSource.DROWN) {
								teleport = true;
							}
						}
					}
					teleport |= isNearSeep(player);
				}
				if (!teleport) {
					for (Hand hand : Hand.values()) {
						ItemStack stack = player.getStackInHand(hand);
						if (stack.isOf(ModItems.AYLYTHIAN_HEART)) {
							teleport = true;
							if (!player.isCreative()) {
								stack.decrement(1);
							}
							break;
						}
					}
				}
				if (teleport) {
					toWorld = ModDimensions.AYLYTH;
				}
			}
			if (toWorld != null) {
				AylythUtil.teleportTo(toWorld, player, 0);
				player.setHealth(player.getMaxHealth() / 2);
				player.clearStatusEffects();
				player.extinguish();
				player.setFrozenTicks(0);
				player.setVelocity(Vec3d.ZERO);
				player.fallDistance = 0;
				player.knockbackVelocity = 0;
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200));
				return false;
			}
			return true;
		});

	}

	private void biomeModifications() {
		BiomeModification worldGen = BiomeModifications.create(new Identifier(Aylyth.MOD_ID, "world_features"));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OAK_SEEP.value()));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SPRUCE_SEEP.value()));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DARK_OAK_SEEP.value()));
	}
	
	private static boolean isNearSeep(PlayerEntity player) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		int radius = 8;
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					if (player.world.getBlockState(mutable.set(player.getX() + x, player.getY() + y, player.getZ() + z)).isIn(ModTags.SEEPS)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
