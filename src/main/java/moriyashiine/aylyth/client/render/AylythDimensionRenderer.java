package moriyashiine.aylyth.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModBiomes;
import moriyashiine.aylyth.common.registry.ModDimensions;
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
import net.minecraft.world.biome.Biome;

@Environment(EnvType.CLIENT)
public class AylythDimensionRenderer {
	public static final SkyProperties SKY_PROPERTIES = new AylythSkyProperties();
	public static final Identifier SUN = new Identifier(Aylyth.MOD_ID, "textures/environment/sun.png");
	public static final Identifier MOON = new Identifier(Aylyth.MOD_ID, "textures/environment/moon.png");
	public static int goalFogStrength = 0;
	private static float currentFogStrength;
	
	public static void renderFog() {
		RenderSystem.setShaderFogStart(0F);
		RenderSystem.setShaderFogEnd(currentFogStrength);
		if (goalFogStrength < currentFogStrength) {
			currentFogStrength -= 0.1F;
		}
		else if (goalFogStrength > currentFogStrength) {
			currentFogStrength += 0.1F;
		}
	}
	
	public static void renderSky(MinecraftClient client, ClientWorld world, VertexBuffer lightSkyBuffer, VertexBuffer starsBuffer, MatrixStack matrices, Matrix4f matrix4f, float ticks, Runnable fogHandler) {
		RenderSystem.disableTexture();
		Vec3d vec3d = world.method_23777(client.gameRenderer.getCamera().getPos(), ticks);
		float skyRed = (float) vec3d.x;
		float skyGreen = (float) vec3d.y;
		float skyBlue = (float) vec3d.z;
		BackgroundRenderer.setFogBlack();
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.depthMask(false);
		RenderSystem.setShaderColor(skyRed, skyGreen, skyBlue, 0.0F);
		Shader shader = RenderSystem.getShader();
		lightSkyBuffer.setShader(matrices.peek().getModel(), matrix4f, shader);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableTexture();
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
		matrices.push();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(world.getSkyAngle(ticks) * 360.0F));
		Matrix4f matrix4f3 = matrices.peek().getModel();
		float celestialSize = 60.0F;
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, SUN);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(matrix4f3, -celestialSize, 100.0F, -celestialSize).texture(0.0F, 0.0F).next();
		bufferBuilder.vertex(matrix4f3, celestialSize, 100.0F, -celestialSize).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(matrix4f3, celestialSize, 100.0F, celestialSize).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(matrix4f3, -celestialSize, 100.0F, celestialSize).texture(0.0F, 1.0F).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
		RenderSystem.setShaderTexture(0, MOON);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(matrix4f3, -celestialSize, -100.0F, celestialSize).texture(0.0F, 0.0F).next();
		bufferBuilder.vertex(matrix4f3, celestialSize, -100.0F, celestialSize).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(matrix4f3, celestialSize, -100.0F, -celestialSize).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(matrix4f3, -celestialSize, -100.0F, -celestialSize).texture(0.0F, 1.0F).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
		RenderSystem.disableTexture();
		float starPower = world.method_23787(ticks);
		if (starPower > 0.0F) {
			RenderSystem.setShaderColor(starPower, starPower, starPower, starPower);
			BackgroundRenderer.method_23792();
			starsBuffer.setShader(matrices.peek().getModel(), matrix4f, GameRenderer.getPositionShader());
			fogHandler.run();
		}
		RenderSystem.disableBlend();
		matrices.pop();
		RenderSystem.disableTexture();
		RenderSystem.setShaderColor(skyRed, skyGreen, skyBlue, 1.0F);
		RenderSystem.enableTexture();
		RenderSystem.depthMask(true);
	}
	
	public static void determineConditions(ClientWorld world, Biome biome) {
		if (world.getRegistryKey() == ModDimensions.AYLYTH) {
			Identifier biomeId = world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome);
			if (biomeId == ModBiomes.CLEARING_ID) {
				goalFogStrength = 40;
			}
			else if (biomeId == ModBiomes.OVERGROWN_CLEARING_ID) {
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
	
	private static class AylythSkyProperties extends SkyProperties {
		public AylythSkyProperties() {
			super(0, false, SkyType.NONE, false, true);
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
