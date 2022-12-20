package moriyashiine.aylyth.client.render.entity.projectile;

import moriyashiine.aylyth.common.entity.projectile.SphereEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

public class SphereEntityRenderer extends EntityRenderer<SphereEntity> {

    public SphereEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SphereEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
