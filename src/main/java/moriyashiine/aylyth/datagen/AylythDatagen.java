package moriyashiine.aylyth.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.report.WorldgenProvider;
import net.minecraft.data.server.BiomeParametersProvider;

public class AylythDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        boolean runClient = true;
        boolean runServer = true;
        fabricDataGenerator.addProvider(runClient, ModModelProvider::new);
        fabricDataGenerator.addProvider(runServer, ModBiomeTagProvider::new);
        fabricDataGenerator.addProvider(runServer, ModWorldgenProvider::new);
    }
}
