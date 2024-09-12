package moriyashiine.aylyth.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import moriyashiine.aylyth.client.advancement.AdvancementIconRendererRegistry;
import moriyashiine.aylyth.client.model.block.SoulHearthBlockModel;
import moriyashiine.aylyth.client.model.entity.layer.CuirassModel;
import moriyashiine.aylyth.client.model.entity.layer.YmpeInfestationModel;
import moriyashiine.aylyth.client.model.entity.layer.YmpeThornRingModel;
import moriyashiine.aylyth.client.model.entity.RootPropEntityModel;
import moriyashiine.aylyth.client.model.entity.ScionEntityModel;
import moriyashiine.aylyth.client.network.AylythClientNetworkHandler;
import moriyashiine.aylyth.client.particle.ParticleFactories;
import moriyashiine.aylyth.client.render.entity.projectile.ThornFlechetteRenderer;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.item.NephriteFlaskItem;
import moriyashiine.aylyth.common.item.ShuckedYmpeFruitItem;
import moriyashiine.aylyth.common.network.AylythPacketTypes;
import moriyashiine.aylyth.common.network.packets.UpdatePressingUpDownPacketC2S;
import moriyashiine.aylyth.client.particle.HindSmokeParticle;
import moriyashiine.aylyth.client.particle.PilotLightParticle;
import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import moriyashiine.aylyth.client.render.AylythRenderLayers;
import moriyashiine.aylyth.client.render.block.entity.SeepBlockEntityRenderer;
import moriyashiine.aylyth.client.render.block.entity.SoulHearthBlockEntityRenderer;
import moriyashiine.aylyth.client.render.block.entity.VitalThuribleBlockEntityRenderer;
import moriyashiine.aylyth.client.render.block.entity.WoodyGrowthBlockEntityRenderer;
import moriyashiine.aylyth.client.render.entity.RootPropEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.*;
import moriyashiine.aylyth.client.render.entity.living.feature.CuirassFeatureRenderer;
import moriyashiine.aylyth.client.render.entity.living.feature.YmpeInfestationFeature;
import moriyashiine.aylyth.client.render.entity.living.feature.YmpeThornRingFeature;
import moriyashiine.aylyth.client.render.entity.projectile.SphereEntityRenderer;
import moriyashiine.aylyth.client.render.entity.projectile.YmpeLanceEntityRenderer;
import moriyashiine.aylyth.client.render.item.BigItemRenderer;
import moriyashiine.aylyth.client.render.item.WoodyGrowthCacheItemRenderer;
import moriyashiine.aylyth.client.screen.TulpaScreen;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.StrewnLeavesBlock;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.data.tag.AylythPotionTags;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.List;

public class AylythClient implements ClientModInitializer {
	public static final KeyBinding DESCEND = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.aylyth.descend", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_G, "category.aylyth.keybind"));
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_1_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_1"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_2_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_2"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_3_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_3"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_4_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_4"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_5_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_5"), "main");
	public static final EntityModelLayer YMPE_THORN_RING_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_thorn_ring"), "main");

