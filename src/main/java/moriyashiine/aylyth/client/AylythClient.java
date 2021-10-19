package moriyashiine.aylyth.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import moriyashiine.aylyth.client.network.packet.SpawnShuckParticlesPacket;
import moriyashiine.aylyth.client.particle.PilotLightParticle;
import moriyashiine.aylyth.client.renderer.block.SeepBlockEntityRenderer;
import moriyashiine.aylyth.client.renderer.entity.living.AylythianEntityRenderer;
import moriyashiine.aylyth.client.renderer.entity.living.ElderAylythianEntityRenderer;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.entity.SeepBlockEntity;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AylythClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(SpawnShuckParticlesPacket.ID, SpawnShuckParticlesPacket::receive);
		ParticleFactoryRegistry.getInstance().register(ModParticles.PILOT_LIGHT, PilotLightParticle.Factory::new);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.YMPE_SIGN.getTexture()));
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.YMPE_SAPLING, ModBlocks.POTTED_YMPE_SAPLING, ModBlocks.YMPE_DOOR, ModBlocks.YMPE_TRAPDOOR, ModBlocks.AYLYTH_BUSH);
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.AYLYTH_BUSH);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			BlockState blockState = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
			return MinecraftClient.getInstance().getBlockColors().getColor(blockState, null, null, tintIndex);
		}, ModBlocks.AYLYTH_BUSH);
		FabricModelPredicateProviderRegistry.register(ModItems.SHUCKED_YMPE_FRUIT, new Identifier(Aylyth.MOD_ID, "variant"), (stack, world, entity, seed) -> stack.hasNbt() && stack.getNbt().contains("StoredEntity") ? 1 : 0);
		EntityRendererRegistry.register(ModEntityTypes.AYLYTHIAN, AylythianEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ELDER_AYLYTHIAN, ElderAylythianEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlocks.SEEP_BLOCK_ENTITY_TYPE, SeepBlockEntityRenderer::new);
		TerraformBoatClientHelper.registerModelLayer(new Identifier(Aylyth.MOD_ID, "ympe"));
	}
}
