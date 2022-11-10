package moriyashiine.aylyth.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import moriyashiine.aylyth.client.model.YmpeInfestationModel;
import moriyashiine.aylyth.client.model.YmpeThornRingModel;
import moriyashiine.aylyth.client.model.entity.RootPropEntityModel;
import moriyashiine.aylyth.client.model.entity.ScionEntityModel;
import moriyashiine.aylyth.client.network.packet.SpawnShuckParticlesPacket;
import moriyashiine.aylyth.client.network.packet.UpdatePressingUpDownPacket;
import moriyashiine.aylyth.client.particle.PilotLightParticle;
import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import moriyashiine.aylyth.client.render.block.entity.SeepBlockEntityRenderer;
import moriyashiine.aylyth.client.render.block.entity.SoulHearthBlockEntityRenderer;
import moriyashiine.aylyth.client.render.block.entity.VitalThuribleBlockEntityRenderer;
import moriyashiine.aylyth.client.render.block.entity.WoodyGrowthBlockEntityRenderer;
import moriyashiine.aylyth.client.render.entity.RootPropEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.*;
import moriyashiine.aylyth.client.render.entity.projectile.YmpeLanceEntityRenderer;
import moriyashiine.aylyth.client.render.item.BigItemRenderer;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.StrewnLeavesBlock;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.mixin.client.DimensionEffectsAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class AylythClient implements ClientModInitializer {
	private static KeyBinding DESCEND;
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_1_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_1"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_2_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_2"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_3_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_3"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_4_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_4"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_5_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_5"), "main");
	public static final EntityModelLayer YMPE_THORN_RING_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_thorn_ring"), "main");

	@Override
	public void onInitializeClient() {
		DimensionEffectsAccessor.aylyth_getByIdentifier().put(ModDimensions.AYLYTH_DIMENSION_KEY.getValue(), AylythDimensionRenderer.DIMENSION_EFFECTS);
		ClientPlayNetworking.registerGlobalReceiver(SpawnShuckParticlesPacket.ID, SpawnShuckParticlesPacket::receive);
		ParticleFactoryRegistry.getInstance().register(ModParticles.PILOT_LIGHT, PilotLightParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.AMBIENT_PILOT_LIGHT, PilotLightParticle.AmbientFactory::new);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.YMPE_BLOCKS.floorSign.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.POMEGRANATE_BLOCKS.floorSign.getTexture()));
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.WRITHEWOOD_BLOCKS.floorSign.getTexture()));

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), cutoutBlocks());
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.AYLYTH_BUSH);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null && state != null && state.getBlock() instanceof StrewnLeavesBlock && state.get(StrewnLeavesBlock.LEAVES) > 0 ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.OAK_STREWN_LEAVES);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.ANTLER_SHOOTS, ModBlocks.GRIPWEED);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			BlockState blockState = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
			return MinecraftClient.getInstance().getBlockColors().getColor(blockState, null, null, tintIndex);
		}, ModBlocks.AYLYTH_BUSH, ModBlocks.ANTLER_SHOOTS, ModBlocks.GRIPWEED);
		ModelPredicateProviderRegistry.register(ModItems.SHUCKED_YMPE_FRUIT, new Identifier(Aylyth.MOD_ID, "variant"), (stack, world, entity, seed) -> stack.hasNbt() && stack.getNbt().contains("StoredEntity") ? 1 : 0);
		BlockEntityRendererRegistry.register(ModBlockEntityTypes.SEEP_BLOCK_ENTITY_TYPE, SeepBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntityTypes.VITAL_THURIBLE_BLOCK_ENTITY, VitalThuribleBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntityTypes.SOUL_HEARTH_BLOCK_ENTITY, SoulHearthBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntityTypes.WOODY_GROWTH_CACHE_BLOCK_ENTITY, WoodyGrowthBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_1_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData1);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_2_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData2);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_3_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData3);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_4_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData4);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_5_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData5);
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
		// TODO EntityRendererRegistry.register(ModEntityTypes.TULPA, TulpaEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.SCION, ScionEntityRenderer::new);
		TerraformBoatClientHelper.registerModelLayers(new Identifier(Aylyth.MOD_ID, "ympe"));
		TerraformBoatClientHelper.registerModelLayers(new Identifier(Aylyth.MOD_ID, "ympe_chest"));
		TerraformBoatClientHelper.registerModelLayers(new Identifier(Aylyth.MOD_ID, "pomegranate"));
		TerraformBoatClientHelper.registerModelLayers(new Identifier(Aylyth.MOD_ID, "pomegranate_chest"));
		TerraformBoatClientHelper.registerModelLayers(new Identifier(Aylyth.MOD_ID, "writhewood"));
		TerraformBoatClientHelper.registerModelLayers(new Identifier(Aylyth.MOD_ID, "writhewood_chest"));
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (client.world != null && client.player != null && client.world.getTime() % 20 == 0) {
				AylythDimensionRenderer.determineConditions(client.world, client.world.getBiome(client.player.getBlockPos()));
			}
		});

		DESCEND = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.aylyth.descend", InputUtil.Type.KEYSYM, 71, "category.aylyth.keybind"));
		ClientTickEvents.END_CLIENT_TICK.register((world) -> {
			PlayerEntity player = MinecraftClient.getInstance().player;
			if (player != null) {
				UpdatePressingUpDownPacket.send(MinecraftClient.getInstance().options.jumpKey.isPressed(), DESCEND.isPressed());
			}

		});

		Identifier ympeBigItemId = Registry.ITEM.getId(ModItems.YMPE_LANCE);
		Identifier glavieBigItemId = Registry.ITEM.getId(ModItems.GLAIVE);
		BigItemRenderer ympeBigItemRenderer = new BigItemRenderer(ympeBigItemId);
		BigItemRenderer glaiveBigItemRenderer = new BigItemRenderer(glavieBigItemId);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(ympeBigItemRenderer);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(glaiveBigItemRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.YMPE_LANCE, ympeBigItemRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.GLAIVE, glaiveBigItemRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.WOODY_GROWTH_CACHE, this::woodyGrowthCacheRenderer);
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
			out.accept(new ModelIdentifier(ympeBigItemId + "_gui", "inventory"));
			out.accept(new ModelIdentifier(ympeBigItemId + "_handheld", "inventory"));
		});
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
			out.accept(new ModelIdentifier(glavieBigItemId + "_gui", "inventory"));
			out.accept(new ModelIdentifier(glavieBigItemId + "_handheld", "inventory"));
		});
	}

	private void woodyGrowthCacheRenderer(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		var rand = Random.create(42);
		var model = MinecraftClient.getInstance().getBlockRenderManager().getModel(ModBlocks.WOODY_GROWTH_CACHE.getDefaultState());
		var consumer = VertexConsumers.union(vertexConsumers.getBuffer(RenderTypes.TINT), vertexConsumers.getBuffer(RenderLayers.getItemLayer(stack, true)));
		for (BakedQuad quad : model.getOverrides().apply(model, stack, MinecraftClient.getInstance().world, mode.isFirstPerson() ? MinecraftClient.getInstance().player : null, 42).getQuads(null, null, rand)) {
			consumer.quad(matrices.peek(), quad, 1.0f, 1.0f, 1.0f, light, overlay);
		}
	}

	private static Block[] cutoutBlocks() {
		return new Block[] {
				ModBlocks.YMPE_BLOCKS.sapling,
				ModBlocks.YMPE_BLOCKS.pottedSapling,
				ModBlocks.YMPE_BLOCKS.door,
				ModBlocks.YMPE_BLOCKS.trapdoor,
				ModBlocks.POMEGRANATE_BLOCKS.sapling,
				ModBlocks.POMEGRANATE_BLOCKS.pottedSapling,
				ModBlocks.POMEGRANATE_BLOCKS.door,
				ModBlocks.POMEGRANATE_BLOCKS.trapdoor,
				ModBlocks.WRITHEWOOD_BLOCKS.sapling,
				ModBlocks.WRITHEWOOD_BLOCKS.pottedSapling,
				ModBlocks.WRITHEWOOD_BLOCKS.door,
				ModBlocks.WRITHEWOOD_BLOCKS.trapdoor,
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
				ModBlocks.LARGE_WOODY_GROWTH
		};
	}
}
