package moriyashiine.aylyth.datagen.client.model;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.joml.Vector3f;

import java.util.EnumMap;
import java.util.Map;

public final class ModelDisplayTransforms implements Model.JsonFactory {
    public static final Codec<ModelDisplayTransforms> CODEC = Codec.unboundedMap(ModelDisplayType.CODEC, ModelTransform.CODEC)
            .xmap(ModelDisplayTransforms::new, modelDisplayTransforms -> modelDisplayTransforms.transforms);
    public static final Codec<ModelDisplayTransforms> MAP_CODEC_CODEC = CODEC.fieldOf("display").codec();

    private final Map<ModelDisplayType, ModelTransform> transforms;

    public ModelDisplayTransforms(Map<ModelDisplayType, ModelTransform> transforms) {
        this.transforms = transforms;
    }

    @Override
    public JsonObject create(Identifier id, Map<TextureKey, Identifier> textures) {
        return MAP_CODEC_CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow(false, s -> {}).getAsJsonObject();
    }

    public record ModelTransform(Vector3f rotation, Vector3f translation, Vector3f scale) {
        public static final Codec<ModelTransform> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codecs.VECTOR_3F.fieldOf("rotation").forGetter(ModelTransform::rotation),
                        Codecs.VECTOR_3F.fieldOf("translation").forGetter(ModelTransform::translation),
                        Codecs.VECTOR_3F.fieldOf("scale").forGetter(ModelTransform::scale)
                ).apply(instance, ModelTransform::new)
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<ModelDisplayType, ModelTransform> transforms;

        public Builder() {
            this.transforms = new EnumMap<>(ModelDisplayType.class);
        }

        public TransformBuilder transformBuilder(ModelDisplayType displayType) {
            return new TransformBuilder(displayType);
        }

        public ModelDisplayTransforms build() {
            return new ModelDisplayTransforms(transforms);
        }

        public class TransformBuilder {
            private final ModelDisplayType displayType;
            private Vector3f rotation;
            private Vector3f translation;
            private Vector3f scale;

            public TransformBuilder(ModelDisplayType displayType) {
                this.displayType = displayType;
            }

            public TransformBuilder rotation(float x, float y, float z) {
                this.rotation = new Vector3f(x, y, z);
                return this;
            }

            public TransformBuilder translation(float x, float y, float z) {
                this.translation = new Vector3f(x, y, z);
                return this;
            }

            public TransformBuilder scale(float x, float y, float z) {
                this.scale = new Vector3f(x, y, z);
                return this;
            }

            public Builder build() {
                if (rotation == null && translation == null && scale == null) {
                    throw new IllegalStateException("No transformation supplied");
                }
                if (Builder.this.transforms.putIfAbsent(displayType, new ModelTransform(rotation, translation, scale)) != null) {
                    throw new IllegalStateException("Definition already exists for transform type " + displayType.asString());
                }
                return Builder.this;
            }
        }
    }
}
