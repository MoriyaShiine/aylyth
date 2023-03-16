package moriyashiine.aylyth.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModBiomeKeys;
import moriyashiine.aylyth.common.registry.ModDimensionKeys;
import moriyashiine.aylyth.mixin.client.WorldRendererAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.tag.BiomeTags;
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
	public static final DimensionRenderingRegistry.SkyRenderer SKY_RENDERER = new AylythSkyRenderer();
	public static final Identifier SUN = new Identifier(Aylyth.MOD_ID, "textures/environment/sun.png");
	public static final Identifier MOON = new Identifier(Aylyth.MOD_ID, "textures/environment/moon.png");
	public static int goalFogStrength = 0;
	private static float currentFogStrength;

	public static void renderFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta) {
		if (!Aylyth.isDebugMode()) {
			float fogStrength = currentFogStrength;
			if (camera.getSubmersionType().equals(CameraSubmersionType.WATER)) {
				ClientPlayerEntity player = MinecraftClient.getInstance().player;
				ClientWorld world = MinecraftClient.getInstance().world;
				if (world.getBiome(player.getBlockPos()).isIn(BiomeTags.HAS_CLOSER_WATER_FOG)) {
					fogStrength *= 0.75;
				}
			}
			RenderSystem.setShaderFogStart(0F);
			RenderSystem.setShaderFogEnd(fogStrength);
			if (goalFogStrength < currentFogStrength) {
				currentFogStrength -= 0.1F;
			} else if (goalFogStrength > currentFogStrength) {
				currentFogStrength += 0.1F;
			}
		}
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

	private static class AylythSkyRenderer implements DimensionRenderingRegistry.SkyRenderer {

		@Override
		public void render(WorldRenderContext context) {
			ClientWorld world = context.world();
			Camera camera = context.camera();
			float tickDelta = context.tickDelta();
			VertexBuffer lightSkyBuffer = ((WorldRendererAccessor)context.worldRenderer()).getLightSkyBuffer();
			VertexBuffer starsBuffer = ((WorldRendererAccessor)context.worldRenderer()).getStarsBuffer();
			MatrixStack matrices = context.matrixStack();
			Matrix4f matrix4f = context.projectionMatrix();
			Runnable fogHandler = () -> BackgroundRenderer.applyFog(camera, BackgroundRenderer.FogType.FOG_SKY, context.gameRenderer().getViewDistance(), true, tickDelta);
			RenderSystem.disableTexture();
			Vec3d vec3d = world.getSkyColor(camera.getPos(), tickDelta);
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
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(world.getSkyAngle(tickDelta) * 360.0F));
			matrices.translate(0, -80, 0);
			Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
			float celestialSize = 13.0F;
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, SUN);
			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
			bufferBuilder.vertex(positionMatrix, -celestialSize, 100.0F, -celestialSize).texture(0.0F, 0.0F).next();
			bufferBuilder.vertex(positionMatrix, celestialSize, 100.0F, -celestialSize).texture(0.0F, 1.0F).next();
			bufferBuilder.vertex(positionMatrix, celestialSize, 100.0F, celestialSize).texture(1.0F, 1.0F).next();
			bufferBuilder.vertex(positionMatrix, -celestialSize, 100.0F, celestialSize).texture(1.0F, 0.0F).next();
			BufferRenderer.drawWithShader(bufferBuilder.end());
			matrices.translate(0, 160, 0);
			RenderSystem.setShaderTexture(0, MOON);
			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
			bufferBuilder.vertex(positionMatrix, -celestialSize, -100.0F, celestialSize).texture(0.0F, 0.0F).next();
			bufferBuilder.vertex(positionMatrix, celestialSize, -100.0F, celestialSize).texture(0.0F, 1.0F).next();
			bufferBuilder.vertex(positionMatrix, celestialSize, -100.0F, -celestialSize).texture(1.0F, 1.0F).next();
			bufferBuilder.vertex(positionMatrix, -celestialSize, -100.0F, -celestialSize).texture(1.0F, 0.0F).next();
			BufferRenderer.drawWithShader(bufferBuilder.end());
			RenderSystem.disableTexture();
			matrices.pop();
			float starPower = world.method_23787(tickDelta);
			if (starPower > 0.0F) {
				matrices.push();
				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
				matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(world.getSkyAngle(tickDelta) * 360.0F));
				matrices.scale(0.1f, 0.1f, 0.1f);
				RenderSystem.setShaderColor(starPower, starPower, starPower, starPower);
				BackgroundRenderer.clearFog();
				starsBuffer.bind();
				starsBuffer.draw(matrices.peek().getPositionMatrix(), matrix4f, GameRenderer.getPositionShader());
				VertexBuffer.unbind();
				fogHandler.run();
				matrices.pop();
			}
			RenderSystem.disableBlend();
			RenderSystem.disableTexture();
			RenderSystem.setShaderColor(skyRed, skyGreen, skyBlue, 1.0F);
			RenderSystem.enableTexture();
			RenderSystem.depthMask(true);
		}
	}
}
