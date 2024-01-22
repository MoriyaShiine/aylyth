package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.datagen.worldgen.biomes.ModBiomes;
import moriyashiine.aylyth.datagen.worldgen.features.ModCarvers;
import moriyashiine.aylyth.datagen.worldgen.features.ModConfiguredFeatures;
import moriyashiine.aylyth.datagen.worldgen.features.ModPlacedFeatures;
import moriyashiine.aylyth.datagen.worldgen.terrain.AylythDensityFunctionTypes;
import moriyashiine.aylyth.datagen.worldgen.terrain.AylythNoiseSettings;
import moriyashiine.aylyth.datagen.worldgen.terrain.AylythNoiseTypes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.*;

public class AylythDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider(AylythModelProvider::new);
        pack.addProvider(AylythLanguageProvider::new);

        pack.addProvider(AylythTagProviders.ModBiomeTags::new);
        var blockTags = pack.addProvider(AylythTagProviders.ModBlockTags::new);
        pack.addProvider((output, registries) -> new AylythTagProviders.ModItemTags(output, registries, blockTags));
        pack.addProvider(AylythTagProviders.ModEntityTypeTags::new);

        pack.addProvider(AylythLootTableProviders.BlockLoot::new);
        pack.addProvider(AylythLootTableProviders.EntityLoot::new);

        pack.addProvider(AylythDynamicDataProvider::new);
        pack.addProvider(AylythRecipeProvider::new);
        pack.addProvider(AylythAdvancementProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.NOISE_PARAMETERS, AylythNoiseTypes::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.DENSITY_FUNCTION, AylythDensityFunctionTypes::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CHUNK_GENERATOR_SETTINGS, AylythNoiseSettings::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_CARVER, ModCarvers::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, AylythDamageTypes::bootstrap);
    }
}
