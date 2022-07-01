package moriyashiine.aylyth.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import moriyashiine.aylyth.common.registry.ModBiomes;
import moriyashiine.aylyth.common.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.BiomeTagProvider;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagEntry;
import net.minecraft.tag.TagFile;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ModBiomeTagProvider extends FabricTagProvider.DynamicRegistryTagProvider<Biome> {
    public ModBiomeTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, Registry.BIOME_KEY);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(ModTags.GENERATES_SEEP).forceAddTag(BiomeTags.IS_FOREST).forceAddTag(BiomeTags.IS_TAIGA);
    }
}
