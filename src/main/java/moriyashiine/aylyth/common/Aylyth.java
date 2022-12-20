package moriyashiine.aylyth.common;

import moriyashiine.aylyth.client.network.packet.UpdatePressingUpDownPacket;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import moriyashiine.aylyth.common.event.LivingEntityDeathEvents;
import moriyashiine.aylyth.common.network.packet.GlaivePacket;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.datagen.worldgen.features.ModPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
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
		ModBiomeSources.init();
		biomeModifications();

		LivingEntityDeathEvents.init();

		ServerPlayNetworking.registerGlobalReceiver(GlaivePacket.ID, GlaivePacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(UpdatePressingUpDownPacket.ID, UpdatePressingUpDownPacket::handle);

		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(this::shucking);
		UseBlockCallback.EVENT.register(this::interactSoulCampfire);
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

	private void shucking(ServerWorld serverWorld, Entity entity, LivingEntity killedEntity) {
		if (entity instanceof LivingEntity living && living.getMainHandStack().isOf(ModItems.YMPE_DAGGER)) {
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
		BiomeModification worldGen = BiomeModifications.create(new Identifier(Aylyth.MOD_ID, "world_features"));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OAK_SEEP.value()));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SPRUCE_SEEP.value()));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DARK_OAK_SEEP.value()));
	}
}
