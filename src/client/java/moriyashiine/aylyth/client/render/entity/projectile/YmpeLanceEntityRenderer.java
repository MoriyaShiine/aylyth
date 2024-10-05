package moriyashiine.aylyth.client.render.entity.projectile;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.projectile.YmpeLanceEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class YmpeLanceEntityRenderer extends ProjectileEntityRenderer<YmpeLanceEntity> {
	private final MinecraftClient client = MinecraftClient.getInstance();

	public YmpeLanceEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public void render(YmpeLanceEntity lanceEntity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexProvider, int light) {
		matrices.push();

		if(lanceEntity.getVehicle() != null) {
			matrices.translate(0, -(lanceEntity.getY() - lanceEntity.getVehicle().getY()) + (lanceEntity.getVehicle().getHeight() * 0.5), 0);
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270 - lanceEntity.getVehicle().getBodyYaw()));
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(150));
		}
		else {
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, lanceEntity.prevYaw, lanceEntity.getYaw()) - 90));
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, lanceEntity.prevPitch, lanceEntity.getPitch()) + 315));
		}

		matrices.scale(2F, 2F, 1F);
		client.getItemRenderer().renderItem(lanceEntity.asItemStack(), ModelTransformationMode.NONE, light, OverlayTexture.DEFAULT_UV, matrices, vertexProvider, null, 0);
		matrices.pop();
	}

	@Override
	public Identifier getTexture(YmpeLanceEntity entity) {
		return Aylyth.id("textures/item/ympe_lance_handheld.png");
	}
}
