package moriyashiine.aylyth.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModBiomeKeys;
import moriyashiine.aylyth.datagen.worldgen.biomes.ModBiomes;
import moriyashiine.aylyth.common.registry.ModDimensionKeys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

@Environment(EnvType.CLIENT)
public class AylythDimensionRenderer {
	public static final DimensionEffects DIMENSION_EFFECTS = new AylythDimensionEffects();
	public static final Identifier SUN = new Identifier(Aylyth.MOD_ID, "textures/environment/sun.png");
	public static final Identifier MOON = new Identifier(Aylyth.MOD_ID, "textures/environment/moon.png");
	public static int goalFogStrength = 0;
	private static float currentFogStrength;

	public static void renderFog() {
		if (!Aylyth.isDebugMode()) {
			RenderSystem.setShaderFogStart(0F);
			RenderSystem.setShaderFogEnd(currentFogStrength);
			if (goalFogStrength < currentFogStrength) {
				currentFogStrength -= 0.1F;
			} else if (goalFogStrength > currentFogStrength) {
				currentFogStrength += 0.1F;
			}
		}
	}
	
	public static void renderSky(MinecraftClient client, ClientWorld world, VertexBuffer lightSkyBuffer, VertexBuffer starsBuffer, MatrixStack matrices, Matrix4f matrix4f, float ticks, Runnable fogHandler) {
			RenderSystem.disableTexture();
			Vec3d vec3d = world.getSkyColor(client.gameRenderer.getCamera().getPos(), ticks);
			float skyRed = (float) vec3d.x;
			float skyGreen = (float) vec3d.y;
			float skyBlue = (float) vec3d.z;
			BackgroundRenderer.setFogBlack();
			BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
			RenderSystem.depthMask(false);
			RenderSystem.setShaderColor(skyRed, skyGreen, skyBlue, 0.0F);
			Shader shader = RenderSystem.getShader();
			lightSkyBuffer.bind();
			lightSkyBuffer.draw(matrices.peek().getPositionMatrix(), matrix4f, shader);
			VertexBuffer.unbind();
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableTexture();
			RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			matrices.push();
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(world.getSkyAngle(ticks) * 360.0F));
			Matrix4f matrix4f3 = matrices.peek().getPositionMatrix();
			float celestialSize = 60.0F;
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, SUN);
			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
			bufferBuilder.vertex(matrix4f3, -celestialSize, 100.0F, -celestialSize).texture(0.0F, 0.0F).next();
			bufferBuilder.vertex(matrix4f3, celestialSize, 100.0F, -celestialSize).texture(0.0F, 1.0F).next();
			bufferBuilder.vertex(matrix4f3, celestialSize, 100.0F, celestialSize).texture(1.0F, 1.0F).next();
			bufferBuilder.vertex(matrix4f3, -celestialSize, 100.0F, celestialSize).texture(1.0F, 0.0F).next();
			BufferRenderer.drawWithShader(bufferBuilder.end());
			RenderSystem.setShaderTexture(0, MOON);
			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
			bufferBuilder.vertex(matrix4f3, -celestialSize, -100.0F, celestialSize).texture(0.0F, 0.0F).next();
			bufferBuilder.vertex(matrix4f3, celestialSize, -100.0F, celestialSize).texture(0.0F, 1.0F).next();
			bufferBuilder.vertex(matrix4f3, celestialSize, -100.0F, -celestialSize).texture(1.0F, 1.0F).next();
			bufferBuilder.vertex(matrix4f3, -celestialSize, -100.0F, -celestialSize).texture(1.0F, 0.0F).next();
			BufferRenderer.drawWithShader(bufferBuilder.end());
			RenderSystem.disableTexture();
			float starPower = world.method_23787(ticks);
			if (starPower > 0.0F) {
				RenderSystem.setShaderColor(starPower, starPower, starPower, starPower);
				BackgroundRenderer.clearFog();
				starsBuffer.bind();
				starsBuffer.draw(matrices.peek().getPositionMatrix(), matrix4f, GameRenderer.getPositionShader());
				VertexBuffer.unbind();
				fogHandler.run();
			}
			matrices.pop();
			RenderSystem.disableBlend();
			RenderSystem.disableTexture();
			RenderSystem.setShaderColor(skyRed, skyGreen, skyBlue, 1.0F);
			RenderSystem.enableTexture();
			RenderSystem.depthMask(true);
	}
	
	public static void determineConditions(ClientWorld world, RegistryEntry<Biome> biome) {
		if (world.getRegistryKey() == ModDimensionKeys.AYLYTH) {
			Identifier biomeId = world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome.value());
			if (biomeId == ModBiomeKeys.CLEARING_ID.getValue() || biomeId == ModBiomeKeys.UPLANDS_ID.getValue()) {
				goalFogStrength = 40;
			}
			else if (biomeId == ModBiomeKeys.OVERGROWN_CLEARING_ID.getValue()) {
				goalFogStrength = 24;
			}
			else {
				goalFogStrength = 16;
			}
		}
		else {
			goalFogStrength = 0;
		}
	}
	
	private static class AylythDimensionEffects extends DimensionEffects {
		public AylythDimensionEffects() {
			super(-70, false, SkyType.NONE, false, true);
		}
		
		@Override
		public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
			return color;
		}
		
		@Override
		public boolean useThickFog(int camX, int camY) {
			return true;
		}
	}
}
