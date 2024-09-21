package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.AylythianEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class AylythianEntityModel extends DefaultedEntityGeoModel<AylythianEntity> {
	public AylythianEntityModel() {
		super(Aylyth.id("living/aylythian"), true);
	}
}
