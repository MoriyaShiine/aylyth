package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.AylythianEntity;
import moriyashiine.aylyth.common.entity.mob.ElderAylythianEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ElderAylythianEntityModel extends GeoModel<ElderAylythianEntity> {
	private static final Identifier MODEL_LOCATION = new Identifier(Aylyth.MOD_ID, "geo/elder_aylythian.geo.json");
	private static Identifier[] TEXTURE_LOCATIONS;
	private static final Identifier ANIMATION_FILE_LOCATION = new Identifier(Aylyth.MOD_ID, "animations/entity/elder_aylythian.animation.json");
	
	@Override
	public Identifier getModelResource(ElderAylythianEntity object) {
		return MODEL_LOCATION;
	}
	
	@Override
	public Identifier getTextureResource(ElderAylythianEntity object) {
		if (TEXTURE_LOCATIONS == null) {
			TEXTURE_LOCATIONS = new Identifier[ElderAylythianEntity.VARIANTS];
			for (int i = 0; i < ElderAylythianEntity.VARIANTS; i++) {
				TEXTURE_LOCATIONS[i] = new Identifier(Aylyth.MOD_ID, "textures/entity/living/elder_aylythian/" + i + ".png");
			}
		}
		return TEXTURE_LOCATIONS[object.getDataTracker().get(ElderAylythianEntity.VARIANT)];
	}
	
	@Override
	public Identifier getAnimationResource(ElderAylythianEntity animatable) {
		return ANIMATION_FILE_LOCATION;
	}
	
	@Override
	public void setCustomAnimations(ElderAylythianEntity entity, long uniqueID, AnimationState<ElderAylythianEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		var head = this.getAnimationProcessor().getBone("head");
		var extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (head != null) {
			head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
			head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
		}
	}
}
