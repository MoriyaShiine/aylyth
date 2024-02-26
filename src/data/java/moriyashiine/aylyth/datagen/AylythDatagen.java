package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.datagen.dynamic.AylythDamageTypeBootstrap;
import moriyashiine.aylyth.datagen.dynamic.biomes.AylythBiomeBootstrap;
import moriyashiine.aylyth.datagen.dynamic.features.AylythConfiguredCarverBootstrap;
import moriyashiine.aylyth.datagen.dynamic.features.AylythConfiguredFeatureBootstrap;
import moriyashiine.aylyth.datagen.dynamic.features.AylythPlacedFeatureBootstrap;
import moriyashiine.aylyth.datagen.dynamic.terrain.AylythDensityFunctionBootstrap;
import moriyashiine.aylyth.datagen.dynamic.terrain.AylythNoiseSettingBootstrap;
import moriyashiine.aylyth.datagen.dynamic.terrain.AylythNoiseTypeBootstrap;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class AylythDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider(AylythModelProvider::new);
        pack.addProvider(AylythLanguageProvider::new);

        pack.addProvider(AylythTagProviders.ModBiomeTagProvider::new);
        var blockTags = pack.addProvider(AylythTagProviders.ModBlockTagProvider::new);
        pack.addProvider((output, registries) -> new AylythTagProviders.ModItemTagProvider(output, registries, blockTags));
        pack.addProvider(AylythTagProviders.ModEntityTypeTagProvider::new);
        pack.addProvider(AylythTagProviders.StatusEffectTagProvider::new);
        pack.addProvider(AylythTagProviders.ModDamageTypeTagProvider::new);

        pack.addProvider(AylythLootTableProviders.BlockLoot::new);
        pack.addProvider(AylythLootTableProviders.EntityLoot::new);

        pack.addProvider(AylythDynamicDataProvider::new);
        pack.addProvider(AylythRecipeProvider::new);
        pack.addProvider(AylythAdvancementProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.NOISE_PARAMETERS, AylythNoiseTypeBootstrap::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.DENSITY_FUNCTION, AylythDensityFunctionBootstrap::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CHUNK_GENERATOR_SETTINGS, AylythNoiseSettingBootstrap::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_CARVER, AylythConfiguredCarverBootstrap::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, AylythConfiguredFeatureBootstrap::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, AylythPlacedFeatureBootstrap::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, AylythBiomeBootstrap::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, AylythDamageTypeBootstrap::bootstrap);
    }
}
