package moriyashiine.aylyth.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AylythDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        boolean runClient = true;
        boolean runServer = true;
        AylythNoiseSettings.init();
        fabricDataGenerator.addProvider(runClient, ModModelProvider::new);
        fabricDataGenerator.addProvider(runServer, ModBiomeTagProvider::new);
        fabricDataGenerator.addProvider(runServer, ModWorldgenProvider::new);
    }
}
