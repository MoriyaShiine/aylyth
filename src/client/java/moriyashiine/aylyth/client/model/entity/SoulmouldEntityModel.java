package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.SoulmouldEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SoulmouldEntityModel extends DefaultedEntityGeoModel<SoulmouldEntity> {
    public SoulmouldEntityModel() {
        super(Aylyth.id("living/ympemould"), true);
    }
}