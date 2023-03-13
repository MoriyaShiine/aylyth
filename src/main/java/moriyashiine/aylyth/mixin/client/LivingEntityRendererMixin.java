package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.render.entity.living.feature.YmpeThornRingFeature;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
	@Shadow protected abstract boolean addFeature(FeatureRenderer<T, M> feature);

	protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) { super(ctx); }

	@Inject(method = "<init>", at = @At("TAIL"))
	private void aylyth_addYmpeThornRingFeature(EntityRendererFactory.Context ctx, M model, float shadowRadius, CallbackInfo info) {
		addFeature((FeatureRenderer<T, M>) new YmpeThornRingFeature((LivingEntityRenderer) (Object) this, ctx.getModelLoader()));
	}
}
