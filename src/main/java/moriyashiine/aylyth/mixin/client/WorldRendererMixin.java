package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@Shadow
	private ClientWorld world;
	@Shadow
	@Final
	private MinecraftClient client;
	
	@Shadow
	@Nullable
	private VertexBuffer lightSkyBuffer;
	
	@Shadow
	@Nullable
	private VertexBuffer starsBuffer;
	
	@Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER, ordinal = 0))
	private void renderyAylythSky(MatrixStack matrices, Matrix4f matrix4f, float f, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
		if (world.getDimensionEffects() == AylythDimensionRenderer.DIMENSION_EFFECTS) {
			AylythDimensionRenderer.renderSky(client, world, lightSkyBuffer, starsBuffer, matrices, matrix4f, f, runnable);
		}
	}
}
