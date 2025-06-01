package moriyashiine.aylyth.common;

import moriyashiine.aylyth.api.AylythEntityApi;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.advancement.AylythCriteria;
import moriyashiine.aylyth.common.block.AylythBlockEntityTypes;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.AylythFlammables;
import moriyashiine.aylyth.common.block.AylythFlattenables;
import moriyashiine.aylyth.common.block.AylythStrippables;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.common.entity.AylythAttributes;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.entity.AylythTrackedDataHandlers;
import moriyashiine.aylyth.common.entity.ai.AylythMemoryTypes;
import moriyashiine.aylyth.common.entity.ai.AylythSensorTypes;
import moriyashiine.aylyth.common.event.FireRitualCraftingEvents;
import moriyashiine.aylyth.common.event.GlaiveSoulRipEvents;
import moriyashiine.aylyth.common.event.HindPledgeEvents;
import moriyashiine.aylyth.common.event.RespawnIntoAylythEvents;
import moriyashiine.aylyth.common.event.ShuckingEvents;
import moriyashiine.aylyth.common.event.VitalHealthEvents;
import moriyashiine.aylyth.common.item.AttackEffectTypes;
import moriyashiine.aylyth.common.item.AylythCompostingChances;
import moriyashiine.aylyth.common.item.AylythConsumeEffectTypes;
import moriyashiine.aylyth.common.item.AylythDataComponentTypes;
import moriyashiine.aylyth.common.item.AylythFuels;
import moriyashiine.aylyth.common.item.AylythItemGroups;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.item.potion.AylythPotionRecipes;
import moriyashiine.aylyth.common.item.potion.AylythPotions;
import moriyashiine.aylyth.common.loot.AylythLootConditionTypes;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import moriyashiine.aylyth.common.loot.AylythModifyLootTableHandler;
import moriyashiine.aylyth.common.loot.AylythEntitySubPredicates;
import moriyashiine.aylyth.common.loot.LootDisplayTypes;
import moriyashiine.aylyth.common.loot.LootTableModifiers;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import moriyashiine.aylyth.common.loot.modifier.LootTableModifier;
import moriyashiine.aylyth.common.network.AylythServerPacketHandler;
import moriyashiine.aylyth.common.network.packets.GlaivePacketC2S;
import moriyashiine.aylyth.common.network.packets.UpdatePressingUpDownPacketC2S;
import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import moriyashiine.aylyth.common.recipe.AylythIngredients;
import moriyashiine.aylyth.common.recipe.AylythRecipeTypes;
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
import moriyashiine.aylyth.common.world.gen.AylythStructureProcessors;
import moriyashiine.aylyth.common.world.gen.AylythTreeDecoratorTypes;
import moriyashiine.aylyth.common.world.gen.AylythTrunkPlacerTypes;
import moriyashiine.aylyth.common.world.gen.biome.AylythBiomeModifications;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Aylyth implements ModInitializer {
	public static final String MOD_ID = "aylyth";
	public static Identifier id(String string) {
		return Identifier.of(MOD_ID, string);
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

		AttackEffectTypes.register();
		AylythConsumeEffectTypes.register();
		AylythDataComponentTypes.register();

		LootDisplayTypes.register();
		LootTableModifiers.register();
		AylythLootContextTypes.register();
		AylythCriteria.register();
		AylythLootConditionTypes.register();
		AylythEntitySubPredicates.register();

		AylythSoundEvents.register();
		AylythParticleTypes.register();

		AylythScreenHandlerTypes.register();

		AylythBlocks.register();
		AylythFlammables.register();
		AylythFlattenables.register();
		AylythStrippables.register();
		AylythBlockEntityTypes.register();

		AylythAttributes.register();

		AylythMemoryTypes.register();
		AylythSensorTypes.register();
		AylythTrackedDataHandlers.register();
		AylythEntityTypes.register();
		AylythStatusEffects.register();

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
		AylythStructureProcessors.register();
		AylythBiomeModifications.register();
		AylythGameRules.register();

		AylythEntityAttachmentTypes.register();
		AylythWorldAttachmentTypes.register();
		AylythPointOfInterestTypes.register();

		AylythIngredients.register();
		AylythRecipeTypes.register();
		AylythPotionRecipes.register();

		DispenserBlock.registerProjectileBehavior(AylythItems.THORN_FLECHETTE);
		DispenserBlock.registerProjectileBehavior(AylythItems.BLIGHTED_THORN_FLECHETTE);

		DynamicRegistries.registerSynced(AylythRegistryKeys.LOOT_TABLE_DISPLAY, LootDisplay.CODEC, LootDisplay.NETWORK_CODEC);
		DynamicRegistries.register(AylythRegistryKeys.LOOT_TABLE_MODIFIER, LootTableModifier.CODEC);

		registerApis();

		RespawnIntoAylythEvents.init();
		FireRitualCraftingEvents.init();
		ShuckingEvents.init();
		GlaiveSoulRipEvents.init();
		HindPledgeEvents.init();
		VitalHealthEvents.init();
		AylythModifyLootTableHandler.register();

		PayloadTypeRegistry.playC2S().register(GlaivePacketC2S.ID, GlaivePacketC2S.PACKET_CODEC);
		PayloadTypeRegistry.playC2S().register(UpdatePressingUpDownPacketC2S.ID, UpdatePressingUpDownPacketC2S.PACKET_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(GlaivePacketC2S.ID, AylythServerPacketHandler::handleGlaiveSpecial);
		ServerPlayNetworking.registerGlobalReceiver(UpdatePressingUpDownPacketC2S.ID, AylythServerPacketHandler::handleUpdatePressingUpDown);
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