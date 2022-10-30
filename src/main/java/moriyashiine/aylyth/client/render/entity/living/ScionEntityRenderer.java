package moriyashiine.aylyth.client.render.entity.living;

import moriyashiine.aylyth.client.model.entity.ScionEntityModel;
import moriyashiine.aylyth.client.render.entity.living.feature.ScionFeatureRenderer;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import net.minecraft.client.render.entity.*;
import net.minecraft.util.Identifier;

public class ScionEntityRenderer extends BipedEntityRenderer<ScionEntity, ScionEntityModel<ScionEntity>> {

    public ScionEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ScionEntityModel<>(ctx.getPart(ScionEntityModel.LAYER_LOCATION)), 0.5f);
        addFeature(new ScionFeatureRenderer(this, ctx.getModelLoader()));
    }

    @Override
    public Identifier getTexture(ScionEntity mobEntity) {
        return new Identifier(Aylyth.MOD_ID, "textures/entity/living/scion/scion.png");
    }


}
