package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.render.entity.living.TulpaEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasHolder;
import net.minecraft.client.texture.SpriteLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpriteAtlasHolder.class)
public class SpriteAtlasHolderMixin {

    @Inject(method = "afterReload", at = @At("TAIL"))
    private void reloadTulpaPlayerTextures(SpriteLoader.StitchResult stitchResult, CallbackInfo ci) {
        TulpaEntityRenderer.TEXTURE_CACHE.forEach((gameProfile, stoneTexture) -> stoneTexture.needsUpdate = true);
    }
}
