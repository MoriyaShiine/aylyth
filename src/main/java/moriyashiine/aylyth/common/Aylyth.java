package moriyashiine.aylyth.common;

import moriyashiine.aylyth.api.interfaces.Vital;
import moriyashiine.aylyth.client.network.packet.UpdatePressingUpDownPacket;
import moriyashiine.aylyth.common.block.WoodyGrowthCacheBlock;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import moriyashiine.aylyth.common.network.packet.GlaivePacket;
import moriyashiine.aylyth.client.network.packet.SpawnShuckParticlesPacket;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.CampfireBlockEntity;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;

public class Aylyth implements ModInitializer {
	public static final String MOD_ID = "aylyth";

	static final boolean DEBUG_MODE = true;
	public static boolean isDebugMode() {
		return DEBUG_MODE && FabricLoader.getInstance().isDevelopmentEnvironment();
	}
	
	@Override
	public void onInitialize() {
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
		ModMemoryTypes.init();
		ModSensorTypes.init();
		biomeModifications();
		ServerPlayNetworking.registerGlobalReceiver(GlaivePacket.ID, GlaivePacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(UpdatePressingUpDownPacket.ID, UpdatePressingUpDownPacket::handle);
		ServerPlayerEvents.AFTER_RESPAWN.register(this::afterRespawn);
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(this::shucking);
		ServerLivingEntityEvents.ALLOW_DEATH.register(this::allowDeath);
		UseBlockCallback.EVENT.register(this::interactSoulCampfore);


	}


	private ActionResult interactSoulCampfore(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
		if(hand == Hand.MAIN_HAND && world.getBlockState(blockHitResult.getBlockPos()).isOf(Blocks.SOUL_CAMPFIRE) && world.getBlockEntity(blockHitResult.getBlockPos()) instanceof CampfireBlockEntity campfireBlockEntity){
			ItemStack itemStack = playerEntity.getMainHandStack();
			if(itemStack.isOf(ModItems.AYLYTHIAN_HEART) || itemStack.isOf(ModItems.WRONGMEAT) || (itemStack.isOf(ModItems.SHUCKED_YMPE_FRUIT) && (itemStack.hasNbt() && itemStack.getNbt().contains("StoredEntity")))){
				if (!world.isClient && campfireBlockEntity.addItem(playerEntity, itemStack,  Integer.MAX_VALUE)) {
					playerEntity.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
					return ActionResult.SUCCESS;
				}
			}
		}
		return ActionResult.PASS;
	}

	private boolean allowDeath(LivingEntity livingEntity, DamageSource damageSource, float damageAmount) {
		if(livingEntity instanceof ServerPlayerEntity player){
			if (damageSource.isOutOfWorld() && damageSource != ModDamageSources.YMPE) {
				return true;
			}
			if (damageSource == ModDamageSources.YMPE) {
				WoodyGrowthCacheBlock.spawnInventory(player.world, player.getBlockPos(), player);
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
					teleport |= AylythUtil.isNearSeep(player, 8);
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
		}
		return true;
	}

	private void shucking(ServerWorld serverWorld, Entity entity, LivingEntity killedEntity) {
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
						serverWorld.playSound(null, killedEntity.getBlockPos(), ModSoundEvents.ENTITY_GENERIC_SHUCKED, killedEntity.getSoundCategory(), 1, killedEntity.getSoundPitch());
						NbtCompound entityCompound = new NbtCompound();
						killedEntity.saveSelfNbt(entityCompound);
						offhand.getOrCreateNbt().put("StoredEntity", entityCompound);
						killedEntity.remove(Entity.RemovalReason.DISCARDED);
						shucked = true;
					}
				}
			}
			if (!shucked) {
				for (YmpeDaggerDropRecipe recipe : serverWorld.getRecipeManager().listAllOfType(ModRecipeTypes.YMPE_DAGGER_DROP_RECIPE_TYPE)) {
					if (recipe.entity_type.equals(killedEntity.getType()) && serverWorld.random.nextFloat() < recipe.chance * (EnchantmentHelper.getLooting(living) + 1)) {
						ItemStack drop = recipe.getOutput().copy();
						if (recipe.entity_type == EntityType.PLAYER) {
							drop.getOrCreateNbt().putString("SkullOwner", killedEntity.getName().getString());
						}
						if (recipe.entity_type == ModEntityTypes.SCION && entity instanceof ScionEntity scionEntity && scionEntity.getStoredPlayerUUID() != null) {
							return;
						}
						int random = 1;
						if(recipe.min <= recipe.max && recipe.min + recipe.max > 0){
							random = serverWorld.getRandom().nextBetween(recipe.min, recipe.max);
						}
						for(int i = 0; i < random; i++){
							ItemScatterer.spawn(serverWorld, killedEntity.getX() + 0.5, killedEntity.getY() + 0.5, killedEntity.getZ() + 0.5, drop);
						}
					}
				}
			}
		}
	}

	private void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
		if (oldPlayer.world.getRegistryKey().equals(ModDimensions.AYLYTH)) {
			AylythUtil.teleportTo(ModDimensions.AYLYTH, newPlayer, 0);
		}
		Vital.of(newPlayer).ifPresent(vital -> vital.setVitalThuribleLevel(((Vital) oldPlayer).getVitalThuribleLevel()));
	}

	private void biomeModifications() {
		BiomeModification worldGen = BiomeModifications.create(new Identifier(Aylyth.MOD_ID, "world_features"));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OAK_SEEP.value()));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SPRUCE_SEEP.value()));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DARK_OAK_SEEP.value()));
	}
}
