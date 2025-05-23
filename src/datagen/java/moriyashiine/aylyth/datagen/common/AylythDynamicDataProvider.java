package moriyashiine.aylyth.datagen.common;

import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
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
        entries.addAll(registries.getOrThrow(RegistryKeys.NOISE_PARAMETERS));
        entries.addAll(registries.getOrThrow(RegistryKeys.DENSITY_FUNCTION));
        entries.addAll(registries.getOrThrow(RegistryKeys.CHUNK_GENERATOR_SETTINGS));
        entries.addAll(registries.getOrThrow(RegistryKeys.CONFIGURED_CARVER));
        entries.addAll(registries.getOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(registries.getOrThrow(RegistryKeys.PLACED_FEATURE));
        entries.addAll(registries.getOrThrow(RegistryKeys.PROCESSOR_LIST));
        entries.addAll(registries.getOrThrow(RegistryKeys.TEMPLATE_POOL));
        entries.addAll(registries.getOrThrow(RegistryKeys.STRUCTURE_SET));
        entries.addAll(registries.getOrThrow(RegistryKeys.STRUCTURE));
        entries.addAll(registries.getOrThrow(RegistryKeys.BIOME));
        entries.addAll(registries.getOrThrow(RegistryKeys.DAMAGE_TYPE));
        entries.addAll(registries.getOrThrow(RegistryKeys.JUKEBOX_SONG));
        entries.addAll(registries.getOrThrow(AylythRegistryKeys.LOOT_TABLE_DISPLAY));
    }

    @Override
    public String getName() {
        return "Aylyth Dynamic Data";
    }
}
