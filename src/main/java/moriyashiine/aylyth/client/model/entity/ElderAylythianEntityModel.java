package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.ElderAylythianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class ElderAylythianEntityModel extends AnimatedGeoModel<ElderAylythianEntity> {
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
	public void setCustomAnimations(ElderAylythianEntity entity, int uniqueID, AnimationEvent customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}
