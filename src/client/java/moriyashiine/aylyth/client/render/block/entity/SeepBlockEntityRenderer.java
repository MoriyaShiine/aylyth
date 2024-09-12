package moriyashiine.aylyth.client.render.block.entity;

import moriyashiine.aylyth.client.integration.iris.IrisCompat;
import moriyashiine.aylyth.client.render.AylythRenderLayers;
import moriyashiine.aylyth.common.block.entity.type.SeepBlockEntity;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class SeepBlockEntityRenderer implements BlockEntityRenderer<SeepBlockEntity> {
	public SeepBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
	}
	
	@Override
	public void render(SeepBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		MatrixStack.Entry matrix = matrices.peek();
		VertexConsumer vertexConsumer;
		if (!FabricLoader.getInstance().isModLoaded("iris") || !IrisCompat.isShaderPackInUse()) {
			vertexConsumer = vertexConsumers.getBuffer(AylythRenderLayers.SEEP);
			this.renderSide(matrix.getPositionMatrix(), vertexConsumer, 0.0625F, 0.9375F, 0, 1, 0.9375F, 0.9375F);
			this.renderSide(matrix.getPositionMatrix(), vertexConsumer, 0.0625F, 0.9375F, 1, 0, 0.0625F, 0.0625F);
			this.renderSide(matrix.getPositionMatrix(), vertexConsumer, 0.9375F, 0.9375F, 1, 0, 0.0625F, 0.9375F);
			this.renderSide(matrix.getPositionMatrix(), vertexConsumer, 0.0625F, 0.0625F, 0, 1, 0.0625F, 0.9375F);
		} else {
			vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(AylythUtil.id("textures/environment/seep_overlay.png")));
			this.renderCompatSide(matrix, vertexConsumer, 0.0625F, 0.9375F, 0, 1, 0.9375F, 0.9375F);
			this.renderCompatSide(matrix, vertexConsumer, 0.0625F, 0.9375F, 1, 0, 0.0625F, 0.0625F);
			this.renderCompatSide(matrix, vertexConsumer, 0.9375F, 0.9375F, 1, 0, 0.0625F, 0.9375F);
			this.renderCompatSide(matrix, vertexConsumer, 0.0625F, 0.0625F, 0, 1, 0.0625F, 0.9375F);
		}
	}

	private void renderCompatSide(MatrixStack.Entry entry, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2) {
		vertices.vertex(entry.getPositionMatrix(), x1, y1, z1).color(1, 1, 1, 1f).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(entry.getNormalMatrix(), 0, 0, 0).next();
		vertices.vertex(entry.getPositionMatrix(), x2, y1, z2).color(1, 1, 1, 1f).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(entry.getNormalMatrix(), 0, 0, 0).next();
		vertices.vertex(entry.getPositionMatrix(), x2, y2, z2).color(1, 1, 1, 1f).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(entry.getNormalMatrix(), 0, 0, 0).next();
		vertices.vertex(entry.getPositionMatrix(), x1, y2, z1).color(1, 1, 1, 1f).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(entry.getNormalMatrix(), 0, 0, 0).next();
	}

	private void renderSide(Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2) {
		vertices.vertex(model, x1, y1, z1).next();
		vertices.vertex(model, x2, y1, z2).next();
		vertices.vertex(model, x2, y2, z2).next();
		vertices.vertex(model, x1, y2, z1).next();
	}
}
