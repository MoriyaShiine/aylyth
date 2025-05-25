package moriyashiine.aylyth.client;

import moriyashiine.aylyth.client.model.block.SoulHearthBlockModel;
import moriyashiine.aylyth.client.model.entity.RootPropEntityModel;
import moriyashiine.aylyth.client.model.entity.ScionEntityModel;
import moriyashiine.aylyth.client.model.entity.layer.CuirassModel;
import moriyashiine.aylyth.client.model.entity.layer.YmpeInfestationModel;
import moriyashiine.aylyth.client.model.entity.layer.YmpeThornRingModel;
import moriyashiine.aylyth.client.network.AylythClientNetworkHandler;
import moriyashiine.aylyth.client.particle.ParticleFactories;
import moriyashiine.aylyth.client.particle.types.HindSmokeParticle;
import moriyashiine.aylyth.client.particle.types.PilotLightParticle;
import moriyashiine.aylyth.client.particle.types.SoulEmberParticle;
import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import moriyashiine.aylyth.client.render.block.entity.SeepBlockEntityRenderer;
import moriyashiine.aylyth.client.render.block.entity.VitalThuribleBlockEntityRenderer;
import moriyashiine.aylyth.client.render.block.entity.WoodyGrowthBlockEntityRenderer;
import moriyashiine.aylyth.client.render.entity.RootPropEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.AylythianEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.BoneflyEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.ElderAylythianEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.FaunaylythianEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.PilotLightEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.RippedSoulEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.ScionEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.SoulmouldEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.TulpaEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.TulpaPlayerEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.WreathedHindEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.feature.CuirassFeatureRenderer;
import moriyashiine.aylyth.client.render.entity.living.feature.YmpeInfestationFeature;
import moriyashiine.aylyth.client.render.entity.living.feature.YmpeThornRingFeature;
import moriyashiine.aylyth.client.render.entity.projectile.SphereEntityRenderer;
import moriyashiine.aylyth.client.render.entity.projectile.ThornFlechetteRenderer;
import moriyashiine.aylyth.client.render.entity.projectile.YmpeLanceEntityRenderer;
import moriyashiine.aylyth.client.render.AylythSkyRenderer;
import moriyashiine.aylyth.client.render.item.property.FlaskChargesProperty;
import moriyashiine.aylyth.client.screen.TulpaScreen;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlockEntityTypes;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.StrewnLeavesBlock;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.network.packets.SpawnParticlesAroundPacketS2C;
import moriyashiine.aylyth.common.network.packets.UpdatePressingUpDownPacketC2S;
import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import moriyashiine.aylyth.common.screenhandler.AylythScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.property.numeric.NumericProperties;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;

public class AylythClient implements ClientModInitializer {
	public static final KeyBinding DESCEND = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.aylyth.descend", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_G, "category.aylyth.keybind"));
	public static EntityModelLayer modelLayer(String id, String name) {
		return new EntityModelLayer(Aylyth.id(id), name);
	}
	public static ModelIdentifier modelId(String id, String variant) {
		return new ModelIdentifier(Aylyth.id(id), variant);
	}

	public static final EntityModelLayer YMPE_BOAT_LAYER = modelLayer("boat/ympe", "main");
	public static final EntityModelLayer YMPE_CHEST_BOAT_LAYER = modelLayer("chest_boat/ympe", "main");
	public static final EntityModelLayer POMEGRANATE_BOAT_LAYER = modelLayer("boat/pomegranate", "main");
	public static final EntityModelLayer POMEGRANATE_CHEST_BOAT_LAYER = modelLayer("chest_boat/pomegranate", "main");
	public static final EntityModelLayer WRITHEWOOD_BOAT_LAYER = modelLayer("boat/writhewood", "main");
	public static final EntityModelLayer WRITHEWOOD_CHEST_BOAT_LAYER = modelLayer("chest_boat/writhewood", "main");

