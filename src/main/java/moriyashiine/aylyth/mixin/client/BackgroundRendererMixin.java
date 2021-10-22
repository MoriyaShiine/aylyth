package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.renderer.AylythDimensionRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
@Environment(EnvType.CLIENT)
public class BackgroundRendererMixin {
	@Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
	private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
		if (AylythDimensionRenderer.goalFogStrength > 0) {
			AylythDimensionRenderer.renderFog();
			ci.cancel();
		}
	}
}
