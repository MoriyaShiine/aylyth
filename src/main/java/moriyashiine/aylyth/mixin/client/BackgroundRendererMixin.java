package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = BackgroundRenderer.class, priority = 1050)
public class BackgroundRendererMixin {
	@Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
	private static void applyAylythFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
		if (AylythDimensionRenderer.goalFogStrength > 0 && !Aylyth.isDebugMode()) {
			AylythDimensionRenderer.renderFog(camera, fogType, viewDistance, thickFog, tickDelta);
			ci.cancel();
		}
	}
}
