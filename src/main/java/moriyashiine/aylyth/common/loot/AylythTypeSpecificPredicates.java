package moriyashiine.aylyth.common.loot;

import com.google.common.collect.ImmutableBiMap;
import moriyashiine.aylyth.common.loot.predicates.ScionPredicate;
import moriyashiine.aylyth.mixin.TypeSpecificPredicateDeserializersAccessor;
import net.minecraft.predicate.entity.TypeSpecificPredicate;

public interface AylythTypeSpecificPredicates {

    ScionPredicate.Deserializer SCION = new ScionPredicate.Deserializer();

    static void register() {
        ImmutableBiMap.Builder<String, TypeSpecificPredicate.Deserializer> builder = ImmutableBiMap.<String, TypeSpecificPredicate.Deserializer>builder().putAll(TypeSpecificPredicate.Deserializers.TYPES);

        builder.put("aylyth:scion", SCION);

        TypeSpecificPredicateDeserializersAccessor.setTypes(builder.buildOrThrow());
    }
}
