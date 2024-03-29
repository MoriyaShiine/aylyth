package moriyashiine.aylyth.client.render.entity.projectile;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.projectile.ThornFlechetteEntity;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class ThornFlechetteRenderer extends ProjectileEntityRenderer<ThornFlechetteEntity> {
    private static final Identifier THORN_FLECHETTE = new Identifier(Aylyth.MOD_ID, "textures/entity/thorn_flechette.png");
    private static final Identifier BLIGHTED_THORN_FLECHETTE = new Identifier(Aylyth.MOD_ID, "textures/entity/blighted_thorn_flechette.png");

    public ThornFlechetteRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ThornFlechetteEntity entity) {
        return entity.asItemStack().getItem() == ModItems.THORN_FLECHETTE ? THORN_FLECHETTE : BLIGHTED_THORN_FLECHETTE;
    }
}
