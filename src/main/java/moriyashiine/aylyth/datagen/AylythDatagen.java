package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.datagen.worldgen.biomes.ModBiomes;
import moriyashiine.aylyth.datagen.worldgen.features.ModCarvers;
import moriyashiine.aylyth.datagen.worldgen.features.ModConfiguredFeatures;
import moriyashiine.aylyth.datagen.worldgen.features.ModPlacedFeatures;
import moriyashiine.aylyth.datagen.worldgen.features.ModVegetationFeatures;
import moriyashiine.aylyth.datagen.worldgen.terrain.AylythNoiseSettings;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AylythDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(AylythModelProvider::new);
        fabricDataGenerator.addProvider(AylythLanguageProvider::new);

        ModConfiguredFeatures.datagenInit();
        ModPlacedFeatures.datagenInit();
        ModVegetationFeatures.datagenInit();
        ModCarvers.datagenInit();
        ModBiomes.datagenInit();
        AylythNoiseSettings.datagenInit();
        AylythTagProviders.registerTagProviders(fabricDataGenerator);
        AylythLootTableProviders.registerProviders(fabricDataGenerator);

        fabricDataGenerator.addProvider(AylythWorldgenProvider::new);
        fabricDataGenerator.addProvider(AylythRecipeProvider::new);
        fabricDataGenerator.addProvider(AylythAdvancementProvider::new);
    }
}
