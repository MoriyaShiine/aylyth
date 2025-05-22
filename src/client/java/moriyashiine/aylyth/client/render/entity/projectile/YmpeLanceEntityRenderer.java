package moriyashiine.aylyth.client.render.entity.projectile;

import moriyashiine.aylyth.client.render.entity.state.YmpeLanceEntityRenderState;
import moriyashiine.aylyth.common.entity.types.projectile.YmpeLanceEntity;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.RotationAxis;

public class YmpeLanceEntityRenderer extends EntityRenderer<YmpeLanceEntity, YmpeLanceEntityRenderState> {
	private final ItemModelManager itemModelManager;

	public YmpeLanceEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.itemModelManager = context.getItemModelManager();
	}

	@Override
	public void updateRenderState(YmpeLanceEntity entity, YmpeLanceEntityRenderState state, float tickDelta) {
		super.updateRenderState(entity, state, tickDelta);
		// TODO: Probably switch this from being a passenger of the attached entity to a data attachment and a feature renderer.
		if (entity.getVehicle() != null) {
			state.yOffset = -(entity.getY() - entity.getVehicle().getY()) + (entity.getVehicle().getHeight() * 0.5);
			state.yaw = 270 - entity.getVehicle().getBodyYaw();
			state.pitch = 150;
		} else {
			state.yOffset = 0;
			state.yaw = entity.getLerpedYaw(tickDelta) - 90;
			state.pitch = entity.getLerpedPitch(tickDelta) + 315;
		}
		if (!entity.asItemStack().isEmpty()) {
			itemModelManager.updateForNonLivingEntity(state.item, entity.asItemStack(), ModelTransformationMode.NONE, entity);
		}
	}

	@Override
	public void render(YmpeLanceEntityRenderState renderState, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrices.push();

		if (renderState.yOffset != 0) {
			matrices.translate(0, renderState.yOffset, 0);
		}
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(renderState.yaw));
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(renderState.pitch));

		matrices.scale(2F, 2F, 1F);
		renderState.item.render(matrices, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV);
		matrices.pop();
	}

	@Override
	public YmpeLanceEntityRenderState createRenderState() {
		return new YmpeLanceEntityRenderState();
	}
}
