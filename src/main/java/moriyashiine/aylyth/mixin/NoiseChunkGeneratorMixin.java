package moriyashiine.aylyth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.datagen.worldgen.terrain.AylythNoiseSettings;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin {

    @WrapOperation(method = "method_45511", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/NoiseChunkGenerator;createFluidLevelSampler(Lnet/minecraft/world/gen/chunk/ChunkGeneratorSettings;)Lnet/minecraft/world/gen/chunk/AquiferSampler$FluidLevelSampler;"))
    private static AquiferSampler.FluidLevelSampler aylyth$init(ChunkGeneratorSettings settings, Operation<AquiferSampler.FluidLevelSampler> original, @Local(argsOnly = true) RegistryEntry<ChunkGeneratorSettings> settingsEntry) {
        if (settingsEntry.matchesKey(AylythNoiseSettings.AYLYTH)) {
            return createAylythFluidLevelSampler(settings);
        }
        return original.call(settings);
    }

    // [VanillaCopy]
    private static AquiferSampler.FluidLevelSampler createAylythFluidLevelSampler(ChunkGeneratorSettings settings) {
        var fluidLevel = new AquiferSampler.FluidLevel(-60, settings.defaultBlock());

        var seaLevel = settings.seaLevel();
        var fluidLevel2 = new AquiferSampler.FluidLevel(seaLevel, settings.defaultFluid());
        return (x, y, z) -> y < Math.min(-54, seaLevel) ? fluidLevel : fluidLevel2;
    }
}
