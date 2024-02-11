package moriyashiine.aylyth.common;

import moriyashiine.aylyth.common.network.packet.SpawnShuckParticlesPacket;
import moriyashiine.aylyth.common.network.packet.UpdatePressingUpDownPacket;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import moriyashiine.aylyth.common.event.LivingEntityDeathEvents;
import moriyashiine.aylyth.common.item.ShuckedYmpeFruitItem;
import moriyashiine.aylyth.common.network.packet.GlaivePacket;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.registry.tag.ModBiomeTags;
import moriyashiine.aylyth.common.registry.tag.ModEntityTypeTags;
import moriyashiine.aylyth.datagen.worldgen.features.ModPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
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

public class Aylyth implements ModInitializer {
	public static final String MOD_ID = "aylyth";

	private static boolean debugMode = true;
	public static boolean isDebugMode() {
		return debugMode && FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	@Override
	public void onInitialize() {
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
		ModPOITypes.init();
		ModCriteria.init();
		ModLootConditions.init();
		ModScreenHandlers.init();
		ModDataTrackers.init();
		biomeModifications();

		LivingEntityDeathEvents.init();

		ServerPlayNetworking.registerGlobalReceiver(GlaivePacket.ID, GlaivePacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(UpdatePressingUpDownPacket.ID, UpdatePressingUpDownPacket::handle);

		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(this::daggerDrops);
		UseBlockCallback.EVENT.register(this::interactSoulCampfire);
		AttackEntityCallback.EVENT.register(this::attackWithYmpeDagger);
	}

	private ActionResult interactSoulCampfire(PlayerEntity playerEntity, World world, Hand hand, BlockHitResult blockHitResult) {
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

	private ActionResult attackWithYmpeDagger(PlayerEntity attacker, World world, Hand hand, Entity target, @Nullable EntityHitResult hitResult) {
		if (target instanceof MobEntity mob) {
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
						ModComponents.PREVENT_DROPS.get(mob).setPreventsDrops(true);
						PlayerLookup.tracking(mob).forEach(trackingPlayer -> SpawnShuckParticlesPacket.send(trackingPlayer, mob));
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
		if (entity instanceof LivingEntity living && living.getMainHandStack().isOf(ModItems.YMPE_DAGGER)) {
			for (YmpeDaggerDropRecipe recipe : serverWorld.getRecipeManager().listAllOfType(ModRecipeTypes.YMPE_DAGGER_DROP_RECIPE_TYPE)) {
				if (recipe.entity_type.equals(killedEntity.getType()) && serverWorld.random.nextFloat() < recipe.chance * (EnchantmentHelper.getLooting(living) + 1)) {
					ItemStack drop = recipe.getOutput(DynamicRegistryManager.EMPTY).copy();
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
		BiomeModifications.create(new Identifier(Aylyth.MOD_ID, "world_features"))
				.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModBiomeTags.GENERATES_SEEP), context -> {
					context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OAK_SEEP);
					context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SPRUCE_SEEP);
					context.getGenerationSettings().addFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DARK_OAK_SEEP);
				});
	}
}