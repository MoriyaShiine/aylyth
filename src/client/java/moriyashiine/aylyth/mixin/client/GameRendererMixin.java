package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow
	private float viewDistance;

	@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;getFov(Lnet/minecraft/client/render/Camera;FZ)D"))
	private void decreaseAylythRenderDistance(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
		if (AylythDimensionRenderer.goalFogStrength > 0 && !Aylyth.isDebugMode()) {
			viewDistance = 12;
		}
	}
}
