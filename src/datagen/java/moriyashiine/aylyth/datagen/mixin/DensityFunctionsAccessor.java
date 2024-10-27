package moriyashiine.aylyth.datagen.mixin;

import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DensityFunctions.class)
public interface DensityFunctionsAccessor {

    @Accessor("field_38250")
    static double getCheeseNoiseTarget() {
        throw new AssertionError("Implemented via mixin");
    }

    @Accessor("field_36617")
    static double getSurfaceDensityThreshold() {
        throw new AssertionError("Implemented via mixin");
    }

    @Invoker("createRidgesFoldedOverworldFunction")
    static DensityFunction createRidgesFoldedOverworldFunction(DensityFunction input) {
        throw new AssertionError("Implemented via mixin");
    }

    @Invoker("createInitialDensityFunction")
    static DensityFunction createInitialDensityFunction(DensityFunction factor, DensityFunction depth) {
        throw new AssertionError("Implemented via mixin");
    }

    @Invoker("applyBlendDensity")
    static DensityFunction applyBlendDensity(DensityFunction input) {
        throw new AssertionError("Implemented via mixin");
    }

    @Invoker("applyBlending")
    static DensityFunction applyBlending(DensityFunction function, DensityFunction blendOffset) {
        throw new AssertionError("Implemented via mixin");
    }

    @Invoker("applySlides")
    static DensityFunction applySlides(DensityFunction density, int minY, int maxY, int topRelativeMinY, int topRelativeMaxY, double topDensity, int bottomRelativeMinY, int bottomRelativeMaxY, double bottomDensity) {
        throw new AssertionError("Implemented via mixin");
    }

    @Invoker("applySurfaceSlides")
    static DensityFunction applySurfaceSlides(boolean amplified, DensityFunction density) {
        throw new AssertionError("Implemented via mixin");
    }

    @Invoker("verticalRangeChoice")
    static DensityFunction verticalRangeChoice(DensityFunction y, DensityFunction whenInRange, int minInclusive, int maxInclusive, int whenOutOfRange) {
        throw new AssertionError("Implemented via mixin");
    }
}
