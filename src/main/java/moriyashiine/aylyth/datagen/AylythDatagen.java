package moriyashiine.aylyth.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.report.WorldgenProvider;
import net.minecraft.data.server.BiomeParametersProvider;

public class AylythDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(c -> new ModBiomeTagProvider(c, true));
        //TODO: remove these two once everything is properly genned
        fabricDataGenerator.addProvider(WorldgenProvider::new);
        fabricDataGenerator.addProvider(BiomeParametersProvider::new);
    }
}
