package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FogShape;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BackgroundRenderer.class, priority = 1050)
public class BackgroundRendererMixin {
	@Shadow private static boolean fogEnabled;

	@Inject(method = "applyFog", at = @At("TAIL"), cancellable = true)
	private static void applyAylythFog(Camera camera, BackgroundRenderer.FogType fogType, Vector4f color, float viewDistance, boolean thickenFog, float tickDelta, CallbackInfoReturnable<Fog> cir) {
		if (fogEnabled && AylythDimensionRenderer.goalFogStrength > 0 && !Aylyth.isDebugMode()) {
			FogShape shape = cir.getReturnValue().shape();
			cir.setReturnValue(AylythDimensionRenderer.applyFog(camera, fogType, shape, color, viewDistance, thickenFog, tickDelta));
		}
	}
}
