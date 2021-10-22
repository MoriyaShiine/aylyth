package moriyashiine.aylyth.common.registry;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.opengl.GL11;

public class ModParticles {
	public static final DefaultParticleType PILOT_LIGHT = FabricParticleTypes.simple(true);
	public static final DefaultParticleType AMBIENT_PILOT_LIGHT = FabricParticleTypes.simple(true);
	
	public static void init() {
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(Aylyth.MOD_ID, "pilot_light"), PILOT_LIGHT);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(Aylyth.MOD_ID, "ambient_pilot_light"), AMBIENT_PILOT_LIGHT);
	}
	
	@Environment(EnvType.CLIENT)
	public static class ParticleTextureSheets {
		public static final ParticleTextureSheet GLOWING = new ParticleTextureSheet() {
			@Override
			public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
				MinecraftClient.getInstance().gameRenderer.getLightmapTextureManager().enable();
				RenderSystem.enableDepthTest();
				RenderSystem.depthMask(false);
				RenderSystem.enableBlend();
				RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
				RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
				AbstractTexture tex = textureManager.getTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
				tex.setFilter(true, false);
				bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
			}
			
			@Override
			public void draw(Tessellator tessellator) {
				AbstractTexture tex = MinecraftClient.getInstance().getTextureManager().getTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
				tex.setFilter(false, false);
				tessellator.draw();
				RenderSystem.disableBlend();
				RenderSystem.depthMask(true);
			}
			
			@Override
			public String toString() {
				return Aylyth.MOD_ID + ":glowing";
			}
		};
	}
}
