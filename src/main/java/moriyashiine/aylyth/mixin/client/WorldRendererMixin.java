package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
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

	@Shadow @Nullable private VertexBuffer lightSkyBuffer;

	@Shadow @Nullable private VertexBuffer starsBuffer;

	@Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER))
	private void renderSkyPre(MatrixStack matrices, Matrix4f matrix4f, float f, Runnable runnable, CallbackInfo ci) {
		if (world.getTime() % 20 == 0) {
			AylythDimensionRenderer.determineConditions(world, world.getBiome(client.player.getBlockPos()));
		}

		if (world.getSkyProperties() == AylythDimensionRenderer.SKY_PROPERTIES) {
			AylythDimensionRenderer.renderSky(client, world, lightSkyBuffer, starsBuffer, matrices, matrix4f, f, runnable);
		}
	}
}
