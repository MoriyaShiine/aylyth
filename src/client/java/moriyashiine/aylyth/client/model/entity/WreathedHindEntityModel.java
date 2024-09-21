package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class WreathedHindEntityModel extends DefaultedEntityGeoModel<WreathedHindEntity> {

    public WreathedHindEntityModel() {
        super(Aylyth.id("living/wreathed_hind"));
    }
}
