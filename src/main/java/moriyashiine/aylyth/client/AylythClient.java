package moriyashiine.aylyth.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import moriyashiine.aylyth.client.model.YmpeInfestationModel;
import moriyashiine.aylyth.client.network.packet.SpawnShuckParticlesPacket;
import moriyashiine.aylyth.client.particle.PilotLightParticle;
import moriyashiine.aylyth.client.render.block.entity.SeepBlockEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.AylythianEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.ElderAylythianEntityRenderer;
import moriyashiine.aylyth.client.render.entity.living.PilotLightEntityRenderer;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AylythClient implements ClientModInitializer {
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_1_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_1"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_2_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_2"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_3_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_3"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_4_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_4"), "main");
	public static final EntityModelLayer YMPE_INFESTATION_STAGE_5_MODEL_LAYER = new EntityModelLayer(new Identifier(Aylyth.MOD_ID, "ympe_infestation_5"), "main");

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(SpawnShuckParticlesPacket.ID, SpawnShuckParticlesPacket::receive);
		ParticleFactoryRegistry.getInstance().register(ModParticles.PILOT_LIGHT, PilotLightParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.AMBIENT_PILOT_LIGHT, PilotLightParticle.AmbientFactory::new);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.YMPE_SIGN.getTexture()));
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.YMPE_SAPLING, ModBlocks.POTTED_YMPE_SAPLING, ModBlocks.YMPE_DOOR, ModBlocks.YMPE_TRAPDOOR, ModBlocks.AYLYTH_BUSH, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.AYLYTH_BUSH);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			BlockState blockState = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
			return MinecraftClient.getInstance().getBlockColors().getColor(blockState, null, null, tintIndex);
		}, ModBlocks.AYLYTH_BUSH);
		FabricModelPredicateProviderRegistry.register(ModItems.SHUCKED_YMPE_FRUIT, new Identifier(Aylyth.MOD_ID, "variant"), (stack, world, entity, seed) -> stack.hasNbt() && stack.getNbt().contains("StoredEntity") ? 1 : 0);
		BlockEntityRendererRegistry.register(ModBlockEntityTypes.SEEP_BLOCK_ENTITY_TYPE, SeepBlockEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.PILOT_LIGHT, PilotLightEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.AYLYTHIAN, AylythianEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ELDER_AYLYTHIAN, ElderAylythianEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_1_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData1);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_2_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData2);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_3_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData3);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_4_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData4);
		EntityModelLayerRegistry.registerModelLayer(YMPE_INFESTATION_STAGE_5_MODEL_LAYER, YmpeInfestationModel::getTexturedModelData5);
		TerraformBoatClientHelper.registerModelLayer(new Identifier(Aylyth.MOD_ID, "ympe"));
	}
}