	@Override
	public void onInitializeClient() {
		DimensionRenderingRegistry.registerDimensionEffects(AylythDimensionData.WORLD.getValue(), AylythDimensionRenderer.DIMENSION_EFFECTS);
		DimensionRenderingRegistry.registerSkyRenderer(AylythDimensionData.WORLD, AylythSkyRenderer.INSTANCE);
		DimensionRenderingRegistry.registerCloudRenderer(AylythDimensionData.WORLD, context -> {});

		PayloadTypeRegistry.playS2C().register(SpawnParticlesAroundPacketS2C.ID, SpawnParticlesAroundPacketS2C.PACKET_CODEC);
		ClientPlayNetworking.registerGlobalReceiver(SpawnParticlesAroundPacketS2C.ID, AylythClientNetworkHandler::handleSpawnParticlesAround);

		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.PILOT_LIGHT, PilotLightParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.AMBIENT_PILOT_LIGHT, PilotLightParticle.AmbientFactory::new);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.HIND_SMOKE, HindSmokeParticle.ShortSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.VAMPIRIC_DRIP, ParticleFactories::createVampiricDrip);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.VAMPIRIC_LAND, ParticleFactories::createVampiricLand);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.BLIGHT_DRIP, ParticleFactories::createBlightDrip);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.BLIGHT_LAND, ParticleFactories::createBlightLand);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.SOUL_EMBER, SoulEmberParticle.Factory::new);

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), cutoutBlocks());

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.DEFAULT, AylythBlocks.AYLYTH_BUSH);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null && state != null && state.getBlock() instanceof StrewnLeavesBlock && state.get(StrewnLeavesBlock.LEAVES) > 0 ? BiomeColors.getFoliageColor(world, pos) : 0xFFFFFFFF, AylythBlocks.OAK_STREWN_LEAVES);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor(), AylythBlocks.ANTLER_SHOOTS, AylythBlocks.GRIPWEED);

		NumericProperties.ID_MAPPER.put(Aylyth.id("flask_charges"), FlaskChargesProperty.CODEC);

		// TODO: Figure out how to best give blight potions a custom model
