package moriyashiine.aylyth.datagen.dynamic.structures;

import moriyashiine.aylyth.common.registry.key.ModStructureKeys;
import moriyashiine.aylyth.common.registry.key.ModStructurePoolKeys;
import moriyashiine.aylyth.common.registry.tag.ModBiomeTags;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;

import java.util.Map;

public class AylythStructureBootstrap {

    public static void bootstrap(Registerable<Structure> context) {
        RegistryEntryLookup<Biome> biomeLookup = context.getRegistryLookup(RegistryKeys.BIOME);
        RegistryEntryLookup<StructurePool> poolLookup = context.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);

        RegistryEntryList<Biome> blackWellBiomes = biomeLookup.getOrThrow(ModBiomeTags.BLACK_WELL_HAS_STRUCTURE);

        RegistryEntry<StructurePool> blackWellPool = poolLookup.getOrThrow(ModStructurePoolKeys.BLACK_WELL);

        context.register(ModStructureKeys.BLACK_WELL, new JigsawStructure(new Structure.Config(blackWellBiomes, Map.of(), GenerationStep.Feature.SURFACE_STRUCTURES, StructureTerrainAdaptation.NONE), blackWellPool, 1, ConstantHeightProvider.ZERO, false, Heightmap.Type.WORLD_SURFACE_WG));
    }
}
