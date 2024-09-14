package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.types.mob.SoulmouldEntity;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SoulmouldEntityModel extends DefaultedEntityGeoModel<SoulmouldEntity> {
    public SoulmouldEntityModel() {
        super(AylythUtil.id("living/ympemould"), true);
    }
}