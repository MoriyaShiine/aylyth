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
import net.minecraft.structure.pool.SinglePoolElement;
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
        RegistryEntry<StructureProcessorList> blackWell = processorListRegistry.getOrThrow(RegistryKey.of(RegistryKeys.PROCESSOR_LIST, Aylyth.id("black_well")));
        context.register(AylythStructurePools.BLACK_WELL, single(emptyPool, Aylyth.id("black_well"), blackWell, StructurePool.Projection.TERRAIN_MATCHING));
    }

    private static StructurePool single(RegistryEntry<StructurePool> fallback, Identifier id, RegistryEntry<StructureProcessorList> processorList, StructurePool.Projection projection) {
        return new StructurePool(fallback, List.of(Pair.of(fromId(id, processorList, projection), 1)));
    }

    private static SinglePoolElement fromId(Identifier id, RegistryEntry<StructureProcessorList> processorList, StructurePool.Projection projection) {
        return SinglePoolElementInvoker.invokeInit(Either.left(id), processorList, projection);
    }
}
