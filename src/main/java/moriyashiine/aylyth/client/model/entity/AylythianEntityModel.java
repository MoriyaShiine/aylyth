package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.AylythianEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class AylythianEntityModel extends GeoModel<AylythianEntity> {
	private static final Identifier MODEL_LOCATION = new Identifier(Aylyth.MOD_ID, "geo/aylythian.geo.json");
	private static final Identifier TEXTURE_LOCATION = new Identifier(Aylyth.MOD_ID, "textures/entity/living/aylythian.png");
	private static final Identifier ANIMATION_FILE_LOCATION = new Identifier(Aylyth.MOD_ID, "animations/entity/aylythian.animation.json");
	
	@Override
	public Identifier getModelResource(AylythianEntity object) {
		return MODEL_LOCATION;
	}
	
	@Override
	public Identifier getTextureResource(AylythianEntity object) {
		return TEXTURE_LOCATION;
	}
	
	@Override
	public Identifier getAnimationResource(AylythianEntity animatable) {
		return ANIMATION_FILE_LOCATION;
	}
	
	@Override
	public void setCustomAnimations(AylythianEntity entity, long uniqueID, AnimationState<AylythianEntity> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		var head = this.getAnimationProcessor().getBone("head");
		var modelData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (head != null) {
			head.setRotX(modelData.headPitch() * ((float) Math.PI / 180F));
			head.setRotY(modelData.netHeadYaw() * ((float) Math.PI / 180F));
		}
	}
}
