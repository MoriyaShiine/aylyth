package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class WreathedHindEntityModel extends DefaultedEntityGeoModel<WreathedHindEntity> {

    public WreathedHindEntityModel() {
        super(AylythUtil.id("living/wreathed_hind"));
    }
}
