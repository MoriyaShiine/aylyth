package moriyashiine.aylyth.datagen.client.model;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import org.joml.Vector3f;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ModelDisplayTransforms implements Supplier<JsonElement> {
    private static final Vector3f ZERO = new Vector3f();
    public static final Codec<ModelDisplayTransforms> CODEC = Codec.unboundedMap(ModelDisplayType.CODEC, ModelTransform.CODEC)
            .xmap(ModelDisplayTransforms::new, modelDisplayTransforms -> modelDisplayTransforms.transforms);
    public static final Codec<ModelDisplayTransforms> MAP_CODEC_CODEC = CODEC.fieldOf("display").codec();

    private final Map<ModelDisplayType, ModelTransform> transforms;

    public ModelDisplayTransforms(Map<ModelDisplayType, ModelTransform> transforms) {
        this.transforms = transforms;
    }

    @Override
    public JsonElement get() {
        return MAP_CODEC_CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow(false, s -> {});
    }

    public record ModelTransform(Vector3f rotation, Vector3f translation, Vector3f scale) {
        public static final Codec<ModelTransform> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codecs.VECTOR_3F.optionalFieldOf("rotation", ZERO).forGetter(ModelTransform::rotation),
                        Codecs.VECTOR_3F.optionalFieldOf("translation", ZERO).forGetter(ModelTransform::translation),
                        Codecs.VECTOR_3F.optionalFieldOf("scale", ZERO).forGetter(ModelTransform::scale)
                ).apply(instance, ModelTransform::new)
        );
    }

    public static Builder builder() {
        return new Builder(true);
    }

    public static Builder block() {
        Builder builder = new Builder(false);
        return builder.transformBuilder(ModelDisplayType.GUI)
                .rotation(30, 225, 0)
                .scale(0.625f)
                .build()
                .transformBuilder(ModelDisplayType.GROUND)
                .translation(0, 3, 0)
                .scale(0.25f)
                .build()
                .transformBuilder(ModelDisplayType.FIXED)
                .scale(0.5f)
                .build()
                .transformBuilder(ModelDisplayType.THIRD_PERSON_RIGHT_HAND)
                .rotation(75, 45, 0)
                .translation(0, 2.5f, 0)
                .scale(0.375f)
                .build()
                .transformBuilder(ModelDisplayType.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 45, 0)
                .scale(0.40f)
                .build()
                .transformBuilder(ModelDisplayType.GROUND)
                .rotation(0, 225, 0)
                .scale(0.40f)
                .build();
    }

    public static class Builder {
        private final boolean strict;
        private final Map<ModelDisplayType, ModelTransform> transforms;

        Builder(boolean strict) {
            this.strict = strict;
            this.transforms = new EnumMap<>(ModelDisplayType.class);
        }

        public TransformBuilder transformBuilder(ModelDisplayType displayType) {
            return new TransformBuilder(displayType);
        }

        public ModelDisplayTransforms finish() {
            return new ModelDisplayTransforms(transforms);
        }

        public class TransformBuilder {
            private final ModelDisplayType displayType;
            private Vector3f rotation;
            private Vector3f translation;
            private Vector3f scale;

            public TransformBuilder(ModelDisplayType displayType) {
                this.displayType = displayType;
                this.rotation = ZERO;
                this.translation = ZERO;
                this.scale = ZERO;
            }

            public TransformBuilder rotation(float x, float y, float z) {
                this.rotation = new Vector3f(x, y, z);
                return this;
            }

            public TransformBuilder rotation(float scalar) {
                this.rotation = new Vector3f(scalar);
                return this;
            }

            public TransformBuilder translation(float x, float y, float z) {
                this.translation = new Vector3f(x, y, z);
                return this;
            }

            public TransformBuilder translation(float scalar) {
                this.translation = new Vector3f(scalar);
                return this;
            }

            public TransformBuilder scale(float x, float y, float z) {
                this.scale = new Vector3f(x, y, z);
                return this;
            }

            public TransformBuilder scale(float scalar) {
                this.scale = new Vector3f(scalar);
                return this;
            }

            public Builder build() {
                if (rotation == ZERO && translation == ZERO && scale == ZERO) {
                    throw new IllegalStateException("No transformation supplied");
                }
                if (strict && Builder.this.transforms.containsKey(displayType)) {
                    throw new IllegalStateException("Definition already exists for transform type " + displayType.asString());
                }

                Builder.this.transforms.put(displayType, new ModelTransform(rotation, translation, scale));

                return Builder.this;
            }
        }
    }
}
