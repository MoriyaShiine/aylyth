package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.common.entity.passive.PilotLightEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

public class PilotLightEntityRenderer extends EntityRenderer<PilotLightEntity> {
	public PilotLightEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}
	
	@Override
	public Identifier getTexture(PilotLightEntity entity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}
	
	@Override
	public boolean shouldRender(PilotLightEntity entity, Frustum frustum, double x, double y, double z) {
		return true;
	}
}
