package moriyashiine.aylyth.datagen.common;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public final class AylythDynamicDataProvider extends FabricDynamicRegistryProvider {
    public AylythDynamicDataProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.NOISE_PARAMETERS));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.DENSITY_FUNCTION));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CHUNK_GENERATOR_SETTINGS));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_CARVER));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.BIOME));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE));
    }

    @Override
    public String getName() {
        return "Aylyth Dynamic Data";
    }
}
