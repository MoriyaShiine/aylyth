package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.datagen.worldgen.terrain.AylythNoiseSettings;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin {

    @Shadow
    private static native AquiferSampler.FluidLevelSampler createFluidLevelSampler(ChunkGeneratorSettings settings);

    @Redirect(method = "method_45511", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/NoiseChunkGenerator;createFluidLevelSampler(Lnet/minecraft/world/gen/chunk/ChunkGeneratorSettings;)Lnet/minecraft/world/gen/chunk/AquiferSampler$FluidLevelSampler;"))
    private static AquiferSampler.FluidLevelSampler aylyth_init(ChunkGeneratorSettings settings, RegistryEntry<ChunkGeneratorSettings> settingsRegistryEntry) {
        if (settingsRegistryEntry.matchesKey(AylythNoiseSettings.AYLYTH)) {
            return createAylythFluidLevelSampler(settings);
        }
        return createFluidLevelSampler(settings);
    }

    // [VanillaCopy]
    private static AquiferSampler.FluidLevelSampler createAylythFluidLevelSampler(ChunkGeneratorSettings settings) {
        var fluidLevel = new AquiferSampler.FluidLevel(-60, settings.defaultBlock());

        var seaLevel = settings.seaLevel();
        var fluidLevel2 = new AquiferSampler.FluidLevel(seaLevel, settings.defaultFluid());
        return (x, y, z) -> y < Math.min(-54, seaLevel) ? fluidLevel : fluidLevel2;
    }
}
