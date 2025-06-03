package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.ElderAylythianEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class ElderAylythianEntityModel extends GeoModel<ElderAylythianEntity> {
	private static final Identifier MODEL_LOCATION = Aylyth.id("geo/entity/living/elder_aylythian.geo.json");
	private static Identifier[] TEXTURE_LOCATIONS;
	private static final Identifier ANIMATION_FILE_LOCATION = Aylyth.id("animations/entity/living/elder_aylythian.animation.json");
	
	@Override
	public Identifier getModelResource(ElderAylythianEntity animatable, @Nullable GeoRenderer<ElderAylythianEntity> renderer) {
		return MODEL_LOCATION;
	}

	@Override
	public Identifier getTextureResource(ElderAylythianEntity animatable, @Nullable GeoRenderer<ElderAylythianEntity> renderer) {
		if (TEXTURE_LOCATIONS == null) {
			TEXTURE_LOCATIONS = new Identifier[ElderAylythianEntity.VARIANTS];
			for (int i = 0; i < ElderAylythianEntity.VARIANTS; i++) {
				TEXTURE_LOCATIONS[i] = Aylyth.id("textures/entity/living/elder_aylythian/" + i + ".png");
			}
		}
		return TEXTURE_LOCATIONS[animatable.getDataTracker().get(ElderAylythianEntity.VARIANT)];
	}

	@Override
	public Identifier getAnimationResource(ElderAylythianEntity animatable) {
		return ANIMATION_FILE_LOCATION;
	}
	
	@Override
	public void setCustomAnimations(ElderAylythianEntity entity, long uniqueID, AnimationState<ElderAylythianEntity> animationState) {
		super.setCustomAnimations(entity, uniqueID, animationState);
		var head = this.getAnimationProcessor().getBone("head");
		var extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
		if (head != null) {
			head.setRotX(extraData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(extraData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}
}
