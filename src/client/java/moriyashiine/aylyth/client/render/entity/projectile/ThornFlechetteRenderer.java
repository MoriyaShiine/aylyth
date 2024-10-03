package moriyashiine.aylyth.client.render.entity.projectile;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.projectile.ThornFlechetteEntity;
import moriyashiine.aylyth.common.item.AylythItems;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class ThornFlechetteRenderer extends ProjectileEntityRenderer<ThornFlechetteEntity> {
    private static final Identifier THORN_FLECHETTE = Aylyth.id("textures/entity/thorn_flechette.png");
    private static final Identifier BLIGHTED_THORN_FLECHETTE = Aylyth.id("textures/entity/blighted_thorn_flechette.png");

    public ThornFlechetteRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ThornFlechetteEntity entity) {
        return entity.asItemStack().getItem() == AylythItems.THORN_FLECHETTE ? THORN_FLECHETTE : BLIGHTED_THORN_FLECHETTE;
    }
}
