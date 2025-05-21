package moriyashiine.aylyth.mixin.client;

import net.minecraft.client.render.Fog;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SkyRendering.class)
public interface SkyRenderingAccessor {
    @Invoker
    void invokeRenderStars(Fog fog, float color, MatrixStack matrices);
}
