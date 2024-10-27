package moriyashiine.aylyth.common.world.gen;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.gen.structure.BlackWellStructureProcessor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

public interface AylythStructureProcessors {

    StructureProcessorType<BlackWellStructureProcessor> BLACK_WELL = register("black_well", BlackWellStructureProcessor.CODEC);

    private static <T extends StructureProcessor> StructureProcessorType<T> register(String id, Codec<T> codec) {
        return Registry.register(Registries.STRUCTURE_PROCESSOR, Aylyth.id(id), () -> codec);
    }

    static void register() {}
}
