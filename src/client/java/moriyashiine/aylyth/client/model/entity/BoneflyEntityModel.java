package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.BoneflyEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BoneflyEntityModel extends DefaultedEntityGeoModel<BoneflyEntity> {
    public BoneflyEntityModel() {
        super(Aylyth.id("living/bonefly"));
    }
}
