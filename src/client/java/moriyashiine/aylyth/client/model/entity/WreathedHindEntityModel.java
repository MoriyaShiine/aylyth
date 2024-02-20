package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class WreathedHindEntityModel extends DefaultedEntityGeoModel<WreathedHindEntity> {

    public WreathedHindEntityModel() {
        super(AylythUtil.id("living/wreathed_hind"));
    }
}
