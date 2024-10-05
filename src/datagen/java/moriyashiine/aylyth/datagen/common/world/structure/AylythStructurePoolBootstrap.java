package moriyashiine.aylyth.datagen.common.world.structure;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.world.structure.AylythStructurePools;
import moriyashiine.aylyth.datagen.mixin.SinglePoolElementInvoker;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;

import java.util.List;

public final class AylythStructurePoolBootstrap {
    private AylythStructurePoolBootstrap () {}

    public static void bootstrap(Registerable<StructurePool> context) {
        RegistryEntryLookup<StructurePool> poolRegistry = context.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        RegistryEntry<StructurePool> emptyPool = poolRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TEMPLATE_POOL, new Identifier("empty")));
        RegistryEntryLookup<StructureProcessorList> processorListRegistry = context.getRegistryLookup(RegistryKeys.PROCESSOR_LIST);
        RegistryEntry<StructureProcessorList> emptyList = processorListRegistry.getOrThrow(RegistryKey.of(RegistryKeys.PROCESSOR_LIST, new Identifier("empty")));
        context.register(AylythStructurePools.BLACK_WELL, new StructurePool(emptyPool, List.of(Pair.of(SinglePoolElementInvoker.invokeInit(Either.left(Aylyth.id("black_well")), emptyList, StructurePool.Projection.TERRAIN_MATCHING), 1))));
    }
}
