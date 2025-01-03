package moriyashiine.aylyth.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import moriyashiine.aylyth.client.advancement.AdvancementIconRendererRegistry;
import moriyashiine.aylyth.client.model.PerspectiveModelLoader;
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
import moriyashiine.aylyth.client.render.AylythRenderLayers;
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
import moriyashiine.aylyth.client.render.item.SpearItemRenderer;
import moriyashiine.aylyth.client.render.item.WoodyGrowthCacheItemRenderer;
import moriyashiine.aylyth.client.screen.TulpaScreen;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlockEntityTypes;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.StrewnLeavesBlock;
import moriyashiine.aylyth.common.data.tag.AylythPotionTags;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.item.types.NephriteFlaskItem;
import moriyashiine.aylyth.common.item.types.ShuckedYmpeFruitItem;
import moriyashiine.aylyth.common.network.AylythPacketTypes;
import moriyashiine.aylyth.common.network.packets.UpdatePressingUpDownPacketC2S;
import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import moriyashiine.aylyth.common.screenhandler.AylythScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
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
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_1_MODEL_LAYER = new EntityModelLayer(Aylyth.id("ympe_infestation_1"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_2_MODEL_LAYER = new EntityModelLayer(Aylyth.id("ympe_infestation_2"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_3_MODEL_LAYER = new EntityModelLayer(Aylyth.id("ympe_infestation_3"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_4_MODEL_LAYER = new EntityModelLayer(Aylyth.id("ympe_infestation_4"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_5_MODEL_LAYER = new EntityModelLayer(Aylyth.id("ympe_infestation_5"), "main");
	public static final EntityModelLayer YMPE_THORN_RING_MODEL_LAYER = new EntityModelLayer(Aylyth.id("ympe_thorn_ring"), "main");

	@Override
	public void onInitializeClient() {
		DimensionRenderingRegistry.registerDimensionEffects(AylythDimensionData.WORLD.getValue(), AylythDimensionRenderer.DIMENSION_EFFECTS);
		DimensionRenderingRegistry.registerSkyRenderer(AylythDimensionData.WORLD, AylythDimensionRenderer::renderSky);
		DimensionRenderingRegistry.registerCloudRenderer(AylythDimensionData.WORLD, context -> {});

		ClientPlayNetworking.registerGlobalReceiver(AylythPacketTypes.SPAWN_PARTICLES_AROUND_PACKET, AylythClientNetworkHandler::handleSpawnParticlesAround);

		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.PILOT_LIGHT, PilotLightParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.AMBIENT_PILOT_LIGHT, PilotLightParticle.AmbientFactory::new);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.HIND_SMOKE, HindSmokeParticle.ShortSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.VAMPIRIC_DRIP, ParticleFactories::createVampiricDrip);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.VAMPIRIC_LAND, ParticleFactories::createVampiricLand);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.BLIGHT_DRIP, ParticleFactories::createBlightDrip);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.BLIGHT_LAND, ParticleFactories::createBlightLand);
		ParticleFactoryRegistry.getInstance().register(AylythParticleTypes.SOUL_EMBER, SoulEmberParticle.Factory::new);

		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.YMPE_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.POMEGRANATE_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.WRITHEWOOD_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.YMPE_HANGING_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.POMEGRANATE_HANGING_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, AylythBlocks.WRITHEWOOD_HANGING_SIGN.getTexture()));

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), cutoutBlocks());

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), AylythBlocks.AYLYTH_BUSH);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null && state != null && state.getBlock() instanceof StrewnLeavesBlock && state.get(StrewnLeavesBlock.LEAVES) > 0 ? BiomeColors.getFoliageColor(world, pos) : 0xFFFFFFFF, AylythBlocks.OAK_STREWN_LEAVES);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : FoliageColors.getDefaultColor(), AylythBlocks.ANTLER_SHOOTS, AylythBlocks.GRIPWEED);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			BlockState blockState = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
			return MinecraftClient.getInstance().getBlockColors().getColor(blockState, null, null, tintIndex);
		}, AylythBlocks.AYLYTH_BUSH, AylythBlocks.ANTLER_SHOOTS, AylythBlocks.GRIPWEED);

		ModelPredicateProviderRegistry.register(AylythItems.SHUCKED_YMPE_FRUIT, Aylyth.id("variant"), (stack, world, entity, seed) -> ShuckedYmpeFruitItem.hasStoredEntity(stack) ? 1 : 0);
		ClampedModelPredicateProvider flaskProvider = (stack, world, entity, seed) -> NephriteFlaskItem.getCharges(stack) / 6f;
		ModelPredicateProviderRegistry.register(AylythItems.NEPHRITE_FLASK, Aylyth.id("uses"), flaskProvider);
		ModelPredicateProviderRegistry.register(AylythItems.DARK_NEPHRITE_FLASK, Aylyth.id("uses"), flaskProvider);
		ModelPredicateProviderRegistry.register(Items.POTION, Aylyth.id("blight_potion"), (stack, world, entity, seed) -> Registries.POTION.getEntry(PotionUtil.getPotion(stack)).isIn(AylythPotionTags.BLIGHT) ? 1 : 0);
		ModelPredicateProviderRegistry.register(Items.SPLASH_POTION, Aylyth.id("blight_potion"), (stack, world, entity, seed) -> Registries.POTION.getEntry(PotionUtil.getPotion(stack)).isIn(AylythPotionTags.BLIGHT) ? 1 : 0);
		ModelPredicateProviderRegistry.register(Items.LINGERING_POTION, Aylyth.id("blight_potion"), (stack, world, entity, seed) -> Registries.POTION.getEntry(PotionUtil.getPotion(stack)).isIn(AylythPotionTags.BLIGHT) ? 1 : 0);

		BlockEntityRendererFactories.register(AylythBlockEntityTypes.SEEP, SeepBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(AylythBlockEntityTypes.VITAL_THURIBLE, VitalThuribleBlockEntityRenderer::new);
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

		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
			if (entityType == EntityType.PLAYER) {
				registrationHelper.register(new YmpeInfestationFeature((PlayerEntityRenderer)entityRenderer, context.getModelLoader()));
				registrationHelper.register(new CuirassFeatureRenderer((PlayerEntityRenderer)entityRenderer, context.getModelLoader()));
			}
			registrationHelper.register(new YmpeThornRingFeature((FeatureRendererContext<LivingEntity, EntityModel<LivingEntity>>) entityRenderer, context.getModelLoader()));
		});

		TerraformBoatClientHelper.registerModelLayers(Aylyth.id("ympe"), false);
		TerraformBoatClientHelper.registerModelLayers(Aylyth.id("pomegranate"), false);
		TerraformBoatClientHelper.registerModelLayers(Aylyth.id("writhewood"), false);

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

		registerSpearItemRenderer(AylythItems.YMPE_LANCE);

		ModelLoadingPlugin.register(pluginContext -> {
			pluginContext.addModels(
					Aylyth.id("item/coker_cola"),
					Aylyth.id("item/coker_cola_splash"),
					Aylyth.id("item/coker_cola_lingering")
			);
			pluginContext.modifyModelBeforeBake().register((model, context) -> {
				if (context.id().equals(ModelIdentifier.ofVanilla("potion", "inventory"))) {
					if (model instanceof JsonUnbakedModel jsonModel) {
						List<ModelOverride.Condition> conditions = List.of(
								new ModelOverride.Condition(Aylyth.id("blight_potion"), 1f)
						);
						jsonModel.getOverrides().add(new ModelOverride(Aylyth.id("item/coker_cola"), conditions));
					}
				} else if (context.id().equals(ModelIdentifier.ofVanilla("splash_potion", "inventory"))) {
					if (model instanceof JsonUnbakedModel jsonModel) {
						List<ModelOverride.Condition> conditions = List.of(
								new ModelOverride.Condition(Aylyth.id("blight_potion"), 1f)
						);
						jsonModel.getOverrides().add(new ModelOverride(Aylyth.id("item/coker_cola_splash"), conditions));
					}
				} else if (context.id().equals(ModelIdentifier.ofVanilla("lingering_potion", "inventory"))) {
					if (model instanceof JsonUnbakedModel jsonModel) {
						List<ModelOverride.Condition> conditions = List.of(
								new ModelOverride.Condition(Aylyth.id("blight_potion"), 1f)
						);
						jsonModel.getOverrides().add(new ModelOverride(Aylyth.id("item/coker_cola_lingering"), conditions));
					}
				}
				return model;
			});
		});

		ModelLoadingPlugin.register(pluginContext -> {
			Identifier soulHearthId = Aylyth.id("block/soul_hearth_charged_lower");
			pluginContext.modifyModelAfterBake().register((model, context) -> {
				if (context.id().equals(soulHearthId)) {
					return new SoulHearthBlockModel(model);
				}
				return model;
			});
		});

		PreparableModelLoadingPlugin.register(PerspectiveModelLoader::load, PerspectiveModelLoader::apply);

		BuiltinItemRendererRegistry.INSTANCE.register(AylythItems.WOODY_GROWTH_CACHE, new WoodyGrowthCacheItemRenderer());

		HandledScreens.register(AylythScreenHandlerTypes.TULPA, TulpaScreen::new);

		CoreShaderRegistrationCallback.EVENT.register(context -> {
			context.register(Aylyth.id("rendertype_seep"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, shader -> AylythRenderLayers.renderLayerSeep = shader);
			context.register(Aylyth.id("rendertype_tint"), VertexFormats.POSITION_TEXTURE, shader -> AylythRenderLayers.renderLayerTint = shader);
		});

		AdvancementIconRendererRegistry.init();
	}

	private void registerSpearItemRenderer(ItemConvertible item) {
		Identifier itemId = Registries.ITEM.getId(item.asItem());
		ModelIdentifier spearId = new ModelIdentifier(itemId.withSuffixedPath("_spear"), "inventory");
		SpearItemRenderer spearItemRenderer = new SpearItemRenderer(spearId);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(spearItemRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(item, spearItemRenderer);
		ModelLoadingPlugin.register(pluginContext -> {
			pluginContext.addModels(spearId);

			final ModelIdentifier itemModel = new ModelIdentifier(itemId, "inventory");
			pluginContext.modifyModelAfterBake().register((model, context) -> {
				if (context.id().equals(itemModel)) {
					return new WrappedSpearItemModel(model);
				}
				return model;
			});
		});
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

	public static class WrappedSpearItemModel extends ForwardingBakedModel {
		public WrappedSpearItemModel(BakedModel model) {
			this.wrapped = model;
		}

		@Override
		public boolean isSideLit() {
			return false;
		}
	}
}
