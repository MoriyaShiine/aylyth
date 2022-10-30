package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AylythDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        boolean runClient = true;
        boolean runServer = true;
        ModConfiguredFeatures.datagenInit();
        ModPlacedFeatures.datagenInit();
        ModVegetationFeatures.datagenInit();
        ModBiomes.datagenInit();
        ModDimensions.datagenInit();
        AylythNoiseSettings.datagenInit();
        fabricDataGenerator.addProvider(runClient, AylythModelProvider::new);
        fabricDataGenerator.addProvider(runClient, AylythLanguageProvider::new);
        AylythTagProviders.registerTagProviders(fabricDataGenerator);
        AylythLootTableProviders.registerProviders(fabricDataGenerator);
        fabricDataGenerator.addProvider(runServer, AylythWorldgenProvider::new);
        fabricDataGenerator.addProvider(runServer, AylythRecipeProvider::new);
    }
}
