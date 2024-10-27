package moriyashiine.aylyth.mixin;

import com.google.common.collect.BiMap;
import net.minecraft.predicate.entity.TypeSpecificPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TypeSpecificPredicate.Deserializers.class)
public interface TypeSpecificPredicateDeserializersAccessor {

    @Mutable
    @Accessor("TYPES")
    static void setTypes(BiMap<String, TypeSpecificPredicate.Deserializer> types) {
        throw new AssertionError("Implemented via mixin");
    }
}
