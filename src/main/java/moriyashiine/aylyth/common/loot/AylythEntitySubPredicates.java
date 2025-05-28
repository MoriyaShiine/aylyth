package moriyashiine.aylyth.common.loot;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.loot.subpredicates.ScionPredicate;
import net.minecraft.predicate.entity.EntitySubPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythEntitySubPredicates {

    MapCodec<ScionPredicate> SCION = register("scion", ScionPredicate.CODEC);

    private static <E extends EntitySubPredicate> MapCodec<E> register(String name, MapCodec<E> codec) {
        return Registry.register(Registries.ENTITY_SUB_PREDICATE_TYPE, Aylyth.id(name), codec);
    }

    static void register() {}
}
