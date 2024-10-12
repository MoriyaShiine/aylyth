package moriyashiine.aylyth.common;

import moriyashiine.aylyth.api.AylythEntityApi;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.advancement.AylythCriteria;
import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererDataTypes;
import moriyashiine.aylyth.common.block.AylythBlockEntityTypes;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.AylythFlammables;
import moriyashiine.aylyth.common.block.AylythStrippables;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.common.data.tag.AylythEntityTypeTags;
import moriyashiine.aylyth.common.entity.AylythAttributes;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.entity.AylythTrackedDataHandlers;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import moriyashiine.aylyth.common.entity.ai.AylythSensorTypes;
import moriyashiine.aylyth.common.event.LivingEntityDeathEvents;
import moriyashiine.aylyth.common.item.AylythBoatTypes;
import moriyashiine.aylyth.common.item.AylythCompostingChances;
import moriyashiine.aylyth.common.item.AylythFuels;
import moriyashiine.aylyth.common.item.AylythItemGroups;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.item.potion.AylythPotions;
import moriyashiine.aylyth.common.item.types.ShuckedYmpeFruitItem;
import moriyashiine.aylyth.common.loot.AylythLootConditionTypes;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import moriyashiine.aylyth.common.loot.AylythModifyLootTableHandler;
import moriyashiine.aylyth.common.loot.LootDisplayTypes;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import moriyashiine.aylyth.common.network.AylythPacketTypes;
import moriyashiine.aylyth.common.network.AylythServerPacketHandler;
import moriyashiine.aylyth.common.network.packets.SpawnParticlesAroundPacketS2C;
import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import moriyashiine.aylyth.common.recipe.AylythRecipeTypes;
import moriyashiine.aylyth.common.recipe.types.SoulCampfireRecipe;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
import moriyashiine.aylyth.common.screenhandler.AylythScreenHandlerTypes;
import moriyashiine.aylyth.common.world.AylythGameRules;
import moriyashiine.aylyth.common.world.AylythPointOfInterestTypes;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import moriyashiine.aylyth.common.world.AylythWorldAttachmentTypes;
import moriyashiine.aylyth.common.world.gen.AylythFeatures;
import moriyashiine.aylyth.common.world.gen.AylythFoliagePlacerTypes;
import moriyashiine.aylyth.common.world.gen.AylythPlacementModifiers;
import moriyashiine.aylyth.common.world.gen.AylythTreeDecoratorTypes;
import moriyashiine.aylyth.common.world.gen.AylythTrunkPlacerTypes;
import moriyashiine.aylyth.common.world.gen.biome.AylythBiomeModifications;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
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
	public static Identifier id(String string){
		return new Identifier(MOD_ID, string);
	}

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
		LootDisplayTypes.register();
		AylythLootContextTypes.register();
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

		AylythAttributes.register();

		AylythMemoryTypes.register();
		AylythSensorTypes.register();
		AylythTrackedDataHandlers.register();
		AylythEntityTypes.register();
		AylythStatusEffects.register();

		AylythBoatTypes.register();
		AylythItems.register();
		AylythFuels.register();
		AylythCompostingChances.register();
		AylythPotions.register();
		AylythItemGroups.register();

		AylythFeatures.register();
		AylythTrunkPlacerTypes.register();
		AylythFoliagePlacerTypes.register();
		AylythTreeDecoratorTypes.register();
		AylythPlacementModifiers.register();
		AylythBiomeModifications.register();
		AylythGameRules.register();

		AylythEntityAttachmentTypes.register();
		AylythWorldAttachmentTypes.register();
		AylythPointOfInterestTypes.register();

		DynamicRegistries.registerSynced(AylythRegistryKeys.LOOT_TABLE_DISPLAY, LootDisplay.CODEC, LootDisplay.NETWORK_CODEC);

		registerApis();

		LivingEntityDeathEvents.init();
		AylythModifyLootTableHandler.register();

		ServerPlayNetworking.registerGlobalReceiver(AylythPacketTypes.GLAIVE_SPECIAL_PACKET, AylythServerPacketHandler::handleGlaiveSpecial);
		ServerPlayNetworking.registerGlobalReceiver(AylythPacketTypes.UPDATE_RIDER_PACKET, AylythServerPacketHandler::handleUpdatePressingUpDown);

		// TODO move to the LivingEntityDeathEvents
		UseBlockCallback.EVENT.register(this::interactSoulCampfire);
		AttackEntityCallback.EVENT.register(this::attackWithYmpeDagger);
	}

	private ActionResult interactSoulCampfire(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
		if(hand == Hand.MAIN_HAND && world.getBlockState(blockHitResult.getBlockPos()).isOf(Blocks.SOUL_CAMPFIRE) && world.getBlockEntity(blockHitResult.getBlockPos()) instanceof CampfireBlockEntity campfireBlockEntity){
			ItemStack itemStack = playerEntity.getMainHandStack();
			// TODO: Cache this?
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
						mob.setAttached(AylythEntityAttachmentTypes.PREVENT_DROPS, Unit.INSTANCE);
						PlayerLookup.tracking(mob).forEach(trackingPlayer -> {
							ServerPlayNetworking.send(trackingPlayer, new SpawnParticlesAroundPacketS2C(mob.getId(), 32, List.of(ParticleTypes.SMOKE, ParticleTypes.FALLING_HONEY)));
						});
						world.playSound(null, mob.getBlockPos(), AylythSoundEvents.ENTITY_GENERIC_SHUCKED.value(), mob.getSoundCategory(), 1, mob.getSoundPitch());
						ShuckedYmpeFruitItem.setStoredEntity(offhand, mob);
						mob.remove(Entity.RemovalReason.DISCARDED);
						// deal a bit of damage to the player
						attacker.damage(world.aylythDamageSources().shucking(), 1);
					}
					return ActionResult.SUCCESS;
				}
			}
		}
		return ActionResult.PASS;
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