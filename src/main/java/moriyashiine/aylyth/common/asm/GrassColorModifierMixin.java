package moriyashiine.aylyth.common.asm;

import net.minecraft.world.biome.BiomeEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BiomeEffects.GrassColorModifier.class)
public abstract class GrassColorModifierMixin {
    @Shadow
    public abstract int getModifiedGrassColor(double x, double z, int color);
}