//		ModelPredicateProviderRegistry.register(Items.POTION, Aylyth.id("blight_potion"), (stack, world, entity, seed) -> Registries.POTION.getEntry(PotionUtil.getPotion(stack)).isIn(AylythPotionTags.BLIGHT) ? 1 : 0);
//		ModelPredicateProviderRegistry.register(Items.SPLASH_POTION, Aylyth.id("blight_potion"), (stack, world, entity, seed) -> Registries.POTION.getEntry(PotionUtil.getPotion(stack)).isIn(AylythPotionTags.BLIGHT) ? 1 : 0);
//		ModelPredicateProviderRegistry.register(Items.LINGERING_POTION, Aylyth.id("blight_potion"), (stack, world, entity, seed) -> Registries.POTION.getEntry(PotionUtil.getPotion(stack)).isIn(AylythPotionTags.BLIGHT) ? 1 : 0);

		BlockEntityRendererFactories.register(AylythBlockEntityTypes.SEEP, SeepBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(AylythBlockEntityTypes.VITAL_THURIBLE, VitalThuribleBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(AylythBlockEntityTypes.WOODY_GROWTH_CACHE, WoodyGrowthBlockEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(YmpeInfestationModel.YMPE_INFESTATION_STAGE_1_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData1);
		EntityModelLayerRegistry.registerModelLayer(YmpeInfestationModel.YMPE_INFESTATION_STAGE_2_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData2);
		EntityModelLayerRegistry.registerModelLayer(YmpeInfestationModel.YMPE_INFESTATION_STAGE_3_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData3);
		EntityModelLayerRegistry.registerModelLayer(YmpeInfestationModel.YMPE_INFESTATION_STAGE_4_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData4);
		EntityModelLayerRegistry.registerModelLayer(YmpeInfestationModel.YMPE_INFESTATION_STAGE_5_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData5);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_1, CuirassModel::createBodyLayer1);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_2, CuirassModel::createBodyLayer2);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_3, CuirassModel::createBodyLayer3);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_4, CuirassModel::createBodyLayer4);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_5, CuirassModel::createBodyLayer5);
		EntityModelLayerRegistry.registerModelLayer(YmpeThornRingModel.YMPE_THORN_RING_MODEL_LAYER, YmpeThornRingModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ScionEntityModel.LAYER_LOCATION, ScionEntityModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(RootPropEntityModel.LAYER_LOCATION, RootPropEntityModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(YMPE_BOAT_LAYER, BoatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(YMPE_CHEST_BOAT_LAYER, BoatEntityModel::getChestTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(POMEGRANATE_BOAT_LAYER, BoatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(POMEGRANATE_CHEST_BOAT_LAYER, BoatEntityModel::getChestTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(WRITHEWOOD_BOAT_LAYER, BoatEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(WRITHEWOOD_CHEST_BOAT_LAYER, BoatEntityModel::getChestTexturedModelData);

		EntityRendererRegistry.register(AylythEntityTypes.PILOT_LIGHT, PilotLightEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.AYLYTHIAN, AylythianEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.ELDER_AYLYTHIAN, ElderAylythianEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.YMPE_LANCE, YmpeLanceEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.YMPEMOULD, SoulmouldEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.BONEFLY, BoneflyEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.ROOT_PROP, RootPropEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.RIPPED_SOUL, RippedSoulEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.TULPA, TulpaEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.TULPA_PLAYER, TulpaPlayerEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.WREATHED_HIND_ENTITY, WreathedHindEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.SPHERE_ENTITY, SphereEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.FAUNAYLYTHIAN, FaunaylythianEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.SCION, ScionEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.THORN_FLECHETTE, ThornFlechetteRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.YMPE_BOAT, ctx -> new BoatEntityRenderer(ctx, YMPE_BOAT_LAYER));
		EntityRendererRegistry.register(AylythEntityTypes.YMPE_CHEST_BOAT, ctx -> new BoatEntityRenderer(ctx, YMPE_CHEST_BOAT_LAYER));
		EntityRendererRegistry.register(AylythEntityTypes.POMEGRANATE_BOAT, ctx -> new BoatEntityRenderer(ctx, POMEGRANATE_BOAT_LAYER));
		EntityRendererRegistry.register(AylythEntityTypes.POMEGRANATE_CHEST_BOAT, ctx -> new BoatEntityRenderer(ctx, POMEGRANATE_CHEST_BOAT_LAYER));
		EntityRendererRegistry.register(AylythEntityTypes.WRITHEWOOD_BOAT, ctx -> new BoatEntityRenderer(ctx, WRITHEWOOD_BOAT_LAYER));
		EntityRendererRegistry.register(AylythEntityTypes.WRITHEWOOD_CHEST_BOAT, ctx -> new BoatEntityRenderer(ctx, WRITHEWOOD_CHEST_BOAT_LAYER));

		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
			if (entityRenderer instanceof PlayerEntityRenderer playerEntityRenderer) {
				registrationHelper.register(new YmpeInfestationFeature(playerEntityRenderer, context.getEntityModels()));
				registrationHelper.register(new CuirassFeatureRenderer(playerEntityRenderer, context.getEntityModels()));
			}
			registrationHelper.register(new YmpeThornRingFeature((FeatureRendererContext<LivingEntityRenderState, EntityModel<LivingEntityRenderState>>) entityRenderer, context.getEntityModels()));
		});

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (client.world != null && client.player != null && client.world.getTime() % 20 == 0) {
				AylythDimensionRenderer.determineConditions(client.world.getBiome(client.player.getBlockPos()));
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(ClientTickHandler::clientTickEnd);
		ClientTickEvents.END_CLIENT_TICK.register((world) -> {
			PlayerEntity player = MinecraftClient.getInstance().player;
			if (player != null) {
				ClientPlayNetworking.send(new UpdatePressingUpDownPacketC2S(MinecraftClient.getInstance().options.jumpKey.isPressed(), DESCEND.isPressed()));
			}
		});

		// TODO: Figure out how to best give blight potions a custom model
//		ModelLoadingPlugin.register(pluginContext -> {
//			pluginContext.addModels(
//					Aylyth.id("item/coker_cola"),
//					Aylyth.id("item/coker_cola_splash"),
//					Aylyth.id("item/coker_cola_lingering")
//			);
//
//			pluginContext.modifyModelBeforeBake().register((model, context) -> {
//				if (context.id().equals(ModelIdentifier.ofVanilla("potion", "inventory"))) {
//					if (model instanceof JsonUnbakedModel jsonModel) {
//						List<ModelOverride.Condition> conditions = List.of(
//								new ModelOverride.Condition(Aylyth.id("blight_potion"), 1f)
//						);
//						jsonModel.getOverrides().add(new ModelOverride(Aylyth.id("item/coker_cola"), conditions));
//					}
//				} else if (context.id().equals(ModelIdentifier.ofVanilla("splash_potion", "inventory"))) {
//					if (model instanceof JsonUnbakedModel jsonModel) {
//						List<ModelOverride.Condition> conditions = List.of(
//								new ModelOverride.Condition(Aylyth.id("blight_potion"), 1f)
//						);
//						jsonModel.getOverrides().add(new ModelOverride(Aylyth.id("item/coker_cola_splash"), conditions));
//					}
//				} else if (context.id().equals(ModelIdentifier.ofVanilla("lingering_potion", "inventory"))) {
//					if (model instanceof JsonUnbakedModel jsonModel) {
//						List<ModelOverride.Condition> conditions = List.of(
//								new ModelOverride.Condition(Aylyth.id("blight_potion"), 1f)
//						);
//						jsonModel.getOverrides().add(new ModelOverride(Aylyth.id("item/coker_cola_lingering"), conditions));
//					}
//				}
//				return model;
//			});
//		});

		ModelLoadingPlugin.register(pluginContext -> {
			Identifier soulHearthId = Aylyth.id("block/soul_hearth_charged_lower");
			pluginContext.modifyModelAfterBake().register((model, context) -> {
				if (context.id().equals(soulHearthId)) {
					return new SoulHearthBlockModel(MinecraftClient.getInstance().getBakedModelManager(), model);
				}
				return model;
			});
		});

		HandledScreens.register(AylythScreenHandlerTypes.TULPA, TulpaScreen::new);
	}

	private static Block[] cutoutBlocks() {
		return new Block[] {
				AylythBlocks.YMPE_SAPLING,
				AylythBlocks.POTTED_YMPE_SAPLING,
				AylythBlocks.YMPE_DOOR,
				AylythBlocks.YMPE_TRAPDOOR,
				AylythBlocks.POMEGRANATE_SAPLING,
				AylythBlocks.POTTED_POMEGRANATE_SAPLING,
				AylythBlocks.POMEGRANATE_DOOR,
				AylythBlocks.POMEGRANATE_TRAPDOOR,
				AylythBlocks.WRITHEWOOD_SAPLING,
				AylythBlocks.POTTED_WRITHEWOOD_SAPLING,
				AylythBlocks.WRITHEWOOD_DOOR,
				AylythBlocks.WRITHEWOOD_TRAPDOOR,
				AylythBlocks.AYLYTH_BUSH,
				AylythBlocks.ANTLER_SHOOTS,
				AylythBlocks.GRIPWEED,
				AylythBlocks.NYSIAN_GRAPE_VINE,
				AylythBlocks.MARIGOLD,
				AylythBlocks.POTTED_MARIGOLD,
				AylythBlocks.OAK_SEEP,
				AylythBlocks.SPRUCE_SEEP,
				AylythBlocks.DARK_OAK_SEEP,
				AylythBlocks.YMPE_SEEP,
				AylythBlocks.OAK_STREWN_LEAVES,
				AylythBlocks.YMPE_STREWN_LEAVES,
				AylythBlocks.GHOSTCAP_MUSHROOM,
				AylythBlocks.SOUL_HEARTH,
				AylythBlocks.VITAL_THURIBLE,
				AylythBlocks.LARGE_WOODY_GROWTH,
				AylythBlocks.GIRASOL_SAPLING,
				AylythBlocks.POTTED_GIRASOL_SAPLING,
				AylythBlocks.BLACK_WELL,
				AylythBlocks.DARK_OAK_BRANCH,
				AylythBlocks.BARE_DARK_OAK_BRANCH,
				AylythBlocks.WRITHEWOOD_BRANCH,
				AylythBlocks.BARE_WRITHEWOOD_BRANCH,
				AylythBlocks.YMPE_BRANCH,
				AylythBlocks.BARE_YMPE_BRANCH,
				AylythBlocks.ORANGE_AYLYTHIAN_OAK_BRANCH,
				AylythBlocks.RED_AYLYTHIAN_OAK_BRANCH,
				AylythBlocks.BROWN_AYLYTHIAN_OAK_BRANCH,
				AylythBlocks.GREEN_AYLYTHIAN_OAK_SAPLING,
				AylythBlocks.POTTED_GREEN_AYLYTHIAN_OAK_SAPLING,
				AylythBlocks.GREEN_AYLYTHIAN_OAK_LEAVES,
				AylythBlocks.ORANGE_AYLYTHIAN_OAK_SAPLING,
				AylythBlocks.POTTED_ORANGE_AYLYTHIAN_OAK_SAPLING,
				AylythBlocks.ORANGE_AYLYTHIAN_OAK_LEAVES,
				AylythBlocks.RED_AYLYTHIAN_OAK_SAPLING,
				AylythBlocks.POTTED_RED_AYLYTHIAN_OAK_SAPLING,
				AylythBlocks.RED_AYLYTHIAN_OAK_LEAVES,
				AylythBlocks.BROWN_AYLYTHIAN_OAK_SAPLING,
				AylythBlocks.POTTED_BROWN_AYLYTHIAN_OAK_SAPLING,
				AylythBlocks.BROWN_AYLYTHIAN_OAK_LEAVES
		};
	}

	public static final class ClientTickHandler {
		private ClientTickHandler() {
		}

		public static int ticksInGame = 0;
		public static float partialTicks = 0;
		public static float delta = 0;
		public static float total = 0;

		public static void calcDelta() {
			float oldTotal = total;
			total = ticksInGame + partialTicks;
			delta = total - oldTotal;
		}

		public static void renderTick(float renderTickTime) {
			partialTicks = renderTickTime;
		}

		public static void clientTickEnd(MinecraftClient mc) {
			if (!mc.isPaused()) {
				ticksInGame++;
				partialTicks = 0;
			}
			calcDelta();
		}
	}
}
