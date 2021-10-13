package moriyashiine.aylyth.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import moriyashiine.aylyth.client.network.packet.SpawnShuckParticlesPacket;
import moriyashiine.aylyth.client.renderer.entity.living.AylythianEntityRenderer;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AylythClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(SpawnShuckParticlesPacket.ID, SpawnShuckParticlesPacket::receive);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.YMPE_SIGN.getTexture()));
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.YMPE_SAPLING, ModBlocks.POTTED_YMPE_SAPLING, ModBlocks.YMPE_DOOR, ModBlocks.YMPE_TRAPDOOR);
		FabricModelPredicateProviderRegistry.register(ModItems.SHUCKED_YMPE_FRUIT, new Identifier(Aylyth.MOD_ID, "variant"), (stack, world, entity, seed) -> stack.hasNbt() && stack.getNbt().contains("StoredEntity") ? 1 : 0);
		EntityRendererRegistry.register(ModEntityTypes.AYLYTHIAN, AylythianEntityRenderer::new);
		TerraformBoatClientHelper.registerModelLayer(new Identifier(Aylyth.MOD_ID, "ympe"));
	}
}
