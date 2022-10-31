package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.*;
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
            ModBiomes.datagenInit();
            ModDimensions.datagenInit();
            AylythNoiseSettings.datagenInit();
            AylythTagProviders.registerTagProviders(fabricDataGenerator);
            AylythLootTableProviders.registerProviders(fabricDataGenerator);
        fabricDataGenerator.addProvider(AylythWorldgenProvider::new);
        fabricDataGenerator.addProvider(AylythRecipeProvider::new);
    }
}
