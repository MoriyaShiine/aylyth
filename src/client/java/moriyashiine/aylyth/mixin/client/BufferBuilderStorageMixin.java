package moriyashiine.aylyth.mixin.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import moriyashiine.aylyth.client.render.AylythRenderLayers;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferBuilderStorage.class)
public class BufferBuilderStorageMixin {

    @Inject(method = "method_22999", at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"))
    private void aylyth_assignBufferBuilder(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferBuilder> map, CallbackInfo ci) {
        map.put(AylythRenderLayers.TINT, new BufferBuilder(AylythRenderLayers.TINT.getExpectedBufferSize()));
    }
}
