package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.render.entity.living.feature.CuirassFeatureRenderer;
import moriyashiine.aylyth.client.render.entity.living.feature.YmpeInfestationFeature;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
	public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}
	
	@Inject(method = "<init>", at = @At("TAIL"))
	private void aylyth_addYmpeInfestationFeature(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
		addFeature(new YmpeInfestationFeature(this, ctx.getModelLoader(), slim));
		addFeature(new CuirassFeatureRenderer(this, ctx.getModelLoader(), slim));
	}
}
