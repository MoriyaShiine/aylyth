package moriyashiine.aylyth.datagen.dynamic.structures;

import moriyashiine.aylyth.common.registry.key.ModStructureKeys;
import moriyashiine.aylyth.common.registry.key.ModStructureSetKeys;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureSet;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.structure.Structure;

public class AylythStructureSetBootstrap {

    public static void bootstrap(Registerable<StructureSet> context) {
        RegistryEntryLookup<Structure> structureLookup = context.getRegistryLookup(RegistryKeys.STRUCTURE);

        RegistryEntry<Structure> blackWell = structureLookup.getOrThrow(ModStructureKeys.BLACK_WELL);

        context.register(ModStructureSetKeys.BLACK_WELL, new StructureSet(blackWell, new RandomSpreadStructurePlacement(5, 2, SpreadType.LINEAR, 776696433)));
    }
}