	@Override
	public void onInitializeClient() {
		DimensionRenderingRegistry.registerDimensionEffects(AylythDimensionData.AYLYTH.getValue(), AylythDimensionRenderer.DIMENSION_EFFECTS);
		DimensionRenderingRegistry.registerSkyRenderer(AylythDimensionData.AYLYTH, AylythDimensionRenderer::renderSky);
		DimensionRenderingRegistry.registerCloudRenderer(AylythDimensionData.AYLYTH, context -> {});

		ClientPlayNetworking.registerGlobalReceiver(AylythPacketTypes.SPAWN_PARTICLES_AROUND_PACKET, AylythClientNetworkHandler::handleSpawnParticlesAround);

		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.PILOT_LIGHT, PilotLightParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.AMBIENT_PILOT_LIGHT, PilotLightParticle.AmbientFactory::new);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.HIND_SMOKE, HindSmokeParticle.ShortSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.VAMPIRIC_DRIP, ParticleFactories::createVampiricDrip);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.VAMPIRIC_LAND, ParticleFactories::createVampiricLand);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.BLIGHT_DRIP, ParticleFactories::createBlightDrip);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.BLIGHT_LAND, ParticleFactories::createBlightLand);

		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.YMPE_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.POMEGRANATE_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.WRITHEWOOD_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.YMPE_HANGING_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.POMEGRANATE_HANGING_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.WRITHEWOOD_HANGING_SIGN.getTexture()));

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), cutoutBlocks());

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), AylythBlocks.AYLYTH_BUSH);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null && state != null && state.getBlock() instanceof StrewnLeavesBlock && state.get(StrewnLeavesBlock.LEAVES) > 0 ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), AylythBlocks.OAK_STREWN_LEAVES);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : FoliageColors.getDefaultColor(), AylythBlocks.ANTLER_SHOOTS, AylythBlocks.GRIPWEED);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			BlockState blockState = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
			return MinecraftClient.getInstance().getBlockColors().getColor(blockState, null, null, tintIndex);
		}, AylythBlocks.AYLYTH_BUSH, AylythBlocks.ANTLER_SHOOTS, AylythBlocks.GRIPWEED);

		ModelPredicateProviderRegistry.register(AylythItems.SHUCKED_YMPE_FRUIT, AylythUtil.id("variant"), (stack, world, entity, seed) -> ShuckedYmpeFruitItem.hasStoredEntity(stack) ? 1 : 0);
		ClampedModelPredicateProvider flaskProvider = (stack, world, entity, seed) -> NephriteFlaskItem.getCharges(stack) / 6f;
		ModelPredicateProviderRegistry.register(AylythItems.NEPHRITE_FLASK, AylythUtil.id("uses"), flaskProvider);
		ModelPredicateProviderRegistry.register(AylythItems.DARK_NEPHRITE_FLASK, AylythUtil.id("uses"), flaskProvider);
		ModelPredicateProviderRegistry.register(Items.POTION, AylythUtil.id("blight_potion"), (stack, world, entity, seed) -> Registries.POTION.getEntry(PotionUtil.getPotion(stack)).isIn(AylythPotionTags.BLIGHT) ? 1 : 0);
		ModelPredicateProviderRegistry.register(Items.SPLASH_POTION, AylythUtil.id("blight_potion"), (stack, world, entity, seed) -> Registries.POTION.getEntry(PotionUtil.getPotion(stack)).isIn(AylythPotionTags.BLIGHT) ? 1 : 0);
		ModelPredicateProviderRegistry.register(Items.LINGERING_POTION, AylythUtil.id("blight_potion"), (stack, world, entity, seed) -> Registries.POTION.getEntry(PotionUtil.getPotion(stack)).isIn(AylythPotionTags.BLIGHT) ? 1 : 0);

		BlockEntityRendererFactories.register(AylythBlockEntityTypes.SEEP, SeepBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(AylythBlockEntityTypes.VITAL_THURIBLE, VitalThuribleBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(AylythBlockEntityTypes.SOUL_HEARTH, SoulHearthBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(AylythBlockEntityTypes.WOODY_GROWTH_CACHE, WoodyGrowthBlockEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_1_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData1);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_2_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData2);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_3_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData3);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_4_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData4);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_5_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData5);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_1, CuirassModel::createBodyLayer1);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_2, CuirassModel::createBodyLayer2);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_3, CuirassModel::createBodyLayer3);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_4, CuirassModel::createBodyLayer4);
		EntityModelLayerRegistry.registerModelLayer(CuirassModel.LAYER_LOCATION_5, CuirassModel::createBodyLayer5);
		EntityModelLayerRegistry.registerModelLayer(YMPE_THORN_RING_MODEL_LAYER, YmpeThornRingModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ScionEntityModel.LAYER_LOCATION, ScionEntityModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(RootPropEntityModel.LAYER_LOCATION, RootPropEntityModel::createBodyLayer);

		EntityRendererRegistry.register(AylythEntityTypes.PILOT_LIGHT, PilotLightEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.AYLYTHIAN, AylythianEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.ELDER_AYLYTHIAN, ElderAylythianEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.YMPE_LANCE, YmpeLanceEntityRenderer::new);
		EntityRendererRegistry.register(AylythEntityTypes.SOULMOULD, SoulmouldEntityRenderer::new);
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

		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
			if (entityType == EntityType.PLAYER) {
				registrationHelper.register(new YmpeInfestationFeature((PlayerEntityRenderer)entityRenderer, context.getModelLoader()));
				registrationHelper.register(new CuirassFeatureRenderer((PlayerEntityRenderer)entityRenderer, context.getModelLoader()));
			}
			registrationHelper.register(new YmpeThornRingFeature((FeatureRendererContext<LivingEntity, EntityModel<LivingEntity>>) entityRenderer, context.getModelLoader()));
		});

		TerraformBoatClientHelper.registerModelLayers(new Identifier(Aylyth.MOD_ID, "ympe"), false);
		TerraformBoatClientHelper.registerModelLayers(new Identifier(Aylyth.MOD_ID, "pomegranate"), false);
		TerraformBoatClientHelper.registerModelLayers(new Identifier(Aylyth.MOD_ID, "writhewood"), false);

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

		registerBigItemRenderer(AylythItems.YMPE_LANCE);
		registerBigItemRenderer(AylythItems.YMPE_GLAIVE);
		registerBigItemRenderer(AylythItems.YMPE_FLAMBERGE);
		registerBigItemRenderer(AylythItems.YMPE_SCYTHE);

		ModelLoadingPlugin.register(pluginContext -> {
			pluginContext.addModels(
					AylythUtil.id("item/coker_cola"),
					AylythUtil.id("item/coker_cola_splash"),
					AylythUtil.id("item/coker_cola_lingering")
			);
			pluginContext.modifyModelBeforeBake().register((model, context) -> {
				if (context.id().equals(ModelIdentifier.ofVanilla("potion", "inventory"))) {
					if (model instanceof JsonUnbakedModel jsonModel) {
						List<ModelOverride.Condition> conditions = List.of(
								new ModelOverride.Condition(AylythUtil.id("blight_potion"), 1f)
						);
						jsonModel.getOverrides().add(new ModelOverride(AylythUtil.id("item/coker_cola"), conditions));
					}
				} else if (context.id().equals(ModelIdentifier.ofVanilla("splash_potion", "inventory"))) {
					if (model instanceof JsonUnbakedModel jsonModel) {
						List<ModelOverride.Condition> conditions = List.of(
								new ModelOverride.Condition(AylythUtil.id("blight_potion"), 1f)
						);
						jsonModel.getOverrides().add(new ModelOverride(AylythUtil.id("item/coker_cola_splash"), conditions));
					}
				} else if (context.id().equals(ModelIdentifier.ofVanilla("lingering_potion", "inventory"))) {
					if (model instanceof JsonUnbakedModel jsonModel) {
						List<ModelOverride.Condition> conditions = List.of(
								new ModelOverride.Condition(AylythUtil.id("blight_potion"), 1f)
						);
						jsonModel.getOverrides().add(new ModelOverride(AylythUtil.id("item/coker_cola_lingering"), conditions));
					}
				}
				return model;
			});
		});

		if (false) {
			ModelLoadingPlugin.register(pluginContext -> {
				pluginContext.modifyModelAfterBake().register((model, context) -> {
					if (context.id().getPath().contains("soul_hearth_charged")) {
						return new SoulHearthBlockModel(model);
					}
					return model;
				});
			});
		}

