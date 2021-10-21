package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.renderer.AylythDimensionRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
@Environment(EnvType.CLIENT)
public class WorldRendererMixin {
	@Shadow private ClientWorld world;
	@Shadow @Final private MinecraftClient client;

	@Inject(method = "renderSky", at = @At("HEAD"))
	private void renderSkyPre(MatrixStack matrices, Matrix4f matrix4f, float f, Runnable runnable, CallbackInfo ci) {
		if (world.getTime() % 20 == 0) {
			AylythDimensionRenderer.determineConditions(world, world.getBiome(client.player.getBlockPos()));
		}
	}
}
