package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.type.mob.AylythianEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class AylythianEntityModel extends DefaultedEntityGeoModel<AylythianEntity> {
	public AylythianEntityModel() {
		super(AylythUtil.id("living/aylythian"), true);
	}
}
