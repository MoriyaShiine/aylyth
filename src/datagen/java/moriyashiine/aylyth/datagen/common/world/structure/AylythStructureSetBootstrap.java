package moriyashiine.aylyth.datagen.common.world.structure;

import moriyashiine.aylyth.common.data.world.structure.AylythStructureSets;
import moriyashiine.aylyth.common.data.world.structure.AylythStructures;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureSet;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.structure.Structure;

public final class AylythStructureSetBootstrap {
    private AylythStructureSetBootstrap() {}

    public static void bootstrap(Registerable<StructureSet> context) {
        RegistryEntryLookup<Structure> structureLookup = context.getRegistryLookup(RegistryKeys.STRUCTURE);
        RegistryEntry<Structure> blackWell = structureLookup.getOrThrow(AylythStructures.BLACK_WELL);
        context.register(AylythStructureSets.BLACK_WELL, new StructureSet(blackWell, new RandomSpreadStructurePlacement(20, 10, SpreadType.LINEAR, 776696433)));
    }
}
