package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.AylythianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class AylythianEntityModel extends AnimatedGeoModel<AylythianEntity> {
	private static final Identifier MODEL_LOCATION = new Identifier(Aylyth.MOD_ID, "geo/aylythian.geo.json");
	private static final Identifier TEXTURE_LOCATION = new Identifier(Aylyth.MOD_ID, "textures/entity/aylythian/shell.png");
	private static final Identifier ANIMATION_FILE_LOCATION = new Identifier(Aylyth.MOD_ID, "animations/entity/aylythian.animation.json");
	
	@Override
	public Identifier getModelLocation(AylythianEntity object) {
		return MODEL_LOCATION;
	}
	
	@Override
	public Identifier getTextureLocation(AylythianEntity object) {
		return TEXTURE_LOCATION;
	}
	
	@Override
	public Identifier getAnimationFileLocation(AylythianEntity animatable) {
		return ANIMATION_FILE_LOCATION;
	}
	
	@Override
	public void setLivingAnimations(AylythianEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}
