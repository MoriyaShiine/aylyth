package moriyashiine.aylyth.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import moriyashiine.aylyth.client.advancement.AdvancementIconRendererRegistry;
import moriyashiine.aylyth.client.model.entity.layer.CuirassModel;
import moriyashiine.aylyth.client.model.entity.layer.YmpeInfestationModel;
import moriyashiine.aylyth.client.model.entity.layer.YmpeThornRingModel;
import moriyashiine.aylyth.client.model.entity.RootPropEntityModel;
import moriyashiine.aylyth.client.model.entity.ScionEntityModel;
import moriyashiine.aylyth.client.network.AylythClientNetworkHandler;
import moriyashiine.aylyth.client.particle.ParticleFactories;
import moriyashiine.aylyth.client.render.entity.projectile.ThornFlechetteRenderer;
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
import moriyashiine.aylyth.common.registry.key.ModDimensionKeys;
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
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

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
		DimensionRenderingRegistry.registerDimensionEffects(ModDimensionKeys.AYLYTH.getValue(), AylythDimensionRenderer.DIMENSION_EFFECTS);
		DimensionRenderingRegistry.registerSkyRenderer(ModDimensionKeys.AYLYTH, AylythDimensionRenderer::renderSky);
		DimensionRenderingRegistry.registerCloudRenderer(ModDimensionKeys.AYLYTH, context -> {});

		ClientPlayNetworking.registerGlobalReceiver(AylythPacketTypes.SPAWN_PARTICLES_AROUND_PACKET, AylythClientNetworkHandler::handleSpawnParticlesAround);

		ParticleFactoryRegistry.getInstance().register(ModParticles.PILOT_LIGHT, PilotLightParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.AMBIENT_PILOT_LIGHT, PilotLightParticle.AmbientFactory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.HIND_SMOKE, HindSmokeParticle.ShortSmokeFactory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.VAMPIRIC_DRIP, ParticleFactories::createVampiricDrip);
		ParticleFactoryRegistry.getInstance().register(ModParticles.VAMPIRIC_LAND, ParticleFactories::createVampiricLand);
		ParticleFactoryRegistry.getInstance().register(ModParticles.BLIGHT_DRIP, ParticleFactories::createBlightDrip);
		ParticleFactoryRegistry.getInstance().register(ModParticles.BLIGHT_LAND, ParticleFactories::createBlightLand);

		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.YMPE_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.POMEGRANATE_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.WRITHEWOOD_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.YMPE_HANGING_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.POMEGRANATE_HANGING_SIGN.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.WRITHEWOOD_HANGING_SIGN.getTexture()));

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), cutoutBlocks());

		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.AYLYTH_BUSH);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null && state != null && state.getBlock() instanceof StrewnLeavesBlock && state.get(StrewnLeavesBlock.LEAVES) > 0 ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.OAK_STREWN_LEAVES);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.ANTLER_SHOOTS, ModBlocks.GRIPWEED);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			BlockState blockState = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
			return MinecraftClient.getInstance().getBlockColors().getColor(blockState, null, null, tintIndex);
		}, ModBlocks.AYLYTH_BUSH, ModBlocks.ANTLER_SHOOTS, ModBlocks.GRIPWEED);

		ModelPredicateProviderRegistry.register(ModItems.SHUCKED_YMPE_FRUIT, AylythUtil.id("variant"), (stack, world, entity, seed) -> ShuckedYmpeFruitItem.hasStoredEntity(stack) ? 1 : 0);
		ClampedModelPredicateProvider flaskProvider = (stack, world, entity, seed) -> NephriteFlaskItem.getCharges(stack) / 6f;
		ModelPredicateProviderRegistry.register(ModItems.NEPHRITE_FLASK, AylythUtil.id("uses"), flaskProvider);
		ModelPredicateProviderRegistry.register(ModItems.DARK_NEPHRITE_FLASK, AylythUtil.id("uses"), flaskProvider);

		BlockEntityRendererFactories.register(ModBlockEntityTypes.SEEP_BLOCK_ENTITY_TYPE, SeepBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(ModBlockEntityTypes.VITAL_THURIBLE_BLOCK_ENTITY, VitalThuribleBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(ModBlockEntityTypes.SOUL_HEARTH_BLOCK_ENTITY, SoulHearthBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(ModBlockEntityTypes.WOODY_GROWTH_CACHE_BLOCK_ENTITY, WoodyGrowthBlockEntityRenderer::new);

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

		EntityRendererRegistry.register(ModEntityTypes.PILOT_LIGHT, PilotLightEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.AYLYTHIAN, AylythianEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ELDER_AYLYTHIAN, ElderAylythianEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.YMPE_LANCE, YmpeLanceEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.SOULMOULD, SoulmouldEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.BONEFLY, BoneflyEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ROOT_PROP, RootPropEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.RIPPED_SOUL, RippedSoulEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.TULPA, TulpaEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.TULPA_PLAYER, TulpaPlayerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.WREATHED_HIND_ENTITY, WreathedHindEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.SPHERE_ENTITY, SphereEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.FAUNAYLYTHIAN, FaunaylythianEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.SCION, ScionEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.THORN_FLECHETTE, ThornFlechetteRenderer::new);

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

		registerBigItemRenderer(ModItems.YMPE_LANCE);
		registerBigItemRenderer(ModItems.YMPE_GLAIVE);
		registerBigItemRenderer(ModItems.YMPE_FLAMBERGE);
		registerBigItemRenderer(ModItems.YMPE_SCYTHE);

//		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier(AylythUtil.id("%s_generated".formatted(Registries.ITEM.getId(ModItems.MYSTERIOUS_SKETCH).getPath())), "inventory")));
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.WOODY_GROWTH_CACHE, new WoodyGrowthCacheItemRenderer());
//		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.MYSTERIOUS_SKETCH, new MysteriousSketchItemRenderer());

		HandledScreens.register(ModScreenHandlers.TULPA_SCREEN_HANDLER, TulpaScreen::new);

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
				ModBlocks.YMPE_SAPLING,
				ModBlocks.YMPE_POTTED_SAPLING,
				ModBlocks.YMPE_DOOR,
				ModBlocks.YMPE_TRAPDOOR,
				ModBlocks.POMEGRANATE_SAPLING,
				ModBlocks.POMEGRANATE_POTTED_SAPLING,
				ModBlocks.POMEGRANATE_DOOR,
				ModBlocks.POMEGRANATE_TRAPDOOR,
				ModBlocks.WRITHEWOOD_SAPLING,
				ModBlocks.WRITHEWOOD_POTTED_SAPLING,
				ModBlocks.WRITHEWOOD_DOOR,
				ModBlocks.WRITHEWOOD_TRAPDOOR,
				ModBlocks.AYLYTH_BUSH,
				ModBlocks.ANTLER_SHOOTS,
				ModBlocks.GRIPWEED,
				ModBlocks.NYSIAN_GRAPE_VINE,
				ModBlocks.MARIGOLD,
				ModBlocks.MARIGOLD_POTTED,
				ModBlocks.OAK_SEEP,
				ModBlocks.SPRUCE_SEEP,
				ModBlocks.DARK_OAK_SEEP,
				ModBlocks.YMPE_SEEP,
				ModBlocks.OAK_STREWN_LEAVES,
				ModBlocks.YMPE_STREWN_LEAVES,
				ModBlocks.GHOSTCAP_MUSHROOM,
				ModBlocks.SOUL_HEARTH,
				ModBlocks.VITAL_THURIBLE,
				ModBlocks.LARGE_WOODY_GROWTH,
				ModBlocks.GIRASOL_SAPLING,
				ModBlocks.GIRASOL_SAPLING_POTTED,
				ModBlocks.BLACK_WELL
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
