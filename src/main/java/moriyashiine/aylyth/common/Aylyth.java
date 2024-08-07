package moriyashiine.aylyth.common;

import moriyashiine.aylyth.api.AylythEntityApi;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.block.SoulHearthBlock;
import moriyashiine.aylyth.common.network.AylythPacketTypes;
import moriyashiine.aylyth.common.network.AylythServerPacketHandler;
import moriyashiine.aylyth.common.network.packets.SpawnParticlesAroundPacketS2C;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import moriyashiine.aylyth.common.event.LivingEntityDeathEvents;
import moriyashiine.aylyth.common.item.ShuckedYmpeFruitItem;
import moriyashiine.aylyth.common.recipe.SoulCampfireRecipe;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.registry.custom.CustomRegistries;
import moriyashiine.aylyth.common.registry.key.ModPlacedFeatureKeys;
import moriyashiine.aylyth.common.registry.tag.ModBiomeTags;
import moriyashiine.aylyth.common.registry.tag.ModEntityTypeTags;
import moriyashiine.aylyth.common.registry.tag.ModItemTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.GenerationStep;
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

		ModEntityAttributes.init();
		ModParticles.init();
		ModBlocks.init();
		ModItems.init();
		ModBlockEntityTypes.init();
		ModEntityTypes.init();
		ModStatusEffects.init();
		ModPotions.init();
		ModSoundEvents.init();
		ModRecipeTypes.init();
		ModFeatures.init();
		ModTrunkPlacerTypes.init();
		ModTreeDecoratorTypes.init();
		ModFoliagePlacerTypes.init();
		ModBoatTypes.init();
		ModMemoryTypes.init();
		ModSensorTypes.init();
		ModBiomeSources.init();
		ModPoiTypes.init();
		ModCriteria.init();
		ModLootConditions.init();
		ModScreenHandlers.init();
		ModDataTrackers.init();
		CustomRegistries.init();
		ModAdvancementRendererData.init();
		ModAttachmentTypes.init();

		biomeModifications();
		registerApis();

		LivingEntityDeathEvents.init();

		ServerPlayNetworking.registerGlobalReceiver(AylythPacketTypes.GLAIVE_SPECIAL_PACKET, AylythServerPacketHandler::handleGlaiveSpecial);
		ServerPlayNetworking.registerGlobalReceiver(AylythPacketTypes.UPDATE_RIDER_PACKET, AylythServerPacketHandler::handleUpdatePressingUpDown);

		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(this::daggerDrops);
		UseBlockCallback.EVENT.register(this::interactSoulCampfire);
		AttackEntityCallback.EVENT.register(this::attackWithYmpeDagger);

	}

	private ActionResult interactSoulCampfire(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
		if(hand == Hand.MAIN_HAND && world.getBlockState(blockHitResult.getBlockPos()).isOf(Blocks.SOUL_CAMPFIRE) && world.getBlockEntity(blockHitResult.getBlockPos()) instanceof CampfireBlockEntity campfireBlockEntity){
			ItemStack itemStack = playerEntity.getMainHandStack();
			List<Ingredient> allowedIngredients = world.getRecipeManager().listAllOfType(ModRecipeTypes.SOULFIRE_RECIPE_TYPE).stream()
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
		if (attacker.getStackInHand(hand).isOf(ModItems.YMPE_DAGGER) && target instanceof MobEntity mob) {
			ItemStack offhand = attacker.getOffHandStack();
			if (offhand.isOf(ModItems.SHUCKED_YMPE_FRUIT)) {
				if (!ShuckedYmpeFruitItem.hasStoredEntity(offhand) && !mob.getType().isIn(ModEntityTypeTags.NON_SHUCKABLE)) {
					if (attacker instanceof ServerPlayerEntity serverPlayer) {
						ModCriteria.SHUCKING.trigger(serverPlayer, mob);
						mob.setHealth(mob.getMaxHealth()); // TODO: check whether this is intended behavior
						mob.clearStatusEffects();
						mob.extinguish();
						mob.setFrozenTicks(0);
						mob.setVelocity(Vec3d.ZERO);
						mob.fallDistance = 0;
						ModEntityComponents.PREVENT_DROPS.get(mob).setPreventsDrops(true);
						PlayerLookup.tracking(mob).forEach(trackingPlayer -> {
							ServerPlayNetworking.send(trackingPlayer, new SpawnParticlesAroundPacketS2C(mob.getId(), 32, List.of(ParticleTypes.SMOKE, ParticleTypes.FALLING_HONEY)));
						});
						world.playSound(null, mob.getBlockPos(), ModSoundEvents.ENTITY_GENERIC_SHUCKED.value(), mob.getSoundCategory(), 1, mob.getSoundPitch());
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
		if (entity instanceof LivingEntity living && living.getMainHandStack().isIn(ModItemTags.HEART_HARVESTERS)) {
			for (YmpeDaggerDropRecipe recipe : serverWorld.getRecipeManager().listAllOfType(ModRecipeTypes.YMPE_DAGGER_DROP_RECIPE_TYPE)) {
				if (recipe.entity_type == killedEntity.getType() && serverWorld.random.nextFloat() < recipe.chance * (EnchantmentHelper.getLooting(living) + 1)) {
					ItemStack drop = recipe.getOutput(serverWorld.getRegistryManager()).copy();
					if (recipe.entity_type == EntityType.PLAYER) {
						drop.getOrCreateNbt().putString("SkullOwner", killedEntity.getName().getString());
					}
					if (recipe.entity_type == ModEntityTypes.SCION && entity instanceof ScionEntity scionEntity && scionEntity.getStoredPlayerUUID() != null) {
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

	private void biomeModifications() {
		// TODO: These need to be changed. It replaces structure logs too, obv.
		BiomeModifications.create(new Identifier(Aylyth.MOD_ID, "world_features"))
				.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModBiomeTags.GENERATES_SEEP), context -> {
					context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatureKeys.OAK_SEEP);
					context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatureKeys.SPRUCE_SEEP);
					context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatureKeys.DARK_OAK_SEEP);
				});
	}

	private void registerApis() {
		AylythEntityApi.VITAL_HOLDER.registerForType((entity, unused) -> (VitalHealthHolder) entity, EntityType.PLAYER);

		ItemStorage.SIDED.registerForBlocks((world, pos, state, blockEntity, context) -> {
			if (state.get(SoulHearthBlock.HALF) == DoubleBlockHalf.LOWER) {
				return SoulHearthBlock.SoulHearthStorage.getOrCreate(world, pos);
			}
			return null;
		}, ModBlocks.SOUL_HEARTH);
	}
}