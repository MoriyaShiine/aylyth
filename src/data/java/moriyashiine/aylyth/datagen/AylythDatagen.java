package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.datagen.dynamic.AylythDamageTypeBootstrap;
import moriyashiine.aylyth.datagen.dynamic.biomes.AylythBiomeBootstrap;
import moriyashiine.aylyth.datagen.dynamic.features.AylythConfiguredCarverBootstrap;
import moriyashiine.aylyth.datagen.dynamic.features.AylythConfiguredFeatureBootstrap;
import moriyashiine.aylyth.datagen.dynamic.features.AylythPlacedFeatureBootstrap;
import moriyashiine.aylyth.datagen.dynamic.terrain.AylythDensityFunctionBootstrap;
import moriyashiine.aylyth.datagen.dynamic.terrain.AylythNoiseSettingBootstrap;
import moriyashiine.aylyth.datagen.dynamic.terrain.AylythNoiseTypeBootstrap;
import moriyashiine.aylyth.datagen.loot.AylythBlockLootTableProvider;
import moriyashiine.aylyth.datagen.loot.AylythEntityLootTableProvider;
import moriyashiine.aylyth.datagen.tags.*;
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

        var blockTags = pack.addProvider(AylythBlockTagProvider::new);
        pack.addProvider((output, registries) -> new AylythItemTagProvider(output, registries, blockTags));
        pack.addProvider(AylythEntityTypeTagProvider::new);
        pack.addProvider(AylythStatusEffectTagProvider::new);
        pack.addProvider(AylythPotionTagProvider::new);
        pack.addProvider(AylythBiomeTagProvider::new);
        pack.addProvider(AylythDamageTypeTagProvider::new);

        pack.addProvider(AylythBlockLootTableProvider::new);
        pack.addProvider(AylythEntityLootTableProvider::new);

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
