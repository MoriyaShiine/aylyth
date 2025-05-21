package moriyashiine.aylyth.mixin.client;

import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {
    @Accessor
    SkyRendering getSkyRendering();
}
