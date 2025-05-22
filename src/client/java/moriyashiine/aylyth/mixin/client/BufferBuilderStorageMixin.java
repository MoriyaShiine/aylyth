package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.client.render.AylythRenderLayers;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.BufferAllocator;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.SequencedMap;

// TODO: Remove debug
@Debug(export = true)
@Mixin(BufferBuilderStorage.class)
public class BufferBuilderStorageMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider;immediate(Ljava/util/SequencedMap;Lnet/minecraft/client/util/BufferAllocator;)Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;"))
    private void aylyth_assignBufferBuilder(int maxBlockBuildersPoolSize, CallbackInfo ci, @Local(ordinal = 0) SequencedMap<RenderLayer, BufferAllocator> sequencedMap) {
        sequencedMap.put(AylythRenderLayers.TINT, new BufferAllocator(AylythRenderLayers.TINT.getExpectedBufferSize()));
    }
}
