package moriyashiine.aylyth.datagen.client.model;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Supplier;

public final class ItemModelOverrides implements Supplier<JsonElement> {
    public static final Codec<ItemModelOverrides> LIST_CODEC = ModelOverride.CODEC.listOf()
            .xmap(ItemModelOverrides::new, itemModelOverrides -> itemModelOverrides.overrides);
    public static final Codec<ItemModelOverrides> MAP_CODEC_CODEC = LIST_CODEC.fieldOf("overrides").codec();

    private final List<ModelOverride> overrides;

    public ItemModelOverrides(List<ModelOverride> overrides) {
        this.overrides = overrides;
    }

    @Override
    public JsonElement get() {
        return MAP_CODEC_CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow(false, s -> {});
    }

    public record ModelOverride(Identifier model, Predicate predicate) {
        public static final Codec<ModelOverride> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Identifier.CODEC.fieldOf("model").forGetter(ModelOverride::model),
                        Predicate.CODEC.fieldOf("predicate").forGetter(ModelOverride::predicate)
                ).apply(instance, ModelOverride::new)
        );
    }

    public record Predicate(Object2FloatMap<Identifier> predicateThresholds) {
        public static final Codec<Predicate> CODEC = Codec.unboundedMap(Identifier.CODEC, Codec.FLOAT)
                .xmap(map -> new Predicate(new Object2FloatOpenHashMap<>(map)), Predicate::predicateThresholds);
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<ModelOverride> overrides;

        Builder() {
            this.overrides = new ObjectArrayList<>();
        }

        public ModelPredicateBuilder overrideBuilder(Identifier model) {
            return new ModelPredicateBuilder(model);
        }

        public ItemModelOverrides finish() {
            return new ItemModelOverrides(overrides);
        }

        public class ModelPredicateBuilder {
            private final Identifier model;
            private final Object2FloatMap<Identifier> predicates;

            ModelPredicateBuilder(Identifier model) {
                this.model = model;
                this.predicates = new Object2FloatOpenHashMap<>();
                this.predicates.defaultReturnValue(-1);
            }

            public ModelPredicateBuilder addPredicate(Identifier predicate, float threshold) {
                if (predicates.putIfAbsent(predicate, threshold) != -1) {
                    throw new IllegalArgumentException("Threshold already registered for predicate {" + predicate + "}");
                }
                return this;
            }

            public Builder build() {
                if (predicates.isEmpty()) {
                    throw new IllegalStateException("Predicates cannot be empty");
                }
                overrides.add(new ModelOverride(model, new Predicate(predicates)));
                return Builder.this;
            }
        }
    }
}
