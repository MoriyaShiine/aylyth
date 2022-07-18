package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStep;

public class ModDimensions {

	public static void init() {}

	//refer to json files in data
	public static final RegistryKey<DimensionOptions> AYLYTH_DIMENSION_KEY = RegistryKey.of(Registry.DIMENSION_KEY, new Identifier(Aylyth.MOD_ID, "aylyth"));
	
	public static RegistryKey<World> AYLYTH = RegistryKey.of(Registry.WORLD_KEY, AYLYTH_DIMENSION_KEY.getValue());

	static {
		Registry.register(Registry.BIOME_SOURCE, new Identifier(Aylyth.MOD_ID, "aylyth_biome_provider"), AylythBiomeSource.CODEC);

		BiomeModification worldGen = BiomeModifications.create(new Identifier(Aylyth.MOD_ID, "world_features"));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.OAK_SEEP.value()));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SPRUCE_SEEP.value()));
		worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ModTags.GENERATES_SEEP), context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DARK_OAK_SEEP.value()));

	}
}