//		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier(AylythUtil.id("%s_generated".formatted(Registries.ITEM.getId(ModItems.MYSTERIOUS_SKETCH).getPath())), "inventory")));
		BuiltinItemRendererRegistry.INSTANCE.register(AylythItems.WOODY_GROWTH_CACHE, new WoodyGrowthCacheItemRenderer());
//		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.MYSTERIOUS_SKETCH, new MysteriousSketchItemRenderer());

		HandledScreens.register(AylythScreenHandlerTypes.TULPA, TulpaScreen::new);

		CoreShaderRegistrationCallback.EVENT.register(context -> {
			context.register(AylythUtil.id("rendertype_seep"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, shader -> AylythRenderLayers.renderLayerSeep = shader);
			context.register(AylythUtil.id("rendertype_tint"), VertexFormats.POSITION_TEXTURE, shader -> AylythRenderLayers.renderLayerTint = shader);
		});

		AdvancementIconRendererRegistry.init();
	}

	private void registerBigItemRenderer(ItemConvertible item) {
		Identifier bigId = Registries.ITEM.getId(item.asItem());
		Identifier guiId = new ModelIdentifier(bigId.withSuffixedPath("_gui"), "inventory");
		Identifier handheldId = new ModelIdentifier(bigId.withSuffixedPath("_handheld"), "inventory");
		BigItemRenderer bigItemRenderer = new BigItemRenderer(bigId);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(bigItemRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(item, bigItemRenderer);
		ModelLoadingPlugin.register(pluginContext -> {
			pluginContext.addModels(guiId, handheldId);

			final Identifier basicModel = new ModelIdentifier(bigId, "inventory");
			pluginContext.modifyModelAfterBake().register((model, context) -> {
				if (context.id().equals(basicModel)) {
					return new WrappedBigItemModel(model);
				}
				return model;
			});
		});
	}

	private static Block[] cutoutBlocks() {
		return new Block[] {
				AylythBlocks.YMPE_SAPLING,
				AylythBlocks.YMPE_POTTED_SAPLING,
				AylythBlocks.YMPE_DOOR,
				AylythBlocks.YMPE_TRAPDOOR,
				AylythBlocks.POMEGRANATE_SAPLING,
				AylythBlocks.POMEGRANATE_POTTED_SAPLING,
				AylythBlocks.POMEGRANATE_DOOR,
				AylythBlocks.POMEGRANATE_TRAPDOOR,
				AylythBlocks.WRITHEWOOD_SAPLING,
				AylythBlocks.WRITHEWOOD_POTTED_SAPLING,
				AylythBlocks.WRITHEWOOD_DOOR,
				AylythBlocks.WRITHEWOOD_TRAPDOOR,
				AylythBlocks.AYLYTH_BUSH,
				AylythBlocks.ANTLER_SHOOTS,
				AylythBlocks.GRIPWEED,
				AylythBlocks.NYSIAN_GRAPE_VINE,
				AylythBlocks.MARIGOLD,
				AylythBlocks.MARIGOLD_POTTED,
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
				AylythBlocks.GIRASOL_SAPLING_POTTED,
				AylythBlocks.BLACK_WELL
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

	public static class WrappedBigItemModel extends ForwardingBakedModel {
		public WrappedBigItemModel(BakedModel model) {
			this.wrapped = model;
		}

		@Override
		public boolean isSideLit() {
			return false;
		}
	}
}
