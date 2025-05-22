package moriyashiine.aylyth.client.render.entity.projectile;

import moriyashiine.aylyth.client.render.entity.state.SphereEntityRenderState;
import moriyashiine.aylyth.common.entity.types.projectile.SphereEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class SphereEntityRenderer extends EntityRenderer<SphereEntity, SphereEntityRenderState> {
    public SphereEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public SphereEntityRenderState createRenderState() {
        return new SphereEntityRenderState();
    }
}
