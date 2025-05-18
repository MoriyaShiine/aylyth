package moriyashiine.aylyth.datagen.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(SinglePoolElement.class)
public interface SinglePoolElementInvoker {
    @Invoker("<init>")
    static SinglePoolElement invokeInit(
            Either<Identifier, StructureTemplate> location,
            RegistryEntry<StructureProcessorList> processors,
            StructurePool.Projection projection,
            Optional<StructureLiquidSettings> overrideLiquidSettings
    ) {
        throw new AssertionError("Implemented via mixin");
    }
}