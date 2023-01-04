package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin {

    @ModifyVariable(method = "<init>", at = @At("STORE"))
    private AquiferSampler.FluidLevel aylyth_init(AquiferSampler.FluidLevel lavaLevel, Registry<StructureSet> structureSetRegistry, Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseRegistry, BiomeSource populationSource, RegistryEntry<ChunkGeneratorSettings> settings) {
        if (settings.matchesKey(RegistryKey.of(Registry.CHUNK_GENERATOR_SETTINGS_KEY, AylythUtil.id("aylyth_settings")))) {
            return new AquiferSampler.FluidLevel(-60, settings.value().defaultBlock());
        }
        return lavaLevel;
    }
}
