package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
import moriyashiine.aylyth.datagen.client.AylythEnglishLanguageProvider;
import moriyashiine.aylyth.datagen.client.AylythModelProvider;
import moriyashiine.aylyth.datagen.common.AylythAdvancementProvider;
import moriyashiine.aylyth.datagen.common.AylythDynamicDataProvider;
import moriyashiine.aylyth.datagen.common.AylythRecipeProvider;
import moriyashiine.aylyth.datagen.common.AylythDamageTypeBootstrap;
import moriyashiine.aylyth.datagen.common.loot.AylythEntityLootAdditionsProvider;
import moriyashiine.aylyth.datagen.common.loot.AylythHarvestLootProvider;
import moriyashiine.aylyth.datagen.common.loot.AylythStripLootProvider;
import moriyashiine.aylyth.datagen.common.loot.LootDisplayBootstrap;
import moriyashiine.aylyth.datagen.common.world.AylythBiomeBootstrap;
import moriyashiine.aylyth.datagen.common.world.feature.AylythConfiguredCarverBootstrap;
import moriyashiine.aylyth.datagen.common.world.feature.AylythConfiguredFeatureBootstrap;
import moriyashiine.aylyth.datagen.common.world.feature.AylythPlacedFeatureBootstrap;
import moriyashiine.aylyth.datagen.common.world.structure.AylythStructureBootstrap;
import moriyashiine.aylyth.datagen.common.world.structure.AylythStructurePoolBootstrap;
import moriyashiine.aylyth.datagen.common.world.structure.AylythStructureProcessorListBootstrap;
import moriyashiine.aylyth.datagen.common.world.structure.AylythStructureSetBootstrap;
import moriyashiine.aylyth.datagen.common.world.terrain.AylythDensityFunctionBootstrap;
import moriyashiine.aylyth.datagen.common.world.terrain.AylythChunkGenSettingsBootstrap;
import moriyashiine.aylyth.datagen.common.world.terrain.AylythNoiseParamsBootstrap;
import moriyashiine.aylyth.datagen.common.loot.AylythBlockLootProvider;
import moriyashiine.aylyth.datagen.common.loot.AylythEntityLootProvider;
import moriyashiine.aylyth.datagen.common.tag.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;

public class AylythDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        var pack = dataGenerator.createPack();

        pack.addProvider(AylythDynamicDataProvider::new);

        pack.addProvider(AylythModelProvider::new);
        pack.addProvider(AylythEnglishLanguageProvider::new);

        var blockTags = pack.addProvider(AylythBlockTagProvider::new);
        pack.addProvider((output, registries) -> new AylythItemTagProvider(output, registries, blockTags));
        pack.addProvider(AylythEntityTypeTagProvider::new);
        pack.addProvider(AylythStatusEffectTagProvider::new);
        pack.addProvider(AylythPotionTagProvider::new);
        pack.addProvider(AylythBiomeTagProvider::new);
        pack.addProvider(AylythDamageTypeTagProvider::new);

        pack.addProvider(AylythRecipeProvider::new);
        pack.addProvider(AylythAdvancementProvider::new);

        pack.addProvider(AylythBlockLootProvider::new);
        pack.addProvider(AylythEntityLootProvider::new);
        pack.addProvider(AylythHarvestLootProvider::new);
        pack.addProvider(AylythStripLootProvider::new);
        pack.addProvider(AylythEntityLootAdditionsProvider::new);
    }

    @Override

    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.NOISE_PARAMETERS, AylythNoiseParamsBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.DENSITY_FUNCTION, AylythDensityFunctionBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.CHUNK_GENERATOR_SETTINGS, AylythChunkGenSettingsBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.CONFIGURED_CARVER, AylythConfiguredCarverBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, AylythConfiguredFeatureBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.PLACED_FEATURE, AylythPlacedFeatureBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.PROCESSOR_LIST, AylythStructureProcessorListBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.TEMPLATE_POOL, AylythStructurePoolBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.STRUCTURE, AylythStructureBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.STRUCTURE_SET, AylythStructureSetBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.BIOME, AylythBiomeBootstrap::bootstrap);
        builder.addRegistry(RegistryKeys.DAMAGE_TYPE, AylythDamageTypeBootstrap::bootstrap);
        builder.addRegistry(AylythRegistryKeys.LOOT_TABLE_DISPLAY, LootDisplayBootstrap::bootstrap);
    }

    @Override
    public String getEffectiveModId() {
        return Aylyth.MOD_ID;
    }
}
