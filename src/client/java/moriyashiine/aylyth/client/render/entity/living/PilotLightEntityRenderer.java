package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.render.entity.state.PilotLightEntityRenderState;
import moriyashiine.aylyth.common.entity.types.mob.PilotLightEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

public class PilotLightEntityRenderer extends EntityRenderer<PilotLightEntity, PilotLightEntityRenderState> {
	public PilotLightEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public boolean shouldRender(PilotLightEntity entity, Frustum frustum, double x, double y, double z) {
		return true;
	}

	@Override
	public PilotLightEntityRenderState createRenderState() {
		return new PilotLightEntityRenderState();
	}
}
