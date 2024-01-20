package moriyashiine.aylyth.common.asm;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("removal")
public class AylythNoiseGrassColorMod extends GrassColorModifierMixin {
    @Override
    public int getModifiedGrassColor(double x, double z, int color) {
        double diff = Biome.FOLIAGE_NOISE.sample(x * 0.0225, z * 0.0225, false);
        return diff < 0.1 ? color - 0x001000 : color;
    }
}

@Mixin(BiomeEffects.GrassColorModifier.class)
abstract class GrassColorModifierMixin {
    @Shadow
    public abstract int getModifiedGrassColor(double x, double z, int color);
}