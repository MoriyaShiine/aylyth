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

    @Inject(method = "assignBufferBuilder", at = @At("HEAD"))
    private static void aylyth_assignBufferBuilder(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferBuilder> builderStorage, RenderLayer layer, CallbackInfo ci) {
        if (!builderStorage.containsKey(AylythRenderLayers.TINT)) {
            builderStorage.put(AylythRenderLayers.TINT, new BufferBuilder(AylythRenderLayers.TINT.getExpectedBufferSize()));
        }
    }
}
