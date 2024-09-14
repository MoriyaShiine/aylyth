package moriyashiine.aylyth.common;

import moriyashiine.aylyth.api.AylythEntityApi;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererDataTypes;
import moriyashiine.aylyth.common.advancement.AylythCriteria;
import moriyashiine.aylyth.common.block.entity.AylythBlockEntityTypes;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.AylythFlammables;
import moriyashiine.aylyth.common.block.AylythStrippables;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.common.entity.*;
import moriyashiine.aylyth.common.entity.attribute.AylythAttributes;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import moriyashiine.aylyth.common.entity.ai.AylythSensorTypes;
import moriyashiine.aylyth.common.item.AylythBoatTypes;
import moriyashiine.aylyth.common.item.AylythFuels;
import moriyashiine.aylyth.common.item.AylythItemGroup;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.item.potion.AylythPotions;
import moriyashiine.aylyth.common.loot.AylythLootConditionTypes;
import moriyashiine.aylyth.common.recipe.AylythRecipeTypes;
import moriyashiine.aylyth.common.screenhandler.AylythScreenHandlerTypes;
import moriyashiine.aylyth.common.world.WorldAttachmentTypes;
import moriyashiine.aylyth.common.world.effects.AylythParticleTypes;
import moriyashiine.aylyth.common.world.effects.AylythSoundEvents;
import moriyashiine.aylyth.common.world.gen.biome.AylythBiomeModifications;
import moriyashiine.aylyth.common.world.AylythPointOfInterestTypes;
import moriyashiine.aylyth.common.network.AylythPacketTypes;
import moriyashiine.aylyth.common.network.AylythServerPacketHandler;
import moriyashiine.aylyth.common.network.packets.SpawnParticlesAroundPacketS2C;
import moriyashiine.aylyth.common.entity.types.mob.ScionEntity;
import moriyashiine.aylyth.common.event.LivingEntityDeathEvents;
import moriyashiine.aylyth.common.item.types.ShuckedYmpeFruitItem;
import moriyashiine.aylyth.common.recipe.types.SoulCampfireRecipe;
import moriyashiine.aylyth.common.recipe.types.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import moriyashiine.aylyth.common.data.tag.AylythEntityTypeTags;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.world.gen.biome.AylythBiomeSources;
import moriyashiine.aylyth.common.world.gen.AylythFeatures;
import moriyashiine.aylyth.common.world.gen.AylythFoliagePlacerTypes;
import moriyashiine.aylyth.common.world.gen.AylythTreeDecoratorTypes;
import moriyashiine.aylyth.common.world.gen.AylythTrunkPlacerTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class Aylyth implements ModInitializer {
	public static final String MOD_ID = "aylyth";
	public static final Logger LOGGER = LoggerFactory.getLogger("Aylyth");

	public static final boolean DEBUG = System.getProperty("aylyth.debug") != null;
	public static boolean isDebugMode() {
		return DEBUG;
	}

	@Override
	public void onInitialize() {
		if (DEBUG) {
			LOGGER.info("Debug mode enabled!");
		}

		AylythRegistries.register();

		AdvancementRendererDataTypes.register();
		AylythCriteria.register();
		AylythLootConditionTypes.register();
		AylythRecipeTypes.register();

		AylythSoundEvents.register();
		AylythParticleTypes.register();

		AylythScreenHandlerTypes.register();

		AylythBlocks.register();
		AylythFlammables.register();
		AylythStrippables.register();
		AylythBlockEntityTypes.register();
		AylythPointOfInterestTypes.register();

		AylythAttributes.register();

		AylythMemoryTypes.register();
		AylythSensorTypes.register();
		AylythTrackedDataHandlers.register();
		AylythEntityTypes.register();
		AylythStatusEffects.register();

		AylythItems.register();
		AylythFuels.register();
		AylythPotions.register();
		AylythItemGroup.register();

		AylythBoatTypes.register();

		AylythFeatures.register();
		AylythTrunkPlacerTypes.register();
		AylythFoliagePlacerTypes.register();
		AylythTreeDecoratorTypes.register();
		AylythBiomeSources.register();
		AylythBiomeModifications.register();

		WorldAttachmentTypes.register();

		registerApis();

		LivingEntityDeathEvents.init();

		ServerPlayNetworking.registerGlobalReceiver(AylythPacketTypes.GLAIVE_SPECIAL_PACKET, AylythServerPacketHandler::handleGlaiveSpecial);
		ServerPlayNetworking.registerGlobalReceiver(AylythPacketTypes.UPDATE_RIDER_PACKET, AylythServerPacketHandler::handleUpdatePressingUpDown);

		// TODO move to the LivingEntityDeathEvents
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(this::daggerDrops);
		UseBlockCallback.EVENT.register(this::interactSoulCampfire);
		AttackEntityCallback.EVENT.register(this::attackWithYmpeDagger);
	}

	private ActionResult interactSoulCampfire(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
		if(hand == Hand.MAIN_HAND && world.getBlockState(blockHitResult.getBlockPos()).isOf(Blocks.SOUL_CAMPFIRE) && world.getBlockEntity(blockHitResult.getBlockPos()) instanceof CampfireBlockEntity campfireBlockEntity){
			ItemStack itemStack = playerEntity.getMainHandStack();
			List<Ingredient> allowedIngredients = world.getRecipeManager().listAllOfType(AylythRecipeTypes.SOULFIRE_TYPE).stream()
					.map(SoulCampfireRecipe::getIngredients)
					.flatMap(Collection::stream)
					.toList();
			if(allowedIngredients.stream().anyMatch(ingredient -> ingredient.test(itemStack))){
				if (!world.isClient && campfireBlockEntity.addItem(playerEntity, itemStack, Integer.MAX_VALUE)) {
					playerEntity.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
					return ActionResult.SUCCESS;
				}
			}
		}
		return ActionResult.PASS;
	}

	private ActionResult attackWithYmpeDagger(PlayerEntity attacker, World world, Hand hand, Entity target, @Nullable EntityHitResult hitResult) {
		if (attacker.getStackInHand(hand).isOf(AylythItems.YMPE_DAGGER) && target instanceof MobEntity mob) {
			ItemStack offhand = attacker.getOffHandStack();
			if (offhand.isOf(AylythItems.SHUCKED_YMPE_FRUIT)) {
				if (!ShuckedYmpeFruitItem.hasStoredEntity(offhand) && !mob.getType().isIn(AylythEntityTypeTags.NON_SHUCKABLE)) {
					if (attacker instanceof ServerPlayerEntity serverPlayer) {
						AylythCriteria.SHUCKING.trigger(serverPlayer, mob);
						mob.setHealth(mob.getMaxHealth()); // TODO: check whether this is intended behavior
						mob.clearStatusEffects();
						mob.extinguish();
						mob.setFrozenTicks(0);
						mob.setVelocity(Vec3d.ZERO);
						mob.fallDistance = 0;
						AylythEntityComponents.PREVENT_DROPS.get(mob).setPreventsDrops(true);
						PlayerLookup.tracking(mob).forEach(trackingPlayer -> {
							ServerPlayNetworking.send(trackingPlayer, new SpawnParticlesAroundPacketS2C(mob.getId(), 32, List.of(ParticleTypes.SMOKE, ParticleTypes.FALLING_HONEY)));
						});
						world.playSound(null, mob.getBlockPos(), AylythSoundEvents.ENTITY_GENERIC_SHUCKED.value(), mob.getSoundCategory(), 1, mob.getSoundPitch());
						ShuckedYmpeFruitItem.setStoredEntity(offhand, mob);
						mob.remove(Entity.RemovalReason.DISCARDED);
					}
					return ActionResult.SUCCESS;
				}
			}
		}
		return ActionResult.PASS;
	}

	private void daggerDrops(ServerWorld serverWorld, Entity entity, LivingEntity killedEntity) {
		if (entity instanceof LivingEntity living && living.getMainHandStack().isIn(AylythItemTags.HEART_HARVESTERS)) {
			for (YmpeDaggerDropRecipe recipe : serverWorld.getRecipeManager().listAllOfType(AylythRecipeTypes.YMPE_DAGGER_DROP_TYPE)) {
				if (recipe.entity_type == killedEntity.getType() && serverWorld.random.nextFloat() < recipe.chance * (EnchantmentHelper.getLooting(living) + 1)) {
					ItemStack drop = recipe.getOutput(serverWorld.getRegistryManager()).copy();
					if (recipe.entity_type == EntityType.PLAYER) {
						drop.getOrCreateNbt().putString("SkullOwner", killedEntity.getName().getString());
					}
					if (recipe.entity_type == AylythEntityTypes.SCION && entity instanceof ScionEntity scionEntity && scionEntity.getStoredPlayerUUID() != null) {
						return;
					}
					int random = 1;
					if (recipe.min <= recipe.max && recipe.min + recipe.max > 0) {
						random = serverWorld.getRandom().nextBetween(recipe.min, recipe.max);
					}
					for (int i = 0; i < random; i++) {
						ItemScatterer.spawn(serverWorld, killedEntity.getX() + 0.5, killedEntity.getY() + 0.5, killedEntity.getZ() + 0.5, drop);
					}
				}
			}
		}
	}

	private void registerApis() {
		AylythEntityApi.VITAL_HOLDER.registerForType((entity, unused) -> (VitalHealthHolder) entity, EntityType.PLAYER);

		ItemStorage.SIDED.registerForBlocks((world, pos, state, blockEntity, context) -> {
			if (state.get(SoulHearthBlock.HALF) == DoubleBlockHalf.LOWER) {
				return SoulHearthBlock.SoulHearthStorage.getOrCreate(world, pos);
			}
			return null;
		}, AylythBlocks.SOUL_HEARTH);
	}
}