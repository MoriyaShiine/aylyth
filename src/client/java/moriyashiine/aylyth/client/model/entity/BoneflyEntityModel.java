package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.types.mob.BoneflyEntity;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BoneflyEntityModel extends DefaultedEntityGeoModel<BoneflyEntity> {
    public BoneflyEntityModel() {
        super(AylythUtil.id("living/bonefly"));
    }
}
