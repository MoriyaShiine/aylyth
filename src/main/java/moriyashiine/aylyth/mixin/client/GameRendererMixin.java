package moriyashiine.aylyth.mixin.client;

import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.client.render.RenderTypes;
import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import moriyashiine.aylyth.client.render.block.entity.SeepBlockEntityRenderer;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderStage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

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
	
	@Inject(method = "loadPrograms", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	private void aylyth_loadShaders(ResourceFactory manager, CallbackInfo ci, List<ShaderStage> list, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> list2) throws IOException {
		list2.add(Pair.of(new ShaderProgram(manager, "rendertype_seep", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL), (shader) -> SeepBlockEntityRenderer.renderTypeSeepShader = shader));
		list2.add(Pair.of(new ShaderProgram(manager, "rendertype_tint", VertexFormats.POSITION_TEXTURE), shader -> RenderTypes.renderlayer_tint = shader));
	}
}
