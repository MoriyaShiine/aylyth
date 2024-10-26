package moriyashiine.aylyth.datagen.common.world.structure;

import moriyashiine.aylyth.common.data.world.structure.AylythStructureProcessorLists;
import moriyashiine.aylyth.common.world.gen.structure.BlackWellStructureProcessor;
import net.minecraft.registry.Registerable;
import net.minecraft.structure.processor.StructureProcessorList;

import java.util.List;

public final class AylythStructureProcessorListBootstrap {
    private AylythStructureProcessorListBootstrap() {}

    public static void bootstrap(Registerable<StructureProcessorList> registerable) {
        registerable.register(AylythStructureProcessorLists.BLACK_WELL, new StructureProcessorList(List.of(new BlackWellStructureProcessor(1, 3, 6))));
    }
}
