package moriyashiine.aylyth.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryKey;

import java.io.IOException;

public record ModWorldgenProvider(FabricDataGenerator generator) implements DataProvider {

    @Override
    public void run(DataWriter writer) {
        var builtin = DynamicRegistryManager.BUILTIN.get();
        var dynamicOps = RegistryOps.of(JsonOps.INSTANCE, builtin);
        DynamicRegistryManager.getInfos().forEach(info -> {
            write(info, builtin, writer, dynamicOps);
        });
    }

    <T> void write(DynamicRegistryManager.Info<T> info, DynamicRegistryManager builtin, DataWriter writer, DynamicOps<JsonElement> dynamicOps) {
        var regKey = info.registry();
        var registry = builtin.get(regKey);
        var resolver = generator.createPathResolver(DataGenerator.OutputType.DATA_PACK, regKey.getValue().getPath());
        registry.streamEntries().forEach(entry -> {
            if (entry.registryKey().getValue().getNamespace().equals(Aylyth.MOD_ID)) {
                try {
                    DataProvider.writeToPath(writer, info.entryCodec().encodeStart(dynamicOps, entry.value()).result().get(), resolver.resolveJson(entry.registryKey().getValue()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public String getName() {
        return "Aylyth worldgen generator";
    }
}
