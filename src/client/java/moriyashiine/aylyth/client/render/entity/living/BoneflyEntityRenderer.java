package moriyashiine.aylyth.client.render.entity.living;


import moriyashiine.aylyth.client.model.entity.BoneflyEntityModel;
import moriyashiine.aylyth.common.entity.type.mob.BoneflyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BoneflyEntityRenderer extends GeoEntityRenderer<BoneflyEntity> {
    public BoneflyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BoneflyEntityModel());
    }

    @Override
    protected float getDeathMaxRotation(BoneflyEntity entityLivingBaseIn) {
        return 0.0F;
    }
}